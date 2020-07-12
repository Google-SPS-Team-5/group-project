document.addEventListener("DOMContentLoaded", function() {
    showFormOrRedirectOut();
});

async function showFormOrRedirectOut() {
    const response = await fetch("/authentication");
    const userJson = await response.json();
    if (userJson.isAdmin == "true") {
        fetchBlobstoreUrl();
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