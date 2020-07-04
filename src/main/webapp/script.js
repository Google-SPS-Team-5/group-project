function initMap() {
  var foodLocations = { 'mozerella': {lat: 1.326574, lng: 103.814158},
                        'pastries': {lat: 1.322040, lng: 103.814554},
                        'curry puffs': {lat: 1.323561, lng: 103.841931} };
  const map = new google.maps.Map(document.getElementById("map"), {
    center: { lat: 1.3521, lng: 103.8198 },
    zoom: 14
  });

  var marker;
  for (const [key, value] of Object.entries(foodLocations)) {
    marker = new google.maps.Marker({position: value, map: map});
    (function(marker, key) {
      // add click event
      google.maps.event.addListener(marker, 'click', function() {
        infowindow = new google.maps.InfoWindow({
            content: key
        });
        infowindow.open(map, marker);
      });
    })(marker, key);
  }
}