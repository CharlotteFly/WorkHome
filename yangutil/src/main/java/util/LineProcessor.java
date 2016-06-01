package util;

import java.io.IOException;

/**
 *
 * Created by hwyang on 2014/8/7.
 */
public interface LineProcessor {
    /**
     * 对每一行进行处理
     * @param line
     */
    public boolean processLine(String line) throws IOException;

}
