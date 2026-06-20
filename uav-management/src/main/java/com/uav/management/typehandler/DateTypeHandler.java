package com.uav.management.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SQLite日期类型处理器
 * 处理SQLite日期与Java Date类型的转换
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == JdbcType.DATE) {
            ps.setString(i, DATE_FORMAT.format(parameter));
        } else {
            ps.setString(i, DATETIME_FORMAT.format(parameter));
        }
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return parseDate(value);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return parseDate(value);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return parseDate(value);
    }

    private Date parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            // 尝试解析日期时间格式
            if (value.contains(" ")) {
                return DATETIME_FORMAT.parse(value);
            } else {
                // 尝试解析日期格式
                return DATE_FORMAT.parse(value);
            }
        } catch (ParseException e) {
            throw new TypeException("Error parsing date string: " + value, e);
        }
    }
}