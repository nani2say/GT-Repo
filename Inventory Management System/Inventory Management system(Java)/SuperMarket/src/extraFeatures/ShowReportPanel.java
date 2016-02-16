package extraFeatures;

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

import java.util.Calendar;
import java.util.Date; 
import database.DBConnectionFactory;

public class ShowReportPanel extends JPanel implements ActionListener , RowSetListener
{

	 DBConnectionFactory factory;	 
	 ReportTableModel reportTableModel;
	 JTable table;
	 JScrollPane jpane;
	 JPanel detailPanel, leftWrapper , rightWrapper , jp3;
	 JButton searchItems, editButton, saveButton, cancelOrderButton, reportButton;
	 JComboBox monthChooser, yearChooser;
	 JLabel lorderId , lsupId ,litemName , lqty , ldate,lstatus;
	 JTextField search, orderId,supId,itemName , qty, date, status;
	 int monthIndex =1; //set offset = itemid during the search
	 String yearIndex="2013";
	 public ShowReportPanel(DBConnectionFactory factory)
	 {
		 this.factory = factory;
		 reportPanel();
		 
		 setPreferredSize(new Dimension(400,400));
		 add(leftWrapper,BorderLayout.CENTER); 
		// add(rightWrapper,BorderLayout.EAST);			 
	 }
	 
	  public void createNewTableModel() throws SQLException
     {  
       reportTableModel = new ReportTableModel(((DBConnectionFactory)factory).getContentsOfLogTable(monthIndex, yearIndex));
       reportTableModel.addEventHandlersToRowSet(this);
       table.setModel(reportTableModel); 
     }

	 void  reportPanel()
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
		 leftWrapper.setPreferredSize(new Dimension(350,400));	
		 
		 jpane = new JScrollPane(table);
         jpane.setPreferredSize(new Dimension(200,350));	
          
         jp3 = new JPanel();
         		  
         String[] monthes = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov", "Dec"};
         monthChooser = new JComboBox(monthes);
         yearChooser = new JComboBox();
         int year = Calendar.getInstance().get(Calendar.YEAR);
         for(int i=year; i>year-10; i--)
         {
        	 yearChooser.addItem(i);
         }
         jp3.add(monthChooser);
         jp3.add(yearChooser);
         reportButton = new JButton("Generate Report");
         reportButton.addActionListener(this);
 		 jp3.add(reportButton);
 		 jp3.setPreferredSize(new Dimension(100,50));
 		 
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
/*		 CachedRowSet currentRowSet = this.myOrdersTableModel.itemsRowSet;

		    try
		    {
		      currentRowSet.moveToCurrentRow();
		       myOrdersTableModel = new OrdersTableModel(myOrdersTableModel.getItemsRowSet());
		       table.setModel(myOrdersTableModel);

		     } 
		    catch (SQLException ex)
		      {
		    	  System.out.println(ex);
		      }*/
		
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
			
			if((s).equals("Generate Report"))
			{
				monthIndex = monthChooser.getSelectedIndex()+1;
				yearIndex = yearChooser.getSelectedItem().toString();
				
				  try{
					   createNewTableModel();}
				       catch(Exception ex) {System.out.println(ex);
				   } 
			}
			/*else if((s).equals("Edit"))
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
				
		
			}*/
		
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
