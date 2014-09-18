package factorymethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;


/**
 * 这个类有很多内容没有优化
 * 配置信息、Constructor都可以通过缓存进一步优化
 */
public class FactoryImpl implements Factory {

    public static final String DEFAULT_NAME = "default";

    private Map<SimpleEntry<Class<?>, String>, Class<?>> registry = new HashMap<>();

    @Override
    public <T> T newInstance(final Class<?> abstractClass) throws InstantiationException, IllegalAccessException {
        return newInstance(abstractClass, DEFAULT_NAME);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T newInstance(final Class<?> abstractClass, final String name) throws IllegalAccessException, InstantiationException {
        return (T) (registry.get(new SimpleEntry<Class<?>, String>(abstractClass, name)).newInstance());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T newInstance(Class<?> abstractClass, String name, Object[] params) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class<?> targetType = registry.get(new SimpleEntry<Class<?>, String>(abstractClass, name));
        Constructor<?> constructor = getConstructor(targetType, params);
        return (T) constructor.newInstance(params);
    }

    @Override
    public <T> T newInstance(Class<?> abstractClass, Object[] params) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return newInstance(abstractClass, DEFAULT_NAME, params);
    }

    @Override
    public Factory config(Class<?> abstractClass, Class<?> implClass, String name) {
        registry.put(new SimpleEntry<>(abstractClass, name), implClass);
        return this;
    }

    @Override
    public Factory config(Class<?> abstractClass, Class<?> implClass) {
        return config(abstractClass, implClass, DEFAULT_NAME);
    }

    @Override
    public Factory config(Map<SimpleEntry<Class<?>, String>, Class<?>> configSettings) {
        registry.clear();
        registry = configSettings;
        return this;
    }

    @Override
    public Constructor<?> getConstructor(Class<?> clazz, Object[] params) throws NoSuchMethodException {
        if ((params == null) || (params.length == 0))
            return clazz.getDeclaredConstructor((Class<?>[]) null);
        Class<?>[] parameterTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++)
            parameterTypes[i] = params[i].getClass();
        return getConstructor(clazz, parameterTypes);
    }

    @Override
    public Constructor<?> getConstructor(Class<?> clazz, Class<?>[] paramTypes) throws NoSuchMethodException {
        if ((paramTypes == null) || (paramTypes.length == 0))
            return clazz.getDeclaredConstructor((Class<?>[]) null);
        return clazz.getDeclaredConstructor(paramTypes);
    }

}
