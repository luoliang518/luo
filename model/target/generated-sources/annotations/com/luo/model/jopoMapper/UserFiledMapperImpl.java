package com.luo.model.jopoMapper;

import com.luo.model.user.UserDo;
import com.luo.model.user.UserDto;
import com.luo.model.user.UserRoleDo;
import com.luo.model.user.UserRoleDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-17T16:35:45+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
public class UserFiledMapperImpl implements UserFiledMapper {

    @Override
    public UserDo userDto2Do(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserDo userDo = new UserDo();

        return userDo;
    }

    @Override
    public UserRoleDo userRoleDto2Do(UserRoleDto userRoleDto) {
        if ( userRoleDto == null ) {
            return null;
        }

        UserRoleDo userRoleDo = new UserRoleDo();

        return userRoleDo;
    }
}
