async function getUserInformation() {
    let response = await fetch('/userinformation');
    let userinformation = await response.json();

    var user = JSON.parse(userinformation);
    document.getElementById("user-description").innerHTML = user.name;
    
    return user;
}