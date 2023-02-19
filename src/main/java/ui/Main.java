package ui;

import client.Client;
import entity.Admin;
import entity.Card;
import mapper.CardMapper;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**主界面类
 * 该类目前有三个功能：查询余额、存款、取款
 * 该类根据Login中返回的client.id生成，
 * 并通过取款和存款返回的int值对client对象对应的存款金额（数据库）进行修改
 * 注:调用存/取款子功能不需要任何参数，仅需要他们的返回值；查询功能需要传入余额（int）
 */
public class Main extends JDialog {
    private Client client;  // client对象
    private int id;
    private JPanel root;
    private JButton Button_inquiry;
    private JButton Button_deposit;
    private JButton Button_exit;
    private JButton Button_withdrawal;
    private JTextField TextField;
    private JTextField depositResult;
    private JButton buttonOK;
    private JButton buttonCancel;


    public Main(int id) {
        setContentPane(root);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        root.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        Button_inquiry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onInquiry();
            }
        });
        Button_deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeposit();
            }
        });
        Button_withdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onWithdraw();
            }
        });
        Button_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }
    /**
     * 响应“查询余额”按钮
     * 点击即打开查询余额页面
    */
    private void onInquiry(){
        double recentDeposit = 999 ;
        ShowDeposit dialog = new ShowDeposit(recentDeposit);
        dialog.setSize(400,250);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }

    /**
     *响应“存款”按钮
     *点击即打开存款页面
     */
    private void onDeposit(){
        DepositMoney dialog = new DepositMoney();
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }
    /**
     *响应“取款”按钮
     *点击即打开取款页面
     */
    private void onWithdraw(){
        WithdrawMoney dialog = new WithdrawMoney();
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }
    /**
     * 响应关闭
     *
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
