package items;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.sql.*;
import javax.sql.rowset.CachedRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import database.DBConnectionFactory;


public class SearchItemPanel extends JPanel implements ActionListener, RowSetListener
{
	 DBConnectionFactory factory;	
	 ItemsTableModel myItemsTableModel;
	 JTable table;
	 JScrollPane jpane;
	 JLabel  idLabel,nameLabel,availQtyLabel, unitpriceLabel, classLabel, uomLabel, reorderLabel, minQtyLabel, maxQtyLabel ;
	 JTextField itemId,name, availQty,unitprice,classfield,uom,reorder,minQty,maxQty ;
	 JPanel detailPanel, leftWrapper, jp3, rightWrapper;
     JButton  saveButton , editButton, clearButton,searchItems,deleteButton;
     //to Display image
     JScrollPane scrollpane;
     JLabel fileIcon;
     JPanel imagePanel;
     JTextField search;
     String offset ="%"; //Default offset
     int flag =0;
     
     
     public void  clearfields()
     {
    	 itemId.setText("");
		  name.setText("");
		  availQty.setText("");
		  unitprice.setText("");
		  classfield.setText("");
		  uom.setText("");
		  reorder.setText("");
		  minQty.setText("");
		  maxQty.setText("");
    	 
     }
     
    public SearchItemPanel(DBConnectionFactory factory) 
		{
			 this.factory = factory;			 
			 rightPanel(); 
			 leftPanel();
			 
			 add(leftWrapper,BorderLayout.WEST); 
			 add(rightWrapper,BorderLayout.EAST);			     
		    	   
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String s=new String(e.getActionCommand());

			if((s).equals("Edit Fields"))
			{	
				if(itemId.getText().length()!=0)
				{
				  //System.out.println("Hi"+itemId.getText().length());	
				  
				  setEdit(true);	
				  editButton.setEnabled(false);
				}
			}
			else if((s).equals("Save Changes"))
			{	
			           factory.updateItem(Integer.parseInt(itemId.getText()),name.getText(),
					   Integer.parseInt(availQty.getText()),Float.parseFloat(unitprice.getText()),
					   classfield.getText().charAt(0),uom.getText(),Integer.parseInt(reorder.getText()),
					   Integer.parseInt(minQty.getText()), Integer.parseInt(maxQty.getText()));
				
			    try {
			    	 myItemsTableModel.itemsRowSet.acceptChanges();
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
			else if((s).equals("Discard Changes"))
			{
				
			}
			
			//Delete Item
			else if((s).equals("Delete Item"))
			{
			  	int res =factory.deleteItem(Integer.parseInt(itemId.getText()));		  	
				
			  	if(res==1)
			  	{
			  	search.setText("");
				 offset ="%";
				   try{ createNewTableModel();}
				   catch(Exception ex) 
				   {
					   System.out.println(ex);
				   } 
				
				   JOptionPane.showMessageDialog(null, "Item Deleted!! ");
				   
				   setEdit(false);
				   editButton.setEnabled(true);
				   clearfields();
				   java.net.URL imgUrl = getClass().getResource("../images/noimage.jpg");        			  
     			  //System.out.println("b == NULLLL"); 
     			   fileIcon.setIcon(new ImageIcon(imgUrl));
     			  
			  	}
			  	else 
			  		JOptionPane.showMessageDialog(null, "Item could NOT be Deleted!! ");
			  }
			
			else if((s).equals("New Search"))
			{
			    search.setText("");
				 offset ="%";
				   try{ createNewTableModel();}
				   catch(Exception ex) 
				   {
					   System.out.println(ex);
				   } 
				
			}
		}
		
		public void rowSetChanged(RowSetEvent event) {  }

		  public void rowChanged(RowSetEvent event) 
		  {

		    CachedRowSet currentRowSet = this.myItemsTableModel.itemsRowSet;

		    try
		    {
		      currentRowSet.moveToCurrentRow();
		       myItemsTableModel = new ItemsTableModel(myItemsTableModel.getItemsRowSet());
		       table.setModel(myItemsTableModel);
 
		     } 
		    catch (SQLException ex)
		      {
		    	  System.out.println(ex);
		      }
		  }

		 public void cursorMoved(RowSetEvent event) {  }	
	
         public  void displayItem(int itemid) 
         {
        	 ResultSet rs = factory.getResults("Select * from Items where itemid ="+itemid);
        	 ResultSet rs2 = factory.retriveImage(itemid);
        	 try{
        	  if(rs.next())
        	   {
 
        		  this.itemId.setText( rs.getString(1));
        		  name.setText( rs.getString(2));
        		  availQty.setText( rs.getString(3));
        		  unitprice.setText( rs.getString(4));
        		  classfield.setText( rs.getString(5));
        		  uom.setText( rs.getString(6));
        		  reorder.setText( rs.getString(7));
        		  minQty.setText( rs.getString(8));
        		  maxQty.setText( rs.getString(9));
        		  
        		  //To Display Image
        		  byte b[] = null;
        		  while(rs2.next())
        		  {
        		  b= rs2.getBytes(2);
        		  }

        		  if(b!=null)
        		  {
        		  // System.out.println("b!=NULLLL");	  
        		   fileIcon.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().createImage(b)));
        		  }
        		  else
        		  {
        			 // System.out.println("Working Directory = " +
        		       //       System.getProperty("user.dir"));
        		        
        			  java.net.URL imgUrl = getClass().getResource("../images/noimage.jpg");        			  
        			  //System.out.println("b == NULLLL"); 
        			  fileIcon.setIcon(new ImageIcon(imgUrl));
        		  }
        	   }
        	 }
        	 catch(Exception e)
        	 { 
        		 e.printStackTrace(); 
        		 System.out.println(e);
        		 
        	 }
         }
         
        public void setEdit(boolean value)
        {
  		  itemId.setEditable(false); //always false, we should not edit items
		  name.setEditable(value);
		  availQty.setEditable( value);
		  unitprice.setEditable( value);
		  classfield.setEditable(value);
		  uom.setEditable( value);
		  reorder.setEditable(value);
		  minQty.setEditable( value);
		  maxQty.setEditable(value);
		  
		  saveButton.setEnabled(value);
		  deleteButton.setEnabled(value);
        	
        }
        
        private void createNewTableModel() throws SQLException
        {  
          myItemsTableModel = new ItemsTableModel(((DBConnectionFactory)factory).getContentsOfItemsTable(offset));
          myItemsTableModel.addEventHandlersToRowSet(this);
          table.setModel(myItemsTableModel); 
        }
   
      public void rightPanel()
      {
    	     rightWrapper = new JPanel();
			 rightWrapper.setPreferredSize(new Dimension(500,500));
			 
			 /* displayPanel for items */
			 detailPanel = new JPanel();
			 detailPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));			 
			 detailPanel.setLayout(new GridLayout(0,4,7,12));
			 detailPanel.setPreferredSize(new Dimension(500,250));//original 500,200
			 
			 idLabel = new JLabel("Item ID");nameLabel = new JLabel("Item Name");availQtyLabel = new JLabel("Available Qty");
			 unitpriceLabel = new JLabel("Unit Price"); classLabel = new JLabel("Class");uomLabel = new JLabel("UOM");
			 reorderLabel= new JLabel("ReOrder Level");minQtyLabel = new JLabel("Min Qty"); maxQtyLabel = new JLabel("Max Qty");
			 
			 itemId = new JTextField(15);name= new JTextField(15);availQty= new JTextField(15);
			 unitprice= new JTextField(15);classfield= new JTextField(15);uom= new JTextField(15);
			 reorder= new JTextField(15);minQty= new JTextField(15);maxQty= new JTextField(15);
					
			 
			 detailPanel.add(idLabel); detailPanel.add(itemId);  detailPanel.add(nameLabel); detailPanel.add(name);
			 detailPanel.add(availQtyLabel);  detailPanel.add(availQty); detailPanel.add(unitpriceLabel); detailPanel.add(unitprice);
			 detailPanel.add(classLabel);  detailPanel.add(classfield); detailPanel.add(uomLabel);detailPanel.add(uom);
			 detailPanel.add(reorderLabel);  detailPanel.add(reorder); detailPanel.add(minQtyLabel);detailPanel.add(minQty);
			 detailPanel.add(maxQtyLabel);detailPanel.add(maxQty);
			 
			 //clearButton = new JButton("Discard Changes");//not needed right now
			 saveButton = new JButton("Save Changes");			
			 editButton = new JButton("Edit Fields");
			 deleteButton= new JButton("Delete Item");
			 
			 editButton.addActionListener(this); saveButton.addActionListener(this); deleteButton.addActionListener(this);
			 detailPanel.add(saveButton); detailPanel.add(editButton);  detailPanel.add(deleteButton);
			
			 rightWrapper.add(detailPanel,BorderLayout.NORTH);
			 
			 //This is for Image display
			 imagePanel = new JPanel();
			 fileIcon =  new JLabel();
			 scrollpane = new JScrollPane(fileIcon);
			 scrollpane.setPreferredSize(new Dimension(300,200));
			 imagePanel.add(scrollpane);
			 rightWrapper.add(imagePanel,BorderLayout.SOUTH);
			 		 
			 this.setEdit(false); //Disabling the textfiled editing while viewing the items
    	  
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
		 leftWrapper.setPreferredSize(new Dimension(200,400));	
		 
		//Jtable configuration 
		 table.addMouseListener(new MouseAdapter()
		           {
			        public void mouseClicked(MouseEvent e)
			        {
				                                         // use if necessary - if (e.getClickCount() == 2)
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				      int itId= Integer.parseInt(table.getValueAt(row,0).toString());
				      displayItem(itId); //send the itemid to display
				      
				      //System.out.println(table.getValueAt(row,0));
				      // do some action if appropriate column
				    }
				});
		 jpane = new JScrollPane(table);
		 jpane.setPreferredSize(new Dimension(300,300));		
		 
		 /* Search item textbox and Button */
		  search=new JTextField(15);
		  search.getDocument().addDocumentListener(new DocumentListener() //Currently this is not being used
		  {
			  public void insertUpdate(DocumentEvent arg0) //imp method
	            {
				 // System.out.println("insert Update");
				  if(flag==0)
				  {	  
				   offset = search.getText();
				   try{ createNewTableModel();}
				   catch(Exception e) {System.out.println(e);} 
				  }
				 
				  flag = (flag+1)%2; //This was getting called twice because of Autosuggest package
	            }

			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			//	 System.out.println("hi from change up");				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) 
			    {
				 
				  offset = search.getText();
				//   try{ createNewTableModel();}
				 //  catch(Exception e) {System.out.println(e);} 
				//  System.out.println("hi from remove up");
				
		     	}				 
			});
		  
  		     //For search field - AUTOCOMPLETE
			    ArrayList<String> suggestions;
			    suggestions= factory.fillItemSuggestions();	           	 
				AutoCompleteDecorator.decorate(search,suggestions, false);
				
		 jp3 = new JPanel();
		 jp3.add(search);
		  searchItems= new JButton("New Search");
		 jp3.add(searchItems);
		 jp3.setPreferredSize(new Dimension(200,100));
		 
		 searchItems.addActionListener(this);
		 
		 /* leftWrapper is the outer layout */
		 leftWrapper.add(jp3,BorderLayout.NORTH);
		 leftWrapper.add(jpane, BorderLayout.SOUTH);
    	
		/* Debug stuff******
		 offset="i";
		 ResultSet rs = factory.getResults("select itemID, itemname ,unitprice from items where itemname LIKE '"+offset+"%'");
		try
		{
		 while(rs.next())
		  {
			 System.out.println(rs.getString(1)); 
			 
		  }
		 }
		catch(Exception e){}
		
		/************/
    }
      
}


