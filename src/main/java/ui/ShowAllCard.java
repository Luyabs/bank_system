package ui;

import entity.Card;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
public class ShowAllCard extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    public ShowAllCard(List<Card> AllCard) {
        setContentPane(contentPane);
        setModal(true);
        Integer id,status;
        double money;   //流动金额
        String bank,name;
        long uid;
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        tableModel.addColumn("账号");
        tableModel.addColumn("存款");
        tableModel.addColumn("银行");
        tableModel.addColumn("姓名");
        tableModel.addColumn("身份证号");
        tableModel.addColumn("卡状态");
        for (Card card : AllCard) {
            id = card.getId();
            money = card.getMoney();
            bank = card.getBank();
            name = card.getUserInform();
            uid = card.getUid();
            status = card.getStatus();
            tableModel.addRow(new Object[]{String.valueOf(id), String.valueOf(money), String.valueOf(bank),
                    String.valueOf(name), String.valueOf(uid), String.valueOf(status)});
        }
    }


}
