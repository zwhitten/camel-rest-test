package com.testing.model;

public class DataPOJO {
    private String data;
    private String value;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public DataPOJO setCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getValue() {
        return value;
    }

    public DataPOJO setValue(String value) {
        this.value = value;
        return this;
    }

    public String getData() {
        return data;
    }

    public DataPOJO setData(String data) {
        this.data = data;
        return this;
    }
}
