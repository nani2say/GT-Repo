

import java.awt.*;
import java.awt.Color;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

//import javax.swing.*;

class ObjectRec 
{

	BufferedImage img,img2;
	
	int x=0;
	int y=0;
	
	int c, rgb[][][], rgb2[][][];
	float hsi[][][];
	int iw,ih;
    int pixels[];
	PixelGrabber pg;
	int count[][];

	int cnt=0,cnt2=0;

	 BufferedImage applyRecognition(BufferedImage img)
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
		

			//for abjects recognition.


// divide the h valies into sets of 30 deg  starting from 345 - 15, 15-30...
// and s values into sets of 0- 0.1 , 0.1 - 0.2.....
// increase the count[hue-range][s-range] for a specific pixel that falls in dat range.
// select the top 10 count values and display that pixels only

count=new int[13][11];

for(int i=0;i<13;i++)
	 for(int j=0;j<11;j++) 
	    count[i][j]=0;

float h=345,s=0;

     for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
	                 int i,j;
	                
					for(h=345, i=1 ;h<360;i++)
					 {
						  for(s=0,j=1; s<1;j++)
						 {
						    
							  if(( hsi[x][y][1]>= h/360 && hsi[x][y][1]<((h+30)%360)/360) &&  (hsi[x][y][2] >s && hsi[x][y][2]<=s+0.1) ) 
							    {   
								   count[i][j]++;
							       break;
							    }
                            //   System.out.println("..i=="+i+"..j="+j+"..h="+h+"..s="+s);
								s = (float)(s+0.1);
					    	 }
						  
							   h=(h+30)%360;
							   if(h==345) break;
			              					 
				    	 }

				  }
			  }

//selecting the top 10 values in count[][]

int top[][] = new int[20][3];
int cnt[][]= new int[15][15];

for(int i=1;i<13;i++)
        for(int j=1;j<11;j++) 
			 {
	         cnt[i][j]=count[i][j];
			 
			 if(count[i][j]>0) System.out.println("..."+count[i][j]);
			 }
				       
				

for(int k=1;k<=10;k++)
			 {
	            int max=-1, x=-1;y=-1;
               for(int i=1;i<13;i++)
				  { 
				   for(int j=1;j<=10;j++) 
				       {

					   
						
			 
				         if(max<cnt[i][j])
						   {
							 max=cnt[i][j];
							 
							 x=i;y=j;
						   }
	     			   }
       			  }

                 cnt[x][y] = 0;
				 top[k][1] = x;
				 top[k][2] = y;

			 }

// to get top 10 ranges converted in to original ranges.. i.e 1-12 to map with 345deg to 315 deg and also S values

float ranges[][];
ranges = new float[11][3];

for(int i=1;i<=10;i++)
			 {
	                  int x = top[i][1];
					  int y = top[i][2];
					     ranges[i][1]=getx(x);
						 ranges[i][2]=gety(y);

						 System.out.println("...x..."+x+"..y.."+y);
			 
			//			 System.out.println("...h..."+ranges[i][1]+"..s.."+ranges[i][2]);
			
			 }

// to display only the top 10 H-S value counts  (objects)

 for(int x=0;x<ih;x++)
			 {
				for(int y=0;y<iw;y++)
				 {
               	   int	flag =0;
				//	int hu=hsi[x][y][1];
				//	int st=hsi[x][y][2];

					for(int i=1;i<=10;i++)
					 {
						float hr=ranges[i][1];
						float sr=ranges[i][2];

						 if( (hsi[x][y][1]>=hr/360 && hsi[x][y][1]< ((hr+30)%360)/360) &&  (hsi[x][y][2] >sr && hsi[x][y][2]<sr+0.1) )
						 {
							  flag=1; break;  
						 }
					  }

					  if(flag==0) hsi[x][y][3]=0;
	                 	              
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


///--------------------------

		 }
		 catch(Exception e) {
		
		 e.printStackTrace();
		 }

		 return img2;
		 //setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	float getx(int x)
	{
	  if(x==1) return 345;  else if(x==2) return 15;
      
	  else if(x==3) return 45;  else if(x==4) return 75;

	  else if(x==5) return 105;  else if(x==6) return 135;

	 else if(x==7) return 165;   if(x==8) return 195;

	  else if(x==9) return 225;  else if(x==10) return 255;

	  else if(x==11) return 285;  else if(x==12) return 315;

	  else return -1;

	}

	float gety(int x)
	{
	  if(x==1) return (float)0;           if(x==2) return (float)0.1;
	  if(x==3) return (float)0.2;         if(x==4) return (float)0.3;
	  if(x==5) return (float)0.4;         if(x==6) return (float)0.5;
	  if(x==7) return (float)0.6;          if(x==8) return (float)0.7;
	   if(x==9) return (float)0.8;         if(x==10) return (float)0.9;

	   return -1;

    
	}

	public void paint(Graphics gr)
	{
		gr.drawImage(img2,40,40,null);
	}
	
}  



// Imax = 1.0 when rgb= (255,0,0)