package yangUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
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
        sheet.createRow(0);
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

    public void writeRow(String... cells) {
        writeRow(Arrays.asList(cells));
    }

    public void writeRow(List<String> cells) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cells.get(i));
        }
    }

    public void writeRow(Object... cells) {
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum > 10000) {
            return;
        }
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        for (int i = 0; i < cells.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(generateString(cells[i]));
        }
    }

    private String generateString(Object cell) {
        if (cell instanceof Collection) {
            StringBuilder builder = new StringBuilder();
            Collection<String> coll = (Collection<String>) cell;
            for (String s : coll) {
                builder.append(s).append("\r\n");
            }
            return builder.toString();
        }
        return cell.toString();
    }

    public void save() throws IOException {
        wb.write(new FileOutputStream(outputPath));
    }

    public static class ColorCell {

    }

}
