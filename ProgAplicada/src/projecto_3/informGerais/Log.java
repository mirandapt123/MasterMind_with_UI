package projecto_3.informGerais;

public class Log {

	private String data;
	private String hora;
	private String utilizador;
	private String accao;
	
	/**
	 * Insere um log com todos os dados.
	 * @param aData
	 * @param aHora
	 * @param aUtilizador
	 * @param aAccao
	 */
	public Log (String aData, String aHora, String aUtilizador, String aAccao) {
		data = aData;
		hora = aHora;
		utilizador = aUtilizador;
		accao = aAccao;
	}
	
	public String getData() {
		return data;
	}

	public String getHora() {
		return hora;
	}

	public String getUtilizador() {
		return utilizador;
	}

	public String getAccao() {
		return accao;
	}

	public String toString() {
		return "<" + data + "> <" + hora + "> <" + utilizador + "> <" + accao + ">\n\n";
	}
	
	
	
}
