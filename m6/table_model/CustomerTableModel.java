package m6.table_model;

import m6.ConnectionManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CustomerTableModel extends AbstractTableModel {
    private Vector<String> columnNames = new Vector<>();
    private Vector<Object[]> data;
    private JFrame ui;

    public CustomerTableModel(JFrame ui) {
        columnNames.add("Name");
        columnNames.add("Username");
        columnNames.add("Account Number");
        columnNames.add("Phone Number");
        columnNames.add("Balanace");

        data = readFromDb();

        this.ui = ui;
    }

    private Vector<Object[]> readFromDb() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Vector<Object[]> v = new Vector<>();

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT login.name, customer.username, customer.accountNumber, customer.phoneNumber, customer.accountNumber, account.balance " +
                            "FROM customer, login, account " +
                            "WHERE login.username=customer.username AND account.accountNumber=customer.accountNumber " +
                            "ORDER BY login.name ASC"
            );

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("login.name");
                String username = rs.getString("customer.username");
                String accountNumber = rs.getString("customer.accountNumber");
                String phoneNumber = rs.getString("customer.phoneNumber");
                String balance = rs.getString("account.balance");

                v.add(new Object[]{name, username, accountNumber, phoneNumber, balance});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(ui, "Error! Failed to fetch data.");
        }

        return v;
    }


    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
    }

}
