package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringProductDaoFileImplTest {

    FlooringProductDao testProductDao;

    @BeforeEach
    void setUp() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        testProductDao =
                ctx.getBean("flooringProductDao", FlooringProductDao.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetAllProducts() throws FlooringDaoPersistenceException {

        List<Product> allProducts = testProductDao.getAllProducts();

        assertEquals(4, allProducts.size());

    }
}