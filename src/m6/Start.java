package m6;

import m6.ui.LoginUI;

import javax.swing.*;
import java.awt.*;

public class Start {

    public static void main(String[] args) {
        // verify and connect to db
        ConnectionManager.getInstance();

        // use native look and feel if available
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }

}
