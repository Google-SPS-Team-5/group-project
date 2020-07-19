// default to load 6 product first
const INITIAL_PRODUCT_LOAD = 8;
const INITIAL_DESC_WORDS = 20;

async function getBusinessData() {
    let response = await fetch("/business-data");
    let businessData = await response.json();
    return businessData;
}

async function fetchUrlData(url) {
    let response = await fetch(url);
    let mockdata = await response.json();
    return mockdata;
}

/**
 * Initialize the home page with product listings.
 */
async function initHomePage() {
    const business = await getBusinessData();
    const productsToLoad = Math.min(INITIAL_PRODUCT_LOAD, business.length);
    const productListings = document.getElementById("product-listings");

    getFilterCategories(business);

    let foodLocations = [];

    for (let i = 0; i < productsToLoad; i++) {
        productListings.appendChild(homePageListingTemplate(business[i]));
        foodLocations.push(createLocation(business[i].data));
    }
    initMap(foodLocations);
}

/**
 * Obtain a list of filter categories to allow product listings to be filtered accordingly.
 */
async function getFilterCategories(business) {
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
 * Prevents user from entering an empty search term.
 */
async function handleSearch() {
    let searchTerm = document.getElementById("query").value;

    if (searchTerm == "") {
        alert("Enter a valid search term!");
        return false;
    }

    let param = { 's': searchTerm }
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
            productListings.appendChild(homePageListingTemplate(searchData[i]));
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
