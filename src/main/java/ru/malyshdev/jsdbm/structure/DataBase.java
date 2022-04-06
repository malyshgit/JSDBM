package ru.malyshdev.jsdbm.structure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private String jdbc_database_url;

    private Connection connection;

    private List<Table<?>> tables;

    public DataBase(String jdbc_database_url){
        this.jdbc_database_url = jdbc_database_url;
        this.tables = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(jdbc_database_url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void link(Table<?> table){
        tables.add(table);
        table.link(this);
    }

    public Connection getConnection() {
        return connection;
    }
}
