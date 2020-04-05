package projecto_3.jogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class GereJogada {

	/**
	 * Verifica a quantidade de jogadas num jogo.
	 * 
	 * @param aComando
	 * @param aSt
	 * @return int com a quantidade de jogos.
	 */
	public int verificaQuantidadeJogada(String aComando, Statement aSt) {
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
	 * Regista uma jogada
	 * 
	 * @param aJogada
	 * @param aSt
	 * @return false se ocorreu um erro, true se foi registado com sucesso.
	 */
	public boolean registaJogada(Jogada aJogada, Statement aSt) {

		try {
			aSt.execute("Insert INTO jogada (jo_id, JOG_ACCAO) Values ("+aJogada.getIdJogo()+", '"+aJogada.getAccao()+"')");
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
    /**
     * Lista todas as jogadas de um jogo
     * @param idJogo
     * @param aSt
     * @return vector de jogadas
     */
	public Vector<Jogada> ListaJogada(String idJogo,  Statement aSt) {

		ResultSet rs = null;

		try {
			
			rs = aSt.executeQuery(" SELECT * FROM jogada where jo_id = '" + idJogo+ "';");

			if (rs == null) {
				return null;
			} else if (!rs.isBeforeFirst()) {
				return null;
			} else {
				Vector<Jogada> listaJogada;
				listaJogada = new Vector<Jogada>();
				Jogada aDados;
				while (rs != null && rs.next()) {
					aDados = new Jogada(rs.getInt(1), rs.getInt(2), rs.getString(3));
					listaJogada.addElement(aDados);
				}
				return listaJogada;
			}
		} catch (SQLException e) {
			return null;
		}
	}
	
}
