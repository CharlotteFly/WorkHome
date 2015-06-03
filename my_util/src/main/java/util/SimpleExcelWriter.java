package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by hwyang on 2015/1/12.
 */
public class SimpleExcelWriter {
    //    private SXSSFWorkbook wb = new SXSSFWorkbook(1000);
    private XSSFWorkbook wb;
    private Sheet sheet;
    private String outputPath;

    private SimpleExcelWriter() {
        wb = new XSSFWorkbook();
        sheet = wb.createSheet();
    }

    public SimpleExcelWriter(String outputPath) {
        this();
        this.outputPath = outputPath;
        Row row = sheet.createRow(0);
    }

    public SimpleExcelWriter(String outputPath, String... headers) {
        this();
        this.outputPath = outputPath;
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    public SimpleExcelWriter(String outputPath, Map<String, Map<String, String>> map) {
        this();
        this.outputPath = outputPath;
        List rowList = new ArrayList();
        rowList.add("");
        rowList.addAll(map.keySet());
        List colList = new ArrayList();
        colList.add("");

    }


    public void writeRow(String... cells) {
//        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
//        for (int i = 0; i < cells.length; i++) {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(cells[i]);
//        }
        writeRow(Arrays.asList(cells));
    }

    public void writeRow(List<Object> cells) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = row.createCell(i);
            Object o = cells.get(i);
            cell.setCellValue(generateString(o));
        }
    }


    public void writeRow(Object... cells) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < cells.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(generateString(cells[i]));
        }
    }

    private String generateString(Object cell) {
        if (cell instanceof Collection) {
            StringBuilder builder = new StringBuilder();
            Collection<Object> coll = (Collection<Object>) cell;
            for (Object s : coll) {
                builder.append(s.toString()).append("\r\n");
            }
            return builder.toString();
        }
        return cell.toString();
    }

    public void save() throws IOException {
        wb.write(new FileOutputStream(outputPath));
    }

    public void writeTableMap(TableListMap tableListMap) {

    }

    public static void main(String[] args) {
        SimpleExcelWriter simpleExcelWriter = new SimpleExcelWriter("");
    }


}
