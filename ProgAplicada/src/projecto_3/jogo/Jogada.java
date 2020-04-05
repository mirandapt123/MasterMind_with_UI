package projecto_3.jogo;

public class Jogada {
	
	int id;
	int idJogo;
	String accao;
	
	/**
	 * Constructor que recebe todos os dados mandatórios
	 * @param aId
	 * @param aIdJogo
	 * @param aAccao
	 */
	public Jogada (int aId, int aIdJogo,String aAccao) {
		id = aId;
		idJogo = aIdJogo;
		accao = aAccao;
	}
	
	/**
	 * Constructor que sem id
	 * @param aIdJogo
	 * @param aAccao
	 */
	public Jogada (int aIdJogo,String aAccao) {
		idJogo = aIdJogo;
		accao = aAccao;
	}
	
	public int getId() {
		return id;
	}

	public int getIdJogo() {
		return idJogo;
	}
	
	public String getAccao() {
		return accao;
	}

	public String toString() {
		return "Jogada [Id da jogada= " + id + ", Id do jogo= " + idJogo + ", Acção= " + accao + "]\n";
	}
	
	
	

}
