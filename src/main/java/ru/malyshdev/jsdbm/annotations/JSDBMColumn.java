package ru.malyshdev.jsdbm.annotations;

import org.postgresql.jdbc.PgBlob;
import org.postgresql.jdbc.PgClob;

import javax.xml.crypto.Data;
import javax.xml.crypto.dom.DOMStructure;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.Reference;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.JDBCType;
import java.sql.SQLData;
import java.sql.Struct;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JSDBMColumn {

    enum DataType{
        CHAR("TEXT"),
        STRING("TEXT"),
        BIGDECIMAL("NUMERIC"),
        BOOLEAN("BIT"),
        BYTE("TINYINT"),
        SHORT("SMALLINT"),
        INTEGER("INT"),
        LONG("BIGINT"),
        FLOAT("REAL"),
        DOUBLE("DOUBLE"),
        BYTE_ARRAY("BINARY"),
        DATE("DATE"),
        TIME("TIME"),
        TIMESTAMP("TIMESTAMP"),
        CLOB("CLOB"),
        BLOB("BLOB"),
        STRING_ARRAY("TEXT ARRAY"),
        BIGDECIMAL_ARRAY("NUMERIC ARRAY"),
        BOOLEAN_ARRAY("BIT ARRAY"),
        SHORT_ARRAY("SMALLINT ARRAY"),
        INTEGER_ARRAY("INT ARRAY"),
        LONG_ARRAY("BIGINT ARRAY"),
        FLOAT_ARRAY("REAL ARRAY"),
        DOUBLE_ARRAY("DOUBLE ARRAY"),
        DATE_ARRAY("DATE ARRAY"),
        TIME_ARRAY("TIME ARRAY"),
        TIMESTAMP_ARRAY("TIMESTAMP ARRAY"),
        CLOB_ARRAY("CLOB ARRAY"),
        BLOB_ARRAY("BLOB ARRAY"),
        STRUCT_ARRAY("STRUCT ARRAY"),
        REF_ARRAY("REF ARRAY"),
        JAVA_OBJECT_ARRAY("JAVA_OBJECT ARRAY"),
        STRUCT("STRUCT"),
        REF("REF"),
        JAVA_OBJECT("JAVA_OBJECT");

        DataType(String name){
            this.name = name;
        }
        private final String name;

        public String getName(){
            return name;
        }

        public static Class<?> getDataClass(DataType dataType){
            return switch (dataType){
                case CHAR -> String.class;
                case STRING -> String.class;
                case BIGDECIMAL -> BigDecimal.class;
                case BOOLEAN -> Boolean.class;
                case BYTE -> Byte.class;
                case SHORT -> Short.class;
                case INTEGER -> Integer.class;
                case LONG -> Long.class;
                case FLOAT -> Float.class;
                case DOUBLE -> Double.class;
                case BYTE_ARRAY -> Byte[].class;
                case DATE -> Date.class;
                case TIME -> LocalTime.class;
                case TIMESTAMP -> Timestamp.class;
                case CLOB -> PgClob.class;
                case BLOB -> PgBlob.class;
                case STRING_ARRAY -> String[].class;
                case BIGDECIMAL_ARRAY -> BigDecimal[].class;
                case BOOLEAN_ARRAY -> Boolean[].class;
                case SHORT_ARRAY -> Short[].class;
                case INTEGER_ARRAY -> Integer[].class;
                case LONG_ARRAY -> Long[].class;
                case FLOAT_ARRAY -> Float[].class;
                case DOUBLE_ARRAY -> Double[].class;
                case DATE_ARRAY -> Date[].class;
                case TIME_ARRAY -> LocalTime[].class;
                case TIMESTAMP_ARRAY -> Timestamp[].class;
                case CLOB_ARRAY -> PgClob[].class;
                case BLOB_ARRAY -> PgBlob[].class;
                case STRUCT_ARRAY -> DOMStructure[].class;
                case REF_ARRAY -> Reference[].class;
                case JAVA_OBJECT_ARRAY -> Object[].class;
                case STRUCT -> DOMStructure.class;
                case REF -> Reference.class;
                case JAVA_OBJECT -> Object.class;
            };
        }
    }
    boolean primary_key() default false;

    boolean not_null() default false;

    String default_value() default "";

    String column_name();

    DataType data_type();
}
