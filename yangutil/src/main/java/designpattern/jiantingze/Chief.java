package designpattern.jiantingze;

class Chief implements ActionEventListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("修改完代码后，总经理说，再做几个方案！给我选");
    }
}