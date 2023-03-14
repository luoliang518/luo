package com.luo.model.jopoMapper;

import com.luo.model.user.UserDo;
import com.luo.model.user.UserDto;
import com.luo.model.user.UserRoleDo;
import com.luo.model.user.UserRoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author luoliang
 */
@Mapper
public interface UserFiledMapper {

    UserFiledMapper INSTANCE = Mappers.getMapper(UserFiledMapper.class);

    /**
     * userDto->userDo
     * @param userDto
     * @return
     */
    @Mappings({
//            @Mapping(source = "name", target = "studentName"),
//            @Mapping(source = "age", target = "studentAge")
            })
    UserDo userDto2Do(UserDto userDto);

    /**
     * userRoleDto->userRoleDo
     * @param userRoleDto
     * @return
     */
    @Mappings({})
    UserRoleDo userRoleDto2Do(UserRoleDto userRoleDto);
}