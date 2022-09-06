package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.log4j.Logger;

import util.Util;

public class Login extends JFrame{
	private static final long serialVersionUID = 4131194516522208537L;
	private static final Logger logger = Logger.getLogger(Login.class);
	private Dotenv dotenv = Dotenv.load();
	
	private JTextField emailField;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JButton loginBtn;
	private JPasswordField passwordField;
	
	public static void main(String args[]){
		Util.getLog4j();
		
		EventQueue.invokeLater(new Runnable() {
			//dispatch thread가 곧바로 처리
			public void run() {
				logger.info("==========Start App===========");
				new Login().setVisible(true);
			}
		});
	}
	public Login() {
		initComponents();
	}

	private void initComponents() {
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Advance Reply Bot");
		setResizable(false);
		
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		emailField = new JTextField();
		jLabel3 = new JLabel();
		jPanel1 = new JPanel();
		loginBtn = new JButton();
		passwordField = new JPasswordField();
		
		jPanel1.setBackground(new Color(57,57,57));
		
		jLabel1.setFont(new Font("Century Gothic", 1, 24));
		jLabel1.setForeground(new Color(255, 255, 255));
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setText("Advance Reply Bot v2.1");
		
		jLabel2.setFont(new Font("Century Gothic", 0, 14));
		jLabel2.setForeground(new Color(255, 255, 255));
		jLabel2.setText("Email");
		
		emailField.setBackground(new Color(57,57,57));
		emailField.setFont(new Font("Century Gothic", 0, 14));
		emailField.setForeground(new Color(255, 255, 255));
		emailField.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
		emailField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == '\n')
					login();
			}
		});
		
		jLabel3.setFont(new Font("Century Gothic", 0, 14));
		jLabel3.setForeground(new Color(255, 255, 255));
		jLabel3.setText("Password");
		
		passwordField.setBackground(new Color(57, 57, 57));
		passwordField.setFont(new Font("Century Gothic", 0, 14));
		passwordField.setForeground(new Color(255, 255, 255));
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(255,255,255)));
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == '\n')
					login();
			}
		});
		
		loginBtn.setBackground(new Color(24, 118, 242));
		loginBtn.setFont(new Font("Century Gothic", 1, 14));
		loginBtn.setForeground(new Color(255, 255, 255));
		loginBtn.setText("Login");
		loginBtn.setBorderPainted(false);
		loginBtn.setFocusPainted(false);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				login();
			}
		});
		
		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
			jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
					.addComponent(emailField)
					.addComponent(loginBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(jLabel2)
							.addComponent(jLabel3))
						.addGap(0,0,Short.MAX_VALUE))
					.addComponent(passwordField))
				.addContainerGap())
		);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGap(47,47,47)
						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addGap(49,49,49)
						.addComponent(jLabel2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(18,18,18)
						.addComponent(jLabel3)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(18,18,18)
						.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(97, Short.MAX_VALUE))
		);
		
		getContentPane().add(jPanel1, BorderLayout.CENTER);
		
		pack();
	}
	
	private void login() {
		String email = this.emailField.getText().trim();
		String password = new String(this.passwordField.getPassword()).trim();

		final String APP_EMAIL = dotenv.get("APP_EMAIL");
		final String APP_PASSWORD = dotenv.get("APP_PASSWORD");
		
		if(email.equalsIgnoreCase(APP_EMAIL) && password.equalsIgnoreCase(APP_PASSWORD)) {
			new Dashboard();
			this.setVisible(false);
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(null, "Incorrect email or password");
		}
	}
}
