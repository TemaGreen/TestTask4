package com.example.task4.component;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Counter {

    Queue<Integer> freedId;
    Integer firstId;

    public Integer getId() {
        if (!freedId.isEmpty()) {
            return freedId.peek();
        } else {
            return firstId++;
        }
    }

    public void addFreedId(Integer freedId) {
        this.freedId.offer(freedId);
    }

    public void reset() {
        freedId.clear();
        firstId = 0;
    }

    public abstract void getFreedId();

    public Counter(Queue<Integer> freedId, Integer firstId) {
        this.freedId = freedId;
        this.firstId = firstId;
    }

    public Counter(Queue<Integer> freedId) {
        this.freedId = freedId;
        this.firstId = 0;
    }

    public Counter() {
        freedId = new LinkedList<>();
        firstId = 0;
    }
}