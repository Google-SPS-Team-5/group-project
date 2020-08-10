document.addEventListener("DOMContentLoaded", function() {
    isLoggedin();
});

/**
 * Fetch data from /login endpoint to check the status of the user.
 * Data that the enpoint contains is JSON.
 * If the user is logged in, the userEmail will be a non-empty string.
 * Depending on the log in status, the navbar rendered will look different.
 */
async function isLoggedin() {
    const response = await fetch("/authentication");
    const userJson = await response.json();
    var navBarContainer = document.getElementById("authentication");
    if (userJson.userEmail) {
        navBarContainer.innerHTML = navBarUserLoggedIn(userJson.username, userJson.url, userJson.isAdmin, userJson.isBusinessOwner, userJson.businessOwnership);
    } else {
        navBarContainer.innerHTML = navBarUserLoggedOut(userJson.url);
    }

}

/**
 * Returns a navbar that contains the user email (link to their profile)
 * and a link to log out.
 * If user is admin, navbar contains Add New Business.
 * If user is business owner, navbar contains Edit Your Business.
 */
function navBarUserLoggedIn(userEmail, logoutUrl, isAdmin, isBusinessOwner, ownedBusinessId) {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const businessId = urlParams.get("businessID");

  if (isAdmin === "true") {
      return `
      <a href="add.html" class="nav-link">Add New Business</a>
      <a href="assignOwner.html" class="nav-link">Assign Business Ownership</a>
      <a href="#" class="nav-link">${userEmail}</a>
      <a href="${logoutUrl}" class="nav-link">Log Out</a>`;
  } else if (isBusinessOwner === "true") { // Generate Edit Your Business button
      return `
      <a href="edit.html?businessID=${ownedBusinessId}" class="nav-link">Edit Your Business</a>
      <a href="#" class="nav-link">${userEmail}</a>
      <a href="${logoutUrl}" class="nav-link">Log Out</a>`
  } else {
      return `
      <a href="#" class="nav-link">${userEmail}</a>
      <a href="${logoutUrl}" class="nav-link">Log Out</a>`;
  }

}

/**
 * Returns a navbar that contains a link to log in
 */
function navBarUserLoggedOut(loginUrl) {
    return `<a href="${loginUrl}" class="nav-link">Log In</a>`;
}


