package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringDaoPersistenceException;
import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringTaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
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
        int orderNumber = 4;

        Order matchOrder = service.validateOrderNumber(date, orderNumber);

        assertEquals("Blake", matchOrder.getCustomerName());

    }

    @Test
    void testValidateState() throws FlooringDaoPersistenceException {

        String state = "TX";

        Tax matchTax = service.validateState(state);

        assertEquals("TX", matchTax.getStateAbbreviation());

    }

    @Test
    void testValidateProduct() throws FlooringDaoPersistenceException {
        String productType = "Tile";
        BigDecimal costPerSquareFoot = new BigDecimal("3.50");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.15");

        Product testProduct = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);

        Product matchProduct = service.validateProduct(productType);

        assertEquals("Tile", matchProduct.getProductType());

    }


    @Test
    void testGetTaxInfo() throws FlooringDaoPersistenceException {

        String stateAbbreviation = "TX";
        String stateName = "Texas";
        BigDecimal taxRate = new BigDecimal("4.45");

        Tax testTax = new Tax(stateAbbreviation, stateName, taxRate);

        Tax taxInfo = service.getTaxInfo(stateAbbreviation);

        assertEquals("TX", taxInfo.getStateAbbreviation());

    }

    @Test
    void testGetOrderByDate() throws FlooringDaoPersistenceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);

        Order testOrder = new Order(4);
        testOrder.setCustomerName("Blake");
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

        List<Order> orderList = service.getOrderByDate(date);

        assertEquals("Blake", orderList.get(0).getCustomerName());

    }

    @Test
    void createOrder() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse("01/01/2026", formatter);

        String stateAbbreviation = "TX";
        String stateName = "Texas";
        BigDecimal taxRate = new BigDecimal("4.45");

        Tax testTax = new Tax(stateAbbreviation, stateName, taxRate);

        String productType = "Tile";
        BigDecimal costPerSquareFoot = new BigDecimal("3.50");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.15");

        Product testProduct = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);

        String customerName = "Blake";
        BigDecimal area = new BigDecimal("100.00");

        Order createdOrder = service.createOrder(date, customerName, testTax, testProduct, area);

        assertEquals("Blake", createdOrder.getCustomerName());

    }


}