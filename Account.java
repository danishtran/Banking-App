package Assignment_5;

public class Account
{
    protected String name;
    protected double balance;

    public Account(String acctName, double initBalance)
    {
        balance = initBalance;
        name = acctName;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public double getBalance()
    {
        return balance;
    }
    public void setBalance(int tCode, double transAmt)
    {
        if(tCode == 1)
        {
            balance = balance - transAmt;
        }
        else if(tCode == 2)
        {
            balance = balance + transAmt;
        }
    }
}
