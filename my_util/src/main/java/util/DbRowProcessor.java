package util;

import java.util.Map;

/**
 * Created by hwyang on 2014/8/14.
 *
 * @author hwyang
 */
public interface DbRowProcessor {
    public void doProcess(Map<String, Object> row);
}
