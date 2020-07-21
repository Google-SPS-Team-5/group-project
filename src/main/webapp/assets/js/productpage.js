async function populateProductDetails() {  
  const response = await fetch("/authentication");
  const userJson = await response.json();
  createEditBusinessLink(userJson.isAdmin);

  business = await getBusinessData();

  populateBusinessDescription(business);
  populateImageGallery(business.photoBlobstoreUrlList);
  populateBusinessWriteup(business);
  populateContactDetails(business);
  
}

async function getBusinessData() {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  if (urlParams.has("businessID")) {
    var businessId = urlParams.get("businessID");
    let response = await fetch(`/edit-business-data?businessID=${businessId}`);
    let businessData = await response.json();
    return businessData.data;
  } else {
    let response = await fetch('/mockdatabusiness');
    let mockdata = await response.json();
    return mockdata;
  }
}

function populateBusinessDescription(business) {
  Array.from(document.getElementsByClassName('businessName')).forEach(element => element.innerHTML += business.name);
  document.getElementById("review").placeholder = `What do you like about ${business.name}?`;

  document.getElementById("businessLogo").src = business.logoBlobstoreUrl;

  const categoryContainer = document.getElementById("businessCategories");
  business.categories.forEach(category => categoryContainer.appendChild(createCategoryElement(category)));

  const businessUrlElement = document.getElementById("businessUrl")
  if (business.websiteUrl !== null && business.websiteUrl !== "" && business.websiteUrl !== undefined){
    console.log("not null");
    businessUrlElement.href = "//" + business.websiteUrl;
  } else {
    console.log("null");
    businessUrlElement.target = "";
  }
  
  const ratingContainer = document.getElementById("aggregateRating");
  ratingContainer.innerHTML = generateRating(business.aggregatedRating);

  initBusinessMap(business);

}

function populateImageGallery(photoUrlList) {
  const urlListLength = photoUrlList.length;
  if (urlListLength == 0) {
    Array.from(document.getElementsByClassName('image-gallery')).forEach(element => element.style.display = "none");
  }
  const imagePreviewGallery = document.getElementById("image-thumbnail-gallery");
  const modalGalleryContainer = document.getElementById("slides-container");
  const modalGalleryImagePreviewContainer = document.getElementById("gallery-thumbnail-container");

  var imageIndex;
  
  for (imageIndex = 1; imageIndex <= urlListLength; imageIndex++) {
    const url = photoUrlList[imageIndex-1];
    imagePreviewGallery.appendChild(createThumbnailImageElement(url, imageIndex));
    modalGalleryContainer.appendChild(createGalleryImageElement(url, imageIndex, urlListLength));
    modalGalleryImagePreviewContainer.appendChild(createGalleryImagePreviewElement(url, imageIndex));
  }  
}

function populateBusinessWriteup(business) {
  document.getElementById("businessDescr").innerHTML = business.description;
  var menuUrlElement = document.getElementById("menuUrl");
  if (business.menuUrl) {
    
    menuUrlElement.href = business.menuUrl;
  } else {
    var menuUrlContainer = document.getElementById("menuContainer");
    menuUrlContainer.parentNode.removeChild(menuUrlContainer)
  }
  
}

function populateContactDetails() {
  document.getElementById("orderInfo").innerHTML = business.orderInformation;
  var contactUrlElement = document.getElementById("contactUrl");
  if (business.contactUrl) {
    contactUrlElement.href = business.contactUrl;
  } else {
    var contactUrlContainer = document.getElementById("contactDetailsContainer");
    contactUrlContainer.parentNode.removeChild(contactUrlContainer);
  }
}

function createCategoryElement(categoryName) {
  const category = document.createElement("mark");
  category.innerHTML = categoryName;
  return category;
}

function createThumbnailImageElement(imageUrl, index) {
  var thumbnailImageElement = document.createElement("img");
  thumbnailImageElement.src = imageUrl;
  thumbnailImageElement.onclick= function(){
                                            openModal();
                                            currentSlide(index);
                                            };
  thumbnailImageElement.className = "hover-shadow cursor";
  return thumbnailImageElement;
}

function createGalleryImageElement(imageUrl, index, length) {
  var slide = document.createElement("div");
  slide.className = "mySlides";
  slide.innerHTML = `<div class="numbertext">${index} / ${length}</div>
                      <img
                        src=${imageUrl}
                        style="height: 400px; width:auto;"
                      />
                    `;
  return slide;
}

function createGalleryImagePreviewElement(imageUrl, index) {
  var galleryImagePreviewElement = document.createElement("div");
  galleryImagePreviewElement.className = "lightbox-column";
  galleryImagePreviewElement.innerHTML = `<img
                                            class="demo cursor"
                                            src=${imageUrl}
                                            style="height: 100px; width:auto"
                                            onclick="currentSlide(${index})"
                                            alt="Product Image ${index}"
                                          />`;
  return galleryImagePreviewElement;
}

function createEditBusinessLink(isAdmin) {
  var editLink = document.getElementById("editBusinessLink");
  if (isAdmin !== "true") {
    editLink.parentNode.removeChild(editLink);
    return;
  }
  
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  if (urlParams.has("businessID")) {
    const businessId = urlParams.get("businessID");
    editLink.href = `edit.html?businessID=${businessId}`;
    editLink.innerHTML = "Edit this business";
  } else {
    editLink.parentNode.removeChild(editLink);
  }  
}

function generateRating(rating){
  if (rating == 404){
    return "No ratings yet";
  }
  
  var starHTML = '';
  for (let i=0; i<5; i++) {
    if (i+0.5<=rating) {
      starHTML += '<i class="fas fa-star yellow-star"></i>';
    } else {
      starHTML += '<i class="fas fa-star"></i>';
    }
  }
  return parseFloat(rating).toFixed(2) + " " + starHTML;
}

function initBusinessMap(business) {
  const map = document.getElementById("map");
  if(business.addressLng !== 404 || business.addressLat !== 404){
    map.src = `https://www.google.com/maps/embed/v1/place?key=AIzaSyD6iOYBZGWKFe57PlDBpThR9y9MhtZgrEw&zoom=11&q=${parseFloat(business.addressLng).toFixed(4)},${parseFloat(business.addressLat).toFixed(4)}`;
    var marker = new google.maps.Marker({
    position: {lat: business.addressLat, lng: business.addressLng},
    map: map,
    title: business.name
  });
  } else {
    map.src = "https://www.google.com/maps/embed/v1/view?key=AIzaSyD6iOYBZGWKFe57PlDBpThR9y9MhtZgrEw&zoom=11&center=1.3521,103.8198";
  }
  
}