package ui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;
import util.EnvHelper;

public class Login extends JFrame{
	private static final long serialVersionUID = 4131194516522208537L;
	private static final Logger logger = Logger.getLogger(Login.class);

	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton loginBtn;

	public Login() {
		initComponents();
	}

	private void initEvents(){
		loginBtn.addActionListener(evt -> login());
		emailField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == '\n')
					login();
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == '\n')
					login();
			}
		});
	}

	private void initComponents() {
		UIBuilder uiBuilder = new UIBuilder();

		JLabel botTitleL = uiBuilder.getTitleJLabelField("Advance Reply Bot v2.1");
		JLabel emailL = uiBuilder.getBasicJLabel("Email");
		emailField = uiBuilder.getBasicJTextField();
		JLabel passwordL = uiBuilder.getBasicJLabel("Password");
		passwordField = uiBuilder.getBasicJPasswordField();
		loginBtn = uiBuilder.getBasicJButton("Login");
		JPanel JPanel = new JPanel();
		JPanel.setBackground(UIBuilder.CUSTOM_GREY);

		GroupLayout JPanelLayout = new GroupLayout(JPanel);
		JPanel.setLayout(JPanelLayout);
		JPanelLayout.setHorizontalGroup(
			JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(JPanelLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(botTitleL, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
					.addComponent(emailField)
					.addComponent(loginBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(JPanelLayout.createSequentialGroup()
						.addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(emailL)
							.addComponent(passwordL))
						.addGap(0,0,Short.MAX_VALUE))
					.addComponent(passwordField))
				.addContainerGap())
		);
		JPanelLayout.setVerticalGroup(
				JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(JPanelLayout.createSequentialGroup()
						.addGap(47,47,47)
						.addComponent(botTitleL, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addGap(49,49,49)
						.addComponent(emailL)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(emailField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(18,18,18)
						.addComponent(passwordL)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(18,18,18)
						.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(97, Short.MAX_VALUE))
		);

		getContentPane().add(JPanel, BorderLayout.CENTER);
		pack();

		initEvents();
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Advance Reply Bot");
		setResizable(false);
	}
	
	private void login() {
		String email = this.emailField.getText().trim();
		String password = new String(this.passwordField.getPassword()).trim();

		final String APP_EMAIL = EnvHelper.getInstance().getValue("APP_EMAIL");
		final String APP_PASSWORD = EnvHelper.getInstance().getValue("APP_PASSWORD");
		
		if(email.equalsIgnoreCase(APP_EMAIL) && password.equalsIgnoreCase(APP_PASSWORD)) {
			new Dashboard();
			this.setVisible(false);
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(null, "Incorrect email or password");
		}
	}
}
