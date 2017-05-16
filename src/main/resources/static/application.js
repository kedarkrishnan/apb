'use strict';

var applicationName = "apb";
var app = angular.module(applicationName,['ngRoute','mgcrea.ngStrap']);

app.config(['$logProvider','$routeProvider',
	function($logProvider,$routeProvider) {
		$logProvider.debugEnabled(true);
				
		$routeProvider
		.when('/', {
		    controller: 'MainController',
		    templateUrl: 'index.html'
		})
		.otherwise({
		    redirectTo: '/'
		});
	}
]);

angular.element(document).ready(function(){
	angular.bootstrap(document,[applicationName]);
});


