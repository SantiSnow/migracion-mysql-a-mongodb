package src;

import java.sql.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Main {

	static DBObject producto;
	
	public static void main(String[] args) {
		
		try {
			//conexion a mongodb
			//creamos una conexion cliente a mongo db
			MongoClient mongoClient = new MongoClient();
			DB dataBase = mongoClient.getDB("JavaMigrations");
			DBCollection collection = dataBase.getCollection("Articulos");
			
			//Conexion a mysql
			System.out.println("Conectando a la base de datos.");
			Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/id13080475_basedatosprueba?serverTimezone=UTC&useSSL=false", "root", "");
			Statement miStatement = miConexion.createStatement();
			String sentenciaSql = "SELECT * FROM ARTICULOS";
			ResultSet miResultado = miStatement.executeQuery(sentenciaSql);
			
			while (miResultado.next()) {
				
				producto = new BasicDBObject("_id", miResultado.getString("ID"))
						.append("NombreAarticulo", miResultado.getString("NOMBREARTICULO"))
						.append("URL", miResultado.getString("URL"))
						.append("Descripcion", miResultado.getString("DESCRIPCION"))
						.append("Imagen", miResultado.getString("FOTO"))
						.append("Tags", miResultado.getString("TAGS"))
						.append("Fecha", miResultado.getString("FECHA"))
						.append("Contenido", miResultado.getString("CONTENIDO"));
				
				collection.insert(producto);
				
			}
			
			miConexion.close();
			
			mongoClient.close();
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
