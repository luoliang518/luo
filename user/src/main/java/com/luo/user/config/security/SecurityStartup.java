package com.luo.user.config.security;

import cn.induschain.dal.dao.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author luoliang
 * @description 项目启动时将所有接口上标注的权限存入数据库
 * @date 2023/10/23 14:43
 */
@Component
public class SecurityStartup implements ApplicationRunner {
    @Autowired
    private RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 扫描并获取所有需要权限处理的接口资源(该方法逻辑写在下面)
//        List<Resource> list = getAuthResources();
        // 先删除所有操作权限类型的权限资源，待会再新增资源，以实现全量更新（注意哦，数据库中不要设置外键，否则会删除失败）
//        resourceService.deleteResourceByType(1);
        // 如果权限资源为空，就不用走后续数据插入步骤
//        if (Collections.isEmpty(list)) {
//            return;
//        }
        // 将资源数据批量添加到数据库
//        resourceService.insertResources(list);
    }

    /**
     * 扫描并返回所有需要权限处理的接口资源
     */
    private List<Permission> getAuthResources() {
        // 接下来要添加到数据库的资源
        List<Permission> list = new LinkedList<>();
        // 拿到所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingInfoHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, handlerMethod) -> {
            // 拿到类(模块)上的权限注解
            Security moduleAuth = handlerMethod.getBeanType().getAnnotation(Security.class);
            // 拿到接口方法上的权限注解
            Security methodAuth = handlerMethod.getMethod().getAnnotation(Security.class);
            // 模块注解和方法注解缺一个都代表不进行权限处理
            if (moduleAuth == null || methodAuth == null) {
                return;
            }

            // 拿到该接口方法的请求方式(GET、POST等)
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            // 如果一个接口方法标记了多个请求方式，权限id是无法识别的，不进行处理
            if (methods.size() != 1) {
                return;
            }
            // 将请求方式和路径用`:`拼接起来，以区分接口。比如：GET:/user/{id}、POST:/user/{id}
            String path = methods.toArray()[0] + ":" + info.getPatternsCondition().getPatterns().toArray()[0];
            // 将权限名、资源路径、资源类型组装成资源对象，并添加集合中
            Permission resource = new Permission();
//                resource.setType(1)
//                        .setPath(path)
//                        .setName(methodAuth.name())
//                        .setId(moduleAuth.id() + methodAuth.id());
            list.add(resource);
        });
        return list;
    }
}