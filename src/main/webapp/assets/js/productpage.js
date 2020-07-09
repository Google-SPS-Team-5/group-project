async function getMockData() {
  let response = await fetch('/mockdata');
  let mockdata = await response.json();
  return mockdata;
}

async function populateProductDetails() {  
  business = await getMockData();

  populateBusinessDescription(business);
  populateImageGallery(business);
  populateBusinessWriteup(business);
  populateContactDetails(business);
}

function populateBusinessDescription(business) {
  Array.from(document.getElementsByClassName('businessName')).forEach(element => element.innerHTML += business.name);

  document.getElementById("businessLogo").src = business.logoBlobstoreUrl;

  const categoryContainer = document.getElementById("businessCategories");
  business.categories.forEach(category => categoryContainer.appendChild(createCategoryElement(category)));

  document.getElementById("businessUrl").href = business.websiteUrl;

  const ratingContainer = document.getElementById("aggregateRating");
  //TODO: Fix star CSS and use CSS to set float rating
}

function populateImageGallery(business) {
  const imagePreviewGallery = document.getElementById("image-thumbnail-gallery");
  const modalGallery = document.getElementById("myModal");

  business.photoBlobstoreUrlList.forEach(photoUrl => imagePreviewGallery.innerHTML += createThumbnailImageElement(photoUrl));

}

function populateBusinessWriteup(business) {
  
}

function populateContactDetails() {
  
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
            style="height: 250px; width:auto"
            onclick="openModal();currentSlide(1)"
            class="hover-shadow cursor"
          />`;
}

function createGalleryImageElement(imageUrl) {

}

function createGalleryImagePreviewElement(imageUrl) {

}