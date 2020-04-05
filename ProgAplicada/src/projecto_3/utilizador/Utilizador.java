package projecto_3.utilizador;

public class Utilizador {
	
	protected int id;
	protected String nome;
	protected String login;
	protected String password;
	protected boolean estado;
	protected String email;
	protected char tipo;
	protected boolean estadoReprovacao;
	
	/**
	 * Insere um utilizador com id.
	 * @param aId
	 * @param aNome
	 * @param aLogin
	 * @param aPassword
	 * @param aEmail
	 * @param aEstado
	 * @param aTipo
	 * @param aEstadoReprovacao
	 */
	public Utilizador(int aId, String aNome, String aLogin, String aPassword, String aEmail, boolean aEstado, char aTipo, boolean aEstadoReprovacao) {
		id = aId;
		nome = aNome;
		login = aLogin;
		password = aPassword;
		estado = aEstado;
		email = aEmail;
		tipo = aTipo;
		estadoReprovacao = aEstadoReprovacao;
	}
	
	/**
	 * Insere um utilizador sem id.
	 * @param aNome
	 * @param aLogin
	 * @param aPassword
	 * @param aEmail
	 * @param aEstado
	 * @param aTipo
	 * @param aEstadoReprovacao
	 */
	public Utilizador(String aNome, String aLogin, String aPassword, String aEmail, boolean aEstado, char aTipo, boolean aEstadoReprovacao) {
		nome = aNome;
		login = aLogin;
		password = aPassword;
		estado = aEstado;
		email = aEmail;
		tipo = aTipo;
		estadoReprovacao = aEstadoReprovacao;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean getEstado() {
		return estado;
	}

	public String getEmail() {
		return email;
	}

	public char getTipo() {
		return tipo;
	}

	public boolean getEstadoReprovacao() {
		return estadoReprovacao;
	}
	
	public String toString() {
		String listagem = "";
		if (estado) {
			listagem += "Utilizador '"+nome+"':\n[Id= "+id+", Nome= " + nome + ", Login= " + login + ", Password= " + password
					+ ", Estado= Activo, \nEmail= " + email + ", Tipo= " + tipo;
		} else {
			listagem += "Utilizador '"+nome+"':\n[Id= "+id+", Nome= " + nome + ", Login= " + login
					+ ", Password= ******, Estado= Inactivo, \nEmail= " + email + ", Tipo= " + tipo;
		}

		if (estadoReprovacao) {
			listagem += ", Estado de Reprovação= Reprovado]\n\n";
		} else {
			listagem += ", Estado de Reprovação= Não reprovado]\n\n";
		}
		return listagem;
	}
	

}
