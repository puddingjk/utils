package org.puddingjk.common;

/**
 * @ClassName : ObjectRestResponse
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 14:02
 */
public class ObjectRestResponse <T> extends BaseResponse {

    T data;
    boolean rel=true;

    public ObjectRestResponse() {
    }

    public static <E> ObjectRestResponse<E> ok(E o) {
        return new ObjectRestResponse<>(200,"操作成功", o,true);
    }

    public static <E> ObjectRestResponse<E> error() {
        return new ObjectRestResponse<>(500,"操作失败",null,false);
    }
    public static <E> ObjectRestResponse<E> validateError(E o) {
        return new ObjectRestResponse<>(200,"验证失败",o,true);
    }


    public ObjectRestResponse(int status, String message, T data, boolean rel) {
        super(status, message);
        this.data = data;
        this.rel = rel;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }


    public ObjectRestResponse rel(boolean rel) {
        this.setRel(rel);
        return this;
    }


    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}