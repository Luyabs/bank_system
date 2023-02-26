package ui;

import javax.swing.*;
import java.awt.event.*;

public class AdminChangeBank extends JDialog {
        private JPanel contentPane;
        private JButton buttonOK;
        private JButton buttonCancel;
        private JTextField textField1;
        private JTextField textField2;
        private Integer id;
        private String bankName;
        public AdminChangeBank() {
            id = 0;
            bankName = " 0 ";
            setContentPane(contentPane);
            setModal(true);
            getRootPane().setDefaultButton(buttonOK);

            buttonOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onOK();
                }
            });

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
        }

        private void onOK() {
            id = Integer.valueOf(textField1.getText());
            bankName = textField2.getText();
            dispose();
        }

        private void onCancel() {
            // 必要时在此处添加您的代码
            dispose();
        }
        public Integer getID(){return id;}
        public String getBankName(){return bankName;}

}
