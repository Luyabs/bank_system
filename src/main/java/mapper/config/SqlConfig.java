package mapper.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlConfig {
    private static Connection connection;  //通过这个对象执行SQL语句

    private SqlConfig() {  //单例模式 - 不能构造新对象
    }

    /**
     * JDBC配置
     * 配置文件在src/resources/SqlConfig.properties
     */
    private static void initial() { //创建数据库连接 除加连接池外不需要改动
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/SqlConfig.properties"));
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("配置文件没配对，连不到DB，去SqlConfig.properties做修改");
            throw new RuntimeException(e);
        }
    }

    /**
     * 只创建一次connection对象
     * @return 单例connection对象
     */
    public static Connection getInstance() {
        if (connection == null)
            initial();
        return connection;
    }
}
