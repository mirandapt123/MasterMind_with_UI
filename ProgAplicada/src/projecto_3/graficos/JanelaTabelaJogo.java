package projecto_3.graficos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import projecto_3.JanelaMenu;
import projecto_3.informGerais.GereLog;
import projecto_3.informGerais.GereNotificacao;
import projecto_3.informGerais.Log;
import projecto_3.jogo.GereJogada;
import projecto_3.jogo.GereJogo;
import projecto_3.jogo.Jogada;
import projecto_3.jogo.Jogo;
import projecto_3.utilizador.GereJogador;
import projecto_3.utilizador.GereUtilizador;

public class JanelaTabelaJogo extends JFrame implements ActionListener {

	private Container cont;
	private JFrame jfra;
	private String nomeLogin = null, nomeUtilizador = null;
	private GereUtilizador aUtilizador = new GereUtilizador();
	private GereJogador aJogador = new GereJogador();
	private GereJogo aJogo = new GereJogo();
	private GereJogada aJogada = new GereJogada();
	private GereLog aLog = new GereLog();
	private GereNotificacao aNotificacao = new GereNotificacao();
	private String[] escolha;
	private Statement st = null;
	private int numeroJogada = 0, numeroB = 0, idJogo = -1, numeroTotalLinhas = 0;
	private long tempoInicial, tempoTotalJogada = 0;
	private String tempoInicioJogo = "", tempoInicioJogada = "", idJogo1 = "";
	private JButton[][] botao = new JButton[20][8];
	private JButton vermelho, rosa, castanho, azul, laranja, magenta, desistir, dica, submeter, sair, simular;
	private String matrizJogo[][];

	/**
	 * Cria uma janela de jogo
	 * @param numLinhas
	 * @param aSt
	 * @param aTempoInicial
	 * @param aLogin
	 * @param aNome
	 */
	public JanelaTabelaJogo(int numLinhas, Statement aSt, long aTempoInicial, String aLogin, String aNome) {
		nomeLogin = aLogin;
		nomeUtilizador = aNome;
		tempoInicial = aTempoInicial;
		numeroTotalLinhas = numLinhas;
		escolha = new String[4];
		escolha = escolheRand();
		matrizJogo = new String[numLinhas][8];
		for (int i = 0; i < numLinhas; i++) {
			for (int j = 0; j < 8; j++) {
				matrizJogo[i][j] = " ";
			}
		}
		st = aSt;
		cont = getContentPane();
		cont.setLayout(new BorderLayout());

		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelTopo.add(new JLabel(
				"                                  As suas jogadas                                                                                                Resultado"));
		JPanel painelBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		desistir = new JButton("  Desistir  ");
		desistir.addActionListener(this);
		dica = new JButton("  Pedir dica  ");
		dica.addActionListener(this);
		submeter = new JButton("  Submeter esta jogada  ");
		submeter.addActionListener(this);
		painelBaixo.add(submeter);
		painelBaixo.add(Box.createHorizontalStrut(30));
		painelBaixo.add(dica);
		painelBaixo.add(Box.createHorizontalStrut(30));
		painelBaixo.add(desistir);

		JPanel p = new JPanel();
		for (int i = numLinhas - 1; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (j == 4) {
					p.add(Box.createVerticalStrut(45));
				}
				botao[i][j] = new JButton();
				int njogada = i + 1;
				if (j <= 3) {
					botao[i][j].setText("J nº" + njogada);
				} else {
					botao[i][j].setText("R nº" + njogada);
				}
				botao[i][j].addActionListener(this);
				p.add(botao[i][j]);
				if (i != 0) {
					botao[i][j].setEnabled(false);
				} else if (j > 3) {
					botao[i][j].setEnabled(false);
				}
			}
		}
		setResizable(false);

		p.setLayout(new GridLayout(numLinhas, 8));
		add(p);
		setVisible(true);

		cont.add(painelTopo, BorderLayout.NORTH);
		cont.add(p, BorderLayout.CENTER);
		cont.add(painelBaixo, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tempoInicioJogo = obtemTempoInicio();
		tempoInicioJogada = obtemTempoInicio();
		Jogo jogo1 = new Jogo(aUtilizador.getIdUser(st, nomeLogin), tempoInicioJogo);
		if (aJogo.registaJogo(jogo1, st)) {
			idJogo = aJogo.verificaQuantidadeJogo("", st);
			Jogada jogada = new Jogada(idJogo, "<" + nomeLogin + "> <linhas> " + numLinhas);
			aJogada.registaJogada(jogada, st);
			jogada = new Jogada(idJogo, "<" + nomeLogin + "> <1> <jogopc>;");
			aJogada.registaJogada(jogada, st);
		} else {
			JOptionPane.showMessageDialog(this,
					"Ocorreu um erro a gravar o jogo e, por isso, o mesmo não poderá ser revisto.",
					"Erro a gravar o jogo", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	/**
	 * Cria uma janela para simular um jogo
	 * @param aSt
	 * @param aTempoInicial
	 * @param aLogin
	 * @param aNome
	 * @param aIdJogo
	 */
	public JanelaTabelaJogo(Statement aSt, long aTempoInicial, String aLogin, String aNome, String aIdJogo) {
		nomeLogin = aLogin;
		nomeUtilizador = aNome;
		tempoInicial = aTempoInicial;
		idJogo1 = aIdJogo;
		int numLinhas = 0;
		st = aSt;
		Vector<Jogada> listaJogada = aJogada.ListaJogada(aIdJogo, st);
		if (listaJogada != null && listaJogada.size() > 0) {
			Enumeration<Jogada> en = listaJogada.elements();
			while (en.hasMoreElements()) {
				String[] splited = en.nextElement().getAccao().split("\\s+");
				if (splited[1].equals("<linhas>")) {
					numLinhas = Integer.parseInt(splited[2]);
				}
			}
		}
		matrizJogo = new String[numLinhas][8];
		for (int i = 0; i < numLinhas; i++) {
			for (int j = 0; j < 8; j++) {
				matrizJogo[i][j] = " ";
			}
		}
		cont = getContentPane();
		cont.setLayout(new BorderLayout());

		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelTopo.add(new JLabel(
				"                                  As suas jogadas                                                                                                Resultado"));
		JPanel painelBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		sair = new JButton("  Sair  ");
		sair.addActionListener(this);
		painelBaixo.add(sair);
		
		simular = new JButton("  Simular  ");
		simular.addActionListener(this);
		painelBaixo.add(simular);

		JPanel p = new JPanel();
		for (int i = numLinhas - 1; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (j == 4) {
					p.add(Box.createVerticalStrut(45));
				}
				botao[i][j] = new JButton();
				int njogada = i + 1;
				if (j <= 3) {
					botao[i][j].setText("J nº" + njogada);
					botao[i][j].setEnabled(false);
				} else {
					botao[i][j].setText("R nº" + njogada);
					botao[i][j].setEnabled(false);
				}
				botao[i][j].addActionListener(this);
				botao[i][j].setEnabled(false);
				p.add(botao[i][j]);
				if (i != 0) {
					botao[i][j].setEnabled(false);
				} else if (j > 3) {
					botao[i][j].setEnabled(false);
				}
			}
		}
		setResizable(true);

		p.setLayout(new GridLayout(numLinhas, 8));
		add(p);
		setVisible(true);

		cont.add(painelTopo, BorderLayout.NORTH);
		cont.add(p, BorderLayout.CENTER);
		cont.add(painelBaixo, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	/**
	 * Vê horas de inicio do jogo
	 * 
	 * @return tempo de inicial do jogo
	 */
	private String obtemTempoInicio() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * Escolhe as cores do computador alteatoriamente
	 * 
	 * @return String [] cores.
	 */
	private String[] escolheRand() {
		int repeticao = 0, contadorCor = 0;
		String[] cores = { "V", "A", "R", "C", "L", "M" };
		String coresEscolhidas[] = new String[4];

		while (contadorCor <= 3) {
			int rnd = new Random().nextInt(cores.length);

			for (int j = 0; j < coresEscolhidas.length; j++) {
				if (coresEscolhidas[j] != null) {
					if (coresEscolhidas[j].equals(cores[rnd])) {
						repeticao++;
					}
				}
			}
			if (repeticao != 0) {
				repeticao = 0;
			} else {
				coresEscolhidas[contadorCor] = cores[rnd];
				contadorCor++;
			}
		}

		return coresEscolhidas;
	}

	/**
	 * calcula o tempo que decorreu uma jogada
	 * 
	 * @param aDataInicial
	 * @param aMsg
	 * @return tempo que decorreu a jogada
	 */
	private String calculaTempoJogada(String aDataInicial, String aMsg) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dataFinal = format.format(new Date());

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(aDataInicial);
			d2 = format.parse(dataFinal);

			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;

			return aMsg + diffHours + "h:" + diffMinutes + "m:" + diffSeconds + "s.";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * calcula o tempo total das jogadas
	 * 
	 * @param aTempoTotalMs
	 * @param aMsg
	 * @return tempo total das jogadas
	 */
	private String calculaTempoTotalJogada(long aTempoTotalMs, String aMsg) {

		try {

			long diffSeconds = aTempoTotalMs / 1000 % 60;
			long diffMinutes = aTempoTotalMs / (60 * 1000) % 60;
			long diffHours = aTempoTotalMs / (60 * 60 * 1000) % 24;

			return aMsg + diffHours + "h:" + diffMinutes + "m:" + diffSeconds + "s.";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * calcula o tempo total das jogadas
	 * 
	 * @param aTempoTotalMs
	 * @param aDataInicial
	 * @param aMsg
	 * @return tempo total das jogadas
	 */
	private long devolveTempoMs(String aDataInicial) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dataFinal = format.format(new Date());

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(aDataInicial);
			d2 = format.parse(dataFinal);

			long diff = d2.getTime() - d1.getTime();

			return diff;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * calcula o tempo que o jogador esteve em jogo
	 * 
	 * @param aIdCert
	 * @return tempo que o jogador esteve em jogo
	 */
	private static long calculaTempoCert(String aTempo) {
		String dataInicial = aTempo;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String dataFinal = format.format(new Date());

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dataInicial);
			d2 = format.parse(dataFinal);

			long diff = d2.getTime() - d1.getTime();

			return diff;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Função que simula um jogo.
	 * 
	 * @param aIdJogo
	 */
	private void simulaJogo(String aIdJogo) {
		Vector<Jogada> listaJogada = aJogada.ListaJogada(aIdJogo, st);
		int contador = 0;
		if (listaJogada != null && listaJogada.size() > 0) {
			Enumeration<Jogada> en = listaJogada.elements();
			while (en.hasMoreElements()) {
				String[] splited = en.nextElement().getAccao().split("\\s+");
				if (splited[1].equals("<1>") && splited[2].equals("<jogopc>;")) {
					contador = 1;
				}
			}
		}

		if (contador == 1) {
			int numLinha = 0;
			if (listaJogada != null && listaJogada.size() > 0) {
				Enumeration<Jogada> en = listaJogada.elements();
				while (en.hasMoreElements()) {
					String[] splited = en.nextElement().getAccao().split("\\s+");
					if (splited[1].equals("<linhas>")) {
						numLinha = Integer.parseInt(splited[2]);
					}
				}
			}
			numeroJogada = 0;
			String matrizJogo[][] = new String[numLinha][8];
			for (int i = 0; i < numLinha; i++) {
				for (int j = 0; j < 8; j++) {
					matrizJogo[i][j] = " ";
				}
			}
			if (listaJogada != null && listaJogada.size() > 0) {
				Enumeration<Jogada> en = listaJogada.elements();
				while (en.hasMoreElements()) {
					matrizJogo = refazJogadaComputador(en.nextElement().getAccao(), matrizJogo, numLinha);
				}
			}
		}

	}

	/**
	 * Refaz uma jogada contra o computador.
	 * 
	 * @param aMensagem
	 * @param aMatriz
	 * @param aNumLinha
	 */
	private String[][] refazJogadaComputador(String aMensagem, String aMatriz[][], int aNumLinha) {
		String[] splited = aMensagem.split("\\s+");

		if (splited[1].equals("<play>")) {
			aMatriz[numeroJogada][0] = "" + splited[2].charAt(1);
			aMatriz[numeroJogada][1] = "" + splited[2].charAt(3);
			aMatriz[numeroJogada][2] = "" + splited[2].charAt(5);
			aMatriz[numeroJogada][3] = "" + splited[2].charAt(7);
			char [] carac = new char [4];
			carac [0] = splited[2].charAt(1);
			carac [1] = splited[2].charAt(3);
			carac [2] = splited[2].charAt(5);
			carac [3] = splited[2].charAt(7);
	
			for (int i = 0; i < 4; i++) {
				if (carac [i] == 'V') {
					botao[numeroJogada][i].setBackground(Color.RED);
					botao[numeroJogada][i].setText("");
				} else if (carac [i] == 'A') {
					botao[numeroJogada][i].setBackground(Color.BLUE);
					botao[numeroJogada][i].setText("");
				} else if (carac [i] == 'R') {
					botao[numeroJogada][i].setBackground(Color.PINK);
					botao[numeroJogada][i].setText("");
				} else if (carac [i] == 'C') {
					botao[numeroJogada][i].setBackground(new Color(102, 51, 0));
					botao[numeroJogada][i].setText("");
				} else if (carac [i] == 'L') {
					botao[numeroJogada][i].setBackground(new Color(255, 140, 0));
					botao[numeroJogada][i].setText("");
				} else if (carac [i] == 'M') {
					botao[numeroJogada][i].setBackground(new Color(205, 0, 255));
					botao[numeroJogada][i].setText("");
				}
			}
			numeroJogada++;
			return aMatriz;
		}

		if (splited[1].equals("<result>")) {
			aMatriz[numeroJogada - 1][4] = "" + splited[2].charAt(1);
			aMatriz[numeroJogada - 1][5] = "" + splited[2].charAt(3);
			aMatriz[numeroJogada - 1][6] = "" + splited[2].charAt(5);
			aMatriz[numeroJogada - 1][7] = "" + splited[2].charAt(7);
			char [] carac = new char [4];
			carac [0] = splited[2].charAt(1);
			carac [1] = splited[2].charAt(3);
			carac [2] = splited[2].charAt(5);
			carac [3] = splited[2].charAt(7);
			for (int i = 4; i < 8; i++) {
				if (carac [i-4] == 'P') {
					botao[numeroJogada-1][i].setBackground(Color.BLACK);
					botao[numeroJogada-1][i].setText("");
				} else if (carac [i-4] == 'B') {
					botao[numeroJogada-1][i].setBackground(Color.WHITE);
					botao[numeroJogada-1][i].setText("");
				} else if (carac [i-4] == 'X') {
					botao[numeroJogada-1][i].setText("X");
				}
			}
			return aMatriz;
		}

		if (splited[2].equals("<defeat>;")) {
			JOptionPane.showMessageDialog(this, "Você perdeu este encontro contra o computador!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
		}

		if (splited[2].equals("<win>;")) {
			JOptionPane.showMessageDialog(this, "Você ganhou este encontro contra o computador!!!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
		}

		if (splited[2].equals("<desistiu>;")) {
			JOptionPane.showMessageDialog(this, "Você desistiu, logo perdeu este jogo contra o computador!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
		}
		return aMatriz;
	}


	/**
	 * Dá uma dica.
	 * 
	 * @param matrizJogo
	 * @param numLinha
	 * @param coresEscolhidas
	 * @return String[][] matriz.
	 */
	private String[][] daDica(String matrizJogo[][], int numLinha, String coresEscolhidas[]) {
		String dicaMatriz[] = new String[4];
		int contadorCor = 0;
		boolean dica = false;

		while (!dica) {
			for (int j = 0; j < 4; j++) {
				int rnd = new Random().nextInt(coresEscolhidas.length);
				dicaMatriz[j] = coresEscolhidas[rnd];
			}
			for (int i = 0; i < 4; i++) {
				if (dicaMatriz[i].equals(coresEscolhidas[i])) {
					contadorCor++;
				}
			}
			if (contadorCor < 3) {
				dica = true;
			} else {
				contadorCor = 0;
			}
		}
		for (int i = 0; i < 4; i++) {
			matrizJogo[numLinha][i] = dicaMatriz[i];
			if (dicaMatriz[i].equals("V")) {
				botao[numeroJogada][i].setBackground(Color.RED);
				botao[numeroJogada][i].setText("");
				if (numeroJogada <= numeroTotalLinhas - 1) {
					botao[numeroJogada + 1][i].setEnabled(true);
				}
				botao[numeroJogada][i].setEnabled(false);
			} else if (dicaMatriz[i].equals("A")) {
				botao[numeroJogada][i].setBackground(Color.BLUE);
				botao[numeroJogada][i].setText("");
				if (numeroJogada <= numeroTotalLinhas - 1) {
					botao[numeroJogada + 1][i].setEnabled(true);
				}
				botao[numeroJogada][i].setEnabled(false);
			} else if (dicaMatriz[i].equals("R")) {
				botao[numeroJogada][i].setBackground(Color.PINK);
				botao[numeroJogada][i].setText("");
				if (numeroJogada <= numeroTotalLinhas - 1) {
					botao[numeroJogada + 1][i].setEnabled(true);
				}
				botao[numeroJogada][i].setEnabled(false);
			} else if (dicaMatriz[i].equals("C")) {
				botao[numeroJogada][i].setBackground(new Color(102, 51, 0));
				botao[numeroJogada][i].setText("");
				if (numeroJogada <= numeroTotalLinhas - 1) {
					botao[numeroJogada + 1][i].setEnabled(true);
				}
				botao[numeroJogada][i].setEnabled(false);
			} else if (dicaMatriz[i].equals("L")) {
				botao[numeroJogada][i].setBackground(new Color(255, 140, 0));
				botao[numeroJogada][i].setText("");
				if (numeroJogada <= numeroTotalLinhas - 1) {
					botao[numeroJogada + 1][i].setEnabled(true);
				}
				botao[numeroJogada][i].setEnabled(false);
			} else if (dicaMatriz[i].equals("M")) {
				botao[numeroJogada][i].setBackground(new Color(205, 0, 255));
				botao[numeroJogada][i].setText("");
				if (numeroJogada <= numeroTotalLinhas - 1) {
					botao[numeroJogada + 1][i].setEnabled(true);
				}
				botao[numeroJogada][i].setEnabled(false);
			}
		}
		introduzResultado(numeroJogada);
		numeroJogada++;
		JOptionPane.showMessageDialog(this, "Dica dada com sucesso!", "Dica", JOptionPane.INFORMATION_MESSAGE);
		return matrizJogo;
	}

	/**
	 * Gestor de eventos
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(sair)) {
			setVisible(false);
			new JanelaMenu(2, nomeLogin, nomeUtilizador, tempoInicial, st, 0);
		}
		
		if (e.getSource().equals(simular)) {
			simular.setEnabled(false);
			simulaJogo(idJogo1);
		}
		
		if (e.getSource().equals(botao[numeroJogada][0])) {
			numeroB = 0;
			trataCores();
		}

		if (e.getSource().equals(submeter)) {
			int contador = 0;
			for (int i = 0; i < 4; i++) {
				if (botao[numeroJogada][i].getText().equals("")) {
					contador++;
				}
			}
			if (contador == 4) {
				if (!introduzResultado(numeroJogada)) {
					numeroJogada++;
					if (numeroJogada >= numeroTotalLinhas) {
						tempoTotalJogada += devolveTempoMs(tempoInicioJogada);
						JOptionPane.showMessageDialog(this, "Perdeu o jogo contra o computador!", "PERDEU!",
								JOptionPane.INFORMATION_MESSAGE);
						if (idJogo != -1) {
							Jogada jogada = new Jogada(idJogo,
									"<" + nomeLogin + "> <play> <" + matrizJogo[numeroJogada - 1][0] + ","
											+ matrizJogo[numeroJogada - 1][1] + "," + matrizJogo[numeroJogada - 1][2]
											+ "," + matrizJogo[numeroJogada - 1][3] + ">;");
							aJogada.registaJogada(jogada, st);
							jogada = new Jogada(idJogo,
									"<" + nomeLogin + "> <result> <" + matrizJogo[numeroJogada - 1][4] + ","
											+ matrizJogo[numeroJogada - 1][5] + "," + matrizJogo[numeroJogada - 1][6]
											+ "," + matrizJogo[numeroJogada - 1][7] + ">;");
							aJogada.registaJogada(jogada, st);
							jogada = new Jogada(idJogo, "<" + nomeLogin + "> <status> <defeat>;");
							aJogada.registaJogada(jogada, st);
						}
						registaLog("Perdeu um jogo contra o computador.");
						String msg = calculaTempoJogada(tempoInicioJogada, "\nDemorou este tempo a efectuar esta jogada: ");
						msg += calculaTempoTotalJogada(tempoTotalJogada, "\nO tempo total das jogadas foi: ");
						msg += calculaTempoTotalJogada(tempoTotalJogada / numeroJogada,
								"\nO tempo médio das suas jogadas foi: ");
						msg += calculaTempoJogada(tempoInicioJogo, "\nO tempo total de jogo foi: ");
						if (aJogador.actualizaNumeroJogos(nomeLogin, st)
								&& aJogador.actualizaTempoJogador(calculaTempoCert(tempoInicioJogo), nomeLogin, st)) {
							msg += "\nO seu número de jogos e tempo total acumulado no jogo foram actualizados!";
							JOptionPane.showMessageDialog(this, msg, "Informações sobre o jogo",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							msg += "\nOcorreu um erro a actualizar o número de jogos e tempo total acumulado no jogo!";
							JOptionPane.showMessageDialog(this, msg, "Informações sobre o jogo",
									JOptionPane.INFORMATION_MESSAGE);
						}
						setVisible(false);
						new JanelaMenu(2, nomeLogin, nomeUtilizador, tempoInicial, st, 0);
					} else {
						for (int i = 0; i < 4; i++) {
							botao[numeroJogada - 1][i].setEnabled(false);
							botao[numeroJogada][i].setEnabled(true);
						}
						if (idJogo != -1) {
							Jogada jogada = new Jogada(idJogo,
									"<" + nomeLogin + "> <play> <" + matrizJogo[numeroJogada - 1][0] + ","
											+ matrizJogo[numeroJogada - 1][1] + "," + matrizJogo[numeroJogada - 1][2]
											+ "," + matrizJogo[numeroJogada - 1][3] + ">;");
							aJogada.registaJogada(jogada, st);
							jogada = new Jogada(idJogo,
									"<" + nomeLogin + "> <result> <" + matrizJogo[numeroJogada - 1][4] + ","
											+ matrizJogo[numeroJogada - 1][5] + "," + matrizJogo[numeroJogada - 1][6]
											+ "," + matrizJogo[numeroJogada - 1][7] + ">;");
							aJogada.registaJogada(jogada, st);
						}
						tempoTotalJogada += devolveTempoMs(tempoInicioJogada);

						String msg = "Já jogou " + numeroJogada + " jogada(s).\n";
						msg += calculaTempoJogada(tempoInicioJogada, "\nDemorou este tempo a efectuar a jogada: ");
						msg += calculaTempoTotalJogada(tempoTotalJogada, "\nO tempo total das jogadas é: ");
						msg += calculaTempoTotalJogada(tempoTotalJogada / numeroJogada,
								"\nO tempo médio das suas jogadas é: ");
						JOptionPane.showMessageDialog(this, msg, "Informações sobre o jogo",
								JOptionPane.INFORMATION_MESSAGE);
						tempoInicioJogada = obtemTempoInicio();
						if (numeroJogada == numeroTotalLinhas - 1) {
							dica.setEnabled(false);
						}
					}
				} else {
					tempoTotalJogada += devolveTempoMs(tempoInicioJogada);
					numeroJogada++;
					JOptionPane.showMessageDialog(this, "Ganhou!", "GANHOU!", JOptionPane.INFORMATION_MESSAGE);
					if (idJogo != -1) {
						Jogada jogada = new Jogada(idJogo,
								"<" + nomeLogin + "> <play> <" + matrizJogo[numeroJogada - 1][0] + ","
										+ matrizJogo[numeroJogada - 1][1] + "," + matrizJogo[numeroJogada - 1][2]
										+ "," + matrizJogo[numeroJogada - 1][3] + ">;");
						aJogada.registaJogada(jogada, st);
						jogada = new Jogada(idJogo,
								"<" + nomeLogin + "> <result> <" + matrizJogo[numeroJogada - 1][4] + ","
										+ matrizJogo[numeroJogada - 1][5] + "," + matrizJogo[numeroJogada - 1][6]
										+ "," + matrizJogo[numeroJogada - 1][7] + ">;");
						aJogada.registaJogada(jogada, st);
						jogada = new Jogada(idJogo, "<" + nomeLogin + "> <status> <win>;");
						aJogada.registaJogada(jogada, st);
					}
					
					String msg = "Precisou de " + numeroJogada + " jogada(s) para vencer o computador!";
					msg += calculaTempoJogada(tempoInicioJogada, "\nDemorou este tempo a efectuar esta jogada: ");
					msg += calculaTempoTotalJogada(tempoTotalJogada, "\nO tempo total das jogadas foi: ");
					msg += calculaTempoTotalJogada(tempoTotalJogada / numeroJogada,
							"\nO tempo médio das suas jogadas foi: ");
					msg += calculaTempoJogada(tempoInicioJogo, "\nO tempo total de jogo foi: ");
					String loginvit = aJogador.jogadorComMaisVit(st);
					if (aJogador.actualizaNumeroJogos(nomeLogin, st) && aJogador.actualizaNumeroVitorias(nomeLogin, st)
							&& aJogador.actualizaTempoJogador(calculaTempoCert(tempoInicioJogo), nomeLogin, st)) {
						msg += "\nO seu número de jogos, número de vitórias e tempo total acumulado no jogo foram actualizados!";
						JOptionPane.showMessageDialog(this, msg, "Informações sobre o jogo",
								JOptionPane.INFORMATION_MESSAGE);
						msg = "";
						int numVitorias = aJogador.obtemNumVitorias(nomeLogin, st);
						if (numVitorias % 10 == 0) {
							msg = "Parabéns! Atingiu as " + numVitorias + " vitórias.\n";
							aNotificacao.registaNotificacaoAdmin("O utilizador com o login: " + nomeLogin
									+ " atingiu as " + numVitorias + " vitórias.", st);
						}
						if (!loginvit.equals(nomeLogin) && aJogador.jogadorComMaisVit(st).equals(nomeLogin)) {
							msg += "Parabéns! Subiu ao primeiro lugar em número de vitórias!\n";
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							aNotificacao.registaNotificacaoAdmin(
									"O utilizador com o login: " + nomeLogin + " é neste momento ("
											+ format.format(new Date()) + ") o jogador com mais vitórias ("
											+ numVitorias + " vitórias) na lista de jogadores.",
									st);
						}
						if (!msg.equals("")) {
							JOptionPane.showMessageDialog(this, msg, "Parabéns!", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						msg += "\nOcorreu um erro a actualizar o número de jogos, número de vitórias e tempo total acumulado no jogo!";
						JOptionPane.showMessageDialog(this, msg, "Informações sobre o jogo",
								JOptionPane.INFORMATION_MESSAGE);
					}
					setVisible(false);
					new JanelaMenu(2, nomeLogin, nomeUtilizador, tempoInicial, st, 0);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Tem de escolher as 4 cores antes de submeter!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getSource().equals(desistir)) {
			registaLog("Desistiu contra o computador.");
			JOptionPane.showMessageDialog(this, "Fraquinho!!! Desistir é para os fracos!", "Desistiu",
					JOptionPane.INFORMATION_MESSAGE);
			if (idJogo != -1) {
				Jogada jogada = new Jogada(idJogo, "<" + nomeLogin + "> <status> <desistiu>;");
				aJogada.registaJogada(jogada, st);
			}
			if (aJogador.actualizaNumeroJogos(nomeLogin, st)
					&& aJogador.actualizaTempoJogador(calculaTempoCert(tempoInicioJogo), nomeLogin, st)) {
				JOptionPane.showMessageDialog(this,
						"O seu número de jogos e tempo total acumulado no jogo foram actualizados!",
						"Informações sobre a gravação", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"Ocorreu um erro a actualizar o número de jogos e tempo total acumulado no jogo!",
						"Informações sobre a gravação", JOptionPane.INFORMATION_MESSAGE);
			}
			setVisible(false);
			new JanelaMenu(2, nomeLogin, nomeUtilizador, tempoInicial, st, 0);
		}

		if (e.getSource().equals(dica)) {
			matrizJogo = daDica(matrizJogo, numeroJogada, escolha);
			if (idJogo != -1) {
				Jogada jogada = new Jogada(idJogo,
						"<" + nomeLogin + "> <play> <" + matrizJogo[numeroJogada - 1][0] + ","
								+ matrizJogo[numeroJogada - 1][1] + "," + matrizJogo[numeroJogada - 1][2] + ","
								+ matrizJogo[numeroJogada - 1][3] + ">;");
				aJogada.registaJogada(jogada, st);
				jogada = new Jogada(idJogo,
						"<" + nomeLogin + "> <result> <" + matrizJogo[numeroJogada - 1][4] + ","
								+ matrizJogo[numeroJogada - 1][5] + "," + matrizJogo[numeroJogada - 1][6] + ","
								+ matrizJogo[numeroJogada - 1][7] + ">;");
				aJogada.registaJogada(jogada, st);
			}
			if (numeroJogada == numeroTotalLinhas - 1) {
				dica.setEnabled(false);
			}
		}

		if (e.getSource().equals(botao[numeroJogada][1])) {
			numeroB = 1;
			trataCores();
		}

		if (e.getSource().equals(botao[numeroJogada][2])) {
			numeroB = 2;
			trataCores();
		}

		if (e.getSource().equals(botao[numeroJogada][3])) {
			numeroB = 3;
			trataCores();
		}

		if (e.getSource().equals(vermelho)) {
			jfra.setVisible(false);
			botao[numeroJogada][numeroB].setBackground(Color.RED);
			botao[numeroJogada][numeroB].setText("");
			matrizJogo[numeroJogada][numeroB] = "V";
		}

		if (e.getSource().equals(azul)) {
			jfra.setVisible(false);
			botao[numeroJogada][numeroB].setBackground(Color.BLUE);
			botao[numeroJogada][numeroB].setText("");
			matrizJogo[numeroJogada][numeroB] = "A";
		}

		if (e.getSource().equals(laranja)) {
			jfra.setVisible(false);
			botao[numeroJogada][numeroB].setBackground(new Color(255, 140, 0));
			botao[numeroJogada][numeroB].setText("");
			matrizJogo[numeroJogada][numeroB] = "L";
		}

		if (e.getSource().equals(magenta)) {
			jfra.setVisible(false);
			botao[numeroJogada][numeroB].setBackground(new Color(205, 0, 255));
			botao[numeroJogada][numeroB].setText("");
			matrizJogo[numeroJogada][numeroB] = "M";
		}

		if (e.getSource().equals(castanho)) {
			jfra.setVisible(false);
			botao[numeroJogada][numeroB].setBackground(new Color(102, 51, 0));
			botao[numeroJogada][numeroB].setText("");
			matrizJogo[numeroJogada][numeroB] = "C";
		}

		if (e.getSource().equals(rosa)) {
			jfra.setVisible(false);
			botao[numeroJogada][numeroB].setBackground(Color.PINK);
			botao[numeroJogada][numeroB].setText("");
			matrizJogo[numeroJogada][numeroB] = "R";
		}

	}

	/**
	 * Introduz o resultado na matriz
	 * 
	 * @param matrizJogo
	 * @param numLinha
	 * @param escolha
	 */
	private boolean introduzResultado(int numLinha) {
		int contador = 0, contador1 = 4, caracter[] = new int[4], contadorP = 0;
		String resultado[] = new String[4];

		for (int i = 0; i < caracter.length; i++) {
			caracter[i] = -1;
		}

		for (int i = 0; i < 4; i++) {
			if (matrizJogo[numLinha][i].equals(escolha[i])) {
				resultado[contador] = "P";
				contador++;
				caracter[i]++;
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < escolha.length; j++) {
				if (i != j && contador < 4) {
					if (matrizJogo[numLinha][i].equals(escolha[j]) && caracter[j] == -1) {
						resultado[contador] = "B";
						caracter[j]++;
						contador++;
					}
				}
			}
		}

		if (contador < 4) {
			for (int i = contador; i < 4; i++) {
				resultado[contador] = "X";
				contador++;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (resultado[i].equals("P")) {
				matrizJogo[numLinha][contador1] = "P";
				botao[numLinha][contador1].setBackground(Color.BLACK);
				botao[numLinha][contador1].setText("");
				contador1++;
				contadorP++;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (resultado[i].equals("B")) {
				matrizJogo[numLinha][contador1] = "B";
				botao[numLinha][contador1].setBackground(Color.WHITE);
				botao[numLinha][contador1].setText("");
				contador1++;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (resultado[i].equals("X")) {
				matrizJogo[numLinha][contador1] = "X";
				botao[numLinha][contador1].setText("X");
				contador1++;
			}
		}

		if (contadorP == 4) {
			return true;
		}

		return false;
	}

	/**
	 * Cria uma janela com as 6 cores disponíveis
	 */
	private void trataCores() {
		jfra = new JFrame();
		JPanel p = new JPanel();
		vermelho = new JButton();
		vermelho.addActionListener(this);
		vermelho.setBackground(Color.RED);
		azul = new JButton();
		azul.addActionListener(this);
		azul.setBackground(Color.BLUE);
		rosa = new JButton();
		rosa.addActionListener(this);
		rosa.setBackground(Color.PINK);
		castanho = new JButton();
		castanho.addActionListener(this);
		castanho.setBackground(new Color(102, 51, 0));
		laranja = new JButton();
		laranja.addActionListener(this);
		laranja.setBackground(new Color(255, 140, 0));
		magenta = new JButton();
		magenta.addActionListener(this);
		magenta.setBackground(new Color(205, 0, 255));
		p.add(vermelho);
		p.add(azul);
		p.add(rosa);
		p.add(castanho);
		p.add(laranja);
		p.add(magenta);
		setResizable(false);
		p.setLayout(new GridLayout(1, 1));
		add(p);
		jfra.add(p);
		jfra.setBounds(new Rectangle(100, 100, 300, 300));
		jfra.setTitle("Escolha as suas cores:");
		jfra.setResizable(false);
		jfra.setVisible(true);
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
