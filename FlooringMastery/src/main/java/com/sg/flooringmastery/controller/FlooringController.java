package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringDaoPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringDataValidationException;

import com.sg.flooringmastery.service.FlooringServiceLayer;
import com.sg.flooringmastery.ui.FlooringView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;

public class FlooringController {

    private FlooringView view;
    private FlooringServiceLayer service;

    public FlooringController(FlooringView view, FlooringServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {

            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }

        } catch (FlooringDaoPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

        exitMessage();

    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() throws FlooringDaoPersistenceException {
        view.displayOrdersBanner();

        boolean hasErrors = false;

        LocalDate date = null;
        do {
            try {
                date = view.getUserDate();

                List<Order> orderList = service.getOrderByDate(date);
                view.displayOrderList(orderList);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            } catch (DateTimeParseException e) {
                view.displayErrorMessage("ERROR: Invalid date format (mm/dd/yyyy).");
                hasErrors = true;

            }


        } while (hasErrors);


    }

    private void addOrder() throws FlooringDaoPersistenceException {
        view.addOrdersBanner();
        boolean hasErrors = false;

        LocalDate date = null;


        do {
            try {
                date = view.getUserDate();

                service.validateDate(date);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            } catch (DateTimeParseException e) {
                view.displayErrorMessage("ERROR: Invalid date format (mm/dd/yyyy).");
                hasErrors = true;
            }


        } while (hasErrors);



        String state = null;
        Tax taxInfo = null;

        do {
            try {
                state = view.getState();

                taxInfo = service.getTaxInfo(state);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            }

        } while (hasErrors);


        String name = null;

        do {
            try {
                name = view.getName();
                service.validateName(name);
                hasErrors = false;
            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;
            }
        } while (hasErrors);

        String product = null;
        Product productInfo = null;

        do {
            try {
                product = view.getProduct();
                productInfo = service.validateProduct(product);
                hasErrors = false;
            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;
            }

        } while (hasErrors);

        BigDecimal area = null;

        do {
            try {
                String stringArea = view.getArea();
                area = new BigDecimal(stringArea);
                area = area.setScale(2, RoundingMode.HALF_UP);
                service.validateArea(area);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;
            }

        } while (hasErrors);

        Order newOrder = service.createOrder(date, name, taxInfo, productInfo, area);

        do {
            view.displayOrder(newOrder);
            String userChoice = view.getConfirmation();
            if (userChoice.equalsIgnoreCase("Y")) {
                service.addOrder(newOrder, date);
                view.print("New order created!");
                hasErrors = false;
            }
            else if (userChoice.equalsIgnoreCase("N")) {
                view.print("Cancelling to menu.");
                hasErrors = false;
            }
            else {
                view.displayErrorMessage("Invalid Choice!");
                hasErrors = true;
            }
        } while (hasErrors);
    }

    private void editOrder() throws FlooringDaoPersistenceException {
        view.editOrdersBanner();

        boolean hasErrors = false;

        LocalDate date = null;

        do {
            try {
                date = view.getUserDate();

                service.getOrderByDate(date);

                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;
            } catch (DateTimeParseException e) {
                view.displayErrorMessage("ERROR: Invalid date format (mm/dd/yyyy).");
                hasErrors = true;
            }

        } while (hasErrors);

        int orderNumber = 0;
        Order oldOrder = null;

        do {
            try {
                orderNumber = view.getOrderNumber();
                oldOrder = service.validateOrderNumber(date, orderNumber);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            } catch (NumberFormatException e) {
                view.displayErrorMessage("ERROR: Input must be an integer!");
                hasErrors = true;

            }

        } while (hasErrors);

        String newName = null;
        String newState = null;
        String newProduct = null;
        BigDecimal newArea = null;

        Tax taxInfo = null;

        do {
            try {
                newState = view.getState();

                if (newState.isEmpty()) {
                    taxInfo = service.getTaxInfo(oldOrder.getState());

                }
                else {
                    taxInfo = service.getTaxInfo(newState);
                    hasErrors = false;

                }
            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            }
        } while (hasErrors);

        do {
            try {
                newName = view.getName();

                if (newName.isEmpty()) {
                    newName = oldOrder.getCustomerName();

                }
                else {
                    service.validateName(newName);
                    hasErrors = false;

                }
            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            }
        } while (hasErrors);

        Product productInfo = null;

        do {
            try {
                newProduct = view.getProduct();

                if (newProduct.isEmpty()) {
                    productInfo = service.validateProduct(oldOrder.getProductType());

                }
                else {
                    productInfo = service.validateProduct(newProduct);
                    hasErrors = false;

                }
            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            }
        } while (hasErrors);

        do {
            try {
                String stringNewArea = view.getArea();

                if (stringNewArea.isEmpty()) {
                    newArea = oldOrder.getArea();
                }
                else {

                    newArea = new BigDecimal(stringNewArea);
                    newArea = newArea.setScale(2, RoundingMode.HALF_UP);
                    service.validateArea(newArea);
                    hasErrors = false;

                }

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            }

        } while (hasErrors);


        Order newOrder = service.createOrder(date, newName, taxInfo, productInfo, newArea);
        newOrder.setOrderNumber(oldOrder.getOrderNumber());

        do {

            view.displayOrder(newOrder);
            String userChoice = view.getConfirmation();
            if (userChoice.equalsIgnoreCase("Y")) {

                service.removeOrder(orderNumber, date);
                service.addOrder(newOrder, date);
                view.print("Order edited!");
                hasErrors = false;
            }
            else if (userChoice.equalsIgnoreCase("N")) {
                view.print("Cancelling to menu.");
                hasErrors = false;
            }
            else {
                view.displayErrorMessage("Invalid Choice!");
                hasErrors = true;
            }
        } while (hasErrors);
    }

    private void removeOrder() throws FlooringDaoPersistenceException {
        view.removeOrdersBanner();

        boolean hasErrors = false;
        LocalDate date = null;

        do {
            try {
                date = view.getUserDate();
                service.getOrderByDate(date);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            } catch (DateTimeParseException e) {
                view.displayErrorMessage("ERROR: Invalid date format (mm/dd/yyyy).");
                hasErrors = true;

            }

        } while (hasErrors);


        int orderNumber = 0;
        Order oldOrder = null;

        do {
            try {
                orderNumber = view.getOrderNumber();
                oldOrder = service.validateOrderNumber(date, orderNumber);
                hasErrors = false;

            } catch (FlooringDataValidationException e) {
                view.displayErrorMessage(e.getMessage());
                hasErrors = true;

            } catch (NumberFormatException e) {
                view.displayErrorMessage("ERROR: Input must be an integer!");
                hasErrors = true;

            }

        } while (hasErrors);

        do {

            view.displayOrder(oldOrder);
            String userChoice = view.getConfirmation();
            if (userChoice.equalsIgnoreCase("Y")) {

                service.removeOrder(orderNumber, date);
                view.print("Order removed!");
                hasErrors = false;
            }
            else if (userChoice.equalsIgnoreCase("N")) {
                view.print("Cancelling to menu.");
                hasErrors = false;
            }
            else {
                view.displayErrorMessage("Invalid Choice!");
                hasErrors = true;
            }
        } while (hasErrors);

    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
