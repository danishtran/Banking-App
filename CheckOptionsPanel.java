package Assignment_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckOptionsPanel extends JPanel
{
    private JLabel prompt;
    private JRadioButton one, two, three, four, five, six;

    public CheckOptionsPanel()
    {
        prompt = new JLabel("Choose action");
        prompt.setFont(new Font("Helvetica", Font.BOLD, 55));

        one = new JRadioButton("Enter Transaction");
        one.setBackground(Color.green);
        two = new JRadioButton("List All Transaction");
        two.setBackground(Color.green);
        three = new JRadioButton("List All Checks");
        three.setBackground(Color.green);
        four = new JRadioButton("List All Deposits");
        four.setBackground(Color.green);
        five = new JRadioButton("Open File");
        five.setBackground(Color.green);
        six = new JRadioButton("Save File");
        six.setBackground(Color.green);

        ButtonGroup group = new ButtonGroup();
        group.add(one);
        group.add(two);
        group.add(three);
        group.add(four);
        group.add(five);
        group.add(six);

        CheckOptionsListener listener = new CheckOptionsListener();
        one.addActionListener(listener);
        two.addActionListener(listener);
        three.addActionListener(listener);
        four.addActionListener(listener);
        five.addActionListener(listener);
        six.addActionListener(listener);
        add(prompt);
        add(one);
        add(two);
        add(three);
        add(four);
        add(five);
        add(six);

        setBackground(Color.green);
        setPreferredSize(new Dimension(450, 200));
    }
    private class CheckOptionsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();
            if (source == one) {
                Main.transactionSelection();
            } else if (source == two) {
                Main.listTransactions();
            } else if (source == three) {
                Main.listChecks();
            } else if (source == four) {
                Main.listDeposits();
//            } else if (source == five) {
//                Main.openFile();
//            } else if (source == six) {
//                Main.saveFile();
            }
        }
    }
}

