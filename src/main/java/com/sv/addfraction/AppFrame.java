package com.sv.addfraction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Shailendra Verma (shailendravermag@gmail.com)
 * Swing UI
 */
public class AppFrame extends JFrame {

    AppFrame() {
        Font baseFont = new Font("Dialog", Font.PLAIN, 12);
        setFont(baseFont);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setForeground(Color.black);
        setLayout(new FlowLayout());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
