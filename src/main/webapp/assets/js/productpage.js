async function populateProductDetails() {  
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
    imagePreviewGallery.innerHTML += createThumbnailImageElement(url);
    modalGalleryContainer.innerHTML += createGalleryImageElement(url, image, urlListLength);
    modalGalleryImagePreviewContainer.innerHTML += createGalleryImagePreviewElement(url, image);
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
  return `<div class="lightbox-column">
          <img
            src=${imageUrl}
            style="height: 250px; width:auto;"
            onclick="openModal();currentSlide(1)"
            class="hover-shadow cursor"
          />
          `;
}

function createGalleryImageElement(imageUrl, index, length) {
  return `<div class="mySlides">
              <div class="numbertext">${index} / ${length}</div>
              <img
                src=${imageUrl}
                style="height: 400px; width:auto;"
              />
            </div>
          `;
}

function createGalleryImagePreviewElement(imageUrl, index) {
  return `<div class="lightbox-column">
              <img
                class="demo cursor"
                src=${imageUrl}
                style="height: 100px; width:auto"
                onclick="currentSlide(${index})"
                alt="Sea Salt Brownies"
              />
            </div>
          `;
}