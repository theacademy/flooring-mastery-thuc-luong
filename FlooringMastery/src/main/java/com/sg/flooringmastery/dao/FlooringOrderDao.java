package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface FlooringOrderDao {


    List<Order> getOrderByDate(LocalDate date) throws FlooringDaoPersistenceException;

    void addOrder(int orderNumber, Order order, LocalDate date) throws FlooringDaoPersistenceException;

    void removeOrder(int orderNumber, LocalDate date) throws FlooringDaoPersistenceException;

}
