package Suppliers;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.ExitListener;
import main.HomeScreen;

import database.DBConnectionFactory;

public class SuppliersFrame implements ActionListener
{
	
 DBConnectionFactory factory;	
 
 JFrame supFrame;
 JButton home,addItem,editItem,searchItem;	
 JPanel cards ;
 JPanel  addCard,searchCard,  editCard;
 //SearchItemPanel searchCard ;
 //EditItemPanel editCard;
 JPanel supActions;
 final static String AddPANEL = "Add Suppliers";
 final static String SearchPANEL = "Search Suppliers";
 final static String EditPANEL = "Edit Suppliers";
 
  public SuppliersFrame(DBConnectionFactory factory) 
  {  
	  this.factory = factory;
	  supFrame = new JFrame("Items Database");
	  supFrame.setSize(850, 600);
	  supFrame.setVisible(true);
	  
	  supActions = new JPanel(new GridLayout(0,1,7,12)); 
	  supActions.setSize(100,600);
	  home= new JButton("Home");  addItem= new JButton(AddPANEL);
	  editItem= new JButton(EditPANEL);  searchItem= new JButton(SearchPANEL);
	  
	  home.addActionListener(this); addItem.addActionListener(this);
	  editItem.addActionListener(this);searchItem.addActionListener(this);
	  
	  supActions.add(home);supActions.add(addItem);supActions.add(searchItem);
	  //supActions.add(editItem);
     
	  cards = new JPanel(new CardLayout());
      
      addCard = new AddSupPanel(factory);
      FlowLayout flowLayout = (FlowLayout) addCard.getLayout();
      
      searchCard= new SearchSupPanel(factory);
       
      editCard= new JPanel();
      editCard.add(new JButton("Button 7"));
      editCard.add(new JButton("Button 8"));
      editCard.add(new JButton("Button 9"));
	  
	  cards.add(addCard, AddPANEL);
      cards.add(searchCard, SearchPANEL);
      cards.add(editCard, EditPANEL);
      cards.setSize(750,600);
	  

	  Container content =  supFrame.getContentPane();
 
	  content.add(supActions,BorderLayout.WEST);
	  content.add(cards,BorderLayout.CENTER);
	  
	  	  	  
	  supFrame.addWindowListener(new ExitListener());
  }
  
  public void actionPerformed(ActionEvent e)
	{
		String s=new String(e.getActionCommand());

		if((s).equals("Home"))
		{	
			supFrame.dispose();
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
