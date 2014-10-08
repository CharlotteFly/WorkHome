package factorymethod;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwyang on 2014/9/15.
 *
 * @author hwyang
 */
public class Factories {
    private static final Map<AbstractMap.SimpleEntry<Class<?>, String>, Class<?>> register = new HashMap<>();


    @SuppressWarnings("unchecked")
    public void config(Class<?> productType, String name, Class<? extends Factory> factoryType) {
        register.put((AbstractMap.SimpleEntry<Class<?>, String>) new AbstractMap.SimpleEntry<>(productType, name), factoryType);
    }

    public static Factory newFactory(Class<?> productType, String name) throws IllegalAccessException, InstantiationException {
        return (Factory)register.get(new AbstractMap.SimpleEntry<Class<?>, String>(productType, name)).newInstance();
    }
}
