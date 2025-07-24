package com.fengshen.db.mybatis;

import java.util.*;
import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.apache.ibatis.type.*;
import java.sql.*;

public class JsonMapTypeHandler extends BaseTypeHandler<Map<String, Object>>
{
    private static final ObjectMapper mapper;
    private final Logger logger;
    
    static {
        mapper = new ObjectMapper();
    }
    
    public JsonMapTypeHandler() {
        this.logger = LoggerFactory.getLogger((Class)JsonMapTypeHandler.class);
    }
    
    public void setNonNullParameter(final PreparedStatement ps, final int i, final Map<String, Object> parameter, final JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }
    
    public Map<String, Object> getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }
    
    public Map<String, Object> getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }
    
    public Map<String, Object> getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }
    
    private String toJson(final Map<String, Object> params) {
        try {
            return JsonMapTypeHandler.mapper.writeValueAsString((Object)params);
        }
        catch (Exception e) {
            this.logger.error("", (Throwable)e);
            return "[]";
        }
    }
    
    private Map<String, Object> toObject(final String content) {
        if (content != null && !content.isEmpty()) {
            try {
                return (Map<String, Object>)JsonMapTypeHandler.mapper.readValue(content, (Class)Map.class);
            }
            catch (Exception e) {
                this.logger.error("", (Throwable)e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
