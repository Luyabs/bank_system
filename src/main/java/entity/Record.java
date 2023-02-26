package entity;

/**
 * 实体类: 交易记录
 * 如果需要把String类型的record还原成Record类对象 请使用utils.ToEntity.toRecord()
 */
public class Record {
    private int id;     //交易id

    private int from_id;    //金额输出方 卡号 (如果是存款此项为0)

    private int to_id;    //金额流入方 卡号 (如果是取款此项为0)

    private double money;   //流动金额

    private int type;   //类型 0:转账 1:存款 2:取款

    private String time; //交易时间 e.g. 2023-02-06

    public Record() {
    }

    public Record(int id, int from_id, int to_id, double money, int type, String time) {
        this.id = id;
        this.from_id = from_id;
        this.to_id = to_id;
        this.money = money;
        this.type = type;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", from_id=" + from_id +
                ", to_id=" + to_id +
                ", money=" + money +
                ", type=" + type +
                ", time=" + time +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
