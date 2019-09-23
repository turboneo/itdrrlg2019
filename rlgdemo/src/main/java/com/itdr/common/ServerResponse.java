package com.itdr.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private  Integer status;
    private  T data;
    private  String mag;

    //获取成功的对象,包括成功状态码和数据
    private ServerResponse(T datas){
        this.status=200;
        this.data=datas;
    }

    //获取成功的对象,包括成功状态码和数据(自定义状态码)
    private ServerResponse(Integer s,T datas){
        this.status=s;
        this.data=datas;
    }

    //获取成功的对象,包括成功状态码和数据以及信息
    private ServerResponse(Integer s,T datas,String m){
        this.status=s;
        this.data=datas;
        this.mag=m;
    }

    //获取失败的对象,包括失败状态码和信息
    private ServerResponse(Integer s,String m){
        this.status=s;
        this.mag=m;
    }

    //获取失败的对象,包括失败信息
    private ServerResponse(String m){
        this.mag=m;
    }

    //成功的时候传入状态码和数据
    public static <T> ServerResponse successRS (Integer status, T data){
        return new ServerResponse(Const.SUCESS);
    }
    public static <T> ServerResponse successRS (String msg){
        return new ServerResponse(Const.SUCESS,msg);
    }

    //成功的时候传入数据
    public static <T> ServerResponse successRS (T data){
        return new ServerResponse(Const.SUCESS,data);
    }

    //成功的时候传入状态码和数据以及信息
    public static <T> ServerResponse successRS (T data,String msg){
        return new ServerResponse(Const.SUCESS,data,msg);
    }


    //失败的时候传入状态码和信息
    public static <T> ServerResponse defeatedRS (Integer errorCode, String errormsg){
        return new ServerResponse(errorCode,errormsg);
    }

    //失败的时候传入信息
    public static <T> ServerResponse defeatedRS (String errormsg){
        return new ServerResponse(Const.ERROR,errormsg);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }
    @JsonIgnore
    public boolean isSuccess() {
        return this.status==Const.SUCESS;
    }
}
