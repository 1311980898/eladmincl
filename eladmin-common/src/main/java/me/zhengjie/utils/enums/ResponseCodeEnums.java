package me.zhengjie.utils.enums;

/**
 * 返回编码 封装
 * 以后app返回码请在这里添加
 * 返回状态枚举
 * create by wxj on 2021/06/23
 */
public enum ResponseCodeEnums {

    SUCCESS(200,"操作成功"),
    REQUIRED_FAILED(300,"必填项未填写"),
    FILED(310,"操作失败"),
    NO_RESOURCE(311,"当前没有要推送的内容!"),
    TUIBIAN_RESOURCE(312,"推必安审核推送未通过!"),

    SYSTEM_FAILED(500,"程序错误");



    private Integer code;
    private String msg;

    ResponseCodeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
