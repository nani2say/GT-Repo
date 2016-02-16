package extraFeatures;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import orders.ShowOrdersPanel;

import main.ExitListener;
import main.HomeScreen;

import database.DBConnectionFactory;

public class ExtrafeaturesFrame implements ActionListener
{
  DBConnectionFactory factory;
 JFrame itemsFrame;
 JButton home,addItem,editItem,searchItem;	
 JPanel cards , card1,card2,card3, reportCard;
 JPanel itemActions;
 JComboBox monthChooser;
 
 final static String Reports1 = "Reports";
 
 
  public ExtrafeaturesFrame(DBConnectionFactory factory) 
  {
	   this.factory = factory;
	   
	  itemsFrame = new JFrame("Extra Features");
	  itemsFrame.setSize(500, 450);
	  itemsFrame.setVisible(true);
	  
	  itemActions = new JPanel(new GridLayout(0,1,7,12)); 
	  itemActions.setSize(100,450);
	  home= new JButton("Home");  addItem= new JButton(Reports1);
	 
	  
	  home.addActionListener(this); addItem.addActionListener(this);
	 
	  
	  itemActions.add(home);itemActions.add(addItem);
	
	  
     
	  cards = new JPanel(new CardLayout());
      
      
      
      reportCard= new ShowReportPanel(factory);
      
      
      
     cards.add(reportCard, Reports1);
	  
	
      cards.setSize(400,450);
	  
	  Container content =  itemsFrame.getContentPane();
	  content.add(itemActions,BorderLayout.WEST);
	  content.add(cards,BorderLayout.EAST);
	  
	  
	  
	  
	  itemsFrame.addWindowListener(new ExitListener());
  }
  
  public void actionPerformed(ActionEvent e)
	{
		String s=new String(e.getActionCommand());
		
		if((s).equals("Home"))
		{	
			itemsFrame.dispose();
			new HomeScreen(factory);
			
		}
		else if((s).equals("Report"))
		{
			
			  CardLayout cl = (CardLayout)(cards.getLayout());
		       cl.show(cards, Reports1);
		          
		}
		
		
		
	}
 }
