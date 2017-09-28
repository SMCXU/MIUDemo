package com.example.customrecycle.frame;

/**
 * @author GISirFive
 * @time 2016/8/17
 */
public class EventCustom {

    //需要传递的内容
    private Object obj;
    //传递的tag 对应KeyEvent中的值
    private String tag;

    public EventCustom() {

    }

    public EventCustom(String tag) {
        this.tag = tag;
    }

    public EventCustom(Object obj, String tag) {
        this.obj = obj;
        this.tag = tag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
