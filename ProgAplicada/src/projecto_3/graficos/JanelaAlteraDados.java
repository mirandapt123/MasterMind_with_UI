package projecto_3.graficos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import projecto_3.JanelaMenu;
import projecto_3.informGerais.GereLog;
import projecto_3.informGerais.GereNotificacao;
import projecto_3.informGerais.Log;
import projecto_3.utilizador.GereAdministrador;
import projecto_3.utilizador.GereJogador;
import projecto_3.utilizador.GereUtilizador;
import projecto_3.utilizador.Utilizador;

public class JanelaAlteraDados extends JFrame implements ActionListener {

	private Container cont;
	private String nomeLogin = null, emailActual = null, nomeUtilizador = null, nomeLoginPrincipal = null;
	private JRadioButton tipoAdmin, tipoJogador;
	private JButton botaoOK, botaoCanc;
	private JTextField caixaLogin, caixaPassword, caixaNome, caixaEmail;
	private GereUtilizador aUtilizador = new GereUtilizador();
	private GereJogador aJogador = new GereJogador();
	private GereAdministrador aAdministrador = new GereAdministrador();
	private GereLog aLog = new GereLog();
	private GereNotificacao aNotificacao = new GereNotificacao();
	private Statement st = null;
	private int tipo1 = 0, alteraProprio = 0;
	private long tempoInicial = 0;

	/**
	 * Janela que altera dados
	 * @param aTipo
	 * @param aLogin
	 * @param aAlteraProprio
	 * @param aSt
	 * @param aTempoInicial
	 */
	public JanelaAlteraDados(int aTipo, String aLogin, int aAlteraProprio, Statement aSt, long aTempoInicial) {
		st = aSt;
		alteraProprio = aAlteraProprio;
		tempoInicial = aTempoInicial;
		tipo1 = aTipo;
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		String[] dados = aUtilizador.devolveDados(aLogin, st);

		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTopo.add(new JLabel("Janela de Alteração de Dados"));

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
		caixaLogin.setText(dados[1]);
		nomeLogin = dados[1];
		painelCaixaLogin.add(caixaLogin);
		caixaLogin.setToolTipText("Insira aqui o seu login!");

		JPanel painelCaixaPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaPassword = new JPasswordField(10);
		caixaPassword.setText(dados[2]);
		painelCaixaPassword.add(caixaPassword);
		caixaPassword.setToolTipText("Insira aqui a sua password!");

		JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaNome = new JTextField(10);
		caixaNome.setText(dados[0]);
		nomeUtilizador = dados[0];
		painelCaixaNome.add(caixaNome);
		caixaNome.setToolTipText("Insira aqui o seu nome!");

		JPanel painelCaixaEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaEmail = new JTextField(15);
		caixaEmail.setText(dados[3]);
		emailActual = dados[3];
		painelCaixaEmail.add(caixaEmail);
		caixaEmail.setToolTipText("Insira aqui o seu email no formato (example@example.example)!");

		JPanel painelCaixaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		if (aTipo == 1) {
			tipoAdmin = new JRadioButton("  Administrador  ", true);
			tipoJogador = new JRadioButton("  Jogador  ");
		} else if (aTipo == 2 && aAlteraProprio == 1) {
			tipoAdmin = new JRadioButton("  Administrador  ");
			tipoJogador = new JRadioButton("  Jogador  ", true);
		} else {
			tipoAdmin = new JRadioButton("  Administrador  ");
			tipoJogador = new JRadioButton("  Jogador  ", true);
			tipoAdmin.setEnabled(false);
			tipoJogador.setEnabled(false);
		}

		ButtonGroup group = new ButtonGroup();
		group.add(tipoAdmin);
		group.add(tipoJogador);
		painelCaixaTipo.add(tipoAdmin);
		painelCaixaTipo.add(tipoJogador);
		tipoAdmin.setToolTipText("Selecione esta opcção para colocar um utilizador do tipo Administrador.");
		tipoJogador.setToolTipText("Selecione esta opcção para colocar um utilizador do tipo Jogador.");

		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botaoCanc = new JButton("  Cancelar  ");
		botaoCanc.setActionCommand("botaoCanc");
		botaoCanc.addActionListener(this);
		painelBotao.add(botaoCanc);

		botaoOK = new JButton("  Alterar dados  ");
		botaoOK.setActionCommand("botaoOK");
		botaoOK.addActionListener(this);
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
	 * Janela que altera dados de outro user
	 * @param aTipo
	 * @param aLoginPrincipal
	 * @param aLogin
	 * @param aAlteraProprio
	 * @param aSt
	 * @param aTempoInicial
	 */
	public JanelaAlteraDados(int aTipo, String aLoginPrincipal, String aLogin, int aAlteraProprio, Statement aSt,
			long aTempoInicial) {
		st = aSt;
		alteraProprio = aAlteraProprio;
		tempoInicial = aTempoInicial;
		tipo1 = aTipo;
		nomeLoginPrincipal = aLoginPrincipal;
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		String[] dados = aUtilizador.devolveDados(aLogin, st);

		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTopo.add(new JLabel("Janela de Alteração de Dados"));

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
		caixaLogin.setText(dados[1]);
		nomeLogin = dados[1];
		painelCaixaLogin.add(caixaLogin);
		caixaLogin.setToolTipText("Insira aqui o seu login!");

		JPanel painelCaixaPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaPassword = new JPasswordField(10);
		caixaPassword.setText(dados[2]);
		painelCaixaPassword.add(caixaPassword);
		caixaPassword.setToolTipText("Insira aqui a sua password!");

		JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaNome = new JTextField(10);
		caixaNome.setText(dados[0]);
		nomeUtilizador = dados[0];
		painelCaixaNome.add(caixaNome);
		caixaNome.setToolTipText("Insira aqui o seu nome!");

		JPanel painelCaixaEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaEmail = new JTextField(15);
		caixaEmail.setText(dados[3]);
		emailActual = dados[3];
		painelCaixaEmail.add(caixaEmail);
		caixaEmail.setToolTipText("Insira aqui o seu email no formato (example@example.example)!");

		JPanel painelCaixaTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		if (aTipo == 1) {
			tipoAdmin = new JRadioButton("  Administrador  ", true);
			tipoJogador = new JRadioButton("  Jogador  ");
		} else if (aTipo == 2 && aAlteraProprio == 1) {
			tipoAdmin = new JRadioButton("  Administrador  ");
			tipoJogador = new JRadioButton("  Jogador  ", true);
		} else {
			tipoAdmin = new JRadioButton("  Administrador  ");
			tipoJogador = new JRadioButton("  Jogador  ", true);
			tipoAdmin.setEnabled(false);
			tipoJogador.setEnabled(false);
		}

		ButtonGroup group = new ButtonGroup();
		group.add(tipoAdmin);
		group.add(tipoJogador);
		painelCaixaTipo.add(tipoAdmin);
		painelCaixaTipo.add(tipoJogador);
		tipoAdmin.setToolTipText("Selecione esta opcção para colocar um utilizador do tipo Administrador.");
		tipoJogador.setToolTipText("Selecione esta opcção para colocar um utilizador do tipo Jogador.");

		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botaoCanc = new JButton("  Cancelar  ");
		botaoCanc.setActionCommand("botaoCanc");
		botaoCanc.addActionListener(this);
		painelBotao.add(botaoCanc);

		botaoOK = new JButton("  Alterar dados  ");
		botaoOK.setActionCommand("botaoOK");
		botaoOK.addActionListener(this);
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
	 * Lê os dados introduzidos e altera os mesmos
	 * @param aSt
	 */
	public void altera(Statement aSt) {
		boolean loginVerificado = false, emailVerificado = false;
		st = aSt;
		String login = caixaLogin.getText();
		String password = caixaPassword.getText();
		String nome = caixaNome.getText();
		String email = caixaEmail.getText();
		if (email.equals(emailActual)) {
			emailVerificado = true;
		} else {
			emailVerificado = verificaEmail(email);
		}

		if (login.equals(nomeLogin)) {
			loginVerificado = true;
		} else {
			loginVerificado = aUtilizador.verificaLogin(login, st);
		}

		int tipo = 0;

		if (tipoAdmin.isSelected()) {
			tipo = 1;
		} else {
			tipo = 2;
		}

		if (login.length() > 0 && password.length() > 0 && nome.length() > 0 && emailVerificado && loginVerificado) {
			if (tipo == 1) {
				Utilizador admin = new Utilizador(nome, login, password, email, true, 'A', false);
				if (aAdministrador.actualizaDadosAdmin(admin, nomeLogin, st)) {
					JOptionPane.showMessageDialog(this, "Dados alterados com sucesso!", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					registaLog("Alterou os dados do utilizador com login '" + login + "'.");
					setVisible(false);
					nomeLogin = login;
					nomeUtilizador = nome;
					tipo1 = 1;
				} else {
					JOptionPane.showMessageDialog(this, "Ocorreu um erro a alterar os dados!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				Utilizador jogador = new Utilizador(nome, login, password, email, true, 'J', false);
				if (aAdministrador.actualizaDadosAdmin(jogador, nomeLogin, st)) {
					JOptionPane.showMessageDialog(this, "Dados alterados com sucesso!", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
					registaLog("Alterou os dados do utilizador com login '" + login + "'.");
					setVisible(false);
					nomeLogin = login;
					nomeUtilizador = nome;
					tipo1 = 1;
				} else {
					JOptionPane.showMessageDialog(this, "Ocorreu um erro a alterar os dados!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			String mensagem = "";

			if (login.length() == 0 || password.length() == 0 || nome.length() == 0 || email.length() == 0) {
				mensagem = "Todos os campos devem ser preenchidos!";
			} else if (!emailVerificado) {
				mensagem = "Inseriu um email num formato inválido ou o mesmo já existe no sistema";
			} else if (!loginVerificado) {
				mensagem = "O login que inseriu já existe! Por favor, escolha outro!";
			}
			JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gestor de eventos
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(botaoCanc)) {
			setVisible(false);
			if (alteraProprio == 0) {
				JanelaMenu menu = new JanelaMenu(tipo1, nomeLogin, nomeUtilizador, tempoInicial, st, 0);
			} else {
				JanelaMenu menu = new JanelaMenu(1, nomeLoginPrincipal,
						aUtilizador.getNomeUtilizador(nomeLoginPrincipal, st), tempoInicial, st, 0);
			}
		}

		if (e.getSource().equals(botaoOK)) {
			altera(st);
			if (alteraProprio == 0) {
				JanelaMenu menu = new JanelaMenu(tipo1, nomeLogin, nomeUtilizador, tempoInicial, st, 0);
			} else {
				JanelaMenu menu = new JanelaMenu(1, nomeLoginPrincipal,
						aUtilizador.getNomeUtilizador(nomeLoginPrincipal, st), tempoInicial, st, 0);
			}

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
