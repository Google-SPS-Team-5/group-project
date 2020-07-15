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

  document.getElementById("businessUrl").href = business.websiteUrl;

  const ratingContainer = document.getElementById("aggregateRating");
  //TODO: Fix star CSS and use CSS to set float rating
}

function populateImageGallery(photoUrlList) {
  const imagePreviewGallery = document.getElementById("image-thumbnail-gallery");
  const modalGalleryContainer = document.getElementById("slides-container");
  const modalGalleryImagePreviewContainer = document.getElementById("gallery-thumbnail-container");

  var image;
  const urlListLength = photoUrlList.length;
  for (image = 1; image <= urlListLength; image++) {
    const url = photoUrlList[image-1];
    imagePreviewGallery.appendChild(createThumbnailImageElement(url));
    modalGalleryContainer.appendChild(createGalleryImageElement(url, image, urlListLength));
    modalGalleryImagePreviewContainer.appendChild(createGalleryImagePreviewElement(url, image));
  }  
}

function populateBusinessWriteup(business) {
  document.getElementById("businessDescr").innerHTML = business.description;
  document.getElementById("menuUrl").href = business.menuUrl;
}

function populateContactDetails() {
  document.getElementById("orderInfo").innerHTML = business.orderInformation;
  document.getElementById("contactUrl").href = business.contactUrl;
}

function createCategoryElement(categoryName) {
  const category = document.createElement("mark");
  category.innerHTML = categoryName;
  return category;
}

function createThumbnailImageElement(imageUrl) {
  var thumbnailImageElement = document.createElement("div");
  thumbnailImageElement.className = "lightbox-column";
  thumbnailImageElement.innerHTML = `<img
                                        src=${imageUrl}
                                        style="height: 250px; width:auto;"
                                        onclick="openModal();currentSlide(1)"
                                        class="hover-shadow cursor"
                                      />`
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
                                            alt="Sea Salt Brownies"
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