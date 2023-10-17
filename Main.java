package Assignment_5;

import javax.swing.*;
import java.text.DecimalFormat;
import java.io.*;

public class Main {
    public static double initBal;
    public static String accountName;
    public static double transAmt;
    public static int checkNumber;
    public static double cashAmt;
    public static double checkAmt;
    public static boolean frstTime = true;
    public static Account account;
    public static CheckingAccount checkingAccount;
    public static boolean looping = true;
    public static DecimalFormat fmt = new DecimalFormat("###,###,##0.00");
    public static int actionCounter;
    public static JFrame frame;

    public static void main(String[] args) {
        accountName = getAccountName();
        initBal = getInitBal();
        account = new Account(accountName, initBal);
        checkingAccount = new CheckingAccount(accountName, initBal);

        frame = new JFrame ("Action");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        CheckOptionsPanel panel = new CheckOptionsPanel();
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static String getAccountName()
    {
        String accountName;
        accountName = JOptionPane.showInputDialog("Enter the account name: ");
        return accountName;
    }
    public static double getInitBal()
    {
        String initBalString;
        initBalString = JOptionPane.showInputDialog("Enter your initial balance: ");
        initBal = Double.parseDouble(initBalString);
        return initBal;
    }
    public static int getTransCode()
    {
        int transCode;
        String codeInput;
        codeInput = JOptionPane.showInputDialog("Enter the transaction code: ");
        transCode = Integer.parseInt(codeInput);
        return transCode;
    }
    public static int getCheckNumber()
    {
        int transCode;
        String codeInput;
        codeInput = JOptionPane.showInputDialog("Enter your check number: ");
        transCode = Integer.parseInt(codeInput);
        return transCode;
    }
    public static double getCheckAmt()
    {
        double checkAmt;
        String amtInput;
        amtInput = JOptionPane.showInputDialog("Enter the transaction amount: ");
        checkAmt = Double.parseDouble(amtInput);
        return checkAmt;
    }
    public static double[] getDepositAmt()
    {
        double[] depositAmtArray = new double[2];
        JTextField cash = new JTextField(5);
        JTextField check = new JTextField(5);
        Object[] message = {"Cash:", cash, "Check:", check};
        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION)
        {
            if (cash.getText().length() == 0)
            {
                depositAmtArray[0] = 0;
            }
            else
            {
                depositAmtArray[0] = Double.parseDouble(cash.getText());
            }
            if (check.getText().length() == 0)
            {
                depositAmtArray[1] = 0;
            }else
            {
                depositAmtArray[1] = Double.parseDouble(check.getText());
            }
        }
        return depositAmtArray;
    }
    public static double processCheck(double transAmt, int checkNumber)
    {
        Check check;
        Transaction transaction;
        account.setBalance(1, transAmt);
        checkingAccount.setServiceCharge(0.15);

        check = new Check(1, actionCounter, transAmt, checkNumber);
        checkingAccount.addTrans(check);
        actionCounter++;

        transaction = new Transaction(3, actionCounter, 0.15);
        actionCounter++;
        checkingAccount.addTrans(transaction);

        return checkingAccount.getServiceCharge();
    }
    public static double processDeposit(double transAmt, double cash, double check)
    {
        Transaction transaction;
        Deposit deposit;
        account.setBalance(2, transAmt);
        checkingAccount.setServiceCharge(0.10);

        deposit = new Deposit(2, actionCounter, transAmt, cash, check);
        actionCounter++;
        checkingAccount.addTrans(deposit);

        transaction = new Transaction(3, actionCounter, 0.10);
        actionCounter++;
        checkingAccount.addTrans(transaction);

        return checkingAccount.getServiceCharge();
    }
    public static void transactionSelection()
    {
        int transCode = getTransCode();
        String message;
        message = accountName + "'s Account\n";
        switch (transCode) {
            case 0:
                if (account.getBalance() < 0)
                {
                    message += "Transaction: End\nCurrent Balance: ($" + fmt.format(account.getBalance() * -1) + ")\nTotal Service Charge: $" +
                            fmt.format(checkingAccount.getServiceCharge());
                } else
                {
                    message += "Transaction: End\nCurrent Balance: $" + fmt.format(account.getBalance()) + "\nTotal Service Charge: $" +
                            fmt.format(checkingAccount.getServiceCharge());
                }

                account.setBalance(1, checkingAccount.getServiceCharge());

                if (account.getBalance() < 0)
                {
                    message += "\nFinal balance: ($" + fmt.format(account.getBalance() * -1) + ")";
                } else {
                    message += "\nFinal balance: $" + fmt.format(account.getBalance());
                }

                JOptionPane.showMessageDialog(null, message);

                looping = false;

                break;

            case 1:
                checkNumber = getCheckNumber();
                checkAmt = getCheckAmt();
                double serviceCharge = processCheck(checkAmt, checkNumber);

                if (account.getBalance() < 0) {
                    message += "Transaction: Check #" + checkNumber + " in Amount of $" + fmt.format(checkAmt) + "\nCurrent Balance: $(" + fmt.format(account.getBalance() * -1) +
                            ")\nService Charge: Check -- charge $0.15";
                } else {
                    message += "Transaction: Check #" + checkNumber + " in Amount of $" + fmt.format(checkAmt) + "\nCurrent Balance: $" + fmt.format(account.getBalance()) +
                            "\nService Charge: Check -- charge $0.15";
                }

                if (account.getBalance() < 500) {
                    if (frstTime) {
                        Transaction transaction;
                        transaction = new Transaction(3, actionCounter, 5);
                        actionCounter++;
                        checkingAccount.addTrans(transaction);

                        checkingAccount.setServiceCharge(5);
                        message += "\nServiceCharge: Below $500 -- charge $5.00";
                        frstTime = false;
                    }
                }

                if (account.getBalance() < 50) {
                    message += "\nWarning: Balance below $50";
                }

                if (account.getBalance() < 0) {
                    Transaction transaction;
                    transaction = new Transaction(3, actionCounter, 10);
                    actionCounter++;
                    checkingAccount.addTrans(transaction);

                    checkingAccount.setServiceCharge(10);
                    message += "\nServiceCharge: Below $0 -- charge $10.00";
                }

                message += "\nTotal Service Charge: $" + fmt.format(checkingAccount.getServiceCharge());
                JOptionPane.showMessageDialog(null, message);

                break;

            case 2:
                double[] depositAmt = getDepositAmt();
                cashAmt = depositAmt[0];
                checkAmt = depositAmt[1];
                transAmt = cashAmt + checkAmt;
                serviceCharge = processDeposit(transAmt, cashAmt, checkAmt);

                if (account.getBalance() < 0) {
                    message += "Transaction: Deposit in Amount of $" + fmt.format(transAmt) + "\nCurrent Balance: ($" + fmt.format(account.getBalance() * -1) +
                            ")\nService Charge: Deposit -- charge $0.10";
                } else {
                    message += "Transaction: Deposit in Amount of $" + fmt.format(transAmt) + "\nCurrent Balance: $" + fmt.format(account.getBalance()) +
                            "\nService Charge: Deposit -- charge $0.10";
                }

                message += "\nTotal Service Charge: $" + fmt.format(checkingAccount.getServiceCharge());
                JOptionPane.showMessageDialog(null, message);

                break;
        }
    }
    public static void listTransactions()
    {
        String message;
        message = "List All Transaction\nName: " + accountName + "\n\nID    Type    Amount\n";
        for (int i = 0; i < checkingAccount.getTransCount(); i++)
        {
            Transaction element = checkingAccount.getTrans(i);
            message += element.getTransId() + "   ";
            if (element.getTransNumber() == 1)
            {
                message += "Check   $" + fmt.format(element.getTransAmount()) + "\n";
            }
            else if (element.getTransNumber() == 2)
            {
                message += "Deposit   $" + fmt.format(element.getTransAmount()) + "\n";
            }
            else if (element.getTransNumber() == 3)
            {
                message += "Svc. Chg.   $" + fmt.format(element.getTransAmount()) + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, message);
    }
    public static void listChecks()
    {
        String message;
        message = "List All Transaction\nName: " + accountName + "\n\nID   Check    Amount\n";
        for (int i = 0; i < checkingAccount.getTransCount(); i++)
        {
            Transaction element = checkingAccount.getTrans(i);
            if (element.getTransNumber() == 1)
            {
                Check checkElement = (Check) element;
                message += element.getTransId() + "   " + checkElement.getCheckNumber() + "    $" + fmt.format(element.getTransAmount()) + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, message);
    }
    public static void listDeposits()
    {
        String message;
        message = "List All Transaction\nName: " + accountName + "\n\nID   Cash   Check    Amount\n";
        for (int i = 0; i < checkingAccount.getTransCount(); i++)
        {
            Transaction element = checkingAccount.getTrans(i);
            if (element.getTransNumber() == 2)
            {
                Deposit depositElement = (Deposit) element;
                message += element.getTransId() + "    $" + fmt.format(depositElement.getCash()) + "    $" +
                        fmt.format(depositElement.getChecks()) + "    $" + fmt.format(element.getTransAmount()) + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, message);
    }
//    public static void openFile()
//    {
//        JFileChooser fileChooser = new JFileChooser();
//        try
//        {
//            int response = fileChooser.showOpenDialog(null);
//            if (response == JFileChooser.APPROVE_OPTION) {
//                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//            }
//        }
//        catch(IOException e) {
//            JOptionPane.showMessageDialog(null, "File not able to be opened. Try again.");
//            int response = fileChooser.showOpenDialog(null);
//            if (response == JFileChooser.APPROVE_OPTION) {
//                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//            }
//        }
//    }
//    public static void saveFile()
//    {
//        JFileChooser fileChooser = new JFileChooser();
//        File defaultFile = new File("/Users/danishtran/Downloads");
//        fileChooser.setCurrentDirectory(defaultFile);
//
//        String optionMessage = "Would you like to use the current default file:" + defaultFile.getAbsolutePath();
//        int res = JOptionPane.showOptionDialog(new JFrame(), optionMessage,"Select an Option",
//                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
//                new Object[] { "Yes", "No", "Cancel" }, JOptionPane.YES_OPTION);
//        if (res == JOptionPane.YES_OPTION) {
//            try {
//                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//            }
//            catch(IOException) {
//                JOptionPane.showMessageDialog(null, "File not able to be opened. Try a different file.");
//                int response = fileChooser.showSaveDialog(null);
//                if (response == JFileChooser.APPROVE_OPTION) {
//                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//                }
//            }
//        }
//        else if (res == JOptionPane.NO_OPTION)
//        {
//            try {
//                int response = fileChooser.showSaveDialog(null);
//                if (response == JFileChooser.APPROVE_OPTION) {
//                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//                }
//            }
//            catch(IOException) {
//                JOptionPane.showMessageDialog(null, "File not able to be opened. Try again.");
//                int response = fileChooser.showSaveDialog(null);
//                if (response == JFileChooser.APPROVE_OPTION) {
//                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//                }
//            }
//        } else if (res == JOptionPane.CANCEL_OPTION) {
//            return;
//        } else if (res == JOptionPane.CLOSED_OPTION) {
//            return;
//        }
//    }
}