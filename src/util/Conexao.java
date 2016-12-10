package util;

import java.sql.SQLException;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sys.Convert;
import totalcross.sys.Settings;

public class Conexao {
	
	private static Connection conexao;
	
	public Conexao() {}

	public static Connection iniciar() {
		try {
			if (conexao == null) {
				conexao = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, "database.db"));
			}
			
			return conexao;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
