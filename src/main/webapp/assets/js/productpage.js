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
}

function populateImageGallery(business) {
  
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