function pageLoad() {
    let myHTML = '<div/>'
        +'<img src="/client/img/logo.jfif"  alt="Logo"/>'
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("LoginBox").style.display='block';
    document.getElementById("create").style.display='block';
    document.getElementById("createStu").style.display='none';
    document.getElementById("createTu").style.display='none';
    document.getElementById("createPa").style.display='none';
    document.getElementById("saveStu").addEventListener("click", saveCreateStu);
    document.getElementById("cancelStu").addEventListener("click", cancelCreateStu);
    document.getElementById("saveTu").addEventListener("click", saveCreateTu);
    document.getElementById("cancelTu").addEventListener("click", cancelCreateTu);
    document.getElementById("savePa").addEventListener("click", saveCreatePa);
    document.getElementById("cancelPa").addEventListener("click", cancelCreatePa);
}

function login(i) {
    debugger;
    const form = document.getElementById("loginForm");
    const formData = new FormData(form);
    if (i === 1) {
        fetch("/Students/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("id", responseData.id);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/student.html';
            }
        });
    }else if (i === 2) {
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
    }else if (i === 3) {
        fetch("/Parents/Plogin", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("id", responseData.id);
                Cookies.set("token", responseData.token);
                window.location.href = '/client/parentcheck.html';
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

function createStu() {
    document.getElementById("name").value = '';
    document.getElementById("surname").value = '';
    document.getElementById("age").value = '';
    document.getElementById("gender").value = '';
    document.getElementById("address1").value = '';
    document.getElementById("pass").value = '';
    document.getElementById("cpass").value = '';

    document.getElementById("LoginBox").style.display = 'none';
    document.getElementById("create").style.display = 'none';
    document.getElementById("createStu").style.display = 'block';
}
function saveCreateStu(event) {
    event.preventDefault();
    if(document.getElementById("pass").value !== document.getElementById("cpass").value){
        alert("passwords do not match");
    }else {
        const form = document.getElementById("StuForm");
        const formData = new FormData(form);
        fetch("Students/new", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                pageLoad()
            }
        });
    }
}
function cancelCreateStu(event) {
    event.preventDefault();
    document.getElementById("LoginBox").style.display = 'block';
    document.getElementById("create").style.display = 'block';
    document.getElementById("createStu").style.display = 'none';
}

function createTu() {
    document.getElementById("tname").value = '';
    document.getElementById("tsurname").value = '';
    document.getElementById("tgender").value = '';
    document.getElementById("experience").value = '';
    document.getElementById("rating").value = '';
    document.getElementById("tpass").value = '';
    document.getElementById("tcpass").value = '';

    document.getElementById("LoginBox").style.display = 'none';
    document.getElementById("create").style.display = 'none';
    document.getElementById("createTu").style.display = 'block';
}
function saveCreateTu(event) {
    event.preventDefault();
    if(document.getElementById("tpass").value !== document.getElementById("tcpass").value){
        alert("passwords do not match");
    }else {
        const form = document.getElementById("TuForm");
        const formData = new FormData(form);
        fetch("Tutors/new", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                pageLoad()
            }
        });
    }
}
function cancelCreateTu(event) {
    event.preventDefault();
    document.getElementById("LoginBox").style.display = 'block';
    document.getElementById("create").style.display = 'block';
    document.getElementById("createTu").style.display = 'none';
}

function createPa() {
    document.getElementById("pname").value = '';
    document.getElementById("psurname").value = '';
    document.getElementById("stud").value = '';
    document.getElementById("ppass").value = '';
    document.getElementById("pcpass").value = '';

    document.getElementById("LoginBox").style.display = 'none';
    document.getElementById("create").style.display = 'none';
    document.getElementById("createPa").style.display = 'block';
}
function saveCreatePa(event) {
    event.preventDefault();
    if(document.getElementById("ppass").value !== document.getElementById("pcpass").value){
        alert("passwords do not match");
    }else {
        const form = document.getElementById("PaForm");
        const formData = new FormData(form);
        fetch("Parents/new", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                pageLoad()
            }
        });
    }
}
function cancelCreatePa(event) {
    event.preventDefault();
    document.getElementById("LoginBox").style.display = 'block';
    document.getElementById("create").style.display = 'block';
    document.getElementById("createPa").style.display = 'none';
}

