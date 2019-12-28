function pageLoad() {
    let myHTML = '<div/>'
        +'<img src="/client/img/logo.jfif"  alt="Logo"/>'
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("sub").addEventListener("click", checker);
}


function checker(event){
    event.preventDefault();
    const form = document.getElementById("StuForm");
    const formData = new FormData(form);
    fetch("/Parents/check", {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.set("stuid", responseData.id);
            window.location.href = '/client/parent.html';
        }
    });
}