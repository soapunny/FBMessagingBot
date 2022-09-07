package ui;

import exception.CannotLoginException;
import exception.ElementNotFoundException;
import models.Automate;
import storages.DBHelper;
import models.ThreadPool;
import org.apache.log4j.Logger;
import util.TmpValueStorage;
import util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Dashboard extends JFrame{
	private static final long serialVersionUID = -2015832150744372995L;
	
	private JTextField email;
	private JTextField wakeUpTimeT;
	private JTextField sleepTimeT;
	private JLabel botTitleL;
	private JLabel logoutL;
	private JLabel emailL;
	private JLabel passwordL;
	private JLabel keywordBasedL;
	private JLabel itemBasedL;
	private JLabel wakeUpTimeL;
	private JLabel sleepTimeL;
	private JLabel isBotOnL;
	private JLabel log;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTable keywordTable;
	private JButton loginBtn;
	private JButton addItemBtn;
	private JButton deleteItemBtn;
	private JButton deleteKeywordBtn;
	private JButton addKeywordBtn;
	private JButton startBotBtn;
	private JButton stopBotBtn;
	private JButton editItemBtn;
	private JPasswordField password;
	private JTable itemTable;
	private JLabel sleepFuncL;
	private JCheckBox sleepFunc;
	private Runnable task;
	private TmpValueStorage<Boolean> isBotOn;
	private boolean login;
	private static final Logger logger = Logger.getLogger(Dashboard.class);
	
	public Dashboard() {
		initComponents();
	}
	
	private void initComponents() {
        jPanel1 = new JPanel();
        botTitleL = new JLabel();
        logoutL = new JLabel();
        jPanel2 = new JPanel();
        email = new JTextField();
        emailL = new JLabel();
        passwordL = new JLabel();
        password = new JPasswordField();
        loginBtn = new JButton();
        keywordBasedL = new JLabel();
        jScrollPane1 = new JScrollPane();
        itemTable = new JTable();
        addItemBtn = new JButton();
        deleteItemBtn = new JButton();
        jScrollPane2 = new JScrollPane();
        keywordTable = new JTable();
        deleteKeywordBtn = new JButton();
        addKeywordBtn = new JButton();
        itemBasedL = new JLabel();
        startBotBtn = new JButton();
        stopBotBtn = new JButton();
        editItemBtn = new JButton();
        log = new JLabel();
        jPanel3 = new JPanel();
        wakeUpTimeL = new JLabel();
        sleepTimeL = new JLabel();
        isBotOnL = new JLabel();
        wakeUpTimeT = new JTextField();
        sleepTimeT = new JTextField();
        sleepFuncL = new JLabel();
        sleepFunc = new JCheckBox();
        
        Color whiteColor = new Color(255, 255, 255);
        email.setCaretColor(whiteColor);
        password.setCaretColor(whiteColor);
        wakeUpTimeT.setCaretColor(whiteColor);
        sleepTimeT.setCaretColor(whiteColor);
        
        jPanel1.setBackground(new Color(24, 118, 242));
        jPanel1.setPreferredSize(new Dimension(714, 60));

        botTitleL.setFont(new Font("Century Gothic", Font.PLAIN, 18)); // NOI18N
        botTitleL.setForeground(new Color(255, 255, 255));
        botTitleL.setText("Advance Reply Bot - AI Version");

        logoutL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        logoutL.setForeground(new Color(255, 255, 255));
        logoutL.setHorizontalAlignment(SwingConstants.CENTER);
        logoutL.setText("Logout");
        logoutL.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutL.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                logoutFacebook();
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botTitleL)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 480, Short.MAX_VALUE)
                .addComponent(logoutL, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(botTitleL, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(logoutL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, BorderLayout.PAGE_START);

        jPanel2.setBackground(new Color(57, 57, 57));

        email.setBackground(new Color(57, 57, 57));
        email.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        email.setForeground(new Color(255, 255, 255));
        email.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));

        emailL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        emailL.setForeground(new Color(255, 255, 255));
        emailL.setText("Facebook Email");

        passwordL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        passwordL.setForeground(new Color(255, 255, 255));
        passwordL.setText("Facebook Password");

        password.setBackground(new Color(57, 57, 57));
        password.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        password.setForeground(new Color(255, 255, 255));
        password.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));

        loginBtn.setBackground(new Color(24, 118, 242));
        loginBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        loginBtn.setForeground(new Color(255, 255, 255));
        loginBtn.setText("Login");
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(evt -> loginFacebook());

        keywordBasedL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        keywordBasedL.setForeground(new Color(255, 255, 255));
        keywordBasedL.setText("Tag Based Response");

        itemTable.setModel(
    		new DefaultTableModel(
        		new Object[][] {},
        		new String[] {"id", "Product Name", "Email", "Question", "Response Message"}
    		){
	        	final Class[] types = new Class[] {
	        			Integer.class, String.class, String.class, String.class, String.class
	        	};
	        	final boolean[] canEdit = new boolean[] {
	        			false, false, false, false, false
	        	};
	        	public Class getColumnClass(int columnIndex) {
	        		return types[columnIndex];
	        	}
	        	public boolean isCellEditable(int rowIndex, int columnIndex) {
	        		return canEdit[columnIndex];
	        	}
		});
        jScrollPane1.setViewportView(itemTable);
        if(itemTable.getColumnModel().getColumnCount() > 0) {
        	itemTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        addItemBtn.setBackground(new Color(24, 118, 242));
        addItemBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        addItemBtn.setForeground(new Color(255, 255, 255));
        addItemBtn.setText("Add");
        addItemBtn.setBorderPainted(false);
        addItemBtn.setFocusPainted(false);
        addItemBtn.addActionListener(this::addItemMessage);

        deleteItemBtn.setBackground(new Color(24, 118, 242));
        deleteItemBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        deleteItemBtn.setForeground(new Color(255, 255, 255));
        deleteItemBtn.setText("Delete");
        deleteItemBtn.setBorderPainted(false);
        deleteItemBtn.setFocusPainted(false);
        deleteItemBtn.addActionListener(this::deleteItemMessage);

        keywordTable.setModel(
    		new DefaultTableModel(
        		new Object[][] {},
        		new String[] {"id", "Product Name", "Keywords", "Response Message"}
        	) {
			final Class[] types = new Class[] {
        			Integer.class, String.class, Object.class, Object.class
        	};
        	final boolean[] canEdit = new boolean[] {
        			false, false, false, false
        	};
        	public Class getColumnClass(int columnIndex) {
        		return types[columnIndex];
        	}
        	public boolean isCellEditable(int rowIndex, int columnIndex) {
        		return canEdit[columnIndex];
        	}
        });
        jScrollPane2.setViewportView(keywordTable);
        if(keywordTable.getColumnModel().getColumnCount() > 0) {
        	keywordTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        deleteKeywordBtn.setBackground(new Color(24, 118, 242));
        deleteKeywordBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        deleteKeywordBtn.setForeground(new Color(255, 255, 255));
        deleteKeywordBtn.setText("Delete");
        deleteKeywordBtn.setBorderPainted(false);
        deleteKeywordBtn.setFocusPainted(false);
        deleteKeywordBtn.addActionListener(this::deleteKeywordMessage);

        addKeywordBtn.setBackground(new Color(24, 118, 242));
        addKeywordBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        addKeywordBtn.setForeground(new Color(255, 255, 255));
        addKeywordBtn.setText("Add");
        addKeywordBtn.setBorderPainted(false);
        addKeywordBtn.setFocusPainted(false);
        addKeywordBtn.addActionListener(this::addKeywordMessage);

        itemBasedL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        itemBasedL.setForeground(new Color(255, 255, 255));
        itemBasedL.setText("Product Based Response");

        startBotBtn.setBackground(new Color(24, 118, 242));
        startBotBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        startBotBtn.setForeground(new Color(255, 255, 255));
        startBotBtn.setText("Start Bot");
        startBotBtn.setBorderPainted(false);
        startBotBtn.setFocusPainted(false);
        startBotBtn.addActionListener(this::startBot);
        login = false;
        startBotBtn.setEnabled(login);

        stopBotBtn.setBackground(new Color(24, 118, 242));
        stopBotBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        stopBotBtn.setForeground(new Color(255, 255, 255));
        stopBotBtn.setText("Stop Bot");
        stopBotBtn.setBorderPainted(false);
        stopBotBtn.setFocusPainted(false);
        stopBotBtn.addActionListener(this::stopBot);

        editItemBtn.setBackground(new Color(24, 118, 242));
        editItemBtn.setFont(new Font("Century Gothic", Font.BOLD, 14)); // NOI18N
        editItemBtn.setForeground(new Color(255, 255, 255));
        editItemBtn.setText("Edit");
        editItemBtn.setBorderPainted(false);
        editItemBtn.setFocusPainted(false);
        editItemBtn.addActionListener(this::editItemMessage);

        log.setFont(new Font("Tahoma", Font.PLAIN, 12)); // NOI18N
        log.setForeground(new Color(255, 255, 255));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(emailL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(95, 95, 95))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(email)
                                .addGap(5, 5, 5)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(password)
                                .addGap(14, 14, 14)
                                .addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                            .addComponent(passwordL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(keywordBasedL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(itemBasedL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(deleteItemBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editItemBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addItemBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(startBotBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stopBotBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(log, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteKeywordBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addKeywordBtn, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailL)
                    .addComponent(passwordL))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(email, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(password, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addComponent(loginBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(itemBasedL)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteItemBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(editItemBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keywordBasedL)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addKeywordBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteKeywordBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(stopBotBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(startBotBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                    .addComponent(log, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, BorderLayout.CENTER);
        

        jPanel3.setBackground(new Color(57, 57, 57));
        
        isBotOnL.setFont(new Font("Century Gothic", Font.PLAIN, 20)); // NOI18N
        isBotOnL.setForeground(new Color(255, 200, 200));
        isBotOnL.setText("WakeUp Time(HHmm)");
        
        wakeUpTimeL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        wakeUpTimeL.setForeground(new Color(255, 255, 255));
        wakeUpTimeL.setText("WakeUp Time(HHmm)");
        
        sleepTimeL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        sleepTimeL.setForeground(new Color(255, 255, 255));
        sleepTimeL.setText("Sleep Time(HHmm)");

        sleepFuncL.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        sleepFuncL.setForeground(new Color(255, 255, 255));
        sleepFuncL.setText("Use Sleep Time?");

        wakeUpTimeT.setBackground(new Color(57, 57, 57));
        wakeUpTimeT.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        wakeUpTimeT.setForeground(new Color(255, 255, 255));
        wakeUpTimeT.setText("0600");
        wakeUpTimeT.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        wakeUpTimeT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					checkSleepTime(wakeUpTimeT);
				} catch (BadLocationException ex) {
					logger.error(ex.getMessage());
				}	
			}
		});
        
        sleepTimeT.setBackground(new Color(57, 57, 57));
        sleepTimeT.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        sleepTimeT.setForeground(new Color(255, 255, 255));
        sleepTimeT.setText("2200");
        sleepTimeT.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        sleepTimeT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					checkSleepTime(sleepTimeT);
				} catch (BadLocationException ex) {
					logger.error(ex.getMessage());
				}
			}
		});
        
        sleepFunc.setBackground(new Color(57, 57, 57));
        sleepFunc.setFont(new Font("Century Gothic", Font.PLAIN, 14)); // NOI18N
        sleepFunc.setForeground(new Color(255, 255, 255));
        sleepFunc.setSelected(true);
        sleepFunc.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        
        sleepFunc.addActionListener(e -> useSleepFunc());
        
        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(isBotOnL)
                                        .addGap(55, 55, 55)))
                                    .addGap(11, 11, 11)
                            .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(sleepFuncL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(95, 95, 95))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(sleepFunc)
                                    .addGap(5, 5, 5)))
                                .addGap(11, 11, 11)
                            .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(wakeUpTimeL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(95, 95, 95))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(wakeUpTimeT)
                                    .addGap(5, 5, 5)))
                            .addGap(11, 11, 11)
                            .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(sleepTimeT)
                                    .addGap(14, 14, 14)
                                    )
                                .addComponent(sleepTimeL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        )
                    .addContainerGap())
            );
        jPanel3Layout.setVerticalGroup(
        		jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                		.addComponent(isBotOnL)
                        .addComponent(sleepFuncL)
                        .addComponent(wakeUpTimeL)
                        .addComponent(sleepTimeL))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    		.addComponent(sleepFunc)
                            .addComponent(sleepTimeT, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addComponent(wakeUpTimeT, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        )
                    .addGap(18, 18, 18)
                    .addContainerGap())
            );
        
        getContentPane().add(jPanel3, BorderLayout.SOUTH);
        
        pack();
        
		//Refresh the DB data
		//Get DATA from DB for itemTable, keywordTable
        try {
			DBHelper.refreshProducts((DefaultTableModel)this.itemTable.getModel());
	        DBHelper.refreshKeywords((DefaultTableModel)this.keywordTable.getModel());
		} catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "Error refreshing table.");
			logger.fatal(e1.getMessage());
		}
        isBotOnL.setText("Bot Off");
        useSleepFunc();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				returnResources();
				System.exit(0);
			}
		});
		
		setVisible(true);
		
		isBotOn = new TmpValueStorage<>(false);
	}
	
	protected void checkSleepTime(JTextField timeField) throws BadLocationException {
		int length = timeField.getText().length();
		if(length <= 0) return;
		
		//only allow number between 0000~2359
		if(!("0".compareTo(timeField.getText()) <=0 &&
				timeField.getText().compareTo("2359") <= 0)) {
			timeField.setText("");
		}
		else if(length >=3 && "6".compareTo(timeField.getText(2, 1)) <= 0) {
			timeField.setText("");
		}
		//length check
		else if(length > 4) {
			timeField.setText(timeField.getText(0, length-1));
		}
		else {
			return;
		}

		JOptionPane.showMessageDialog(null, "Wrong Sleep Time or Wake Up Time(ex. 0000 ~ 2359)");
	}
	
	private boolean checkTimeField() {
		if(!hasSleepTime()) return true;
		
		int sleepTimeLen = sleepTimeT.getText().length();
		int wakeUpTimeLen = wakeUpTimeT.getText().length();
		
		if(sleepTimeLen == 4 && wakeUpTimeLen == 4) {
			return sleepTimeT.getText().compareTo(wakeUpTimeT.getText()) != 0;
		}
		
		return false;
	}

	private void useSleepFunc() {
		if(sleepFunc.isSelected()) {
			wakeUpTimeT.setEnabled(true);
			sleepTimeT.setEnabled(true);
	        wakeUpTimeT.setBackground(new Color(57, 57, 57));
	        sleepTimeT.setBackground(new Color(57, 57, 57));
            useSleepTime(true);
		}else {
			wakeUpTimeT.setEnabled(false);
			sleepTimeT.setEnabled(false);
	        wakeUpTimeT.setBackground(new Color(157, 157, 157));
	        sleepTimeT.setBackground(new Color(157, 157, 157));
            useSleepTime(false);
		}
	}

    public boolean hasSleepTime(){
        return sleepFunc.isSelected();
    }

    private void useSleepTime(boolean use){
        sleepFunc.setSelected(use);
    }
	
	private void setEnableSleepFunc(boolean isEnabled) {
		sleepFunc.setEnabled(isEnabled);
		wakeUpTimeT.setEnabled(isEnabled);
		sleepTimeT.setEnabled(isEnabled);
		if(isEnabled) {
			if(sleepFunc.isSelected()) {
		        wakeUpTimeT.setBackground(new Color(57, 57, 57));
		        sleepTimeT.setBackground(new Color(57, 57, 57));
			}
		}else {
			if(sleepFunc.isSelected()) {
		        wakeUpTimeT.setBackground(new Color(157, 157, 157));
		        sleepTimeT.setBackground(new Color(157, 157, 157));
			}
		}
	}

	private void returnResources() {
		ThreadPool.getInstance().clear();
		DBHelper.release();
		logger.info("==========Application Exit==========");
		Automate.getInstance().close();
		this.setVisible(false);
	}
	
	//Logout Event
	private void logoutFacebook() {
		if(login) {
			login = Automate.getInstance().logout();
			loginBtn.setEnabled(true);
			startBotBtn.setEnabled(login);
		}
	}
	//Login Event
	private void loginFacebook() {
		String email = this.email.getText().trim();
		String password = new String(this.password.getPassword()).trim();
		
		if(!email.equals("") && !password.equals("")) {
			ThreadPool.getInstance().execute(
                    () -> {
                        try {
                            login = Automate.getInstance().login(email, password);
                            loginBtn.setEnabled(false);
                        }catch(CannotLoginException e) {
                            JOptionPane.showMessageDialog(null, "Facebook login failed. Try again.");
                            logger.error(e.getMessage());
                            Automate.getInstance().close();
                        }finally {
                            startBotBtn.setEnabled(login);
                        }
                    });
		}else {
			JOptionPane.showMessageDialog(null, "Enter email and password");
		}
	}
	//product table add Event
	private void addItemMessage(ActionEvent evt) {
		new AddItemMessage((DefaultTableModel)this.itemTable.getModel());
	}
	//product table edit event
	private void editItemMessage(ActionEvent evt) {
		int numOfRows = this.itemTable.getSelectedRowCount();
		if(numOfRows == 1) {
			int row = this.itemTable.getSelectedRow();
			int id = (int) this.itemTable.getValueAt(row, 0);
			new EditItemMessage((DefaultTableModel)this.itemTable.getModel(), id);
		}else {
			JOptionPane.showMessageDialog(null, "Please select 1 row.");
		}
	}
	//product table delete Event
	private void deleteItemMessage(ActionEvent evt) {
		int numOfRows = this.itemTable.getSelectedRowCount();
		
		if(numOfRows == 1) {
			int row = this.itemTable.getSelectedRow();
			int id = (int)this.itemTable.getValueAt(row, 0);
			String question = (String)this.itemTable.getValueAt(row, 3);
			try {
				DBHelper.deleteProduct(id);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error in deleteItemMessage query");
	            logger.fatal(e.getMessage());
			}
			((DefaultTableModel)this.itemTable.getModel()).removeRow(row);
			logger.info("["+id+" item's \""+question+"\" message is deleted from the Item table.]");
		}else {
			JOptionPane.showMessageDialog(null, "Please select 1 row.");
		}
	}
	//keyword table add event
	private void addKeywordMessage(ActionEvent evt) {
		new AddKeywordMessage((DefaultTableModel)this.keywordTable.getModel());
	}
	//keyword table delete event
	private void deleteKeywordMessage(ActionEvent evt) {
		int numOfRows = this.keywordTable.getSelectedRowCount();
		
		if(numOfRows == 1) {
			int row = this.keywordTable.getSelectedRow();
			int id = (int)this.keywordTable.getValueAt(row, 0);
			int productId = (int)this.keywordTable.getValueAt(row, 1);
			String keyword = (String)this.keywordTable.getValueAt(row, 2);
			try {
				DBHelper.deleteKeyword(id);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error in deleting a keyword message from Keyword table.");
				logger.fatal(e.getMessage());
			}
			((DefaultTableModel)this.keywordTable.getModel()).removeRow(row);
			logger.info("["+productId+" product's \""+ keyword+"\" keyword message is deleted from the keyword table]");
		}else {
			JOptionPane.showMessageDialog(null, "Please select 1 row.");
		}
	}

	//start bot event
	private void startBot(ActionEvent evt) {
		if(!checkTimeField()) {
			JOptionPane.showMessageDialog(null, "Invalid Time Set");
			return;
		}
		task = new Thread(() -> {
            setEnableSleepFunc(false);
            isBotOn.setValue(true);
            TmpValueStorage<Boolean> isThreadOn = isBotOn;

            while(isThreadOn.getValue()) {
                try {
Automate.getInstance().browsePosts(log);
                    if(hasSleepTime()) {
                        if(Automate.getInstance().isTimeToSleep(sleepTimeT.getText().trim(), wakeUpTimeT.getText().trim())) {
                            logger.info("[Bot is sleeping]");
                            isBotOnL.setText("Bot On[Sleeping]");
                        }else {
                            isBotOnL.setText("Bot On[Working]");
                            logger.info("[Bot is working(has sleep time)]");
                            try {
                                //Automate.getInstance().likePosts(log);
                                Automate.getInstance().doMessage(log, isThreadOn);
//Automate.getInstance().watchVideo(log);
                            } catch (ElementNotFoundException e) {
                                logger.error(e.getMessage());
                            }
                            if(!isThreadOn.getValue()) return;
                        }
                    }else {
                        isBotOnL.setText("Bot On[Working]");
                        logger.info("[Bot is working(has no sleep time)]");
                        try {
                            //Automate.getInstance().likePosts(log);
                            Automate.getInstance().doMessage(log, isThreadOn);
//Automate.getInstance().watchVideo(log);
                        } catch (ElementNotFoundException e) {
                            logger.error(e.getMessage());
                        }
                        if(!isThreadOn.getValue()) return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: messaging fail");
                    logger.error(e.getMessage());
                }
                if(!isThreadOn.getValue()) return;
                Util.sleepRandom(10, 20);//10~20 mins cycle
            }
        });
		ThreadPool.getInstance().execute(task);
		startBotBtn.setEnabled(false);
		logger.info("[Bot start]");
	}

	//stop bot event
	private void stopBot(ActionEvent evt) {
		setEnableSleepFunc(true);
		try {
			if(task != null) {
				((Thread)task).interrupt();
				task = null;
				isBotOn.setValue(false);
				isBotOn = new TmpValueStorage<>(false);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}

		isBotOnL.setText("Bot Off");
		logger.info("[Bot stop]");
		startBotBtn.setEnabled(true);
	}

}
