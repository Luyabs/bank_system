package ui;

import client.Client;
import entity.Card;

import javax.management.ObjectName;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class AdminMain extends JDialog {
    private JPanel contentPane;
    private JButton ButtonChangeStatus;
    private JButton buttonInquiryAllCard;
    private JButton buttonChangeBnak;
    private JButton buttonOK;
    private JButton buttonCancel;
    private Integer id;
    private Client client;

    public AdminMain(int id, Client client) {
        this.id = id;
        this.client = client;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 单击十字时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonInquiryAllCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnInquiry();
            }
        });
        buttonChangeBnak.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnChangeBank();
            }
        });
        ButtonChangeStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OnChangeStatus();
            }
        });
    }

    /***
     * 查询所有卡信息
     */
    private void OnInquiry(){
        List<Card> ShowAllCard = null;
        try {
            ShowAllCard = client.getCardList();
            ShowAllCard frame = new ShowAllCard(ShowAllCard);
            SetPosition.setFrameCenter(frame);
            frame.pack();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 更改卡所属银行
     */
    private void OnChangeBank(){

        try {
            AdminChangeBank frame = new AdminChangeBank();
            SetPosition.setFrameCenter(frame);
            frame.pack();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
            Integer clientId = frame.getID();
            String bankName = frame.getBankName();
            if(client.updateBank(clientId,bankName))
                JOptionPane.showMessageDialog(null, "修改成功！");
            else
                JOptionPane.showMessageDialog(null, "修改失败！");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * 更改卡状态
     */
    private void OnChangeStatus(){


        try {

            AdminChangeStatus frame = new AdminChangeStatus();
            SetPosition.setFrameCenter(frame);
            frame.pack();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
            Integer ifCardExisted = 0;                  //判断卡是否存在
            Integer clientid = frame.getId(),status = 0;
            List<Card> ShowAllCard = null;
            ShowAllCard = client.getCardList();
            for(Card card :ShowAllCard){
                if(card.getId() == clientid){
                    ifCardExisted = 1;
                    break;
                }
            }
            if(ifCardExisted == 1){
                if(client.getDetailedInfo(clientid).getStatus() == 1)
                    status = 0;
                else if(client.getDetailedInfo(clientid).getStatus() == 0)
                    status = 1;
                else
                    JOptionPane.showMessageDialog(null, "卡状态异常！");

                if(client.updateStatus(clientid,status))
                    JOptionPane.showMessageDialog(null, "修改成功！");
                else
                    JOptionPane.showMessageDialog(null, "修改失败！");
            }
            else
                JOptionPane.showMessageDialog(null, "输入卡号不存在！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

}
