import java.util.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class HashMapTest {

	public static void main(String[] args) {
	
		Map<String, String> hm1 = new HashMap<String, String>();

			 hm1.put("O", "One");
			 hm1.put("T", "TWO");hm1.put("H", "THREE");
	
			Set<String> keys = hm1.keySet();
			
			for(String s:keys)
			 {
			  hm1.put("X","Test");
			 }
			
}
}