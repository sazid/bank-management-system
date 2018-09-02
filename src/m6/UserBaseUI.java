package m6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserBaseUI extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel titleLabel, nameLabel, extraInfoLabel;
    private JButton logoutButton;
    private UserLoginInfo loginInfo;

    public UserBaseUI(UserLoginInfo loginInfo) {
        super("Bank Management System");
        this.loginInfo = loginInfo;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        initBaseUI();
        bindBaseUI();
    }

    /**
     * Initialize ui elements
     */
    private void initBaseUI() {
        panel = new JPanel();
        panel.setLayout(null);

        titleLabel = new JLabel("Bank Management System");
        titleLabel.setFont(new Font("Courier", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 500, 35);

        nameLabel = new JLabel("Name: " + loginInfo.name);
        nameLabel.setFont(new Font("Courier", Font.PLAIN, 12));
        nameLabel.setBounds(10, 35, 200, 20);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(670, 10, 100, 30);

        panel.add(titleLabel);
        panel.add(nameLabel);
        panel.add(logoutButton);
        add(panel);
    }

    /**
     * Bind event listeners
     */
    private void bindBaseUI() {
        logoutButton.addActionListener(this);
    }

    public void setPageTitle(String title) {
        titleLabel.setText(title);
    }

    /**
     * Logout user
     */
    private void logout() {
        new LoginUI().setVisible(true);
        setVisible(false);
        dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            logout();
        }
    }
}
