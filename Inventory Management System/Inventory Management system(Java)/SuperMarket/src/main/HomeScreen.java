package main;
import items.ItemsFrame;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import orders.OrdersFrame;


import Suppliers.SuppliersFrame;

import database.DBConnectionFactory;
import extraFeatures.ExtrafeaturesFrame;




public class HomeScreen implements ActionListener
{ 
	JFrame optionsFrame; 
	DBConnectionFactory factory;
	
	JButton items,supp,cust,others;
	
  public HomeScreen(DBConnectionFactory factory)
    {
	  this.factory = factory;
	  
	   optionsFrame = new JFrame("Home"); 
	   optionsFrame.setSize(500, 450); 
	   optionsFrame.setVisible(true);
	  
	    Container content = optionsFrame.getContentPane();
	    GridLayout grd = new GridLayout(0,2,7,12);
	    content.setLayout(grd);
	    items= new JButton("Items Database");
	    supp= new JButton("Suppliers Database");
	    cust = new JButton("Orders Database");
	    others = new JButton("Extra features");
	    
	    content.add(items);content.add(supp);
	    content.add(cust);content.add(others);
	    
	    items.addActionListener(this);cust.addActionListener(this);
	    supp.addActionListener(this);others.addActionListener(this);
	    
	    optionsFrame.addWindowListener(new ExitListener());
	    	   
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String s=new String(e.getActionCommand());

		if((s).equals("Items Database"))
		{
			optionsFrame.dispose();

			 new ItemsFrame(factory);
		}
		
		else if((s).equals("Suppliers Database"))
			{
				 optionsFrame.dispose();
				 new SuppliersFrame(factory);
			}
		
		else if((s).equals("Orders Database"))
		{
			 optionsFrame.dispose();
			 new OrdersFrame(factory);
		}
		
		else if((s).equals("Extra features"))
		{
			 optionsFrame.dispose();
			 new ExtrafeaturesFrame(factory);
		}
		
			
	}
	
	

}
