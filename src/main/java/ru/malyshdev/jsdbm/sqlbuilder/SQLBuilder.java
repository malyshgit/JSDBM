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
    protected List<Object> sql_values;

    public SQLBuilder(Connection connection){
        sql = new ArrayList<>();
        sql_values = new ArrayList<>();
        this.connection = connection;
    }

    public SQLBuilder CREATE(){
        sql.add("CREATE");
        return this;
    }

    public SQLBuilder DROP(){
        sql.add("DROP");
        return this;
    }

    public SQLBuilder SELECT(Object... columns){
        sql.add("SELECT");
        sql.add(Arrays.stream(columns).map(Object::toString).collect(Collectors.joining(", ")));
        return this;
    }

    public SQLBuilder INSERT(){
        sql.add("INSERT");
        return this;
    }

    public SQLBuilder FROM(Object... tables){
        sql.add("FROM");
        sql.add(Arrays.stream(tables).map(Object::toString).collect(Collectors.joining(", ")));
        return this;
    }

    public SQLBuilder INTO(String table_name){
        sql.add("INTO");
        sql.add(table_name);
        return this;
    }

    public SQLBuilder COLUMNS(String... columns){
        sql.add("(");
        sql.add(String.join(", ", columns));
        sql.add(")");
        return this;
    }

    public SQLBuilder VALUES(Object... values){
        sql.add("VALUES (");
        sql.add(Arrays.stream(values).map(v-> "?").collect(Collectors.joining(", ")));
        sql_values.addAll(Arrays.stream(values).toList());
        sql.add(")");
        return this;
    }

    public ResultSet request(){
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


    public SQLBuilder TABLE(){
        sql.add("TABLE");
        return this;
    }

    public SQLBuilder NAME(String table_name){
        sql.add(table_name);
        return this;
    }

    public SQLBuilder IF_NOT_EXISTS(){
        sql.add("IF NOT EXISTS");
        return this;
    }

    public SQLBuilder IF_EXISTS(){
        sql.add("IF EXISTS");
        return this;
    }

    public SQLBuilder COLUMNS(String[]... columns){
        sql.add("(");
        sql.add(Arrays.stream(columns).map(o -> o[0] + " "+ o[1].toUpperCase()).collect(Collectors.joining(", ")));
        sql.add(")");
        return this;
    }

    public SQLBuilder COLUMNS(Map<String, String> columns){
        sql.add("(");
        sql.add(columns.entrySet().stream().map(e -> e.getKey() + " "+e.getValue()).collect(Collectors.joining(", ")));
        sql.add(")");
        return this;
    }
}
