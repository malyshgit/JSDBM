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

    public SELECT SELECT(Object... columns){
        sql.add("SELECT");
        sql.add(Arrays.stream(columns).map(Object::toString).collect(Collectors.joining(", ")));
        return new SELECT(this);
    }

    public INSERT INSERT(Object... columns){
        sql.add("INSERT");
        sql.add(Arrays.stream(columns).map(Object::toString).collect(Collectors.joining(", ")));
        return new INSERT(this);
    }

    public static class INSERT{

        SQLBuilder builder;

        public INSERT(SQLBuilder builder){
            this.builder = builder;
        }

        public INTO INTO(String table_name){
            builder.sql.add("INTO");
            builder.sql.add(table_name);
            return new INTO(builder);
        }

        public static class INTO{

            SQLBuilder builder;

            public INTO(SQLBuilder builder){
                this.builder = builder;
            }

            public INTO COLUMNS(String... columns){
                builder.sql.add("(");
                builder.sql.add(String.join(", ", columns));
                builder.sql.add(")");
                return this;
            }

            public INTO VALUES(Object... values){
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

    }

    public static class SELECT{

        SQLBuilder builder;

        public SELECT(SQLBuilder builder){
            this.builder = builder;
        }

        public FROM FROM(Object... tables){
            builder.sql.add("FROM");
            builder.sql.add(Arrays.stream(tables).map(Object::toString).collect(Collectors.joining(", ")));
            return new FROM(builder);
        }

        public static class FROM{

            SQLBuilder builder;

            public FROM(SQLBuilder builder){
                this.builder = builder;
            }

            public WHERE WHERE(String condition){
                builder.sql.add("WHERE");
                builder.sql.add(condition);
                return new WHERE(builder);
            }

            public String getSqlQuery(){
                return builder.getSqlQuery();
            }

            public ResultSet execute(){
                return builder.execute();
            }

            public static class WHERE{

                SQLBuilder builder;

                public WHERE(SQLBuilder builder){
                    this.builder = builder;
                }

                public WHERE AND(String condition){
                    builder.sql.add("AND");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public WHERE OR(String condition){
                    builder.sql.add("OR");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public WHERE NOT(String condition){
                    builder.sql.add("NOT");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public WHERE NOT_LIKE(String condition){
                    builder.sql.add("NOT LIKE");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public WHERE BETWEEN(String condition){
                    builder.sql.add("BETWEEN");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public WHERE IS(String condition){
                    builder.sql.add("IS");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public WHERE IN(String condition){
                    builder.sql.add("IN");
                    builder.sql.add(condition);
                    return new WHERE(builder);
                }

                public String getSqlQuery(){
                    return builder.getSqlQuery();
                }

                public ResultSet execute(){
                    return builder.execute();
                }

            }

        }

    }

    public static class DROP{

        SQLBuilder builder;

        public DROP(SQLBuilder builder){
            this.builder = builder;
        }

        public TABLE TABLE(){
            builder.sql.add("TABLE");
            return new TABLE(builder);
        }

        public static class TABLE {

            SQLBuilder builder;

            public TABLE(SQLBuilder builder){
                this.builder = builder;
            }

            public TABLE IF_EXISTS(){
                if(builder.sql.size() == 2) builder.sql.add("IF EXISTS");
                return this;
            }

            public String getSqlQuery(){
                return builder.getSqlQuery();
            }

            public ResultSet execute(){
                return builder.execute();
            }

        }
    }

    public static class CREATE{

        SQLBuilder builder;

        public CREATE(SQLBuilder builder){
            this.builder = builder;
        }

        public TABLE TABLE(){
            builder.sql.add("TABLE");
            return new TABLE(builder);
        }

        public static class TABLE {

            SQLBuilder builder;

            public TABLE(SQLBuilder builder){
                this.builder = builder;
            }

            public TABLE IF_NOT_EXISTS(){
                if(builder.sql.size() == 2) builder.sql.add("IF NOT EXISTS");
                return this;
            }

            public TABLE NAME(String table_name){
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
