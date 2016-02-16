
import java.util.*;
import java.io.*;

/* This Interface is declared to handle various locations tax functionalities and associated tax Constants */
interface Tax
   {
     final float CA_TAX_PERCENT =9.75f;
	 final float NJ_TAX_PERCENT =7.0f;
	 
	 abstract public void caliculateTax_CA();
	 abstract public void caliculateTax_NJ();
   }
   
/* User defined Exception to handle negative Numbers for Quantity and Price */  
class NegativeNumberException extends Exception {}  
 
/* This class represents an individual item in the store. It also loads the item categories during the start of the program. 
 * Item categories are loaded from the file 'item.properties'.
 * If the file 'item.properties'  is not found, it creates a new file with certain items and categories in it. */
class Item
{
  protected String itemName;
  protected int quantity;
  protected double price;
  protected String category;
  
  protected static Properties itemProp=new Properties();;
  
  public Item(String itemName, int quantity, double price)
      {
	    this.itemName=itemName;
		this.quantity=quantity;
		this.price=price;	
       
	   /* Get the category of the item from the properties file */
        category = itemProp.getProperty(itemName,"UNKNOWN");  //default is UNKNOWN	   
	          // if(category.equals("UNKNOWN"))
	          // System.out.println("\n Category of "+itemName+" is "+category+". Please add the item to the configuration file- 'item.properties'. Tax is being calculated for this item ");
	   }  
	   
 /* Used to display Details of one Item */	   
  public void displayItemDetails()
      { 
	    System.out.println(" Item Name: "+itemName+" Quantity: "+quantity+" Price: "+price+" Category: "+category);
	  }  

  /* Initialize the program to load the item categories into a variable - itemProp */	  
   static void initStore()
       {	   
		 try
		  {
           itemProp.load(new FileInputStream("item.properties"));	
		  }
         catch (IOException e) 
	       {   	
		   
		     /*If the file does not exist add the item categories to a new properties file */
			// System.out.println("Loading Item Categories.. ");			 
             addItemCategory("book","OTHER");
			 addItemCategory("pen","OTHER");
			 addItemCategory("paper","OTHER");
			 addItemCategory("potato chips","FOOD");
			 addItemCategory("tshirt","CLOTH");
			 addItemCategory("sweater","CLOTH");
			 addItemCategory("music cd","OTHER");
			 addItemCategory("bread","FOOD");
		     
			 writePropFile();		   
		   // e.printStackTrace();
           }		
	    }   
	
  /* It can be used by the administrator to add new items and categories to the existing list */ 	
  static void addItemCategory(String itemName, String category)
	    {    
         itemProp.setProperty(itemName,category); 
		 }
   
   /* Write the Properties onto a file. It should be called after using addItemCategory() method */
   static void writePropFile()
         {   
          try		 
             { itemProp.store(new FileOutputStream("item.properties"),"Item categories"); 
              }
          catch (IOException e)
             {
                System.out.println(" An Exception occurred inside writePropFile() method ");
			  }			
          }

   /* static block is used to initialize the static variable itemProp */		  
	 static
       {
	    initStore();
	   }	 
	}
      	
/* This class is used to store the items in an ArrayList and implement billing feature along with Tax calculations*/
class ShoppingBasket implements Tax
 {
   protected ArrayList<Item> basket;
   protected String location;
   protected double subTotal;
   protected double tax;
   protected double totalBill;
   
   public ShoppingBasket( )
               {
			    basket = new ArrayList<Item>();
			   }
   
   /* This functions is used to accept input from the user and add items to the basket after validation */
   public void addItems()
             {
			    Scanner sc = new Scanner(System.in);
				char choice;
				String  name; 
				double price;
                int qty;
 				
				while(true)
				{
				  try
				  {
			       System.out.println(" Enter Item Name: ");
 	               name=sc.nextLine();                   
				   System.out.println(" Enter Price : ");
                   price= Double.parseDouble(sc.nextLine()); 
				   System.out.println(" Enter Quantity : ");
                   qty= Integer.parseInt(sc.nextLine());
                   
				   if(qty<=0 || price <=0) throw new NegativeNumberException();
				   
				   Item itemObj = new Item(name,qty,price);
				   basket.add(itemObj);
                   }
				  catch(NegativeNumberException e) {System.out.println("Error: Please enter positive numbers for Qty and Price"); } 
                  catch(Exception e) {System.out.println("Error: Invalid Entry "); }						 	
                  
				  System.out.println("Do you want to add more items? <Y/N> ");
                  choice = sc.next().charAt(0);	
                  sc.nextLine();// To consume the /n character
				  if(choice!='y'&& choice!='Y') break;	
                  				  
				} 
			 }   
   
    /*This method is used to separate bill calculation procedures for NJ and CA.
     *It also validates the location before calling appropriate methods */	
	public void caliculateBill()
    {
	  Scanner sc = new Scanner(System.in);
	  try
		{
	     System.out.println(" Enter Location Code(CA/NJ): ");
		 location=sc.next();
		 }
		catch(Exception e) {
		                     System.out.println("Error: Invalid Location Entry ");
							  System.exit(0);
							} 
	    if(location.equalsIgnoreCase("NJ"))
            {
			    caliculateTax_NJ();							
			}	
        else if (location.equalsIgnoreCase("CA"))
             {
                 caliculateTax_CA();
              }
         else
     		 {
		      System.out.println("Error: Invalid Location Entry. It should be either CA or NJ");
			  System.exit(0);		
              } 							  
	 }
	 
   /* This method is used to display the bill with proper alignment	 */
	 public void displayBill()
         {
		  String heading1 = "Item";
          String heading2 = "Price";
		  String heading3 = "Qty";
		  
		   System.out.printf("\nBill\n====\n");
		   
		   System.out.printf("%-15s%-10s%10s",heading1,heading2,heading3);
		   System.out.printf("\n");
		   
		   for(Item obj: basket)
				   {				     
				    System.out.printf("\n%-15s%-10.2f%10d",obj.itemName,obj.price,obj.quantity);
					}
			
			System.out.printf("\n%-15s%20.2f","Subtotal",subTotal);	
            System.out.printf("\n%-15s%20.2f","Tax",tax);
			System.out.printf("\n%-15s%20.2f","Total",totalBill);
			
			 System.out.printf("\n"); //The End
			           			
		 }	
	
 /*Method to handle the Tax calculations for New Jersey  */	
    public void caliculateTax_NJ()
      {	  
         subTotal=0;
         double taxableAmount=0;
	  
				for(Item obj: basket)
				   {
				     if(!obj.category.equals("FOOD" ) && !obj.category.equals("CLOTH"))
					  {
					    taxableAmount+=obj.price*obj.quantity;
					   }
				      subTotal+= obj.price*obj.quantity;		
				   }	
              tax =  taxableAmount* NJ_TAX_PERCENT*0.01;  
			  roundTax(); //To round off to the nearest 0.05
			  totalBill= subTotal+tax; 
        }  
  
/*Method to handle the Tax calculations for California  */	  
   public void caliculateTax_CA()
   {
     subTotal=0;
     double taxableAmount=0;
	  
				for(Item obj: basket)
				   {
				     if(!obj.category.equals("FOOD" ))
					  {					   						
					    taxableAmount+=obj.price*obj.quantity;
					   }
				     subTotal+= obj.price*obj.quantity;				   
				   }	
        tax =  taxableAmount* CA_TAX_PERCENT*0.01;  
		roundTax();
        totalBill= subTotal+tax; 		
   }   
  
	/* To round off the tax to nearest 0.05 */ 
    public void roundTax()
     {
	  //System.out.println("Before Rounding to 0.05, tax is "+tax);
	   tax = Math.ceil(tax * 20) / 20;
	  //System.out.println("After Rounding, tax is "+tax);
	 } 
 }
 

/*This class is defined to test other classes and its the starting point for the application */
public class ShoppingBasketApplication
 {
   public static void main(String args[])
   {
	  ShoppingBasket sb = new ShoppingBasket();
	  sb.addItems();
	  sb.caliculateBill();
	  sb.displayBill();
   }
 }