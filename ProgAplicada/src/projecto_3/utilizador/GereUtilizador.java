package projecto_3.utilizador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import projecto_3.utilizador.Utilizador;

public class GereUtilizador {

	/**
	 * Verifica quantos utilizadores existem na base de dados.
	 * 
	 * @param aSt
	 * @param aComando
	 * @return número de utilizadores na base de dados.
	 */
	public int verificaListaVazia(Statement aSt, String aComando) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM utilizador" + aComando);

			if (rs == null) {
				return 0;
			} else {
				while (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Verifica se um login existe
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return false se existir, true se não.
	 */
	public boolean verificaLogin(String aLogin, Statement aSt) {
		ResultSet rs = null;
		int valor = 0;
		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return false;
			} else {
				while (rs.next()) {
					valor = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return false;
		}

		if (valor == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica se um email já existe na base de dados
	 * 
	 * @param aEmail
	 * @param aSt
	 * @return false se já, true se não.
	 */
	public boolean verificaEmail(String aEmail, Statement aSt) {
		ResultSet rs = null;
		int valor = 0;
		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM utilizador where u_email = '" + aEmail + "';");

			if (rs == null) {
				return false;
			} else {
				while (rs.next()) {
					valor = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return false;
		}

		if (valor == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica a autenticação de um utilizador
	 * 
	 * @param aLogin
	 * @param aPassword
	 * @param aSt
	 * @return true se bem sucedida, false se não.
	 */
	public boolean verificaAutenticacao(String aLogin, String aPassword, Statement aSt) {
		ResultSet rs = null;
		int valor = 0;
		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM utilizador where u_login = '" + aLogin + "' AND u_password ='"
					+ aPassword + "';");

			if (rs == null) {
				return false;
			} else {
				while (rs.next()) {
					valor = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return false;
		}

		if (valor == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Verifica o estado de reprovação
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return boolean do estado de reprovação.
	 */
	public boolean verificaEstadoReprovacao(String aLogin, Statement aSt) {
		ResultSet rs = null;
		boolean valor = true;
		try {

			rs = aSt.executeQuery(" SELECT u_estadoreprovacao FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return true;
			} else {
				while (rs.next()) {
					valor = rs.getBoolean(1);
				}
			}
		} catch (SQLException e) {
			return true;
		}

		return valor;
	}

	/**
	 * Verifica estado de um utilizador
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return boolean do estado do utilizador.
	 */
	public boolean verificaEstado(String aLogin, Statement aSt) {
		ResultSet rs = null;
		boolean valor = false;
		try {

			rs = aSt.executeQuery(" SELECT u_estado FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return false;
			} else {
				while (rs.next()) {
					valor = rs.getBoolean(1);
				}
			}
		} catch (SQLException e) {
			return false;
		}

		return valor;
	}

	/**
	 * Verifica o tipo de utilizador
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return 1 se for do tipo gestor, 2 do tipo técnico ou 3 do tipo fabricante.
	 */
	public int verificaTipo(String aLogin, Statement aSt) {
		ResultSet rs = null;
		char valor = 'a';
		try {

			rs = aSt.executeQuery(" SELECT u_tipo FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return 0;
			} else {
				while (rs.next()) {
					valor = rs.getString(1).charAt(0);
				}
			}
		} catch (SQLException e) {
			return 0;
		}

		if (valor == 'A') {
			return 1;
		} else if (valor == 'J') {
			return 2;
		}
		return 0;
	}

	/**
	 * Lista os utilizadores inactivos
	 * 
	 * @param model
	 * @param aSt
	 * @return Tabela com os dados dos utilizadores encontrados.
	 */
	public DefaultTableModel listaUserInactivo(Statement aSt, DefaultTableModel model) {

		ResultSet rs = null;

		try {

			rs = aSt.executeQuery("select * from utilizador where u_estado = false and u_estadoreprovacao = false");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				Object[] rowData = new Object[6];
				while (rs.next()) {
					rowData[0] = rs.getInt(1);
					rowData[1] = rs.getString(2);
					rowData[2] = rs.getString(3);
					rowData[3] = "*****";
					rowData[4] = rs.getString(7);

					if (rs.getString(8).charAt(0) == 'A') {
						rowData[5] = "Administrador";
					} else {
						rowData[5] = "Jogador";
					}

					model.addRow(rowData);
				}
				return model;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Lista os utilizadores inactivos
	 * 
	 * @param aSt
	 * @return Array com os logins dos utilizadores encontrados.
	 */
	public String[] listaUserInactivoArray(Statement aSt) {

		ResultSet rs = null;
		int contador = 0;

		try {

			rs = aSt.executeQuery("select * from utilizador where u_estado = false");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				while (rs.next()) {
					contador++;
				}
			}

			rs = aSt.executeQuery("select * from utilizador where u_estado = false");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				String[] rowData = new String[contador];
				contador = 0;
				while (rs.next()) {
					rowData[contador] = rs.getString(3);
					contador++;
				}
				return rowData;
			}
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Devolve os dados de um utilizador
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return Array com os logins dos utilizadores encontrados.
	 */
	public String[] devolveDados(String aLogin,Statement aSt) {

		ResultSet rs = null;

		try {

			rs = aSt.executeQuery("select * from utilizador where u_login = '"+aLogin+"'");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				String[] rowData = new String[5];
				while (rs.next()) {
					rowData[0] = rs.getString(2);
					rowData[1] = rs.getString(3);
					rowData[2] = rs.getString(4);
					rowData[3] = rs.getString(7);
				}
				return rowData;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Aprova um utilizador.
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return boolean a informar se correu tudo bem.
	 */
	public boolean aprovaUtilizador(String aLogin, Statement aSt) {
		int valor = 0;
		boolean estado = false, estadoReprovacao = false;
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(
					"select u_estado, u_estadoreprovacao from utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return false;
			} else if (!rs.isBeforeFirst()) {
				return false;
			} else {
				while (rs.next()) {
					estado = rs.getBoolean(1);
					estadoReprovacao = rs.getBoolean(2);
				}
				if (estado) {
					return false;
				} else {
					if (estadoReprovacao) {
						valor = aSt.executeUpdate(
								" UPDATE utilizador SET u_estado = true, u_estadoreprovacao = false where u_login = '"
										+ aLogin + "';");
						if (valor == 0) {
							return false;
						} else {
							return true;
						}
					} else {
						valor = aSt.executeUpdate(
								" UPDATE utilizador SET u_estado = true where u_login = '" + aLogin + "';");
						if (valor == 0) {
							return false;
						} else {
							return true;
						}
					}
				}
			}
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Lista utilizadores reprovados.
	 * 
	 * @param model
	 * @param aSt
	 * @return Tabela com os dados dos utilizadores encontrados.
	 */
	public DefaultTableModel listaUtilizadorReprovado(Statement aSt, DefaultTableModel model) {

		ResultSet rs = null;

		try {

			rs = aSt.executeQuery("select * from utilizador where u_estadoreprovacao = true;");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				Object[] rowData = new Object[6];
				while (rs.next()) {
					rowData[0] = rs.getInt(1);
					rowData[1] = rs.getString(2);
					rowData[2] = rs.getString(3);
					rowData[3] = "*****";
					rowData[4] = rs.getString(7);

					if (rs.getString(8).charAt(0) == 'A') {
						rowData[5] = "Administrador";
					} else {
						rowData[5] = "Jogador";
					}
					model.addRow(rowData);
				}
				return model;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Lista utilizadores que podem ser reprovados.
	 * 
	 * @param aSt
	 * @return String com os dados dos utilizadores encontrados.
	 */
	public String[] listaUtilizadorReprovado1(Statement aSt) {

		ResultSet rs = null;
		int contador = 0;

		try {

			rs = aSt.executeQuery("select * from utilizador where u_estadoreprovacao != true and u_tipo != 'A'");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				while (rs.next()) {
					contador++;
				}
			}

			rs = aSt.executeQuery("select * from utilizador where u_estadoreprovacao != true and u_tipo != 'A'");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				String[] rowData = new String[contador];
				contador = 0;
				while (rs.next()) {
					rowData[contador] = rs.getString(3);
					contador++;
				}
				return rowData;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Obtem o id do utilizador através do login.
	 * 
	 * @param aSt
	 * @param aLogin
	 * @return id do utilizador.
	 */
	public int getIdUser(Statement aSt, String aLogin) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT u_id FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return 0;
			} else {
				while (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Lista todos os utilizadores.
	 * 
	 * @param aComando
	 * @param aSt
	 * @param model
	 * @return tabela com a lista de utilizadores.
	 */
	public DefaultTableModel listaUserTotais(String aComando, Statement aSt, DefaultTableModel model) {

		ResultSet rs = null;

		try {

			rs = aSt.executeQuery("select * from utilizador " + aComando);

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				Object[] rowData = new Object[8];
				while (rs.next()) {
					Utilizador user = new Utilizador(rs.getInt(1), rs.getString(2), rs.getString(3), "*****",
							rs.getString(7), rs.getBoolean(5), rs.getString(8).charAt(0), rs.getBoolean(6));
					rowData[0] = user.getId();
					rowData[1] = user.getNome();
					rowData[2] = user.getLogin();
					rowData[3] = "******";

					if (user.getEstado()) {
						rowData[4] = "Activo";
					} else {
						rowData[4] = "Inactivo";
					}

					rowData[5] = user.getEmail();

					if (user.getTipo() == 'A') {
						rowData[6] = "Administrador";
					} else {
						rowData[6] = "Jogador";
					}

					if (user.getEstadoReprovacao()) {
						rowData[7] = "Reprovado";
					} else {
						rowData[7] = "Não Reprovado";
					}
					model.addRow(rowData);
				}
				return model;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Reprova um utilizador
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return boolean a indicar se o utilizador foi reprovador com sucesso.
	 */
	public boolean reprovaUtilizador(String aLogin, Statement aSt) {
		int valor = 0;
		boolean estado = false, estadoReprovacao = false;
		char tipo = 'A';
		ResultSet rs = null;

		try {
			rs = aSt.executeQuery(
					"select u_tipo, u_estado, u_estadoreprovacao from utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return false;
			} else if (!rs.isBeforeFirst()) {
				return false;
			} else {
				while (rs.next()) {
					tipo = rs.getString(1).charAt(0);
					estado = rs.getBoolean(2);
					estadoReprovacao = rs.getBoolean(3);
				}
				if (tipo == 'A') {
					return false;
				} else {
					if (estadoReprovacao) {
						return false;
					} else {
						if (estado) {
							valor = aSt.executeUpdate(
									" UPDATE utilizador SET u_estado = false, u_estadoreprovacao = true where u_login = '"
											+ aLogin + "';");
							if (valor == 0) {
								return false;
							} else {
								return true;
							}
						} else {
							valor = aSt
									.executeUpdate(" UPDATE utilizador SET u_estadoreprovacao = true where u_login = '"
											+ aLogin + "';");
							if (valor == 0) {
								return false;
							} else {
								return true;
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Inactiva uma conta
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return true se correu bem, false se não.
	 */
	public boolean inactivaconta(String aLogin, Statement aSt) {
		int valor = 0;

		try {

			valor = aSt.executeUpdate(
					" UPDATE utilizador SET u_estado = false, u_estadoreprovacao = true where u_login = '" + aLogin
							+ "';");

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
	 * Verifica quantos utilizadores reprovados existem.
	 * 
	 * @param aSt
	 * @return número de utilizadores reprovados.
	 */
	public int verificaListaReprovado(Statement aSt) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM utilizador where u_estadoreprovacao = true;");

			if (rs == null) {
				return 0;
			} else {
				while (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Verifica quantos utilizadores reprovados sem ser do tipo G existem.
	 * 
	 * @param aSt
	 * @return número de utilizadores reprovados.
	 */
	public int verificaListaReprovado1(Statement aSt) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(
					" SELECT count(*) FROM utilizador where u_estadoreprovacao != true and u_tipo != 'A';");

			if (rs == null) {
				return 0;
			} else {
				while (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Verifica quantos utilizadores existem inactivos.
	 * 
	 * @param aSt
	 * @return número de utilizadores inactivos.
	 */
	public int verificaListaInactivo(Statement aSt) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(
					" SELECT count(*) FROM utilizador where u_estado = false and u_estadoreprovacao = false;");

			if (rs == null) {
				return 0;
			} else {
				while (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Obtem o nome do utilizador
	 * 
	 * @param aLogin
	 * @param aSt
	 * @return String do nome de utilizador.
	 */
	public String getNomeUtilizador(String aLogin, Statement aSt) {
		ResultSet rs = null;
		String valor = null;
		try {

			rs = aSt.executeQuery(" SELECT u_name FROM utilizador where u_login = '" + aLogin + "';");

			if (rs == null) {
				return "O comando para enviar o nome de utilizador não pode ser executado neste momento!";
			} else {
				while (rs.next()) {
					valor = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			return "\nA tabela de utilizadores não foi encontrada\n" + e;
		}

		return valor;
	}
	
	/**
	 * Obtem o login do utilizador através do id
	 * 
	 * @param aId
	 * @param aSt
	 * @return String do login de utilizador.
	 */
	public String getLoginId(int aId, Statement aSt) {
		ResultSet rs = null;
		String valor = null;
		try {

			rs = aSt.executeQuery(" SELECT u_login FROM utilizador where u_id = '" + aId + "';");

			if (rs == null) {
				return null;
			} else {
				while (rs.next()) {
					valor = rs.getString(1);
				}
			}
		} catch (SQLException e) {
			return null;
		}

		return valor;
	}

}
