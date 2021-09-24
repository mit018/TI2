package dao;

import java.sql.*;

import dao.SerieDAO;
import model.Serie;

public class SerieDAO {

	private Connection conexao;

	public SerieDAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "db_catalogo";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirSerie(Serie serie) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO series (id, qtd_episodios, qtd_temporadas, nome) " + "VALUES ("
					+ serie.getId() + ", '" + serie.getQtdEpisodios() + "', '" + serie.getQtdTemporadas() + "', '"
					+ serie.getNome() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {
			System.out.println("Erro ao criar a serie.");
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarSerie(Serie serie) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE series SET nome = '" + serie.getNome() + "', qtd_episodios = '"
					+ serie.getQtdEpisodios() + "', qtd_temporadas = '" + serie.getQtdTemporadas() + "'"
					+ " WHERE id = " + serie.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			System.out.println("Erro ao atualizar a serie.");
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean excluirSerie(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM series WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			System.out.println("Erro ao excluir a serie.");
			throw new RuntimeException(u);
		}
		return status;
	}

	public Serie[] getSeries() {
		Serie[] series = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM series");
			if (rs.next()) {
				rs.last();
				series = new Serie[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					series[i] = new Serie(rs.getInt("id"), rs.getInt("qtd_episodios"), rs.getInt("qtd_temporadas"),
							rs.getString("nome"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.out.println("Erro ao listar as series.");
			System.err.println(e.getMessage());
		}
		return series;
	}

	public Serie getSerie(int id) {
		Serie serie = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM series WHERE id = " + id;
			ResultSet rs = st.executeQuery(query);
			
			if(rs.next()) {
			serie = new Serie(rs.getInt("id"), rs.getInt("qtd_episodios"), rs.getInt("qtd_temporadas"),
					rs.getString("nome"));
			}
			
			st.close();
		} catch (Exception e) {
			System.out.println("Erro ao listar serie.");
			System.err.println(e.getMessage());
		}
		return serie;
	}
}