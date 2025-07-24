package com.fengshen.db.mybatis;

import java.util.*;
import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.apache.ibatis.type.*;
import java.sql.*;

public class JsonListTypeHandler extends BaseTypeHandler<List>
{
    private static final ObjectMapper mapper;
    private final Logger logger;
    
    static {
        mapper = new ObjectMapper();
    }
    
    public JsonListTypeHandler() {
        this.logger = LoggerFactory.getLogger((Class)JsonListTypeHandler.class);
    }
    
    public void setNonNullParameter(final PreparedStatement ps, final int i, final List parameter, final JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }
    
    public List getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }
    
    public List getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }
    
    public List getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }
    
    private String toJson(final List params) {
        try {
            return JsonListTypeHandler.mapper.writeValueAsString((Object)params);
        }
        catch (Exception e) {
            this.logger.error("", (Throwable)e);
            return "[]";
        }
    }
    
    private List toObject(final String content) {
        if (content != null && !content.isEmpty()) {
            try {
                return (List)JsonListTypeHandler.mapper.readValue(content, (Class)List.class);
            }
            catch (Exception e) {
                this.logger.error("", (Throwable)e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
