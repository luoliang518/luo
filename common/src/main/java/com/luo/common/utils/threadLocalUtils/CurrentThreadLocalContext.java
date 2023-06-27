package com.luo.common.utils.threadLocalUtils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.luo.model.user.vo.UserVo;
import lombok.Data;

@Data
public class CurrentThreadLocalContext {
    public static final TransmittableThreadLocal<UserVo> USER_THREAD_LOCAL_CONTEXT = new TransmittableThreadLocal<>();
    public static UserVo getCurrentThreadLocalContext(){
        return USER_THREAD_LOCAL_CONTEXT.get();
    }
    public static void removeCurrentThreadLocalContext(){
        USER_THREAD_LOCAL_CONTEXT.remove();
    }
}
