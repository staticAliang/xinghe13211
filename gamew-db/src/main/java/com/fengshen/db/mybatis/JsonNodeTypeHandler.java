package com.fengshen.db.mybatis;

import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.apache.ibatis.type.*;
import com.fasterxml.jackson.core.*;
import java.io.*;
import java.sql.*;

public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode>
{
    private static final ObjectMapper mapper;
    private final Logger logger;
    
    static {
        mapper = new ObjectMapper();
    }
    
    public JsonNodeTypeHandler() {
        this.logger = LoggerFactory.getLogger((Class)JsonNodeTypeHandler.class);
    }
    
    public void setNonNullParameter(final PreparedStatement ps, final int i, final JsonNode parameter, final JdbcType jdbcType) throws SQLException {
        String str = null;
        try {
            str = JsonNodeTypeHandler.mapper.writeValueAsString((Object)parameter);
        }
        catch (JsonProcessingException e) {
            this.logger.error("", (Throwable)e);
            str = "{}";
        }
        ps.setString(i, str);
    }
    
    public JsonNode getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        final String jsonSource = rs.getString(columnName);
        if (jsonSource == null) {
            return null;
        }
        try {
            return JsonNodeTypeHandler.mapper.readTree(jsonSource);
        }
        catch (IOException e) {
            this.logger.error("", (Throwable)e);
            return null;
        }
    }
    
    public JsonNode getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        final String jsonSource = rs.getString(columnIndex);
        if (jsonSource == null) {
            return null;
        }
        try {
            return JsonNodeTypeHandler.mapper.readTree(jsonSource);
        }
        catch (IOException e) {
            this.logger.error("", (Throwable)e);
            return null;
        }
    }
    
    public JsonNode getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        final String jsonSource = cs.getString(columnIndex);
        if (jsonSource == null) {
            return null;
        }
        try {
            return JsonNodeTypeHandler.mapper.readTree(jsonSource);
        }
        catch (IOException e) {
            this.logger.error("", (Throwable)e);
            return null;
        }
    }
}
