package designpattern.jiantingze;

class PM implements ActionEventListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("修改完代码后，项目经理说，那里还不行，缺个分好；");
    }

} 