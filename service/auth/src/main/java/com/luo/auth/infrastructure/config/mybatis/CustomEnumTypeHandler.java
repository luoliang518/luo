package com.luo.auth.infrastructure.config.mybatis;

import com.luo.common.base.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomEnumTypeHandler<E extends Enum<E> & BaseEnum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public CustomEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type 参数不能为 null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String enumName = rs.getString(columnName);
        return fromDesc(enumName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String enumName = rs.getString(columnIndex);
        return fromDesc(enumName);
    }

    @Override
    public E getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String enumName = cs.getString(columnIndex);
        return fromDesc(enumName);
    }

    private E fromDesc(String enumName) {
        for (E constant : type.getEnumConstants()) {
            if (constant.name().equals(enumName)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("没有匹配的枚举常量: " + enumName);
    }
}
