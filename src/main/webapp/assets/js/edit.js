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
    const response = await fetch("/edit-business-data");
    const business = await response.json();

    document.getElementById("name").defaultValue = business.name;
    document.getElementById("desc").defaultValue = business.description;
    var categories = document.getElementById("categories");
    for (const category of business.categories) {
        for (i = 0; i< categories.options.length; i++) {
            if (categories.options[i].value == category) {
                categories.options[i].selected = true;
            }
        }
    }
    document.getElementById("address").defaultValue = business.address;
    document.getElementById("addressLat").defaultValue = business.addressLat;
    document.getElementById("addressLng").defaultValue = business.addressLng;
    document.getElementById("contactDetails").defaultValue = business.contactInformation;
    document.getElementById("orderDetails").defaultValue = business.orderInformation;
    document.getElementById("businessLink").defaultValue = business.websiteUrl;
    document.getElementById("menuLink").defaultValue = business.menuUrl;
    document.getElementById("minPrice").defaultValue = business.minPrice;
    document.getElementById("maxPrice").defaultValue = business.maxPrice;
    document.getElementById("logo").defaultValue = business.logoBlobstoreUrl;
    document.getElementById("pictures").defaultValue = business.photoBlobstoreUrlList;

    console.log(business);
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