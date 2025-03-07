package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringDaoPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringServiceLayer {
    public void validateArea(BigDecimal area);

    public void validateDate(LocalDate date);

    public void validateName(String name);

    public Order validateOrderNumber(LocalDate date, int orderNumber) throws FlooringDaoPersistenceException;

    public Product validateProduct(String product) throws FlooringDaoPersistenceException;

    public Tax validateState(String state) throws FlooringDaoPersistenceException;

    public Tax getTaxInfo(String state) throws FlooringDaoPersistenceException;

    public List<Order> getOrderByDate(LocalDate date) throws FlooringDaoPersistenceException;

    public Order createOrder(LocalDate date, String name, Tax taxInfo, Product productInfo, BigDecimal area);

    public void addOrder(Order order, LocalDate date) throws FlooringDaoPersistenceException;

    public void removeOrder(int orderNumber, LocalDate date) throws FlooringDaoPersistenceException;

}
