package com.luo.auth.infrastructure.converter;

import com.luo.auth.domain.roleAggregate.entity.Permission;
import com.luo.auth.infrastructure.repository.po.PermissionPO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:44:20+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PermissionConverterImpl implements PermissionConverter {

    @Override
    public Permission permissionPoToPermission(PermissionPO permissionPO) {
        if ( permissionPO == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.permissionId( permissionPO.getId() );
        permission.permissionParId( permissionPO.getPermissionParId() );
        permission.permissionLevel( permissionPO.getPermissionLevel() );
        permission.permissionCode( permissionPO.getPermissionCode() );
        permission.permissionName( permissionPO.getPermissionName() );
        permission.permissionDesc( permissionPO.getPermissionDesc() );

        return permission.build();
    }
}
