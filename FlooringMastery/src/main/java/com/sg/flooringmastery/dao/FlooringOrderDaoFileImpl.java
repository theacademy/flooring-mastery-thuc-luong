package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FlooringOrderDaoFileImpl implements FlooringOrderDao {


    public static final String DELIMITER = ",";

    private Map<Integer, Order> orders = new HashMap<>();

    public FlooringOrderDaoFileImpl() {

    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public void addOrder(int orderNumber, Order order, LocalDate date) throws FlooringDaoPersistenceException {
        try {
            loadOrders(date);
        } catch (FlooringDaoPersistenceException e) {
            // Do nothing. File does not exist. A new file will be created.
        }
        orders.put(orderNumber, order);

        writeOrders(date);
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate date) throws FlooringDaoPersistenceException {
        loadOrders(date);
        orders.remove(orderNumber);
        writeOrders(date);

    }

    @Override
    public List<Order> getOrderByDate(LocalDate date) throws FlooringDaoPersistenceException {

        loadOrders(date);

        return new ArrayList<>(orders.values());
    }

    private Order unmarshallOrder(String orderAsText) {

        String[] orderTokens = orderAsText.split(DELIMITER);

        int orderNumber = Integer.parseInt(orderTokens[0]);

        Order orderFromFile = new Order(orderNumber);

        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));

        return orderFromFile;

    }

    private void loadOrders(LocalDate date) throws FlooringDaoPersistenceException {
        Scanner sc;
        DateTimeFormatter dayFormat
                = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFormat
                = DateTimeFormatter.ofPattern("MM");

        String orderFile = "Orders_" + date.format(monthFormat) + date.format(dayFormat) + date.getYear() + ".txt";

        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(orderFile)));
        } catch (FileNotFoundException e) {
            throw new FlooringDaoPersistenceException(
                "ERROR: Could not load order data into memory", e);
        }

        String currentLine;
        Order currentOrder;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        sc.close();

    }

    private String marshallOrder(Order order) {
        String orderAsText = order.getOrderNumber() + DELIMITER;

        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTax() + DELIMITER;
        orderAsText += order.getTotal() + DELIMITER;

        return orderAsText;
    }

    private void writeOrders(LocalDate date) throws FlooringDaoPersistenceException {
        PrintWriter out;
        DateTimeFormatter dayFormat
                = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFormat
                = DateTimeFormatter.ofPattern("MM");

        String orderFile = "Orders_" + date.format(monthFormat) + date.format(dayFormat) + date.getYear() + ".txt";


        try {
            out = new PrintWriter(new FileWriter(orderFile));
        } catch (IOException e) {
            throw new FlooringDaoPersistenceException(
                    "ERROR: Could not save order data.", e);
        }

        String orderAsText;
        List<Order> orderList = getOrders();

        for (Order currentOrder : orderList) {
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
            out.flush();
        }

        out.close();

    }


}
