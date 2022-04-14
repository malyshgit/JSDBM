package ru.malyshdev.jsdbm.sqlbuilder;

import javax.print.attribute.standard.MediaSize;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLBuilder {

    protected StringBuilder sql;

    public SQLBuilder(){
        sql = new StringBuilder();
    }

    public CREATE CREATE(){
        sql.append("CREATE");
        return new CREATE(this);
    }

    public DROP DROP(){
        sql.append("DROP");
        return new DROP(this);
    }

    public DELETE DELETE(){
        sql.append("DELETE");
        return new DELETE(this);
    }

    public static class DELETE{

        SQLBuilder builder;

        public DELETE(SQLBuilder builder){
            this.builder = builder;
        }

        public DELETE FROM(String table_name){
            builder.sql.append(" ");
            builder.sql.append("FROM");
            builder.sql.append(" ");
            builder.sql.append(table_name);
            return this;
        }

        public DELETE WHERE(String condition){
            builder.sql.append(" ");
            builder.sql.append("WHERE");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE AND(String condition){
            builder.sql.append(" ");
            builder.sql.append("AND");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE OR(String condition){
            builder.sql.append(" ");
            builder.sql.append("OR");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE NOT(String condition){
            builder.sql.append(" ");
            builder.sql.append("NOT");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE NOT_LIKE(String condition){
            builder.sql.append(" ");
            builder.sql.append("NOT LIKE");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE BETWEEN(String condition){
            builder.sql.append(" ");
            builder.sql.append("BETWEEN");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE IS(String condition){
            builder.sql.append(" ");
            builder.sql.append("IS");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public DELETE IN(String condition){
            builder.sql.append(" ");
            builder.sql.append("IN");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public Integer execute(Connection connection){
            return builder.executeUpdate(connection);
        }
    }

    public SELECT SELECT(String... columns){
        sql.append("SELECT");
        sql.append(" ");
        sql.append(String.join(", ", columns));
        return new SELECT(this);
    }

    public INSERT INSERT(){
        sql.append("INSERT");
        return new INSERT(this);
    }

    public UPDATE UPDATE(String table_name){
        sql.append("UPDATE");
        sql.append(" ");
        sql.append(table_name);
        return new UPDATE(this);
    }

    public static class UPDATE{

        SQLBuilder builder;

        public UPDATE(SQLBuilder builder){
            this.builder = builder;
        }

        public UPDATE SET(Map<String, Object> columns_and_values){
            builder.sql.append(" ");
            builder.sql.append("SET");
            builder.sql.append(" ");
            builder.sql.append(columns_and_values.entrySet().stream().map(e->e.getKey()+"="+(e.getValue() instanceof WithoutQuotes ? e.getValue().toString() : "'"+e.getValue().toString()+"'")).collect(Collectors.joining(", ")));
            return this;
        }

        public UPDATE WHERE(String condition){
            builder.sql.append(" ");
            builder.sql.append("WHERE");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE AND(String condition){
            builder.sql.append(" ");
            builder.sql.append("AND");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE OR(String condition){
            builder.sql.append(" ");
            builder.sql.append("OR");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE NOT(String condition){
            builder.sql.append(" ");
            builder.sql.append("NOT");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE NOT_LIKE(String condition){
            builder.sql.append(" ");
            builder.sql.append("NOT LIKE");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE BETWEEN(String condition){
            builder.sql.append(" ");
            builder.sql.append("BETWEEN");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE IS(String condition){
            builder.sql.append(" ");
            builder.sql.append("IS");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public UPDATE IN(String condition){
            builder.sql.append(" ");
            builder.sql.append("IN");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public Integer execute(Connection connection){
            return builder.executeUpdate(connection);
        }

    }

    public static class INSERT{

        SQLBuilder builder;

        public INSERT(SQLBuilder builder){
            this.builder = builder;
        }

        public INSERT INTO(String table_name){
            builder.sql.append(" ");
            builder.sql.append("INTO");
            builder.sql.append(" ");
            builder.sql.append(table_name);
            return this;
        }

        public INSERT COLUMNS(String... columns){
            builder.sql.append(" ");
            builder.sql.append("(");
            builder.sql.append(String.join(", ", columns));
            builder.sql.append(")");
            return this;
        }

        public INSERT VALUES(Object... values){
            builder.sql.append(" ");
            builder.sql.append("VALUES");
            builder.sql.append("(");
            builder.sql.append(Arrays.stream(values).map(o-> o instanceof WithoutQuotes ? o.toString() : "'"+o.toString()+"'").collect(Collectors.joining(", ")));
            builder.sql.append(")");
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public Integer execute(Connection connection){
            return builder.executeUpdate(connection);
        }

    }

    public static class SELECT{

        SQLBuilder builder;

        public SELECT(SQLBuilder builder){
            this.builder = builder;
        }

        public SELECT FROM(String... tables){
            builder.sql.append(" ");
            builder.sql.append("FROM");
            builder.sql.append(" ");
            builder.sql.append(String.join(", ", tables));
            return this;
        }

        public SELECT WHERE(String condition){
            builder.sql.append(" ");
            builder.sql.append("WHERE");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT AND(String condition){
            builder.sql.append(" ");
            builder.sql.append("AND");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT OR(String condition){
            builder.sql.append(" ");
            builder.sql.append("OR");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT NOT(String condition){
            builder.sql.append(" ");
            builder.sql.append("NOT");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT NOT_LIKE(String condition){
            builder.sql.append(" ");
            builder.sql.append("NOT LIKE");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT BETWEEN(String condition){
            builder.sql.append(" ");
            builder.sql.append("BETWEEN");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT IS(String condition){
            builder.sql.append(" ");
            builder.sql.append("IS");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public SELECT IN(String condition){
            builder.sql.append(" ");
            builder.sql.append("IN");
            builder.sql.append(" ");
            builder.sql.append(condition);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(Connection connection){
            return builder.executeQuery(connection);
        }

    }

    public static class DROP{

        SQLBuilder builder;

        public DROP(SQLBuilder builder){
            this.builder = builder;
        }

        public DROP TABLE(){
            builder.sql.append(" ");
            builder.sql.append("TABLE");
            return this;
        }

        public DROP IF_EXISTS(){
            builder.sql.append(" ");
            builder.sql.append("IF EXISTS");
            return this;
        }

        public DROP NAME(String table_name){
            builder.sql.append(" ");
            builder.sql.append(table_name);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public Integer execute(Connection connection){
            return builder.executeUpdate(connection);
        }
    }

    public static class CREATE{

        SQLBuilder builder;

        public CREATE(SQLBuilder builder){
            this.builder = builder;
        }

        public CREATE TABLE(){
            builder.sql.append(" ");
            builder.sql.append("TABLE");
            return this;
        }

        public CREATE IF_NOT_EXISTS(){
            builder.sql.append(" ");
            builder.sql.append("IF NOT EXISTS");
            return this;
        }

        public CREATE NAME(String table_name){
            builder.sql.append(" ");
            builder.sql.append(table_name);
            return this;
        }

        public CREATE COLUMNS(Map<String, String> columns){
            builder.sql.append(" ");
            builder.sql.append("(");
            builder.sql.append(columns.entrySet().stream().map(e->e.getKey()+" "+e.getValue()).collect(Collectors.joining(", ")));
            builder.sql.append(")");
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public Integer execute(Connection connection){
            return builder.executeUpdate(connection);
        }
    }


    public ResultSet executeQuery(Connection connection){
        try {
            var preparedStatement = connection.prepareStatement(sql.toString());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer executeUpdate(Connection connection){
        try {
            var preparedStatement = connection.prepareStatement(sql.toString());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public String getSqlQuery(){
        return sql.toString();
    }

}
