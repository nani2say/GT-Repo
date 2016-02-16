

import java.awt.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*; 


class SaveFile  implements ActionListener
{

	BufferedImage img,img2;
	int x=0;
	int y=0;
	int c,rgb[][][] ,rgb2[][][];
	float hsi[][][];
	int iw,ih;
    int pixels[];
	PixelGrabber pg;
	JFrame f;
	JTextField tf; JButton button;
	 JLabel label;
	 String fname;
	Intf f2;
	

SaveFile(BufferedImage img3)
	{
       f2= new Intf();
	  f2.setSize(800,800);

		f2.setVisible(true);

		img=img3;
		f2.img=img;
		f2.repaint();
		 
			 f= new JFrame(" Save File ");
			 tf = new JTextField(10);
	
			  label= new JLabel(" Enter File name (xxx.png)");
			

             button = new JButton(" Save Image ");
			 
              f.setLayout(new FlowLayout());

			 f.add(label); f.add(tf); f.add(button);
	 		 	 
				 f.setSize(300,300);
                f.setVisible(true);

	       button.addActionListener(this);       
			
		 //setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	void save()
	{
		try
		{
			File outputfile = new File(fname);
            ImageIO.write(img, "png", outputfile);
		
		}
		catch (Exception e)
		{

		}
		
	}
	
	
	
	 public void actionPerformed(ActionEvent ae) 
	 {

	 
   
      fname = tf.getText();
	 
	  f.dispose();

     save();
	  f2.repaint();
          
	 }
	
}  



// Imax = 1.0 when rgb= (255,0,0)