package projecto_3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import projecto_3.graficos.JanelaAlteraDados;
import projecto_3.graficos.JanelaAlteraDadosDB;
import projecto_3.graficos.JanelaTabelaJogo;
import projecto_3.informGerais.GereLog;
import projecto_3.informGerais.GereNotificacao;
import projecto_3.informGerais.Log;
import projecto_3.jogo.GereJogo;
import projecto_3.utilizador.GereAdministrador;
import projecto_3.utilizador.GereJogador;
import projecto_3.utilizador.GereUtilizador;

public class JanelaMenu extends JFrame implements ActionListener {
	private JPanel panel;
	private JList list;
	private JFrame janelaAlteraDados;
	private JMenuBar bar;
	private JTextField caixaNome;
	private JButton botaoPesqLog, botaoCanc, botaoAlt, botaoPesqJog, aprovaUserBotao, reprovaUserBotao,
			alterarOsMeusDados1, botaoInactiva, botaoJogarPC, simular, listarUti, criarButt, aprovarButt,
			listarJogosButt, simularButt, jogarPcButt;
	private JMenu fileMenu, menuLogin, baseDados;
	private JMenuItem aboutItem, exitItem, login, registar, logout, listarTodosLogs, pesquisarLog, verDadosDB,
			alterarDadosDB, listarUser, pesquisaJogador, listarJog, listarUserAprov, listarUserReprovado, aprovaUser,
			reprovaUser, listarOsMeusDados, alterarOsMeusDados, alterarDadosOutros, criaNovoUser, inactivarConta,
			listarJogo, jogarVsPc, simularJogo;
	private String nomeLogin = "", nomeUtilizador = "Convidado";
	private GereLog aLog = new GereLog();
	private GereBaseDados aBaseDados = new GereBaseDados();
	private GereUtilizador aUtilizador = new GereUtilizador();
	private GereJogador aJogador = new GereJogador();
	private GereJogo aJogo = new GereJogo();
	private GereNotificacao aNotificacao = new GereNotificacao();
	private GereAdministrador aAdministrador = new GereAdministrador();
	private long tempoInicial = -1;
	private Statement st = null;
	private int tipo, id;

	/**
	 * Cria a janela secundária
	 * @param aTempoInicial
	 */
	public JanelaMenu(long aTempoInicial) {
		tempoInicial = aTempoInicial;
		bar = new JMenuBar();

		fileMenu = new JMenu("Ficheiro");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		aboutItem = new JMenuItem("Acerca...");
		aboutItem.setMnemonic(KeyEvent.VK_A);
		aboutItem.addActionListener(this);

		exitItem = new JMenuItem("Sair");
		exitItem.setMnemonic(KeyEvent.VK_S);
		exitItem.addActionListener(this);

		fileMenu.add(aboutItem);
		fileMenu.add(exitItem);

		bar.add(fileMenu);

		menuLogin = new JMenu("Login/Registar");
		menuLogin.setMnemonic(KeyEvent.VK_L);

		login = new JMenuItem("Login");
		login.setMnemonic(KeyEvent.VK_L);
		login.addActionListener(this);

		registar = new JMenuItem("Registar");
		registar.setMnemonic(KeyEvent.VK_R);
		registar.addActionListener(this);

		menuLogin.add(login);
		menuLogin.add(registar);

		bar.add(menuLogin);

		baseDados = new JMenu("Base de dados");
		baseDados.setMnemonic(KeyEvent.VK_B);

		verDadosDB = new JMenuItem("Ver dados de acesso à base de dados");
		verDadosDB.setMnemonic(KeyEvent.VK_V);
		verDadosDB.addActionListener(this);

		alterarDadosDB = new JMenuItem("Alterar dados de acesso à base de dados");
		alterarDadosDB.setMnemonic(KeyEvent.VK_A);
		alterarDadosDB.addActionListener(this);

		baseDados.add(verDadosDB);
		baseDados.add(alterarDadosDB);

		bar.add(baseDados);

		setJMenuBar(bar);

		getContentPane().removeAll();

		JPanel painelPrincipal = new JPanel(new FlowLayout());
		JLabel jlabel = new JLabel("More inf:zemiranda99@gmail.com");
		jlabel.setFont(new Font("Verdana", 1, 20));
		JLabel jlabel1 = new JLabel("Efectue o registo/login e venha experimentar!");
		jlabel1.setFont(new Font("Verdana", 1, 15));
		painelPrincipal.add(jlabel);
		painelPrincipal.add(Box.createHorizontalStrut(120));
		painelPrincipal.add(jlabel1);
		TitledBorder border = new TitledBorder("Geral.");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);
		painelPrincipal.setBorder(border);

		getContentPane().add(painelPrincipal);
		setVisible(true);

		getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(200, 200, 500, 400));
		setTitle(".:: MasterMind ::. (Sem utilizador logado).");
		setVisible(true);
	}

	/**
	 * Cria a janela principal
	 * @param aTipo
	 * @param aLogin
	 * @param aNome
	 * @param aTempoInicial
	 * @param aSt
	 * @param aMostraNotificacao
	 */
	public JanelaMenu(int aTipo, String aLogin, String aNome, long aTempoInicial, Statement aSt,
			int aMostraNotificacao) {
		st = aSt;
		tempoInicial = aTempoInicial;
		nomeLogin = aLogin;
		nomeUtilizador = aNome;
		tipo = aTipo;

		if (aTipo == 1) {
			bar = new JMenuBar();

			fileMenu = new JMenu("Ficheiro");
			fileMenu.setMnemonic(KeyEvent.VK_F);

			aboutItem = new JMenuItem("Acerca...");
			aboutItem.setMnemonic(KeyEvent.VK_A);
			aboutItem.addActionListener(this);

			exitItem = new JMenuItem("Sair do programa");
			exitItem.setMnemonic(KeyEvent.VK_S);
			exitItem.addActionListener(this);

			logout = new JMenuItem("Fazer 'logout'");
			logout.setMnemonic(KeyEvent.VK_F);
			logout.addActionListener(this);

			fileMenu.add(aboutItem);
			fileMenu.add(exitItem);
			fileMenu.add(logout);

			bar.add(fileMenu);

			JMenuItem log = new JMenu("Ver log(s)");
			log.setMnemonic(KeyEvent.VK_E);

			listarTodosLogs = new JMenuItem("Listar todos os logs");
			listarTodosLogs.setMnemonic(KeyEvent.VK_L);
			listarTodosLogs.addActionListener(this);

			pesquisarLog = new JMenuItem("Pesquisar logs de um determinado utilizador");
			pesquisarLog.setMnemonic(KeyEvent.VK_L);
			pesquisarLog.addActionListener(this);

			log.add(listarTodosLogs);
			log.add(pesquisarLog);

			bar.add(log);

			JMenuItem gerirUserAdmin = new JMenu("Gestão de utilizadores");
			gerirUserAdmin.setMnemonic(KeyEvent.VK_G);

			listarUserAprov = new JMenuItem("Listar utilizador(es) que aguarda(m) aprovação");
			listarUserAprov.setMnemonic(KeyEvent.VK_L);
			listarUserAprov.addActionListener(this);

			listarUserReprovado = new JMenuItem("Listar todos os utilizadores reprovados");
			listarUserReprovado.setMnemonic(KeyEvent.VK_L);
			listarUserReprovado.addActionListener(this);

			JMenuItem aprovarReprovarUser = new JMenu("Aprovar/Reprovar utilizadores");
			aprovarReprovarUser.setMnemonic(KeyEvent.VK_A);

			aprovaUser = new JMenuItem("Aprovar um utilizador");
			aprovaUser.setMnemonic(KeyEvent.VK_L);
			aprovaUser.addActionListener(this);

			reprovaUser = new JMenuItem("Reprovar um utilizador");
			reprovaUser.setMnemonic(KeyEvent.VK_L);
			reprovaUser.addActionListener(this);

			aprovarReprovarUser.add(aprovaUser);
			aprovarReprovarUser.add(reprovaUser);

			listarOsMeusDados = new JMenuItem("Listar os meus dados de utilizador");
			listarOsMeusDados.setMnemonic(KeyEvent.VK_L);
			listarOsMeusDados.addActionListener(this);

			JMenuItem menuAlterarDados = new JMenu("Alterar dados de utilizador");
			menuAlterarDados.setMnemonic(KeyEvent.VK_A);

			alterarOsMeusDados = new JMenuItem("Alterar os meus dados");
			alterarOsMeusDados.setMnemonic(KeyEvent.VK_L);
			alterarOsMeusDados.addActionListener(this);

			alterarDadosOutros = new JMenuItem("Alterar dados de outros utilizadores");
			alterarDadosOutros.setMnemonic(KeyEvent.VK_T);
			alterarDadosOutros.addActionListener(this);

			menuAlterarDados.add(alterarOsMeusDados);
			menuAlterarDados.add(alterarDadosOutros);

			criaNovoUser = new JMenuItem("Criar um novo utilizador");
			criaNovoUser.setMnemonic(KeyEvent.VK_C);
			criaNovoUser.addActionListener(this);

			listarUser = new JMenuItem("Listar todos os utilizadores");
			listarUser.setMnemonic(KeyEvent.VK_I);
			listarUser.addActionListener(this);

			gerirUserAdmin.add(listarUserAprov);
			gerirUserAdmin.add(listarUserReprovado);
			gerirUserAdmin.add(aprovarReprovarUser);
			gerirUserAdmin.add(listarOsMeusDados);
			gerirUserAdmin.add(menuAlterarDados);
			gerirUserAdmin.add(criaNovoUser);
			gerirUserAdmin.add(listarUser);

			bar.add(gerirUserAdmin);

			JMenuItem gerirJogador = new JMenu("Gerir jogador(es)");
			gerirJogador.setMnemonic(KeyEvent.VK_G);

			listarJog = new JMenuItem("Listar todos os jogadores");
			listarJog.setMnemonic(KeyEvent.VK_I);
			listarJog.addActionListener(this);

			pesquisaJogador = new JMenuItem("Pesquisar jogador(es) por nome");
			pesquisaJogador.setMnemonic(KeyEvent.VK_R);
			pesquisaJogador.addActionListener(this);

			gerirJogador.add(listarJog);
			gerirJogador.add(pesquisaJogador);

			bar.add(gerirJogador);

			setJMenuBar(bar);

			getContentPane().removeAll();

			listarUti = new JButton("  Listar todos os utilizadores  ");
			listarUti.setActionCommand("verAlterar");
			listarUti.addActionListener(this);

			criarButt = new JButton("  Criar novo utilizador ");
			criarButt.setActionCommand("continuar1menu");
			criarButt.addActionListener(this);

			aprovarButt = new JButton("  Aprovar um utilizador ");
			aprovarButt.setActionCommand("continuar1menu");
			aprovarButt.addActionListener(this);

			JPanel painelLogin = new JPanel(new GridLayout(3, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(listarUti);
			painelLogin.add(painelBotao1);
			JPanel painelBotao2 = new JPanel(new FlowLayout());
			painelBotao2.add(criarButt);
			painelLogin.add(painelBotao2);
			JPanel painelBotao3 = new JPanel(new FlowLayout());
			painelBotao3.add(aprovarButt);
			painelLogin.add(painelBotao3);

			JPanel painelPrincipal = new JPanel(new FlowLayout());
			painelPrincipal.add(painelLogin);

			TitledBorder border = new TitledBorder("Funções de atalho.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			painelPrincipal.setBorder(border);

			getContentPane().add(painelPrincipal);
			setVisible(true);

			getContentPane().setBackground(Color.GRAY);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(new Rectangle(200, 200, 500, 400));
			setTitle(".:: MasterMind ::. (Utilizador do tipo Administrador)");

			if (aMostraNotificacao == 1) {
				int idUser = aUtilizador.getIdUser(st, nomeLogin);
				listaNotificacao("n, utilizador_notificacao nu where n.N_ID = nu.N_ID and nu.u_id = " + idUser);
				aNotificacao.retiraNotificacao(idUser, st);
				aNotificacao.eliminaNotificacao(st);
			}

		} else {
			bar = new JMenuBar();

			fileMenu = new JMenu("Ficheiro");
			fileMenu.setMnemonic(KeyEvent.VK_F);

			aboutItem = new JMenuItem("Acerca...");
			aboutItem.setMnemonic(KeyEvent.VK_A);
			aboutItem.addActionListener(this);

			exitItem = new JMenuItem("Sair do programa");
			exitItem.setMnemonic(KeyEvent.VK_S);
			exitItem.addActionListener(this);

			logout = new JMenuItem("Fazer 'logout'");
			logout.setMnemonic(KeyEvent.VK_F);
			logout.addActionListener(this);

			fileMenu.add(aboutItem);
			fileMenu.add(exitItem);
			fileMenu.add(logout);

			bar.add(fileMenu);

			JMenuItem gerirUserAdmin = new JMenu("Gestão da minha conta");
			gerirUserAdmin.setMnemonic(KeyEvent.VK_G);

			listarOsMeusDados = new JMenuItem("Listar os meus dados de utilizador");
			listarOsMeusDados.setMnemonic(KeyEvent.VK_L);
			listarOsMeusDados.addActionListener(this);

			alterarOsMeusDados = new JMenuItem("Alterar os meus dados");
			alterarOsMeusDados.setMnemonic(KeyEvent.VK_L);
			alterarOsMeusDados.addActionListener(this);

			inactivarConta = new JMenuItem("Inactivar a minha conta");
			inactivarConta.setMnemonic(KeyEvent.VK_I);
			inactivarConta.addActionListener(this);

			gerirUserAdmin.add(listarOsMeusDados);
			gerirUserAdmin.add(alterarOsMeusDados);
			gerirUserAdmin.add(inactivarConta);

			bar.add(gerirUserAdmin);

			JMenuItem gerirJogador = new JMenu("Gerir jogador(es)");
			gerirJogador.setMnemonic(KeyEvent.VK_G);

			listarJog = new JMenuItem("Listar todos os jogadores");
			listarJog.setMnemonic(KeyEvent.VK_I);
			listarJog.addActionListener(this);

			gerirJogador.add(listarJog);

			bar.add(gerirJogador);

			JMenuItem menuJogo = new JMenu("Menu de jogo");
			menuJogo.setMnemonic(KeyEvent.VK_G);

			jogarVsPc = new JMenuItem("Jogar contra o computador");
			jogarVsPc.setMnemonic(KeyEvent.VK_J);
			jogarVsPc.addActionListener(this);

			simularJogo = new JMenuItem("Simular um determinado jogo");
			simularJogo.setMnemonic(KeyEvent.VK_S);
			simularJogo.addActionListener(this);

			listarJogo = new JMenuItem("Listar todos os meus jogos");
			listarJogo.setMnemonic(KeyEvent.VK_R);
			listarJogo.addActionListener(this);

			menuJogo.add(jogarVsPc);
			menuJogo.add(simularJogo);
			menuJogo.add(listarJogo);

			bar.add(menuJogo);

			setJMenuBar(bar);

			getContentPane().removeAll();

			jogarPcButt = new JButton("  Jogar um jogo contra o computador  ");
			jogarPcButt.setActionCommand("verAlterar");
			jogarPcButt.addActionListener(this);

			simularButt = new JButton("  Simular um jogo ");
			simularButt.setActionCommand("continuar1menu");
			simularButt.addActionListener(this);

			listarJogosButt = new JButton("  Listar todos os meus jogos ");
			listarJogosButt.setActionCommand("continuar1menu");
			listarJogosButt.addActionListener(this);

			JPanel painelLogin = new JPanel(new GridLayout(3, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(jogarPcButt);
			painelLogin.add(painelBotao1);
			JPanel painelBotao2 = new JPanel(new FlowLayout());
			painelBotao2.add(simularButt);
			painelLogin.add(painelBotao2);
			JPanel painelBotao3 = new JPanel(new FlowLayout());
			painelBotao3.add(listarJogosButt);
			painelLogin.add(painelBotao3);

			JPanel painelPrincipal = new JPanel(new FlowLayout());
			painelPrincipal.add(painelLogin);

			TitledBorder border = new TitledBorder("Funções de atalho.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			border.setTitleColor(Color.RED);
			painelPrincipal.setBorder(border);

			getContentPane().add(painelPrincipal);
			setVisible(true);

			getContentPane().setBackground(Color.GRAY);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(new Rectangle(200, 200, 500, 400));

			setTitle(".:: MasterMind ::. (Utilizador do tipo Jogador)");
		}
		setVisible(true);
	}

	/**
	 * Calcula tempo decorrido entre o início do programa e o fim.
	 * 
	 * @param tempoInicial
	 */
	private static void calculaTempoDecorrido(long tempoInicial, String aNomeUtilizador) {
		Date dataInicio = new Date(tempoInicial);
		long tempoFinal = (new Date()).getTime();
		Date dataFim = new Date(tempoFinal);
		long tempoTotal = tempoFinal - tempoInicial;
		long tempoTotalSeg = tempoTotal / 1000;
		long tempoTotalMin = tempoTotalSeg / 60;
		long tempoTotalHora = tempoTotalMin / 60;

		String msgDespedida = "Adeus, " + aNomeUtilizador + "!";
		JOptionPane.showMessageDialog(new JFrame(),
				msgDespedida + "\n\nInício do processo: " + dataInicio + ".\nFim do processo: " + dataFim
						+ ".\nTempo de execução: " + tempoTotal + " Milissegundos (" + tempoTotalSeg + " Segundo(s); "
						+ tempoTotalMin + " Minuto(s); " + tempoTotalHora + " Hora(s) ).",
				"Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Gestão de eventos
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(criaNovoUser) || e.getSource().equals(criarButt)) {
			setVisible(false);
			new ClassePrincipal(2, true, nomeLogin, nomeUtilizador);
		}

		if (e.getSource().equals(aboutItem))
			JOptionPane.showMessageDialog(JanelaMenu.this, "Respect, walk!", "Acerca...", JOptionPane.PLAIN_MESSAGE);

		if (e.getSource().equals(exitItem)) {
			if (st != null) {
				registaLog("Terminou o programa.");
				aBaseDados.fechaBaseDados();
			}

			if (tempoInicial != -1) {
				calculaTempoDecorrido(tempoInicial, nomeUtilizador);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Adeus, Convidado!", "Informação",
						JOptionPane.INFORMATION_MESSAGE);
			}
			System.exit(0);
		}

		if (e.getSource().equals(listarOsMeusDados)) {
			registaLog("Listou os seus dados.");
			listaMeusDados();
		}

		if (e.getSource().equals(alterarOsMeusDados) || e.getSource().equals(alterarOsMeusDados1)) {
			id = aUtilizador.getIdUser(st, nomeLogin);
			registaLog("Alterou os seus dados.");
			getContentPane().removeAll();
			janelaAlteraDados = new JanelaAlteraDados(tipo, nomeLogin, 0, st, tempoInicial);
			janelaAlteraDados.setBounds(new Rectangle(200, 200, 570, 270));
			janelaAlteraDados.setTitle("Alterar dados");
			janelaAlteraDados.setResizable(false);
			janelaAlteraDados.setVisible(true);
			setVisible(false);
		}

		if (e.getSource().equals(alterarDadosOutros)) {
			getContentPane().removeAll();
			JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
			painelLabelNome.add(new JLabel("Introduza o login do utilizador cujo os dados deseja alterar:"));

			JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			caixaNome = new JTextField(10);
			painelCaixaNome.add(caixaNome);
			caixaNome.setToolTipText("Insira aqui o login do utilizador que deseja alterar!");

			JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
			botaoCanc = new JButton("  Cancelar  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			botaoAlt = new JButton("  Alterar  ");
			botaoAlt.setActionCommand("ok");
			botaoAlt.addActionListener(this);
			painelBotao.add(botaoAlt);
			painelBotao.add(botaoCanc);
			GridLayout gl = new GridLayout(1, 1);
			gl.setHgap(1);
			gl.setVgap(1);
			JPanel painelLogin = new JPanel(gl);
			TitledBorder border = new TitledBorder("Alterar os dados de um determinado utilizador.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			painelLogin.setBorder(border);
			painelLogin.add(painelLabelNome);
			painelLogin.add(painelCaixaNome);
			painelLogin.add(painelBotao);

			getContentPane().add(painelLogin);
			setVisible(true);
		}

		if (e.getSource().equals(jogarVsPc) || e.getSource().equals(jogarPcButt)) {
			getContentPane().removeAll();
			JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
			painelLabelNome.add(new JLabel("Introduza o número de linhas que deseja que o tabuleiro tenha (10-20):"));

			JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			caixaNome = new JTextField(2);
			painelCaixaNome.add(caixaNome);
			caixaNome.setToolTipText("Insira aqui o número de linhas do tabuleiro!");

			JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
			botaoCanc = new JButton("  Cancelar  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			botaoJogarPC = new JButton("  Jogar  ");
			botaoJogarPC.setActionCommand("ok");
			botaoJogarPC.addActionListener(this);
			painelBotao.add(botaoJogarPC);
			painelBotao.add(botaoCanc);
			GridLayout gl = new GridLayout(1, 1);
			gl.setHgap(1);
			gl.setVgap(1);
			JPanel painelLogin = new JPanel(gl);
			TitledBorder border = new TitledBorder("Introduzir o número de linhas do tabuleiro.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			painelLogin.setBorder(border);
			painelLogin.add(painelLabelNome);
			painelLogin.add(painelCaixaNome);
			painelLogin.add(painelBotao);

			getContentPane().add(painelLogin);
			setVisible(true);
		}

		if (e.getSource().equals(simularJogo) || e.getSource().equals(simularButt)) {
			getContentPane().removeAll();
			String[] data = aJogo.listaUserInactivoArray(st, aUtilizador.getIdUser(st, nomeLogin));

			if (data != null) {
				list = new JList(data);
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list.setLayoutOrientation(JList.VERTICAL);
				list.setVisibleRowCount(-1);
				JScrollPane pane = new JScrollPane(list);
				pane.setPreferredSize(new Dimension(250, 80));
				botaoCanc = new JButton("  Cancelar  ");
				botaoCanc.setActionCommand("voltar");
				botaoCanc.addActionListener(this);

				simular = new JButton("  Simular ");
				simular.setActionCommand("simular");
				simular.addActionListener(this);

				JPanel painelLogin = new JPanel(new GridLayout(1, 2));
				JPanel painelBotao1 = new JPanel(new FlowLayout());
				painelBotao1.add(botaoCanc);
				painelLogin.add(painelBotao1);
				JPanel painelBotao2 = new JPanel(new FlowLayout());
				painelBotao2.add(simular);
				painelLogin.add(painelBotao2);

				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panel.add(pane, BorderLayout.CENTER);
				panel.add(painelLogin, BorderLayout.SOUTH);
				TitledBorder border = new TitledBorder("Simular o jogo através do seu id.");
				border.setTitleJustification(TitledBorder.CENTER);
				border.setTitlePosition(TitledBorder.TOP);
				panel.setBorder(border);
				getContentPane().add(panel);
				setVisible(true);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Não existem jogos para simular!", "Informação", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource().equals(simular)) {
			String aIdJogo = (String) list.getSelectedValue();
			registaLog("Simulou o jogo com id:" + aIdJogo + ".");
			JanelaTabelaJogo tabelajogo = new JanelaTabelaJogo(st, tempoInicial, nomeLogin, nomeUtilizador, aIdJogo);
			tabelajogo.setBounds(new Rectangle(000, 000, 670, 670));
			tabelajogo.setTitle("Simulação de um jogo");
			tabelajogo.setResizable(false);
			tabelajogo.setVisible(true);
			setVisible(false);
		}

		if (e.getSource().equals(botaoJogarPC)) {
			boolean sair = false;
			do {
				String leitura = caixaNome.getText();
				try {
					int numLinhas = Integer.parseInt(leitura);
					if (numLinhas > 9 && numLinhas < 21) {
						getContentPane().removeAll();
						registaLog("Jogou contra o computador.");
						JanelaTabelaJogo tabelajogo = new JanelaTabelaJogo(numLinhas, st, tempoInicial, nomeLogin,
								nomeUtilizador);
						tabelajogo.setBounds(new Rectangle(000, 000, 670, 670));
						tabelajogo.setTitle("Jogo contra o computador");
						tabelajogo.setResizable(false);
						tabelajogo.setVisible(true);
						setVisible(false);
						sair = true;
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Só pode digitar um número entre 10 e 20!", "Erro",
								JOptionPane.ERROR_MESSAGE);
						sair = true;
					}
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(new JFrame(), "Só pode digitar inteiros!", "Erro",
							JOptionPane.ERROR_MESSAGE);
					sair = true;
				}

			} while (!sair);
		}

		if (e.getSource().equals(botaoAlt)) {
			String login = caixaNome.getText();

			if (login.equalsIgnoreCase(nomeLogin)) {
				id = aUtilizador.getIdUser(st, nomeLogin);
				getContentPane().removeAll();
				registaLog("Alterou os seus dados.");
				janelaAlteraDados = new JanelaAlteraDados(tipo, nomeLogin, 0, st, tempoInicial);
				janelaAlteraDados.setBounds(new Rectangle(200, 200, 570, 270));
				janelaAlteraDados.setTitle("Alterar dados");
				janelaAlteraDados.setResizable(false);
				janelaAlteraDados.setVisible(true);
				setVisible(false);
			} else {
				if (!aUtilizador.verificaLogin(login, st)) {
					int tipoAlterar = aUtilizador.verificaTipo(login, st);
					if (tipoAlterar == 1) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Não pode alterar dados de um utilizador do tipo Administrador!", "Erro",
								JOptionPane.ERROR_MESSAGE);
					} else if (tipoAlterar == 2) {
						getContentPane().removeAll();
						registaLog("Alterou os dados do utilizador com login: " + login + ".");
						janelaAlteraDados = new JanelaAlteraDados(tipoAlterar, nomeLogin, login, 1, st, tempoInicial);
						janelaAlteraDados.setBounds(new Rectangle(200, 200, 570, 270));
						janelaAlteraDados.setTitle("Alterar dados");
						janelaAlteraDados.setResizable(false);
						janelaAlteraDados.setVisible(true);
						setVisible(false);
					}
				} else {
					setVisible(false);
					JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
							"Esse login não existe na base de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (e.getSource().equals(verDadosDB)) {
			String mensagem = "";

			Properties prop = new Properties();

			try {
				prop.load(new FileInputStream("config.properties"));
				mensagem += "Nome da base de dados: " + prop.getProperty("dbname") + "\n";
				mensagem += "User da base de dados: " + prop.getProperty("dbuser") + "\n";
				mensagem += "Url da base de dados: " + prop.getProperty("dburl") + "\n";
				mensagem += "Password da base de dados: " + prop.getProperty("dbpassword") + "\n";
				mensagem += "Porto da base de dados: " + prop.getProperty("dbporto") + "\n";

				JOptionPane.showMessageDialog(new JFrame(), mensagem, "Dados da Base de Dados",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (FileNotFoundException fnde) {
				JOptionPane.showMessageDialog(new JFrame(),
						"O ficheiro não foi encontrado, o mesmo foi agora criado com dados genéricos. Clique outra vez!",
						"Erro", JOptionPane.ERROR_MESSAGE);
				colocaDados();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		if (e.getSource().equals(alterarDadosDB)) {
			setVisible(false);
			JanelaAlteraDadosDB altera = new JanelaAlteraDadosDB(st, tempoInicial);
			altera.setBounds(new Rectangle(200, 200, 570, 270));
			altera.setTitle("Alterar dados da Base de dados");
			altera.setResizable(false);
			altera.setVisible(true);
		}

		if (e.getSource().equals(login)) {
			setVisible(false);
			new ClassePrincipal(1, false, "", "");
		}

		if (e.getSource().equals(botaoInactiva)) {
			if (aUtilizador.inactivaconta(nomeLogin, st)) {
				aNotificacao.registaNotificacaoAdmin(
						"O utilizador com o login: " + nomeLogin + " inactivou a sua conta.", st);
				registaLog("Inactivou a sua conta.");
				JOptionPane.showMessageDialog(new JFrame(),
						"A sua conta foi inactivada com sucesso! Adeus, " + nomeUtilizador + ".", "Informação",
						JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				new JanelaMenu(tempoInicial);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Ocorreu um erro a inactivar a sua conta, por favor tente novamente!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				registaLog("Tentou inactivar a sua conta, mas ocorreu um erro.");
			}
		}

		if (e.getSource().equals(logout)) {
			JOptionPane.showMessageDialog(new JFrame(), "Adeus, " + nomeUtilizador + ".", "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			registaLog("Terminou a sessão no programa.");
			setVisible(false);
			if (st != null) {
				aBaseDados.fechaBaseDados();
			}
			new JanelaMenu(tempoInicial);
		}

		if (e.getSource().equals(registar)) {
			setVisible(false);
			new ClassePrincipal(2, false, "", "");
		}

		if (e.getSource().equals(listarJogo) || e.getSource().equals(listarJogosButt)) {
			registaLog("Listou todos os seus jogos.");
			getContentPane().removeAll();
			JTable table = new JTable();
			DefaultTableModel model = new DefaultTableModel();
			Object[] columnsName = new Object[2];
			columnsName[0] = "ID do Jogo";
			columnsName[1] = "Data";
			model.setColumnIdentifiers(columnsName);
			model = aJogo.listaJogo("where u_id = " + aUtilizador.getIdUser(st, nomeLogin), st, model);
			if (model != null) {
				table.setModel(model);
				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				botaoCanc = new JButton("  Voltar ao menu principal  ");
				botaoCanc.setActionCommand("voltar");
				botaoCanc.addActionListener(this);
				JPanel painelLogin = new JPanel(new GridLayout(1, 1));
				JPanel painelBotao1 = new JPanel(new FlowLayout());
				painelBotao1.add(botaoCanc);
				painelLogin.add(painelBotao1);
				JScrollPane pane = new JScrollPane(table);
				panel.add(pane, BorderLayout.CENTER);
				panel.add(painelLogin, BorderLayout.SOUTH);
				TitledBorder border = new TitledBorder("Listagem de todos os seus jogos.");
				border.setTitleJustification(TitledBorder.CENTER);
				border.setTitlePosition(TitledBorder.TOP);
				panel.setBorder(border);
				getContentPane().add(panel);
				setVisible(true);
				table.setEnabled(false);
				table.setAutoCreateRowSorter(true);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Ainda não tem nenhum jogo efectuado!", "Informação", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource().equals(listarTodosLogs)) {
			listaLogs("Listagem dos logs.", "order by l_data_log, l_hora;");
		}

		if (e.getSource().equals(listarUserAprov)) {
			registaLog("Listou utilizadores que aguardam aprovação.");
			getContentPane().removeAll();
			JTable table = new JTable();
			DefaultTableModel model = new DefaultTableModel();
			Object[] columnsName = new Object[6];
			columnsName[0] = "ID";
			columnsName[1] = "Nome";
			columnsName[2] = "Login";
			columnsName[3] = "Password";
			columnsName[4] = "Email";
			columnsName[5] = "Tipo";
			model.setColumnIdentifiers(columnsName);
			model = aUtilizador.listaUserInactivo(st, model);
			if (model != null) {
				table.setModel(model);
				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				botaoCanc = new JButton("  Voltar ao menu principal  ");
				botaoCanc.setActionCommand("voltar");
				botaoCanc.addActionListener(this);
				JPanel painelLogin = new JPanel(new GridLayout(1, 1));
				JPanel painelBotao1 = new JPanel(new FlowLayout());
				painelBotao1.add(botaoCanc);
				painelLogin.add(painelBotao1);
				JScrollPane pane = new JScrollPane(table);
				panel.add(pane, BorderLayout.CENTER);
				panel.add(painelLogin, BorderLayout.SOUTH);
				TitledBorder border = new TitledBorder("Listagem de todos os utilizadores que aguardam aprovação.");
				border.setTitleJustification(TitledBorder.CENTER);
				border.setTitlePosition(TitledBorder.TOP);
				panel.setBorder(border);
				getContentPane().add(panel);
				setVisible(true);
				table.setEnabled(false);
				table.setAutoCreateRowSorter(true);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Não existem utilizadores a aguardar aprovação!", "Informação",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource().equals(listarUserReprovado)) {
			registaLog("Listou todos os utilizadores reprovados.");
			getContentPane().removeAll();
			JTable table = new JTable();
			DefaultTableModel model = new DefaultTableModel();
			Object[] columnsName = new Object[6];
			columnsName[0] = "ID";
			columnsName[1] = "Nome";
			columnsName[2] = "Login";
			columnsName[3] = "Password";
			columnsName[4] = "Email";
			columnsName[5] = "Tipo";
			model.setColumnIdentifiers(columnsName);
			model = aUtilizador.listaUtilizadorReprovado(st, model);
			if (model != null) {
				table.setModel(model);
				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				botaoCanc = new JButton("  Voltar ao menu principal  ");
				botaoCanc.setActionCommand("voltar");
				botaoCanc.addActionListener(this);
				JPanel painelLogin = new JPanel(new GridLayout(1, 1));
				JPanel painelBotao1 = new JPanel(new FlowLayout());
				painelBotao1.add(botaoCanc);
				painelLogin.add(painelBotao1);
				JScrollPane pane = new JScrollPane(table);
				panel.add(pane, BorderLayout.CENTER);
				panel.add(painelLogin, BorderLayout.SOUTH);
				TitledBorder border = new TitledBorder("Listagem de todos os utilizadores que estão reprovados.");
				border.setTitleJustification(TitledBorder.CENTER);
				border.setTitlePosition(TitledBorder.TOP);
				panel.setBorder(border);
				getContentPane().add(panel);
				setVisible(true);
				table.setEnabled(false);
				table.setAutoCreateRowSorter(true);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Não existem utilizadores reprovados!", "Informação", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (e.getSource().equals(aprovaUser) || e.getSource().equals(aprovarButt)) {
			registaLog("Listou os users que aguardam aprovação.");
			listaUserAprovado();
		}

		if (e.getSource().equals(aprovaUserBotao)) {
			String login = (String) list.getSelectedValue();
			if (login != null) {
				boolean estado = aUtilizador.aprovaUtilizador(login, st);

				if (estado) {
					registaLog("Aprovou o utilizador com login: '" + login + "'.");
					JOptionPane.showMessageDialog(new JFrame(),
							"O utilizador com o login '" + login + "' foi aprovado com sucesso!", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					registaLog("Tentou aprovar o utilizador com login: '" + login + "', mas ocorre um um erro.");
					JOptionPane.showMessageDialog(new JFrame(),
							"Ocorreu um erro a aprovar o utilizador com o login '" + login + "'!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Tem de selecionar um utilizador!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			listaUserAprovado();
		}

		if (e.getSource().equals(reprovaUser)) {
			registaLog("Listou os users que estão reprovados.");
			listaUserReprovado();
		}

		if (e.getSource().equals(reprovaUserBotao)) {
			String login = (String) list.getSelectedValue();
			boolean estado = aUtilizador.reprovaUtilizador(login, st);

			if (estado) {
				registaLog("Reprovou o utilizador com login: '" + login + "'.");
				JOptionPane.showMessageDialog(new JFrame(),
						"O utilizador com o login '" + login + "' foi reprovado com sucesso!", "Informação",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				registaLog("Tentou reprovar o utilizador com login: '" + login + "', mas ocorreu um erro.");
				JOptionPane.showMessageDialog(new JFrame(),
						"Ocorreu um erro a reprovar o utilizador com o login '" + login + "'!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
			listaUserReprovado();
		}

		if (e.getSource().equals(pesquisarLog)) {

			getContentPane().removeAll();
			JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
			painelLabelNome.add(new JLabel("Introduza o nome que deseja pesquisar:"));

			JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			caixaNome = new JTextField(10);
			painelCaixaNome.add(caixaNome);
			caixaNome.setToolTipText("Insira aqui o nome que deseja pesquisar!");

			JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
			botaoCanc = new JButton("  Cancelar  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			botaoPesqLog = new JButton("  Pesquisar  ");
			botaoPesqLog.setActionCommand("ok");
			botaoPesqLog.addActionListener(this);
			painelBotao.add(botaoPesqLog);
			painelBotao.add(botaoCanc);
			GridLayout gl = new GridLayout(1, 1);
			gl.setHgap(1);
			gl.setVgap(1);
			JPanel painelLogin = new JPanel(gl);
			TitledBorder border = new TitledBorder("Pesquisar todos os logs de um determinado utilizador.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			painelLogin.setBorder(border);
			painelLogin.add(painelLabelNome);
			painelLogin.add(painelCaixaNome);
			painelLogin.add(painelBotao);

			getContentPane().add(painelLogin);
			setVisible(true);

		}

		if (e.getSource().equals(pesquisaJogador)) {

			getContentPane().removeAll();
			JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
			painelLabelNome.add(new JLabel("Introduza o nome do jogador deseja pesquisar:"));

			JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			caixaNome = new JTextField(10);
			painelCaixaNome.add(caixaNome);
			caixaNome.setToolTipText("Insira aqui o nome do jogador que deseja pesquisar!");

			JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
			botaoCanc = new JButton("  Cancelar  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			botaoPesqJog = new JButton("  Pesquisar Jogador ");
			botaoPesqJog.setActionCommand("ok");
			botaoPesqJog.addActionListener(this);
			painelBotao.add(botaoPesqJog);
			painelBotao.add(botaoCanc);
			GridLayout gl = new GridLayout(1, 1);
			gl.setHgap(1);
			gl.setVgap(1);
			JPanel painelLogin = new JPanel(gl);
			TitledBorder border = new TitledBorder("Pesquisar um jogador por nome.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			painelLogin.setBorder(border);
			painelLogin.add(painelLabelNome);
			painelLogin.add(painelCaixaNome);
			painelLogin.add(painelBotao);

			getContentPane().add(painelLogin);
			setVisible(true);

		}

		if (e.getSource().equals(listarUser) || e.getSource().equals(listarUti)) {
			registaLog("Listou todos os utilizadores.");
			listaUser("Listagem de todos os utilizadores.", ";");
		}

		if (e.getSource().equals(inactivarConta)) {
			getContentPane().removeAll();
			JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
			painelLabelNome.add(new JLabel("Tem a certeza que deseja inactivar a sua conta?"));

			JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
			botaoCanc = new JButton("  Não  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			botaoInactiva = new JButton("  Sim ");
			botaoInactiva.setActionCommand("ok");
			botaoInactiva.addActionListener(this);
			painelBotao.add(botaoInactiva);
			painelBotao.add(botaoCanc);
			GridLayout gl = new GridLayout(1, 1);
			gl.setHgap(1);
			gl.setVgap(1);
			JPanel painelLogin = new JPanel(gl);
			TitledBorder border = new TitledBorder("Inactivar a minha conta.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			painelLogin.setBorder(border);
			painelLogin.add(painelLabelNome);
			painelLogin.add(painelBotao);

			getContentPane().add(painelLogin);
			setVisible(true);
		}

		if (e.getSource().equals(listarJog)) {
			registaLog("Listou todos os jogadores.");
			listaJogador("Listagem de todos os jogadores.", "where u.u_id = j.u_id;");
		}

		if (e.getSource().equals(botaoCanc)) {
			colocaMenu();
		}

		if (e.getSource().equals(botaoPesqLog)) {
			String nome = caixaNome.getText();
			if (nome.length() > 0) {
				getContentPane().removeAll();
				JTable table = new JTable();
				DefaultTableModel model = new DefaultTableModel();
				Object[] columnsName = new Object[4];
				columnsName[0] = "Data";
				columnsName[1] = "Hora";
				columnsName[2] = "Utilizador";
				columnsName[3] = "Acção";
				model.setColumnIdentifiers(columnsName);
				model = aLog.listaLog(" where l_utilizador like '%" + nome + "%' order by l_data_log, l_hora", st,
						model);
				if (model != null) {
					registaLog("Pesquisou os logs do utilizador: " + nome + ".");
					table.setModel(model);
					panel = new JPanel();
					panel.setLayout(new BorderLayout());
					JScrollPane pane = new JScrollPane(table);
					botaoCanc = new JButton("  Voltar ao menu principal  ");
					botaoCanc.setActionCommand("voltar");
					botaoCanc.addActionListener(this);
					JPanel painelLogin = new JPanel(new GridLayout(1, 1));
					JPanel painelBotao1 = new JPanel(new FlowLayout());
					painelBotao1.add(botaoCanc);
					painelLogin.add(painelBotao1);
					panel.add(pane, BorderLayout.CENTER);
					panel.add(painelLogin, BorderLayout.SOUTH);
					TitledBorder border = new TitledBorder(
							"Listagem de todos os logs que tenham no nome de utilizador '" + nome + "'.");
					border.setTitleJustification(TitledBorder.CENTER);
					border.setTitlePosition(TitledBorder.TOP);
					panel.setBorder(border);
					getContentPane().add(panel);
					setVisible(true);
					table.setEnabled(false);
					table.setAutoCreateRowSorter(true);
				} else {
					setVisible(false);
					JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
							"Não foram encontrados logs que tenham como utilizador esse nome!", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Tem de digitar alguma coisa!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getSource().equals(botaoPesqJog)) {
			String nome = caixaNome.getText();
			if (nome.length() > 0) {
				getContentPane().removeAll();
				JTable table = new JTable();
				DefaultTableModel model = new DefaultTableModel();
				Object[] columnsName = new Object[8];
				columnsName[0] = "ID";
				columnsName[1] = "Nome";
				columnsName[2] = "Estado";
				columnsName[3] = "Tipo";
				columnsName[4] = "Estado de Reprovação";
				columnsName[5] = "Tempo total acumulado de jogo";
				columnsName[6] = "Número total de jogos";
				columnsName[7] = "Número total de vitórias";
				model.setColumnIdentifiers(columnsName);
				model = aJogador.listaJogTotais("where u.u_id = j.u_id and (u.u_name like '%" + nome + "%' )", st,
						model);
				if (model != null) {
					registaLog("Pesquisou um jogador por nome (" + nome + ").");
					table.setModel(model);
					panel = new JPanel();
					panel.setLayout(new BorderLayout());
					JScrollPane pane = new JScrollPane(table);
					botaoCanc = new JButton("  Voltar ao menu principal  ");
					botaoCanc.setActionCommand("voltar");
					botaoCanc.addActionListener(this);
					JPanel painelLogin = new JPanel(new GridLayout(1, 1));
					JPanel painelBotao1 = new JPanel(new FlowLayout());
					painelBotao1.add(botaoCanc);
					painelLogin.add(painelBotao1);
					panel.add(pane, BorderLayout.CENTER);
					panel.add(painelLogin, BorderLayout.SOUTH);
					TitledBorder border = new TitledBorder(
							"Listagem de todos os jogadores que tenham no nome de utilizador '" + nome + "'.");
					border.setTitleJustification(TitledBorder.CENTER);
					border.setTitlePosition(TitledBorder.TOP);
					panel.setBorder(border);
					getContentPane().add(panel);
					setVisible(true);
					table.setEnabled(false);
					table.setAutoCreateRowSorter(true);
				} else {
					setVisible(false);
					JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
							"Não foram encontrados jogadores que tenham como utilizador esse nome!", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Tem de digitar alguma coisa!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}

		repaint();
	}

	/**
	 * lista todos os users que podem ser aprovados
	 */
	private void listaUserAprovado() {
		getContentPane().removeAll();
		String[] data = aUtilizador.listaUserInactivoArray(st);

		if (data != null) {
			list = new JList(data);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setLayoutOrientation(JList.VERTICAL);
			list.setVisibleRowCount(-1);
			JScrollPane pane = new JScrollPane(list);
			pane.setPreferredSize(new Dimension(250, 80));
			botaoCanc = new JButton("  Cancelar  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);

			aprovaUserBotao = new JButton("  Aprovar ");
			aprovaUserBotao.setActionCommand("aprovaUserBotao");
			aprovaUserBotao.addActionListener(this);

			JPanel painelLogin = new JPanel(new GridLayout(1, 2));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(botaoCanc);
			painelLogin.add(painelBotao1);
			JPanel painelBotao2 = new JPanel(new FlowLayout());
			painelBotao2.add(aprovaUserBotao);
			painelLogin.add(painelBotao2);

			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			panel.add(painelLogin, BorderLayout.SOUTH);
			TitledBorder border = new TitledBorder("Aprovar um utilizador através do seu login.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			panel.setBorder(border);
			getContentPane().add(panel);
			setVisible(true);
		} else {
			setVisible(false);
			JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
					"Não existem utilizadores para aprovar!", "Informação", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Lista todos os users que podem ser reprovados
	 */
	private void listaUserReprovado() {
		getContentPane().removeAll();
		String[] data = aUtilizador.listaUtilizadorReprovado1(st);

		if (data != null) {
			list = new JList(data);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setLayoutOrientation(JList.VERTICAL);
			list.setVisibleRowCount(-1);
			JScrollPane pane = new JScrollPane(list);
			pane.setPreferredSize(new Dimension(250, 80));
			botaoCanc = new JButton("  Cancelar  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);

			reprovaUserBotao = new JButton("  Reprovar ");
			reprovaUserBotao.setActionCommand("reprovaUserBotao");
			reprovaUserBotao.addActionListener(this);

			JPanel painelLogin = new JPanel(new GridLayout(1, 2));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(botaoCanc);
			painelLogin.add(painelBotao1);
			JPanel painelBotao2 = new JPanel(new FlowLayout());
			painelBotao2.add(reprovaUserBotao);
			painelLogin.add(painelBotao2);

			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(pane, BorderLayout.CENTER);
			panel.add(painelLogin, BorderLayout.SOUTH);
			TitledBorder border = new TitledBorder("Reprovar um utilizador através do seu login.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			panel.setBorder(border);
			getContentPane().add(panel);
			setVisible(true);
		} else {
			setVisible(false);
			JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
					"Não existem utilizadores para reprovar!", "Informação", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Coloca as funções de atalho segundo o tipo de utilizador
	 */
	private void colocaMenu() {
		getContentPane().removeAll();
		if (tipo == 1) {
			listarUti = new JButton("  Listar todos os utilizadores  ");
			listarUti.setActionCommand("verAlterar");
			listarUti.addActionListener(this);

			criarButt = new JButton("  Criar novo utilizador ");
			criarButt.setActionCommand("continuar1menu");
			criarButt.addActionListener(this);

			aprovarButt = new JButton("  Aprovar um utilizador ");
			aprovarButt.setActionCommand("continuar1menu");
			aprovarButt.addActionListener(this);

			JPanel painelLogin = new JPanel(new GridLayout(3, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(listarUti);
			painelLogin.add(painelBotao1);
			JPanel painelBotao2 = new JPanel(new FlowLayout());
			painelBotao2.add(criarButt);
			painelLogin.add(painelBotao2);
			JPanel painelBotao3 = new JPanel(new FlowLayout());
			painelBotao3.add(aprovarButt);
			painelLogin.add(painelBotao3);

			JPanel painelPrincipal = new JPanel(new FlowLayout());
			painelPrincipal.add(painelLogin);

			TitledBorder border = new TitledBorder("Funções de atalho.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			border.setTitleColor(Color.BLUE);
			painelPrincipal.setBorder(border);

			getContentPane().add(painelPrincipal);
			setVisible(true);
		} else {
			jogarPcButt = new JButton("  Jogar um jogo contra o computador  ");
			jogarPcButt.setActionCommand("verAlterar");
			jogarPcButt.addActionListener(this);

			simularButt = new JButton("  Simular um jogo ");
			simularButt.setActionCommand("continuar1menu");
			simularButt.addActionListener(this);

			listarJogosButt = new JButton("  Listar todos os meus jogos ");
			listarJogosButt.setActionCommand("continuar1menu");
			listarJogosButt.addActionListener(this);

			JPanel painelLogin = new JPanel(new GridLayout(3, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(jogarPcButt);
			painelLogin.add(painelBotao1);
			JPanel painelBotao2 = new JPanel(new FlowLayout());
			painelBotao2.add(simularButt);
			painelLogin.add(painelBotao2);
			JPanel painelBotao3 = new JPanel(new FlowLayout());
			painelBotao3.add(listarJogosButt);
			painelLogin.add(painelBotao3);

			JPanel painelPrincipal = new JPanel(new FlowLayout());
			painelPrincipal.add(painelLogin);

			TitledBorder border = new TitledBorder("Funções de atalho.");
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			border.setTitleColor(Color.RED);
			painelPrincipal.setBorder(border);

			getContentPane().add(painelPrincipal);
			setVisible(true);
		}
	}

	/**
	 * Lista todos os logs
	 * 
	 * @param aMsg
	 * @param aComando
	 */
	private void listaLogs(String aMsg, String aComando) {
		getContentPane().removeAll();
		registaLog("Listou todos os logs.");
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		Object[] columnsName = new Object[4];
		columnsName[0] = "Data";
		columnsName[1] = "Hora";
		columnsName[2] = "Utilizador";
		columnsName[3] = "Acção";
		model.setColumnIdentifiers(columnsName);
		model = aLog.listaLog(aComando, st, model);
		if (model != null) {
			table.setModel(model);
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(table);
			botaoCanc = new JButton("  Voltar ao menu principal  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			JPanel painelLogin = new JPanel(new GridLayout(1, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(botaoCanc);
			painelLogin.add(painelBotao1);
			panel.add(pane, BorderLayout.CENTER);
			panel.add(painelLogin, BorderLayout.SOUTH);
			TitledBorder border = new TitledBorder(aMsg);
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			panel.setBorder(border);
			getContentPane().add(panel);
			setVisible(true);
			table.setEnabled(false);
			table.setAutoCreateRowSorter(true);
		} else {
			setVisible(false);
			JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
					"Não existem logs para mostrar!", "Informação", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Lista todos os users.
	 * 
	 * @param aMsg
	 * @param aComando
	 */
	private void listaUser(String aMsg, String aComando) {
		getContentPane().removeAll();
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		Object[] columnsName = new Object[8];
		columnsName[0] = "ID";
		columnsName[1] = "Nome";
		columnsName[2] = "Login";
		columnsName[3] = "Password";
		columnsName[4] = "Estado";
		columnsName[5] = "Email";
		columnsName[6] = "Tipo";
		columnsName[7] = "Estado de Reprovação";
		model.setColumnIdentifiers(columnsName);
		model = aUtilizador.listaUserTotais(aComando, st, model);
		if (model != null) {
			table.setModel(model);
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(table);
			botaoCanc = new JButton("  Voltar ao menu principal  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			JPanel painelLogin = new JPanel(new GridLayout(1, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(botaoCanc);
			painelLogin.add(painelBotao1);
			panel.add(pane, BorderLayout.CENTER);
			panel.add(painelLogin, BorderLayout.SOUTH);
			TitledBorder border = new TitledBorder(aMsg);
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			panel.setBorder(border);
			getContentPane().add(panel);
			setVisible(true);
			table.setEnabled(false);
			table.setAutoCreateRowSorter(true);
		} else {
			setVisible(false);
			JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
					"Não existem utilizadores para mostrar!", "Informação", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Se não for encontrado o ficheiro com os dados de acesso à base de dados, são
	 * colocados uns dados genéricos.
	 */
	private static void colocaDados() {
		Properties prop = new Properties();

		prop.setProperty("dbname", "db");
		prop.setProperty("dburl", "localhost");
		prop.setProperty("dbuser", "root");
		prop.setProperty("dbpassword", "insular22");
		prop.setProperty("dbporto", "3306");

		try {
			prop.store(new FileOutputStream("config.properties"), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Lista todos os jogadores
	 * 
	 * @param aMsg
	 * @param aComando
	 */
	private void listaJogador(String aMsg, String aComando) {
		getContentPane().removeAll();
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		Object[] columnsName = new Object[8];
		columnsName[0] = "ID";
		columnsName[1] = "Nome";
		columnsName[2] = "Estado";
		columnsName[3] = "Tipo";
		columnsName[4] = "Estado de Reprovação";
		columnsName[5] = "Tempo total acumulado de jogo";
		columnsName[6] = "Número total de jogos";
		columnsName[7] = "Número total de vitórias";
		model.setColumnIdentifiers(columnsName);
		model = aJogador.listaJogTotais(aComando, st, model);
		if (model != null) {
			table.setModel(model);
			panel = new JPanel();
			panel.setLayout(new BorderLayout());

			botaoCanc = new JButton("  Voltar ao menu principal  ");
			botaoCanc.setActionCommand("voltar");
			botaoCanc.addActionListener(this);
			JPanel painelLogin = new JPanel(new GridLayout(1, 1));
			JPanel painelBotao1 = new JPanel(new FlowLayout());
			painelBotao1.add(botaoCanc);
			painelLogin.add(painelBotao1);
			JScrollPane pane = new JScrollPane(table);
			panel.add(pane, BorderLayout.CENTER);
			panel.add(painelLogin, BorderLayout.SOUTH);
			TitledBorder border = new TitledBorder(aMsg);
			border.setTitleJustification(TitledBorder.CENTER);
			border.setTitlePosition(TitledBorder.TOP);
			panel.setBorder(border);
			getContentPane().add(panel);
			setVisible(true);
			table.setEnabled(false);
			table.setAutoCreateRowSorter(true);
		} else {
			setVisible(false);
			JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
					"Não existem jogadores para mostrar!", "Informação", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Lista os dados do utilizador
	 */
	private void listaMeusDados() {

		if (tipo == 1) {
			getContentPane().removeAll();
			JTable table = new JTable();
			DefaultTableModel model = new DefaultTableModel();
			Object[] columnsName = new Object[6];
			columnsName[0] = "ID";
			columnsName[1] = "Nome";
			columnsName[2] = "Login";
			columnsName[3] = "Password";
			columnsName[4] = "Email";
			columnsName[5] = "Tipo";
			model.setColumnIdentifiers(columnsName);
			model = aAdministrador.listaDadosAdmin(nomeLogin, st, model);
			if (model != null) {
				table.setModel(model);
				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				botaoCanc = new JButton("  Voltar ao menu principal  ");
				botaoCanc.setActionCommand("voltar");
				botaoCanc.addActionListener(this);

				alterarOsMeusDados1 = new JButton("  Alterar os meus dados ");
				alterarOsMeusDados1.setActionCommand("alterarOsMeusDados1");
				alterarOsMeusDados1.addActionListener(this);

				JPanel painelLogin = new JPanel(new GridLayout(1, 2));
				JPanel painelBotao1 = new JPanel(new FlowLayout());
				painelBotao1.add(botaoCanc);
				painelLogin.add(painelBotao1);
				JPanel painelBotao2 = new JPanel(new FlowLayout());
				painelBotao2.add(alterarOsMeusDados1);
				painelLogin.add(painelBotao2);
				JScrollPane pane = new JScrollPane(table);
				panel.add(pane, BorderLayout.CENTER);
				panel.add(painelLogin, BorderLayout.SOUTH);
				TitledBorder border = new TitledBorder("Listagem dos seus dados de utilizador.");
				border.setTitleJustification(TitledBorder.CENTER);
				border.setTitlePosition(TitledBorder.TOP);
				panel.setBorder(border);
				getContentPane().add(panel);
				setVisible(true);
				table.setEnabled(false);
				table.setAutoCreateRowSorter(true);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Ups! Não foi possível mostrar os seus dados.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			getContentPane().removeAll();
			JTable table = new JTable();
			DefaultTableModel model = new DefaultTableModel();
			Object[] columnsName = new Object[9];
			columnsName[0] = "ID";
			columnsName[1] = "Nome";
			columnsName[2] = "Login";
			columnsName[3] = "Password";
			columnsName[4] = "Email";
			columnsName[5] = "Tipo";
			columnsName[6] = "Tempo total acumulado de jogo";
			columnsName[7] = "Número total de jogos";
			columnsName[8] = "Número total de vitórias";
			model.setColumnIdentifiers(columnsName);
			model = aJogador.listaDadosJogador(nomeLogin, st, model);
			if (model != null) {
				table.setModel(model);
				panel = new JPanel();
				panel.setLayout(new BorderLayout());
				botaoCanc = new JButton("  Voltar ao menu principal  ");
				botaoCanc.setActionCommand("voltar");
				botaoCanc.addActionListener(this);

				alterarOsMeusDados1 = new JButton("  Alterar os meus dados ");
				alterarOsMeusDados1.setActionCommand("alterarOsMeusDados1");
				alterarOsMeusDados1.addActionListener(this);

				JPanel painelLogin = new JPanel(new GridLayout(1, 2));
				JPanel painelBotao1 = new JPanel(new FlowLayout());
				painelBotao1.add(botaoCanc);
				painelLogin.add(painelBotao1);
				JPanel painelBotao2 = new JPanel(new FlowLayout());
				painelBotao2.add(alterarOsMeusDados1);
				painelLogin.add(painelBotao2);
				JScrollPane pane = new JScrollPane(table);
				panel.add(pane, BorderLayout.CENTER);
				panel.add(painelLogin, BorderLayout.SOUTH);
				TitledBorder border = new TitledBorder("Listagem dos seus dados de utilizador.");
				border.setTitleJustification(TitledBorder.CENTER);
				border.setTitlePosition(TitledBorder.TOP);
				panel.setBorder(border);
				getContentPane().add(panel);
				setVisible(true);
				table.setEnabled(false);
				table.setAutoCreateRowSorter(true);
			} else {
				setVisible(false);
				JOptionPane.showMessageDialog(new JanelaMenu(tipo, nomeLogin, nomeUtilizador, tempoInicial, st, 0),
						"Ups! Não foi possível mostrar os seus dados.", "Informação", JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	/**
	 * Lista notificações.
	 * 
	 * @param aComando
	 * @param aComando2
	 * @param aMsg
	 */
	private void listaNotificacao(String aComando) {

		String mensagem = aNotificacao.listaNotificacao(aComando, st);

		if (mensagem == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Não tem notificações novas!", "Notificações",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), mensagem, "Notificações", JOptionPane.INFORMATION_MESSAGE);
		}

		registaLog("Foram mostradas as notificações.");
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
