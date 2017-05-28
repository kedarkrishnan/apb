'use strict';
angular.module('apb').controller('MainController', ['$scope','$log','$window','$http', '$location',
	function($scope,$log,$window,$http,$location){
		var apiUrl ="https://apb.mybluemix.net/api";
		var wsUrl ="wss://apb.mybluemix.net";
		//apiUrl ="http://localhost:8080/api";
		//wsUrl ="ws://localhost:8080";
		
		$log.debug('mainController');		
		$scope.winHeight = $window.innerHeight + "px";
		$scope.alertListBGC = '#ff3300';
		$scope.alertFontColor = '#ffffff';
        $scope.markers = [];
        $scope.alertList = [];
        $scope.responserList = [];
        
        var infoWindow = new google.maps.InfoWindow();
	    var assignTo = [];
	    var originLocation ={} ;
	    var destLocation = {} ; 

	    var icons = {
          User: {
            icon : {
				    url: 'assets/images/User.png', // url				   
				    origin: new google.maps.Point(0,0), // origin
				    anchor: new google.maps.Point(0, 0) // anchor
				}
            
          },

          Responser: {
          	icon : {
				    url: 'assets/images/Responser.png', // url				    
				    origin: new google.maps.Point(0,0), // origin
				    anchor: new google.maps.Point(0,0) // anchor
				}                     
          }
        };
			
		var mapOptions = {
		        zoom: 17,
		        center: new google.maps.LatLng(-36.880774,174.7055693),
		        mapTypeId: google.maps.MapTypeId.ROADMAP,
		        zoomControl: true,
	            zoomControlOptions: {
	                position: google.maps.ControlPosition.LEFT_CENTER
	            },
	    	}
			$scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);
			var directionsService = new google.maps.DirectionsService;
        	var directionsDisplay = new google.maps.DirectionsRenderer({
			      map: $scope.map
			    });
        	//display the route:
	        function calculateAndDisplayRoute(directionsService, directionsDisplay) {
		    	console.log('calculateAndDisplayRoute');
		    	directionsService.route({	        	
		          origin: originLocation,
		          destination: destLocation,
		          travelMode: google.maps.TravelMode.DRIVING
		        }, function(response, status) {
		          if (status === 'OK') {
		            directionsDisplay.setDirections(response);
		          } else {
		            window.alert('Directions request failed due to ' + status);
		          }
		        });
		     }
	
		
		function init(){
			var userId = $location.search().userId ? $location.search().userId : "Admin";						
			var ws = new WebSocket(wsUrl + "/GeoLocationHandler");
            ws.onopen = function(){
	            $log.info("Web Socket is connected to " + wsUrl);
	            ws.send(userId)
            };            
            ws.onmessage = function(message){
                $log.info("Message received from WebSocket");
                var data = message.data.split(",");
                var mark = {
			        userId : data[0],
			        lat : data[1],
			        long : data[2],
			        role : data[3],
                    status: 1
			    }
			   
			    $scope.$apply(function(){
			    	 createMarker(mark);			    	 
			    })                		   
            }                        
		}	    
	    
	    var createMarker = function (info){
	        console.log("info",info);

	        if(checkWhetherUserExist(info.userId)){
	        	checkWhetherUserExist(info.userId).setMap(null);
	        	deletePreviousMarker(info);	        	
	        } 

        	var marker = new google.maps.Marker({
	            map: $scope.map,
	            position: new google.maps.LatLng(info.lat, info.long),
	            title: info.userId,
	            icon: icons[info.role].icon,
	            info: info,
	            optimized: false
		        });	        	        	        
	        
	        $scope.markers.push(marker);
	        if(info.role === 'User'){
	        	$scope.alertList.push(marker);
	        	console.log('alerts List',$scope.alertList);
	        }
	        if(info.role === 'Responser'){
	        	$scope.responserList.push(marker);
	        	console.log('responserList',$scope.responserList);
	        }

	        google.maps.event.addDomListener(marker, 'click', function(){
	        	if(marker.info.role === 'Responser'){
	        		if(!marker.userDetails){
        				getUserDetails(marker.info.userId,marker.info.role).then(function(response){
	        				marker.userDetails = response;	        				
	        				marker.content = responserDetails(marker);	
	        				checkAssignment(infoWindow,marker);
	        				
        				})        				        				        		
        			}else{
        				marker.content = responserDetails(marker);
        				checkAssignment(infoWindow,marker);
        			} 	        			        		
	        	}
	        	
	        	if(marker.info.role === 'User'){
	        		//first time load user
	        		if(!marker.userDetails){
        				getUserDetails(marker.info.userId,marker.info.role).then(function(response){
	        				marker.userDetails = response;
	        				marker.content = layoutMedicalDetails(marker);
	        				checkResponserAssignment(marker,infoWindow);	        				
        				})
        			} else{
        				marker.content = layoutMedicalDetails(marker);
	        			checkResponserAssignment(marker,infoWindow);	        				
        			}     		
	        	}	            
	        });	        	        
	    } 
	    	    
	    function checkResponserAssignment(marker,infoWindow){
	    	// The "assign to" button has been clicked:
			if(assignTo.length > 0){
				if(marker.info.responser){
					alert("Already assigned!");
				}else{
					assignTo.push(marker);
					console.log('assignToEnd',assignTo);
					marker.info.responser = assignTo[0];
					assignTo[0].info.assignTo = assignTo[1];
					
					sendAssignToServer(assignTo[0],assignTo[1]);
					assignTo = [];
					$scope.$apply(function(){
						marker.info.status = 0;
						$scope.alertListBGC = '#ff9800';
						$scope.alertFontColor = '#ffffff';	
					})					
				}				
			} 
			// The "assign to" button hasn't been clicked:	
			if(assignTo.length === 0 && marker.info.responser){
				console.log('NoAssignTo');
				originLocation = marker.position;
				marker.content += '<div><hr><h4><img class="icon-responser" src="assets/images/Responser.png"></img><a id="setRoute1">' + marker.info.responser.info.userId + '</a></h4></div>';
				displayContent(infoWindow,marker);
				var setRoute1 = document.getElementById('setRoute1');
    			setRoute1.addEventListener('click',function(){
    				destLocation = marker.info.responser.position;
    				calculateAndDisplayRoute(directionsService, directionsDisplay);
    				infoWindow.close();
    			})
			}else{
				displayContent(infoWindow,marker);
			}
			
	    }

	    function checkAssignment(infoWindow,marker){
	    	if(!marker.info.assignTo){
    			marker.content += '<div></br><button class="btn btn-assign" id="assignBtn" >Assign To</button></div>';
    			displayContent(infoWindow,marker);
    			var assignResponserTo = document.getElementById('assignBtn');        				
				assignResponserTo.addEventListener('click',function(){
		        	assignTo = [];
		    		assignTo.push(marker);
		    		console.log('assignTo',assignTo);
		    		infoWindow.close();
		        })

    		}

    		if(marker.info.assignTo){
    			originLocation = marker.position;
    			marker.content += '<div><hr><h4><img class="icon-user" src="assets/images/User.png"></img><a id="setRoute2">' + marker.info.assignTo.info.userId + '</a></h4></div>';
    			displayContent(infoWindow,marker);
    			var setRoute2 = document.getElementById('setRoute2');
    			setRoute2.addEventListener('click',function(){
    				destLocation = marker.info.assignTo.position;
    				calculateAndDisplayRoute(directionsService, directionsDisplay);
    				infoWindow.close();
    			})
    		}
	    }

	    var checkWhetherUserExist = function(userId){
	    	var result = $scope.markers.find(function(marker){
    			return marker.info.userId == userId;
			});
			return result;
	    }

	    var deletePreviousMarker = function(info){
	    	console.log('deletemarker');
	    	var index = $scope.markers.indexOf(function(marker){
    			return marker.info.userId == info.userId;
			});
			$scope.markers.splice(index,1);

			if(info.role == 'User'){
				var index1 = $scope.alertList.indexOf(function(marker){
    			return marker.info.userId == userId;
				});
				$scope.alertList.splice(index1,1);				
			}
			
			if(info.role == 'Responser'){
				var index2 = $scope.responserList.indexOf(function(marker){
    			return marker.info.userId == userId;
				});
				$scope.responserList.splice(index2,1);				
			}			
	    } 	    

	    var  getUserDetails = function(id,type){
	    	$log.info("get Details for ",type,id);
	    	var by = type==='Responser'?"/id":"";
	    	return $http.get(apiUrl + '/' + type.toLowerCase() + by + '/' + id)
	    			.then(function(response){
	    				$log.info("Details ",response);
	    				return response.data;
	    			})
	    }

	    var layoutMedicalDetails = function(marker){
	    	var content = "<div>"
	    	content += "<b>Medical Details</b>";
	    	content += "<p class='medicalDetails'>" + marker.userDetails.medicalDetails + "</p>";
	    	content += "<hr>";
	    	content += "<b>Emergency Contact Details</b>";	    	
	    	content += "<ul class='emergencyContact'>";
	    	var emergencyContact = marker.userDetails.emergencyContact[0];
			content += "<li>Contact Name: " + emergencyContact.contactName + "</li>";
			content += "<li>Phone: " + emergencyContact.phone + "</li>";
			content += "<li>Relation: " + emergencyContact.relation + "</li>";
	    	content += "</ul>";
	    	content += '<div>';
	    	return content;
	    }

	    var responserDetails = function(marker){
	    	var content = "<div>"
	    	content += "Vehical Number: <b>" + marker.userDetails.vehicalNumber + "</b>";
	    	content += '<div>';
	    	return content;
	    }

	    var displayContent = function(infoWindow,marker){	    	
	    	var markerDetails = '<div class="markerDetails">'
	    	markerDetails += '<h3 id="userId">'; 
	    	if(marker.userDetails){
	    		if(marker.info.role==='User'){
	    			markerDetails += marker.userDetails.userName; 	    		
	    		}else{
	    			markerDetails += marker.userDetails.responserName; 	    		
	    		}
	    	}	    		
	    	markerDetails += "<small> (" + marker.title + ")</small></h3>";
	    	markerDetails += marker.content + '</div>';

	    	infoWindow.setContent(markerDetails);
	        infoWindow.open($scope.map, marker);
	    }
	    
	    var sendAssignToServer = function(responser,user){
	    	$log.debug('sendAssignToServer');
	    	return $http.post( apiUrl + '/geolocation/tag/' + responser.info.userId + '?userId=' + user.info.userId + '&lat=' + user.info.lat + '&lng=' + user.info.long)

	    }

	    $scope.hasResponser = function(){
	    	 if($scope.responserList.length > 0){
	    	 	return true;
	    	 }else{
	    	 	return false;
	    	 }
	    }

	    $scope.openInfoWindow = function(selectedMarker){
	    	console.log('openInfoWindow',selectedMarker);
	        google.maps.event.trigger(selectedMarker, 'click');
	    }	    

	    init();

	    var testUrl = function(){
	    	return $http.post(apiUrl + '/responser');
	    }


  }
])