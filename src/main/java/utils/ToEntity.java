package utils;

import entity.Admin;
import entity.Card;
import entity.Record;

import java.util.List;


/**
 * 工具类：根据向导生成的toString方法 将String还原成实体类的成员序列
 */
public class ToEntity {

    public static Card toCard(String str) {
        return (Card) BaseToEntity.toEntity(Card.class, str);
    }

    public static List<Card> toCardList(String str) {
        return BaseToEntity.toEntityList(Card.class, str);
    }

    public static Record toRecord(String str) {
        return (Record) BaseToEntity.toEntity(Record.class, str);
    }

    public static List<Record> toRecordList(String str) {
        return BaseToEntity.toEntityList(Record.class, str);
    }

    public static Admin toAdmin(String str) {
        return (Admin) BaseToEntity.toEntity(Admin.class, str);
    }

    public static List<Admin> toAdminList(String str) {
        return BaseToEntity.toEntityList(Admin.class, str);
    }

    public static void main(String[] args) {
        System.out.println(toCard("Card{id=1, password='ab', money=2.0, bank='null', uid=0, userInform='null', status=0}"));
        List<Card> cards = toCardList("[Card{id=0, password='tytyyyutgh', money=0.0, bank='null', uid=0, userInform='null', status=0}, Card{id=1, password='null', money=0.0, bank='null', uid=124214, userInform='null', status=0}]");
        System.out.println(cards);
        System.out.println(cards.getClass());

        Admin admin = new Admin(1, "123", 1);
        System.out.println(toAdmin("Admin{id=1, password='123', status=1}"));
    }

}
