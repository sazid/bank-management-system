package m6.ui;

import m6.UserLoginInfo;
import m6.components.StyledButton;
import m6.table_model.CustomerTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomerViewerUI extends UserBaseUI implements ListSelectionListener {

    private UserLoginInfo userLoginInfo;

    private StyledButton addCustomerBtn;
    private JTable table;
    private JScrollPane scrollPane;

    public CustomerViewerUI(UserLoginInfo userLoginInfo) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;

        setPageTitle("View Customers");
        backButton.setVisible(true);
        backButton.addActionListener(this);

        initUI();
        bind();
    }

    private void initUI() {
        table = new JTable(new CustomerTableModel(this));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 115, 775, 345);

        addCustomerBtn = new StyledButton("Add Customer");
        addCustomerBtn.setBounds(10, 72, 120, 35);
        addCustomerBtn.setBackground(new Color(0xDCBD7E));

        mainPanel.add(addCustomerBtn);

        mainPanel.add(scrollPane);
    }

    private void bind() {
        addCustomerBtn.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);
    }

    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (!e.getValueIsAdjusting()) {
            String username = table.getModel().getValueAt(lsm.getLeadSelectionIndex(), 1).toString();

            new CustomerEditorUI(userLoginInfo, username).setVisible(true);
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
        } else if (src == addCustomerBtn) {
            new CustomerEditorUI(userLoginInfo, null).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

}
