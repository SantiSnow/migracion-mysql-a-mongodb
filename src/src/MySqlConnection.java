package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnection {

	Connection miConexion;
	
	public ResultSet consulta(String baseDatosSql, String tabla) throws SQLException {
		miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + baseDatosSql + "?serverTimezone=UTC&useSSL=false", "root", "");
		Statement miStatement = miConexion.createStatement();
		String sentenciaSql = "SELECT * FROM " + tabla;
		
		ResultSet miResultado = miStatement.executeQuery(sentenciaSql);
		
		return miResultado;
	}
	
	public void cerrarPool() throws SQLException {
		miConexion.close();
	}
}
