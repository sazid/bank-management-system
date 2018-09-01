package m6;

import java.awt.*;

public class Start {

    public static void main(String[] args) {
        // verify and connect to db
        ConnectionManager.getInstance();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }

}
