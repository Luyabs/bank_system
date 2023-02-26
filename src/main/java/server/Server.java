package server;

import entity.Admin;
import entity.Card;
import entity.Record;
import mapper.AdminMapper;
import mapper.CardMapper;
import mapper.RecordMapper;
import utils.ToEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 服务端通信类
 * 与客户端以TCP协议 通信
 * 接收来自客户端的String格式: [前三位操作码][数据]
 * 返回给客户端boolean/String
 */
public class Server implements ServerResponse {
    private final CardMapper cardMapper = new CardMapper();
    private final RecordMapper recordMapper = new RecordMapper();
    private final AdminMapper adminMapper = new AdminMapper();
    private final ServerSocket server;
    private DataInputStream in;
    private DataOutputStream out;

    /**
     * 构造方法
     *
     * @param port 服务端自身的端口号
     */
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("服务端已启动: " + server.getLocalSocketAddress());


        while (true) {
            Socket client = server.accept();    //接收client连接
            System.out.println("连接对象: " + client.getRemoteSocketAddress());
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            communicateWithClient();    //对该client启动通信服务
        }
    }

    /**
     * 当客户端未离线时 服务端一直准备接收来自客户端的String
     * 客户端传来的String前三位为操作位，以此来表示不同的数据访问请求
     * 服务端根据操作位执行对应的数据访问，并返回响应结果
     */
    public void communicateWithClient() throws IOException {
        boolean end = false;
        try {
            while (!end) {
                String request = in.readUTF();      //读取请求 并切分操作与数据
                String operation = request.substring(0, 3);
                String data = request.substring(3);

                System.out.println("operation: " + operation + "  data: " + data);

                switch (operation) {
                    case "000" : end = true;   //客户端登出
                        break;
                    case "001" : out.writeBoolean(checkLogin(data)); //用户登录 - 返回登陆是否成功
                        break;
                    case "002" : out.writeBoolean(checkLoginAdmin(data)); //管理员登录 - 返回登陆是否成功
                        break;
                    case "003" : out.writeUTF(searchDetailedInfo(data)); //用户查询详细信息 - 返回card的详细信息.
                        break;
                    case "004" : out.writeBoolean(transfer(data)); //转账/存款/取款 - 返回是否转账成功
                        break;
                    case "005" : out.writeUTF(searchTransferRecords(data).toString()); //查询卡的转账记录 - 返回List<Record>
                        break;
                    case "006" : out.writeBoolean(register(data)); //注册新卡 - 返回注册是否成功
                        break;
                    case "007" : out.writeBoolean(updatePassword(data)); //更改密码 - 返回更改是否成功
                        break;
                    case "008" : out.writeBoolean(deleteCard(data)); //销毁卡 - 返回销毁是否成功
                        break;
                    case "009" : out.writeUTF(getCardList().toString()); //查询所有卡的信息 - 返回所有卡的信息
                        break;
                    case "010" : out.writeBoolean(updateBank(data)); //更新卡的所属银行 - 返回是否更新成功
                        break;
                    case "011" : out.writeBoolean(updateStatus(data)); //更新卡的状态 - 返回是否更新成功
                        break;

                    //TODO: 追加其他功能?

                    default : System.out.println("未知操作码");
                        break;
                }
            }
        } catch (SocketException ex) {
            System.out.println("当前连接的客户端已断开");
        }
    }

    /**
     * 检查登录[普通用户]
     * 读取登录信息 -> 通过mapper层方法 访问数据库 -> 返回登录结果
     *
     * @param data 有效内容为card.id card.password
     * @return 是否登陆成功
     */
    @Override
    public boolean checkLogin(String data) {
        //TODO: 密码加密
        Card card = ToEntity.toCard(data);   //先转化为实体类
        int id = card.getId();
        String password = card.getPassword();

        Card targetCard = cardMapper.selectPasswordById(id); // 查card表
        return targetCard != null && targetCard.getStatus() != 0 && targetCard.getPassword().equals(password);
    }

    /**
     * 检查登录[管理员]
     * 读取登录信息 -> 通过mapper层方法 访问数据库 -> 返回登录结果
     *
     * @param data 有效内容为admin.id admin.password
     * @return 是否登陆成功
     */
    @Override
    public boolean checkLoginAdmin(String data) {
        //TODO: 密码加密
        Admin admin = ToEntity.toAdmin(data);   //先转化为实体类
        int id = admin.getId();
        String password = admin.getPassword();

        Admin targetCardAdmin = adminMapper.selectPasswordById(id); // 查admin表
        return targetCardAdmin != null && targetCardAdmin.getPassword().equals(password);
    }

    /**
     * 通过卡号id 查寻卡的详细信息
     * 读取登录信息 -> 通过mapper层方法 访问数据库 -> 返回详细信息
     *
     * @param data 有效内容为card.id
     * @return 完整卡的信息
     */
    @Override
    public String searchDetailedInfo(String data) {
        Card card = ToEntity.toCard(data);
        int id = card.getId();
        return cardMapper.selectDetailedById(id).toString(); // 查card表
    }

    /**
     * 转账/存款/取款
     * 判断是否满足条件 + 向数据库写入记录
     * 注意存款时 资金流入方id == 0    取款时 资金流出方id == 0
     *
     * @param cards cards.get(0).getId()资金流出方; cards.get(1).getId()资金流入方; card.get(0或1).getMoney()流动金额
     * @return 是否成功
     */
    @Override
    public boolean transfer(String cards) {
        List<Card> cardList = ToEntity.toCardList(cards);
        //流出方id
        int id0 = cardList.get(0).getId();
        //流入方id
        int id1 = cardList.get(1).getId();
        //交易资金
        double money = cardList.get(0).getMoney();
        if (money < 0)
            return false;
        if (id0 == 0) {
            //存款
            if (!cardMapper.idCheck(id1))
                return false;
            cardMapper.setMoney(id1, money);
            return recordMapper.addRecord(0, id1, money, 1);
        } else if (id1 == 0) {
            //取款
            if (!cardMapper.idCheck(id0))
                return false;
            if (cardMapper.moneyCheck(id0, money)) {
                cardMapper.setMoney(id0, -money);
                return recordMapper.addRecord(id0, 0, money, 2);
            }
        } else {
            //转账
            if (!cardMapper.idCheck(id0))
                return false;
            if (!cardMapper.idCheck(id1))
                return false;
            System.out.println(id0 + " " + id1 + " " + money);
            if (cardMapper.moneyCheck(id0, money)) {
                cardMapper.setMoney(id0, -money);
                cardMapper.setMoney(id1, money);
                return recordMapper.addRecord(id0, id1, money, 0);
            }
        }
        return false;
    }

    /**
     * 查询有关该用户的收支记录
     *
     * @param card 有效内容为card.id
     * @return 包含该id的所有转账记录
     */
    @Override
    public List<Record> searchTransferRecords(String card) {
        Card _card = ToEntity.toCard(card);   //先转化为实体类
        int id = _card.getId();
        List<Record> records1 = recordMapper.selectRecordsByToId(id);    //查record表
        List<Record> records2 = recordMapper.selectRecordsByFromId(id);

        List<Record> records = new ArrayList<>();
        records.addAll(records1);
        records.addAll(records2);
        Collections.sort(records, (o1, o2) -> o2.getId() - o1.getId());
        return records;
    }

    /**
     * 通过card的完整信息进行注册
     * 需要判断卡号是否存在于数据库中
     *
     * @param data 含有完整信息(所有属性)的card
     * @return 注册是否成功
     */
    @Override
    public boolean register(String data) {
        Card card = ToEntity.toCard(data);
        int id = card.getId();
        String password = card.getPassword();
        String bank = card.getBank();
        long uid = card.getUid();
        String user_inform = card.getUserInform();
        if (cardMapper.idCheck(id))
            return false;
        return cardMapper.addCard(id, password, bank, uid, user_inform, 1);
    }

    /**
     * 将卡号=card.id的卡重新设置密码为password
     * 需要判断卡号是否存在于数据库中
     *
     * @param data 有效数据 card.id card.password
     * @return 是否更新成功
     */
    @Override
    public boolean updatePassword(String data) {
        Card card = ToEntity.toCard(data);
        int id = card.getId();
        String password = card.getPassword();

        if (!cardMapper.idCheck(id))
            return false;
        return cardMapper.setPassword(id,password);
    }

    /**
     * 将卡号=card.id的卡销毁
     * 需判断卡号是否存在于数据库中
     * 需判断卡号余额money是否为0
     *
     * @param data 有效数据仅为 card.id
     * @return 是否销毁成功
     */
    @Override
    public boolean deleteCard(String data) {
        Card card = ToEntity.toCard(data);
        int id=card.getId();

        if(!cardMapper.idCheck(id))
            return false;
        if(cardMapper.selectDetailedById(id).getMoney()!=0)
            return false;
        return cardMapper.deleteCard(id);
    }

    /**
     * 获取card表中所有数据
     *
     * @return List<Card>
     */
    @Override
    public List<Card> getCardList() {
        List<Card> list=cardMapper.selectAllCard();
        return list;
    }

    /**
     * 将卡号=card.id的卡更新所属银行
     * 需判断卡号是否存在于数据库中
     *
     * @param data 有效数据 card.id card.bank
     * @return 是否更新成功
     */
    @Override
    public boolean updateBank(String data) {
        Card card = ToEntity.toCard(data);
        int id=card.getId();
        if(!cardMapper.idCheck(id))
            return false;
        return cardMapper.setBank(card.getId(), card.getBank());
    }

    /**
     * 将卡号=card.id的卡更新状态
     * 需判断卡号是否存在于数据库中
     * 注意status = 1 有效 = 0冻结
     *
     * @param data 有效数据 card.id card.status
     * @return 是否更新成功
     */
    @Override
    public boolean updateStatus(String data) {
        Card card = ToEntity.toCard(data);
        int id=card.getId();
        int status = 0;
        if(!cardMapper.idCheck(id))
            return false;
        return cardMapper.setStatus(card.getId(), status);
    }


    // TODO: 追加其他功能？

}
