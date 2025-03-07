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
        loadTaxes();

        return new ArrayList<>(taxes.values());
    }



    private Tax unmarshallTax(String taxAsText) {

        String[] taxTokens = taxAsText.split(DELIMITER);

        String stateAbbreviation = taxTokens[0];

        Tax taxFromFile = new Tax(stateAbbreviation);

        taxFromFile.setState(taxTokens[1]);
        taxFromFile.setTaxRate(new BigDecimal(taxTokens[2]));

        return taxFromFile;
    }



    private void loadTaxes() throws FlooringDaoPersistenceException {
        Scanner sc;

        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringDaoPersistenceException(
                    "ERROR: Could not load tax data into memory", e);
        }

        String currentLine;
        Tax currentTax;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getStateAbbreviation(), currentTax);
        }
        sc.close();


    }
}
