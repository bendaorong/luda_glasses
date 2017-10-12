package com.luda.comm.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/3.
 */
public class ResultHandle<T extends Serializable> implements Serializable{
    private static final long serialVersionUID = 2774242035842352310L;

    /**
     * 实际返回对象
     */
    private T returnContent;

    /**
     *
     *  接口调用成功／失败
     *
     */
    private boolean success=true;
    /**
     *
     * 失败描述
     *
     */
    private String msg;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean isFail() {
        return !success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.success=false;
        this.msg = msg;
    }
    public void setMsg(Exception e) {
        this.success=false;
        this.msg = e.toString();
    }

    public T getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(T returnContent) {
        this.returnContent = returnContent;
    }
}
