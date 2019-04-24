import java.io.File;
import java.io.FileWriter;
import java.sql.*;

public class demo {
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://8.8.8.8:3306/test";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 写入文件
            String filename = "file.txt";
            File file = new File(filename);
            //if file doesnt exists, then create it
            if (!file.exists()){
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(),true);
            //fileWritter.write(data);

            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select * from user limit 100";
            ResultSet rs = stmt.executeQuery(sql);
            int col = rs.getMetaData().getColumnCount();
            System.out.println("一共 " + col + " 列！");
            System.out.println("---------------------------------------");
            // 展开结果集数据库
            while(rs.next()){
                // 输出数据
                for (int i = 1; i < col; i++) {
                    String str1 = rs.getString(i) + "\001";
                    System.out.print(str1);
                    fileWritter.write(str1);
                }
                System.out.print("\n");
                fileWritter.write("\n");

            }
            System.out.println("---------------------------------------");
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
            fileWritter.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }

        }
        System.out.println("Goodbye!");

    }

}
