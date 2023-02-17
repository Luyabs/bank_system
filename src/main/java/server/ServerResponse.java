package server;

import entity.Card;
import entity.Record;

import java.util.List;

public interface ServerResponse {
    boolean checkLogin(String card);    // 检查登录[普通用户]
    boolean checkLoginAdmin(String admin);    // 检查登录[管理员]
    String searchDetailedInfo(String card);    // 通过卡号id 查寻卡的详细信息

    // TODO: Week08 基本需求2,3
    boolean transfer(String cards);    // 通过cards进行转账 其中card[0].id是金额流出方 card[1].id是金额流入方 card[0].money是金额变化量

    List<Record> searchTransferRecords(String card);    // 通过卡号id 查询卡的转账记录

    // TODO: Week09 基本需求4,5,6 + 追加需求1,2,3
    boolean register(String card);  // 通过card的完整信息进行注册，需要判断卡号是否存在于数据库中

    boolean updatePassword(String card);   // 将卡号=card.id的卡重新设置密码为password

    boolean deleteCard(String card);    // 将卡号=card.id的卡从数据库注销 只有余额非空的卡才能被注销

    List<Card> getCardList();   // 返回所有card

    boolean updateBank(String card);    // 将卡号=card.id的卡更新bank属性

    boolean updateStatus(String card);  // 将卡号=card.id的卡更新status属性

    // TODO: 追加其他功能？
}
