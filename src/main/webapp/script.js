function initMap() {
  var foodLocations = [{'position': {lat: 1.326574, lng: 103.814158}, 'desc': 'Mozerella cheeseball' },
                       {'position': {lat: 1.322040, lng: 103.814554}, 'desc': 'Pastries' },
                       {'position': {lat: 1.323561, lng: 103.841931}, 'desc': 'Curry puffs'}];
  const map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 1.3521, lng: 103.8198 },
    zoom: 14
  });

  var marker;
  for (i in foodLocations) {
    marker = new google.maps.Marker({position: foodLocations[i].position, map: map});
    (function(marker, i) {
      // add click event
      google.maps.event.addListener(marker, 'click', function() {
        infowindow = new google.maps.InfoWindow({
            content: foodLocations[i].desc
        });
        infowindow.open(map, marker);
      });
    })(marker, i);
  }
}