async function getUserInformation() {
    const response = await fetch('/userinformation');
    var userinformation = await response.json();

    var user = JSON.parse(userinformation);
    var username = document.getElementById("user-description");
    username.innerHTML = user.name;

    return user;
}