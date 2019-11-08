package com.lig.common.mapper;


import java.util.List;
public class ResponsePageData<T> {
    private Integer code = 0;
    private String msg = "success";
    private long count;
    private List<T> data ;

    public ResponsePageData(long count,List<T> data){
        this.count = count;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
