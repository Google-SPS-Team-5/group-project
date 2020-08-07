document.addEventListener("DOMContentLoaded", function() {
    showFormOrRedirectOut();
});

async function showFormOrRedirectOut() {
    const response = await fetch("/authentication");
    const userJson = await response.json();
    if (userJson.isAdmin === "true") {
        document.getElementById("page-title").style.display = "block";
        document.getElementById("form-container").style.visibility = "visible";
    } else {
        document.getElementById("error-message").style.display = "block";
        document.getElementById("error-message").style.margin = "5rem";
        setTimeout(function() { window.location.href = "/"; }, 3000);
    }
}