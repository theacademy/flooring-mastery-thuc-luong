package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringTaxDaoStubImpl implements FlooringTaxDao {

    public static final String DELIMITER = ",";
    public final String TAX_FILE;

    private Map<String, Tax> taxes = new HashMap<>();

    public FlooringTaxDaoStubImpl(String TAX_FILE) {
        this.TAX_FILE = TAX_FILE;
    }

    public FlooringTaxDaoStubImpl() {
        this.TAX_FILE = "testTaxes.txt";
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringDaoPersistenceException {

        String stateAbbreviation = "TX";
        String stateName = "Texas";
        BigDecimal taxRate = new BigDecimal("4.45");

        Tax testTax = new Tax(stateAbbreviation, stateName, taxRate);

        List<Tax> taxList = new ArrayList<>();

        taxList.add(testTax);

        return taxList;
    }

}
