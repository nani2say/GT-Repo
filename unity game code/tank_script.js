#pragma strict

var forwardspeed:float =50;
var turnspeed:float =2;
 
 //------- turret code
var turret : GameObject;
var gun : GameObject;

var right : Vector3 = Vector3(0.0,1.0,0.0);
var left : Vector3 = Vector3(0.0,-1.0,0.0);

var up : Vector3 = Vector3(0.0,0.0,1.0);
var down : Vector3 = Vector3(0.0,0.0,-1.0);

//-------- 
 

function Start () {

}

function Update () {

var ForwardMoveAmount = Input.GetAxis("Vertical")*forwardspeed;

var TurnAmount=Input.GetAxis("Horizontal")*turnspeed;

transform.Rotate(0,TurnAmount,0);

rigidbody.AddRelativeForce(0,0,ForwardMoveAmount);

//--------------- turret rotation

//--------- turret rotation
      
      if(Input.GetKey(KeyCode.Z)) turret.transform.Rotate(left);

       if(Input.GetKey(KeyCode.X)) turret.transform.Rotate(right);


      if(Input.GetKey(KeyCode.C)) gun.transform.Rotate(up);

     if(Input.GetKey(KeyCode.V)) gun.transform.Rotate(down);


}