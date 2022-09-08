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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storages.DBHelper;
import util.EnvHelper;

public class AddKeywordMessage extends JFrame{
	private static final long serialVersionUID = 6505714560201573858L;
	private static Logger logger = LogManager.getLogger(AddKeywordMessage.class);
	
	private JButton jButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane;
    private JTextField keyword;
    private JTextArea message;
    private JTextField productId;
    
	private DefaultTableModel model;
	
	public AddKeywordMessage(DefaultTableModel model) {
		this.model = model;
		initComponents();
	}
	
	private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        productId = new javax.swing.JTextField();
        jScrollPane = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jButton = new javax.swing.JButton();
        keyword = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(24, 118, 242));
        jPanel1.setPreferredSize(new java.awt.Dimension(437, 60));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Add Message");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(259, Short.MAX_VALUE))
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
        jLabel2.setText("Product ID (Exact Match)");

        productId.setBackground(new java.awt.Color(57, 57, 57));
        productId.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        productId.setForeground(new java.awt.Color(255, 255, 255));
        productId.setCaretColor(Color.white);
        productId.setText(EnvHelper.getInstance().getValue("FOR_ALL_PRODUCTS"));
        productId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

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
        jLabel4.setText("Response Message");

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

        keyword.setBackground(new java.awt.Color(57, 57, 57));
        keyword.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        keyword.setForeground(new java.awt.Color(255, 255, 255));
        keyword.setCaretColor(Color.white);
        keyword.setText("available");
        keyword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Keyword");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(169, 169, 169))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(keyword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                            .addComponent(productId, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
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
		String keyword = this.keyword.getText();
		String productId = this.productId.getText();
		String andKey = "", orKey = "";
		String message = this.message.getText();
		
		if(productId.contentEquals("") || keyword.contentEquals("") || message.contentEquals("")) {
			JOptionPane.showMessageDialog(null, "Empty fields");
			return;
		}
		
		if(keyword.contains("&")) {
			String tmp[] = keyword.split("&");
			keyword = tmp[0];
			andKey = tmp[1];
			orKey = "this_text_is_not_possible";
			
		}else if(keyword.contains("|")) {
			String tmp[] = keyword.split("\\|");
			keyword = tmp[0];
			orKey = tmp[1];
			andKey = "this_text_is_not_possible";
		}
		
		try {
			if(DBHelper.addKeywordMessage(productId, keyword, andKey, orKey, message)) {
				this.setVisible(false);
				this.dispose();
				DBHelper.refreshKeywords(model);
				logger.info("["+productId+" product's \""+ keyword+"\" keyword message is added in the keyword table]");
			}else {
	            JOptionPane.showMessageDialog(null, "Cannot add, check fields.");
			}
		}catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in addkeywordMessage query");
			logger.fatal(e.getMessage());
		}
	}

}
