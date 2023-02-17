package mapper;

import entity.Admin;
import mapper.config.SqlConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这里写关于Admin的SQL
 */
public class AdminMapper {

    private final Connection connection = SqlConfig.getInstance();  //通过这个对象执行SQL语句

    /**
     * 按id查管理员
     * @param id 管理员id
     * @return Admin: 建议使用admin.id admin.password
     */
    public Admin selectPasswordById(int id) {
        Admin admin = null;
        try { //执行SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, password from admin where id = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                admin = new Admin(
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
        return admin;
    }
}
