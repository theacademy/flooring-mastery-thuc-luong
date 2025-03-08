package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringProductDaoStubImpl implements FlooringProductDao{

    public static final String DELIMITER = ",";
    public final String PRODUCT_FILE;

    private Map<String, Product> products = new HashMap<>();

    public FlooringProductDaoStubImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE = PRODUCT_FILE;
    }

    public FlooringProductDaoStubImpl() {
        this.PRODUCT_FILE = "Products.txt";
    }

    @Override
    public List<Product> getAllProducts() throws FlooringDaoPersistenceException {
        String productType = "Tile";
        BigDecimal costPerSquareFoot = new BigDecimal("3.50");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.15");

        Product testProduct = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);

        List<Product> productList = new ArrayList<>();

        productList.add(testProduct);

        return productList;
    }



}