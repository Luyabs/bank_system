package mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        int i=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update card set money = money + ? where id = ?"
            );
            preparedStatement.setDouble(1, changeMoney);
            preparedStatement.setInt(2, id);
            i=preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return i>0;
    }


    /**
     * 修改卡所属的bank
     *
     * @param id 卡号
     * @param bank 更改的银行
     * @return 修改是否成功
     */
    public boolean setBank(int id, String bank) {
        int i=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update card set bank = '"+bank+"' where id = ?"
            );
            preparedStatement.setInt(1,id);
            i=preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return i>0;
    }


    /**
     *
     * @param id 卡号
     * @param Status 新状态
     * @return 修改是否成功
     */
    public boolean setStatus(int id, int Status) {
        int i=0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update card set status = ? where id = ?"
            );
            preparedStatement.setInt(1,Status);
            preparedStatement.setInt(2,id);
            i=preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return i>0;
    }


    /**
     * 查询所有card详细信息
     *
     * @return List<Card>
     */
    public List<Card> selectAllCard() {
        List<Card> list=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from card"
            );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Card card=new Card();
                card.setId(rs.getInt("id"));
                card.setMoney(rs.getDouble("money"));
                card.setBank(rs.getString("bank"));
                card.setUid(rs.getLong("uid"));
                card.setUserInform(rs.getString("user_inform"));
                card.setStatus(rs.getInt("status"));
                list.add(card);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return list;
    }
}



