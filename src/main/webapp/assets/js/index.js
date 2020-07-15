// default to load 6 product first
const INITIAL_PRODUCT_LOAD = 6;
const INITIAL_DESC_WORDS = 20;

/**
 * Initialize the home page with product listings.
 */
async function initHomePage() {
  const business = await getBusinessData();
  const productsToLoad = Math.min(INITIAL_PRODUCT_LOAD, business.length);
  const productListings = document.getElementById("product-listings");
  let foodLocations = [];
  
  for (let i = 0; i < productsToLoad; i++) {
    productListings.appendChild(homePageListingTemplate(business[i]));
    foodLocations.push(createLocation(business[i].data));
  }
  initMap(foodLocations);
}

async function getBusinessData() {
  let response = await fetch("/business-data");
  let businessData = await response.json();
  return businessData;
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
function homePageListingTemplate(business) {
  const product = business.data;
  const baseImage = product.logoBlobstoreUrl;
  const description = truncateWords(product.description, INITIAL_DESC_WORDS)
  const productListingCard = document.createElement("div");
  productListingCard.className = "product-listing-card";
  productListingCard.innerHTML =
          `<div class='product-listing-image'>
          <a href="/product.html?businessID=${business.id}">
            <img src=${baseImage}>
          </a>
          </div>
          <h3>${product.name}</h3>
          <p class="categories">${product.categories}</p>
          <p class="price">Price: From \$${product.minPrice}</p>
          <p>${description}...</p>
          <p class="rating">Rating: ${product.aggregatedRating}</p>
          <span>
            <button>
              <i class="fa fa-cart-arrow-down"></i>
              <a href=${product.contactUrl}>Contact Business</a>
            </button>
          </span>`
  return productListingCard;
}

/**
 * Truncates a string into a fixed number of words
 */
function truncateWords(str, no_words) {
    return str.split(" ").splice(0, no_words).join(" ");
}
