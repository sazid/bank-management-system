package m6;

import javax.swing.*;
import java.sql.*;

/**
 * Home page for employees. Based on whether the employee is a manager or not, it'll automatically
 * show the relevant actions which he can perform.
 */
public class EmployeeUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;

    private JButton manageAccountBtn, manageCustomerBtn, manageEmployeeBtn;

    public EmployeeUI(UserLoginInfo userLoginInfo) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;

        setPageTitle("Employee | Home");

        initUI();
        bind();
        readFromDb();
    }

    private void initUI() {
        manageAccountBtn = new JButton("Manage Accounts");
        manageAccountBtn.setBounds(10, 300, 120, 30);

        manageCustomerBtn = new JButton("Manage Customer");
        manageCustomerBtn.setBounds(140, 300, 120, 30);

        manageEmployeeBtn = new JButton("Manage Employee");
        manageEmployeeBtn.setBounds(270, 300, 120, 30);
        manageEmployeeBtn.setVisible(false);

        mainPanel.add(manageAccountBtn);
        mainPanel.add(manageCustomerBtn);
        mainPanel.add(manageEmployeeBtn);
    }

    private void bind() {
    }

    /**
     * Enables all manager related functions
     */
    private void enableManagerFunctions() {
        setPageTitle("Manager | Home");
        manageEmployeeBtn.setVisible(true);
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
