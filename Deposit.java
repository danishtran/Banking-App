package Assignment_5;

public class Deposit extends Transaction
{
    private double cash;
    private double checks;

    public Deposit(int tCount, int tId, double tAmt, double cash, double checks)
    {
        super(tCount, tId, tAmt);
        this.cash = cash;
        this.checks = checks;
    }
    public double getCash() {
        return this.cash;
    }

    public double getChecks() {
        return this.checks;
    }
}
