package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;

import java.util.List;

public interface FlooringProductDao {

    List<Product> getAllProducts() throws FlooringDaoPersistenceException;



}
