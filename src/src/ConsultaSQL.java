package src;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConsultaSQL {
	
	public static void main(String[] args) {
	
		MySqlConnection conexion = new MySqlConnection();
		
		String baseDatosSql = JOptionPane.showInputDialog("Ingrese nombre de la BBDD MySQL de origen");
		String tabla = JOptionPane.showInputDialog("Ingrese nombre de la tabla MySQL de Origen");
		String usuario = JOptionPane.showInputDialog("Ingrese ususario de la base de datos MySQL de Origen");
		String password = JOptionPane.showInputDialog("Ingrese password de la base de datos MySQL de Origen");
		
		try {
			ResultSet resultado = conexion.consulta(baseDatosSql, tabla, usuario, password);
			
			while(resultado.next()) {
				System.out.println(resultado.getString(1) + " - " + resultado.getString(2) + " - " + resultado.getString(3) + " - " + resultado.getString(4));
			}
		conexion.cerrarPool();
			
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "No se encontro la base de datos de origen. Por favor, chequee el nombre de la base de datos y su tabla.");
			e.printStackTrace();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "El usuario y/o contraseña de MySQL no parecen correctos, por favor verifiquelos.");
			e.printStackTrace();
		}
		
	
	}

}
