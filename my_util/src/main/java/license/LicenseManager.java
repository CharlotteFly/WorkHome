package license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hwyang on 2015/11/19.
 */
public class LicenseManager {
    private static String getMacAddress() throws IOException {
        // 执行ipconfig /all命令
        Process p = new ProcessBuilder("ipconfig", "/all").start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("物理地址")) {
                return line.substring(line.indexOf(":") + 1).trim();
            }
        }
        reader.close();
        return "";
    }

    public static void main(String[] args) throws IOException {
        String cpu = getMacAddress();
        System.out.println(cpu);
    }
}
