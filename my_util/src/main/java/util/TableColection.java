package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class TableColection<R, C, V extends Object> {

    private Object[][] value;
    private ArrayList<R> rows;
    private ArrayList<C> cols;

    public TableColection() {
    }

    public Collection<V> getRow(R row) {
        Collection<V> result = new HashSet<V>();
        int rowIndex = rows.indexOf(row);
        for (int i = 0; i < cols.size(); i++) {
            result.add((V) value[rowIndex][i]);
        }
        return result;
    }

    public Collection<V> getCol(C col) {
        Collection<V> result = new HashSet<V>();
        int colIndex = cols.indexOf(col);
        for (int i = 0; i < cols.size(); i++) {
            result.add((V) value[i][colIndex]);
        }
        return result;
    }

    public V get(R row, C col) {
        int rowIndex = rows.indexOf(row);
        int colIndex = cols.indexOf(col);
        return (V) value[rowIndex][colIndex];
    }

//    public V put(R row, C col, Object o) {
//        return (V) value[rowIndex][colIndex];
//    }

}
