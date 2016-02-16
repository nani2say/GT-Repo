package Suppliers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
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


public class AddSupPanel extends JPanel implements ActionListener
{
	 DBConnectionFactory factory;	 
	 JLabel sname,sphone,scity,itemId;
     JTextField name,phone,city,item;
     JButton saveButton, clearButton;
     JPanel jp1 ,jp2;
     
    public AddSupPanel(DBConnectionFactory factory)
		{
			 this.factory = factory;
			 
			 Dimension expectedDimension = new Dimension(400,200);
		        
			 Box box = new Box(BoxLayout.Y_AXIS);  //Box is used for center allignment
		     box.add(Box.createVerticalGlue());
		     box.add(Box.createVerticalGlue());
		        
			 
			 setPreferredSize(new Dimension(650,500));//for outer panel
			 jp1= new JPanel();
			 add(jp1);
			 
			 jp1.setMaximumSize(expectedDimension);
			 jp1.setPreferredSize(expectedDimension);		        
			 GridLayout grd = new GridLayout(0,2,7,12);
			 jp1.setLayout(grd); 
			 //jp1.setPreferredSize(new Dimension(400,200));
               
			 itemId = new JLabel("Item Id");
			 item = new JTextField(10);
			 
			 sname = new JLabel("Supplier Name"); 
			 sphone = new JLabel("Phone"); 
			 scity = new JLabel("City");
			 name =new JTextField(10); 
			 phone =new JTextField(10); 
			 city =new JTextField(10);
			 saveButton = new JButton("Save"); 
			 clearButton = new JButton("Clear");
			 
			 clearButton.addActionListener(this);  
			 saveButton.addActionListener(this);
				 
			 jp1.add(sname);jp1.add(name);
			 jp1.add(sphone);jp1.add(phone);
			 jp1.add(scity);jp1.add(city);
			 jp1.add(itemId); jp1.add(item);
			 jp1.add(saveButton);jp1.add(clearButton);
			 
			 add(box);
			
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String s=new String(e.getActionCommand());

			if((s).equals("Save"))
			{

				String supname = name.getText();	
				String pno = phone.getText();
				String cty = city.getText();
				int itemid = Integer.parseInt(item.getText());
				
				int supid = factory.saveSupplier(supname,pno,cty,itemid);
				
				if(supid !=-1)
				JOptionPane.showMessageDialog(null, "Supplier Record has been successfully inserted! ");
				else
					JOptionPane.showMessageDialog(null, "There was a problem inserting the record! Please check input. ");
				
				//clearing the fields
				name.setText(""); 	phone.setText("");   city.setText(""); item.setText("");
			}
			
			else if((s).equals("Clear"))
			{
			  name.setText(""); 	phone.setText("");   city.setText(""); item.setText("");			
			}

		}
		
		void clearFields()
		{
		    name.setText("");
		}
	}
