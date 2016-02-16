

var hp : int =100;

function Update ()
 {
    //print("Checkkkkkk");
   if( hp ==0 || hp <0)
     {
     gameObject.GetComponent('Detonator').Explode(); 
     Destroy(gameObject,3); 
     hp=10;
     }
  }


function OnTriggerEnter(other:Collider)
{
//print("Trigger from Tank!!!");
 hp = hp-10;
  
  if(other.collider.tag=="GameController" ) 
  {
  if(other.rigidbody)
     {
      Destroy(other.rigidbody);
      }
   }
   else if(other.collider.tag=="Player" )  //if enemy hits the player.
    {    
     print("ssup!!");
      var player = GameObject.Find("TankStruc");            
      var playerScript = player.GetComponent("health_score");
      
      playerScript.Hit();
      
    } 
  
  
  
}

/*
function onCollisionEnter(other:Collider)
{
print("hiiii");
 hp = hp-10;
 
 Destroy(other.rigidbody);
}
*/