package projecto_3.informGerais;

public class Notificacao {

	private String mensagem;
	
	/**
	 * Insere uma notificação com a mensagem.
	 * @param aMensagem
	 */
	public Notificacao (String aMensagem) {
		mensagem = aMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String toString() {
		return "Notificacao [Mensagem= " + mensagem + "]\n\n";
	}
	
	
	
}
