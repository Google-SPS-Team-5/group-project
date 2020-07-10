async function fetchBlobstoreUrlAndShowForm() {
    const response = await fetch('/business-blobstore');
    const imageUploadUrl = await response.text();
    const addBusinessForm = document.getElementById('add-business-form');
    addBusinessForm.action = imageUploadUrl;
  }