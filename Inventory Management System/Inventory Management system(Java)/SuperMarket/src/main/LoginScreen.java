package main;
import items.ItemsFrame;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;

import orders.OrdersFrame;


import Suppliers.SuppliersFrame;

import database.DBConnectionFactory;
import extraFeatures.ExtrafeaturesFrame;




public class LoginScreen implements ActionListener
{ 
	JFrame loginFrame; 
	DBConnectionFactory factory;
	
	
	JTextField username;
	JPasswordField password;
	JLabel lbUsername;
	JLabel lbPassword;
	JButton loginButton;
	JButton cancelButton;
	boolean succeeded;
	String usr;
	String psw;
  public LoginScreen(DBConnectionFactory factory)
    {
	  this.factory = factory;
	  
	   loginFrame = new JFrame("Login"); 
	   loginFrame.setSize(500, 450); 
	   loginFrame.setVisible(true);
	  
	    Container content = loginFrame.getContentPane();
	    JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints cs = new GridBagConstraints();
	    cs.fill = GridBagConstraints.HORIZONTAL;
	    
	    lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        username = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(username, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        password = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(password, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
        loginButton = new JButton("Login");    
	    loginButton.addActionListener(this);
	    
	    
	    cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        content.add(panel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);
        
	    loginFrame.addWindowListener(new ExitListener());
	    	   
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String s=new String(e.getActionCommand());

		if((s).equals("Login"))
		{
			try {
				if(factory.authenticate(username.getText(), password.getText())){
					loginFrame.dispose();
					new HomeScreen(factory);
				}
				else
				{
					JOptionPane.showMessageDialog(loginFrame, "Username or Password is incorrect.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			 
		}
		
		else if((s).equals("Cancel"))
			{
				 loginFrame.dispose();
				 new ExitListener();
			}
		
		
		
			
	}
	
	public static void main(String[] args)
	{
		
        try {  /* Use an appropriate Look and Feel */
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
           } catch (Exception ex) {
            ex.printStackTrace();
           } 
       
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        DBConnectionFactory dbfactory = new DBConnectionFactory("jdbc:mysql://localhost:3306/supermarket","nani","1234"); 
        LoginScreen hs = new LoginScreen(dbfactory);
  
	}

}
