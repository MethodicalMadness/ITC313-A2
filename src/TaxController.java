import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TaxController {


    private static int thresholdA;
    private static int thresholdB;
    private static int thresholdC;
    private static int thresholdD;
    private static int taxRateA;
    private static int taxRateB;
    private static int taxRateC;
    private static int taxRateD;
    private static int taxRateE;
    private static int plusC;
    private static int plusD;
    private static int plusE;
    private static int perB;
    private static int perC;
    private static int perD;
    private static int perE;


    public TaxController() {
    }


    public static void processTaxRatesTxt(String pathname){
        if(pathname == ""){
            pathname = "taxrates.txt";
        }
        String thisLine = "";
        try {
            Scanner input = new Scanner(new File(pathname));
            int i = 0;
            while(input.hasNextLine()){
                thisLine = input.nextLine();
                thisLine = thisLine.replaceAll("[,]", ""); //condense the numbers
                thisLine = thisLine.replaceAll("\\D+"," "); //remove noise
                thisLine = thisLine.replaceAll("^\\s+", ""); //remove leading whitespace
                String strArray[] = thisLine.split(" "); //place whole numbers into array
                int intArray[] = new int[strArray.length];
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
                    thresholdA = intArray[1];
                    taxRateA = intArray[2];
                } else if(i == 2){
                    thresholdB = intArray[1];
                    taxRateB = intArray[2];
                    perB = intArray[3];
                } else if(i == 3){
                    thresholdC = intArray[1];
                    plusC = intArray[2];
                    taxRateC = intArray[3];
                    perC = intArray[4];
                } else if(i == 4){
                    thresholdD = intArray[1];
                    plusD = intArray[2];
                    taxRateD = intArray[3];
                    perD = intArray[4];
                } else if(i == 5){
                    plusE = intArray[1];
                    taxRateE = intArray[2];
                    perE = intArray[3];
                } else{
                    //Some who wander are not lost, but you sure are
                    break;
                }
                i++;
            }
            input.close();
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
            System.out.println("Please enter file path or name");
            Scanner userInput = new Scanner(System.in);
            pathname = userInput.nextLine();
            TaxController.processTaxRatesTxt(pathname);
        }
    }
}
