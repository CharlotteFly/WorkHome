package designpattern.jiantingze;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 程序员要根据编程事件的属性进行操作
 */
class Programmer {
    //程序猿被n个人监听着
    private List<ActionEventListener> list = new ArrayList<>();

    public void addListener(ActionEventListener ael) {//程序猿的监听者们，要同意别人监听才行
        list.add(ael);
    }

    ActionEvent e = new Code();

    //需要修改时，程序猿进行修改操作  
    public void updateCode() {
        if (((Code) e).isUpdate()) {//如果是真，则需要进行修改
            System.out.println("程序猿修改代码");
            //监听者们的做法  
            for (int i = 0; i < list.size(); ++i) {
                ActionEventListener a = list.get(i);
                a.actionPerformed(e);
            }
        }
    }
}  