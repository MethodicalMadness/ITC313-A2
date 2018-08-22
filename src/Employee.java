import java.text.DecimalFormat;

/**
 * @author Michael Coleman
 */
public class Employee {


    String employeeId;
    double employeeIncome;
    double employeeTax;

    /**
     * Employee Constructor
     * @param employeeId
     * @param employeeIncome
     * @param employeeTax
     */
    public Employee(String employeeId, double employeeIncome, double employeeTax) {
        this.employeeId = employeeId;
        this.employeeIncome = (double)Math.round(employeeIncome* 100d) / 100d;
        this.employeeTax = (double)Math.round(employeeTax* 100d) / 100d;
    }

    /**
     * Formatted description of the employees details.
     * @return String
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        String formattedDescription = "\r\n" + employeeId + "\t\t\t" + df.format(employeeIncome) +
                "\t\t" + df.format(employeeTax);
        return formattedDescription;
    }
}
