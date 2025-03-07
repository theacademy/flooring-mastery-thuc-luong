package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringView {

    private UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Exit");

        String inputString = io.readString("Please select from the above choices.");
        return Integer.parseInt(inputString);
    }

    public void displayOrdersBanner() {
        io.print("=== Display Orders ===");
    }

    public void addOrdersBanner() {
        io.print("=== Add Order ===");
    }

    public void editOrdersBanner() {
        io.print("=== Edit Order ===");
    }

    public void removeOrdersBanner() {
        io.print("=== Remove Order ===");
    }

    public void displayOrder(Order order) {
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Cost per Square Foot: " + order.getCostPerSquareFoot());
        io.print("Labor Cost per Square Foot: " + order.getLaborCostPerSquareFoot());
        io.print("Material Cost: " + order.getMaterialCost());
        io.print("Labor Cost: " + order.getLaborCost());
        io.print("Tax: " + order.getTax());
        io.print("Total: " + order.getTotal());
        io.print("");
    }

    public void displayOrderList(List<Order> orderList) {

        io.print("=== ORDERS ===");

        for (Order currentOrder : orderList) {
            displayOrder(currentOrder);

        }

    }

    public LocalDate getUserDate() {


        String date = io.readString("Enter a date (mm/dd/yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse(date, formatter);

        return ld;
    }

    public int getOrderNumber() {
        String orderNumber = io.readString("Enter order number: ");
        return Integer.parseInt(orderNumber);
    }

    public String getName() {

        return io.readString("Enter customer name: ");
    }

    public String getProduct() {

        return io.readString("Enter product type: ");
    }

    public String getState() {

        return io.readString("Enter customer state: ");
    }

    public String getArea() {

        return io.readString("Enter required area: ");

    }

    public String getConfirmation() {
        return io.readString("Would you like to proceed? [Y/N]");
    }

    public void print(String msg) {
        io.print(msg);
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }


    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }


}
