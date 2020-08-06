// default to load 8 product first
const INITIAL_PRODUCT_LOAD = 8;
const INITIAL_DESC_WORDS = 20;
var products;
var categorySelected = "All"
var numProductsLoaded = 0

async function getBusinessData() {
  let response = await fetch("/business-data");
  let businessData = await response.json();
  return businessData;
}

/**
 * Initialize the home page with product listings.
 */
async function initHomePage() {
  let businesses = await getBusinessData();
  loadCategories(businesses);
  let foodLocations = [];

  // Load all products
  const productListings = document.getElementById("product-listings");
  for (let i = 0; i < businesses.length; i++) {
    productListings.appendChild(homePageListingTemplate(businesses[i]));
    foodLocations.push(createLocation(businesses[i].data));
  }
  initMap(foodLocations);

  // Hide products more than the specified limit
  numProductsLoaded = Math.min(INITIAL_PRODUCT_LOAD, businesses.length);
  products = document.getElementsByClassName("product-listing-card");
  if (numProductsLoaded >= products.length) {
    document.getElementById("loadMore").innerHTML = "";
  } else {
    for (i = numProductsLoaded; i < products.length; i++) {
      products[i].style.display = "none";
    }
  }
}

/**
 * Show more products.
 */
function loadMoreProducts() {
  const newProductsToLoad = Math.min(numProductsLoaded + INITIAL_PRODUCT_LOAD, products.length);
  for (; numProductsLoaded < newProductsToLoad; numProductsLoaded++) {
    products[numProductsLoaded].style.display = "";
  }
  if (numProductsLoaded == products.length) {
    document.getElementById("loadMore").style.display = "none";
  }
}

/**
 * Obtain a list of filter categories to allow product listings to be filtered accordingly.
 */
function loadCategories(business) {
  var categories = [];
  for (let i = 0; i < business.length; i++) {
    categories = [...categories, ...business[i].data.categories];
  }

  var categories = new Set(categories);
  var filterButtons = document.getElementById("filter-buttons");
  filterButtons.appendChild(addCategoryFilters('All', true));
  for (let category of categories.values()) {
    filterButtons.appendChild(addCategoryFilters(category), false);
  }
}

/**
 * Add filter buttons in HTML.
 */
function addCategoryFilters(category, active) {
  let btn = document.createElement('button');
  btn.className = "filter-btn";
  btn.setAttribute('id', `filter-btn-${category}`);
  btn.setAttribute('onclick', `filterCategory('${category}')`);
  if (active) {
    btn.className += " active"
  }
  btn.innerText = category;
  return btn;
}

/**
 * Filter product listings based on the category selected.
 */
function filterCategory(category) {
  categorySelected = category
  // show products and hide map
  document.getElementById("product-listings").style.display = "grid";
  document.getElementById("map-container").style.display = "none";

  // set active classes for the tabs so they can change color
  tablinks = document.getElementsByClassName("filter-btn");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(`filter-btn-${category}`).className += " active";

  // filter for products
  let fullProducts = document.getElementsByClassName("product-listing-card");
  var productsToLoad = []

  for (let i = 0; i < fullProducts.length; i++) {
    let existingCategories = [];
    categoryPills = fullProducts[i].getElementsByClassName('category-pill');
    for (let i = 0; i < categoryPills.length; i++) {
      existingCategories.push(categoryPills[i].innerHTML);
    }
    // only show listings within the category
    if (existingCategories.includes(categorySelected) || categorySelected == "All") {
      productsToLoad.push(fullProducts[i])
    } else {
      fullProducts[i].style.display = "none";
    }
  }

  // show initial products and hide the others
  numProductsLoaded = 0;
  for (let i = 0; i < productsToLoad.length; i++) {
    if (numProductsLoaded < INITIAL_PRODUCT_LOAD) {
      productsToLoad[i].style.display = "";
      numProductsLoaded++;
    } else {
      productsToLoad[i].style.display = "none";
    }
  }

  // show load more button
  if (numProductsLoaded < productsToLoad.length) {
    document.getElementById("loadMore").style.display = "";
  }
  products = productsToLoad
}

/** Shows map tab and hide products
 */

function showMapTab() {
  // set active classes for the tabs so they can change color
  tablinks = document.getElementsByClassName("filter-btn");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById("map-btn").className += " active";

  // hide products and show map
  document.getElementById("product-listings").style.display = "none";
  document.getElementById("map-container").style.display = "block";
}

/**
 * Search businesses names based on search term.
 */
function searchBusinesses(products, searchTerms) {

  let searchedData = new Set();
  for (let i = 0; i < searchTerms.length; i++) {
    for (let j = 0; j < products.length; j++) {
      if (products[j].getElementsByTagName("h3")[0].innerText.toLowerCase().includes(searchTerms[i].toLowerCase())) {
        searchedData.add(products[j]);
      }
    }
  }
  return searchedData;
}

/**
 * Prevents user from entering an empty search term.
 */
async function handleSearch() {
  let searchTerms = document.getElementById("query").value;

  if (searchTerms == "") {
    alert("Enter a valid search term!");
    return false;
  }

  let productsToSearch = [];
  if (categorySelected == "All") {
    productsToSearch = products;
  } else {
    for (let product of products) {
      let existingCategories = [];
      categoryPills = product.getElementsByClassName('category-pill');
      for (let i = 0; i < categoryPills.length; i++) {
        existingCategories.push(categoryPills[i].innerHTML);
      }
      if (existingCategories.includes(categorySelected)) {
        productsToSearch.push(product);
      }
    }
  }

  // uses the initial businesses data retrieved when the page is loaded
  let searchData = searchBusinesses(productsToSearch, searchTerms.split(" "));
  let notFoundResult = new Set([...productsToSearch].filter(x => !searchData.has(x)));

  if (searchData.size === 0) {
    alert(`No search result found within the products in ${categorySelected} category!`);
    return false;
  } else {
    document.getElementById("product-listings-title").innerText = `Search Result for "${searchTerms}": `;
    for (let result of searchData.values()) {
      result.style.display = "";
    }
    for (let result of notFoundResult.values()) {
      result.style.display = "none";
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
    center: { lat: 1.294015, lng: 103.842999 },
    zoom: 13
  });

  var marker;
  for (i in foodLocations) {
    marker = new google.maps.Marker({ position: foodLocations[i].position, map: map });
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

  let stars = createStars(product.aggregatedRating);
  let subtitle = createListingSubtitle(product.categories, product.minPrice);

  const productListingCard = document.createElement("div");
  productListingCard.className = "product-listing-card";
  productListingCard.innerHTML =
          `<div class='product-listing-image' style="position: relative">
            <a href="/product.html?businessID=${business.id}">
              <img style="height: 300px; object-fit: cover;" src=${baseImage}>
            </a>
            <div class="rating" style="position: absolute; top: 5%; right: 5%;">${stars}</div>
          </div>
          <a href="/product.html?businessID=${business.id}">
            <h3 class="listing-name">${product.name}</h3>
          </a>
          <p style="margin: 0 2rem;">${description}${description != '' ? '...' : ''}<p>
          <div style="margin: 2rem;">${subtitle}</div>`
    return productListingCard;
}

function createListingSubtitle(categories, minPrice) {
  var categoriesHTML = createCategoryPills(categories);
  var minPriceHTML = minPrice == 404 ? '' : `From: \$${minPrice}`;
  var divider = categoriesHTML == '' || minPriceHTML == '' ? '' : ' | ';
  return `<p>${categoriesHTML}${divider}${minPriceHTML}</p>`;
}

function createCategoryPills(categories) {
  var categoryPills = '';
  for (let i = 0; i < categories.length; i++) {
    categoryPills += `<span class="category-pill">${categories[i]}</span>`;
  }
  return categoryPills;
}

function createStars(rating) {
  var starHTML = '';

  if (rating == 404) {
    rating = 0;
  }
  for (let i = 0; i < 5; i++) {
    if (i < rating) {
      starHTML += '<i class="fas fa-star yellow-star medium-star"></i>';
    } else {
      starHTML += '<i class="fas fa-star gray-star medium-star"></i>';
    }
  }
  return starHTML;
}

/**
 * Truncates a string into a fixed number of words
 */
function truncateWords(str, no_words) {
  return str.split(" ").splice(0, no_words).join(" ");
}
