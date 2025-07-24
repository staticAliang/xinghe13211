package com.fengshen.db.mybatis;

import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.apache.ibatis.type.*;
import java.sql.*;

public class JsonStringArrayTypeHandler extends BaseTypeHandler<String[]>
{
    private static final ObjectMapper mapper;
    private final Logger logger;
    
    static {
        mapper = new ObjectMapper();
    }
    
    public JsonStringArrayTypeHandler() {
        this.logger = LoggerFactory.getLogger((Class)JsonStringArrayTypeHandler.class);
    }
    
    public void setNonNullParameter(final PreparedStatement ps, final int i, final String[] parameter, final JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }
    
    public String[] getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }
    
    public String[] getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }
    
    public String[] getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }
    
    private String toJson(final String[] params) {
        try {
            return JsonStringArrayTypeHandler.mapper.writeValueAsString((Object)params);
        }
        catch (Exception e) {
            this.logger.error("", (Throwable)e);
            return "[]";
        }
    }
    
    private String[] toObject(final String content) {
        if (content != null && !content.isEmpty()) {
            try {
                return (String[])JsonStringArrayTypeHandler.mapper.readValue(content, (Class)String[].class);
            }
            catch (Exception e) {
                this.logger.error("", (Throwable)e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
