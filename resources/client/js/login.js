function pageLoad() {
    let myHTML = '<div style="align:left;"/>'
        +'<img src="/client/img/logo.jfif"  alt="Logo"/>'
    document.getElementById("testDiv").innerHTML = myHTML;
    document.getElementById("loginButton").addEventListener("click", login);
}

function login(event) {
    event.preventDefault();
    const form = document.getElementById("loginForm");
    const formData = new FormData(form);
    if (document.getElementsByName("user").value = "student") {
        fetch("/Students/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("username", responseData.FName);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/student.html';
            }
        });
    }else if (document.getElementsByName("user").value = "tutor") {
        fetch("/Tutors/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("username", responseData.TFName);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/tutor.html';
            }
        });
    }else if (document.getElementsByName("user").value = "parent") {
        fetch("/Parents/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("username", responseData.PFName);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/parent.html';
            }
        });
    }
}

function logout() {
    fetch("/Students/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.remove("FName");
            Cookies.remove("token");
            window.location.href = '/client/login.html';
        }
    });
}
function Plogout() {
    fetch("/Parents/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.remove("PFName");
            Cookies.remove("token");
            window.location.href = '/client/login.html';
        }
    });
}
function Tlogout() {
    fetch("/Tutors/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            Cookies.remove("TFName");
            Cookies.remove("token");
            window.location.href = '/client/login.html';
        }
    });
}
