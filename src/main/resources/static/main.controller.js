'use strict';
angular.module('apb').controller('MainController', ['$scope','$log','$window',
	function($scope,$log,$window){
		$log.debug('mainController');
		$scope.aside = {title: 'Title', content: 'Aside'};
        $scope.alertList = [];
        $scope.winHeight = $window.innerHeight + "px";
		function init(){
			var userId = "Admin";
			var ws = new WebSocket("wss://" + window.location.host + "/GeoLocationHandler");
            ws.onopen = function(){
              console.log("Web Socket is connected");
              ws.send(userId);
            };            

            ws.onmessage = function(message){
                console.log("Message received from WebSocket");
                console.log(message);
                var data = message.data.split(",");
                var mark = {
			        userId : data[0],
			        lat : data[1],
			        long : data[2],
			        role : data[3],
                    status: 1
			    }
			    createMarker(mark);
                $scope.alertList.add(mark);

            }
		}


		//Data
	var locations=
	    [{
	        userName : 'PAKnSAVE',
	        desc : 'This is a supermarket.',
	        lat : -36.8767,
	        long : 174.6328,
	        role : 'user'
	    },
	    {
	        userName : 'Henderson High School',
	        desc : 'This is a school.',
	        lat : -36.8835,
	        long : 174.6280,
	        role : 'user'
	    },
	    {
	        userName : 'Waitakere Hospital',
	        desc : 'This is a hospital.',
	        lat : -36.8693,
	        long : 174.6299,
	        role : 'ambulance'
	    },
	    {
	        userName : 'Westpac',
	        lat : -36.8798,
	        long : 174.6325,
	        role : 'ambulance'
	    }
	];

	var icons = {
      User: {
        icon: 'assets/images/user.png'
      },
      Responser: {
        icon: 'assets/images/ambulance.png'
      }
    };

    var mapOptions = {
        zoom: 14,
        center: new google.maps.LatLng(-36.8788, 174.6320),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }

    $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

    $scope.markers = [];
    
    var infoWindow = new google.maps.InfoWindow();
    var i;
    
    var createMarker = function (info){
        console.log(info);
        var marker = new google.maps.Marker({
            map: $scope.map,
            position: new google.maps.LatLng(info.lat, info.long),
            title: info.userId,
            icon: icons[info.role].icon,
         });
        marker.content = '';	        
        
        google.maps.event.addListener(marker, 'click', function(){
            if(marker.info.medicalHistory === ''){ // check if user
                //make server call to get the medical details;
                //marker.info.medicalHistory = data;
            }
            //if ambulance show button

            infoWindow.setContent(setInfoContent(info));
            infoWindow.open($scope.map, marker);
        });
        
        $scope.markers.push(marker);
        
    }  


    function setInfoContent(info){
        var text = '<h2>' + info.userId + '</h2>';
        //if medicalHistory is present add medicalHistory in a table
        //if responser is present add responser in a div

        return text;
    }
    
    // for (i = 0; i < locations.length; i++){
    //     createMarker(locations[i]);
    // }

    $scope.openInfoWindow = function(e, selectedMarker){
        e.preventDefault();
        google.maps.event.trigger(selectedMarker, 'click');
    }

	init();
  }
])