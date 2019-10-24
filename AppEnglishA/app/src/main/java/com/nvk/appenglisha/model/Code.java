package com.nvk.appenglisha.model;

public class Code {
    private int id;
    private String code_name;
    private int status;

    public Code() {
    }

    public Code(int id, String code_name, int status) {
        this.id = id;
        this.code_name = code_name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode_name() {
        return code_name;
    }

    public void setCode_name(String code_name) {
        this.code_name = code_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
