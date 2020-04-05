package projecto_3;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import projecto_3.utilizador.GereUtilizador;
import projecto_3.informGerais.GereLog;
import projecto_3.informGerais.Log;

public class JanelaLogin extends JFrame {

	private Container cont;
	private JButton botaoOK, botaoCanc;
	private JTextField caixaLogin, caixaPassword;
	private GereUtilizador aUtilizador = new GereUtilizador();
	private GereLog aLog = new GereLog();
	private Statement st = null;
	
	/**
	 * Cria uma janela de login
	 * @param aListener
	 */
	JanelaLogin (ClassePrincipal aListener) {
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		
		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTopo.add(new JLabel("Janela de Autenticação"));
		
		GridLayout gl = new GridLayout(3,2);
		gl.setHgap(2);
		gl.setVgap(2);
		JPanel painelLogin = new JPanel(gl);
		
		JPanel painelLabelLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelLogin.add(new JLabel("Login"));
		
		JPanel painelLabelPassword= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelPassword.add(new JLabel("Password"));
		
		JPanel painelCaixaLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaLogin = new JTextField(10);
		painelCaixaLogin.add(caixaLogin);
		caixaLogin.setToolTipText("Insira aqui o seu login!");
		
		JPanel painelCaixaPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaPassword = new JPasswordField(10);
		painelCaixaPassword.add(caixaPassword);
		caixaPassword.setToolTipText("Insira aqui a sua password!");
		
		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botaoCanc = new JButton ("  Voltar  ");
		botaoCanc.setActionCommand("voltar");
		botaoCanc.addActionListener(aListener);
		painelBotao.add(botaoCanc);
		botaoOK = new JButton ("  OK  ");
		botaoOK.setActionCommand("ok");
		botaoOK.addActionListener(aListener);
		painelBotao.add(botaoOK);
		
		painelLogin.add(painelLabelLogin);
		painelLogin.add(painelCaixaLogin);
		
		painelLogin.add(painelLabelPassword);
		painelLogin.add(painelCaixaPassword);
		
		painelLogin.add(new JPanel());
		painelLogin.add(painelBotao);
		
		cont.add(painelTopo, BorderLayout.NORTH);
		cont.add(painelLogin, BorderLayout.CENTER);
		cont.add(new JPanel(), BorderLayout.SOUTH);
	}
	
	/**
	 * Lê os dados inseridos pelo utilizador
	 * @param aSt
	 * @return login se correr tudo bem
	 */
	public String autenticacao(Statement aSt) {
		st = aSt;
		String login = caixaLogin.getText();
		String password = caixaPassword.getText();
			
		if (login.length() > 0 && password.length() > 0)
			return (verificaAutenticacao(login, password));
		else {
			String mensagem;
			
			if (login.length() == 0) {
				if (password.length() == 0) { 
					mensagem = "Os campos LOGIN e PASSWORD devem ser preenchidos";
				} else {
					mensagem = "O campo LOGIN deve ser preenchido";
				}
			} else {
				mensagem = "O campo PASSWORD deve ser preenchido";
			}
					
			JOptionPane.showMessageDialog(this,  mensagem,  "Informação", JOptionPane.ERROR_MESSAGE);
			return " ";
		}
	}
	
	/**
	 * Verifica a autenticação do utilizador
	 * @param aLogin
	 * @param aPassword
	 * @return login se for efectuado com sucesso
	 */
	private String verificaAutenticacao(String aLogin, String aPassword) {
		if (aUtilizador.verificaAutenticacao(aLogin, aPassword, st)) {
			if (!aUtilizador.verificaEstadoReprovacao(aLogin, st)) {
				if (aUtilizador.verificaEstado(aLogin, st)) {
					registaLog("Efectou o login na aplicação.", aLogin);
					return aLogin;
				} else {
					return "";
				}
			} else {
				return "";
			}
		} else {
			return null;
		}
	}	
	
	/**
	 * Regista os logs.
	 * 
	 * @param aAccao
	 */
	private void registaLog(String aAccao, String nomeLogin) {

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
