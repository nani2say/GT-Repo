//attach this script to the object that will follow FPC
//this script working conjunction with SmoothLookAt script that you should also apply to the object
//and Constant Force component that should be applied to the object
//The object needs a sphere collider and it has to be a rigidbody with Freeze Rotation turned on

//object to be followed
var detectObject: Transform;
var ob : Transform;
//distance that will trigger following action
var distanceDetection: float;

function Update () {
	
	if (detectObject) {
		var dist = Vector3.Distance(detectObject.position, transform.position);
		
		//if distance is less than what is specified then do something
		if(dist<distanceDetection){
			//print("attack");
			GetComponent(SmoothLookAt).enabled = true;
			GetComponent(ConstantForce).enabled = true;
			ob.GetComponent(enemy_shoot).enabled = true;
		}else{
			//print("stop attack");
			GetComponent(SmoothLookAt).enabled = false;
			GetComponent(ConstantForce).enabled = false;
			ob.GetComponent(enemy_shoot).enabled = false;
		}

	}

}