package projecto_3;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import projecto_3.graficos.JanelaRegisto;
import projecto_3.utilizador.GereAdministrador;
import projecto_3.utilizador.GereUtilizador;

public class ClassePrincipal implements ActionListener {

	private static GereUtilizador aUtilizador = new GereUtilizador();
	private static GereBaseDados aBaseDados = new GereBaseDados();
	private static GereAdministrador aAdministrador = new GereAdministrador();
	private static String nomeUtilizador = null, nomeLogin = null;
	private static String dbname = "", dbuser = "", dbpassword = "", dburl = "", dbporto = "";
	private static Statement st = null;
	private static int tipoInicio = 0;
	private static long tempoTotal = 0;
	private static boolean activo = false, primeiro = false;

	public static void main(String[] args) {
		arranqueDados();
		tempoTotal = new Date().getTime();
		new JanelaMenu(tempoTotal);
	}

	private JanelaLogin janelaLogin;
	private JanelaRegisto janelaRegisto;
	
    /**
    * Cria um janela de registo ou de login 
    * @param tipoJanela
    * @param aActivo
    * @param aNomeLogin
    * @param aNomeUtilizador
    */
	ClassePrincipal(int tipoJanela, boolean aActivo, String aNomeLogin, String aNomeUtilizador) {
		activo = aActivo;
		nomeLogin = aNomeLogin;
		nomeUtilizador = aNomeUtilizador;
		if (tipoJanela == 1) {
			arranqueDados();
			if (st == null) {
				st = aBaseDados.iniciaBaseDados(dbporto, dburl, dbuser, dbpassword, dbname);
			}
			if (st != null) {
				if (aAdministrador.verificaListaVazia(st)) {
					JOptionPane.showMessageDialog(new JFrame(),
							"N�o existe nenhum administrador registado, a criar um...", "Erro",
							JOptionPane.ERROR_MESSAGE);
					activo = true;
					primeiro = true;
					janelaRegisto = new JanelaRegisto(this, true, true);
					janelaRegisto.setBounds(new Rectangle(200, 200, 570, 270));
					janelaRegisto.setTitle("Registo");
					janelaRegisto.setResizable(false);
					janelaRegisto.setVisible(true);
				} else {
					janelaLogin = new JanelaLogin(this);
					janelaLogin.setBounds(new Rectangle(200, 200, 350, 170));
					janelaLogin.setTitle("Autentica��o");
					janelaLogin.setResizable(false);
					janelaLogin.setVisible(true);

				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"N�o foi poss�vel conectar � base de dados! Verifique os dados de acesso.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				new JanelaMenu(tempoTotal);
			}
		}

		if (tipoJanela == 2) {
			arranqueDados();
			if (st == null) {
				st = aBaseDados.iniciaBaseDados(dbporto, dburl, dbuser, dbpassword, dbname);
			}
			if (st != null) {
				janelaRegisto = new JanelaRegisto(this, activo, false);
				janelaRegisto.setBounds(new Rectangle(200, 200, 570, 270));
				janelaRegisto.setTitle("Registo");
				janelaRegisto.setResizable(false);
				janelaRegisto.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"N�o foi poss�vel conectar � base de dados! Verifique os dados de acesso.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				new JanelaMenu(tempoTotal);
			}
		}

	}

	/**
	 * Gest�o de eventos
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("ok")) {
			nomeLogin = janelaLogin.autenticacao(st);
			tipoInicio = aUtilizador.verificaTipo(nomeLogin, st);
			nomeUtilizador = aUtilizador.getNomeUtilizador(nomeLogin, st);
			if (tipoInicio == 1) {
				janelaLogin.setVisible(false);
				JOptionPane.showMessageDialog(new JFrame(), "Ol�, " + nomeUtilizador + "!", "Informa��o",
						JOptionPane.INFORMATION_MESSAGE);
				new JanelaMenu(tipoInicio, nomeLogin, nomeUtilizador, tempoTotal, st, 1);
			} else if (tipoInicio == 2) {
				janelaLogin.setVisible(false);
				JOptionPane.showMessageDialog(new JFrame(), "Ol�, " + nomeUtilizador + "!", "Informa��o",
						JOptionPane.INFORMATION_MESSAGE);
				new JanelaMenu(tipoInicio, nomeLogin, nomeUtilizador, tempoTotal, st, 0);
			} else if (nomeLogin == null) {
				JOptionPane.showMessageDialog(new JFrame(), "Os dados que introduziu n�o est�o correctos.",
						"Informa��o", JOptionPane.ERROR_MESSAGE);
			} else if (nomeLogin == "") {
				JOptionPane.showMessageDialog(new JFrame(), "A sua conta est� inactiva ou foi desactivada.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getActionCommand().equals("voltar")) {
			janelaLogin.setVisible(false);
			new JanelaMenu(tempoTotal);
		}

		if (e.getActionCommand().equals("registar")) {
			String resultado = janelaRegisto.regista(st);

			if (resultado.equals(":)")) {
				janelaRegisto.setVisible(false);
				if (activo) {
					JOptionPane.showMessageDialog(new JFrame(), "O utilizador foi registado com sucesso!", "Informa��o",
							JOptionPane.INFORMATION_MESSAGE);
					if (primeiro) {
						new JanelaMenu(tempoTotal);
					} else {
						new JanelaMenu(1, nomeLogin, nomeUtilizador, tempoTotal, st, 0);
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"O utilizador foi registado com sucesso, aguarde a activa��o da conta por parte do Administrador!",
							"Informa��o", JOptionPane.INFORMATION_MESSAGE);
					new JanelaMenu(tempoTotal);
				}
			} else if (resultado == "") {
				JOptionPane.showMessageDialog(new JFrame(),
						"Ocorreu um erro a registar o utilizador. Por favor, tente novamente!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getActionCommand().equals("voltarRegisto")) {
			janelaRegisto.setVisible(false);
			if (activo) {
				new JanelaMenu(1, nomeLogin, nomeUtilizador, tempoTotal, st, 0);
			} else {
				new JanelaMenu(tempoTotal);
			}

		}

	}

	/**
	 * Se n�o for encontrado o ficheiro com os dados de acesso � base de dados, s�o
	 * colocados uns dados gen�ricos.
	 */
	private static void colocaDados() {
		Properties prop = new Properties();

		prop.setProperty("dbname", "db");
		prop.setProperty("dburl", "localhost");
		prop.setProperty("dbuser", "root");
		prop.setProperty("dbpassword", "root");
		prop.setProperty("dbporto", "3306");

		dbname = "db";
		dbuser = "root";
		dburl = "localhost";
		dbpassword = "root";
		dbporto = "3306";

		try {
			prop.store(new FileOutputStream("config.properties"), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Verifica se o ficheiro que contem os dados de acesso � base de dados existe,
	 * se n�o existir os dados ser�o os dados gen�ricos que constam no manual de
	 * utilizador.
	 */
	private static void arranqueDados() {

		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("config.properties"));
			dbname = prop.getProperty("dbname");
			dbuser = prop.getProperty("dbuser");
			dburl = prop.getProperty("dburl");
			dbpassword = prop.getProperty("dbpassword");
			dbporto = prop.getProperty("dbporto");
		} catch (FileNotFoundException fnde) {
			JOptionPane.showMessageDialog(new JFrame(),
					"O ficheiro com os dados de acesso � base de dados n�o foi encontrado e, por isso, os dados de acesso ser�o os de origem.",
					"Informa��o", JOptionPane.INFORMATION_MESSAGE);
			colocaDados();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
