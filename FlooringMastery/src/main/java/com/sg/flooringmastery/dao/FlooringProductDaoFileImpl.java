package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringProductDaoFileImpl implements FlooringProductDao{

    public static final String DELIMITER = ",";
    public final String PRODUCT_FILE;

    private Map<String, Product> products = new HashMap<>();

    public FlooringProductDaoFileImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE = PRODUCT_FILE;
    }

    public FlooringProductDaoFileImpl() {
        this.PRODUCT_FILE = "Products.txt";
    }

    @Override
    public List<Product> getAllProducts() throws FlooringDaoPersistenceException {
        loadProducts();

        return new ArrayList<>(products.values());
    }

    private Product unmarshallProduct(String productAsText) {

        String[] productTokens = productAsText.split(DELIMITER);

        String productType = productTokens[0];

        Product productFromFile = new Product(productType);

        productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));

        return productFromFile;
    }

    private void loadProducts() throws FlooringDaoPersistenceException {
        Scanner sc;

        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringDaoPersistenceException(
                    "ERROR: Could not load product data into memory", e);
        }

        String currentLine;
        Product currentProduct;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        sc.close();
    }


}
