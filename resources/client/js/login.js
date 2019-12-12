function pageLoad() {
    let myHTML = '<div/>'
        +'<img src="/client/img/logo.jfif"  alt="Logo"/>'
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("loginButton").addEventListener("click", login);
}

function login(event) {
    event.preventDefault();
    debugger;
    const form = document.getElementById("loginForm");
    const formData = new FormData(form);
    if (document.getElementsByName("user").value = "student") {
        debugger;
        fetch("/Students/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                debugger;
                Cookies.set("id", responseData.id);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/student.html';
            }
        });
    }else if (document.getElementsByName("user").value = "tutor") {
        fetch("/Tutors/Tlogin", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("id", responseData.id);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/tutor.html';
            }
        });
    }else if (document.getElementsByName("user").value = "parent") {
        fetch("/Parents/Plogin", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("username", responseData.id);
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
            Cookies.remove("id");
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
            Cookies.remove("id");
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
            Cookies.remove("id");
            Cookies.remove("token");
            window.location.href = '/client/login.html';
        }
    });
}
function checkLogin() {
    let id = Cookies.get("id");
    let logInHTML = '';
    if (id === undefined) {
        logInHTML = "Not logged in. <a href='/client/login.html'>Log in</a>";
    } else {
        logInHTML = "Logged in as user ID:" + id + ". <a href='/client/login.html?logout'>Log out</a>";
    }
    document.getElementById("loggedInDetails").innerHTML = logInHTML;
}


