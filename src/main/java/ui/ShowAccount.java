package ui;

import client.Client;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ShowAccount extends JDialog{
    private Client client;  // client对象
    boolean ifLogOut;
    private JPanel panel1;
    private JButton Button_logout;
    private JButton Button_password;
    private String oldPassword;
    private String newPassword;
    private double money;
    ShowAccount(String Password ,double money) {
        ifLogOut = false;       //默认为不注销
        oldPassword = Password;
        newPassword = Password; //新密码默认为旧密码
        setContentPane(panel1);
        setModal(true);
        Button_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        Button_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOutAccount();
            }
        });
    }
    public void changePassword(){
        ChangePassword dialog = new ChangePassword(oldPassword);//传递密码作为参数
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        newPassword = dialog.returnNewPassword();   //得到新密码
    }
    public void logOutAccount(){
        if(money != 0)
            JOptionPane.showMessageDialog(null, "当前金额不为0，无法注销！");
        else{
            ifLogOut = true;
            dispose();
        }
    }
    public boolean returnIfLogOut(){
        return ifLogOut;
    }
    public String returnNewPassword(){
        return newPassword;
    }

    /*public static void main (String[]args){
        Integer id = 12345678;
        ShowAccount dialog = new ShowAccount(id);
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        System.exit(0);
    }*/
}
