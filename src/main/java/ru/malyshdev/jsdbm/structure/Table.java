package ru.malyshdev.jsdbm.structure;

import ru.malyshdev.jsdbm.annotations.JSDBMAnnotations;
import ru.malyshdev.jsdbm.sqlbuilder.SQLBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Table<E> {

    private ru.malyshdev.jsdbm.structure.DataBase dataBase;
    private ConcurrentLinkedQueue<E> entry_list;
    protected String table_name;

    public Table(){
        entry_list = new ConcurrentLinkedQueue<>();
        var table_class = this.getClass().getAnnotation(JSDBMAnnotations.Table.class);
        this.table_name = table_class.table_name();
    }

    public String getName(){
        return table_name;
    }

    protected void link(ru.malyshdev.jsdbm.structure.DataBase dataBase){
        this.dataBase = dataBase;
        try {
            var result = new SQLBuilder(dataBase.getConnection())
                    .SELECT("*")
                    .FROM(table_name)
                    .execute();

            var entry_class = Class.forName((((ParameterizedType) this.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName()).getDeclaredConstructors()[0];

            while (result.next()) {
                var entry_instance = entry_class.newInstance();
                var fields = entry_instance.getClass().getDeclaredFields();
                for (var field : fields) {
                    var annotation = field.getAnnotation(JSDBMAnnotations.Column.class);
                    if (annotation != null) {
                        var column_name = annotation.column_name();
                        var value = result.getObject(column_name, field.getType());
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

}
