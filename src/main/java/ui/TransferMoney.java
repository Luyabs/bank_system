package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransferMoney extends JDialog{
    private JPanel panel1;
    private JButton button_OK;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel Toid;
    Integer toid;
    Integer toMoney;

    TransferMoney() {
        setContentPane(panel1);
        setModal(true);
        getRootPane().setDefaultButton(button_OK);
        textField1.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中央
        textField2.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中央
        button_OK.addActionListener(e -> OnOK());
    }
    void OnOK(){
        try {
            String STR_toid = textField1.getText();
            String STR_tomoney = textField2.getText();
            Matcher M_ID = Pattern.compile("\\d*").matcher(STR_toid);
            Matcher M_MONEY = Pattern.compile("^[1-9]\\d*$").matcher(STR_tomoney);
            if (M_ID.find() && M_MONEY.find()) {
                toid = Integer.parseInt(STR_toid);
                toMoney = Integer.parseInt(STR_tomoney);
                JOptionPane.showMessageDialog(null, "输入成功！");
                this.setVisible(false);//输入成功后暂时不释放资源：不可见
            } else
                JOptionPane.showMessageDialog(null, "输入失败！");
            //this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING) );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "不能输入字符串! ");
        }
    }

    Integer ReturnToid(){return toid;}
    Integer Returntomoney(){return toMoney;}
    /*
    * public static void main(String args[]){
        TransferMoney dialog = new TransferMoney();
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        System.exit(0);
    }*/
}
