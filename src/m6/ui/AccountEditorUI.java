package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;
import m6.table_model.CustomerTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AccountEditorUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;
    private String accountNumber;

    private JButton saveBtn;
    private JLabel accountNumberLabel, balanceLabel;
    private JTextField accountNumberTf, balanceTf;

    public AccountEditorUI(UserLoginInfo userLoginInfo, String accountNumber) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;
        this.accountNumber = accountNumber;

        if (accountNumber != null) {
            setPageTitle("Edit Account");
        } else {
            setPageTitle("Add Account");
        }

        backButton.setVisible(true);
        backButton.addActionListener(this);

        initUI();
        bind();

        if (accountNumber != null) {
            readFromDb();
        }
    }

    private void initUI() {
        int x = 230;

        accountNumberLabel = new JLabel("Account Number: ");
        accountNumberLabel.setBounds(x, 180, 100, 30);

        accountNumberTf = new JTextField();
        accountNumberTf.setBounds(x + 110, 180, 200, 30);

        balanceLabel = new JLabel("Balance: ");
        balanceLabel.setBounds(x, 180 + 50, 100, 30);

        balanceTf = new JTextField();
        balanceTf.setBounds(x + 110, 180 + 50, 200, 30);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(x + 110, 180 + 50 + 50, 200, 30);

        mainPanel.add(accountNumberLabel);
        mainPanel.add(balanceLabel);
        mainPanel.add(accountNumberTf);
        mainPanel.add(balanceTf);
        mainPanel.add(saveBtn);
    }

    private void bind() {
        saveBtn.addActionListener(this);
    }

    private void readFromDb() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT accountNumber, balance FROM account WHERE accountNumber=?"
            );

            ps.setString(1, accountNumber);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String accountNumber = rs.getString("accountNumber");
                double balance = rs.getDouble("balance");

                accountNumberTf.setText(accountNumber);
                balanceTf.setText(String.valueOf(balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Failed to fetch data.");

            new EmployeeUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object src = e.getSource();

        if (src == backButton) {
            new EmployeeUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == saveBtn) {
            save();
        }
    }

    private void save() {
        // depending on whether the user is in edit mode, appropriate query will be used
        String insertQuery = "INSERT INTO account VALUES(?, ?)";
        String updateQuery = "UPDATE account SET accountNumber=?, balance=? WHERE accountNumber=?";

        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps;
        try {
            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                ps = conn.prepareStatement(insertQuery);
            } else {
                ps = conn.prepareStatement(updateQuery);
                ps.setString(3, accountNumber);
            }

            ps.setString(1, accountNumberTf.getText().trim());
            ps.setDouble(2, Double.parseDouble(balanceTf.getText()));

            System.out.println(ps);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Success!");

            new EmployeeUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Failed to add/update account.");
        }
    }

}
