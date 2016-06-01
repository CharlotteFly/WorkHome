package util;

import java.util.*;

public class Table<R,C,V extends Object> {

    private Object[][] value;
    private ArrayList<R> rows;
    private ArrayList<C> cols;

    public Table(final Map<R, Map<C, V>> map) {
        this.rows = new ArrayList<R>(map.keySet());
        Set<C> set = new HashSet<C>();
        for (Map<C, V> values : map.values()) {
            for (C c : values.keySet()) {
                set.add(c);
            }
        }
        this.cols = new ArrayList<C>(set);
        value = new Object[this.rows.size()][this.cols.size()];
        for (int row = 0; row < rows.size(); row++) {
            for (int col = 0; col < cols.size(); col++) {
                value[row][col] = map.get(rows.get(row)).get(cols.get(col));
            }
        }
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


}
