

public class TestClass
 {
    public static void main(String args[])
	{
	  /* Test 1 */
	  System.out.println("\n Test Case 1 \n");
	  ShoppingBasket sb = new ShoppingBasket();
	  sb.basket.add(new Item("book",1,12.99)); //item name , qty, price
	  sb.basket.add(new Item("potato chips",1,3.99));
	  sb.location="CA";
	  sb.caliculateBill();
	  sb.displayBill();	
	  
	    /* Test 2 */
	  System.out.println("\n Test Case 2 \n");
	  ShoppingBasket sb2 = new ShoppingBasket();
	  sb2.basket.add(new Item("book",1,12.99)); //item name , qty, price
	  sb2.basket.add(new Item("music cd",3,9.99));
	  sb2.location="NJ";
	  sb2.caliculateBill();
	  sb2.displayBill();	
	  
	    /* Test 3 */
	  System.out.println("\n Test Case 3 \n");	
	  ShoppingBasket sb3 = new ShoppingBasket();
	  sb3.basket.add(new Item("music cd",2,9.99)); //item name , qty, price
	  sb3.basket.add(new Item("sweater",1,29.99));
	  sb3.location="NJ";
	  sb3.caliculateBill();
	  sb3.displayBill();	
	
	}
 
 }