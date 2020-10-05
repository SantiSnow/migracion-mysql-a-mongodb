package src;

import java.sql.*;
import javax.swing.JOptionPane;
import org.bson.Document;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

public class Main {

	
	
	public static void main(String[] args) {
		
		JOptionPane.showMessageDialog(null, "Bienvenido, este programa migra una Base de datos de MySQL a MongoDB");
		
		String mongoDB = JOptionPane.showInputDialog("Ingrese nombre de la BBDD Mongo de destino");
		String coleccion = JOptionPane.showInputDialog("Ingrese nombre de la Coleccion Mongo de destino");
		String baseDatosSql = JOptionPane.showInputDialog("Ingrese nombre de la BBDD MySQL de origen");
		String tabla = JOptionPane.showInputDialog("Ingrese nombre de la tabla MySQL de Origen");
		String usuario = JOptionPane.showInputDialog("Ingrese ususario de la base de datos MySQL de Origen");
		String password = JOptionPane.showInputDialog("Ingrese password de la base de datos MySQL de Origen");
		Document producto;
		
		//objetos para conectar a mongodb y a mysql
		MongoConnection conexionMongo = new MongoConnection();
		MySqlConnection conexionSql = new MySqlConnection();
		
		//conexion a mongodb
		MongoDatabase dataBase = conexionMongo.mongoCon(mongoDB, coleccion);
		
		try {
			
			
			//Conexion a mysql
			ResultSet miResultado = conexionSql.consulta(baseDatosSql, tabla, usuario, password);
			
			//leer cada resultado de mysql
			while (miResultado.next()) {
				
				//colocarlo dentro de un objeto de tipo document de mongo db
				producto = new Document("_id", miResultado.getString("ID"))
						.append("NombreAarticulo", miResultado.getString("NOMBREARTICULO"))
						.append("URL", miResultado.getString("URL"))
						.append("Descripcion", miResultado.getString("DESCRIPCION"))
						.append("Imagen", miResultado.getString("FOTO"))
						.append("Tags", miResultado.getString("TAGS"))
						.append("Fecha", miResultado.getString("FECHA"))
						.append("Contenido", miResultado.getString("CONTENIDO"));
				
				//insertar el documento en la coleccion
				dataBase.getCollection(coleccion).insertOne(producto);
				
			}
			//cerrar los pool de conexiones
			conexionSql.cerrarPool();
			
			JOptionPane.showMessageDialog(null, "La base de datos fue migrada con éxito. Cerrando conexiones... Hasta pronto.");
			
			
		}
		catch(MongoCommandException e) {
			JOptionPane.showMessageDialog(null, "La base de datos que desea crear ya existe, intente con otro nuevamente con otro nombre.");
			
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "No se encontro la base de datos de origen. Por favor, chequee el nombre de la base de datos y su tabla.");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "El usuario y/o contraseña de MySQL no parecen correctos, por favor verifiquelos.");
		}
		finally {
			
			
			conexionMongo.cerrarPool();
		}
	}

}
