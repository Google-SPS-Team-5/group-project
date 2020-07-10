document.addEventListener("DOMContentLoaded", function() {
    fetchBlobstoreUrl();
});

async function fetchBlobstoreUrl() {
    const response = await fetch('/business-blobstore');
    const imageUploadUrl = await response.text();
    const addBusinessForm = document.getElementById('add-business-form');
    addBusinessForm.action = imageUploadUrl;
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