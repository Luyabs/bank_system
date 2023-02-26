package ui;

import entity.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.List;

public class ShowRecord2 extends JDialog{
    private JPanel panel1;
    private JTable table1;
    private JScrollPane JPanel2;
    public ShowRecord2(List<Record> CardRecord){
        setContentPane(panel1);
        setModal(true);
        Integer id;
        int from_id;    //金额输出方 卡号 (如果是存款此项为0)
        int to_id;    //金额流入方 卡号 (如果是取款此项为0)
        double money;   //流动金额
        int type;   //类型 0:转账 1:存款 2:取款
        String time;
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        tableModel.addColumn("id");
        tableModel.addColumn("from_id");
        tableModel.addColumn("to_id");
        tableModel.addColumn("金额");
        tableModel.addColumn("状态");
        tableModel.addColumn("时间");
        for (Record record : CardRecord) {
            id = record.getId();
            from_id = record.getFrom_id();
            to_id = record.getTo_id();
            money = record.getMoney();
            type = record.getType();
            time = record.getTime();
            tableModel.addRow(new Object[]{String.valueOf(id), String.valueOf(from_id), String.valueOf(to_id),
                    String.valueOf(money), String.valueOf(type), time});
        }
    }
}
