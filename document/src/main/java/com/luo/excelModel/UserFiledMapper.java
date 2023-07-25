package com.luo.excelModel;

import com.luo.model.user.dto.UserDto;
import com.luo.model.user.dto.UserRoleDto;
import com.luo.model.user.entity.UserDo;
import com.luo.model.user.entity.UserRoleDo;
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
    @Mappings({})
    UserExportVO userDoDto2Vo(UserDo userDo);

    @Mappings({})
    UserRoleVo userRoleDoDto2Vo(UserRoleDo userRoleDo);
}