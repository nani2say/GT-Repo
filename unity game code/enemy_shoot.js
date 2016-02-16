#pragma strict


var projectile : Rigidbody;
var speed = 150;
//var otherClip : AudioClip;


/*function Update () 
{

      fire();
      wait();
    
}
*/

function Start()
{
 print('Enter Start');
  while(true)
   {
   
   fire();
   yield WaitForSeconds(1);
  // wait();
   }
}

/*function wait()
{ 
  
 yield WaitForSeconds(1);
 }
*/
function fire () 
{
  // print("Firing");
//  audio.PlayOneShot(otherClip);
//yield WaitForSeconds (1);

var clone = Instantiate(projectile, transform.position, transform.rotation);
clone.velocity = transform.TransformDirection( Vector3 (0, 0, speed));

Destroy (clone.gameObject, 5);

}

/*
function OnTriggerEnter(other:Collider)
{
print("Triggerrr from Bullet");
}


function onCollisionEnter(col:Collision)
 {
  print("collisionnnnn");
 
 Destroy(col.rigidbody);
 
 }

*/
