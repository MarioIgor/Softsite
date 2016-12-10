package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pessoa;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import util.Conexao;

public class PessoaDAO extends BancoDeDadosDAO{

	public static void inserir(Pessoa pessoa) {
		String comando = "insert into PESSOA "
				 	   + "(NOME, IDADE) "
				 	   + "values ('" + pessoa.getNome() + "', "+ pessoa.getIdade() +")";
		executar(comando);
	}

	public static void editar(Pessoa pessoa) {
		String comando = "update PESSOA "
				 	   + "set NOME = '" + pessoa.getNome() + "', IDADE = " + pessoa.getIdade()
				 	   + " where CHAVE = " + pessoa.getChave();
		executar(comando);		
	}

	public static void excluir(String chave) {
		String comando = "delete from PESSOA where CHAVE = " + chave;
		executar(comando);		
	}

	public static List<Pessoa> buscar() {
		Statement statement = null;
		List<Pessoa> pessoas = new ArrayList<>();		
		try {
			String query = "select CHAVE,NOME,IDADE from PESSOA";
			statement = (Statement) Conexao.iniciar().createStatement();
			ResultSet resultado = statement.executeQuery(query);
			while (resultado.next()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setChave(resultado.getInt("CHAVE"));
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setIdade(resultado.getInt("IDADE"));
				pessoas.add(pessoa);
			}
			return pessoas;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static Pessoa buscar(String chave) {
		Pessoa pessoa = null;
		Statement statement = null;
		try {
			String query = "select CHAVE,NOME,IDADE from PESSOA where CHAVE = " + chave;
			statement = (Statement)Conexao.iniciar().createStatement();
			ResultSet resultado = statement.executeQuery(query);
			if (resultado.next()) {
				pessoa = new Pessoa();
				pessoa.setChave(resultado.getInt("CHAVE"));
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setIdade(resultado.getInt("IDADE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return pessoa;
	}

	
}
