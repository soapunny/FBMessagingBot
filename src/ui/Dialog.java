package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import enums.DialogType;
import exception.CannotSleepInThreadException;
import util.Util;

public class Dialog extends JFrame implements Runnable{
	private static final long serialVersionUID = -4343447438153878098L;

	private static final Logger logger = Logger.getLogger(Dialog.class);
	
	private JLabel messageL;
	private JPanel jPanel1;
	private JButton confirmBtn;
	
	private String title = "";
	private String msg = "";
	private DialogType type = DialogType.INFORMATIONAL_MESSAGE;
	private int remainingTime = 0;
	private boolean isOn = true;
	
	public static void main(String args[]){
		Util.getLog4j();
		
		EventQueue.invokeLater(new Runnable() {
			//dispatch thread가 곧바로 처리
			public void run() {
				logger.debug("==========Dialog Test===========");
				new Dialog("Test Dialog", "Working well", DialogType.TIMER_MESSAGE, 5).setVisible(true);
			}
		});
	}

	public Dialog(String title, String msg, DialogType type, int remainingTime) {
		this.title = title;
		this.msg = msg;
		this.type = type;
		this.remainingTime = remainingTime;
		initComponents();
	}

	private void initComponents() {
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle(title);
		setResizable(false);
		
		jPanel1 = new JPanel();
		messageL = new JLabel();
		confirmBtn = new JButton();
		
		jPanel1.setBackground(new Color(57,57,57));
		
		messageL.setFont(new Font("Century Gothic", 1, 24));
		messageL.setForeground(new Color(255, 255, 255));
		messageL.setHorizontalAlignment(SwingConstants.CENTER);
		messageL.setText(msg + "(" + remainingTime + "secs)");
		
		confirmBtn.setBackground(new Color(24, 118, 242));
		confirmBtn.setFont(new Font("Century Gothic", 1, 14));
		confirmBtn.setForeground(new Color(255, 255, 255));
		confirmBtn.setText("confirm");
		confirmBtn.setBorderPainted(false);
		confirmBtn.setFocusPainted(false);
		confirmBtn.setEnabled(false);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				confirm();
			}
		});
		
		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
			jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(messageL, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
					.addComponent(confirmBtn, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
				.addContainerGap()
		));
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGap(47,47,47)
						.addComponent(messageL, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(confirmBtn, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(47, Short.MAX_VALUE))
		);
		
		getContentPane().add(jPanel1, BorderLayout.CENTER);
		
		new Thread(this).start();
		
		pack();
	}
	
	private void confirm() {
		logger.debug("==========close===========");
		this.dispose();
	}
	
	private void countDown() {
		if(remainingTime <= 0)
			return;
		while(remainingTime-- > 0) {
			try {
				Util.easySleep(1000);
			} catch (CannotSleepInThreadException e) {
				e.printStackTrace();
			}
			messageL.setText(msg + "(" + remainingTime + "secs)");
		}
		confirmBtn.setEnabled(true);
		confirm();
	}

	@Override
	public void run() {
		countDown();
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}
}
