package orders;

import items.ItemsTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.CachedRowSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import database.DBConnectionFactory;

public class ShowOrdersPanel extends JPanel implements ActionListener , RowSetListener
{

	 DBConnectionFactory factory;	 
	 OrdersTableModel myOrdersTableModel;
	 JTable table;
	 JScrollPane jpane;
	 JPanel detailPanel, leftWrapper , rightWrapper , jp3;
	 JButton searchItems, editButton, saveButton, cancelOrderButton ;
	 JLabel lorderId , lsupId ,litemName , lqty , ldate,lstatus;
	 JTextField search, orderId,supId,itemName , qty, date, status;
	 int offset =-1; //set offset = itemid during the search
	 
	 public ShowOrdersPanel(DBConnectionFactory factory)
	 {
		 this.factory = factory;
		 rightPanel(); 
		 leftPanel();
		 
		 setPreferredSize(new Dimension(660,400));
		 add(leftWrapper,BorderLayout.WEST); 
		 add(rightWrapper,BorderLayout.EAST);	
		 
	 }
	 
	  public void createNewTableModel() throws SQLException
     {  
       myOrdersTableModel = new OrdersTableModel(((DBConnectionFactory)factory).getContentsOfOrdersTable(offset));
       myOrdersTableModel.addEventHandlersToRowSet(this);
       table.setModel(myOrdersTableModel); 
     }

	  
	 void rightPanel()
	 {

		    rightWrapper = new JPanel();
			rightWrapper.setPreferredSize(new Dimension(450,400));
					 
					 /* displayPanel for items */
					 detailPanel = new JPanel();
					 detailPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));			 
					 detailPanel.setLayout(new GridLayout(0,2,7,12));
					 detailPanel.setPreferredSize(new Dimension(400,300));//original 500,200
					 
					 lorderId = new JLabel("Order ID");  lsupId =new JLabel("Suplier ID"); litemName =new JLabel("Item ID"); 
					 lqty=new JLabel("Quantity");  ldate =new JLabel("Date"); lstatus =new JLabel("Status");
					 
					 orderId = new JTextField(10);  supId =new JTextField(10); itemName =new JTextField(10); 
					 qty=new JTextField(10); date =new JTextField(10); status =new JTextField(10);
					 
					 detailPanel.add(lorderId); detailPanel.add(orderId);detailPanel.add(lsupId);detailPanel.add(supId);detailPanel.add(litemName);detailPanel.add(itemName);					 
					 detailPanel.add(lqty);detailPanel.add(qty);detailPanel.add(ldate);detailPanel.add(date);detailPanel.add(lstatus);detailPanel.add(status);
	 
					 editButton = new JButton("Edit"); saveButton=new JButton("Save"); cancelOrderButton=new JButton("Cancel Order"); 
					 editButton.addActionListener(this);saveButton.addActionListener(this);cancelOrderButton.addActionListener(this);
					 
					 detailPanel.add(editButton); detailPanel.add(saveButton);
					 //detailPanel.add(cancelOrderButton);
			      
					 setEdit(false); //make them non-editable initially
					 
					 rightWrapper.add(detailPanel,BorderLayout.NORTH);
	 }
	 
	 void  leftPanel()
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
		 
		 table.addMouseListener(new MouseAdapter()
         {
	        public void mouseClicked(MouseEvent e)
	        {
		                                         // use if necessary - if (e.getClickCount() == 2)
		      JTable target = (JTable)e.getSource();
		      int row = target.getSelectedRow();
		      int column = target.getSelectedColumn();
		      int orderId= Integer.parseInt(table.getValueAt(row,0).toString());
		      displayOrder(orderId); //send the itemid to display
		      
		      //System.out.println(table.getValueAt(row,0));
		      // do some action if appropriate column
		    }
		});
		 
          jpane = new JScrollPane(table);
          jpane.setPreferredSize(new Dimension(300,300));	
          
         jp3 = new JPanel();
         search=new JTextField(15);
		  
 		  
 		 searchItems= new JButton("Search Order(Item Id)");
 		 searchItems.addActionListener(this);
 		 jp3.add(search);
 		 jp3.add(searchItems);
 		 jp3.setPreferredSize(new Dimension(200,100));
 		 
          leftWrapper.add(jpane, BorderLayout.SOUTH);
          leftWrapper.add(jp3, BorderLayout.NORTH);
          
	 }

	@Override
	public void cursorMoved(RowSetEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rowChanged(RowSetEvent arg0)
	{
		 CachedRowSet currentRowSet = this.myOrdersTableModel.itemsRowSet;

		    try
		    {
		      currentRowSet.moveToCurrentRow();
		       myOrdersTableModel = new OrdersTableModel(myOrdersTableModel.getItemsRowSet());
		       table.setModel(myOrdersTableModel);

		     } 
		    catch (SQLException ex)
		      {
		    	  System.out.println(ex);
		      }
		
	}

	void displayOrder(int orderid)
	{
		 ResultSet rs = factory.getResults("Select orderid, sup_id, itemname, qty, status, date from Orders Ord, Items It where orderid ="+orderid+" and Ord.itemid = It.itemid");
	     
		 try{
       	  
		  if(rs.next())
       	   {
			  orderId.setText( rs.getString(1));
			  supId.setText( rs.getString(2));
			  itemName.setText( rs.getString(3));
			  qty.setText( rs.getString(4));
			  status.setText( rs.getString(5));
			  date.setText( rs.getDate(6).toString());
			  
       	   }
		 }
       	 catch(Exception e)
    	 { 
    		 e.printStackTrace(); 
    		 System.out.println(e);
    		 
    	 }
	
	}
	@Override
	public void rowSetChanged(RowSetEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	 public void actionPerformed(ActionEvent e)
		{
			String s=new String(e.getActionCommand());

			if((s).equals("Search Order(Item Id)"))
			{
				 offset = Integer.parseInt(search.getText());
				   try{
					   createNewTableModel();}
				       catch(Exception ex) {System.out.println(ex);
				   } 
			}
			else if((s).equals("Edit"))
			{
				if(orderId.getText().length()!=0)
				{
				 setEdit(true);
				 editButton.setEnabled(false);
				}
		   		
			}
			else if((s).equals("Save"))
			{
			    int ret = factory.updateOrders(Integer.parseInt(orderId.getText()),Integer.parseInt(qty.getText()), status.getText().charAt(0));
				
				try {
			    	 myOrdersTableModel.itemsRowSet.acceptChanges();
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
				
				if(ret!=-1) JOptionPane.showMessageDialog(null, "Update the record succesfully! ");
				else JOptionPane.showMessageDialog(null, "Record could not be updated!. Please check the input. ");
				
				//...final step
				setEdit(false);	
				editButton.setEnabled(true);
			}
			else if((s).equals("Cancel Order"))
			{
				
		
			}
		
		}
	  
	 public void setEdit(boolean value)
     {
		 qty.setEditable(value);
		 status.setEditable(value);
		 
		 //These are always false
		 itemName.setEditable(false);
		 orderId.setEditable(false);
		 supId.setEditable(false);
		 itemName.setEditable(false);
		 date.setEditable(false);
     
     }
}
