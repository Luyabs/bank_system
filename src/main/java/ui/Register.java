package ui;

import javax.swing.*;
import java.util.Arrays;

public class Register extends JDialog {
    private JPanel contentPane;
    private JTextField textField1;
    private JPasswordField textField6;
    private JTextField textField4;
    private JPasswordField textField5;
    private JTextField textField2;
    private JButton buttonOK;
    private JTextField textField3;
    private int id;
    private String password;
    private String bank;
    private long uid;
    private String userInform;
    public Register() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
        try {
            String t1, t2, t3, t4;
            char[] t5, t6;
            t1 = textField1.getText();
            t2 = textField2.getText();
            t3 = textField3.getText();
            t4 = textField4.getText();
            t5 = textField5.getPassword();
            t6 = textField6.getPassword();
            id = Integer.parseInt(t1);
            userInform = t2;
            uid = Long.parseLong(t3);
            bank = t4;
            if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty() || t4.isEmpty() || t5.length == 0 || t6.length == 0) {
                JOptionPane.showMessageDialog(null, "输入不能为空！");
            } else {
                if (!Arrays.equals(t5, t6))
                    JOptionPane.showMessageDialog(null, "两次输入密码不一致！");
                else {
                    password = String.valueOf(t5);
                    dispose();
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "输入不能为字符串！");
        }
    }

    public Integer returnId(){ return id; }
    public String returnPassword(){return password;}
    public String returnBank(){return bank;}
    public long returnUid(){return uid;}
    public String returnUserInform(){return userInform;}

    /*public static void main(String[] args) {
        Register dialog = new Register();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
