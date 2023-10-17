package Assignment_5;

import java.util.ArrayList;

public class CheckingAccount extends Account
{
    private ArrayList<Transaction> transList;
    private double balance;
    private double totalServiceCharge;
    public CheckingAccount(String acctName, double initBalance)
    {
        super(acctName, initBalance);
        transList = new ArrayList();
        totalServiceCharge = 0;
    }
    public double getServiceCharge()
    {
        return totalServiceCharge;
    }
    public void setServiceCharge(double currentServiceCharge)
    {
        totalServiceCharge = totalServiceCharge + currentServiceCharge;
    }
    public void addTrans(Transaction newTrans)
    {

        transList.add(newTrans);
    }
    public int getTransCount()
    {
        return transList.size();
    }
    public Transaction getTrans(int i)
    {
        return transList.get(i);
    }
}