package projecto_3.utilizador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class GereAdministrador {

	/**
	 * Lista dados de um utilizador do tipo administrador.
	 * 
	 * @param aLogin
	 * @param aSt
	 * @param model
	 * @return Tabela com os dados do administrador
	 */
	public DefaultTableModel listaDadosAdmin(String aLogin, Statement aSt, DefaultTableModel model) {

		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT * FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return null;
			} else {
				Object[] rowData = new Object[6];
				while (rs.next()) {
					rowData[0] = rs.getInt(1);
					rowData[1] = rs.getString(2);
					rowData[2] = rs.getString(3);
					rowData[3] = rs.getString(4);
					rowData[4] = rs.getString(7);
					rowData[5] = "Administrador";

					model.addRow(rowData);
				}
				return model;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Faz um 'update' aos dados do administrador na base de dados.
	 * 
	 * @param aAdmin
	 * @param aLogin
	 * @param aSt
	 * @return boolean a informar se os dados foram alterados com sucesso
	 */
	public boolean actualizaDadosAdmin(Utilizador aAdmin, String aLogin, Statement aSt) {
		int valor = 0;

		try {
            String aComando = "u_login = '"+aAdmin.getLogin()+"', u_name = '"+aAdmin.getNome()+"', u_password = '"+aAdmin.getPassword()+"',"
            		          + " u_email = '"+aAdmin.getEmail()+"', u_tipo = '"+aAdmin.getTipo()+"'";
            
			valor = aSt.executeUpdate(" UPDATE utilizador SET " + aComando + " where u_login = '" + aLogin + "';");

			if (valor == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Verifica se existem administradores na base de dados.
	 * 
	 * @param aSt
	 * @return boolean a indicar se lista está vazia.
	 */
	public boolean verificaListaVazia(Statement aSt) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM utilizador where u_tipo='A' and u_estado = true and u_estadoreprovacao = false");

			if (rs == null) {
				return false;
			} else {
				while (rs.next()) {
					if (rs.getInt(1) > 0) {
						return false;
					} else {
						return true;
					}
				}
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	/**
	 * Regista um administrador na base de dados
	 * 
	 * @param aAdministrador
	 * @param aSt
	 * @return true se for bem sucedido, false se não.
	 */
	public boolean registaAdministrador(Utilizador aAdministrador, Statement aSt) {

		if (enviaDadosAdmin(aAdministrador, aSt)) {
			return true;
		}
		return false;
	}

	/**
	 * Introduz um administrador na base de dados.
	 * 
	 * @param aAdministrador
	 * @param aSt
	 * @return true se for bem sucedido, false se não.
	 */
	private boolean enviaDadosAdmin(Utilizador aAdministrador, Statement aSt) {

		try {
			aSt.execute(
					"Insert INTO utilizador (U_login, u_name, U_EMAIL, U_PASSWORD, U_ESTADO, U_ESTADOREPROVACAO, U_TIPO)"
							+ "VALUES ('" + aAdministrador.getLogin() + "','" + aAdministrador.getNome() + "','"
							+ aAdministrador.getEmail() + "','" + aAdministrador.getPassword() + "',"
							+ aAdministrador.getEstado() + "," + aAdministrador.getEstadoReprovacao() + ",'"
							+ aAdministrador.getTipo() + "');");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
