package designpattern.jiantingze;

/**
 * @description 编程事件
 */
class Code extends ActionEvent {
    //这个事件的属性是敲打代码、修改代码、处理时间等等...  
    private boolean beat = true;//默认敲代码
    private boolean update = true;//默认修改代码
    private long time;

    public boolean isBeat() {
        return beat;
    }

    public void setBeat(boolean beat) {
        this.beat = beat;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}  