package ui;
import client.Client;
import entity.Admin;
import entity.Card;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

import entity.Record;
public class Inquire1 extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton button_depsit;
    private JButton button_record;
    private Card card;
    List<Record> cardrecord;

    public Inquire1(Card card1, List<Record> cardrecord1) {
        card = card1;
        cardrecord = cardrecord1;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
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
        button_depsit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onInquiryDeposit();
            }
        });
        button_record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onInquiryRecord();
            }
        });
    }

    private void onInquiryDeposit() {
        double recentDeposit = 0;
        recentDeposit = card.getMoney();
        ShowDeposit dialog = new ShowDeposit(recentDeposit);
        SetPosition.setFrameCenter(dialog);
        dialog.setSize(400,250);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);

    }

    private void onInquiryRecord() {
        ShowRecord2 frame = new ShowRecord2(cardrecord);
        SetPosition.setFrameCenter(frame);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        //frame.dispose();

    }
    private void onCancel(){
        dispose();
    }

    /*public static void main(String[] args) {
        Inquire1 dialog = new Inquire1();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
