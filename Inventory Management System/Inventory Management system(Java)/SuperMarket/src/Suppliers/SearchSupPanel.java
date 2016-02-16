package Suppliers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import main.GenericTableModel;

import database.DBConnectionFactory;

public class SearchSupPanel extends JPanel implements ActionListener, RowSetListener
{
	 DBConnectionFactory factory;	
	 GenericTableModel  myTableModel;
	 JTable table;
	 int offset=-1;
	 JPanel  leftWrapper , rightWrapper , jp3;
	 JScrollPane jpane;
	 JLabel  supidLabel,supphoneLabel,supcityLabel, itemNameLabel, supnameLabel; 
	 JTextField supid,supphone,supcity, itemName, search, supname; 
	 JButton searchButton, nSearch;
	 JButton  saveButton , editButton, deleteButton;
	 JPanel  detailPanel;
	 
	 public SearchSupPanel(DBConnectionFactory factory) 
		{
			 this.factory = factory;	
			 
			    // rightPanel(); 
				 leftPanel();
				 rightPanel();
				 setPreferredSize(new Dimension(760,500));
				 add(leftWrapper,BorderLayout.EAST); 
				 add(rightWrapper,BorderLayout.WEST);	
		}
	
	 public void leftPanel()
	    {
			 table = new JTable();
			 try
			 {
				 createNewTableModel();
			 }
			 catch (SQLException e)
			 {}			 		
			 
			 //leftWrapper config
			 leftWrapper= new JPanel(new BorderLayout());
			 leftWrapper.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));	
			 leftWrapper.setPreferredSize(new Dimension(300,400));	
			 
			//Jtable configuration 
			 table.addMouseListener(new MouseAdapter()
			           {
				        public void mouseClicked(MouseEvent e)
				        {
					                                         // use if necessary - if (e.getClickCount() == 2)
					      JTable target = (JTable)e.getSource();
					      int row = target.getSelectedRow();
					      int column = target.getSelectedColumn();
					      int sId= Integer.parseInt(table.getValueAt(row,0).toString());
					      displaySup(sId); //send the supplier to display
					      
					      //System.out.println(table.getValueAt(row,0));
					      // do some action if appropriate column
					    }
					});
			 jpane = new JScrollPane(table);
			 jpane.setPreferredSize(new Dimension(300,300));		
			 
			 /* Search item textbox and Button */
			  search=new JTextField(15);

					
			 jp3 = new JPanel();
			 jp3.add(search);
			  searchButton= new JButton("Search Suppliers(Sup Id)");
			  nSearch = new JButton("New Search");
			  
			 jp3.add(searchButton); jp3.add(nSearch);
			 jp3.setPreferredSize(new Dimension(200,100));
			 
			 searchButton.addActionListener(this); nSearch.addActionListener(this);
			 
			 /* leftWrapper is the outer layout */
			 leftWrapper.add(jp3,BorderLayout.NORTH);
			 leftWrapper.add(jpane, BorderLayout.SOUTH);
	    	

	    }
	 
	 public void rightPanel()
     {
   	         rightWrapper = new JPanel();
			 rightWrapper.setPreferredSize(new Dimension(350,500));
			 
			 /* displayPanel for items */
			 detailPanel = new JPanel();
			 detailPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));			 
			 detailPanel.setLayout(new GridLayout(0,2,7,12));
			 detailPanel.setPreferredSize(new Dimension(320,250));//original 500,200
			
			 // JLabel  supidLabel,supphoneLabel,supcityLabel, itemidLabel; 
			 //JTextField supid,supphone,supcity, itemid, search; 
			 supidLabel = new JLabel("Supplier ID"); supphoneLabel =  new JLabel(" Supplier Phone ");supnameLabel = new JLabel(" Supplier Name ");
			 supcityLabel = new JLabel("Supplier City ");  itemNameLabel = new JLabel(" Item Name");
			 
			 supid = new JTextField(15); supphone = new JTextField(15);  supname = new JTextField(15); 
			 supcity =  new JTextField(15); itemName = new JTextField(15);
					
			 
			 detailPanel.add(supidLabel);detailPanel.add(supid);detailPanel.add(supnameLabel);detailPanel.add(supname);
			 detailPanel.add(supphoneLabel);detailPanel.add(supphone);
			 detailPanel.add(supcityLabel);detailPanel.add(supcity);
			 detailPanel.add(itemNameLabel); detailPanel.add(itemName);
						 
			 //clearButton = new JButton("Discard Changes");//not needed right now
			 saveButton = new JButton("Save Changes");			
			 editButton = new JButton("Edit Fields");
			 deleteButton= new JButton("Delete Item");
			 
			 editButton.addActionListener(this); saveButton.addActionListener(this); deleteButton.addActionListener(this);
			 detailPanel.add(saveButton); detailPanel.add(editButton); // detailPanel.add(deleteButton);
			
			 rightWrapper.add(detailPanel,BorderLayout.NORTH);
			 
			 supid.setEditable(false);		
			 itemName.setEditable(false);
			 this.setEdit(false); //Disabling the textfiled editing while viewing the items
   	  
     }

	 void setEdit(boolean var)
	 {
		 supname.setEditable(var);
		 supphone.setEditable(var);
		 supcity.setEditable(var);
		// itemName.setEditable(var);
	 }
	 
	  void displaySup(int sid)
	  {
		  ResultSet rs = factory.getResults("Select s.sup_id,sup_name,sup_phone,sup_city,i.itemname from suppliers s, items i, item_sup_rel r  where s.sup_id ="+sid+" and i.itemid = r.itemid and s.sup_id =r.sup_id");
		
		try
		 { 
		   if(rs.next())
		   { 
			 supid.setText(rs.getString(1));
		     supname.setText( rs.getString(2));
		     supphone.setText( rs.getString(3));
		     supcity.setText( rs.getString(4));
		     itemName.setText( rs.getString(5));
		    
		    }
		  }
	      catch(Exception e)
	      {	    	  
	    	 e.printStackTrace(); 
     		 System.out.println(e);  	  
	      }
		 
	  }
	 
	 public  void createNewTableModel() throws SQLException
	 {  
	       myTableModel = new GenericTableModel(((DBConnectionFactory)factory).getSuppliers(offset));
	       myTableModel.addEventHandlersToRowSet(this);
	       table.setModel(myTableModel); 
	  }
	
	 
	 public void actionPerformed(ActionEvent e)
		{
			String s=new String(e.getActionCommand());

		   if((s).equals("New Search"))
			{
			    search.setText("");
				 offset =-1;
				 
				   try{ createNewTableModel();}
				   catch(Exception ex) 
				   {
					   System.out.println(ex);
				   } 
			}
			else if((s).equals("Search Suppliers(Sup Id)"))
			{
				offset = Integer.parseInt(search.getText());
				  
				try{
					   createNewTableModel();}
				       catch(Exception ex) {System.out.println(ex);
				   } 
			}
			else if((s).equals("Edit Fields"))
			{
				if(supid.getText().length()!=0)
				{
				 
				  setEdit(true);	
				  editButton.setEnabled(false);
				}
				
			}
			else if((s).equals("Save Changes"))
			{	
			           factory.updateSupplier(Integer.parseInt(supid.getText()),supname.getText(), supphone.getText(),supcity.getText());
				
			    try {
			    	myTableModel.itemsRowSet.acceptChanges();
			         }
			       catch (SQLException sqle)
			         {
			    	  System.out.println(sqle);   
			    	  // Now revert back changes
		              try{
		                  createNewTableModel();
		                 } 
		               catch (SQLException sqle2) {
		            	  System.out.println(sqle2);
		                 }			   
			         }
				//...final step
				setEdit(false);	
				editButton.setEnabled(true);
			    }
			else if((s).equals("Delete Supplier"))
			{	        
				
			}
		}
	 
	 public void rowSetChanged(RowSetEvent event) {  }
	  public void rowChanged(RowSetEvent event)  {}

	 public void cursorMoved(RowSetEvent event) {
		
		
	}
}
