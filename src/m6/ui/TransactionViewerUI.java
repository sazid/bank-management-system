package m6.ui;

import m6.UserLoginInfo;
import m6.table_model.TranscationTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TransactionViewerUI extends UserBaseUI {

    private UserLoginInfo userLoginInfo;
    private String username;

    private JTable table;
    private JScrollPane scrollPane;

    public TransactionViewerUI(UserLoginInfo userLoginInfo, String username) {
        super(userLoginInfo);
        this.userLoginInfo = userLoginInfo;
        this.username = username;

        setPageTitle("View Transactions | " + username);
        backButton.setVisible(true);
        backButton.addActionListener(this);

        initUI();
    }

    private void initUI() {
        table = new JTable(new TranscationTableModel(this, username));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 75, 765, 375);

        mainPanel.add(scrollPane);
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Object src = e.getSource();

        if (src == backButton) {
            new CustomerViewerUI(userLoginInfo).setVisible(true);
            setVisible(false);
            dispose();
        }
    }

}
