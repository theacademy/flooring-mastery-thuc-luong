package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringOrderDaoFileImplTest {

    FlooringOrderDao testOrderDao;

    @BeforeEach
    void setUp() throws IOException {
//        String testFile = "Orders_01012026.txt";
//        // Use the FileWriter to quickly blank the file
//        new FileWriter(testFile);
        testOrderDao = new FlooringOrderDaoFileImpl();

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testAddOrder() throws FlooringDaoPersistenceException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);

        List<Order> orderList = testOrderDao.getOrderByDate(date);

        Order testOrder = new Order(4);
        testOrder.setCustomerName("Bob");
        testOrder.setState("CA");
        testOrder.setTaxRate(new BigDecimal("25.00"));
        testOrder.setProductType("Tile");
        testOrder.setArea(new BigDecimal("100.00"));
        testOrder.setCostPerSquareFoot(new BigDecimal("25.00"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("25.00"));
        testOrder.setMaterialCost(new BigDecimal("25.00"));
        testOrder.setLaborCost(new BigDecimal("25.00"));
        testOrder.setTax(new BigDecimal("25.00"));
        testOrder.setTotal(new BigDecimal("25.00"));

        assertEquals(2, orderList.size());

        testOrderDao.addOrder(testOrder.getOrderNumber(), testOrder, date);

        orderList = testOrderDao.getOrderByDate(date);

        assertEquals(3, orderList.size());

    }

    @Test
    void testRemoveOrder() throws FlooringDaoPersistenceException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);

        List<Order> orderList = testOrderDao.getOrderByDate(date);

        Order testOrder = new Order(4);
        testOrder.setCustomerName("Bob");
        testOrder.setState("CA");
        testOrder.setTaxRate(new BigDecimal("25.00"));
        testOrder.setProductType("Tile");
        testOrder.setArea(new BigDecimal("100.00"));
        testOrder.setCostPerSquareFoot(new BigDecimal("25.00"));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal("25.00"));
        testOrder.setMaterialCost(new BigDecimal("25.00"));
        testOrder.setLaborCost(new BigDecimal("25.00"));
        testOrder.setTax(new BigDecimal("25.00"));
        testOrder.setTotal(new BigDecimal("25.00"));

        assertEquals(3, orderList.size());

        testOrderDao.removeOrder(testOrder.getOrderNumber(), date);

        orderList = testOrderDao.getOrderByDate(date);

        assertEquals(2, orderList.size());

    }

    @Test
    void testGetOrderByDate() throws FlooringDaoPersistenceException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);

        List<Order> orderList = testOrderDao.getOrderByDate(date);
        System.out.println(orderList.size());

        assertEquals(2, orderList.size());

    }
}