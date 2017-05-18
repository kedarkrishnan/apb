'use strict';

var app = angular.module('apb',['ngRoute','mgcrea.ngStrap']);

app.config(['$httpProvider','$logProvider','$routeProvider',
	function($httpProvider,$logProvider,$routeProvider) {
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
])


