package client;

import entity.Admin;
import entity.Card;
import entity.Record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static utils.ToEntity.*;

/**
 * 客户端通信类
 * 与服务端以TCP协议 通信
 * 发送给服务端String格式: [前三位操作码][数据]
 * 接收服务端的boolean/String
 */
public class Client implements ClientRequest {
    private final Socket client;
    private final DataInputStream in;
    private final DataOutputStream out;

    /**
     * 构造方法
     * @param serverName 服务端ip地址
     * @param port 服务端端口号
     * @throws IOException
     */
    public Client(String serverName, int port) throws IOException {
        client = new Socket(serverName, port);
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
        System.out.println("客户端已启动: " + client.getLocalSocketAddress());
        System.out.println("连接对象: " + client.getRemoteSocketAddress());
    }

    /**
     * @return 返回客户端是否与服务端相连
     */
    public boolean isConnected() {
        return client.isConnected();
    }

    /**
     * 登出
     */
    @Override
    public void logout() throws IOException {
        out.writeUTF("000");
    }

    /**
     * 请求登录[普通用户]
     * 发送001操作码 + card信息到server
     * @param id 与 password
     * @return 登陆是否成功
     */
    @Override
    public boolean login(int id, String password) throws IOException {
        Card card = new Card(id, password);
        out.writeUTF("001" + card);  // 传输card.id card.password 给Server
        return in.readBoolean();        // 得到是否登陆成功作为返回值
    }

    /**
     * 请求登录[管理员]
     * 发送002操作码 + admin信息到server
     * @param id 与 password
     * @return 登陆是否成功
     */
    @Override
    public boolean loginAdmin(int id, String password) throws IOException {
        Admin admin = new Admin(id, password);
        out.writeUTF("002" + admin);  // 传输admin.id admin.password 给Server
        return in.readBoolean();        // 得到是否登陆成功作为返回值
    }

    /**
     * 通过卡号id 查寻卡的详细信息
     * 发送003操作码 + card.id到server
     * @param id
     * @return 卡号详细信息
     */
    @Override
    public Card getDetailedInfo(int id) throws IOException {
        Card card = new Card(id);
        out.writeUTF("003" + card);  // 传输card.id
        return toCard(in.readUTF());    // 返回详细信息
    }

    /**
     * 转账/存款/取款
     * @param fromId 资金流出方卡号 [如果是存款 此项为0]
     * @param toId 资金流入方卡号 [如果是取款 此项为0]
     * @param money 转账/存款/取款金额
     * @return 转账/存款/取款是否成功
     */
    // TODO: 服务端还没建
    @Override
    public boolean transfer(int fromId, int toId, double money) throws IOException {
        Card from = new Card(fromId);
        from.setMoney(money);
        Card to = new Card(toId);
        to.setMoney(money);
        List<Card> cards = new ArrayList<>();
        cards.add(from);
        cards.add(to);
        out.writeUTF("004" + cards);
        return in.readBoolean();    //返回是否转账成功
    }

    /**
     * 查询转账记录
     * @param id 卡号
     * @return 包含此卡号的转账记录
     */
    // TODO: 服务端还没建
    @Override
    public List<Record> getTransferRecords(int id) throws IOException {
        Card card = new Card(id);
        out.writeUTF("005" + card);
        return toRecordList(in.readUTF());  // 返回转账记录列表
    }

    /**
     * 注册
     * @param password 密码
     * @param bank 所属银行
     * @param uid 用户id
     * @param userInform 用户其他信息
     * @return 是否注册成功
     */
    // TODO: 服务端还没建
    @Override
    public boolean register(int id, String password, String bank, long uid, String userInform) throws IOException {
        Card card = new Card(id, password, 0, bank, uid, userInform, 1);
        out.writeUTF("006" + card);
        return in.readBoolean();
    }

    /**
     * 修改密码
     * @param id 卡号
     * @param password 新密码
     * @return 更改密码是否成功
     */
    // TODO: 服务端还没建
    @Override
    public boolean updatePassword(int id, String password) throws IOException {
        Card card = new Card(id, password);
        out.writeUTF("007" + card);
        return in.readBoolean();
    }

    /**
     * 删除卡
     * @param id 卡号
     * @return 删除成功 (在卡余额为0时可行)
     */
    // TODO: 服务端还没建
    @Override
    public boolean deleteCard(int id) throws IOException {
        Card card = new Card(id);
        out.writeUTF("008" + card);
        return in.readBoolean();
    }

    /**
     * [管理员] 获取所有卡的信息
     * @return 返回所有卡的信息
     */
    // TODO: 服务端还没建
    @Override
    public List<Card> getCardList() throws IOException {
        out.writeUTF("009");
        return toCardList(in.readUTF());
    }

    /**
     * [管理员] 更新卡的所属银行
     * @param id 卡号
     * @param bank 新的银行
     * @return 是否更新成功
     */
    @Override
    public boolean updateBank(int id, String bank) throws IOException {
        Card card = new Card(id);
        card.setBank(bank);
        out.writeUTF("010" + card);
        return in.readBoolean();
    }

    /**
     * [管理员] 更新卡的状态 0:禁用 1:删除
     * @param id 卡号
     * @param status 状态
     */
    @Override
    public boolean updateStatus(int id,int status) throws IOException {
        Card card = new Card(id);
        card.setStatus(status);
        out.writeUTF("011" + card);
        return in.readBoolean();
    }

// TODO: 追加其他功能?

}
