

import java.awt.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*; 


class HistoEnhance implements ActionListener
{

	BufferedImage img,img2;
	int x=0;
	int y=0;
	int c,rgb[][][] ,rgb2[][][];
	float hsi[][][];
	int iw,ih;
    int pixels[];
	PixelGrabber pg;
   float zmax,zmin , smax,smin; 
	JFrame f;
	JTextField tf,tf2; JButton button;
	 JLabel label,label1;
	Intf f2;
	

HistoEnhance(BufferedImage img3)
	{
       f2= new Intf();
	  f2.setSize(800,800);

		f2.setVisible(true);

		img=img3;
		f2.img=img;
		f2.repaint();
		 
			 f= new JFrame(" Enter M C Values ");
			 tf = new JTextField(10);
			 tf2 = new JTextField(10);
			  label= new JLabel(" Enter Zmax <0-1>");
			  label1= new JLabel(" Enter Zmin (0-1> ");

             button = new JButton(" Get Image ");
			 
              f.setLayout(new FlowLayout());

			 f.add(label); f.add(tf); f.add(label1); f.add(tf2); f.add(button);
	 		 	 
				 f.setSize(300,300);
                f.setVisible(true);

	       button.addActionListener(this);       
			
		 //setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }


	void functionHist()
	{
		 try{
			 
            iw=img.getWidth(null);
		     ih=img.getHeight(null);
		     pixels=new int[iw*ih];
			 rgb =new int[ih][iw][4];
		     pg= new PixelGrabber(img,0,0,iw,ih,pixels,0,iw);
		     pg.grabPixels();
	
            for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
					c=pixels[x*iw+y];
				
					rgb[x][y][0]=0xff & (c>>24);
			        rgb[x][y][1]=0xff & (c>>16);
	                rgb[x][y][2]=0xff & (c>>8); 
	                rgb[x][y][3]=0xff & (c);

				
			     }
            
			  }
			 

			  //to convert from rgb to hsi

       hsi=new float[ih][iw][4];
        //DecimalFormat decimalformat = new DecimalFormat("0.000");

		for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {

					float hs[] = Color.RGBtoHSB(rgb[x][y][1], rgb[x][y][2], rgb[x][y][3], null);

				   hsi[x][y][1]=hs[0];          //    decimalformat.format(hs[0]);              
				   hsi[x][y][2]=hs[1];   //decimalformat.format(hs[1]);               
				   hsi[x][y][3]=hs[2];       //decimalformat.format(hs[2]);    
				   
                  

					//System.out.println("h=="+hsi[x][y][1]+" s=="+hsi[x][y][2]+" i=="+hsi[x][y][3]);

			     }
			  }
			

			//to caliculate smax and smin

smax=hsi[0][0][2];
smin=hsi[0][0][2];
     
	 for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
				  if(smax<hsi[x][y][2])  smax=hsi[x][y][2];
				   if(smin>hsi[x][y][2])  smin=hsi[x][y][2];
			     }
			  }
 
 // convert the range of saturation from smax-smin to zmax-zmin
 float sin=0;
	 for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
                      sin= hsi[x][y][2];         
		       hsi[x][y][2]=(zmax-zmin)*(sin-smin)/(smax-smin)+zmin;

			     }
			  }
 

            //to convert from hsi to rgb again

          rgb2 =new int[ih][iw][4];

		 for(int x=0;x<ih;x++)
			 {
				  for(int y=0;y<iw;y++)
				   {
                       int rg = Color.HSBtoRGB(hsi[x][y][1], hsi[x][y][2],hsi[x][y][3]);
                       
					   int r = (rg >> 16) & 0xFF;
                       int g = (rg >> 8) & 0xFF;
                       int b = rg & 0xFF;

				      
			          rgb2[x][y][1]=r;         //red
	                  rgb2[x][y][2]=g;          //green
	                  rgb2[x][y][3]=b;            //blue

				
			       }
			  }
			   


   ////--------------------
  
   img2 =new BufferedImage(iw,ih, BufferedImage.TYPE_INT_RGB);


    for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
                   
					 c=((rgb[x][y][0] << 24)
                                   & 0xFF000000)
					 | ((rgb2[x][y][1] << 16)
                                   & 0x00FF0000)
                     | ((rgb2[x][y][2] << 8)
						           & 0x0000FF00)
					 | ((rgb2[x][y][3])
						           & 0x000000FF);
                   
                      img2.setRGB(y,x,c);
				 }
			 }

		 }    //try

		 catch(Exception e) {
		
		 e.printStackTrace();
		 }

		   f2.x=img.getWidth(null)+40;
			  f2.img2=img2;
		
	}
	
	
	
	 public void actionPerformed(ActionEvent ae) 
	 {

	 
   
     zmax=Float.parseFloat(tf.getText());

	  zmin = Float.parseFloat(tf2.getText());
  
	  f.dispose();

      functionHist();
	 
	  f2.repaint();
          
	 }
	
}  



// Imax = 1.0 when rgb= (255,0,0)