package items;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import database.DBConnectionFactory;


public class AddItemPanel extends JPanel implements ActionListener
{
	 DBConnectionFactory factory;	 
	   JFrame f;	
	   JButton addButton;
	   JButton cancelButton;
	   JButton AddImage; 
	   JTextField name, availQty ,unitPrice, classfield, uom, reorderLevel, minQty , maxQty;
	   JLabel lname, lavailQty ,lunitPrice, lclassfield, luom, lreorderLevel, lminQty , lmaxQty;
	   String fileName;
	   JLabel fileIcon, fileLabel;
	   
	   JPanel jp1;
	   JPanel jp2;	   
	   JScrollPane scrollpane;

    public AddItemPanel(DBConnectionFactory factory)
		{
			 this.factory = factory;
			 
			 jp1 = new JPanel();
			 jp2 = new JPanel();
			 
			 fileLabel = new JLabel();
			 fileIcon =  new JLabel();
			// f = new JFrame("This is a test");
			 //f.setSize(400, 420);
			 AddImage = new JButton("Add Image");
			 
			 name=new JTextField(10); availQty =new JTextField(10); unitPrice =new JTextField(10); classfield =new JTextField(10);
			 uom =new JTextField(10); reorderLevel =new JTextField(10);  minQty =new JTextField(10); maxQty =new JTextField(10); 
			 
			 lname=new JLabel("Name "); lavailQty =new JLabel("Available Quantity"); lunitPrice =new JLabel("Unit Price"); lclassfield =new JLabel("Class");
			 luom =new JLabel("Unit Of Measurement"); lreorderLevel =new JLabel("Reorder Level");  lminQty =new JLabel("Min Qty"); lmaxQty =new JLabel("Max Qty"); 
			 
			 addButton =new JButton("Save");
			 cancelButton = new JButton("Cancel");

			 GridLayout grd = new GridLayout(0,2,7,12);
			 jp1.setLayout(grd); 
		     
			 jp1.add(lname); jp1.add(name);  jp1.add(lavailQty); jp1.add(availQty);
			 jp1.add(lunitPrice); jp1.add(unitPrice);
			 jp1.add(lclassfield); jp1.add(classfield);
			 jp1.add(luom); jp1.add(uom); jp1.add(lreorderLevel); jp1.add(reorderLevel);
			 jp1.add(lminQty);  jp1.add(minQty); jp1.add(lmaxQty); jp1.add(maxQty);
		     
			 jp1.add(AddImage); jp1.add(fileLabel);
			 jp1.add(addButton); jp1.add(cancelButton);	
		     
			 scrollpane = new JScrollPane(fileIcon);
			 scrollpane.setPreferredSize(new Dimension(400,140));
			 
			 jp2.add(scrollpane);
		    
		     addButton.addActionListener(this);
		     cancelButton.addActionListener(this);
		     AddImage.addActionListener(this);		     	     
		     
		     setPreferredSize(new Dimension(400,500)); //For the Outer ItemPAnel
		     
		     jp1.setPreferredSize(new Dimension(400,350));
		     jp2.setPreferredSize(new Dimension(400,180));
		     

		     add(jp1 , BorderLayout.NORTH);
		     add(jp2 , BorderLayout.SOUTH);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String s=new String(e.getActionCommand());

			if((s).equals("Save"))
			{
				// call a  validate() fucntion on the text fields for input formats and nulls
				
				String itemname = name.getText();
	      		int  availqty=Integer.parseInt(availQty.getText());
	      		float unitprice = Float.parseFloat(unitPrice.getText());
	      		char classf= classfield.getText().charAt(0);
	      		String um = uom.getText(); 
	      		int rlevel =Integer.parseInt(reorderLevel.getText());
	      		int minqty=Integer.parseInt(minQty.getText());
	      		int maxqty= Integer.parseInt(maxQty.getText());
			
	      		int itemid= factory.saveItem(itemname,availqty,unitprice,classf,um,rlevel,minqty,maxqty);
	      		
	      		if(fileName !=null && itemid!=-1 )
	      		 {
	      		   factory.storeImage(itemid , fileName);
	      		 }	  
	      		
	      		JOptionPane.showMessageDialog(null, "Item Record has been successfully inserted! ");
	      		clearFields();
	            
			}
			else if((s).equals("Add Image"))
			 {
				   JFileChooser chooser=new JFileChooser(new File("Desktop")); //currently pointing to My Documents
				  
				   chooser.setMultiSelectionEnabled(false);
				   chooser.setVisible(true);

				   chooser.showOpenDialog(this);

				   File file=chooser.getSelectedFile();
				   
				   if(file!=null){fileName=file.getPath();}
				   
				   if(fileName!=null)
				      {
				         fileLabel.setText("File:"+" "+fileName);
				         fileIcon.setIcon(new ImageIcon(fileName));
				      } 	
				
			  }
			else if((s).equals("Cancel"))
			{
				clearFields();
				fileIcon.setIcon(null);
			}

		}
		
		void clearFields()
		{
		    name.setText(""); availQty.setText(""); unitPrice.setText("");
			classfield.setText(""); uom.setText(""); reorderLevel.setText("");
			minQty.setText("");  maxQty.setText("");
			
		}
	}
