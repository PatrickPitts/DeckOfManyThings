package org.nerdcore.phylactory.databaseInit;

import java.sql.*;

public class Init {

    public static void main(String[] args) {

        String url = "jdbc:sqlite:src/main/resources/phylactory.db";
        try(Connection conn = DriverManager.getConnection(url)){
            System.out.print("connection successful");
            Statement st = conn.createStatement();
//            System.out.println("Creating test table");
//            String sql = "DROP TABLE IF EXISTS test;\n" +
//                    "\n" +
//                    "CREATE TABLE test (\n" +
//                    "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "username varchar(64),\n" +
//                    "password varchar(64)\n" +
//                    "\n" +
//                    ");\n" +
//                    "\n" +
//                    "INSERT INTO test (username, password) VALUES\n" +
//                    "('username', 'password'), ('anothername', 'wordpass');";
//
//            st.executeUpdate(sql);
//            System.out.println("table created");
//
//            sql = "INSERT INTO test ('username', 'password') VALUES ('John', 'Green'), ('user', 'badpass');";
//            st.executeUpdate(sql);
            System.out.println("polling table");
            String sql = "SELECT * FROM test;";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getInt("id") + rs.getString("username") + rs.getString("password"));
            }
            System.out.println("done polling");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
