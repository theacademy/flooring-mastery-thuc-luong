package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringDaoPersistenceException;
import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringTaxDao;
import com.sg.flooringmastery.dto.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringServiceLayerImplTest {

    FlooringServiceLayer service;

    public FlooringServiceLayerImplTest () {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringServiceLayer.class);
    }

    @BeforeEach
    void setUp() {



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);

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
    }

    @AfterEach
    void tearDown() {
    }



    @Test
    void validateOrderNumber() throws FlooringDaoPersistenceException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);
        int orderNumber = 1;

        Order matchOrder = service.validateOrderNumber(date, orderNumber);

        assertEquals("Blake", matchOrder.getCustomerName());

    }

    @Test
    void getTaxInfo() {
    }

    @Test
    void getOrderByDate() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void addOrder() {
    }

    @Test
    void removeOrder() {
    }

    @Test
    void testCalculateMaterialCost() {
    }

    @Test
    void testCalculateLaborCost() {
    }

    @Test
    void testCalculateTax() {
    }

    @Test
    void testCalculateTotal() {



    }
}