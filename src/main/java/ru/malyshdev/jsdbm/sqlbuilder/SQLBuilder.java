package ru.malyshdev.jsdbm.sqlbuilder;

import javax.print.attribute.standard.MediaSize;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SQLBuilder {

    protected StringBuilder sql;
    protected LinkedList<Object> sql_values;

    public SQLBuilder(){
        sql = new StringBuilder();
        sql_values = new LinkedList<>();
    }

    public SQLBuilder SQL(String sql_string){
        sql.append(sql_string);
        return this;
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

        public DELETE WHERE(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("WHERE");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE AND(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("AND");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE OR(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("OR");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE NOT(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("NOT");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE NOT_LIKE(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("NOT LIKE");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE BETWEEN(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("BETWEEN");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE IS(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("IS");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public DELETE IN(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("IN");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
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

        public UPDATE SET(TripleMap<String, String, Object> columns_and_values){
            builder.sql.append(" ");
            builder.sql.append("SET");
            builder.sql.append(" ");
            for(var cv : columns_and_values.getEntryList()){
                builder.sql.append(cv.getKey());
                builder.sql.append("=");
                if(cv.hasValue1()){
                    builder.sql.append(cv.getValue1());
                }else{
                    builder.sql.append("?");
                }
                if(cv.hasValue2()){
                    builder.sql_values.add(cv.getValue2());
                }
            };
            return this;
        }

        public UPDATE WHERE(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("WHERE");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE AND(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("AND");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE OR(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("OR");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE NOT(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("NOT");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE NOT_LIKE(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("NOT LIKE");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE BETWEEN(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("BETWEEN");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE IS(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("IS");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public UPDATE IN(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("IN");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
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
            builder.sql.append(Arrays.stream(values).map(o-> {
                builder.sql_values.add(o);
                return "?";
            }).collect(Collectors.joining(", ")));
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

        public SELECT WHERE(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("WHERE");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT AND(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("AND");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT OR(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("OR");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT NOT(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("NOT");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT NOT_LIKE(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("NOT LIKE");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT BETWEEN(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("BETWEEN");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT IS(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("IS");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
            return this;
        }

        public SELECT IN(TripleMap.TripleEntry<String, String, Object> condition){
            builder.sql.append(" ");
            builder.sql.append("IN");
            builder.sql.append(" ");
            builder.sql.append(condition.getKey());
            if(condition.hasValue1() || condition.hasValue2()){
                builder.sql.append("=");
            }else return this;
            if(condition.hasValue1()){
                builder.sql.append(condition.getValue1());
            }else{
                builder.sql.append("?");
            }
            if(condition.hasValue2()){
                builder.sql_values.add(condition.getValue2());
            }
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

    public Integer executeUpdate(Connection connection){
        try {
            var preparedStatement = connection.prepareStatement(sql.toString());
            for(var i = 1; i <= sql_values.size(); i++){
                var value = sql_values.get(i-1);
                preparedStatement.setObject(i, value);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public String getSqlQuery(){
        return sql.toString().replaceAll("\\?", "%s").formatted(sql_values.stream().map(Object::toString).toList());
    }

}
