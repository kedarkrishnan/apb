'use strict';
angular.module('apb').controller('MainController', ['$scope','$log','$window','$http', '$location',
	function($scope,$log,$window,$http,$location){
		$log.debug('mainController');
		
		$scope.winHeight = $window.innerHeight + "px";
		$scope.alertListBGC = '#ff3300';
		$scope.alertFontColor = '#ffffff';
        $scope.markers = [];
        $scope.alertList = [];
        
        var infoWindow = new google.maps.InfoWindow();
	    var assignTo = [];

	    var icons = {
          User: {

            icon : {
				    url: 'assets/images/User.jpeg', // url
				   
				    origin: new google.maps.Point(0,0), // origin
				    anchor: new google.maps.Point(0, 0) // anchor
				}
            
          },

          Responser: {

          	icon : {
				    url: 'assets/images/Responser.png', // url
				    
				    origin: new google.maps.Point(0,0), // origin
				    anchor: new google.maps.Point(0, 0) // anchor
				}                     
          }
        };
		
		
		function init(){
			var userId = $location.search().userId ? $location.search().userId : "Admin";
			var mapOptions = {
		        zoom: 14,
		        center: new google.maps.LatLng(-36.8385546, 174.5709301),
		        mapTypeId: google.maps.MapTypeId.ROADMAP
	    	}

			$scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);	    	  
			var ws = new WebSocket("wss://apb.mybluemix.net/GeoLocationHandler");
            ws.onopen = function(){
	            console.log("Web Socket is connected");
	            ws.send(userId)
            };            

            ws.onmessage = function(message){
                console.log("Message received from WebSocket");
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
                console.log("markers",$scope.markers);			   
            }          
               
		}	    
	    
	    var createMarker = function (info){
	        console.log("info",info);

	        if(checkWhetherUserExist(info.userId)){
	        	checkWhetherUserExist(info.userId).setMap(null);
	        	deletePreviousMarker(info.userId);	        	
	        } 

        	var marker = new google.maps.Marker({
            map: $scope.map,
            position: new google.maps.LatLng(info.lat, info.long),
            title: info.userId,
            icon: icons[info.role].icon,
            info: info
	        });	        	        	        
	        
	        $scope.markers.push(marker);
	        if(info.role === 'User'){
	        	$scope.alertList.push(marker);
	        	console.log('$scope.alertList',$scope.alertList);
	        }

	        

	        google.maps.event.addListener(marker, 'click', function(){

	        	if(marker.info.role === 'Responser'){
	        		if(marker.info.status == 1){
	        			marker.content = '<div></br><button class="btn btn-danger" id="assignBtn" >Assign To</button></div>';
	        			displayContent(infoWindow,marker);

	        			var assignResponserTo = document.getElementById('assignBtn');        				
        				assignResponserTo.addEventListener('click',function(){
				        	assignTo = [];
				    		assignTo.push(marker.info.userId);
				    		console.log('assignTo',assignTo);
				        })

	        		}

	        		if(marker.info.status == 2){
	        			marker.content = '<div></br><h3>Assigned To: ' + marker.info.assignTo + '</h3></div>';
	        			displayContent(infoWindow,marker);

	        		}
	        		
	        	}
	        	
	        	if(marker.info.role === 'User'){
	        		//first time load user
	        		if(!marker.userDetails){
	        			getUserDetails(marker.info.userId).then(function(response){
	        				marker.userDetails = response;
	        				marker.content = layoutMedicalDetails(marker);
	        				displayContent(infoWindow,marker);
	        				})
	        			} 
	        				
    				// The "assign to" button has been clicked:
    				if(assignTo.length > 0){
    					if(marker.info.responderId){
    						alert("This user is already assigned!");
    					}else{
    						assignTo.push(marker.info.userId);
    						console.log('assignToEnd',assignTo);
    						getUserDetails(marker.info.userId).then(function(){
    							//add responserId to the user
    							marker.info.responserId = assignTo[0];
    							// add assignTo to the responser
    							var responser = $scope.markers.find(function(marker){
    								return marker.info.userId == assignTo[0];
    							})
    							responser.info.assignTo = assignTo[1];
    							responser.info.status = 2;

    							assignTo = [];
    							$scope.alertListBGC = '#f2f2f2';
    							$scope.alertFontColor = '#003cb3';
    							console.log('markers',$scope.markers);
    							
    						})
    					}
    				} 
    				// The "assign to" button hasn't been clicked:	
    				if(assignTo.length == 0){
    					console.log('NoAssignTo');
    					//the user is already assigned
    					if(marker.info.responserId){
    						if(!marker.content.includes('Assigned'))
		        				marker.content += '<div></br><h4>Assigned To: <strong>' + marker.info.responserId + '</strong></h4></div>';
		        			} 
    				}
	        			displayContent(infoWindow,marker);	        		
	        	}	            
	        });
	        
	        
	        
	    } 

	    var checkWhetherUserExist = function(userId){
	    	var result = $scope.markers.find(function(marker){
    			return marker.info.userId == userId;
			});
			return result;
	    }

	    var deletePreviousMarker = function(userId){
	    	var index = $scope.markers.indexOf(function(marker){
    			return marker.info.userId == userId;
			});
			$scope.markers.splice(index,1);
	    } 	    

	   var  getUserDetails = function(userId){
	    	console.log("getUserDetails");
	    	return $http.get('https://apb.mybluemix.net/api/user/' + userId)
	    			.then(function(response){
	    				return response.data;
	    			})
	    }

	    var layoutMedicalDetails = function(marker){
	    	var content = '<table class="table table-bordered"><tbody>';
	    	marker.userDetails.medicalDetails.forEach(function(detail){
	    		content += '<tr><td>' + detail.detailType + '</td><td>' + detail.detailValue +'</td></tr>';
	    	})
	    	content += '</tbody></table>';
	    	return content;
	    }

	    var displayContent = function(infoWindow,marker){

	    	infoWindow.setContent('<h2>' + marker.title + '</h2>' + marker.content);
	        infoWindow.open($scope.map, marker);
	    }
	    
	    var sendAssignToServer = function(assignTo,info){
	    	console.log('sendAssignToServer');
	    	return $http.post('http://apb.mybluemix.net/api/geolocation/tag/' + assignTo[0] + '?userId=' + assignTo[1] + '&lat=' + info.lat + '&lng=' + info.long)

	    }

	    $scope.openInfoWindow = function(selectedMarker){
	    	console.log('openInfoWindow',selectedMarker);
	        google.maps.event.trigger(selectedMarker, 'click');
	    }

	    init();

	    var testUrl = function(){
	    	return $http.post('https://apb.mybluemix.net/api/responser');
	    }


  }
])