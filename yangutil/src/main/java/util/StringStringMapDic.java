package util;

import java.io.Reader;

/**
 * Created by hwyang on 2015/1/23.
 */
public class StringStringMapDic extends BaseMapDic<String, String> {

    public StringStringMapDic(Reader in) {
        super(in);
    }

    public StringStringMapDic(Reader in, LineHandler handler) {
        super(in, handler);
    }

    @Override
    public String setKey(String key) {
        return key;
    }

    @Override
    public String setValue(String value) {
        return value;
    }

}
