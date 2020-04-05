package projecto_3.graficos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import projecto_3.informGerais.Log;
import projecto_3.utilizador.GereAdministrador;
import projecto_3.utilizador.GereJogador;
import projecto_3.utilizador.GereUtilizador;
import projecto_3.utilizador.Jogador;
import projecto_3.utilizador.Utilizador;
import projecto_3.ClassePrincipal;
import projecto_3.informGerais.GereLog;
import projecto_3.informGerais.GereNotificacao;

public class JanelaRegisto extends JFrame {

	private Container cont;
	private String nomeLogin = null;
	private JRadioButton tipoAdmin, tipoJogador;
	private JButton botaoOK, botaoCanc;
	private JTextField caixaLogin, caixaPassword, caixaNome, caixaEmail;
	private GereUtilizador aUtilizador = new GereUtilizador();
	private GereJogador aJogador = new GereJogador();
	private GereAdministrador aAdministrador = new GereAdministrador();
	private GereLog aLog = new GereLog();
	private GereNotificacao aNotificacao = new GereNotificacao();
	private Statement st = null;
	private boolean activo = false;

	/**
	 * Cria uma janela de registo
	 * @param aListener
	 * @param aAdmin
	 * @param primeiro
	 */
	public JanelaRegisto(ClassePrincipal aListener, boolean aAdmin, boolean primeiro) {
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		activo = aAdmin;
		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTopo.add(new JLabel("Janela de Registo"));

		GridLayout gl = new GridLayout(6, 2);
		gl.setHgap(2);
		gl.setVgap(2);
		JPanel painelLogin = new JPanel(gl);

		JPanel painelLabelLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelLogin.add(new JLabel("Login"));

		JPanel painelLabelPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelPassword.add(new JLabel("Password"));

		JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelNome.add(new JLabel("Nome"));

		JPanel painelLabelEmail = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelEmail.add(new JLabel("Email"));

		JPanel painelLabelTipo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelTipo.add(new JLabel("Tipo de utilizador:"));

		JPanel painelCaixaLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaLogin = new JTextField(10);
		painelCaixaLogin.add(caixaLogin);
		caixaLogin.setToolTipText("Insira aqui o seu login!");

		JPanel painelCaixaPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaPassword = new JPasswordField(10);
		painelCaixaPassword.add(caixaPassword);
		caixaPassword.setToolTipText("Insira aqui a sua password!");

		JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaNome = new JTextField(10);
		painelCaixaNome.add(caixaNome);
		caixaNome.setToolTipText("Insira aqui o seu nome!");

		JPanel painelCaixaEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaEmail = new JTextField(15);
		painelCaixaEmail.add(caixaEmail);
		caixaEmail.setToolTipText("Insira aqui o seu email no formato (example@example.example)!");

		JPanel painelCaixaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		if (primeiro) {
			tipoAdmin = new JRadioButton("  Administrador  ", true);
			tipoJogador = new JRadioButton("  Jogador  ");
			tipoAdmin.setEnabled(false);
			tipoJogador.setEnabled(false);
		} else {
			tipoAdmin = new JRadioButton("  Administrador  ");
			tipoJogador = new JRadioButton("  Jogador  ", true);
		}
		ButtonGroup group = new ButtonGroup();
		group.add(tipoAdmin);
		group.add(tipoJogador);
		painelCaixaTipo.add(tipoAdmin);
		painelCaixaTipo.add(tipoJogador);
		tipoAdmin.setToolTipText("Selecione esta opcção para registar um utilizador do tipo Administrador.");
		tipoJogador.setToolTipText("Selecione esta opcção para registar um utilizador do tipo Jogador.");

		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botaoCanc = new JButton("  Voltar  ");
		botaoCanc.setActionCommand("voltarRegisto");
		botaoCanc.addActionListener(aListener);
		painelBotao.add(botaoCanc);

		botaoOK = new JButton("  Registar  ");
		botaoOK.setActionCommand("registar");
		botaoOK.addActionListener(aListener);
		painelBotao.add(botaoOK);

		painelLogin.add(painelLabelLogin);
		painelLogin.add(painelCaixaLogin);

		painelLogin.add(painelLabelPassword);
		painelLogin.add(painelCaixaPassword);

		painelLogin.add(painelLabelNome);
		painelLogin.add(painelCaixaNome);

		painelLogin.add(painelLabelEmail);
		painelLogin.add(painelCaixaEmail);

		painelLogin.add(painelLabelTipo);
		painelLogin.add(painelCaixaTipo);

		painelLogin.add(new JPanel());
		painelLogin.add(painelBotao);

		cont.add(painelTopo, BorderLayout.NORTH);
		cont.add(painelLogin, BorderLayout.CENTER);
		cont.add(new JPanel(), BorderLayout.SOUTH);
	}

	/**
	 * Lê os dados introduzidos pelo utilizador
	 * @param aSt
	 * @return resultado da função regista utilizador ou se ocorreu um erro
	 */
	public String regista(Statement aSt) {
		st = aSt;
		String login = caixaLogin.getText();
		String password = caixaPassword.getText();
		String nome = caixaNome.getText();
		String email = caixaEmail.getText();
		boolean emailVerificado = verificaEmail(email);
		boolean loginVerificado = aUtilizador.verificaLogin(login, st);
		int tipo = 0;

		if (tipoAdmin.isSelected()) {
			tipo = 1;
		} else {
			tipo = 2;
		}

		if (login.length() > 0 && password.length() > 0 && nome.length() > 0 && emailVerificado && loginVerificado)
			return (registaUser(login, password, nome, email, tipo));
		else {
			String mensagem = "";

			if (login.length() == 0 || password.length() == 0 || nome.length() == 0 || email.length() == 0) {
				mensagem = "Todos os campos devem ser preenchidos!";
			} else if (!emailVerificado) {
				mensagem = "Inseriu um email num formato inválido ou o mesmo já existe no sistema";
			} else if (!loginVerificado) {
				mensagem = "O login que inseriu já existe! Por favor, escolha outro!";
			}

			JOptionPane.showMessageDialog(this, mensagem, "Informação", JOptionPane.ERROR_MESSAGE);
			return ".";
		}
	}

	/**
	 * Verifica o email.
	 * 
	 * @return Email.
	 */
	private boolean verificaEmail(String aEmail) {
		boolean arroba = false, ponto = false;
		int iarroba = 0;
		for (int i = 0; i < aEmail.length(); i++) {
			if (arroba) {
				if (aEmail.charAt(i) == '.' && (i - iarroba >= 2) && (aEmail.length() - i >= 2)) {
					ponto = true;
				}
			}
			if (aEmail.charAt(i) == '@' && i >= 1) {
				arroba = true;
				iarroba = i;
			}
		}
		if (arroba && ponto) {
			if (aUtilizador.verificaEmail(aEmail, st)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Regista um utilizador
	 * @param aLogin
	 * @param aPassword
	 * @param aNome
	 * @param aEmail
	 * @param aTipo
	 * @return String a indicar se correu tudo bem
	 */
	private String registaUser(String aLogin, String aPassword, String aNome, String aEmail, int aTipo) {
		nomeLogin = aLogin;
		if (aTipo == 1) {
			Utilizador utilizador = new Utilizador(aNome, aLogin, aPassword, aEmail, activo, 'A', false);
			if (aAdministrador.registaAdministrador(utilizador, st)) {
				aNotificacao.registaNotificacaoAdmin(
						"O utilizador com o login: " + aLogin + " criou uma conta do tipo Administrador.", st);
				registaLog("Registou um administrador com login: " + aLogin + ".");
				return ":)";
			} else {
				return "";
			}
		} else if (aTipo == 2) {
			Jogador jogador = new Jogador(aNome, aLogin, aPassword, activo, aEmail, false, 0, 0, "00:00:00");
			if (aJogador.registaJogador(jogador, st)) {
				aNotificacao.registaNotificacaoAdmin(
						"O utilizador com o login: " + aLogin + " criou uma conta do tipo Jogador.", st);
				registaLog("Registou um jogador com login: " + aLogin + ".");
				return ":)";
			} else {
				return "";
			}
		}
		return "";
	}

	/**
	 * Regista os logs.
	 * 
	 * @param aAccao
	 */
	private void registaLog(String aAccao) {

		String data = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

		if (nomeLogin == null) {
			Log log = new Log(data, hora, "convidado", aAccao);
			aLog.criaLog(log, st);
		} else {
			Log log = new Log(data, hora, nomeLogin, aAccao);
			aLog.criaLog(log, st);
		}

	}

}
