package mapper;

import java.sql.*;

import entity.Card;
import mapper.config.SqlConfig;

/**
 * 这里写关于Card的SQL
 */
public class CardMapper {

    private final Connection connection = SqlConfig.getInstance();  //通过这个对象执行SQL语句

    /**
     * 按id查card
     *
     * @param id 卡号
     * @return Card: 只有card.id card.password
     */
    public Card selectPasswordById(int id) {
        Card card = null;
        try { //执行SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, password from card_password where id = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                card = new Card(
                        rs.getInt("id"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return card;
    }

    /**
     * 按id查card
     *
     * @param id 卡号
     * @return Card: 完整的全部数据
     */
    public Card selectDetailedById(int id) {
        Card card = null;
        try { //执行SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * " +
                            "from card " +
                            "join card_password p on card.id = p.id " +
                            "where card.id = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                card = new Card(
                        rs.getInt("id"),
                        rs.getString("password"),
                        rs.getDouble("money"),
                        rs.getString("bank"),
                        rs.getLong("uid"),
                        rs.getString("user_inform"),
                        rs.getInt("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return card;
    }

    /**
     * 判断id是否存在
     *
     * @param id 卡号
     * @return 是否满足
     */
    public boolean idCheck(int id) {
        try {
            PreparedStatement idCheck = connection.prepareStatement(
                    "select * from card where id = ?"
            );
            idCheck.setInt(1, id);
            ResultSet resultSet = idCheck.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断资金流出方资金是否满足要求
     *
     * @param id 资金流出方； money 流出金额
     * @return 是否满足
     */
    public boolean moneyCheck(int id, double money) {
        try {
            //检查资金流出方是否满足要求
            PreparedStatement moneyCheck = connection.prepareStatement(
                    "select money from card where id = ?"
            );
            moneyCheck.setInt(1, id);
            ResultSet checkResult = moneyCheck.executeQuery();
            checkResult.next();
            double cardMoney = checkResult.getDouble("money");
            if (cardMoney >= money)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return false;
    }


    /**
     * 修改卡中资金
     *
     * @param id 卡号； money 改变金额(为正表示资金流入，为负表示资金流出）
     * @return 是否满足
     */
    public boolean setMoney(int id, double changeMoney) {
        int i = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update card set money = money + ? where id = ?"
            );
            preparedStatement.setDouble(1, changeMoney);
            preparedStatement.setInt(2, id);
            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return i > 0;
    }

    /**
     * @param id       银行卡号
     * @param password 密码
     * @param bank     银行
     * @param uid      用户id
     * @param username 用户名
     * @param status   银行
     * @return 是否成功添加
     */
    public boolean addCard(int id, String password, String bank, long uid, String username, int status) {
        int i = 0, j = 0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "insert into card(id,bank,uid,user_inform,status)" +
                            "values(?,?,?,?,?)"
            );
            preparedStatement1.setInt(1, id);
            preparedStatement1.setString(2, bank);
            preparedStatement1.setLong(3, uid);
            preparedStatement1.setString(4, username);
            preparedStatement1.setInt(5, status);
            i = preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "insert into card_password(id,password)" +
                            "values (?,?)"
            );
            preparedStatement2.setInt(1, id);
            preparedStatement2.setString(2, password);
            j = preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return i > 0 && j > 0;
    }

    /**
     * @param id       银行卡号
     * @param password 修改的密码
     * @return 是否修改成功
     */
    public boolean setPassword(int id, String password) {
        int i = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update card_password set password=? where id=?"
            );
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return i > 0;
    }

    public boolean deleteCard(int id) {
        int i = 0, j = 0;
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "delete from card where id = ?"
            );
            preparedStatement1.setInt(1, id);
            i = preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "delete from card_password where id=?"
            );
            preparedStatement2.setInt(1, id);
            j = preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return i > 0 && j > 0;
    }
}


