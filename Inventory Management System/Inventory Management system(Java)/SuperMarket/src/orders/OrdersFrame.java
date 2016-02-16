package orders;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.ExitListener;
import main.HomeScreen;

import database.DBConnectionFactory;

public class OrdersFrame implements ActionListener
{
 
 DBConnectionFactory factory;
 JFrame itemsFrame;
 JButton home,showDeficit,editOrders,searchOrders;	
 JPanel cards , deficitCard,searchCard,editCard;
 JPanel itemActions;
 
 final static String deficitPANEL = "Deficit Items";
 final static String SearchPANEL = "Search Orders";
 final static String EditPANEL = "Edit Orders";
 
  public  OrdersFrame(DBConnectionFactory factory) 
  {
	  this.factory= factory;
	  itemsFrame = new JFrame("Orders Database");
	  itemsFrame.setSize(800, 550);
	  itemsFrame.setVisible(true);
	  
	  itemActions = new JPanel(new GridLayout(0,1,7,12)); 
	  itemActions.setSize(100,550);
	  home= new JButton("Home");  showDeficit= new JButton(deficitPANEL);
	  editOrders= new JButton(EditPANEL);  searchOrders= new JButton(SearchPANEL);
	  
	  home.addActionListener(this); showDeficit.addActionListener(this);
	  editOrders.addActionListener(this);searchOrders.addActionListener(this);
	  
	  itemActions.add(home);itemActions.add(showDeficit);itemActions.add(searchOrders);
	  //itemActions.add(editOrders);
	  
     
	  cards = new JPanel(new CardLayout());
     
	  deficitCard=  new DeficitPanel(factory);
      
	  searchCard= new ShowOrdersPanel(factory);
      
       
       editCard= new JPanel();

	  
	  cards.add(deficitCard, deficitPANEL);
      cards.add(searchCard, SearchPANEL);
      cards.add(editCard, EditPANEL);
      cards.setSize(700,550);
	  

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
		else if((s).equals(deficitPANEL))
		{
			//System.out.println("Hi");			
			try{
				((DeficitPanel) deficitCard).createNewTableModel();}
		    catch(Exception ex) {System.out.println(ex);}
			
			  CardLayout cl = (CardLayout)(cards.getLayout());
		       cl.show(cards, deficitPANEL);
		}
		
		else if((s).equals(SearchPANEL))
		{
			try{
				((ShowOrdersPanel) searchCard).createNewTableModel();}
		    catch(Exception ex) {System.out.println(ex);}
			
			  CardLayout cl = (CardLayout)(cards.getLayout());
		       cl.show(cards, SearchPANEL);
		}
		
		else if((s).equals(EditPANEL))
		{
			
			  CardLayout cl = (CardLayout)(cards.getLayout());
		       cl.show(cards, EditPANEL );
		}
		
	}
 }
