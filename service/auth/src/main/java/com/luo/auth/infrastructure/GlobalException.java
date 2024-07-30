package com.luo.auth.infrastructure;

import com.luo.common.exception.ServiceException;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/7/29
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler({ServiceException.class})
    public Response<?> handleServiceException(HttpServletRequest request, ServiceException e) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        Response<?> result = ResponseUtil.error(e.getCode(), e.getMsg());

        ResponseUtil.putExtraNoData(result, "stackTrace", e.getStackTrace());
        ResponseUtil.putExtraNoData(result, "exceptionMessage", e.getClass().getName() + ": " + e.getMessage());

        return result;
    }
}
