package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 取款界面类：
 * 生成该类对象无需任何参数：该类仅返回一个合法的正整数
 * 根据返回值（取款数目）在Main类中进行数据库删改
 */
public class WithdrawMoney extends JDialog {
    private JPanel panel1;
    private JButton Button_OK;
    private JTextField textField1;
    private JLabel information;
    Integer INT_money;              //取款的金额：输入异常时INT_money默认为0

    WithdrawMoney() {
        setContentPane(panel1);
        setModal(true);
        getRootPane().setDefaultButton(Button_OK);
        textField1.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中央
        Button_OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IfinputLegal();
            }
        });
    }
    void IfinputLegal(){
        INT_money = 0;
        String STR_money = textField1.getText();
        //判断输入数据为非负整数;
        Matcher M_money = Pattern.compile("^[+-]?[0-9]+$").matcher(STR_money);
        if (M_money.find()) {
            INT_money = Integer.parseInt(STR_money);
            JOptionPane.showMessageDialog(null, "输入成功！");
            //dispose();
            this.setVisible(false);//输入成功后暂时不释放资源：不可见
        } else {
            JOptionPane.showMessageDialog(null, "输入数据不合法！");
        }
    }
    Integer returnAddMoney () {
        return INT_money;
    }

    public static void main(String Args[]){
        WithdrawMoney dialog = new WithdrawMoney();
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        System.exit(0);
    }








}
