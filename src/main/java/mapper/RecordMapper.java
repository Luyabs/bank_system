package mapper;

import java.sql.*;

import entity.Record;
import mapper.config.SqlConfig;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 这里写关于Record的SQL
 */
public class RecordMapper {

    private final Connection connection = SqlConfig.getInstance();  //通过这个对象执行SQL语句

    /**
     * 按照id查该id作为支出方的record序列
     * @param id 支出方id
     * @return records 完整的收支记录序列
     */
    public List<Record> selectRecordsByFromId(int id) {
        List<Record> records=new ArrayList<>();
        Record record=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from record where from_id=?"
            );
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                record=new Record(
                        rs.getInt("id"),
                        rs.getInt("from_id"),
                        rs.getInt("to_id"),
                        rs.getDouble("money"),
                        rs.getInt("type"),
                        rs.getString("time")
                );
                records.add(record);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return records;
    }

    /**
     * 按照id查该id作为接收方的record序列
     * @param id 接收方id
     * @return records 完整的收支记录序列
     */
    public List<Record> selectRecordsByToId(int id) {
        List<Record> records=new ArrayList<>();
        Record record=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from record where to_id=?"
            );
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                record=new Record(
                        rs.getInt("id"),
                        rs.getInt("from_id"),
                        rs.getInt("to_id"),
                        rs.getDouble("money"),
                        rs.getInt("type"),
                        rs.getString("time")
                );
                records.add(record);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("!!!!!!配置文件没配对，连不到DB，去SqlConfig.properties做修改");
        }
        return records;
    }
}
