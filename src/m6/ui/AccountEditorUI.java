package m6.ui;

import m6.UserLoginInfo;
import m6.table_model.CustomerTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccountEditorUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;
    private String accountNumber;

    private JButton saveBtn;
    private JLabel accountNumberLabel, balanceLabel;
    private JTextField accountNumberTf, balanceTf;

    public AccountEditorUI(UserLoginInfo userLoginInfo, String accountNumber, boolean editMode) {
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

    }

}
