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


}

function populateImageGallery(business) {
  
}

function populateBusinessWriteup(business) {
  
}

function populateContactDetails() {
  
}