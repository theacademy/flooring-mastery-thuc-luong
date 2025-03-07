package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;

import java.util.List;

public interface FlooringTaxDao {

    List<Tax> getAllTaxes() throws FlooringDaoPersistenceException;

}
