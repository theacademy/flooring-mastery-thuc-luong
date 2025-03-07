package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringDaoPersistenceException;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringTaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FlooringServiceLayerImpl implements FlooringServiceLayer{

    private FlooringOrderDao orderDao;
    private FlooringProductDao productDao;
    private FlooringTaxDao taxDao;

    public FlooringServiceLayerImpl(FlooringOrderDao orderDao,
                                    FlooringProductDao productDao,
                                    FlooringTaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public void validateDate(LocalDate date) throws FlooringDataValidationException {
        if (date.isBefore(LocalDate.now())) {
            throw new FlooringDataValidationException("ERROR: Date must be in the future!");
        }
    }


    public void validateName(String name) throws FlooringDataValidationException {
        if (name == null || name.trim().length() == 0) {
            throw new FlooringDataValidationException("ERROR: Customer name cannot be empty!");
        }

        if (name.matches("[a-zA-Z0-9.,]+")) {
            return;
        } else {
            throw new FlooringDataValidationException("ERROR: Customer name must [a-z][0-9][A-Z][,.] characters! ");
        }
    }

    @Override
    public Order validateOrderNumber(LocalDate date, int orderNumber) throws FlooringDaoPersistenceException {
        List<Order> orderList = orderDao.getOrderByDate(date);

        List<Order> matchOrderNumber = orderList.stream().filter((p) -> p.getOrderNumber() == orderNumber).collect(Collectors.toList());

        if (matchOrderNumber.isEmpty()) {
            throw new FlooringDataValidationException("ERROR: Order number does not exist!");

        }

        return  matchOrderNumber.get(0);

    }

    @Override
    public Tax validateState(String state) throws FlooringDaoPersistenceException {
        List<Tax> taxList = taxDao.getAllTaxes();

        List<Tax> matchState = taxList.stream().filter((p) -> p.getState().equalsIgnoreCase(state)).collect(Collectors.toList());
        List<Tax> matchStateAbbreviation = taxList.stream().filter((p) -> p.getStateAbbreviation().equalsIgnoreCase(state)).collect(Collectors.toList());


        if (!matchStateAbbreviation.isEmpty()) {
            return matchStateAbbreviation.get(0);
        }
        else if (!matchState.isEmpty()) {
            return matchState.get(0);
        }
        else {
            throw new FlooringDataValidationException("ERROR: State is not in tax list!");
        }

    }

    @Override
    public Product validateProduct(String product) throws FlooringDaoPersistenceException {
        List<Product> productList = productDao.getAllProducts();

        List<Product> matchProduct = productList.stream().filter((p) -> p.getProductType().equalsIgnoreCase(product)).collect(Collectors.toList());

        if (!matchProduct.isEmpty()) {
            return matchProduct.get(0);
        }
        else {
            throw new FlooringDataValidationException("ERROR: Product type is not sold!");
        }
    }

    @Override
    public void validateArea(BigDecimal area) {
        if (area.compareTo(new BigDecimal("100")) < 0) {
            throw new FlooringDataValidationException("ERROR: Area is not large enough!");
        }
    }

    @Override
    public Tax getTaxInfo(String state) throws FlooringDaoPersistenceException {
        Tax taxInfo = validateState(state);

        return taxInfo;
    }

    @Override
    public List<Order> getOrderByDate(LocalDate date) throws FlooringDaoPersistenceException {
        validateDate(date);
        return orderDao.getOrderByDate(date);
    }


    @Override
    public Order createOrder(LocalDate date, String name, Tax taxInfo, Product productInfo, BigDecimal area) {
        Order newOrder = new Order(Order.incrementCounter());

        newOrder.setCustomerName(name);
        newOrder.setState(taxInfo.getStateAbbreviation());
        newOrder.setTaxRate(taxInfo.getTaxRate());
        newOrder.setProductType(productInfo.getProductType());
        newOrder.setArea(area);
        newOrder.setCostPerSquareFoot(productInfo.getCostPerSquareFoot());
        newOrder.setLaborCostPerSquareFoot(productInfo.getLaborCostPerSquareFoot());

        BigDecimal materialCost = calculateMaterialCost(area, productInfo.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = calculateLaborCost(area, productInfo.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxCost = calculateTax(materialCost, laborCost, taxInfo.getTaxRate()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalCost = calculateTotal(materialCost, laborCost, taxCost).setScale(2, RoundingMode.HALF_UP);

        newOrder.setMaterialCost(materialCost);
        newOrder.setLaborCost(laborCost);
        newOrder.setTax(taxCost);
        newOrder.setTotal(totalCost);

        return newOrder;

    }

    @Override
    public void addOrder(Order order, LocalDate date) throws FlooringDaoPersistenceException {
        orderDao.addOrder(order.getOrderNumber(), order, date);
    }


    @Override
    public void removeOrder(int orderNumber, LocalDate date) throws FlooringDaoPersistenceException {
        orderDao.removeOrder(orderNumber, date);
    }

    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        return area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) {
        return area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        return materialCost.add(laborCost).multiply(taxRate.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
    }


    public BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) {
        return materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
    }
}

