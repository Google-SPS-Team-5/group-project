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
    const response = await fetch("/login");
    const userJson = await response.json();
    var navBarContainer = document.getElementById("nav-container");
    if (userJson.userEmail) {
        navBarContainer.innerHTML = navBarUserLoggedIn(userJson.userEmail, userJson.url)
    } else {
        navBarContainer.innerHTML = navBarUserLoggedOut(userJson.url)
    }

}

/**
 * Returns a navbar that contains the user email (link to their profile)
 * and a link to log out
 */
function navBarUserLoggedIn(userEmail, logoutUrl) {
    return `
        <a href="index.html" class="nav-link">Home</a>
        <a href="about.html" class="nav-link">About us</a>
        <a href="${logoutUrl}" class="nav-link authentication">Log Out</a>
        <a href="#" class="nav-link authentication">${userEmail}</a>
        <div id="search-container">
            <form action="/placeholder.js">
                <input type="text" placeholder="Search listing...">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>`;
}

/**
 * Returns a navbar that contains a link to log in
 */
function navBarUserLoggedOut(loginUrl) {
    return `
        <a href="index.html" class="nav-link">Home</a>
        <a href="about.html" class="nav-link">About us</a>
        <a href="${loginUrl}" class="nav-link authentication">Log In</a>
        <div id="search-container">
            <form action="/placeholder.js">
                <input type="text" placeholder="Search listing...">
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>
        </div>`;
}


