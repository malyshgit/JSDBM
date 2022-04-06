package ru.malyshdev.jsdbm.sqlbuilder;

import javax.print.attribute.standard.MediaSize;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLBuilder {

    protected Connection connection;
    protected List<String> sql;
    protected List<Object> sql_values;

    public SQLBuilder(Connection connection){
        sql = new ArrayList<>();
        sql_values = new ArrayList<>();
        this.connection = connection;
    }

    public CREATE CREATE(){
        sql.add("CREATE");
        return new CREATE(this);
    }

    public DROP DROP(){
        sql.add("DROP");
        return new DROP(this);
    }

    public DELETE DELETE(){
        sql.add("DELETE");
        return new DELETE(this);
    }

    public static class DELETE{

        SQLBuilder builder;

        public DELETE(SQLBuilder builder){
            this.builder = builder;
        }

        public DELETE FROM(String table_name){
            builder.sql.add("FROM");
            builder.sql.add(table_name);
            return this;
        }

        public DELETE WHERE(String condition){
            builder.sql.add("WHERE");
            builder.sql.add(condition);
            return this;
        }

        public DELETE AND(String condition){
            builder.sql.add("AND");
            builder.sql.add(condition);
            return this;
        }

        public DELETE OR(String condition){
            builder.sql.add("OR");
            builder.sql.add(condition);
            return this;
        }

        public DELETE NOT(String condition){
            builder.sql.add("NOT");
            builder.sql.add(condition);
            return this;
        }

        public DELETE NOT_LIKE(String condition){
            builder.sql.add("NOT LIKE");
            builder.sql.add(condition);
            return this;
        }

        public DELETE BETWEEN(String condition){
            builder.sql.add("BETWEEN");
            builder.sql.add(condition);
            return this;
        }

        public DELETE IS(String condition){
            builder.sql.add("IS");
            builder.sql.add(condition);
            return this;
        }

        public DELETE IN(String condition){
            builder.sql.add("IN");
            builder.sql.add(condition);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(){
            return builder.execute();
        }
    }

    public SELECT SELECT(String... columns){
        sql.add("SELECT");
        sql.add(Arrays.stream(columns).map(Object::toString).collect(Collectors.joining(", ")));
        return new SELECT(this);
    }

    public INSERT INSERT(String... columns){
        sql.add("INSERT");
        sql.add(Arrays.stream(columns).map(Object::toString).collect(Collectors.joining(", ")));
        return new INSERT(this);
    }

    public UPDATE UPDATE(String table_name){
        sql.add("UPDATE");
        sql.add(table_name);
        return new UPDATE(this);
    }

    public static class UPDATE{

        SQLBuilder builder;

        public UPDATE(SQLBuilder builder){
            this.builder = builder;
        }

        public UPDATE AND_SET(String column, String value){
            builder.sql.add(", ");
            builder.sql.add(column);
            builder.sql.add("=");
            builder.sql.add(value);
            return this;
        }

        public UPDATE SET(String column, String value){
            builder.sql.add("SET");
            builder.sql.add(column);
            builder.sql.add("=");
            builder.sql.add(value);
            return this;
        }

        public UPDATE AND_SET(String column, Object value){
            builder.sql.add(",");
            builder.sql.add(column);
            builder.sql.add("=?");
            builder.sql_values.add(value);
            return this;
        }


        public UPDATE SET(String column, Object value){
            builder.sql.add("SET");
            builder.sql.add(column);
            builder.sql.add("=?");
            builder.sql_values.add(value);
            return this;
        }

        public UPDATE WHERE(String condition){
            builder.sql.add("WHERE");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE AND(String condition){
            builder.sql.add("AND");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE OR(String condition){
            builder.sql.add("OR");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE NOT(String condition){
            builder.sql.add("NOT");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE NOT_LIKE(String condition){
            builder.sql.add("NOT LIKE");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE BETWEEN(String condition){
            builder.sql.add("BETWEEN");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE IS(String condition){
            builder.sql.add("IS");
            builder.sql.add(condition);
            return this;
        }

        public UPDATE IN(String condition){
            builder.sql.add("IN");
            builder.sql.add(condition);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(){
            return builder.execute();
        }

    }

    public static class INSERT{

        SQLBuilder builder;

        public INSERT(SQLBuilder builder){
            this.builder = builder;
        }

        public INSERT INTO(String table_name){
            builder.sql.add("INTO");
            builder.sql.add(table_name);
            return this;
        }

        public INSERT COLUMNS(String... columns){
            builder.sql.add("(");
            builder.sql.add(String.join(", ", columns));
            builder.sql.add(")");
            return this;
        }

        public INSERT VALUES(Object... values){
            builder.sql.add("VALUES");
            builder.sql.add("(");
            builder.sql.add(Arrays.stream(values).map(v->"?").collect(Collectors.joining(", ")));
            builder.sql_values.addAll(Arrays.stream(values).toList());
            builder.sql.add(")");
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(){
            return builder.execute();
        }

    }

    public static class SELECT{

        SQLBuilder builder;

        public SELECT(SQLBuilder builder){
            this.builder = builder;
        }

        public SELECT FROM(Object... tables){
            builder.sql.add("FROM");
            builder.sql.add(Arrays.stream(tables).map(Object::toString).collect(Collectors.joining(", ")));
            return this;
        }

        public SELECT WHERE(String condition){
            builder.sql.add("WHERE");
            builder.sql.add(condition);
            return this;
        }

        public SELECT AND(String condition){
            builder.sql.add("AND");
            builder.sql.add(condition);
            return this;
        }

        public SELECT OR(String condition){
            builder.sql.add("OR");
            builder.sql.add(condition);
            return this;
        }

        public SELECT NOT(String condition){
            builder.sql.add("NOT");
            builder.sql.add(condition);
            return this;
        }

        public SELECT NOT_LIKE(String condition){
            builder.sql.add("NOT LIKE");
            builder.sql.add(condition);
            return this;
        }

        public SELECT BETWEEN(String condition){
            builder.sql.add("BETWEEN");
            builder.sql.add(condition);
            return this;
        }

        public SELECT IS(String condition){
            builder.sql.add("IS");
            builder.sql.add(condition);
            return this;
        }

        public SELECT IN(String condition){
            builder.sql.add("IN");
            builder.sql.add(condition);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(){
            return builder.execute();
        }

    }

    public static class DROP{

        SQLBuilder builder;

        public DROP(SQLBuilder builder){
            this.builder = builder;
        }

        public DROP TABLE(){
            builder.sql.add("TABLE");
            return this;
        }

        public DROP IF_EXISTS(){
            if(builder.sql.size() == 2) builder.sql.add("IF EXISTS");
            return this;
        }

        public DROP NAME(String table_name){
            builder.sql.add(table_name);
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(){
            return builder.execute();
        }
    }

    public static class CREATE{

        SQLBuilder builder;

        public CREATE(SQLBuilder builder){
            this.builder = builder;
        }

        public CREATE TABLE(){
            builder.sql.add("TABLE");
            return this;
        }

        public CREATE IF_NOT_EXISTS(){
            if(builder.sql.size() == 2) builder.sql.add("IF NOT EXISTS");
            return this;
        }

        public CREATE NAME(String table_name){
            builder.sql.add(table_name);
            return this;
        }

        public CREATE COLUMNS(Map<String, String> columns){
            builder.sql.add("(");
            builder.sql.add(columns.entrySet().stream().map(e->e.getKey()+" "+e.getValue()).collect(Collectors.joining(", ")));
            builder.sql.add(")");
            return this;
        }

        public String getSqlQuery(){
            return builder.getSqlQuery();
        }

        public ResultSet execute(){
            return builder.execute();
        }
    }


    public ResultSet execute(){
        try {
            var preparedStatement = connection.prepareStatement(String.join(" ", sql));
            for(var i = 1; i <= sql_values.size(); i++){
                var value = sql_values.get(i-1);
                preparedStatement.setObject(i, value);
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getSqlQuery(){
        return String.format(String.join(" ", sql).replaceAll("\\?", "%s"), sql_values);
    }

}
