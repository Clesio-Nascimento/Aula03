package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Pais;

public class PaisDAO {		
	public int criar(Pais pais) {
		String sqlInsert = "INSERT INTO pais(nome, população, area) VALUES (?, ?, ?)";
		
		try (Connection conn = ConnectionFactory.connPais(); 
			PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			
				stm.setString(1, pais.getNome());
				stm.setLong(2, pais.getPopulacao());
				stm.setDouble(3, pais.getArea());
				stm.execute();
				
			String sqlQuery = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery);
					ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					pais.setId(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pais.getId();
	}
	
	public void atualizar(Pais pais) {
		String sqlUpdate = "UPDATE pais SET nome=?, população=?, area=? WHERE id=?";
		try (Connection conn = ConnectionFactory.connPais();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setString(1, pais.getNome());
			stm.setLong(2, pais.getPopulacao());
			stm.setDouble(3, pais.getArea());
			stm.setInt(4, pais.getId());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(int id) {
		String sqlDelete = "DELETE FROM pais WHERE id = ?";

		try (Connection conn = ConnectionFactory.connPais();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, id);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Pais carregar(int id) {
		Pais pais = new Pais();
		pais.setId(id);
		String sqlSelect = "SELECT nome, população, area FROM pais WHERE pais.id = ?";
		
		try (Connection conn = ConnectionFactory.connPais();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, pais.getId());
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					pais.setNome(rs.getString("nome"));
					pais.setPopulacao(rs.getLong("população"));
					pais.setArea(rs.getDouble("area"));
				} else {
					pais.setId(-1);
					pais.setNome(null);
					pais.setPopulacao(0);
					pais.setArea(0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return pais;
	}
	
	/*
	public Pais maior(Pais pais) {
		pais = new Pais();
		String sqlSelect = 
				"SELECT id, nome, população, area FROM pais WHERE população = (SELECT MAX(população) FROM pais)";
		
		try (Connection conn = ConnectionFactory.connPais();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					pais.setId(rs.getInt("id"));
					pais.setNome(rs.getString("nome"));
					pais.setPopulacao(rs.getLong("população"));
					pais.setArea(rs.getDouble("area"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return pais;
	}
	
	public Pais menor(Pais pais) {
		pais = new Pais();
		String sqlSelect = 
				"SELECT id, nome, população, area FROM pais WHERE população = (SELECT MIN(população) FROM pais)";
		
		try (Connection conn = ConnectionFactory.connPais();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					pais.setId(rs.getInt("id"));
					pais.setNome(rs.getString("nome"));
					pais.setPopulacao(rs.getLong("população"));
					pais.setArea(rs.getDouble("area"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return pais;
	}*/
	
	public int maior(Pais[] pais) {
		int iMa = 0;
		for (int i = 1; i < pais.length; i++) {
			if (pais[i].getPopulacao() > pais[iMa].getPopulacao()) {
				iMa = i;
			}
		}
		return pais[iMa].getId();
	}
	
	public int menor(Pais[] pais) {
		double t = pais[0].getArea();
		int grava = 0;
		for (int i = 0; i < pais.length; i++) {
			if (pais[i].getArea() < t) {
				t = pais[i].getArea();
				grava = pais[i].getId();
			}
		}
		return grava;
	}
}
