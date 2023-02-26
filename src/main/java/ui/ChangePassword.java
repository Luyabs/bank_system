package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends JDialog{
    private JPanel panel1;
    private JPasswordField passwordField_newagain;
    private JPasswordField passwordField_new;
    private JTextField passwordField_old;
    private JButton Button_OK;
    private JButton Button_Cancel;
    private String newPassword1,newPassword2;
    private String oldPasswordInput;
    private String oldPasswordCorrect;

    ChangePassword(String Password) {
        oldPasswordCorrect = Password;
        setContentPane(panel1);
        setModal(true);
        getRootPane().setDefaultButton(Button_OK);
        passwordField_old.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中央
        passwordField_new.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中央
        passwordField_newagain.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中
        newPassword1 = oldPasswordCorrect; //新密码默认为旧密码
        Button_OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnOK();
            }
        });
        Button_Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

    }
    private void OnOK(){
        oldPasswordInput = passwordField_old.getText();
        newPassword1 = String.valueOf(passwordField_new.getPassword());
        newPassword2 = String.valueOf(passwordField_newagain.getPassword());
        if(!oldPasswordInput.equals(oldPasswordCorrect)){   //原密码错误
            JOptionPane.showMessageDialog(null, "原密码错误！");
        }
        else{                                               //原密码正确
            Matcher M_newpassword = Pattern.compile("[0-9a-zA-Z]{6}").matcher(newPassword1);
            //如果新密码符合数字加英文字母的形式且为6位，则输入成功
            if(M_newpassword.matches()&&newPassword1.equals(newPassword2)){
                JOptionPane.showMessageDialog(null, "输入成功！");
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "输入不合法或新密码不一致！");
                newPassword1 = oldPasswordCorrect;
            }
        }
    }
    private void onCancel() {
        //关闭当前界面
        dispose();
    }
    public String returnNewPassword(){
        return newPassword1;
    }
}
