

/**
* Authentication
* @namespace ezy.authentication.services
*/
//substituted 'thinkster' with 'ezy'

  angular
    .module('ezy.authentication.services',[ 'ngCookies'])
    .factory('Authentication', Authentication);

 //since angular 1.4 cookies have to use only get, put ,remove methods. direct access does not work!
  Authentication.$inject = ['$cookies', '$http'];

  /**
  * @namespace Authentication
  * @returns {Factory}
  */
  function Authentication($cookies, $http) {
    /**
    * @name Authentication
    * @desc The Factory to be returned
    */
    var Authentication = {
      getAuthenticatedAccount: getAuthenticatedAccount,
      isAuthenticated: isAuthenticated,
      login: login,
      logout: logout,
      register: register,
      setAuthenticatedAccount: setAuthenticatedAccount,
      unauthenticate: unauthenticate //pycharm its a spell mistake
    };

    return Authentication;

    ////////////////////

    /**
    * @name register
    * @desc Try to register a new user
    * @param {string} username The username entered by the user
    * @param {string} password The password entered by the user
    * @param {string} email The email entered by the user
    * @returns {Promise}
    * @memberOf thinkster.authentication.services.Authentication
    */
    function register(email, password, username, first_name, last_name) {
      return $http.post('/api/v1/accounts/', {
        username: username,
        password: password,
        email: email,
        first_name: first_name,
        last_name: last_name
      }).then(registerSuccessFn, registerErrorFn);

      /**
  * @name registerSuccessFn
  * @desc Log the new user in
  */
        function registerSuccessFn(data, status, headers, config) {
         // alert("reg success");
           Authentication.login(username, password);
       }

  /**
  * @name registerErrorFn
  * @desc Log "Epic failure!" to the console
  */
          function registerErrorFn(data, status, headers, config) {
           alert("reg failed");
           console.error('Epic failure!');
             }

    } //register

    /**
 * @name login
 * @desc Try to log in with email `email` and password `password`
 * @param {string} email The email entered by the user
 * @param {string} password The password entered by the user
 * @returns {Promise}
 * @memberOf thinkster.authentication.services.Authentication
 */
  function login(username, password) {
  //alert("from auth login()");
  return $http.post('/api/v1/auth/login/', {
    username: username, password: password
  }).then(loginSuccessFn, loginErrorFn);

  /**
   * @name loginSuccessFn
   * @desc Set the authenticated account and redirect to index
   */
  function loginSuccessFn(data, status, headers, config) {
  //  alert("login success!");
    Authentication.setAuthenticatedAccount(data.data);

    window.location = '/';
  }

  /**
   * @name loginErrorFn
   * @desc Log "Epic failure!" to the console
   */
  function loginErrorFn(data, status, headers, config) {
    alert("login FAILED!")  //nothing happens after this. We need to take care of this case.
    console.error('Epic failure!');
  }

  }//login

  /**
 * @name getAuthenticatedAccount
 * @desc Return the currently authenticated account
 * @returns {object|undefined} Account if authenticated, else `undefined`
 * @memberOf thinkster.authentication.services.Authentication
 */
function getAuthenticatedAccount() {
  if (!$cookies.get('authenticatedAccount')) {
    return;
  }

  return JSON.parse($cookies.get('authenticatedAccount'));
}

/**
 * @name isAuthenticated
 * @desc Check if the current user is authenticated
 * @returns {boolean} True is user is authenticated, else false.
 * @memberOf thinkster.authentication.services.Authentication
 */
 //since angular 1.4 cookies have to use only get, put ,remove methods. direct access does not work!
function isAuthenticated() {
  return !!$cookies.get('authenticatedAccount');
}

/**
 * @name setAuthenticatedAccount
 * @desc Stringify the account object and store it in a cookie
 * @param {Object} user The account object to be stored
 * @returns {undefined}
 * @memberOf thinkster.authentication.services.Authentication
 */
 //since angular 1.4 cookies have to use only get, put ,remove methods. direct access does not work!
function setAuthenticatedAccount(account) {
  //$cookies.authenticatedAccount = JSON.stringify(account);
   $cookies.put('authenticatedAccount',JSON.stringify(account));
}

 /**
 * @name unauthenticate
 * @desc Delete the cookie where the user object is stored
 * @returns {undefined}
 * @memberOf thinkster.authentication.services.Authentication
 */
  function unauthenticate() {
     delete $cookies.remove('authenticatedAccount');
  }

  /**
 * @name logout
 * @desc Try to log the user out
 * @returns {Promise}
 * @memberOf thinkster.authentication.services.Authentication
 */
function logout() {
  return $http.post('/api/v1/auth/logout/')
    .then(logoutSuccessFn, logoutErrorFn);

  /**
   * @name logoutSuccessFn
   * @desc Unauthenticate and redirect to index with page reload
   */
  function logoutSuccessFn(data, status, headers, config) {
    Authentication.unauthenticate();
   // alert("logout success");
    window.location = '/';
  }

  /**
   * @name logoutErrorFn
   * @desc Log "Epic failure!" to the console
   */
  function logoutErrorFn(data, status, headers, config) {
    alert("logout failed!");
    console.error('Epic failure!');
     }
    }

  }//Authentication
