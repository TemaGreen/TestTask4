package com.example.task4.entity;

import com.example.task4.component.Counter;
import com.example.task4.component.TableDataCounter;
import com.example.task4.dto.TableDataDto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class TableData {

    private int id;
    private Date date;
    private String name;
    private long value;

    public static Counter counter;
    static {
        if(counter == null) {
            counter = new TableDataCounter();
            counter.getFreedId();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public TableData(int id, Date date, String name, long value) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.value = value;

    }

    public TableData(int id, TableDataDto tableDataDto) {
        this.id = id;
        this.date = tableDataDto.getDate();
        this.name = tableDataDto.getName();
        this.value = tableDataDto.getValue();
    }

    public TableData(TableDataDto tableDataDto) {
        this.id = counter.getId();
        this.date = tableDataDto.getDate();
        this.name = tableDataDto.getName();
        this.value = tableDataDto.getValue();
    }

    public TableData(Date date, String name, long value) {
        this.id = counter.getId();
        this.date = date;
        this.name = name;
        this.value = value;
    }

    public TableData(String date, String name, long value) {
        try {
            id = counter.getId();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            this.date = new Date(f.parse(date).getTime());
            this.name = name;
            this.value = value;
        } catch (ParseException e) {
            System.out.println(e);
        }
    }

    public TableData() {
    }
}
