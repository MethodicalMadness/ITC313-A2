

public class Main {


    public static void main(String[] args){
        TaxController taxController = new TaxController();
        taxController.processTaxRatesTxt("");
        System.out.println(String.valueOf(taxController.determineTax(100000)));

    }
}
