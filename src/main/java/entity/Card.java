package entity;

/**
 * 实体类: 银行卡
 * 如果需要把String类型的card还原成Card类 请使用utils.ToEntity.toCard()
 */
public class Card {
    private int id; //卡号 (8位)

    private String password;    //密码

    private double money;   //金额

    private String bank;    //所属银行

    private long uid;    //所属用户id (18位)

    private String userInform;  //所属用户其他信息

    private int status; //状态: 0:冻结 1:正常

    public Card() {
    }

    public Card(int id) {
        this.id = id;
    }

    public Card(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public Card(int id, String password, double money, String bank, long uid, String userInform, int status) {
        this.id = id;
        this.password = password;
        this.money = money;
        this.bank = bank;
        this.uid = uid;
        this.userInform = userInform;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", bank='" + bank + '\'' +
                ", uid=" + uid +
                ", userInform='" + userInform + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserInform() {
        return userInform;
    }

    public void setUserInform(String userInform) {
        this.userInform = userInform;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
