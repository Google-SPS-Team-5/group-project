// default to load 6 product first
const INITIAL_PRODUCT_LOAD = 6
const INITIAL_DESC_WORDS = 20

async function getMultipleMockData() {
  let response = await fetch('/multiplemockdatabusiness');
  let mockdata = await response.json();
  return mockdata;
}

/**
 * Initialize the home page with product listings.
 */
async function initHomePage() {
  const mockData = await getMultipleMockData();
  const productsToLoad = Math.min(INITIAL_PRODUCT_LOAD, mockData.length);

  getFilterCategories(mockData)

  let foodLocations = [];

  for (let i = 0; i < productsToLoad; i++) {
    document.getElementById("product-listings").innerHTML += homePageListingTemplate(mockData[i]);
    foodLocations.push(createLocation(mockData[i]));
  }
  initMap(foodLocations);
}

/**
 * Obtain a list of filter categories to allow product listings to be filtered accordingly.
 */
async function getFilterCategories(mockData) {
  var categories = []
  mockData.forEach(data => {
    categories = [...categories, ...data.categories]
  });

  var categories = new Set(categories)
  document.getElementById("filter-buttons").innerHTML += addCategoryFilters('All');
  for (let category of categories.values()) {
    document.getElementById("filter-buttons").innerHTML += addCategoryFilters(category);
  }
}

/**
 * Add filter buttons in HTML.
 */
function addCategoryFilters(category) {
  return `<button class="btn" onclick="filterCategory('${category}')">${category}</button>`
}

/**
 * Filter product listings based on the category selected.
 */
function filterCategory(category) {
  let products = document.getElementsByClassName("product-listing-card");

  for (let i = 0; i < products.length; i++) {
    existingCategories = products[i].getElementsByClassName('categories')[0].innerHTML.split(",")
    if (existingCategories.includes(category) || category == "All") {
      products[i].style.display = "";
    } else {
      products[i].style.display = "none";
    }
  }
}

/**
 * Prevents user from entering an empty search term.
 */
function isEmpty() {
  var x;
  x = document.getElementById("query").value;
  if (x == "") {
    alert("Enter a valid search term!");
    return false;
  };
  return true;
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
  const description = truncateWords(product.description, INITIAL_DESC_WORDS)
  return `<div class="product-listing-card">
            <div class='product-listing-image'>
            <img src=${baseImage}>
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
            </span>
          </div>
          `;
}

/**
 * Truncates a string into a fixed number of words
 */
function truncateWords(str, no_words) {
    return str.split(" ").splice(0, no_words).join(" ");
}
