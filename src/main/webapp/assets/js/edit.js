document.addEventListener("DOMContentLoaded", function() {
    showFormOrRedirectOut();
});

async function showFormOrRedirectOut() {
    const response = await fetch("/authentication");
    const userJson = await response.json();
    if (userJson.isAdmin == "true") {
        fetchBlobstoreUrl();
        populateForm();
        document.getElementById("page-title").style.display = "block";
        document.getElementById("form-container").style.visibility = "visible";
    } else {
        document.getElementById("error-message").style.display = "block";
        document.getElementById("error-message").style.margin = "5rem";
        setTimeout(function(){window.location.href = "/";}, 3000);
    }
}

async function fetchBlobstoreUrl() {
    const response = await fetch("/edit-business-blobstore");
    const imageUploadUrl = await response.text();
    const editBusinessForm = document.getElementById("edit-business-form");
    editBusinessForm.action = imageUploadUrl;
}

async function populateForm() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const businessId = urlParams.get("businessID")

    const response = await fetch(`/edit-business-data?businessID=${businessId}`);
    const business = await response.json();

    console.log(business);
    document.getElementById("businessId").defaultValue = business.id;
    document.getElementById("name").defaultValue = business.data.name;
    document.getElementById("desc").defaultValue = business.data.description;
    var categories = document.getElementById("categories");
    for (const category of business.data.categories) {
        for (i = 0; i< categories.options.length; i++) {
            if (categories.options[i].value == category) {
                categories.options[i].selected = true;
            }
        }
    }
    document.getElementById("address").defaultValue = business.data.address;
    document.getElementById("addressLat").defaultValue = business.data.addressLat;
    document.getElementById("addressLng").defaultValue = business.data.addressLng;
    document.getElementById("contactDetails").defaultValue = business.data.contactInformation;
    document.getElementById("orderDetails").defaultValue = business.data.orderInformation;
    document.getElementById("businessLink").defaultValue = business.data.websiteUrl;
    document.getElementById("menuLink").defaultValue = business.data.menuUrl;
    document.getElementById("minPrice").defaultValue = business.data.minPrice;
    document.getElementById("maxPrice").defaultValue = business.data.maxPrice;
    document.getElementById("logo").defaultValue = business.data.logoBlobstoreUrl;
    document.getElementById("pictures").defaultValue = business.data.photoBlobstoreUrlList;
}

document.querySelector("#logo").onchange = function(){
  document.querySelector("#logo-file-name").textContent = this.files[0].name;
}

document.querySelector("#pictures").onchange = function(){
  var fileNames = "";
  
  for (i = 0; i < this.files.length; i++) {
    fileNames += this.files[i].name + "\n";
  }
  document.querySelector("#pictures-file-names").textContent = fileNames;
}