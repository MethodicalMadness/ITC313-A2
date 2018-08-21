import java.text.DecimalFormat;

public class Employee {


    String employeeId;
    double employeeIncome;
    double employeeTax;


    public Employee(String employeeId, double employeeIncome, double employeeTax) {
        this.employeeId = employeeId;
        this.employeeIncome = (double)Math.round(employeeIncome* 100d) / 100d;
        this.employeeTax = (double)Math.round(employeeTax* 100d) / 100d;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.00");
        return "\r\n" + employeeId + "\t\t\t" + df.format(employeeIncome) +
                "\t\t" + df.format(employeeTax);
    }
}
