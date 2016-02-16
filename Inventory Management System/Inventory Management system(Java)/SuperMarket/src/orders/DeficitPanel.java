package orders;

import items.ItemsTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

import main.GenericTableModel;

import database.DBConnectionFactory;

public class DeficitPanel  extends JPanel implements ActionListener , RowSetListener
{
	 DBConnectionFactory factory;
	 GenericTableModel  myTableModel;
	 JTable table;
	 JLabel litemName, litemId , lreqQty, lsupName;	 
	 JTextField itemName, itemId , reqQty;	
	 JTextField search;
	 JButton searchItems, newSearch, placeOrder;
	 JScrollPane jpane;
	 JPanel neworderPanel, leftWrapper , rightWrapper , jp3;
	 JComboBox supChooser;
	 int offset=-1;
	 Map<String , Integer> sup;
	 
	public  DeficitPanel (DBConnectionFactory factory)
	{
	   this.factory =factory; 
	  
	     rightPanel(); 
		 leftPanel();
		 
		 setPreferredSize(new Dimension(660,500));
		 add(leftWrapper,BorderLayout.NORTH); 
		 add(rightWrapper,BorderLayout.SOUTH);	
		
	}
	
	public  void createNewTableModel() throws SQLException
	 {  
	       myTableModel = new GenericTableModel(((DBConnectionFactory)factory).getDeficitItems(offset));
	       myTableModel.addEventHandlersToRowSet(this);
	       table.setModel(myTableModel); 
	  }
	
	void rightPanel()
	{
		   rightWrapper = new JPanel();
		   rightWrapper.setPreferredSize(new Dimension(450,200));
		   
		   neworderPanel = new JPanel();
		   neworderPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));			 
		   neworderPanel.setLayout(new GridLayout(0,2,7,12));
		   neworderPanel.setPreferredSize(new Dimension(400,150));//original 500,200
		   
		   litemName = new JLabel("Item Name"); litemId=new JLabel("Item ID");  lreqQty =new JLabel("Req Qty");
		   lsupName = new JLabel("Select Supplier");
		   itemName = new JTextField(10);itemId = new JTextField(10); reqQty =new JTextField(10);
		   
		   placeOrder = new JButton("Place Order");
		   placeOrder.addActionListener(this);
		   
		   sup =  new HashMap<String,Integer>();
				  
		   supChooser = new JComboBox(sup.keySet().toArray());
		   
		   neworderPanel.add(litemId); neworderPanel.add(itemId);neworderPanel.add(litemName); neworderPanel.add(itemName);
		   neworderPanel.add(lreqQty);neworderPanel.add(reqQty);
		   
		   neworderPanel.add(lsupName);neworderPanel.add(supChooser);
		   neworderPanel.add(placeOrder);
		   
		   rightWrapper.add(neworderPanel,BorderLayout.NORTH);
		   
	}
	
	void leftPanel()
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
		 leftWrapper.setPreferredSize(new Dimension(300,300));	
		 
		 table.addMouseListener(new MouseAdapter()
         {
	        public void mouseClicked(MouseEvent e)
	        {
		                                         // use if necessary - if (e.getClickCount() == 2)
		      JTable target = (JTable)e.getSource();
		      int row = target.getSelectedRow();
		      int column = target.getSelectedColumn();
		      int itemid= Integer.parseInt(table.getValueAt(row,0).toString());
		      String name  = table.getValueAt(row,1).toString();
		      int availQty= Integer.parseInt(table.getValueAt(row,2).toString());
		      int minQty = Integer.parseInt(table.getValueAt(row,3).toString());
		      int req = minQty - availQty;
		      display(itemid,name,req); //send the itemid to display
		      
		      //System.out.println(table.getValueAt(row,0));
		      // do some action if appropriate column
		    }
		});
		 
          jpane = new JScrollPane(table);
          jpane.setPreferredSize(new Dimension(300,200));	
          
         jp3 = new JPanel();
         search=new JTextField(15);
		  
 		  
 		 searchItems= new JButton("Search Item");
 		 searchItems.addActionListener(this);
 		 newSearch = new JButton("New Search");
 		 newSearch.addActionListener(this);
 		 
 		 jp3.add(search);
 		 jp3.add(searchItems);
 		 jp3.add(newSearch);
 		 
 		 jp3.setPreferredSize(new Dimension(300,100));
 		 
          leftWrapper.add(jpane, BorderLayout.SOUTH);
          leftWrapper.add(jp3, BorderLayout.NORTH);
		
	}
	
	void display(int itemid, String name, int req)
	{
		
		sup = factory.fillSupplier(itemid);
		supChooser.removeAllItems();
		
		//adding new items to JCombo box
		for(String var:sup.keySet())
	   	   supChooser.addItem(var);		
		 	
		itemId.setText(itemid+"");
		itemName.setText(name);
		reqQty.setText(req+"");

	}
	
	@Override
	public void cursorMoved(RowSetEvent arg0)
	{		
	}
	public void rowSetChanged(RowSetEvent arg0)
	{		
	}

	@Override
	public void rowChanged(RowSetEvent arg0)
	{
		 CachedRowSet currentRowSet = this.myTableModel.itemsRowSet;

		    try
		    {
		      currentRowSet.moveToCurrentRow();
		       myTableModel = new GenericTableModel(myTableModel.getItemsRowSet());
		       table.setModel(myTableModel);

		     } 
		    catch (SQLException ex)
		      {
		    	  System.out.println(ex);
		      }
		
	}
	
	 public void actionPerformed(ActionEvent e)
		{
			String s=new String(e.getActionCommand());

			if((s).equals("Search Item"))
			{

				 offset = Integer.parseInt(search.getText());
				   try{
					   createNewTableModel();}
				       catch(Exception ex) {System.out.println(ex);
				   } 
			}
			else if((s).equals("New Search"))
			{
				search.setText("");
				
				 offset =-1;
				   try{
					   createNewTableModel();}
				       catch(Exception ex) {System.out.println(ex);
				   } 
			}
			else if((s).equals("Place Order"))
			  {
				String supname = supChooser.getSelectedItem().toString();
				int supid = sup.get(supname);
				int orderid = factory.placeOrder(Integer.parseInt(itemId.getText()),supid,Integer.parseInt(reqQty.getText()));
				
				if(orderid !=-1)
					JOptionPane.showMessageDialog(null, "New Order has been placed successully! ");
				else
						JOptionPane.showMessageDialog(null, "There was a problem inserting the record! Please check input. ");
			  
				 try{
					   createNewTableModel();}
				       catch(Exception ex) {System.out.println(ex);}
				
			  }
		}
	  
}
