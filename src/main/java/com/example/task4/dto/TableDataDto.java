package com.example.task4.dto;

import com.example.task4.entity.TableData;

import java.sql.Date;

public class TableDataDto {
    private Date date;
    private String name;
    private long value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public TableDataDto(TableData tableData) {
        this.date = tableData.getDate();
        this.name = tableData.getName();
        this.value = tableData.getValue();
    }

    public TableDataDto(Date date, String name, long value) {
        this.date = date;
        this.name = name;
        this.value = value;
    }

    public TableDataDto() {
    }
}
