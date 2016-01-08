package yangUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

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

    //xml转换成bean
    public static <T> T parseToBean(String xmlstr,Class<T> clazz)  {
        T requestXml = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            requestXml = (T)um.unmarshal(new StringReader(xmlstr));
        } catch (JAXBException e) {
            e.getMessage();
        }
        return requestXml;
    }

    //xml转换成bean
    public static <T> T parseToBean(File file,Class<T> clazz)  {
        T requestXml = null;
        try {
            Document document = readDocument(file);
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            requestXml = (T) um.unmarshal(new StringReader(document.asXML()));
        } catch (JAXBException e) {
            e.getMessage();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return requestXml;
    }

    //bean 转换成 xml
    private static String parseToXml(Object javaBean) throws Exception{
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(javaBean.getClass());
        Marshaller  marshal = context.createMarshaller();
        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
        marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
        marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
        marshal.setProperty("jaxb.encoding", "utf-8");
        marshal.marshal(javaBean,writer);
        return new String(writer.getBuffer());
    }

    //bean 转换成 xml
    public static void parseToFile(Object javaBean,File output) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(javaBean.getClass());
        Marshaller  marshal = context.createMarshaller();
        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
        marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
        marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
        marshal.setProperty("jaxb.encoding", "utf-8");
        FileWriter writer = new FileWriter(output);
        marshal.marshal(javaBean, writer);
    }


    public static void main(String[] args) throws Exception {
    }
}
