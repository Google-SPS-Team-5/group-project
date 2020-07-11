async function getUserInformation() {
    let response = await fetch('/userinformation');
    let userinformation = await response.json();
    return userinformation;
}