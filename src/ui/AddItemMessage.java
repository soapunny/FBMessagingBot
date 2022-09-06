package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import storages.DBHelper;

public class AddItemMessage extends JFrame {
	private static final long serialVersionUID = 7380319754641703763L;
	private static Logger logger = Logger.getLogger(AddItemMessage.class);
	
	private JTextField agentName;
    private JTextField agentNumber;
    private JTextField email;
    private JTextArea message;
    private JTextField question;
    private JTextField subject;
    private JTextField title;
    private JButton jButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane;
    
    private DefaultTableModel model;

	public AddItemMessage(DefaultTableModel model) {
		this.model = model;
		
		initComponents();
		logger.info("");
	}
	
	public void initComponents() {
		jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        jScrollPane = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jButton = new javax.swing.JButton();
        email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        subject = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        agentName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        agentNumber = new javax.swing.JTextField();
        question = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(24, 118, 242));
        jPanel1.setPreferredSize(new java.awt.Dimension(437, 60));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Add Item based Message");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(198, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(57, 57, 57));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Product ID (Exact Match)*");

        title.setBackground(new java.awt.Color(57, 57, 57));
        title.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setCaretColor(Color.white);
        title.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        message.setBackground(new java.awt.Color(57, 57, 57));
        message.setColumns(20);
        message.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        message.setForeground(new java.awt.Color(255, 255, 255));
        message.setLineWrap(true);
        message.setRows(5);
        message.setCaretColor(Color.white);
        message.setText("Hello. Yes it is.\r\n" + 
        		"\r\n" + 
        		"If you want to schedule a showing, I will need your:\r\n" + 
        		"\r\n" + 
        		"name\r\n" + 
        		"phone \r\n" + 
        		"email\r\n" + 
        		"\r\n" + 
        		"and then we'll have the leasing agent reach out to you to schedule the date and time to see it.");
        message.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane.setViewportView(message);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Response Message*");

        jButton.setBackground(new java.awt.Color(24, 118, 242));
        jButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton.setForeground(new java.awt.Color(255, 255, 255));
        jButton.setText("Add");
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        email.setBackground(new java.awt.Color(57, 57, 57));
        email.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setCaretColor(Color.white);
        email.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Email Address to send mail*:");

        subject.setBackground(new java.awt.Color(57, 57, 57));
        subject.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        subject.setForeground(new java.awt.Color(255, 255, 255));
        subject.setCaretColor(Color.white);
        subject.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Subject*:");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Agent Name:");

        agentName.setBackground(new java.awt.Color(57, 57, 57));
        agentName.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        agentName.setForeground(new java.awt.Color(255, 255, 255));
        agentName.setCaretColor(Color.white);
        agentName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Agent Number:");

        agentNumber.setBackground(new java.awt.Color(57, 57, 57));
        agentNumber.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        agentNumber.setForeground(new java.awt.Color(255, 255, 255));
        agentNumber.setCaretColor(Color.white);
        agentNumber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        question.setBackground(new java.awt.Color(57, 57, 57));
        question.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        question.setForeground(new java.awt.Color(255, 255, 255));
        question.setCaretColor(Color.white);
        question.setText("available");
        question.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Question*:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane)
                    .addComponent(title)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(email)
                    .addComponent(subject)
                    .addComponent(agentName)
                    .addComponent(agentNumber)
                    .addComponent(question)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(169, 169, 169)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subject, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(question, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
        pack();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}

	protected void jButtonActionPerformed(ActionEvent evt) {
		String title = this.title.getText();
		String email = this.email.getText().trim();
		String subject = this.subject.getText();
		String agentName = this.agentName.getText();
		String agentNumber = this.agentNumber.getText();
		String question = this.question.getText();
		String message = this.message.getText();
		
		try {
			if(DBHelper.addProductMessage(title, email, subject, agentName, agentNumber, question, message)) {
				this.setVisible(false);
				this.dispose();
				DBHelper.refreshProducts(model);
				logger.info("["+title+" item's \""+question+"\" message is added]");
			}else {
	            JOptionPane.showMessageDialog(null, "Cannot add, check fields.");
			}
		}catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in addProductMessage query or refreshing");
            logger.fatal(e.getMessage());
		}
	}
}
