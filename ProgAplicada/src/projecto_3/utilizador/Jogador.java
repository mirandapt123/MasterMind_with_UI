package projecto_3.utilizador;

public class Jogador extends Utilizador{
	
	private int numJogos;
	private int numVitorias;
	private String tempoTotal;
	
	/**
	 * Adiciona um Jogador sem id.
	 * @param aNome
	 * @param aLogin
	 * @param aPassword
	 * @param aEstado
	 * @param aEmail
	 * @param aEstadoReprovacao
	 * @param aNumJogos
	 * @param aNumVitorias
	 * @param aTempoTotal
	 */
	public Jogador(String aNome, String aLogin, String aPassword, boolean aEstado, String aEmail, boolean aEstadoReprovacao, int aNumJogos, int aNumVitorias, String aTempoTotal) {
		super(aNome, aLogin, aPassword, aEmail, aEstado, 'J', aEstadoReprovacao);
        numJogos = aNumJogos;
        numVitorias = aNumVitorias;
        tempoTotal = aTempoTotal;
	}
	
	/**
	 * Adiciona um Jogador com id.
	 * @param aId
	 * @param aNome
	 * @param aLogin
	 * @param aPassword
	 * @param aEstado
	 * @param aEmail
	 * @param aEstadoReprovacao
	 * @param aNumJogos
	 * @param aNumVitorias
	 * @param aTempoTotal
	 */
	public Jogador(int aId, String aNome, String aLogin, String aPassword, boolean aEstado, String aEmail, boolean aEstadoReprovacao, int aNumJogos, int aNumVitorias, String aTempoTotal) {
		super(aId, aNome, aLogin, aPassword, aEmail, aEstado, 'J', aEstadoReprovacao);
        numJogos = aNumJogos;
        numVitorias = aNumVitorias;
        tempoTotal = aTempoTotal;
	}

	public int getNumJogos() {
		return numJogos;
	}

	public int getNumVitorias() {
		return numVitorias;
	}

	public String getTempoTotal() {
		return tempoTotal;
	}
	
	public int getId() {
		return id;
	}
	
public String toString() {
		
		String listagem = "";
		if (estado) {
			listagem += "Utilizador '" + nome + "':\n[Id= "+id+", Nome= " + nome + ", Login= " + login + ", Password= " + password
					+ ", Estado= Activo, \nEmail= " + email + ", Tipo= " + tipo + ", Número de jogos= "
					+ numJogos + ", Número de vitórias= " + numVitorias + ",\nTempo total acumulado de jogo= " + tempoTotal;
			
		} else {
			listagem += "Utilizador '" + nome + "':\n[Id= "+id+", Nome= " + nome + ", Login= " + login + ", Password= " + password
					+ ", Estado= Inactivo, \nEmail= " + email + ", Tipo= " + tipo + ", Número de jogos= "
					+ numJogos + ", Número de vitórias= " + numVitorias + ",\nTempo total acumulado de jogo= " + tempoTotal;
		}
		
		if (estadoReprovacao) {
			listagem += ", Estado de Reprovação= Reprovado]\n\n";
		} else {
			listagem += ", Estado de Reprovação= Não reprovado]\n\n";
		}
		return listagem;
	}
	
	
	
	
}
