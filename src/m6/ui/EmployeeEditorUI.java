package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;
import m6.components.StyledButton;
import m6.components.StyledLabel;
import m6.components.StyledTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeEditorUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;
    private String username;

    private StyledButton saveBtn, deleteBtn;
    private StyledLabel usernameLabel, phoneNumberLabel, roleLabel, salaryLabel;
    private StyledTextField usernameTf, phoneNumberTf, roleTf, salaryTf;

    public EmployeeEditorUI(UserLoginInfo userLoginInfo, String username) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;
        this.username = username;

        if (username != null) {
            setPageTitle("Edit Employee");
        } else {
            setPageTitle("Add Employee");
        }

        backButton.setVisible(true);
        backButton.addActionListener(this);

        initUI();
        bind();

        if (username != null) {
            readFromDb();
        }
    }

    private void initUI() {
        int x = 200;
        int y = 140;

        usernameLabel = new StyledLabel("Username: ");
        usernameLabel.setBounds(x, y, 100, 30);

        usernameTf = new StyledTextField();
        usernameTf.setBounds(x + 110, y, 200, 30);
        if (username != null) {
            usernameTf.setEditable(false);
        }

        phoneNumberLabel = new StyledLabel("Phone Number: ");
        phoneNumberLabel.setBounds(x, y + 40, 100, 30);

        phoneNumberTf = new StyledTextField();
        phoneNumberTf.setBounds(x + 110, y + 40, 200, 30);

        roleLabel = new StyledLabel("Role: ");
        roleLabel.setBounds(x, y + 40 * 2, 100, 30);

        roleTf = new StyledTextField();
        roleTf.setBounds(x + 110, y + 40 * 2, 200, 30);

        salaryLabel = new StyledLabel("Salary: ");
        salaryLabel.setBounds(x, y + 40 * 3, 100, 30);

        salaryTf = new StyledTextField();
        salaryTf.setBounds(x + 110, y + 40 * 3, 200, 30);

        saveBtn = new StyledButton("Save");
        saveBtn.setBackground(Color.GREEN);
        saveBtn.setBounds(x + 110, y + 40 * 4, 200, 30);

        deleteBtn = new StyledButton("Delete");
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setBounds(x + 110, y + 40 * 5, 200, 30);
        if (username == null || username.isEmpty()) {
            deleteBtn.setVisible(false);
        }

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameTf);

        mainPanel.add(phoneNumberLabel);
        mainPanel.add(phoneNumberTf);

        mainPanel.add(roleLabel);
        mainPanel.add(roleTf);

        mainPanel.add(salaryLabel);
        mainPanel.add(salaryTf);

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
                    "SELECT username, phoneNumber, role, salary FROM employee WHERE username=?"
            );

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String phoneNumber = rs.getString("phoneNumber");
                String role = rs.getString("role");
                double salary = rs.getDouble("salary");

                usernameTf.setText(username);
                phoneNumberTf.setText(phoneNumber);
                roleTf.setText(role);
                salaryTf.setText(String.valueOf(salary));
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

    private boolean verifyUsername(String username) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT username FROM login WHERE username=?"
            );
            ps.setString(1, username);
            System.out.println(ps);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void save() {
        // depending on whether the user is in edit mode, appropriate query will be used
        String insertQuery = "INSERT INTO employee VALUES(?, ?, ?, ?)";
        String updateEmployeeQuery = "UPDATE employee SET phoneNumber=?, role=?, salary=? WHERE username=?";

        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps;
        try {
            if (username == null || username.trim().isEmpty()) {
                if (!verifyUsername(usernameTf.getText())) {
                    JOptionPane.showMessageDialog(this,
                            "No user present with the given username.");
                    return;
                }

                ps = conn.prepareStatement(insertQuery);

                ps.setString(1, usernameTf.getText());
                ps.setString(2, phoneNumberTf.getText().trim());
                ps.setString(3, roleTf.getText().trim());
                ps.setDouble(4, Double.parseDouble(salaryTf.getText()));
            } else {
                ps = conn.prepareStatement(updateEmployeeQuery);

                ps.setString(1, phoneNumberTf.getText().trim());
                ps.setString(2, roleTf.getText().trim());
                ps.setDouble(3, Double.parseDouble(salaryTf.getText()));
                ps.setString(4, username);
            }

            System.out.println(ps);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Success!");

            new EmployeeUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Failed to add/update employee.");
        }
    }

    private void delete() {
        // depending on whether the user is in edit mode, appropriate query will be used
        String updateQuery = "DELETE FROM employee WHERE username=?";

        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(updateQuery);
            ps.setString(1, username);

            System.out.println(ps);
            int count = ps.executeUpdate();

            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Success!");
                new EmployeeUI(userLoginInfo).setVisible(true);
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error! Wrong username.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Failed to delete employee.");
        }
    }
    
}

