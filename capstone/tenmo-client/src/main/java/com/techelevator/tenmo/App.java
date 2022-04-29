package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TenmoService tenmoService = new TenmoService();

    private AuthenticatedUser currentUser;


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        tenmoService.setAuthToken(currentUser.getToken());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }

    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {

        Account acUser = tenmoService.getAccount(currentUser.getUser().getUsername());
        System.out.println("Your current account balance is: $" + acUser.getBalance());

    }

    private void viewTransferHistory() {

        List<Transfer> transfers = tenmoService.listOfTransfers(2);

        System.out.println("-----------------------------------------\n\t\t\tTransfers\n\nID\t\t\tFrom/To\t\t\tAmount" +
                "\n\t\t\t Type\n-----------------------------------------");

        for (Transfer i : transfers) {
            String toFrom = "";
            if (!i.getUsernameFrom().equals(currentUser.getUser().getUsername())) {
                toFrom = "From: " + i.getUsernameFrom();
            } else {
                toFrom = "To: " + i.getUsernameTo();
            }
<<<<<<< HEAD
            System.out.println(i.getTransfer_id() + "\t\t" + toFrom + "\t\t" + "$" + i.getAmount() + "");
=======
            System.out.println(i.getTransfer_id() + "\t\t" + toFrom + "\t\t" + "$" + i.getAmount() + "\t\t" + i.getTransfer_status_desc());
>>>>>>> badbecf37c917aa3aa6c6d151fe681200774fc54

        }
        int transferId = -1;
        while (transferId != 0) {
            transferId = consoleService.promptForInt("\nPlease enter transfer ID to view details (0 to cancel):\n");
            for (Transfer i : transfers) {
                if (transferId == i.getTransfer_id()) {
                    System.out.println("-----------------------------------------\n\t\t\tTransfer Details\t\t\t" +
                            "\n-----------------------------------------");
                    System.out.println("Id: " + i.getTransfer_id());
                    System.out.println("From: " + i.getUsernameFrom());
                    System.out.println("To: " + i.getUsernameTo());
                    System.out.println("Type: " + i.getTransfer_type_desc());
                    System.out.println("Status: " + i.getTransfer_status_desc());
                    System.out.println("Amount: $" + i.getAmount());
                    System.out.println("");
                    transferId = 0;
                }
            }
        }

    }

    private void viewPendingRequests() {

        List<Transfer> transfers = tenmoService.listOfTransfers(1);
        String formatStr = "%-10s %-24s %-10s";
        System.out.println("-------------------------------------------");
        System.out.println("Pending Transfers");
        System.out.println(String.format(formatStr, "ID", "From", "Amount"));
        System.out.println("-------------------------------------------");

        for (Transfer i : transfers) {
            String toFrom = "";
//            if(!i.getUsernameFrom().equals(currentUser.getUser().getUsername())) {
//                toFrom = i.getUsernameFrom();
//            } else {
            toFrom = i.getUsernameTo();
//            }
            System.out.println(String.format(formatStr,
                    i.getTransfer_id(), toFrom, "$ " + i.getAmount()));
        }
        System.out.println("---------");
        int pendingId = consoleService.promptForInt("\nPlease enter transfer ID to approve/reject (0 to cancel):\n");
        //
        Account acUser = tenmoService.getAccount(currentUser.getUser().getUsername());
        for (Transfer i : transfers) {
            if (pendingId == i.getTransfer_id()) {
                int option = consoleService.promptForInt("1: Approve\n2: Reject\n0: Don't approve or reject\n----------\nPlease choose an option: ");
                if (option == 1 && i.getAmount().compareTo(acUser.getBalance()) <= 0) {
                    System.out.println(i);
                    tenmoService.approveBucks(i);
                    System.out.println("Transfer approved.");
                } else if (option == 1 && i.getAmount().compareTo(acUser.getBalance()) >= 0) {
                    System.out.println("You don't have enough money to approve this transfer.");
                } else if (option == 2) {

                    //remove the transfer
                } else if (option == 0) {

                    tenmoService.rejectTransfer(i);
                    System.out.println("Transfer Rejected");
                } else if (option == 0){

                    //Don't do anything
                } else {
                    System.out.println("Invalid input.");
                }
            }

        }
        //
    }

    private void sendBucks() {

        Account acUser = tenmoService.getAccount(currentUser.getUser().getUsername());
        List<User> listUsers = tenmoService.userList();
        Set<Long> values = new HashSet<>();
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID\t\t\t\tName");
        System.out.println("-------------------------------------------");
        for (User user : listUsers) {
            System.out.println(user.getId() + "\t\t\t" + user.getUsername());
            values.add(user.getId());
        }
        System.out.println("---------");

        int sendTo = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");


            while (Objects.equals((long) sendTo, currentUser.getUser().getId()) || (!values.contains((long) sendTo) && sendTo != 0)) {
                System.out.println("Invalid recipient.");
                sendTo = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");
            }

            BigDecimal amountToSend = consoleService.promptForBigDecimal("Enter amount:");
            if (amountToSend.compareTo(BigDecimal.ZERO) != 0) {


                while (amountToSend.compareTo(acUser.getBalance()) >= 0 || amountToSend.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Invalid transfer amount.");
                    amountToSend = consoleService.promptForBigDecimal("Enter amount:");
                }


                Transfer transfer = new Transfer(currentUser.getUser().getId().intValue(), sendTo, amountToSend);
                tenmoService.transferBucks(transfer);
            }

    }


    private void requestBucks() {

        Account acUser = tenmoService.getAccount(currentUser.getUser().getUsername());
        List<User> listUsers = tenmoService.userList();
        Set<Long> values = new HashSet<>();
        for (User user : listUsers) {
            System.out.println(user.getId() + " : " + user.getUsername());
            values.add(user.getId());
        }
            int requestFrom = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel):");
            while (Objects.equals((long) requestFrom, currentUser.getUser().getId()) || !values.contains((long) requestFrom)) {
                System.out.println("Invalid recipient.");
                requestFrom = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel):");
            }
            BigDecimal amountToRequest = consoleService.promptForBigDecimal("Enter amount:");
            while (amountToRequest.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid transfer amount.");
                amountToRequest = consoleService.promptForBigDecimal("Enter amount:");
            }
            Transfer makeTransfer = new Transfer(requestFrom, currentUser.getUser().getId().intValue(), amountToRequest);
            tenmoService.requestBucks(makeTransfer);


        }

    }

