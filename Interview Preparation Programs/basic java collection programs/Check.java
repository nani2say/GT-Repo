import java.io.*;
import java.util.*;

class Item
{
 protected int item_num;
 protected String name;
 protected float value;

 public String toString()
    {
	  return "Item: "+name+" code: "+item_num+" value: $"+value; 
	}
}

public class Check
{
public static void main(String args[])
    {
	  ArrayList<Item> alist = new ArrayList<Item> ();
	  Scanner sc = new Scanner(System.in);
	  Item it;
	  
	  System.out.println("\n enter the number of Items ");
	  int n =sc.nextInt();
	  
	  for(int i=0;i<n;i++)
	  {	  
	   it= new Item();
	   	  
	  System.out.println("\n Item name: ");
	  it.name = sc.next();	  
	  
	  System.out.println("\n Item Code: ");
	  it.item_num = sc.nextInt();	
	  System.out.println("\n Item value: ");
	  it.value = sc.nextFloat();	
	  
	  System.out.println("Item:"+it.name);	  	  
	  alist.add(it);
	  
	  }
	  
	   System.out.println(alist);
	 	  	   
    }
 
} 