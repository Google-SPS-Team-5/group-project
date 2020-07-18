async function getUserInformation() {
    const response = await fetch('/userinformation');
    var userinformation = await response.json();

    var nameContainer = (document.getElementById("user-description"));
    var emailContainer = (document.getElementById("user-email"));
    var name = userinformation.name.toUpperCase();
    nameContainer.innerHTML = name;
    emailContainer.innerHTML = userinformation.email;

    return userinformation;
}