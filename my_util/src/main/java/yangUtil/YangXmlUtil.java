package yangUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by hwyang on 2015/2/10.
 */
public class YangXmlUtil {
    /**
     * 读dom文档
     *
     * @throws DocumentException
     */
    public static Document readDocument(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(file);
    }

    public static Document readDocument(Reader reader) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        return saxReader.read(reader);
    }

    public static Document createDocument() {
        return DocumentHelper.createDocument();
    }

    /**
     * 写dom文档
     *
     * @throws IOException
     */
    public static void saveDocument(Document doc, FileWriter writer) throws IOException {
        //  输出全部原始数据，在编译器中显示
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndent("    ");
        format.setEncoding("UTF-8");//根据需要设置编码
        XMLWriter xmlWriter = new XMLWriter(writer, format);
        xmlWriter.write(doc);
        xmlWriter.close();
    }

    public static void reformatXmlDoc(String filePath) throws DocumentException, IOException {
        File file = new File(filePath);
        Document document = readDocument(file);
        saveDocument(document, new FileWriter(file));
    }
}
