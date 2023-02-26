package client;

import entity.Card;
import entity.Record;

import java.io.IOException;
import java.util.List;

public interface ClientRequest {
    void logout() throws IOException;  // 客户端登出

    boolean login(int id, String password) throws IOException;    //发起登录请求[用户] 返回是否以用户身份登陆成功

    boolean loginAdmin(int id, String password) throws IOException;    //发起登录请求[管理员] 返回是否以管理员身份登陆成功

    Card getDetailedInfo(int id) throws IOException;     //通过卡号id 查寻卡的详细信息[可以查到Card类中除密码外的所有项; 需要传入card.id]

    // TODO: Week08 基本需求2,3
    List<Record> getTransferRecords(int id) throws IOException;    //通过卡号id 查询卡的转账记录

    boolean transfer(int fromId, int toId, double money) throws IOException;     //转账/存款/取款 从from卡号转到to卡号money

    // TODO: Week09 基本需求4,5,6 + 追加需求1,2,3
    boolean register(int id, String password, String bank, long uid, String userInform) throws IOException;    //注册 需提供密码 所属银行 用户id 用户信息 返回是否成功(id重复代表注册失败)

    boolean updatePassword(int id, String password) throws IOException;     //通过卡号id 更新卡的密码 返回是否成功

    boolean deleteCard(int id) throws IOException;       // 注销账户 返回是否注销成功 (余额不为0的情况下将返回false)

    List<Card> getCardList() throws IOException;           //管理员: 获取所有卡的所有详细信息

    boolean updateBank(int id, String bank) throws IOException;     //管理员: 更改用户卡所属银行

    boolean updateStatus(int id,int status) throws IOException;    //管理员: 更改用户卡的状态 0:冻结 1:正常

    // TODO: 追加其他功能？
}
