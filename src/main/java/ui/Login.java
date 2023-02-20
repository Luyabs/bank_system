package ui;

import client.Client;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.SocketException;

public class Login extends JDialog {
    private Client client;  // client对象

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cardId;
    private JPasswordField cardPassword;
    private JLabel textId;
    private JLabel textPassword;
    private JTextField loginResult;
    private JLabel textResult;

    public Login() throws IOException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 点击 X 时调用 onCancel()
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

    private void onLogin(){
        //登录
        try {
            if (client == null || !client.isConnected())    // 当未连接服务端时 进行连接
                client = new Client("localhost", 6789);
            if (client.login(Integer.parseInt(cardId.getText()), String.valueOf(cardPassword.getPassword())))
            {
                loginResult.setText("用户登陆成功");
                Main dialog = new Main(Integer.parseInt(cardId.getText()), client);
                SetPosition.setFrameCenter(dialog);
                dialog.setSize(400,250);
                dialog.pack();
                dialog.setVisible(true);
                dialog.setAlwaysOnTop(true);
            }
            else if (client.loginAdmin(Integer.parseInt(cardId.getText()), String.valueOf(cardPassword.getPassword())))
                loginResult.setText("管理员登陆成功");
            else
                 loginResult.setText("账号或密码错误");
        } catch (NumberFormatException e) {
            loginResult.setText("请输入整型id");
        } catch (SocketException e) {
            loginResult.setText("服务端出现异常或未启动");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        //关闭当前界面
        dispose();
    }

    public static void main(String[] args) throws IOException {
        Login dialog = new Login();
        SetPosition.setFrameCenter(dialog);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
