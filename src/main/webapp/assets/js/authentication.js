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
        navBarContainer.innerHTML = navBarUserLoggedIn(userJson.userEmail, userJson.url, userJson.isAdmin)
    } else {
        navBarContainer.innerHTML = navBarUserLoggedOut(userJson.url)
    }

}

/**
 * Returns a navbar that contains the user email (link to their profile)
 * and a link to log out
 */
function navBarUserLoggedIn(userEmail, logoutUrl, isAdmin) {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const businessId = urlParams.get("businessID");

  if (isAdmin === "true") {
      return `
      <a href="add.html" class="nav-link">Add New Business</a>
      <a href="#" class="nav-link">${userEmail}</a>
      <a href="${logoutUrl}" class="nav-link">Log Out</a>`;
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


