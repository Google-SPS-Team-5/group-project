function generatePreview() {
  const business = getPreviewBusinessData();
  console.log(business);
  populateBusinessDescription(business);
  // populateImageGallery(business.photoBlobstoreUrlList);
  populateBusinessWriteup(business);
  populateContactDetails(business);

  document.getElementById("product-page-content").style.display = "block";
}

function getPreviewBusinessData() {
  var jsonTemplate = `{ "name":"${document.getElementById("name").value}",
                        "categories":[${getPreviewCategories()}],
                        "minPrice":${document.getElementById("minPrice").value},
                        "maxPrice":${document.getElementById("maxPrice").value},
                        "addressLng":${document.getElementById("addressLng").value},
                        "addressLat":${document.getElementById("addressLat").value},
                        "address":"${document.getElementById("address").value}",
                        "logoBlobstoreUrl":[],
                        "photoBlobstoreUrlList":[],
                        "description":"${document.getElementById("desc").value}",
                        "menuUrl":"${document.getElementById("menuLink").value}",
                        "orderInformation":"${document.getElementById("orderDetails").value}",
                        "contactInformation":"${document.getElementById("contactDetails").value}",
                        "websiteUrl":"${document.getElementById("businessLink").value}"
                      }`;

  return JSON.parse(jsonTemplate);
}

function populateBusinessDescription(business) {
  Array.from(document.getElementsByClassName('businessName')).forEach(element => element.innerHTML += business.name);

  document.getElementById("businessLogo").src = business.logoBlobstoreUrl;

  const categoryContainer = document.getElementById("businessCategories");
  if (business.categories) {
    business.categories.forEach(category => categoryContainer.appendChild(createCategoryElement(category)));
  }
  

  const businessUrlElement = document.getElementById("businessUrl")
  if (business.websiteUrl){
    businessUrlElement.href = business.websiteUrl;
  } else {
    businessUrlElement.target = "";
  }

  initBusinessMap(business);

}

function populateImageGallery(photoUrlList) {
  const urlListLength = photoUrlList.length;
  const imagePreviewGallery = document.getElementById("image-thumbnail-gallery");
  if (urlListLength == 1) {
    if (photoUrlList[0] == ""){
      Array.from(document.getElementsByClassName('image-gallery')).forEach(element => element.style.display = "none");
      return;
    }
  }

  if (urlListLength == 0){
    Array.from(document.getElementsByClassName('image-gallery')).forEach(element => element.style.display = "none");
    return;
  }
  
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
  document.getElementById("businessDescr").innerHTML = nullOrPlaceholderString(business.description, "<i>Sorry, this business doesn't have a description yet!</i>");
  var menuUrlElement = document.getElementById("menuUrl");
  if (business.menuUrl) {
    
    menuUrlElement.href = business.menuUrl;
  } else {
    var menuUrlContainer = document.getElementById("menuContainer");
    menuUrlContainer.parentNode.removeChild(menuUrlContainer)
  }
  
}

function populateContactDetails(business) {
  document.getElementById("orderInfo").innerHTML = nullOrPlaceholderString(business.orderInformation, "<i>Sorry, this business doesn't have order information yet!</i>");
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
                        src="${imageUrl}"
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
                                            src="${imageUrl}"
                                            style="height: 100px; width:auto"
                                            onclick="currentSlide(${index})"
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

function initBusinessMap(business) {
  const map = document.getElementById("map");
  if(business.addressLng !== 404 || business.addressLat !== 404){
    let lat = parseFloat(business.addressLat)
    let lng = parseFloat(business.addressLng)
    map.src = `https://www.google.com/maps/embed/v1/place?key=AIzaSyD6iOYBZGWKFe57PlDBpThR9y9MhtZgrEw&zoom=13&q=${lat},${lng}&center=${lat},${lng}`;
  } else {
    map.src = "https://www.google.com/maps/embed/v1/view?key=AIzaSyD6iOYBZGWKFe57PlDBpThR9y9MhtZgrEw&zoom=11&center=1.3521,103.8198";
  }
  
}

function nullOrPlaceholderString(string, placeHolder) {
  if (string) {
    return string;
  } else {
    return placeHolder;
  }
}

function getPreviewCategories() {
  var selectedCategories = [];
  for (var option of document.getElementById('categories').options) {
    if (option.selected) {
      selectedCategories.push("\""+option.value+"\"");
    }
  }
  return selectedCategories;
}