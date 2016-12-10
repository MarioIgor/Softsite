package dao;

import java.sql.SQLException;
import totalcross.sql.Statement;
import util.Conexao;

public class BancoDeDadosDAO {
	
	public static void executar(String comando) {
		Statement statement = null;
		try {
			statement = (Statement) Conexao.iniciar().createStatement();	
			statement.execute(comando);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public static void criar() {
		String comando = "create table if not exists PESSOA ("
					   + "CHAVE integer primary key autoincrement, "
				 	   + "NOME varchar, "
				 	   + "IDADE integer )";
		executar(comando);		
	}
}
