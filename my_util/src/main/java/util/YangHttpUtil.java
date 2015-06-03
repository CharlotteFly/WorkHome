package util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hwyang on 2015/6/3.
 */
public class YangHttpUtil {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8021/analysis/aspect");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("");// 提交模式
        conn.setConnectTimeout(50000);// 连接超时 单位毫秒
        conn.setReadTimeout(15000);// 读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
    }
}
