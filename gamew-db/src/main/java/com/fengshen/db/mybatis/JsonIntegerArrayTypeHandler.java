package com.fengshen.db.mybatis;

import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.apache.ibatis.type.*;
import java.sql.*;

public class JsonIntegerArrayTypeHandler extends BaseTypeHandler<Integer[]>
{
    private static final ObjectMapper mapper;
    private final Logger logger;
    
    static {
        mapper = new ObjectMapper();
    }
    
    public JsonIntegerArrayTypeHandler() {
        this.logger = LoggerFactory.getLogger((Class)JsonIntegerArrayTypeHandler.class);
    }
    
    public void setNonNullParameter(final PreparedStatement ps, final int i, final Integer[] parameter, final JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }
    
    public Integer[] getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }
    
    public Integer[] getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }
    
    public Integer[] getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }
    
    private String toJson(final Integer[] params) {
        try {
            return JsonIntegerArrayTypeHandler.mapper.writeValueAsString((Object)params);
        }
        catch (Exception e) {
            this.logger.error("", (Throwable)e);
            return "[]";
        }
    }
    
    private Integer[] toObject(final String content) {
        if (content != null && !content.isEmpty()) {
            try {
                return (Integer[])JsonIntegerArrayTypeHandler.mapper.readValue(content, (Class)Integer[].class);
            }
            catch (Exception e) {
                this.logger.error("", (Throwable)e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
