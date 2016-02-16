/* 
Project 1
CS6370
Puzzle
*/

<!--
var G_Pos;
var count = 0;
var time;
var timer_on = new Boolean(false);
var move = 0;
var best_move;
var best_time;
var soundEmbed = null;

function initEmpty()
{
	G_Pos="t44";
}


//-----------------------------------
//------------Code to Shuffle
//--------------------------------

function shuffle()
{
 

for (var i=1;i<=400;i++)    // Swaps the empty cell with adjacent cells 400 times 
	{
		
		var Gpos=G_Pos;

        var g_node = document.getElementById(Gpos);  //get the empty cell Node from GLobal Variable

        var Apos = getAdjacent(Gpos);  // this function will give one adjacent cell based on random() func  --- returns a sting representation

         var adj_node = document.getElementById(Apos); // get the  NODE out of String representation

		swap(g_node,adj_node);  // Swap
    
	}
	//re-initialize move and count counter
	count = 0;
	move = 0;
	document.getElementById("moves").innerHTML = "Moves: 0";
  //start time when shuffled
  stopCount();
  startCount();
  
  //start music
  soundPlay("01-main-theme-overworld");
  }  // end of shuffle
  

 function getAdjacent(gpos)
 {
	    //  var tmp = gnode.getAttribute("id");
	
		  var row =parseInt(gpos.charAt(1));
		  var col =parseInt(gpos.charAt(2));
  
     //  alert('row '+row+' col'+col);

      // var decision = Math.floor(Math.random()*10)%2; // this will give either 0 or 1
	  
	   var count =0;
       var nodes = new Array();

	   // Below code generates the set of all posible adjacent nodes stored into array - nodes
	 
       if(  (row+1)<=4) {
		                 nodes[count]="t"+(row+1)+col;
						 count++ ; 
						}
	   if(  (row-1)>=1)  {
		                 nodes[count]="t"+(row-1)+col;
		                 count++;
	                      }
						  
       if( (col+1)<=4) {
		                 nodes[count]="t"+row+(col+1);
						  count++;
	                   }

       if(  (col-1)>=1) {
		                 nodes[count]="t"+row+(col-1);
						 count++;                 			    
	                     }
    
     //count will have the length of array

	  // We shall select one of the array elements to be used for swapping based on random function
	  
      var d =  Math.floor(Math.random()*10)%count;

       var adj =  nodes[d];
	   
	  

	 //  alert(adj);
 
       return adj;
 }

//-------------------------------------------

//--  function to swap the contents of an empty node to another Adjacent cell;

function swap(empt,adj)
 {
          var tmp = adj.getAttribute("id");
	
		  var pos1 =parseInt(tmp.charAt(1));
		  var pos2 =parseInt(tmp.charAt(2));

		  G_Pos='t'+pos1+pos2;

		//  alert('pos='+G_Pos);
			
		/* Copies the current node on to the empty node */
		empt.innerHTML = adj.innerHTML;
		empt.style.backgroundImage = adj.style.backgroundImage;
		
		/* Sets thte current node to be black */
		adj.innerHTML = "";
		adj.style.backgroundImage = "none";
		
		
   }


//-----------------------------

/* Checks to see if two tiles are adjacent to each other on the board*/
function isAdjacent(first_id, second_id) {
	var first_id_num;
	var second_id_num;
	var first_id_tens;
	var first_id_ones;
	var second_id_tens;
	var second_id_ones;
	
	first_id_num = first_id.getAttribute("id");
	second_id_num = second_id.getAttribute("id"); 
	
	first_id_tens = parseInt(first_id_num.charAt(1));
	first_id_ones = parseInt(first_id_num.charAt(2));
	second_id_tens = parseInt(second_id_num.charAt(1));
	second_id_ones = parseInt(second_id_num.charAt(2));
	
	if (((first_id_tens == second_id_tens) && ((first_id_ones == second_id_ones+1) || (first_id_ones == second_id_ones-1))) || 
		((first_id_ones == second_id_ones) && ((first_id_tens == second_id_tens+1) || (first_id_tens == second_id_tens-1)))) {
		return true;
	}
	else {
		return false;
	}
		
}

/* Returns node of the empty adjacent square if there is one. Else returns null. */
function isValid(id) {

	//var empty_node_num = prompt("Enter empty node ID", "t44");

    var empty_node_num=G_Pos;

	empty_node = document.getElementById(empty_node_num);
	
	if(isAdjacent(id, empty_node)) {
		return empty_node;
	}
	else {
		return null;
	}
}

/* Checks to see if tile can be moved (next to blank square). If so tile is highlighted. */
function highlightValid(id) {

	var valid_move = isValid(id);
	
	if(valid_move != null) {
		id.style.color="#006600";
		id.style.borderColor="#FF0000";
		id.style.textDecoration="underline";
	}
}

/* Un-highlights a tile */
function unHighlightValid(id) {
	id.style.color="#000000";
	id.style.borderColor="#000000";
	id.style.textDecoration="none";
}

/* First check to see if there is an adjacent empty square and if so moves current piece to it. Else does nothing */
function makeMove(id) {
	
	/* Checks to see if there is an adjacent empty square */

	var valid_move = isValid(id);

	/* If adjacent empty square exists, move current piece to it. Else do nothing */
	if(valid_move != null) {
		
			swap(valid_move,id);   // here valid_move is the empty cell  
				
				
		    
            
              // THIS IS WHERE WE CAN WRITE Code for  - END OF GAME NOTIFICATION   -- lets call it check_end_of_game()
			  if(timer_on != false){
			    move++;
			    document.getElementById("moves").innerHTML = "Moves: " + move;
			    check_end_of_game();
	       
		      }
			
			/* Needed for appearences. If not called then the newly created empty node tile will continued to be highlighted since it used to be a valid move */
			unHighlightValid(id);	
	}
}

function check_end_of_game() {
// tile to be checked
var tile;

var ids = new Array ();
ids[0] = "t11";
ids[1] = "t12";
ids[2] = "t13";
ids[3] = "t14";
ids[4] = "t21";
ids[5] = "t22";
ids[6] = "t23";
ids[7] = "t24";
ids[8] = "t31";
ids[9] = "t32";
ids[10] = "t33";
ids[11] = "t34";
ids[12] = "t41";
ids[13] = "t42";
ids[14] = "t43";
ids[15] = "t44";

//loop counter & index for tile value
var i = 1;

//Array index 
var k = 0;

for(i=1; i<16; i++) {
 tile = document.getElementById(ids[k]).innerHTML;

if(tile.toString() != i.toString()) {
return;
}

k++;
}


if(best_move == null){
						best_move = move;
				     }

if(best_time == null) 
					{
						 best_time= count;
					}

if(move < best_move){
					     best_move = move;
				    }

if(count < best_time){
						best_time = count;
					}
					
document.getElementById("bestt").innerHTML ="Best Time: " + best_time + " secs";
document.getElementById("bestm").innerHTML ="Best Moves: " + best_move + " moves";
stopCount();
if(count < 150){
soundPlay("07-castle-complete");
}
else
{
soundPlay("06-level-complete"); 
}


alert("Congratulations!  You Have Sloved The Puzzle");




}
function startCount()
{
//if the timer is off then turn it on
//this prevents the timer from being constantly started during multi shuffles

if(timer_on == false) {
 count = 0;
 timer_on = true;
//timer increment in miliseconds. 1000ms = 1 sec
}
if(timer_on == true) {
document.getElementById("time").innerHTML="Timer: " + count + " secs";
count = count + 1;
time=setTimeout("startCount()",1000);
}


if(count == 150){
soundPlay("18-hurry-overworld");
}

if(count == 215)
{
soundPlay("14-hurry-underground");
}
}

function stopCount()
{

timer_on = false;

//stops the setTimeout (stopping the timer)
clearTimeout(time);
}

 
 function soundPlay(which)
{
    if (soundEmbed)
       document.body.removeChild(soundEmbed);
    soundEmbed = document.createElement("embed");
    soundEmbed.setAttribute("src", "Sounds/"+which+".mp3");
    soundEmbed.setAttribute("hidden", true);
    soundEmbed.setAttribute("autostart", true);
	soundEmbed.loop = "true";
    document.body.appendChild(soundEmbed);
}
-->