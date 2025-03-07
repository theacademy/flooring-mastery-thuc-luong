package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dto.Tax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringTaxDaoFileImplTest {

    FlooringTaxDao testTaxDao;

    @BeforeEach
    void setUp() throws IOException {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        testTaxDao =
                ctx.getBean("flooringTaxDao", FlooringTaxDao.class);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testGetAllTaxes() throws FlooringDaoPersistenceException {

        List<Tax> allTaxes = testTaxDao.getAllTaxes();

        assertEquals(4, allTaxes.size());

    }
}