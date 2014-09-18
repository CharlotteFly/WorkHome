package factorymethod;

/**
 * Created by hwyang on 2014/9/15.
 *
 * @author hwyang
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Map;

/**
 * 一个更符合生产环境要求的通用工厂类型
 *
 * 因为Java 5/6/7泛型运行态的类型擦除，无法直接 new T()或者访问T.class
 * 所以在相关方法需要重复定义类型参数<T>和Class<?> abstractClass参数
 * 使用中，两个参数一般应保持一致
 *
 * @author wangxiang
 *
 */
public interface Factory{

    /**
     * 构造目标实例
     * @param <T>	目标产品类型，一般采用抽象的产品类型
     * @param abstractClass	抽象产品类型
     * @return	目标实例
     */
    <T> T newInstance(final Class<?> abstractClass) throws InstantiationException, IllegalAccessException;

    /**
     * 构造目标实例
     * @param <T>	目标产品类型，一般采用抽象的产品类型
     * @param abstractClass	抽象产品类型
     * @param params	构造参数
     * @return	目标实例
     */
    <T> T newInstance(final Class<?> abstractClass, final Object[] params) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException;

    /**
     * 构造目标实例
     * @param <T>	目标产品类型，一般采用抽象的产品类型
     * @param abstractClass	抽象产品类型
     * @param name 逻辑名称
     * @return	目标实例
     */
    <T> T newInstance(final Class<?> abstractClass, final String name) throws InstantiationException, IllegalAccessException;

    /**
     * 构造目标实例
     * @param <T>	目标产品类型，一般采用抽象的产品类型
     * @param abstractClass	抽象产品类型
     * @param params	构造参数
     * @return	目标实例
     */
    <T> T newInstance(Class<?> abstractClass, String name, Object[] params) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException;

    /**
     * 配置抽象产品类型与具体产品类型的映射关系
     * 项目中，也可以通过这些重载的接口，注入从配置文件提取的配置信息
     * @param abstractClass	抽象的产品类型
     * @param implClass	具体产品类型
     * @param name 指定目标产品的逻辑名称
     * @return	工厂类型自身，定义上采用连贯接口的方式
     */
    Factory config(final Class<?> abstractClass, final Class<?> implClass, String name);
    Factory config(final Class<?> abstractClass, final Class<?> implClass);
    Factory config(Map<AbstractMap.SimpleEntry<Class<?>, String>, Class<?>> configSettings);

    /**
     * 获得指定类型使用特定构造参数的构造函数
     * @param clazz	指定类型
     * @param params	构造函数参数列表
     * @return	构造函数
     */
    Constructor<?> getConstructor(Class<?> clazz, Object[] params)
            throws ClassNotFoundException, NoSuchMethodException,
            IllegalArgumentException, SecurityException, InstantiationException,
            IllegalAccessException, InvocationTargetException;

    /**
     * 获得指定类型使用特定构造参数的构造函数
     * @param clazz 指定类型
     * @param paramTypes	构造函数参数类型列表
     * @return	构造函数
     */
    Constructor<?> getConstructor(final Class<?> clazz, final Class<?>[] paramTypes)
            throws SecurityException, NoSuchMethodException;
}
