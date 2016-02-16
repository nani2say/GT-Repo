

var curhealth : int =200;
var maxhealth : int =200;
var points : int =0;

function Hit()
 {
   curhealth = curhealth-10;
  
 }

function Score()
  {
   points=points+10;
   
  }
  
 function OnGUI () 
 {
   // GUI.Label (Rect (10, 10, 100, 20), "Hello World!");
   // GUI.Box(new Rect(10,10,Screen.width/(2/(maxhealth/curhealth)),20),"HEALTH"+curhealth );
 
    GUI.color = Color.red;
    if(curhealth<=0)
   {
     // Destroy(gameObject);
      GUI.Label(Rect(100,100,200,80),"You Loose!");
     if(GUI.Button(Rect(100,150,100,50),"Play Again?"))
       {
        Application.LoadLevel(0);
        }      
   }
   
   else{
        
   //GUI.backgroundColor = Color.yellow;
      GUI.Label(new Rect(10,10,100,20),"HEALTH");
      GUI.Box(new Rect(70,10,curhealth,20)," "+curhealth);
  
     }
   
 } 