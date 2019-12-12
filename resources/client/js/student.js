function pageLoading() {
    debugger;
    let name = Cookies.get("id");
    let myHTML = '<div/>'
        + '<img src="/client/img/logo.jfif"  alt="Logo"/>'
    document.getElementById("imageDiv").innerHTML = myHTML
    document.getElementById("heading").innerHTML = name
}