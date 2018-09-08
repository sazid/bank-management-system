package m6.ui;

import m6.UserLoginInfo;
import m6.components.StyledButton;
import m6.table_model.AccountTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccountsViewerUI extends UserBaseUI implements ListSelectionListener {

    private UserLoginInfo userLoginInfo;

    private StyledButton addAccountBtn;

    private JTable table;
    private JScrollPane scrollPane;

    public AccountsViewerUI(UserLoginInfo userLoginInfo) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;

        setPageTitle("View Accounts");
        backButton.setVisible(true);
        backButton.addActionListener(this);

        initUI();
        bind();
    }

    private void initUI() {
        table = new JTable(new AccountTableModel(this));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 115, 775, 345);

        addAccountBtn = new StyledButton("Add Account");
        addAccountBtn.setBounds(10, 72, 120, 35);
        addAccountBtn.setBackground(new Color(0xDCBD7E));

        mainPanel.add(addAccountBtn);

        mainPanel.add(scrollPane);
    }

    private void bind() {
        addAccountBtn.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);
    }

    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (!e.getValueIsAdjusting()) {
            String accountNumber = table.getModel().getValueAt(lsm.getLeadSelectionIndex(), 2).toString();

            new AccountEditorUI(userLoginInfo, accountNumber).setVisible(true);
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
        } else if (src == addAccountBtn) {
            new AccountEditorUI(userLoginInfo, null).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

}
