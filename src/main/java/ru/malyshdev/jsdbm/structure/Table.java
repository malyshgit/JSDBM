package ru.malyshdev.jsdbm.structure;

import ru.malyshdev.jsdbm.annotations.JSDBMColumn;
import ru.malyshdev.jsdbm.annotations.JSDBMTable;
import ru.malyshdev.jsdbm.sqlbuilder.SQLBuilder;

import java.lang.invoke.TypeDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Array;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class Table<E> {

    private ru.malyshdev.jsdbm.structure.DataBase dataBase;
    private ConcurrentLinkedQueue<E> entry_list;
    protected String table_name;

    public Table(){
        entry_list = new ConcurrentLinkedQueue<>();
        var table_class = this.getClass().getAnnotation(JSDBMTable.class);
        this.table_name = table_class.table_name();
    }

    public Table(String table_name, Map<String, JSDBMColumn.DataType> columns){
        entry_list = new ConcurrentLinkedQueue<>();
        var table_class = this.getClass().getAnnotation(JSDBMTable.class);
        this.table_name = table_class.table_name();
    }

    public String getName(){
        return table_name;
    }

    protected void link(ru.malyshdev.jsdbm.structure.DataBase dataBase){
        this.dataBase = dataBase;
        try {
            var entry_class = Class.forName((((ParameterizedType) this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName()).getDeclaredConstructors()[0];

            Map<String, String> columns = new java.util.HashMap<>(Map.of());

            Arrays.stream(entry_class.newInstance().getClass().getDeclaredFields()).forEachOrdered(field -> {
                var annotation = field.getAnnotation(JSDBMColumn.class);
                if (annotation != null) {
                    String value = annotation.data_type().getName();

                    if(annotation.primary_key()) value += " PRIMARY KEY";
                    if(annotation.not_null()) value += " NOT NULL";
                    if(!annotation.default_value().isEmpty()) value += " "+annotation.default_value();

                    columns.put(annotation.column_name(), value);
                }
            });
            System.out.println(columns);
            new SQLBuilder()
                    .CREATE()
                    .TABLE()
                    .IF_NOT_EXISTS()
                    .NAME(table_name)
                    .COLUMNS(columns)
                    .execute(dataBase.getConnection());

            var result = new SQLBuilder()
                    .SELECT("*")
                    .FROM(table_name)
                    .execute(dataBase.getConnection());

            while (result.next()) {
                var entry_instance = entry_class.newInstance();
                var fields = entry_instance.getClass().getDeclaredFields();
                for (var field : fields) {
                    var annotation = field.getAnnotation(JSDBMColumn.class);
                    if (annotation != null) {
                        var column_name = annotation.column_name();
                        Object value;
                        if(result.getObject(column_name) instanceof Array){
                            if(Collection.class.isAssignableFrom(field.getType())){
                                value = Arrays.stream(
                                        (Object[])result.getArray(column_name).getArray())
                                        .toList().stream()
                                        .map(o ->
                                                JSDBMColumn.DataType.getDataClass(annotation.data_type()).getComponentType()
                                                        .cast(o))
                                        .collect(Collectors.toList());
                            }else {
                                value = result.getArray(column_name).getArray();
                            }
                        }else{
                            value = result.getObject(column_name, field.getType());
                        }
                        field.setAccessible(true);
                        field.set(entry_instance, value);
                        field.setAccessible(false);
                    }
                }
                addEntry((E) entry_instance);
            }

        } catch (SQLException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void addEntry(E entry){
        entry_list.add(entry);
    }

    public ConcurrentLinkedQueue<E> getEntryList() {
        return entry_list;
    }

    public void setEntryList(ConcurrentLinkedQueue<E> entry_list) {
        this.entry_list = entry_list;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

}
