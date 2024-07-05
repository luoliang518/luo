package com.luo.auth.interfaces.facade.controller;

import com.luo.auth.application.user.dto.vo.UserVO;
import com.luo.auth.application.user.service.TenantAppService;
import com.luo.auth.domain.userAggregate.entity.User;
import com.luo.common.result.Response;
import com.luo.common.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Slf4j
public class TenantController {
    private final TenantAppService tenantAppService;
    /**
     * 项目正常测试接口
     */
    @GetMapping("/say")
    public Response<String> sayHello(){
        return ResponseUtil.success("Hello");
    }
    /**
     * 用户选择租户
     */
    @PostMapping("/choiceTenant")
    public Response<UserVO> choiceTenant(@RequestBody Long tenantId, HttpServletRequest request){
        return ResponseUtil.success(tenantAppService.choiceTenant(tenantId,request));
    }
}
