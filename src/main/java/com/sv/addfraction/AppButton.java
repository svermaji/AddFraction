package com.sv.addfraction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Shailendra Verma (shailendravermag@gmail.com)
 * Swing UI
 */
public class AppButton extends JButton {

    AppButton(String text, char mnemonic) {
        setText(text);
        setMnemonic(mnemonic);
    }
}
