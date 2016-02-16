

import java.awt.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*; 


class IlluminHS implements ActionListener
{

	BufferedImage img,img2;
	int x=0;
	int y=0;
	int c,rgb[][][] ,rgb2[][][];
	float hsi[][][];
	int iw,ih;
    int pixels[];
	PixelGrabber pg;
    float hue,sat;
	JFrame f;
	JTextField tf,tf2; JButton button;
	 JLabel label,label1;
	Intf f2;
	

	IlluminHS(BufferedImage img3)
	{
       f2= new Intf();
	  f2.setSize(800,800);

		f2.setVisible(true);

		img=img3;
		f2.img=img;
		f2.repaint();
		 
			 f= new JFrame(" Enter Hue ");
			 tf = new JTextField(10);
			 tf2 = new JTextField(10);
			  label= new JLabel(" Enter Hue <0-360>");
			  label1= new JLabel(" Enter Saturation<0-100> ");

             button = new JButton(" Get Image ");
			 
              f.setLayout(new FlowLayout());

			 f.add(label); f.add(tf); f.add(label1); f.add(tf2); f.add(button);
	 		 	 
				 f.setSize(300,300);
                f.setVisible(true);

	       button.addActionListener(this);       
			
		 //setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }
	void functionHS()
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
			

// to Find Smax
float smax=hsi[0][0][2];
for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
					if(smax<hsi[x][y][2]) smax= hsi[x][y][2];
				 }
			 }

			//to apply illumination

float d=0,h=0,s=0;
     
	 for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
					// to odify huew value
					 h= hsi[x][y][1];   //hue ,h
                    s=hsi[x][y][2];  //sat ,s
				     
                    d= s/(s+sat);
					 d= 1-d;
				   
				   float dif= hue-h;
				   
				   if(dif>0)
					 {
					   hsi[x][y][1]= h+ dif*d;
					 }
				   else 
					 {
					   dif=-dif;					 
					   hsi[x][y][1]= h-dif*d;
					 }
                    
		    // to modify S value
			    if(s<smax/4)  hsi[x][y][2]=sat;    // if sin < thresold/4   Sop= Sin
			     else  hsi[x][y][2] = (float)Math.sqrt(sat*s);	    
			     }
			  }

 System.out.println("ratio..."+d+"...sat.."+sat+"..hue.."+hue);
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

	 
   
      hue = Integer.parseInt(tf.getText());
	  hue=hue/360;

	  sat = Integer.parseInt(tf2.getText());
      sat=sat/100;
  
	  f.dispose();

      functionHS();
	 
	  f2.repaint();
          
	 }
	
}  



// Imax = 1.0 when rgb= (255,0,0)