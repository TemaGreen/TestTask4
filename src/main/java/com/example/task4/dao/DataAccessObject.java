package com.example.task4.dao;

import java.util.List;

public interface DataAccessObject<T> {
    void insert(T value);

    void insertAll(List<T> value);

    void delete(Integer id);

    void deleteAll();

    void update(Integer id, T value);

    T findId(int id);

    List<T> finaAll();

    List<T> init();
}
