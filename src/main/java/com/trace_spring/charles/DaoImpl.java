package com.trace_spring.charles;

import com.trace_spring.charles.dao.Dao;

public class DaoImpl implements Dao {

    public void select() {
        System.out.println("Enter DaoImpl.select()");
    }

    public void insert() {
        System.out.println("Enter DaoImpl.insert()");
    }
}
