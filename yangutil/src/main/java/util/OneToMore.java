package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 一对多
 * Created by hwyang on 2014/12/24.
 */
public class OneToMore<O, M> extends HashMap<O, List<M>> {

    @Override
    public List<M> put(O key, List<M> value) {
        return super.put(key, value);
    }

    public boolean putValue(O key, M vUnit) {
        List<M> ms = this.get(key);
        if (ms == null) {
            ms = new ArrayList<>();
            this.put(key, ms);
        }
        return ms.add(vUnit);
    }

}
