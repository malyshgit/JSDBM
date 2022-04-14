package ru.malyshdev.jsdbm.sqlbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TripleMap<X,Y,Z> {

    private final List<TripleEntry<X,Y,Z>> entryList;

    public TripleMap(){
        this.entryList = new ArrayList<>();
    }

    public static <X,Y,Z> TripleMap<X, Y, Z> of(TripleEntry<X,Y,Z>... entries){
        var tm = new TripleMap<X, Y, Z>();
        for(var e : entries){
            tm.put(e);
        }
        return tm;
    }

    public void put(X key, Y value1, Z value2){
        entryList.add(new TripleEntry<>(key, value1, value2));
    }

    public void put(TripleEntry<X,Y,Z> entry){
        entryList.add(entry);
    }

    public List<TripleEntry<X, Y, Z>> getEntryList() {
        return entryList;
    }

    public static class TripleEntry<X,Y,Z>{

        private X key;
        private Y value1;
        private Z value2;

        public TripleEntry(){

        }

        public TripleEntry(X key, Y value1, Z value2){
            this.key = key;
            this.value1 = value1;
            this.value2 = value2;
        }

        public X getKey() {
            return key;
        }

        public void setKey(X key) {
            this.key = key;
        }

        public Y getValue1() {
            return value1;
        }

        public void setValue1(Y value1) {
            this.value1 = value1;
        }

        public Z getValue2() {
            return value2;
        }

        public void setValue2(Z value2) {
            this.value2 = value2;
        }
    }

}
