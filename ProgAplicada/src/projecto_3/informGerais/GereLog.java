package projecto_3.informGerais;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableModel;

public class GereLog {

	/**
	 * Verifica a quantidade de logs existentes na base de dados.
	 * 
	 * @param aComando
	 * @param aSt
	 * @return número de logs existentes na base de dados.
	 */
	public int verificaQuantidadeLog(String aComando, Statement aSt) {
		ResultSet rs = null;

		try {

			rs = aSt.executeQuery(" SELECT count(*) FROM logs " + aComando + ";");

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
	 * Regista um log na base de dados.
	 * 
	 * @param aLog
	 * @param aSt
	 * @return true se não ocorrer um erro, false se ocorrer.
	 */
	public boolean criaLog(Log aLog, Statement aSt) {

		try {
			String startDate = aLog.getData();
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date date = sdf1.parse(startDate);
			java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

			aSt.execute("Insert INTO logs (l_data_log, l_hora, l_utilizador, l_accao)" + " VALUES ('" + sqlStartDate
					+ "','" + aLog.getHora() + "','" + aLog.getUtilizador() + "','" + aLog.getAccao() + "');");

			return true;
		} catch (SQLException e) {
			return false;
		} catch (ParseException pe) {
			return false;
		}
	}

	/**
	 * Lista os logs.
	 * 
	 * @param aComando
	 * @param aSt
	 * @param model
	 * @return tabela com os dados dos logs.
	 */
	public DefaultTableModel listaLog(String aComando, Statement aSt, DefaultTableModel model) {

		ResultSet rs = null;

		try {

			rs = aSt.executeQuery("select * from logs " + aComando);

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				Object[] rowData = new Object[4];
				while (rs.next()) {
					Log log = new Log(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
					rowData[0] = log.getData();
					rowData[1] = log.getHora();
					rowData[2] = log.getUtilizador();
					rowData[3] = log.getAccao();
					model.addRow(rowData);
				}
				return model;
			}
		} catch (SQLException e) {
			return null;
		}
	}

}
