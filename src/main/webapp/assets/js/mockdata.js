async function getMockDataBusiness() {
  let response = await fetch('/mockdatabusiness');
  let mockdata = await response.json();
  return mockdata;
}

async function getMultipleMockDataBusiness() {
  let response = await fetch('/multiplemockdatabusiness');
  let mockdata = await response.json();
  return mockdata;
}

async function getMockDataReviews() {
  let response = await fetch('/mockdatareview');
  let mockdata = await response.json();
  return mockdata;
}
