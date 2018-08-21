import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaxController {

    private double thresholdA;
    private double thresholdB;
    private double thresholdC;
    private double thresholdD;
    private double taxRateA;
    private double taxRateB;
    private double taxRateC;
    private double taxRateD;
    private double taxRateE;
    private double plusC;
    private double plusD;
    private double plusE;
    private double perB;
    private double perC;
    private double perD;
    private double perE;

    public TaxController() {
    }

    public void processTaxRatesTxt(String pathname){
        if(pathname == ""){
            pathname = "taxrates.txt";
        }
        String thisLine = "";
        try {
            Scanner fileInput = new Scanner(new File(pathname));
            int i = 1;
            fileInput.nextLine(); //ignore first array, it is empty.
            while(fileInput.hasNextLine()){
                thisLine = fileInput.nextLine();
                String cleanLine = "";
                thisLine = thisLine.replaceAll("[,]", ""); //condense the numbers
                Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
                Matcher matcher = regex.matcher(thisLine);
                while(matcher.find()){
                    cleanLine += matcher.group(1) + " ";
                }
                String strArray[] = cleanLine.split(" "); //place numbers into array
                double[] doubleArray = new double[strArray.length];
                for(int j = 0 ; j < strArray.length ; j++){
                    try {
                        doubleArray[j] = Double.valueOf(strArray[j]);
                    } catch (NumberFormatException e){
                        System.out.println("NumberFormatException @" + j);
                    }
                    //System.out.println(j + ":" + strArray[j]); //testing
                }
                if(i == 1){
                    thresholdA = (double)doubleArray[1];
                    taxRateA = (double)doubleArray[2];
                } else if(i == 2){
                    thresholdB = (double)doubleArray[1];
                    taxRateB = (double)doubleArray[2]/100d;
                    perB = (double)doubleArray[3];
                } else if(i == 3){
                    thresholdC = (double)doubleArray[1];
                    plusC = (double)doubleArray[2];
                    taxRateC = (double)doubleArray[3]/100d;
                    perC = (double)doubleArray[4];
                } else if(i == 4){
                    thresholdD = (double)doubleArray[1];
                    plusD = (double)doubleArray[2];
                    taxRateD = (double)doubleArray[3]/100d;
                    perD = (double)doubleArray[4];
                } else if(i == 5){
                    plusE = (double)doubleArray[1];
                    taxRateE = (double)doubleArray[2]/100d;
                    perE = (double)doubleArray[3];
                } else{
                    //Some who wander are not lost, but you sure are
                    break;
                }
                i++;
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
            System.out.println("Please enter file path or name");
            Scanner input = new Scanner(System.in);
            pathname = input.nextLine();
            processTaxRatesTxt(pathname);
        }
    }

    public double determineTaxThreshold(double income){
        double tax = 0.00;
        if (income <= thresholdA){
            tax = taxRateA;
        }else if(income < thresholdB){
            tax = calcTax(income,thresholdA,taxRateB,perB,taxRateA);
        }else if(income < thresholdC){
            tax = calcTax(income,thresholdB,taxRateC,perC,plusC);
        }else if(income < thresholdD){
            tax = calcTax(income,thresholdC,taxRateD,perD,plusD);
        }else {
            tax = calcTax(income,thresholdD,taxRateE,perE,plusE);
        }
        return tax;
    }

    public double calcTax(double income, double prevThreshold, double taxRate, double per, double plus){
        double tax = 0;
        tax = (((income - prevThreshold)*taxRate)+plus)/per;
        return tax;
    }

    public void writeToTaxReport(Employee employee) {
        File taxReport = new File("taxreport.txt");
        FileWriter output;
        String header = "Employee Id\t\tTaxable Income\t\tTax";
        try {
            if(taxReport.exists()){
                output = new FileWriter(taxReport,true);
                output.write(employee.toString());
                System.out.println(header);
                System.out.println(employee.toString());
                output.close();
            }else{
                output = new FileWriter(taxReport);
                output.write(header);
                output.close();
                writeToTaxReport(employee);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    private HashMap<Integer, Employee> processTaxRecords(String pathname){
        HashMap<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
        if(pathname == ""){
            pathname = "taxreport.txt";
        }
        String thisLine = "";
        try {
            Scanner fileInput = new Scanner(new File(pathname));
            fileInput.nextLine();
            while (fileInput.hasNextLine()) {
                thisLine = fileInput.nextLine();
                String temp = "";
                Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
                Matcher matcher = regex.matcher(thisLine);
                int i = 0;
                String id = "";
                double income = 0d;
                double tax = 0d;
                while (matcher.find()) {
                    temp = matcher.group(1);
                    if (i == 0) {
                        id = temp;
                        i = 1;
                    }else if (i == 1) {
                        income = Double.valueOf(temp);
                        i = 2;
                    }else if (i == 2) {
                        tax = Double.valueOf(temp);
                        i = 3;
                    }else if (i == 3) {
                        Employee employee = new Employee(id, income, tax);
                        employeeMap.put(Integer.valueOf(id), employee); //never gets added
                        id = temp;
                        i = 1;
                    }else{
                        System.out.println("It appears as though something went wrong while" +
                                " populating the employee hash map. Exiting the program.");
                        System.exit(1);
                    }
                }
            }
        }catch(FileNotFoundException e) {
            System.out.println("File not found!");
            System.out.println("Please enter file path or name");
            Scanner input = new Scanner(System.in);
            pathname = input.nextLine();
            processTaxRecords(pathname);
        }
        return employeeMap;
    }

    public void getEmployeeTaxRecord(int id){
        HashMap<Integer, Employee> employeeMap = processTaxRecords("");
        if(employeeMap.containsKey(id)){
            Employee employee = employeeMap.get(id);
            String header = "Employee Id\t\tTaxable Income\t\tTax";
            System.out.println(header);
            System.out.println(employee.toString());
        }else {
            if (employeeMap.size() == 0){
                System.out.println("You appear to not have any employees.");
            }else {
                System.out.println("That key does not appear to exist.");
            }
        }
    }
}
