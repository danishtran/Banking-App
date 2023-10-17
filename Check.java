package Assignment_5;

public class Check extends Transaction
{
    private int checkNumber;
    public Check(int tCount, int tId, double tAmt, int checkNumber)
    {
        super(tCount, tId, tAmt);
        this.checkNumber = checkNumber;
    }
    public int getCheckNumber()
    {
        return checkNumber;
    }
    public void setCheckNumber(int checkNumber)
    {
        this.checkNumber = checkNumber;
    }
}
