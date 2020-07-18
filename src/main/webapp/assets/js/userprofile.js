async function getUserInformation() {
    const response = await fetch('/userinformation');
    var userinformation = await response.json();

    console.log(userinformation.name);
    var nameContainer = (document.getElementById("user-description"));
    var name = userinformation.name.toUpperCase();
    nameContainer.innerHTML = name;

    return userinformation;
}
