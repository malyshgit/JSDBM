package ru.malyshdev.jsdbm.sqlbuilder;

public class WithoutQuotes{

    private final String originalString;
    public WithoutQuotes(String originalString){
        this.originalString = originalString;
    }

    public String getOriginalString() {
        return originalString;
    }

    @Override
    public String toString() {
        return originalString;
    }
}
