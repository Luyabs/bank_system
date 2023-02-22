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
        String[] columnNames = {"序号", "姓名", "语文", "数学", "英语", "总分"};
        Object[][] rowData = {
                {1, "张三", 80, 80, 80, 240},
                {2, "John", 70, 80, 90, 240},
                {3, "Sue", 70, 70, 70, 210},
                {4, "Jane", 80, 70, 60, 210},
                {5, "Joe_05", 80, 70, 60, 210},
                {6, "Joe_06", 80, 70, 60, 210},
                {7, "Joe_07", 80, 70, 60, 210},
                {8, "Joe_08", 80, 70, 60, 210},
                {9, "Joe_09", 80, 70, 60, 210},
                {10, "Joe_10", 80, 70, 60, 210},
                {11, "Joe_11", 80, 70, 60, 210},
                {12, "Joe_12", 80, 70, 60, 210},
                {13, "Joe_13", 80, 70, 60, 210},
                {14, "Joe_14", 80, 70, 60, 210},
                {15, "Joe_15", 80, 70, 60, 210},
                {16, "Joe_16", 80, 70, 60, 210},
                {17, "Joe_17", 80, 70, 60, 210},
                {18, "Joe_18", 80, 70, 60, 210},
                {19, "Joe_19", 80, 70, 60, 210},
                {20, "Joe_20", 80, 70, 60, 210}
        };
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
