package util;

import java.io.Reader;

/**
 * Created by hwyang on 2015/1/23.
 */
public class StringIntegerMapDic extends BaseMapDic<String, Integer> {

    public StringIntegerMapDic(Reader in) {
        super(in);
    }

    public StringIntegerMapDic(Reader in, LineHandler handler) {
        super(in, handler);
    }

    @Override
    public String setKey(String key) {
        return key;
    }

    @Override
    public Integer setValue(String value) {
        return Integer.parseInt(value);
    }

}
