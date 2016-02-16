package items;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.ExitListener;
import main.HomeScreen;

import database.DBConnectionFactory;

public class ItemsFrame implements ActionListener
{
	
 DBConnectionFactory factory;	
 
 JFrame itemsFrame;
 JButton home,addItem,editItem,searchItem;	
 JPanel cards ;
 AddItemPanel addCard;
// SearchItemPanel searchCard;
 JPanel  searchCard,  editCard;
 //SearchItemPanel searchCard ;
 //EditItemPanel editCard;
 JPanel itemActions;
 final static String AddPANEL = "Add Items";
 final static String SearchPANEL = "Search Items";
 final static String EditPANEL = "Edit Items";
 
  public ItemsFrame(DBConnectionFactory factory) 
  {  
	  this.factory = factory;
	  itemsFrame = new JFrame("Items Database");
	  itemsFrame.setSize(850, 600);
	  itemsFrame.setVisible(true);
	  
	  itemActions = new JPanel(new GridLayout(0,1,7,12)); 
	  itemActions.setSize(100,600);
	  home= new JButton("Home");  addItem= new JButton(AddPANEL);
	  editItem= new JButton(EditPANEL);  searchItem= new JButton(SearchPANEL);
	  
	  home.addActionListener(this); addItem.addActionListener(this);
	  editItem.addActionListener(this);searchItem.addActionListener(this);
	  
	  itemActions.add(home);itemActions.add(addItem);itemActions.add(searchItem);
	  //itemActions.add(editItem); 
     
	  cards = new JPanel(new CardLayout());
      
      addCard = new AddItemPanel(factory);
      
      searchCard= new SearchItemPanel(factory);
      
     // searchCard= new JPanel();
    //  searchCard.add(new JButton("Button 6"));
      
      
      editCard= new JPanel();
      editCard.add(new JButton("Button 7"));
      editCard.add(new JButton("Button 8"));
      editCard.add(new JButton("Button 9"));
	  
	  cards.add(addCard, AddPANEL);
      cards.add(searchCard, SearchPANEL);
     // cards.add(editCard, EditPANEL);
      cards.setSize(750,600);
	  

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
		else if((s).equals(AddPANEL))
		{			
			  CardLayout cl = (CardLayout)(cards.getLayout());
		      cl.show(cards, AddPANEL);
		}
		
		else if((s).equals(SearchPANEL))
		{
			
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
