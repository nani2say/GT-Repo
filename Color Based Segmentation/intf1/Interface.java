
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

//import javax.swing.*;

class Intf extends Frame implements ActionListener
{

	BufferedImage img,img2;
	int x=0;
	int y=0;
	int c,rgb[][][] ,rgb2[][][];
	float hsi[][][];
	int iw,ih;
    int pixels[];
	PixelGrabber pg;

	MenuBar mb; 
	Menu m1;
	Menu m2,m3,m4,m5,m6;
	MenuItem open,save,exit,Ineg,Sneg,m23,colregion,pickcolor;
	MenuItem ill_H,ill_HS,ill_HSV, StLineEnhance, HistEnhance, obj;
	
	JFileChooser fc;
	
	

	Intf()
	{
		img2=null;
		fc = new JFileChooser();

		 mb =new MenuBar();
		 setMenuBar(mb);
		 m1 = new Menu("File");
		 m2 = new Menu("Negative Transforms");
		  m3 = new Menu("Colored Regions");
		  m4= new Menu("Illumination With a Source");
          m5=new Menu("Image EnhanceMent");
         m6=new Menu(" Recognize Colored Objects ");

		  
			mb.add(m1);	mb.add(m2);mb.add(m3);	mb.add(m4);mb.add(m5);	mb.add(m6);

         open= new MenuItem("Open");
        	 save= new MenuItem("Save output image");
        	 exit= new MenuItem("Exit");
			m1.add(open);m1.add(save);m1.add(exit);
           
		     Ineg= new MenuItem("Negative of Intensity");
        	 Sneg= new MenuItem("Negative of Saturation");
         
			 
            m2.add(Ineg);m2.add(Sneg);	
			
           pickcolor= new MenuItem("Pick a color");
		   colregion=  new MenuItem("Get Colored Regions Only");

		   m3.add(pickcolor); m3.add(colregion);

             ill_H= new MenuItem("Illumination With Given Hue");
        	 ill_HS= new MenuItem("Illumination with Given Hue & Saturation");
        	 ill_HSV= new MenuItem("Illumination With give H ,S and Intensity");

			m4.add(ill_H); m4.add(ill_HS);    m4.add(ill_HSV);
         
			StLineEnhance= new MenuItem("EnhanceMent Based on S=Ms+C");
		    HistEnhance=  new MenuItem("Histogram EnhanceMent");
            m5.add(StLineEnhance); m5.add(HistEnhance);

		obj= new MenuItem("Recognize colored Objects ");
		m6.add(obj);
			

			
			open.addActionListener(this);
			save.addActionListener(this);
			exit.addActionListener(this);

			
			Ineg.addActionListener(this);
			Sneg.addActionListener(this);

			pickcolor.addActionListener(this);
			colregion.addActionListener(this);

			ill_H.addActionListener(this);
			ill_HS.addActionListener(this);
			ill_HSV.addActionListener(this);

 
          StLineEnhance.addActionListener(this);
			HistEnhance.addActionListener(this);

			 obj.addActionListener(this);



		//  setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public void paint(Graphics gr)
	{
		gr.drawImage(img,40,40,null);
		gr.drawImage(img2,x,40,null);
		
	}

	public void actionPerformed(ActionEvent ae)
	{
		
		if(ae.getSource()==open)
		   {  
			 int returnVal = fc.showOpenDialog(null);
			 if(returnVal == JFileChooser.APPROVE_OPTION) 
				 {
          System.out.println("You chose to open this file: " +fc.getSelectedFile().getName());
				 
				  File file=fc.getSelectedFile();
				 
				   try
				   {
					     img = ImageIO.read(file); 
				   }
				   catch (Exception e)
				   {
				   }
				 

		   img2=null;		
			repaint();
         
			System.out.println("Open option clicked");
				 }

	     }
		
		if(ae.getSource()==save)
		   {  
			SaveFile sf= new SaveFile(img2);
             dispose();

		   }

		   if(ae.getSource()==exit)
		   {  
			   dispose();
		   }


		 if(ae.getSource()==Ineg)
			   {  
			     
				 IntensityNeg  in= new IntensityNeg();
				  img2=in.applyInegative(img);
	
				   x=img.getWidth(null)+40;
			    repaint();
			   }

			if(ae.getSource()==Sneg)
			   {  
			     
				 SatNegative sn= new  SatNegative();
				  img2=sn.applySnegative(img);
	
				   x=img.getWidth(null)+40;
			    repaint();
			   }
            

		if(ae.getSource()==pickcolor)
		   {  
			ExtractColor ec= new ExtractColor(img);
			dispose();
		   }
		    
			if(ae.getSource()==colregion)
		   {  
                ColRegion cr= new  ColRegion();
				  img2=cr.colReg(img);
	
				   x=img.getWidth(null)+40;
			    repaint();

		   }


		    if(ae.getSource()==ill_H)
		   {  
				IlluminH ih= new IlluminH(img);
				dispose();

		   }

		    if(ae.getSource()==ill_HS)
		   {  
				IlluminHS ih= new IlluminHS(img);
				dispose();
		   }

		    if(ae.getSource()==ill_HSV)
		   {  
				IlluminHSV ih= new IlluminHSV(img);
				dispose();
		   }
          
		   if(ae.getSource()==StLineEnhance)
		   {  
			   EnhancMC em= new EnhancMC(img);
			   dispose();
		   }

		   if(ae.getSource()==HistEnhance)
		   {  
			   HistoEnhance he= new HistoEnhance(img);
			   dispose();
		   }


         if(ae.getSource()==obj)
		   {  
             ObjectRec  ob= new ObjectRec();
				  img2=ob.applyRecognition(img);

				   x=img.getWidth(null)+40;
			    repaint();
		   }

	   }


	
}  


public class Interface
{
	public static void main(String[] args) 
	{

		Intf f= new Intf();
		f.setSize(800,800);

		f.setVisible(true);
	}
}
