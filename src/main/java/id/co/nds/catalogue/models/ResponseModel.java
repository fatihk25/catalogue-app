package id.co.nds.catalogue.models;

public class ResponseModel {
    private String msg;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object object) {
        this.data = object;
    }
}
