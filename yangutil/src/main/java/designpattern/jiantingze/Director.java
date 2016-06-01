package designpattern.jiantingze;

class Director implements ActionEventListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("修改完代码后，运营总监说，你没做出我想要的感觉");
    }
}  