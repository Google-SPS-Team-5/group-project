// default to load 6 product first
const INITIAL_PRODUCT_LOAD = 6

async function getMultipleMockData() {
  let response = await fetch('/multiplemockdata');
  let mockdata = await response.json();
  return mockdata;
}

/**
 * Initialize the home page with product listings.
 */
async function initHomePage() {
  const mockData = await getMultipleMockData();
  const productsToLoad = Math.min(INITIAL_PRODUCT_LOAD, mockData.length);
  let foodLocations = []

  for (let i = 0; i < productsToLoad; i++) {
    document.getElementById("productListings").innerHTML += homePageListingTemplate(mockData[i]);
    foodLocations.push(createLocation(mockData[i]))
  }
  initMap(foodLocations)
}

/**
 * Creates the product listing location for google map marker.
 */
function createLocation(product) {
  return {
            "position": {
              lat: product.addressLat,
              lng: product.addressLng
            },
            "desc": product.name
          };
}

/**
 * Initialize the google map with markers.
 */
function initMap(foodLocations) {
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

/**
 * Returns a product listing on the home page.
 */
function homePageListingTemplate(product) {
  const baseImage = product.photoBlobstoreUrlList.length === 0 ? "" : product.photoBlobstoreUrlList[0];
  return `<div class="product-listing-card">
            <div class='product-listing-image'>
            <img src=${baseImage}>
            </div>
            <h3>${product.name}</h3>
            <p class="categories">${product.categories}</p>
            <p class="price">Price undefined</p>
            <p>${product.description}</p>
            <p class="delivery">${product.orderInformation}</p>
            <p class="rating">Rating: ${product.aggregatedRating}</p>
            <span>
              <button>
                <i class="fa fa-cart-arrow-down"></i>
                <a href=${product.contactUrl}>Contact Business</a>
              </button>
            </span>
          </div>
          `;
}