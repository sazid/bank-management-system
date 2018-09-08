package m6.ui;

import m6.UserLoginInfo;
import m6.components.StyledButton;
import m6.table_model.EmployeeTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EmployeeViewerUI extends UserBaseUI implements ListSelectionListener {

    private UserLoginInfo userLoginInfo;

    private StyledButton addEmployeeBtn;
    private JTable table;
    private JScrollPane scrollPane;

    public EmployeeViewerUI(UserLoginInfo userLoginInfo) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;

        setPageTitle("View Employees");
        backButton.setVisible(true);
        backButton.addActionListener(this);

        initUI();
        bind();
    }

    private void initUI() {
        table = new JTable(new EmployeeTableModel(this));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 115, 775, 345);

        addEmployeeBtn = new StyledButton("Add Employee");
        addEmployeeBtn.setBounds(10, 72, 120, 35);
        addEmployeeBtn.setBackground(new Color(0xDCBD7E));

        mainPanel.add(addEmployeeBtn);

        mainPanel.add(scrollPane);
    }

    private void bind() {
        addEmployeeBtn.addActionListener(this);
        table.getSelectionModel().addListSelectionListener(this);
    }

    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if (!e.getValueIsAdjusting()) {
            String username = table.getModel().getValueAt(lsm.getLeadSelectionIndex(), 1).toString();

            new EmployeeEditorUI(userLoginInfo, username).setVisible(true);
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
        } else if (src == addEmployeeBtn) {
            new EmployeeEditorUI(userLoginInfo, null).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

}
