package yangUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串工具类
 * Created by hwyang on 2015/1/6.
 */
public class StringYangUtil {

    public static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String toMD5(String str) {
        md.update(str.getBytes());
        byte[] byteDigest = md.digest();
        int i;
        StringBuilder buf = new StringBuilder("");
        for (byte aByteDigest : byteDigest) {
            i = aByteDigest;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        //32位加密
        return buf.toString();
        // 16位的加密
        //return buf.toString().substring(8, 24);
    }
}
