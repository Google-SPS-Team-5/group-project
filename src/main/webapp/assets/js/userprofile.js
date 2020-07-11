async function getUserInformation() {
    let response = await fetch('/userinformation');
    let userinformation = await response.json();

    var user = JSON.parse(userinformation);

    return user;
}