package springMock;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 模拟springIOC
 * Created by hwyang on 2014/9/30.
 */
public class ApplicationContext {
    private Map<String, Object> beanFactory = new HashMap<String, Object>();
    private boolean isInit = false;

    public Map<String, Object> getBeanFactory() {
        if(!isInit){
            try {
                config("analyzer/config.xml");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return beanFactory;
    }

    @SuppressWarnings("unchecked")
    public void config(String path) throws Exception{
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        List<Element> beans = document.selectNodes("//bean");
        for (Element bean : beans) {
            String aClass = bean.attribute("class").getValue();
            String id = bean.attribute("id").getValue();
            Class<?> clazz = Class.forName(aClass);
            Object instance = clazz.newInstance();
            beanFactory.put(id, instance);
            List<Element> parameters = bean.selectNodes("child::property");
            for (Element parameterEle : parameters) {
                String parameterName = parameterEle.attribute("name").getValue();
                Attribute valueAttribute = parameterEle.attribute("value");
                Attribute refAttribute = parameterEle.attribute("ref");
                Class parameterType = getParameterType(clazz, parameterName);
                Object parameterValue ;
                if (valueAttribute != null) {
                    String value = valueAttribute.getText();
                    if (parameterType == String.class) {
                        parameterValue = value;
                    }else{
                        parameterValue = parameterType.getMethod("valueOf",String.class).invoke(parameterType, value);
                    }
                    doSetValueMethod(instance, parameterName, parameterValue);
                }
                if (refAttribute != null) {
                    String ref = refAttribute.getText();
                    parameterValue = beanFactory.get(ref);
                    doSetBeanMethod(instance,parameterName,parameterValue);
                }
            }
        }
        isInit = true;
    }

    private Class getParameterType(Class<?> clazz, String parameterName) throws NoSuchMethodException {
        String methodName = "get" + parameterName.substring(0, 1).toUpperCase() + parameterName.substring(1);
        return clazz.getMethod(methodName).getReturnType();
    }

    private void doSetValueMethod(Object instance, String parameterName, Object arg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + parameterName.substring(0, 1).toUpperCase() + parameterName.substring(1);
        Class argClazz = arg.getClass();
        Method method  = instance.getClass().getMethod(methodName, argClazz);
        method.invoke(instance, arg);
    }

    private void doSetBeanMethod(Object instance, String parameterName, Object arg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + parameterName.substring(0, 1).toUpperCase() + parameterName.substring(1);
        Class argClazz = arg.getClass().getInterfaces()[0];
        Method method  = instance.getClass().getMethod(methodName, argClazz);
        method.invoke(instance, arg);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ApplicationContext();
        applicationContext.config("analyzer/config.xml");
        System.out.println();
    }
}
