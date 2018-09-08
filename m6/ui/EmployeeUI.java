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
            viewEmployeeBtn,
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
        final int x = 240;
        final int buttonHeight = 35;
        final int buttonWidth = 300;

        // Accounts
        viewAccountBtn = new StyledButton("View Accounts");
        viewAccountBtn.setBounds(x, 150, buttonWidth, buttonHeight);

        // Customers
        viewCustomerBtn = new StyledButton("View Customers");
        viewCustomerBtn.setBounds(x, 200, buttonWidth, buttonHeight);

        // Employees
        viewEmployeeBtn = new StyledButton("View Employees");
        viewEmployeeBtn.setBounds(x, 250, buttonWidth, buttonHeight);
        viewEmployeeBtn.setVisible(false);

        myInfoBtn = new StyledButton("My Information");
        myInfoBtn.setBounds(x, 350, buttonWidth, buttonHeight);

        mainPanel.add(viewAccountBtn);

        mainPanel.add(viewCustomerBtn);

        mainPanel.add(viewEmployeeBtn);

        mainPanel.add(myInfoBtn);
    }

    private void bind() {
        viewAccountBtn.addActionListener(this);

        viewCustomerBtn.addActionListener(this);

        viewEmployeeBtn.addActionListener(this);

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
        } else if (src == myInfoBtn) {
            new LoginEditorUI(userLoginInfo, userLoginInfo.username).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

    /**
     * Enables all manager related functions
     */
    private void enableManagerFunctions() {
        setPageTitle("Manager | Home");
        viewEmployeeBtn.setVisible(true);
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
