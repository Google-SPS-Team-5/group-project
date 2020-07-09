document.addEventListener("DOMContentLoaded", function(){ 
  isLoggedin();
});

function isLoggedin() {
    console.log("trigger")
    fetch('/login').then(response => response.json()).then(userJson => {
        console.log(userJson);
        var navBarContainer = document.getElementById("nav-container");
        if (userJson.userEmail) {
            navBarContainer.innerHTML = navBarUserLoggedIn(userJson.userEmail, userJson.url)
        } else {
            navBarContainer.innerHTML = navBarUserLoggedOut(userJson.url)
        }
    });
}

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


