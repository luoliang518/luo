package com.luo.auth.infrastructure.acl;

import com.luo.auth.domain.messageAggergate.valueObject.VerificationCode;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.common.enums.CacheKeyEnum;
import com.luo.common.exception.ServiceException;
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
    /*---------------------------------------------token---------------------------------------------*/
    /**
     * 防止并发生成多个token的锁
     */
    public User getUserLock(User user) {
        RLock lock = redisson.getLock(CacheKeyEnum.GenerateToken.create(user.getAccount()));
        try {
            if (!lock.tryLock(0, 60, TimeUnit.SECONDS)) {
                RBucket<User> bucket = redisson.getBucket(CacheKeyEnum.UserLogin.create(
                        user.getAccount(), user.getIp(),
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

    public void saveUserTokenCache(User user) {
        // 用户当前信息存入缓存
        redisson.getBucket(CacheKeyEnum.UserLogin.create(user.getAccount(), user.getIp(),
                        CacheKeyEnum.UserInfo.create()))
                .set(user, user.getToken().getTokenSurvivalTime(), TimeUnit.SECONDS);
        // 将该token放入缓存新token处 如果新token已经有值，则移入token再赋予新token值
        redisson.getBucket(CacheKeyEnum.UserLogin.create(user.getAccount(), user.getIp(),
                        CacheKeyEnum.UserToken.create()))
                .set(user.getToken(), user.getToken().getTokenSurvivalTime(), TimeUnit.SECONDS);
    }

    public User getUserInfo(String account, String ip) {
        try {
            return (User) redisson.getBucket(CacheKeyEnum.UserLogin.create(
                    account, ip,
                    CacheKeyEnum.UserInfo.create())
            ).get();
        }catch (Exception e){
            return null;
        }
    }
    /*---------------------------------------------email---------------------------------------------*/
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