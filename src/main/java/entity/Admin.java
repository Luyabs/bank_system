package entity;

/**
 * 实体类: 管理员
 * 如果需要把String类型的admin还原成Admin类对象 请使用utils.ToEntity.toAdmin()
 */
public class Admin {
    private int id;     //管理员id

    private String password;    //密码

    private int status; //状态: 0:冻结 1:正常

    public Admin() {
    }

    public Admin(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public Admin(int id, String password, int status) {
        this.id = id;
        this.password = password;
        this.status = status;
    }


    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", password='" + password + '\'' +
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
