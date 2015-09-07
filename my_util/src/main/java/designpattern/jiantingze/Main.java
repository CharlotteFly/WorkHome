package designpattern.jiantingze;

public class Main {
    /**
     * 监听者三要素：
     * 1.事件(Event)
     * 2.事件源(EventSource)
     * 3.响应事件的监听者(Listener)
     * <p/>
     * <p/>
     * 1、事件源添加监听者
     * 2、事件源触发事件
     * 3、把事件传递给各个监听者，监听者响应事件
     */
    public static void main(String[] args) {
        Programmer pro = new Programmer();//生成一个事件源对象，将要做事情  
        pro.addListener(new PM());//增加监听者  
        pro.addListener(new Director());//增加监听者  
        pro.addListener(new Chief());//增加监听者  
        pro.updateCode();//程序猿去修改代码  
    }
}  