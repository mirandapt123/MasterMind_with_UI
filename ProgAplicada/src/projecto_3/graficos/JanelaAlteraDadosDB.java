package projecto_3.graficos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projecto_3.JanelaMenu;

public class JanelaAlteraDadosDB extends JFrame implements ActionListener {

	private Container cont;
	private JButton botaoOK, botaoCanc;
	private JTextField caixaLogin, caixaPassword, caixaNome, caixaPorto, caixaUrl;
	private Statement st = null;
	private long tempoInicial = 0;

	/**
	 * Cria a janela para alterar os dados de acesso à base de dados.
	 * @param aSt
	 * @param aTempoInicial
	 */
	public JanelaAlteraDadosDB(Statement aSt, long aTempoInicial) {
		st = aSt;
		tempoInicial = aTempoInicial;
		cont = getContentPane();
		cont.setLayout(new BorderLayout());

		JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelTopo.add(new JLabel("Janela de Alteração de Dados da Base de Dados"));

		GridLayout gl = new GridLayout(6, 2);
		gl.setHgap(2);
		gl.setVgap(2);
		JPanel painelLogin = new JPanel(gl);
		String dbname = "";
		String dbuser = "";
		String dburl = "";
		String dbpassword = "";
		String dbporto = "";
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("config.properties"));
			dbname = prop.getProperty("dbname");
			dbuser = prop.getProperty("dbuser");
			dburl = prop.getProperty("dburl");
			dbpassword = prop.getProperty("dbpassword");
			dbporto = prop.getProperty("dbporto");
		} catch (FileNotFoundException fnde) {

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel painelLabelLogin = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelLogin.add(new JLabel("Nome da Base de Dados:"));

		JPanel painelLabelPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelPassword.add(new JLabel("Password:"));

		JPanel painelLabelNome = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelNome.add(new JLabel("User:"));

		JPanel painelLabelUrl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelUrl.add(new JLabel("Url:"));

		JPanel painelLabelPorto = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		painelLabelPorto.add(new JLabel("Porto:"));

		JPanel painelCaixaLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaLogin = new JTextField(10);
		caixaLogin.setText(dbname);
		painelCaixaLogin.add(caixaLogin);
		caixaLogin.setToolTipText("Insira aqui o nome da base de dados!");

		JPanel painelCaixaPassword = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaPassword = new JTextField(10);
		caixaPassword.setText(dbpassword);
		painelCaixaPassword.add(caixaPassword);
		caixaPassword.setToolTipText("Insira aqui a sua password!");

		JPanel painelCaixaNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaNome = new JTextField(10);
		caixaNome.setText(dbuser);
		painelCaixaNome.add(caixaNome);
		caixaNome.setToolTipText("Insira aqui o user!");

		JPanel painelCaixaUrl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaUrl = new JTextField(15);
		caixaUrl.setText(dburl);
		painelCaixaUrl.add(caixaUrl);
		caixaUrl.setToolTipText("Insira aqui o url!");

		JPanel painelCaixaPorto = new JPanel(new FlowLayout(FlowLayout.LEFT));
		caixaPorto = new JTextField(15);
		caixaPorto.setText(dbporto);
		painelCaixaPorto.add(caixaPorto);
		caixaPorto.setToolTipText("Insira aqui o porto!");

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

		painelLogin.add(painelLabelUrl);
		painelLogin.add(painelCaixaUrl);

		painelLogin.add(painelLabelPorto);
		painelLogin.add(painelCaixaPorto);

		painelLogin.add(new JPanel());
		painelLogin.add(painelBotao);

		cont.add(painelTopo, BorderLayout.NORTH);
		cont.add(painelLogin, BorderLayout.CENTER);
		cont.add(new JPanel(), BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * Altera os dados da base de dados
	 * @param aSt
	 */
	public void altera(Statement aSt) {
		st = aSt;
		String name = caixaLogin.getText();
		String password = caixaPassword.getText();
		String user = caixaNome.getText();
		String porto = caixaPorto.getText();
		String url = caixaUrl.getText();

		if (name.length() > 0 && password.length() > 0 && user.length() > 0 && porto.length() > 0 && url.length() > 0) {
			Properties prop = new Properties();

			prop.setProperty("dbname", name);
			prop.setProperty("dburl", url);
			prop.setProperty("dbuser", user);
			prop.setProperty("dbpassword", password);
			prop.setProperty("dbporto", porto);

			try {
				prop.store(new FileOutputStream("config.properties"), null);
				JOptionPane.showMessageDialog(this, "Dados alterados com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				JFrame janelaMenu = new JanelaMenu(tempoInicial);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Ocorreu um erro!", "Erro", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
				JFrame janelaMenu = new JanelaMenu(tempoInicial);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gestor de eventos
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(botaoCanc)) {
			setVisible(false);
			JFrame janelaMenu = new JanelaMenu(tempoInicial);
		}

		if (e.getSource().equals(botaoOK)) {
			altera(st);
		}

	}
}
