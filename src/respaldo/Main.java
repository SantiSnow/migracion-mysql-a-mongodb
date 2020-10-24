package respaldo;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String filename = "C:\\users\\Santiago\\my-back-up.csv";

        try{
            FileWriter fw = new FileWriter(filename);
            Class.forName("com.mysql.jdbc.Driver");
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate?serverTimezone=UTC&useSSL=false", "root", "");
            Statement miStatement = miConexion.createStatement();
            String sentenciaSql = "SELECT * FROM producto";
            ResultSet rs = miStatement.executeQuery(sentenciaSql);

            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append('\n');
            }

            fw.flush();
            fw.close();
            miConexion.close();
            System.out.println("Archivo generado");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}