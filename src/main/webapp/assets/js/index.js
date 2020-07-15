// default to load 6 product first
const INITIAL_PRODUCT_LOAD = 6;
const INITIAL_DESC_WORDS = 20;

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
  var categories = [];
  mockData.forEach(data => {
    categories = [...categories, ...data.categories];
  });

  var categories = new Set(categories);
  var filterButtons = document.getElementById("filter-buttons");
  filterButtons.appendChild(addCategoryFilters('All'));
  for (let category of categories.values()) {
    filterButtons.appendChild(addCategoryFilters(category));
  }
}

/**
 * Add filter buttons in HTML.
 */
function addCategoryFilters(category) {
  let btn = document.createElement('button');
  btn.setAttribute('class', 'btn');
  btn.setAttribute('onclick', `filterCategory('${category}')`)
  btn.innerText = category
  return btn
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

async function fetchUrlData(url) {
  let response = await fetch(url);
  let mockdata = await response.json();
  return mockdata;
}

/**
 * Prevents user from entering an empty search term.
 */
async function handleSearch() {
  let searchTerm = document.getElementById("query").value;

  if (searchTerm == "") {
    alert("Enter a valid search term!");
    return false;
  }

  let param = {'s': searchTerm}
  let searchParams = new URLSearchParams(param).toString();

  let searchData = await fetchUrlData(`/search?${searchParams}`);

  if (searchData.length === 0) {
    alert("No search result found!");
    return false;
  } else {
    document.getElementById("product-listings-title").innerText = `Search Result for "${searchTerm}": `;
    productListings = document.getElementById("product-listings");
    productListings.innerHTML = '';

    for (let i = 0; i < searchData.length; i++) {
      console.log(searchData[i])
      productListings.innerHTML += homePageListingTemplate(searchData[i]);
    }
  }
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
