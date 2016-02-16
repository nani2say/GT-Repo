

var hp : int =100;

function Update ()
 {
    //print("Checkkkkkk");
 /*  if( hp ==0 || hp <0)
     {
     gameObject.GetComponent('Detonator').Explode(); 
     Destroy(gameObject,3); 
     hp=10;
     }
     */
  }


function OnTriggerEnter(other:Collider)
{
print("On Trigger Enter - Player!!!");
 hp = hp-10;
  
 /* if(other.collider.tag!="Player") 
  {
  if(other.rigidbody)
  {
  Destroy(other.rigidbody);
   }
  }
  */ 
}

function onCollisionEnter(other:Collider)
{
 print("On Collision Enter - Player");
 
 }


/*
function onCollisionEnter(other:Collider)
{
print("hiiii");
 hp = hp-10;
 
 Destroy(other.rigidbody);
}
*/