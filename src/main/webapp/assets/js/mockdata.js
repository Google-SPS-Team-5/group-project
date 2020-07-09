async function getMockData() {
  let response = await fetch('/mockdata');
  let mockdata = await response.json();
  return mockdata;
}

async function getMultipleMockData() {
  let response = await fetch('/multiplemockdata');
  let mockdata = await response.json();
  return mockdata;
}