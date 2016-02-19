
var module = angular.module("ezy", ['ngRoute','ezy.authentication.services']); // this is the main module in the application. every other module should be included here.
                                                                               //inject all other modules or services or dependencies here
module.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.xsrfCookieName = 'csrftoken';
    $httpProvider.defaults.xsrfHeaderName = 'X-CSRFToken';
}]);

//-------- Shared service between TestServer Controller and DisplayTestController

module.service('ShareService', ShareService);

function ShareService()
 {
  var ShareService =
  {
   initSelections: initSelections, //this is a function
   activeTestServerId: activeTestServerId, //these are just shared variables
   startTime: startTime,
   activePaper: activePaper,
   selections: selections,
   activeTestId:activeTestId,
   scores:scores
  };

  return ShareService;

  var activeTestServerId;
  var activePaperId;
  var startTime;
  var activePaper;
  var selections;
  var activeTestId;
  var scores;

  function initSelections()
  {
   // alert("from initselections- paperid ="+this.activePaper.id); //i think we can use ShareService in pace of "this"
    var sublength = this.activePaper.subsections.length;

    this.selections = new Array();
    this.scores = new Array();

    //initilaize a 2D array
    for(var i =0; i< sublength; i++)
     {
      this.selections[i] = new Array(this.activePaper.subsections[i].questions.length);
     }

     //initialize all selections with 0. They might already be zero's. This is just a safety measure
    //here we assume Row Number = Subsection and Columns represent Questions where we store selections(answers)
    // i.e selections[2][3] = 1 means SubSection =2 QuestionNum =3 and Answer/Choice = 1 (1st radio selected
     for(var i =0 ;i<this.selections.length ; i++)
      for(var j =0 ; j < this.selections[i].length ; j++ )
          this.selections[i][j] = 0;

  }

 }


//--------------

module.config(['$routeProvider',
    function($routeProvider) {
         $routeProvider.when('/register', {
      controller: 'RegisterController',
      controllerAs: 'vm',
      templateUrl: '/static/templates/authentication/register.html'
    }).when('/userUpload',{
                 templateUrl:"/static/templates/FileUpload/user_upload.html"
             }).when('/upload',{
                 templateUrl:"/static/templates/FileUpload/file_upload.html"
             }).when('/login', {
       controller: 'LoginController',
       controllerAs: 'vm',
       templateUrl: '/static/templates/authentication/login.html'
       }).when('/viewtests', {
           controller: 'TestServerController',
           controllerAs: 'vm',
           templateUrl: '/static/templates/Test/TestServerList.html'
      }).when('/teststartpage', {
           controller: 'DisplayTestInstructionsController',
           controllerAs: 'vm',
           templateUrl: '/static/templates/Test/DisplayTestInstructions.html'
      })
     .when('/writetest', {
           controller: 'DisplayTestController',
           controllerAs: 'vm',
           templateUrl: '/static/templates/Test/DisplayTest.html'
      })
      .when('/displayscores', {
           controller: 'DisplayScoresController',
           controllerAs: 'vm',
           templateUrl: '/static/templates/Test/DisplayScores.html'
      })
      .when('/', {
           controller: 'HomePageController',
           controllerAs: 'vm',
           templateUrl: '/static/templates/GenericHomePage.html'
      }).otherwise({
                redirectTo: '/' //should be just "/" to go back to index. Thats how thinkster is.
            });

    }]);


  module.controller('RegisterController', RegisterController);

  RegisterController.$inject = ['$location', '$scope','Authentication'];

  /**
  * @namespace RegisterController
  */
  function RegisterController($location, $scope, Authentication) {
    var vm = this;
    vm.register = register;
   // alert("hello from reg controller");

    activate();

    /**
    * @name activate
    * @desc Actions to be performed when this controller is instantiated
    * @memberOf thinkster.authentication.controllers.RegisterController
     */
     function activate() {
      //alert("hello from register activate");
     // If the user is authenticated, they should not be here.
      if (Authentication.isAuthenticated()) {
          $location.url('/'); //should we replace with window.location = '/'; like in login success funtion. Anyway this case as a whole doesnt make sense to me.
                              // I mean we might not enable register link in first place when the user is logged in.
        }
       }

    /**
    * @name register
    * @desc Try to register a new user
    * @param {string} username The username entered by the user
    * @param {string} password The password entered by the user
    * @param {string} email The email entered by the user
    * @returns {Promise}
    * @memberOf thinkster.authentication.services.Authentication
    */
    function register() {
    // alert("hi222"+vm.username+" "+vm.username);
     Authentication.register(vm.email, vm.password, vm.username, vm.first_name, vm.last_name);
    }
  }

//Login Controllers

  module.controller('LoginController', LoginController);

  LoginController.$inject = ['$location', '$scope','Authentication'];

  /**
  * @namespace LoginController
  */
  function LoginController($location, $scope, Authentication) {
   //alert("from login controller");
    var vm = this;

    vm.login = login;

    activate();

    /**
    * @name activate
    * @desc Actions to be performed when this controller is instantiated
    * @memberOf thinkster.authentication.controllers.LoginController
    */
    function activate() {
      // If the user is authenticated, they should not be here.
   //   alert('login activate');
      if (Authentication.isAuthenticated()) {
        $location.url('/');
      }
    }

    /**
    * @name login
    * @desc Log the user in
    * @memberOf thinkster.authentication.controllers.LoginController
    */
    function login() {
      Authentication.login(vm.username, vm.password);
    }

} //login controller

  module.controller('NavbarController', NavbarController);

  NavbarController.$inject = ['$scope', 'Authentication'];

  /**
  * @namespace NavbarController
  */
  function NavbarController($scope, Authentication) {
    var vm = this;

    vm.logout = logout;

    /**
    * @name logout
    * @desc Log the user out
    * @memberOf thinkster.layout.controllers.NavbarController
    */
    function logout() {
      Authentication.logout();
    }
  }//navbarcontroller

//--------- HomePage Controller -----


module.controller('HomePageController', HomePageController);

HomePageController.$inject = ['$location', '$scope','Authentication'];

  function HomePageController($location, $scope, Authentication)
  {
    var vm = this;
    vm.isAuthenticated = Authentication.isAuthenticated();
   // alert("hello -"+vm.isAuthenticated);
  }
//----- end Homepage controller

//-- TestServer Controller ----
  module.controller('TestServerController', TestServerController);

  TestServerController.$inject = ['$location', '$scope','$http','ShareService'];

  function TestServerController($location, $scope, $http, ShareService )
   {
     var vm = this; //not sure if this is needed

     $http.get('/api/v1/testservers/')
      .then(function(response){ $scope.testservers = response.data; })

     $scope.starttest = function(idx)
     {
      ShareService.activeTestServerId = $scope.testservers[idx].id  //store testserverid
      ShareService.activePaperId =  $scope.testservers[idx].paper.id  //store paperid

     // alert("start test-"+$scope.testservers[idx].label)
      //make a call to DisplayTestController??
       $location.path('/teststartpage') // change it to displaytest
     }
   }

//----------------

//------ DisplayTestController ----

module.controller('DisplayTestInstructionsController', DisplayTestInstructionsController);

  DisplayTestInstructionsController.$inject = ['$location', '$scope','$http','ShareService'];

  function DisplayTestInstructionsController($location, $scope, $http, ShareService )
   {
    //api call to get paper
    $http.get('/api/v1/getpaper/'+ShareService.activePaperId+'/')
      .then(function(response){
                                 $scope.paper = response.data;
                                 ShareService.activePaper = response.data; //store in service to pass to DisplayTest Controller
                                 //alert("paper-label="+$scope.paper.label);
                                 })

    $scope.displaytest = function()
     {
      //alert("hello from displaytest()");
       $location.path('/writetest'); //call actual test display
     }
   }

 //--------

  module.controller('DisplayTestController', DisplayTestController);

  DisplayTestController.$inject = ['$location', '$scope','$http','ShareService'];

  function DisplayTestController($location, $scope, $http, ShareService )
   {
      $scope.paper = ShareService.activePaper;
     // alert("hello from write test(). sublength =" +$scope.paper.subsections.length);

      $scope.currentSubsection = $scope.paper.subsections[0]; // starting subsection Data

      $scope.currentSubsecIdx  = 0;  //starting subsction Number
      $scope.currentQtnIdx = 0;     //starting question in a Subsection
      $scope.displayInstructionsFlag = true;

      ShareService.initSelections(); //will initialize ShareService.selections array
      $scope.selections = ShareService.selections;
     // alert("Selections length ="+$scope.selections.length);
      $scope.startSubsection = function()
      {
      //This will hide the intstructions DIV
       $scope.displayInstructionsFlag  = false;

       //if its the first subsection. Post starttime and testserverid to "Tests" table and store the testId that is returned
       if($scope.currentSubsecIdx == 0)
       {
             var date = new Date();
             var isoformatdate = date.toISOString();

             return $http.post('/api/v1/testrecord/', {
                     tserver_id: ShareService.activeTestServerId,
                     start_time: isoformatdate
                     }).then(testStartSuccessFn, testStartErrorFn);

              function testStartSuccessFn(data, status, headers, config) {
                 //nothing as of now
                   //data.data will have the tests.id value i guess
                    alert("Test Id = "+data.data.id);
                    ShareService.activeTestId = data.data.id; //store it for later user
                   }

              function testStartErrorFn(data, status, headers, config) {
                    alert("could not record test start time!");
                    console.error('Post call failed. Could not record test start time!');
                    }
         }//IF
      }//startSubsection

     //called when submit is pressed for every section.
     $scope.submitSection = function()
     {
      // alert("hello from submitSection()");

       //step1. Capture answers or this subsections and post to database.
        var resp =  composeTestResponseString($scope.currentSubsecIdx, $scope.currentSubsection, ShareService.activeTestId , $scope.selections);
        //alert("from submitsection ="+ resp);

        var tempsubsecId = $scope.currentSubsection.id;

        //api call to record endtime for the student/test
          $http.post('/api/v1/testresponse/', resp)
           .then(function(response){
                                       //****  some variables may be printed as undefined  here Or from an alert in Later part in  beacause of delay in api calls. this is normal.
                                     ShareService.scores[tempsubsecId] = response.data.score;
                                     alert("Test Response Records submission Succeeded! subsec id = "+tempsubsecId+" score ="+ShareService.scores[tempsubsecId]);

                                   });

       //step2. Move to next section
       $scope.currentSubsecIdx++;

       //step3. check if that was the last section  *** END OF TEST ****
       if($scope.currentSubsecIdx == $scope.paper.subsections.length)
       {
          //step3.1 update TestsModel with EndTime

          //api call to record endtime for the student/test
          $http.put('/api/v1/testrecord/'+ShareService.activeTestId+'/')
           .then(function(response){
                                     alert("Endtime Record succeeeded.");
                                 })

         // step 3.2 done with test. Move to home Page?
         alert("Congratulations! You have completed this test!")

          $location.path('/displayscores');
         //$location.path('/#/this_is_not_working/'); //this is not working as of now i think.
       }
       else //Configure for next section
       {
        $scope.currentSubsection = $scope.paper.subsections[$scope.currentSubsecIdx];
        $scope.currentQtnIdx = 0;
        $scope.displayInstructionsFlag = true;
       }

      //--- internal functions to this function. Generates Json of test responses
       function composeTestResponseString(curSubsecIdx, curSubsec, testId, selectArray)
        {

         //--- debug Stuff ---
           //var print = " ";
           //for(var j =0 ; j < $scope.selections[$scope.currentSubsecIdx].length ; j++ )
           //   print = print+" #qtn "+j+"=" + $scope.selections[$scope.currentSubsecIdx][j];
           //alert(print); //display selections for the current subsection

          var respArray = [];

          for(var j =0 ; j < selectArray[curSubsecIdx].length ; j++ )
          {
            qtnId = curSubsec.questions[j].id;
            optNum = parseInt(selectArray[curSubsecIdx][j]); //converting to an integer field
            respArray.push(new Response(testId,curSubsec.id,qtnId,optNum));
          }

         var response = JSON.stringify(respArray);
         //alert(); //use console.log() ??

         function Response(testid, subsecid, questionid, optionnum)
          {
           this.test        = testId;
           this.subsec      = subsecid
           this.question    = questionid;
           this.option_num  = optionnum;
          }

          return response;

        } //composeTestResponseString


     } //submitSection function



   }

//-----------

//------ DisplayScoresController ----

module.controller('DisplayScoresController', DisplayScoresController);

  DisplayScoresController.$inject = ['$location', '$scope','$http','ShareService'];

  function DisplayScoresController($location, $scope, $http, ShareService )
   {
    $scope.scores = ShareService.scores;
    $scope.paper  = ShareService.activePaper;
   }

 //--------

//user upload module starts..

module.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

module.service('fileUserUpload', ['$http', function ($http) {
    console.log("inside file userUpload..")
    this.uploadFileToUrl = function(file, uploadUrl,selectedUser){
        var fn_uploadFileToUrl = this;
        var fd = new FormData();
        fd.append('file', file);
        console.log("the selected user is:" + selectedUser)
        fd.append("selected_user",JSON.stringify(selectedUser));
        fd.append("upload_type","user_upload");
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}

        }).then(function(data, status, headers, config){
            console.log("success");
            console.log(data);

            console.log(status)
        },function(data, status, headers, config){
            console.log("failure!");
            console.log(data);

            console.log(status)
        });
    }
}]);



module.controller('myUserCtrl', ['$http','$scope', 'fileUserUpload', function($http,$scope, fileUserUpload){


    $scope.uploadUserFile = function(){
        var file = $scope.myFile;
        var selectedUser = $scope.selectUser;
        var uploadUrl = "/api/v1/fileUserUpload/";
        console.log("the upload URL " + uploadUrl)
        fileUserUpload.uploadFileToUrl(file, uploadUrl,selectedUser);
    };

}]);




// Question file upload module starts..
module.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

module.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl,selectedOption,selectedPaper){
        var fn_uploadFileToUrl = this;
        var fd = new FormData();
        fd.append('file', file);
        fd.append("selected_sub_section",JSON.stringify(selectedOption));
        fd.append("selected_paper",JSON.stringify(selectedPaper))
        fd.append("upload_type","question_upload");
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}

        }).then(function(data, status, headers, config){
            console.log("success");
            console.log(data);

            console.log(status)
        },function(data, status, headers, config){
            console.log("success");
            console.log(data);

            console.log(status)
        });


        var fileLoadFailure = function(data, status, headers, config){
            console.log("failure");
            console.log(data)
            console.log(status)

        };

        /*.success(function(status,message){
        console.log("success",status);
        console.log("message",message)
            })
        .error(function(status,error){
                console.log('error',error)
                console.log('status',status)
        });*/
    }
}]);



module.controller('myCtrl', ['$http','$scope', 'fileUpload', function($http,$scope, fileUpload){


    $scope.papers = [];
    $scope.subsections = [];
    //make a call to the API to fetch all the papers.
    var paperURL = "/paper/"
    $http.get(paperURL).then(function(data,status,headers,config){
        console.log(data["data"]);
        console.log(status);
        //preparing the paper.

        var choice_data =  data["data"];
        for(var i= 0, len = choice_data.length ; i< len ; i++){
            var one_choice = choice_data[i]
            var mapping = {"id":"id","name":"label","instructions":"instructions","paper_type":"paper_type"}
            var instruction_set = multipleMapping(one_choice,mapping);
            instruction_set["type"] = "paper" //specifically setting it to paper.
            $scope.papers.push(instruction_set)
            var subsection_data = getPattern(one_choice,"subsections")
            for(var j= 0, len2 = subsection_data.length; j< len2 ; j++){
                var one_sub_section = subsection_data[0];
                var subsection_mapping = {"id":"id","description":"description","name":"label","subsection_type":"subsection_type","timer":"timer"};
                var subsection_instruction_set = multipleMapping(one_sub_section,subsection_mapping);
                subsection_instruction_set["type"] = "subsection"

                $scope.subsections.push(subsection_instruction_set)

            }
        }

        console.log($scope.papers);
        console.log($scope.subsections)

    },function(){
        console.log("an error occured. Reference #ezy.js.myCtrl controller. The HTTP call to fetch the paper wasn't successful.");
    });


    //$scope.papers = [{id:"P1",name:'Paper1'},{id:"P2",name:'paper2'}]; //list of all papers
    // A lot needs to improve here. On selection of paper, a call need to be made to Django database to fetch the list
    // of all the subsections of that paper. An endpoint should be enabled for that.

    $scope.uploadFile = function(){
        var file = $scope.myFile;
        var selectedOption = $scope.selectedItem;
        var selectedPaper = $scope.select_paper
        console.log("selected paper",selectedPaper)
        var uploadUrl = "/api/v1/fileUpload/";
        console.log("the upload URL " + uploadUrl)
        fileUpload.uploadFileToUrl(file, uploadUrl,selectedOption,selectedPaper);
    };

}]);


// ---------------- *** -------------------
// start of utility Javascript functions.
// these should be in their own file. TODO: v2 feature.
// ---------------- *** -------------------




function multipleMapping(obj,key_value_pairs){
    var return_object = {};
    for(key in key_value_pairs){
        return_object[key] = getPattern(obj,key_value_pairs[key])

    }
    return return_object;
}

function getPattern(obj,pattern){
	try {
        if (typeof obj === 'string') {
            obj = new global.JSON().decode(obj);
        }
        var fieldNames = pattern.split('.');

        var interactiveObject = obj;
        for (var i = 0, len = fieldNames.length; i < len; i++) {
            var fieldName = fieldNames[i] + '';

            var arrayDetected = fieldName.split('[').length == 1 ? false : true;
            if (arrayDetected == true) {
                var lb = fieldName.indexOf('[');
                var rb = fieldName.indexOf(']');
                var arrayIndex = fieldName.slice(++lb, rb);
                var arrayKey = arrayIndex.split("=")[0];
                var arrayValue = arrayIndex.split("=")[1];
                var objectString = fieldName.slice(0, lb - 1);
                interactiveObject = interactiveObject[objectString];
                //iterate and find `name` = given value.
                for (var j = 0, jlen = interactiveObject.length; j < jlen; j++) {
                    if (_.isEmpty(interactiveObject)) {
                        return '';
                    }
                    if (interactiveObject[j][arrayKey] == arrayValue) {


                        break;
                    }
                }
                //global.JSUtil.logObject(interactiveObject,"the final");

            } else {
                //gs.debug("fieldName" + fieldNames[i] + "I" + i);
                if (_.isEmpty(interactiveObject)) {
                    return '';
                }
                interactiveObject = interactiveObject[fieldNames[i]];

            }
        }

        if (typeof interactiveObject === "undefined") return '';

        return interactiveObject;
    }catch(e){
        console.log("error in the get pattern in ezy.js")
    }
}


// -------------- End of Utility functions --------------

//---------------------------------
//thinksetr contents
/*
(function () {
  'use strict';

  angular
    .module('ezy', [
      'ezy.config',
      'ezy.routes',
      'ezy.authentication'
    ]);

  angular
  .module('ezy.config', []);

  angular
    .module('ezy.routes', ['ngRoute']);

})();

//-------------CSRF tokens
/*
angular
  .module('ezy')
  .run(run);

run.$inject = ['$http'];

/**
* @name run
* @desc Update xsrf $http headers to align with Django's defaults

function run($http) {
  $http.defaults.xsrfHeaderName = 'X-CSRFToken';
  $http.defaults.xsrfCookieName = 'csrftoken';
}
*/