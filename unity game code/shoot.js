#pragma strict


var projectile : Rigidbody;
var speed = 150;
var otherClip : AudioClip;

function Update () 
{

if ( Input.GetButton ("Fire1")) 
  {
   print("Firing");
  audio.PlayOneShot(otherClip);

var clone = Instantiate(projectile, transform.position, transform.rotation);
clone.velocity = transform.TransformDirection( Vector3 (0, 0, speed));

Destroy (clone.gameObject, 5);

   }

}

/* is not kinematic - so it will not work
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

