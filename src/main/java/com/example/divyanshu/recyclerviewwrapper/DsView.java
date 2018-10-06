package com.example.divyanshu.recyclerviewwrapper;

public class DsView {

    String view;
    int id;
    String fieldName;


    public DsView(String view, int id, String fieldName) {
        this.view = view;
        this.id = id;
        this.fieldName = fieldName;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
