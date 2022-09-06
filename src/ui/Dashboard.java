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
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
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
	private JButton login;
	private JButton login1;
	private JButton login2;
	private JButton login3;
	private JButton login4;
	private JButton login5;
	private JButton login6;
	private JButton login7;
	private JPasswordField password;
	private JTable productTable;
	private JLabel sleepFuncL;
	private JCheckBox sleepFunc;
	
	private Runnable task;
	private TmpValueStorage<Boolean> isBotOn;
	private boolean hasLogined;
	
	private static final Logger logger = Logger.getLogger(Dashboard.class);
	
	public static void main(String args[]) {
		Util.getLog4j();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				logger.info("==========Start App===========");
				new Dashboard().setVisible(true);
			}
		});
	}
	
	
	public Dashboard() {
		initComponents();
	}
	
	private void initComponents() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jPanel2 = new JPanel();
        email = new JTextField();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        password = new JPasswordField();
        login = new JButton();
        jLabel5 = new JLabel();
        jScrollPane1 = new JScrollPane();
        productTable = new JTable();
        login1 = new JButton();
        login2 = new JButton();
        jScrollPane2 = new JScrollPane();
        keywordTable = new JTable();
        login3 = new JButton();
        login4 = new JButton();
        jLabel6 = new JLabel();
        login5 = new JButton();
        login6 = new JButton();
        login7 = new JButton();
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

        jLabel1.setFont(new Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("Advance Reply Bot - AI Version");

        jLabel2.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Logout");
        jLabel2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new MouseAdapter() {
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
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 480, Short.MAX_VALUE)
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, BorderLayout.PAGE_START);

        jPanel2.setBackground(new Color(57, 57, 57));

        email.setBackground(new Color(57, 57, 57));
        email.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        email.setForeground(new Color(255, 255, 255));
        email.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));

        jLabel3.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new Color(255, 255, 255));
        jLabel3.setText("Facebook Email");

        jLabel4.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setText("Facebook Password");

        password.setBackground(new Color(57, 57, 57));
        password.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        password.setForeground(new Color(255, 255, 255));
        password.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));

        login.setBackground(new Color(24, 118, 242));
        login.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login.setForeground(new Color(255, 255, 255));
        login.setText("Login");
        login.setBorderPainted(false);
        login.setFocusPainted(false);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loginFacebook();
            }
        });

        jLabel5.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setForeground(new Color(255, 255, 255));
        jLabel5.setText("Tag Based Response");

        productTable.setModel(
    		new DefaultTableModel(
        		new Object[][] {},
        		new String[] {"id", "Product Name", "Email", "Question", "Response Message"}
    		){
	        	Class[] types = new Class[] {
	        			Integer.class, String.class, String.class, String.class, String.class
	        	};
	        	boolean[] canEdit = new boolean[] {
	        			false, false, false, false, false
	        	};
	        	public Class getColumnClass(int columnIndex) {
	        		return types[columnIndex];
	        	}
	        	public boolean isCellEditable(int rowIndex, int columnIndex) {
	        		return canEdit[columnIndex];
	        	}
		});
        jScrollPane1.setViewportView(productTable);
        if(productTable.getColumnModel().getColumnCount() > 0) {
        	productTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        login1.setBackground(new Color(24, 118, 242));
        login1.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login1.setForeground(new Color(255, 255, 255));
        login1.setText("Add");
        login1.setBorderPainted(false);
        login1.setFocusPainted(false);
        login1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addItemMessage(evt);
            }
        });

        login2.setBackground(new Color(24, 118, 242));
        login2.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login2.setForeground(new Color(255, 255, 255));
        login2.setText("Delete");
        login2.setBorderPainted(false);
        login2.setFocusPainted(false);
        login2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteItemMessage(evt);
            }
        });

        keywordTable.setModel(
    		new DefaultTableModel(
        		new Object[][] {},
        		new String[] {"id", "Product Name", "Keywords", "Response Message"}
        	) {
			Class[] types = new Class[] {
        			Integer.class, String.class, Object.class, Object.class
        	};
        	boolean[] canEdit = new boolean[] {
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

        login3.setBackground(new Color(24, 118, 242));
        login3.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login3.setForeground(new Color(255, 255, 255));
        login3.setText("Delete");
        login3.setBorderPainted(false);
        login3.setFocusPainted(false);
        login3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteKeywordMessage(evt);
            }
        });

        login4.setBackground(new Color(24, 118, 242));
        login4.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login4.setForeground(new Color(255, 255, 255));
        login4.setText("Add");
        login4.setBorderPainted(false);
        login4.setFocusPainted(false);
        login4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addKeywordMessage(evt);
            }
        });

        jLabel6.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setForeground(new Color(255, 255, 255));
        jLabel6.setText("Product Based Response");

        login5.setBackground(new Color(24, 118, 242));
        login5.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login5.setForeground(new Color(255, 255, 255));
        login5.setText("Start Bot");
        login5.setBorderPainted(false);
        login5.setFocusPainted(false);
        login5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startBot(evt);
            }
        });
        hasLogined = false;
        login5.setEnabled(hasLogined);

        login6.setBackground(new Color(24, 118, 242));
        login6.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login6.setForeground(new Color(255, 255, 255));
        login6.setText("Stop Bot");
        login6.setBorderPainted(false);
        login6.setFocusPainted(false);
        login6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                stopBot(evt);
            }
        });

        login7.setBackground(new Color(24, 118, 242));
        login7.setFont(new Font("Century Gothic", 1, 14)); // NOI18N
        login7.setForeground(new Color(255, 255, 255));
        login7.setText("Edit");
        login7.setBorderPainted(false);
        login7.setFocusPainted(false);
        login7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editItemMessage(evt);
            }
        });

        log.setFont(new Font("Tahoma", 0, 12)); // NOI18N
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
                                .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(95, 95, 95))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(email)
                                .addGap(5, 5, 5)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(password)
                                .addGap(14, 14, 14)
                                .addComponent(login, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(login2, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(login7, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(login1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(login5, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(login6, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(log, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(login3, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(login4, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(email, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(password, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addComponent(login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(login1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(login2, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addComponent(login7, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(login4, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(login3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(login6, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                        .addComponent(login5, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                    .addComponent(log, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, BorderLayout.CENTER);
        

        jPanel3.setBackground(new Color(57, 57, 57));
        
        isBotOnL.setFont(new Font("Century Gothic", 0, 20)); // NOI18N
        isBotOnL.setForeground(new Color(255, 200, 200));
        isBotOnL.setText("WakeUp Time(HHmm)");
        
        wakeUpTimeL.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        wakeUpTimeL.setForeground(new Color(255, 255, 255));
        wakeUpTimeL.setText("WakeUp Time(HHmm)");
        
        sleepTimeL.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        sleepTimeL.setForeground(new Color(255, 255, 255));
        sleepTimeL.setText("Sleep Time(HHmm)");

        sleepFuncL.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        sleepFuncL.setForeground(new Color(255, 255, 255));
        sleepFuncL.setText("Use Sleep Time?");

        wakeUpTimeT.setBackground(new Color(57, 57, 57));
        wakeUpTimeT.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        wakeUpTimeT.setForeground(new Color(255, 255, 255));
        wakeUpTimeT.setText("0600");
        wakeUpTimeT.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        wakeUpTimeT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					checkSleepTime(e, wakeUpTimeT);
				} catch (BadLocationException ex) {
					logger.error(ex.getMessage());
				}	
			}
		});
        
        sleepTimeT.setBackground(new Color(57, 57, 57));
        sleepTimeT.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        sleepTimeT.setForeground(new Color(255, 255, 255));
        sleepTimeT.setText("2200");
        sleepTimeT.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        sleepTimeT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					checkSleepTime(e, sleepTimeT);
				} catch (BadLocationException ex) {
					logger.error(ex.getMessage());
				}
			}
		});
        
        sleepFunc.setBackground(new Color(57, 57, 57));
        sleepFunc.setFont(new Font("Century Gothic", 0, 14)); // NOI18N
        sleepFunc.setForeground(new Color(255, 255, 255));
        sleepFunc.setSelected(true);
        sleepFunc.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        
        sleepFunc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				useSleepFunc();
			}
		});
        
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
		//Get DATA from DB for productTable, keywordTable
        try {
			DBHelper.refreshProducts((DefaultTableModel)this.productTable.getModel());
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
		
		isBotOn = new TmpValueStorage<Boolean>(false);
	}
	
	protected void checkSleepTime(KeyEvent e, JTextField timeField) throws BadLocationException {
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
			if(sleepTimeT.getText().compareTo(wakeUpTimeT.getText()) != 0)
				return true;
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
		if(hasLogined) {
			hasLogined = Automate.getInstance().logout();
			login.setEnabled(true);
			login5.setEnabled(hasLogined);
		}
	}
	//Login Event
	private void loginFacebook() {
		String email = this.email.getText().trim();
		String password = new String(this.password.getPassword()).trim();
		
		if(!email.equals("") && !password.equals("")) {
			ThreadPool.getInstance().execute(
				new Runnable() {
					public void run() {
						try {
							hasLogined = Automate.getInstance().login(email, password);
							login.setEnabled(false);
						}catch(CannotLoginException e) {
							JOptionPane.showMessageDialog(null, "Facebook login failed. Try again.");
							logger.error(e.getMessage());
							Automate.getInstance().close();
						}finally {
							login5.setEnabled(hasLogined);
						}
					}
			});
		}else {
			JOptionPane.showMessageDialog(null, "Enter email and password");
		}
	}
	//product table add Event
	private void addItemMessage(ActionEvent evt) {
		new AddItemMessage((DefaultTableModel)this.productTable.getModel());
	}
	//product table edit event
	private void editItemMessage(ActionEvent evt) {
		int numOfRows = this.productTable.getSelectedRowCount();
		if(numOfRows == 1) {
			int row = this.productTable.getSelectedRow();
			int id = (int) this.productTable.getValueAt(row, 0);
			new EditItemMessage((DefaultTableModel)this.productTable.getModel(), id);
		}else {
			JOptionPane.showMessageDialog(null, "Please select 1 row.");
		}
	}
	//product table delete Event
	private void deleteItemMessage(ActionEvent evt) {
		int numOfRows = this.productTable.getSelectedRowCount();
		
		if(numOfRows == 1) {
			int row = this.productTable.getSelectedRow();
			int id = (int)this.productTable.getValueAt(row, 0);
			String question = (String)this.productTable.getValueAt(row, 3);
			try {
				DBHelper.deleteProduct(id);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error in deleteItemMessage query");
	            logger.fatal(e.getMessage());
			}
			((DefaultTableModel)this.productTable.getModel()).removeRow(row);
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
		task = new Thread() {
			@Override
			public void run() {
				setEnableSleepFunc(false);
				isBotOn.setValue(true);
				TmpValueStorage<Boolean> isThreadOn = isBotOn;

				while(isThreadOn.getValue())
				{
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
					} finally {
						if(!isThreadOn.getValue()) return;
						Util.sleepRandom(10, 20);//10~20 mins cycle
					}
				}
			}
		};
		ThreadPool.getInstance().execute(task);
		login5.setEnabled(false);
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
				isBotOn = new TmpValueStorage<Boolean>(false);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
		}

		isBotOnL.setText("Bot Off");
		logger.info("[Bot stop]");
		login5.setEnabled(true);
	}

}
