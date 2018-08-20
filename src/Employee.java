

public class Employee {


    String employeeId;
    double employeeIncome;
    double employeeTax;


    public Employee(String employeeId, double employeeIncome, double employeeTax) {
        this.employeeId = employeeId;
        this.employeeIncome = employeeIncome;
        this.employeeTax = employeeTax;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeIncome(double employeeIncome) {
        this.employeeIncome = employeeIncome;
    }

    public void setEmployeeTax(double employeeTax) {
        this.employeeTax = employeeTax;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getEmployeeIncome() {
        return employeeIncome;
    }

    public double getEmployeeTax() {
        return employeeTax;
    }

    @Override
    public String toString() {
        return "Employee Id =" + employeeId +
                ", Income =" + employeeIncome +
                ", Tax =" + employeeTax;
    }
}
