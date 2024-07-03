package com.luo.auth.infrastructure.acl;

import com.luo.auth.domain.messageAggergate.entity.VerificationCode;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.auth.infrastructure.util.IPUtil;
import com.luo.common.enums.CacheKeyEnum;
import com.luo.common.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class CacheAcl {
    private final RedissonClient redisson;

    /**
     * 防止并发生成多个token的锁
     */
    public User getUserLock(HttpServletRequest request, User user) {
        RLock lock = redisson.getLock(CacheKeyEnum.GenerateToken.create(user.getAccount()));
        try {
            if (!lock.tryLock(0, 60, TimeUnit.SECONDS)) {
                RBucket<User> bucket = redisson.getBucket(CacheKeyEnum.User.create(
                        user.getAccount(), IPUtil.getIPAddress(request),
                        CacheKeyEnum.UserInfo.create())
                );
                if (bucket.isExists()) {
                    return bucket.get();
                }
                return null;
            }
        } catch (InterruptedException e) {
            throw new ServiceException(e);
        }
        return null;
    }


    public void saveUserTokenCache(HttpServletRequest request, User user, String token) {
        // 存入缓存
        redisson.getBucket(CacheKeyEnum.User.create(user.getAccount(), IPUtil.getIPAddress(request),
                        CacheKeyEnum.UserToken.create()))
                .set(token, user.getToken().getExpires(), TimeUnit.SECONDS);
        redisson.getBucket(CacheKeyEnum.User.create(user.getAccount(), IPUtil.getIPAddress(request),
                        CacheKeyEnum.UserInfo.create()))
                .set(user, user.getToken().getExpires(), TimeUnit.SECONDS);
    }

    public User getUserInfo(String account, HttpServletRequest request) {
        return (User) redisson.getBucket(CacheKeyEnum.User.create(
                account, IPUtil.getIPAddress(request),
                CacheKeyEnum.UserInfo.create())
        ).get();
    }

    public long getEmailKeepLive(VerificationCode verificationCode) {
        return redisson.getBucket(CacheKeyEnum.SendEmailCode.create(verificationCode.getEmailMessage().getEmail()))
                .remainTimeToLive();
    }

    public VerificationCode getEmailCode(VerificationCode verificationCode) {
        return ((VerificationCode) redisson.getBucket(CacheKeyEnum.SendEmailCode.create(verificationCode.getEmailMessage().getEmail()))
                .get());
    }

    public void saveEmailCode(VerificationCode verificationCode) {
        redisson.getBucket(CacheKeyEnum.SendEmailCode.create(verificationCode.getEmailMessage().getEmail()))
                .set(verificationCode,verificationCode.getExpiration(), TimeUnit.SECONDS);
    }
}
