import java.util.Scanner;

public class TaxUi {

    private TaxController taxController;

    public TaxUi() {
        taxController = new TaxController();
    }

    public TaxUi(TaxController taxController) {
        this.taxController = taxController;
    }

    private void displayMenu(){
        System.out.println("Please select one of the following options:");
        System.out.println("Choose '1' to calculate tax");
        System.out.println("Choose '2' to search tax records");
        System.out.println("Choose '3' to exit program");
    }

    public void userInteface() {
        displayMenu();
        int selection = menuSelection();
        switch (selection) {
            case 0:
                System.out.println("An error successfully occurred, please restart the program");
            case 1:
                System.out.println("You have chosen '1' to calculate tax");
                selectionOne();
            case 2:
                System.out.println("You have chosen '2' to search tax records");
            case 3:
                System.out.println("You have chosen '3' to exit the program");
            default:
                System.out.println("Wow, this is embarrassing. Something has gone wrong, please restart the program.");
        }
    }

    private int menuSelection(){
        taxController.processTaxRatesTxt("");
        Scanner input = new Scanner(System.in);
        int selection = 0;
        while (true){
            if (input.hasNextInt()){
                selection = input.nextInt();
                if(selection < 1 || selection > 3){
                    System.out.println("Unexpected input. Please try again.");
                } else {
                    break;
                }
            } else {
                System.out.println("Unexpected input. Please try again.");
                input.next();
            }
        }
        return selection;
    }

    private void selectionOne(){
        Scanner input = new Scanner(System.in);
        int id;
        double income;
        double tax;
        System.out.println("Please enter the 4 digits employees id.");
        while (true){
            if (input.hasNextInt()){
                id = input.nextInt();
                if(String.valueOf(id).length() != 4){
                    System.out.println("Employee Id must be 4 digits");
                } else {
                    break;
                }
            } else {
                System.out.println("Employee Id must be 4 digits");
                input.next();
            }
        }
        System.out.println("Please enter the employees annual income.");
        while (true) {
            if (input.hasNextDouble()) {
                income = input.nextDouble();
                if (income < 0) {
                    System.out.println("Employee income must be a number greater than zero");
                } else {
                    break;
                }
            } else {
                System.out.println("Employee income must be a number greater than zero");
                input.next();
            }
        }
        tax = taxController.determineTax(income);
        Employee employee = new Employee(String.valueOf(id), income, tax);
        System.out.println(employee.toString()); //testing
        System.out.println("Would you like to calculate the tax for another employee?");
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
                    menuSelection();
                    break;
                case "n":
                    menuSelection();
                    break;
                default:
                    System.out.println("Please try again.");
            }
        }
    }
}
