package src;

import java.sql.*;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Main {

	
	
	public static void main(String[] args) {
		
		String mongoDB = "JavaMigrations";
		String coleccion = "Articulos";
		String baseDatosSql = "id13080475_basedatosprueba";
		String tabla = "ARTICULOS";
		Document producto;
		
		try {
			//conexion a mongodb
			//creamos una conexion cliente a mongo db
			MongoClient mongoClient = new MongoClient();
			MongoDatabase dataBase = mongoClient.getDatabase(mongoDB);
			dataBase.createCollection(coleccion);
			
			//Conexion a mysql
			System.out.println("Conectando a la base de datos.");
			Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + baseDatosSql + "?serverTimezone=UTC&useSSL=false", "root", "");
			Statement miStatement = miConexion.createStatement();
			String sentenciaSql = "SELECT * FROM " + tabla;
			ResultSet miResultado = miStatement.executeQuery(sentenciaSql);
			
			while (miResultado.next()) {
				
				producto = new Document("_id", miResultado.getString("ID"))
						.append("NombreAarticulo", miResultado.getString("NOMBREARTICULO"))
						.append("URL", miResultado.getString("URL"))
						.append("Descripcion", miResultado.getString("DESCRIPCION"))
						.append("Imagen", miResultado.getString("FOTO"))
						.append("Tags", miResultado.getString("TAGS"))
						.append("Fecha", miResultado.getString("FECHA"))
						.append("Contenido", miResultado.getString("CONTENIDO"));
				
				dataBase.getCollection(coleccion).insertOne(producto);
				
			}
			
			miConexion.close();
			
			mongoClient.close();
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
