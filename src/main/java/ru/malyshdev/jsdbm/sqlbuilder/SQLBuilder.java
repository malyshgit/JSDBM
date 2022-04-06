package ru.malyshdev.jsdbm.sqlbuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLBuilder {

    protected Connection connection;
    protected List<String> sql;
    protected List<Object> values;

    public SQLBuilder(Connection connection){
        sql = new ArrayList<>();
        values = new ArrayList<>();
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

    public SELECT SELECT(Object... columns){
        sql.add("SELECT");
        sql.add(Arrays.stream(columns).map(Object::toString).collect(Collectors.joining(", ")));
        return new SELECT(this);
    }

    public static class SELECT{

        protected SQLBuilder builder;

        public SELECT(SQLBuilder builder){
            this.builder = builder;
        }

        public FROM FROM(Object... tables){
            builder.sql.add("FROM");
            builder.sql.add(Arrays.stream(tables).map(Object::toString).collect(Collectors.joining(", ")));
            return new FROM(builder);
        }
    }

    public static class FROM{

        protected SQLBuilder builder;

        public FROM(SQLBuilder builder){
            this.builder = builder;
        }

        public ResultSet request(){
            try {
                var preparedStatement = builder.connection.prepareStatement(String.join(" ", builder.sql));
                for(var i = 1; i <= builder.values.size(); i++){
                    var value = builder.values.get(i-1);
                    preparedStatement.setObject(i, value);
                }
                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static class CREATE {

        protected SQLBuilder builder;

        public CREATE(SQLBuilder builder) {
            this.builder = builder;
        }

        public TABLE TABLE(){
            builder.sql.add("TABLE");
            return new TABLE(builder);
        }
    }

    public static class DROP {

        protected SQLBuilder builder;

        public DROP(SQLBuilder builder) {
            this.builder = builder;
        }

        public TABLE TABLE(){
            builder.sql.add("TABLE");
            return new TABLE(builder);
        }
    }

    public static class TABLE {

        protected SQLBuilder builder;

        public TABLE(SQLBuilder builder) {
            this.builder = builder;
        }

        public TABLE NAME(String table_name){
            builder.sql.add(table_name);
            return this;
        }

        public TABLE IF_NOT_EXISTS(){
            builder.sql.add("IF NOT EXISTS");
            return this;
        }

        public TABLE IF_EXISTS(){
            builder.sql.add("IF EXISTS");
            return this;
        }

        public TABLE COLUMNS(String[]... columns){
            builder.sql.add("(");
            builder.sql.add(Arrays.stream(columns).map(o -> o[0] + " "+ o[1].toUpperCase()).collect(Collectors.joining(", ")));
            builder.sql.add(")");
            return this;
        }

        public TABLE COLUMNS(Map<String, String> columns){
            builder.sql.add("(");
            builder.sql.add(columns.entrySet().stream().map(e -> e.getKey() + " "+e.getValue()).collect(Collectors.joining(", ")));
            builder.sql.add(")");
            return this;
        }

        public ResultSet request(){
            try {
                var preparedStatement = builder.connection.prepareStatement(String.join(" ", builder.sql));
                for(var i = 1; i <= builder.values.size(); i++){
                    var value = builder.values.get(i-1);
                    preparedStatement.setObject(i, value);
                }
                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
