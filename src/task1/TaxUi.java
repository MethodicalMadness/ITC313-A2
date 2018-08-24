package task1;

import java.util.Scanner;

/**
 * @author Michael Coleman
 */
public class TaxUi {

    private TaxController taxController;

    /**
     * No args constructor, creates taxController.
     */
    public TaxUi() {
        taxController = new TaxController();
    }

    /**
     * Method to display the menu options.
     */
    public void displayMenu(){
        System.out.println("|-----------* * * Tax Man v1.0 * * *----------|");
        System.out.println("| Please select one of the following options: |");
        System.out.println("|---------------------------------------------|");
        System.out.println("| Choose '1' to calculate tax                 |");
        System.out.println("| Choose '2' to search tax records            |");
        System.out.println("| Choose '3' to exit program                  |");
        System.out.println("|---------------------------------------------|");
    }

    /**
     * method that uses ints to select the menu options
     */
    public void userInteface() {
        displayMenu();
        int selection = menuSelection();
        switch (selection) {
            case 1:
                System.out.println("You have chosen '1' to calculate tax");
                selectionOne();
            case 2:
                System.out.println("You have chosen '2' to search tax records");
                selectionTwo();
            case 3:
                System.out.println("You have chosen '3' to exit the program");
                selectionThree();
            default:
                System.out.println("An error occurred in task1.TaxUi.userInterface() " +
                        "Exiting the program.");
                System.exit(1);
        }
    }

    /**
     * Method that determines if input is acceptable for the userInterface()
     * @return Integer that is acceptable for the userInterface() selection
     */
    private int menuSelection(){
        taxController.processTaxRatesTxt("");
        Scanner input = new Scanner(System.in);
        int selection = 0;
        while (true){
            if (input.hasNextInt()){
                selection = input.nextInt();
                if(selection < 1 || selection > 3){
                    System.out.println("Please try again.");
                } else {
                    break;
                }
            } else {
                System.out.println("Please try again.");
                input.next();
            }
        }
        return selection;
    }

    /**
     * Method contains logic required to guide user through the
     * calculation of tax for one employee at a time.
     */
    private void selectionOne(){
        String id;
        double income;
        double tax;
        Scanner input = new Scanner(System.in);
        id = taxController.checkId(); //need to fix leading zeros
        System.out.println("Please enter the employees annual income.");
        while (true) {
            if (input.hasNextDouble()) {
                income = input.nextDouble();
                if (income < 0) {
                    System.out.println("task1.Employee income must be a number greater than zero");
                } else {
                    break;
                }
            } else {
                System.out.println("task1.Employee income must be a number greater than zero");
                input.next();
            }
        }
        tax = taxController.determineTaxThreshold(income);
        Employee employee = new Employee(String.valueOf(id), income, tax);
        taxController.writeToTaxReport(employee);
        System.out.println("Would you like to calculate the tax for another employee? [Y/N]");
        while (true) {
            String answer = input.next();
            switch (answer){
                case "Y":
                    selectionOne();
                    break;
                case "y":
                    selectionOne();
                    break;
                case "N":
                    userInteface();
                    break;
                case "n":
                    userInteface();
                    break;
                default:
                    System.out.println("Please try again.");
            }
        }
    }

    /**
     * Method contains logic required to guide user through
     * searching the tax records.
     */
    private void selectionTwo(){
        String id = taxController.checkId();
        taxController.getEmployeeTaxRecord(id);
        System.out.println("Would you like to search the tax records for another employee? [Y/N]");
        Scanner input = new Scanner(System.in);
        while (true) {
            String answer = input.next();
            switch (answer){
                case "Y":
                    selectionTwo();
                    break;
                case "y":
                    selectionTwo();
                    break;
                case "N":
                    userInteface();
                    break;
                case "n":
                    userInteface();
                    break;
                default:
                    System.out.println("Please try again.");
            }
        }
    }

    /**
     * simple thank you and exits the program with exit code zero.
     */
    private void selectionThree(){
        System.out.println("Thank you for using 'Tax Man v1.0'");
        System.exit(0);
    }
}
