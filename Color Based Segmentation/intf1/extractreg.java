

import java.awt.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

//import javax.swing.*;

class Appregion2 extends Frame 
{

	BufferedImage img,img2;
	int x=0;
	int y=0;
	int c,rgb[][][] ,rgb2[][][];
	float hsi[][][],hue,tol;
	int iw,ih;
    int pixels[];
	PixelGrabber pg;

	int cnt=0,cnt2=0;

	Appregion2()
	{
		
		 try{

			 
		System.out.println("Enter hue  (between 0 & 360 )..... \n");
		  DataInputStream dis = new DataInputStream(System.in);
             hue = Integer.parseInt(dis.readLine());
			 hue=hue/360;                   // since we hav to convert hue between the range 0-1

 
        System.out.println("Enter tolerance (<20)..... \n");
        tol= Integer.parseInt(dis.readLine());
                     tol=tol/360;
			 
			 File file = new File("p1.bmp"); 
             
			
			 img = ImageIO.read(file); 

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
			

			//to apply negative filter I =i, H= (h+180)%360, S=smax-s
 		
           int flag=0;

           float ht=hue+tol;
		           if(ht>1) 
			         {
					   ht=ht-1;
                       flag=1;
					 }

		   float ht2=hue-tol;
		             if(ht2<0)
			          {
						 
						 ht2= ht2+1;
                         flag=2;
					  }

     for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
	                if(flag==0)                 //  incase of no problems with range of hue nd tolerance           
				     {
						if( (hsi[x][y][1]>ht2) &&(hsi[x][y][1]<ht)&& hsi[x][y][2]>0.1 );
					    else hsi[x][y][3]=0;
					 }

					else if(flag==1)                // incase of hue exeeds 360 (i.e >1 here)  
				     {
						if( ((hsi[x][y][1]>ht2) &&(hsi[x][y][1]<=1) || (hsi[x][y][1]>=0) &&(hsi[x][y][1]<ht))&& hsi[x][y][2]>0.1);
					    else hsi[x][y][3]=0;
					 }

                    else if(flag==2)               // incase of hue less than 0.... if hue is -ve angle then angle= 360-angle  so here it is (1-x) 
				     {
						if( ((hsi[x][y][1]>=0) &&(hsi[x][y][1]<ht) ||(hsi[x][y][1]>ht2) &&(hsi[x][y][1]<=1))&& hsi[x][y][2]>0.1 );
					    else hsi[x][y][3]=0;
					 }

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
  
   img2 =new BufferedImage(650, 650, BufferedImage.TYPE_INT_RGB);


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

		 }
		 catch(Exception e) 
			 {
		
		       e.printStackTrace();
		      }
		 //setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void paint(Graphics gr)
	{
		gr.drawImage(img2,4,30,null);
	}
	
}  

public class extractreg
{
	public static void main(String[] args) 
	{
      
	
		Appregion2 f= new Appregion2();
		f.setSize(700,700);

		f.setVisible(true);
	}
}

// Imax = 1.0 when rgb= (255,0,0)