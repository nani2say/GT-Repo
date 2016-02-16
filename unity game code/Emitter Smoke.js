function Update () {
if(Input.GetAxis("Vertical")){
particleEmitter.emit = true;}
else{
if(Input.GetAxis("Horizontal")){
particleEmitter.emit = true;}
else{
particleEmitter.emit = false;}}
}