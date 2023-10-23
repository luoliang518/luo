package com.luo.user.config.security;

import cn.induschain.common.exception.ServiceException;
import cn.induschain.common.redis.RedisUtil;
import cn.induschain.common.response.ResultCode;
import cn.induschain.dal.dao.Permission;
import cn.induschain.dal.dao.RolePermission;
import cn.induschain.dal.dao.common.UserContextHandler;
import cn.induschain.dal.repository.PermissionRepository;
import cn.induschain.dal.repository.RolePermissionRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luoliang
 * @description 权限拦截 暂时实现 TODO 使用redis进行优化
 * @date 2023/10/23 14:31
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是静态资源，或者没有UserContext直接放行 如果没有userContext则代表该路径已经在Shiro中被标记为放行路径
        if (!(handler instanceof HandlerMethod) || UserContextHandler.get() == null) {
            return true;
        }

        // 如果用uri判断的话就是/API/user/test/100，就和路径参数匹配不上了，所以要用这种方式获得
        String pattern = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        // 将请求方式（GET、POST等）和请求路径用 : 拼接起来，等下好进行判断。最终拼成字符串的就像这样：DELETE:/API/user
        String path = request.getMethod() + ":" + pattern;

        // 拿到所有权限路径 和 当前用户拥有的权限路径
        List<Permission> permissions = permissionRepository.getBaseMapper().selectList(null);
        Set<String> allPaths = permissions.stream().map(Permission::getUri).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        String roleId = UserContextHandler.get().getRoleId();
        List<RolePermission> rolePermissions = rolePermissionRepository.getBaseMapper().selectList(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getJsid, roleId));
        List<Permission> userPermissions = permissionRepository.getBaseMapper().selectList(new LambdaQueryWrapper<Permission>()
                .in(Permission::getId, rolePermissions.stream().map(RolePermission::getQxid).collect(Collectors.toSet())));
        Set<String> userPaths = userPermissions.stream().map(Permission::getUri).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        // 第一个判断：所有权限路径中包含该接口，才代表该接口需要权限处理，所以这是先决条件，
        // 第二个判断：判断该接口是不是属于当前用户的权限范围，如果不是，则代表该接口用户没有权限
        if (allPaths.contains(path) && !userPaths.contains(path)) {
            throw new ServiceException(ResultCode.DO_NOT_HAVE_THIS_PERMISSION.getMessage());
        }
        // 有权限就放行
        return true;
    }

}