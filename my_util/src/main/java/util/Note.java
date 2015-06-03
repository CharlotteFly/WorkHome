package util;

/**
 * Created by hwyang on 2015/4/9.
 */
public class Note {

    public static class SqlServerPagingSql {
        static final String baseSql = "SELECT  *\n" +
                "FROM    ( SELECT TOP %d\n" +
                "                    ROW_NUMBER() OVER ( ORDER BY dbo.%s.%s DESC ) AS rownum ,\n" +
                "                    *\n" +
                "          FROM      dbo.%s\n" +
                "        ) AS temp\n" +
                "WHERE   temp.rownum > %d\n" +
                "ORDER BY temp.%s";
        String tableName;
        String orderField;
        Integer pageSize;

        public SqlServerPagingSql(String tableName, String orderField, Integer pageSize) {
            this.tableName = tableName;
            this.orderField = orderField;
            this.pageSize = pageSize;
        }

        public String getSql(Integer pageIndex) {
            return String.format(baseSql, pageSize * pageIndex, tableName, orderField, tableName, pageSize * (pageIndex - 1), orderField);
        }
    }

    public static class MySqlPagingSql {
        static final String baseSql = "select * from %s limit %d,%d order by %s";
        String tableName;
        String orderField;
        Integer pageSize;

        public MySqlPagingSql(String tableName, String orderField, Integer pageSize) {
            this.tableName = tableName;
            this.orderField = orderField;
            this.pageSize = pageSize;
        }

        public String getSql(Integer pageIndex) {
            return String.format(baseSql, tableName, pageSize * (pageIndex - 1), pageSize, orderField);
        }
    }
}
