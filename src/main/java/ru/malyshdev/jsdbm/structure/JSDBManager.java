package ru.malyshdev.jsdbm.structure;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JSDBManager {

    private static List<DataBase> dataBases;

    public static DataBase connect(String jdbc_database_url){
        dataBases = new ArrayList<>();
        DataBase dataBase = new DataBase(jdbc_database_url);
        dataBases.add(dataBase);
        return dataBase;
    }

    public static List<DataBase> getDataBases() {
        return dataBases;
    }
}
