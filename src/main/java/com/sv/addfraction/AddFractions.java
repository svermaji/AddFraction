package com.sv.addfraction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Shailendra Verma (shailendravermag@gmail.com)
 * to add fractions and provide result via LCD/LCM methodology.
 *
 * Swing UI is given for convenience
 */
public class AddFractions extends AppFrame {

    private JTextArea taQuestion;
    private JTextArea taAnswer;
    private JButton btnCalculate, btnSample, btnExit;

    int[] num, den, denLcm;

    public static void main(String[] args) {
        new AddFractions().initComponents();
    }

    /**
     * This method initializes the form.
     */
    private void initComponents() {
        Container parentContainer = getContentPane();
        JPanel qPanel = new JPanel();
        JPanel qBtns = new JPanel();

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(2);
        gridLayout.setColumns(1);
        parentContainer.setLayout(gridLayout);
        setTitle("Add Fractions");

        qPanel.setLayout(new BorderLayout());
        qBtns.setLayout(new BoxLayout(qBtns, BoxLayout.Y_AXIS));

        Border emptyBorder = new EmptyBorder(new Insets(5, 5, 5, 5));

        taQuestion = new JTextArea(5, 60);
        taAnswer = new JTextArea(5, 60);
        taAnswer.setEditable(false);
        taAnswer.setForeground(Color.BLUE);
        btnCalculate = new JButton("Calculate");
        btnSample = new JButton("Sample");
        btnCalculate.addActionListener(evt -> startCalculate(taQuestion.getText()));
        btnSample.addActionListener(evt -> showSample());
        btnExit = new JButton("Exit");
        qBtns.add(btnCalculate);
        qBtns.add(btnSample);
        qBtns.add(btnExit);
        JScrollPane jspQues = new JScrollPane(taQuestion);
        jspQues.setBorder(emptyBorder);
        qPanel.add(jspQues, BorderLayout.CENTER);
        qPanel.add(qBtns, BorderLayout.EAST);

        parentContainer.add(qPanel);
        JScrollPane jspAns = new JScrollPane(taAnswer);
        jspAns.setBorder(emptyBorder);
        parentContainer.add(jspAns);

        btnExit.addActionListener(evt -> exitForm());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm();
            }
        });

        showSample();
        setToCenter();
    }

    private void showSample() {
        taQuestion.setText("3/4 1/4 5/4\n1/22 3/11 4/33 6/44");
        startCalculate(taQuestion.getText());
    }

    private void startCalculate(String text) {
        String[] questions = text.split("\n");
        taAnswer.setText("");
        for (String q : questions) {
            try {
                taAnswer.append(calculate(q.split(" ")));
                taAnswer.append("\n");
            } catch (Exception e) {
                // no action
            }
        }
    }

    private String calculate(String[] args) {
        num = new int[args.length];
        den = new int[args.length];
        denLcm = new int[args.length];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            String[] nums = arg.split("/");
            num[i] = Integer.parseInt(nums[0]);
            den[i] = Integer.parseInt(nums[1]);
            denLcm[i] = Integer.parseInt(nums[1]);
            sb.append(num[i]).append("/").append(den[i]);
            if (i < args.length-1) {
                sb.append(" + ");
            }
        }

        int lcm = getLCM(denLcm);
        String question = sb.toString();

        int sum = 0;
        for (int i = 0; i < den.length; i++) {
            num[i] *= lcm / den[i];
            sum += num[i];
        }

        String ans = sum + "/" + lcm;
        String quotientAns = "";
        String remainderAns = "";

        if (sum / lcm >= 1) {
            quotientAns = " or " + (sum / lcm);
            if (sum % lcm > 0)
                remainderAns = " " + sum % lcm + "/" + lcm;
        }

        String simplifiedAns = simplified(sum, lcm);

        return question + " = " + ans + quotientAns + remainderAns + simplifiedAns;

    }

    private String simplified(int sum, int lcm) {
        int min = Math.min(sum, lcm);
        boolean simlified = false;
        for (int i = 2; i <= min; i++) {
            if (sum % i == 0 && lcm % i == 0) {
                while (sum % i == 0 && lcm % i == 0) {
                    sum /= i;
                    lcm /= i;
                }
                simlified = true;
            }
        }
        if (simlified) {
            StringBuilder sbOut = new StringBuilder();
            sbOut.append(" or ").append(sum).append("/").append(lcm);
            if (sum / lcm >= 1) {
                sbOut.append(" or ").append(sum / lcm);
                if (sum % lcm > 0)
                    sbOut.append(" ").append(sum % lcm).append("/").append(lcm);
            }
            return sbOut.toString();
        }
        return "";
    }

    private int getLCM(int[] dens) {
        int max = dens[0];
        for (int i = 1; i < dens.length; i++) {
            max = Math.max(max, dens[i]);
        }

        int lcm = 1;
        for (int i = 2; i <= max; i++) {
            lcm = lcm * include(i, dens);
        }

        return lcm;
    }

    private int include(int num, int[] dens) {
        int retVal = 1;
        for (int j = 0; j < dens.length; j++) {
            if (dens[j] % num == 0) {
                retVal = num;
                dens[j] /= num;
            }
        }
        if (retVal == 1) {
            return retVal;
        }

        return retVal * include(num, dens);
    }

    private void setToCenter() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Exit the Application
     */
    private void exitForm() {
        setVisible(false);
        dispose();
        System.exit(0);
    }
}