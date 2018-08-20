import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
            int i = 0;
            while(fileInput.hasNextLine()){
                thisLine = fileInput.nextLine();
                thisLine = thisLine.replaceAll("[,]", ""); //condense the numbers
                thisLine = thisLine.replaceAll("\\D+"," "); //remove noise
                thisLine = thisLine.replaceAll("^\\s+", ""); //remove leading whitespace
                String strArray[] = thisLine.split(" "); //place whole numbers into array
                int[] intArray = new int[strArray.length];
                //System.out.println("array length = " + intArray.length); //testing
                if(i == 0) {
                    //ignore first array, it is empty.
                }else {
                    for(int j = 0 ; j < strArray.length ; j++){
                        try {
                            intArray[j] = Integer.parseInt(strArray[j]);
                        } catch (NumberFormatException e){
                            System.out.println("NumberFormatException @" + j);
                        }
                        //System.out.println(j + ":" + strArray[j]); //testing
                    }
                }
                if(i == 0){
                    //ignore first array, it is empty.
                } else if(i == 1){
                    thresholdA = (double)intArray[1];
                    taxRateA = (double)intArray[2]/100;
                } else if(i == 2){
                    thresholdB = (double)intArray[1];
                    taxRateB = (double)intArray[2]/100;
                    perB = (double)intArray[3];
                } else if(i == 3){
                    thresholdC = (double)intArray[1];
                    plusC = (double)intArray[2];
                    taxRateC = ((double)intArray[3] + ((double)intArray[4]/10))/100;
                    perC = (double)intArray[5];
                } else if(i == 4){
                    thresholdD = (double)intArray[1];
                    plusD = (double)intArray[2];
                    taxRateD = (double)intArray[3]/100;
                    perD = (double)intArray[4];
                } else if(i == 5){
                    plusE = (double)intArray[1];
                    taxRateE = (double)intArray[2]/100;
                    perE = (double)intArray[3];
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


    public double determineTax(double income){
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
}
