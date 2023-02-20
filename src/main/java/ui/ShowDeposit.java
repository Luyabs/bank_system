package ui;

import javax.swing.*;
import client.Client;
import entity.Admin;
import entity.Card;
import mapper.CardMapper;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ShowDeposit extends JDialog {
    private JPanel panel1;
    private JButton button_OK;
    private JTextField textField1;
    private JLabel information;

    ShowDeposit(double deposit){
        setContentPane(panel1);
        setModal(true);
        getRootPane().setDefaultButton(button_OK);
        textField1.setHorizontalAlignment(JTextField.CENTER);//文本出现在文本框中央
        textField1.setText(String.valueOf(deposit));
        button_OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnOK();
            }
        });
    }
    void OnOK(){
        dispose();
        //this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING) );
    }
    /*public static void main(String[] args) {
        double deposit = 1.1;
        ShowDeposit dialog = new ShowDeposit(deposit);
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        System.exit(0);
    }*/
}
