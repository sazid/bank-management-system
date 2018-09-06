package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;
import m6.components.StyledButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Home page for employees. Based on whether the employee is a manager or not, it'll automatically
 * show the relevant actions which he can perform.
 */
public class EmployeeUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;

    private StyledButton viewAccountBtn,
            viewCustomerBtn,
            viewEmployeeBtn, editEmployeeBtn, addEmployeeBtn,
            viewLoginBtn, editLoginBtn, addLoginBtn,
            myInfoBtn;

    public EmployeeUI(UserLoginInfo userLoginInfo) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;

        setPageTitle("Employee | Home");

        initUI();
        bind();
        readFromDb();
    }

    private void initUI() {
        final int x = 170;
        final int buttonHeight = 35;
        final int buttonWidth = 130;

        // Accounts
        viewAccountBtn = new StyledButton("View Accounts");
        viewAccountBtn.setBounds(x + 2 * 150, 150, buttonWidth, buttonHeight);

        // Customers
        viewCustomerBtn = new StyledButton("View Customers");
        viewCustomerBtn.setBounds(x + 2 * 150, 200, buttonWidth, buttonHeight);

        // Employees
        viewEmployeeBtn = new StyledButton("View Employees");
        viewEmployeeBtn.setBounds(x, 250, buttonWidth, buttonHeight);
        viewEmployeeBtn.setVisible(false);

        addEmployeeBtn = new StyledButton("Add Employee");
        addEmployeeBtn.setBounds(x + 150, 250, buttonWidth, buttonHeight);
        addEmployeeBtn.setVisible(false);

        editEmployeeBtn = new StyledButton("Edit Employee");
        editEmployeeBtn.setBounds(x + 2 * 150, 250, buttonWidth, buttonHeight);
        editEmployeeBtn.setVisible(false);

        // Users
        viewLoginBtn = new StyledButton("View Users");
        viewLoginBtn.setBounds(x, 300, buttonWidth, buttonHeight);

        addLoginBtn = new StyledButton("Add User");
        addLoginBtn.setBounds(x + 150, 300, buttonWidth, buttonHeight);

        editLoginBtn = new StyledButton("Edit User");
        editLoginBtn.setBounds(x + 2 * 150, 300, buttonWidth, buttonHeight);

        myInfoBtn = new StyledButton("My Information");
        myInfoBtn.setBounds(x, 350, 430, buttonHeight);

        mainPanel.add(viewAccountBtn);

        mainPanel.add(viewCustomerBtn);

        mainPanel.add(viewEmployeeBtn);
        mainPanel.add(addEmployeeBtn);
        mainPanel.add(editEmployeeBtn);

        mainPanel.add(viewLoginBtn);
        mainPanel.add(addLoginBtn);
        mainPanel.add(editLoginBtn);

        mainPanel.add(myInfoBtn);
    }

    private void bind() {
        viewAccountBtn.addActionListener(this);

        viewCustomerBtn.addActionListener(this);

        viewLoginBtn.addActionListener(this);
        addLoginBtn.addActionListener(this);
        editLoginBtn.addActionListener(this);

        viewEmployeeBtn.addActionListener(this);
        addEmployeeBtn.addActionListener(this);
        editEmployeeBtn.addActionListener(this);

        myInfoBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object src = e.getSource();

        if (src == viewAccountBtn) {
            new AccountsViewerUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == viewCustomerBtn) {
            new CustomerViewerUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == viewEmployeeBtn) {
            new EmployeeViewerUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == viewLoginBtn) {
            new LoginViewerUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == addEmployeeBtn) {
            new EmployeeEditorUI(userLoginInfo, null).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == editEmployeeBtn) {
            String username = JOptionPane.showInputDialog(this,"Employee username: ");

            if (verifyEmployeeUsername(username)) {
                new EmployeeEditorUI(userLoginInfo, username).setVisible(true);
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username not found or username not specified.");
            }
        } else if (src == addLoginBtn) {
            new LoginEditorUI(userLoginInfo, null).setVisible(true);
            setVisible(false);
            dispose();
        } else if (src == editLoginBtn) {
            String username = JOptionPane.showInputDialog(this,"Username: ");

            if (verifyUsername(username)) {
                new LoginEditorUI(userLoginInfo, username).setVisible(true);
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username not found or username not specified.");
            }
        } else if (src == myInfoBtn) {
            new LoginEditorUI(userLoginInfo, userLoginInfo.username).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

    private boolean verifyAccountNumber(String accountNumber) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT accountNumber FROM account WHERE accountNumber=?"
            );
            ps.setString(1, accountNumber);

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

    private boolean verifyEmployeeUsername(String username) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT username FROM employee WHERE username=?"
            );
            ps.setString(1, username);

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

    private boolean verifyCustomerUsername(String username) {
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT username FROM customer WHERE username=?"
            );
            ps.setString(1, username);

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

    /**
     * Enables all manager related functions
     */
    private void enableManagerFunctions() {
        setPageTitle("Manager | Home");
        viewEmployeeBtn.setVisible(true);
        editEmployeeBtn.setVisible(true);
        addEmployeeBtn.setVisible(true);
    }

    private void readFromDb() {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM employee WHERE username=?"
            );
            ps.setString(1, userLoginInfo.username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("manager")) {
                    // enable all manager functions, if the current user is a manager
                    enableManagerFunctions();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Failed to fetch data.");
        }
    }

}
