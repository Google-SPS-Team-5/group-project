async function getMockData() {
  let response = await fetch('/mockdatabusiness');
  let mockdata = await response.json();
  return mockdata;
}

async function getMultipleMockData() {
  let response = await fetch('/multiplemockdatabusiness');
  let mockdata = await response.json();
  return mockdata;
}