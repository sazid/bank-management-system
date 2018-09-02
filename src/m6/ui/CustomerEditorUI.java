package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerEditorUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;
    private String accountNumber;

    private JButton saveBtn, deleteBtn;
    private JLabel accountNumberLabel, balanceLabel;
    private JTextField accountNumberTf, balanceTf;

    public CustomerEditorUI(UserLoginInfo userLoginInfo, String accountNumber) {
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
        int x = 200;
        int y = 140;

        accountNumberLabel = new JLabel("Account Number: ");
        accountNumberLabel.setBounds(x, y, 100, 30);

        accountNumberTf = new JTextField();
        accountNumberTf.setBounds(x + 110, y, 200, 30);

        balanceLabel = new JLabel("Balance: ");
        balanceLabel.setBounds(x, y + 40, 100, 30);

        balanceTf = new JTextField();
        balanceTf.setBounds(x + 110, y + 40, 200, 30);

        saveBtn = new JButton("Save");
        saveBtn.setBackground(Color.GREEN);
        saveBtn.setBounds(x + 110, y + 40 + 40, 200, 30);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setBounds(x + 110, y + 40 + 40 + 40, 200, 30);
        if (accountNumber == null || accountNumber.isEmpty()) {
            deleteBtn.setVisible(false);
        }

        mainPanel.add(accountNumberLabel);
        mainPanel.add(balanceLabel);
        mainPanel.add(accountNumberTf);
        mainPanel.add(balanceTf);
        mainPanel.add(saveBtn);
        mainPanel.add(deleteBtn);
    }

    private void bind() {
        saveBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
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
        } else if (src == deleteBtn) {
            delete();
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

    private void delete() {
        // depending on whether the user is in edit mode, appropriate query will be used
        String updateQuery = "DELETE FROM account WHERE accountNumber=?";

        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(updateQuery);
            ps.setString(1, accountNumber);

            System.out.println(ps);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Success!");

            new EmployeeUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Failed to delete account.");
        }
    }
    
}
