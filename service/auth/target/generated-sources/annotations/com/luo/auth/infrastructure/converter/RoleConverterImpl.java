package com.luo.auth.infrastructure.converter;

import com.luo.auth.domain.roleAggregate.entity.Role;
import com.luo.auth.infrastructure.repository.po.RolePO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:44:20+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role rolePOToRole(RolePO rolePO) {
        if ( rolePO == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.roleId( rolePO.getId() );
        role.roleName( rolePO.getRoleName() );

        return role.build();
    }

    @Override
    public List<Role> rolePOSToRoles(List<RolePO> rolePOS) {
        if ( rolePOS == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( rolePOS.size() );
        for ( RolePO rolePO : rolePOS ) {
            list.add( rolePOToRole( rolePO ) );
        }

        return list;
    }
}
