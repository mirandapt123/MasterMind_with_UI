package projecto_3.jogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;


public class GereJogo {

	/**
	 * Verifica a quantidade de jogos.
	 * 
	 * @param aComando
	 * @param aSt
	 * @return int com a quantidade de jogos.
	 */
	public int verificaQuantidadeJogo(String aComando, Statement aSt) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM jogo " + aComando + ";");

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
	 * Regista um jogo
	 * 
	 * @param aJogo
	 * @param aSt
	 * @return false se ocorreu um erro, true se foi registado com sucesso.
	 */
	public boolean registaJogo(Jogo aJogo, Statement aSt) {

        int idJogo = verificaQuantidadeJogo("", aSt);
        idJogo ++;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date parsedDate = dateFormat.parse(aJogo.getData());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			
			aSt.execute("Insert INTO jogo (jo_id, u_id, jo_data) Values ("+idJogo+","+aJogo.getuId()+", '"+timestamp+"')");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} catch (ParseException pe) {
			System.out.println(pe);
			return false;
		}
	}
	
	/**
	 * Lista todos os jogos de um utilizador.
	 * @param aComando
	 * @param aSt
	 * @param model
	 * @return Tabela com os jogos.
	 */
	public DefaultTableModel listaJogo(String aComando, Statement aSt, DefaultTableModel model) {

		ResultSet rs = null;
		
		try {

			rs = aSt.executeQuery(" SELECT * FROM jogo "+aComando);

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				Object[] rowData = new Object[2];
				while (rs != null && rs.next()) {
					rowData[0] = rs.getInt(1);
					rowData[1] = rs.getString(3);

					model.addRow(rowData);
				}
				return model;
			}
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * Lista todos os jogos por id
	 * 
	 * @param aSt
	 * @return Array com os ids dos jogos encontraods
	 */
	public String[] listaUserInactivoArray(Statement aSt, int aId) {

		ResultSet rs = null;
		int contador = 0;

		try {

			rs = aSt.executeQuery("select jo_id from jogo where u_id = " + aId);

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				while (rs.next()) {
					contador++;
				}
			}

			rs = aSt.executeQuery("select jo_id from jogo where u_id = " + aId);

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				String[] rowData = new String[contador];
				contador = 0;
				while (rs.next()) {
					rowData[contador] = rs.getString(1);
					contador++;
				}
				return rowData;
			}
		} catch (SQLException e) {
			return null;
		}
	}
	
}
