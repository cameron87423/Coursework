function pageLoad() {
    let name = Cookies.get("id");
    const user = Cookies.get("user");
    let myHTML = '<div/>'
        + '<img src="/client/img/logo.jfif"  alt="Logo"/>';
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("disStu").style.display='none';
    document.getElementById("disTu").style.display='none';
    document.getElementById("disPa").style.display='none';
    document.getElementById("del").style.display='none';
    document.getElementById("editStu").style.display='none';
    document.getElementById("editTu").style.display='none';
    document.getElementById("editPa").style.display='none';
    document.getElementById("passStu").style.display='none';
    document.getElementById("passTu").style.display='none';
    document.getElementById("passPa").style.display='none';
    document.getElementById("saveStu").addEventListener("click", saveUpdate);
    document.getElementById("cancelStu").addEventListener("click", cancelUpdate);
    document.getElementById("saveTu").addEventListener("click", saveUpdate);
    document.getElementById("cancelTu").addEventListener("click", cancelUpdate);
    document.getElementById("savePa").addEventListener("click", saveUpdate);
    document.getElementById("cancelPa").addEventListener("click", cancelUpdate);
    document.getElementById("savePassStu").addEventListener("click", savePass);
    document.getElementById("cancelPassStu").addEventListener("click", cancelPass);
    document.getElementById("savePassTu").addEventListener("click", savePass);
    document.getElementById("cancelPassTu").addEventListener("click", cancelPass);
    document.getElementById("savePassPa").addEventListener("click", savePass);
    document.getElementById("cancelPassPa").addEventListener("click", cancelPass);

    document.getElementById("heading").innerHTML ="Profile: " + user + " " + name;
    if(user === "student"){
        document.getElementById("disStu").style.display='block';
        document.getElementById("del").style.display='block';
        fetch('/Students/pick/' + Cookies.get("id"),{method: 'get'}
        ).then(response => response.json()
        ).then(responseData => {
            document.getElementById("name").innerHTML = responseData.Name;
            document.getElementById("surname").innerHTML = responseData.Surname;
            document.getElementById("age").innerHTML = responseData.Age;
            document.getElementById("address1").innerHTML = responseData.Address1;
            document.getElementById("address2").innerHTML = responseData.Address2;
        })
    }else if(user === "tutor"){
        document.getElementById("disTu").style.display='block';
        document.getElementById("del").style.display='block';
        fetch('/Tutors/pick/' + Cookies.get("id"),{method: 'get'}
        ).then(response => response.json()
        ).then(responseData => {
            document.getElementById("tname").innerHTML = responseData.Name;
            document.getElementById("tsurname").innerHTML = responseData.Surname;
            document.getElementById("gender").innerHTML = responseData.Gender;
            document.getElementById("experience").innerHTML = responseData.Experince;
            document.getElementById("rating").innerHTML = responseData.Rating;
        })
    }else if(user === "parent"){
        debugger;
        document.getElementById("disPa").style.display='block';
        document.getElementById("del").style.display='block';
        fetch('/Parents/pick/' + Cookies.get("stuid"),{method: 'get'}
        ).then(response => response.json()
        ).then(responseData => {
            document.getElementById("studentID").innerHTML = responseData.StudentID;
            document.getElementById("studentName").innerHTML = responseData.StudentName;
            document.getElementById("paddress1").innerHTML = responseData.Address1;
            document.getElementById("paddress2").innerHTML = responseData.Address2;
            document.getElementById("pname").innerHTML = responseData.Name;
            document.getElementById("psurname").innerHTML = responseData.Surname;
        })
    }
}

function homepage(){
    const user = Cookies.get("user");
    if(user === "student"){
        window.location.href = '/client/student.html'
    }else if(user === "tutor"){
        window.location.href = '/client/tutor.html'
    }else if(user === "parent"){
        window.location.href = '/client/parent.html'
    }
}

function update(){
    const user = Cookies.get("user");
    if(user === "student"){
        document.getElementById("student").value = Cookies.get("id");
        document.getElementById("ename").value = '';
        document.getElementById("esurname").value = '';
        document.getElementById("eage").value = '';
        document.getElementById("egender").value = '';
        document.getElementById("eaddress1").value = '';
        document.getElementById("eaddress2").value = '';

        document.getElementById("disStu").style.display='none';
        document.getElementById("del").style.display='none';
        document.getElementById("editStu").style.display='block';
    }else if(user === "tutor"){
        document.getElementById("tutor").value = Cookies.get("id");
        document.getElementById("etname").value = '';
        document.getElementById("etsurname").value = '';
        document.getElementById("etgender").value = '';
        document.getElementById("eexperience").value = '';
        document.getElementById("erating").value = '';

        document.getElementById("disTu").style.display='none';
        document.getElementById("del").style.display='none';
        document.getElementById("editTu").style.display='block';
    }else if(user === "parent"){
        document.getElementById("parent").value = Cookies.get("id");
        document.getElementById("epname").value = '';
        document.getElementById("epsurname").value = '';
        document.getElementById("stud").value = '';
        document.getElementById("epaddress1").value = '';
        document.getElementById("epaddress2").value = '';

        document.getElementById("disPa").style.display='none';
        document.getElementById("del").style.display='none';
        document.getElementById("editPa").style.display='block';
    }
}

function saveUpdate(event){
    event.preventDefault();
    const user = Cookies.get("user");
    if(user === "student") {
        const form = document.getElementById("StuForm");
        const formData = new FormData(form);
        fetch("/Students/change", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                pageLoad()
            }
        });
    }else if(user === "tutor") {
        const form = document.getElementById("TuForm");
        const formData = new FormData(form);
        fetch("/Tutors/change", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                pageLoad()
            }
        });
    }else if(user === "parent") {
        const form = document.getElementById("PaForm");
        const formData = new FormData(form);
        fetch("/Parents/change", {method: 'post', body: formData}
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

function cancelUpdate(event){
    event.preventDefault();
    pageLoad()
}

function pass(){
    const user = Cookies.get("user");
    if(user === "student"){
        document.getElementById("pstudent").value = Cookies.get("id");
        document.getElementById("passname").value = '';
        document.getElementById("password").value = '';

        document.getElementById("disStu").style.display='none';
        document.getElementById("del").style.display='none';
        document.getElementById("passStu").style.display='block';
    }else if(user === "tutor"){
        document.getElementById("ptutor").value = Cookies.get("id");
        document.getElementById("passtname").value = '';
        document.getElementById("tpassword").value = '';

        document.getElementById("disTu").style.display='none';
        document.getElementById("del").style.display='none';
        document.getElementById("passTu").style.display='block';
    }else if(user === "parent"){
        document.getElementById("pparent").value = Cookies.get("id");
        document.getElementById("passpname").value = '';
        document.getElementById("ppassword").value = '';

        document.getElementById("disPa").style.display='none';
        document.getElementById("del").style.display='none';
        document.getElementById("passPa").style.display='block';
    }
}

function savePass(event){
    event.preventDefault();
    const ok = confirm("Are you sure?");
    if (ok === true) {
        const user = Cookies.get("user");
        if (user === "student") {
            const form = document.getElementById("StuPass");
            const formData = new FormData(form);
            fetch("Students/Password", {method: 'post', body: formData}
            ).then(response => response.json()
            ).then(responseData => {
                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoad()
                }
            });
        } else if (user === "tutor") {
            const form = document.getElementById("TuPass");
            const formData = new FormData(form);
            fetch("Tutors/Password", {method: 'post', body: formData}
            ).then(response => response.json()
            ).then(responseData => {
                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoad()
                }
            });
        } else if (user === "parent") {
            const form = document.getElementById("PaPass");
            const formData = new FormData(form);
            fetch("Parents/Password", {method: 'post', body: formData}
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
}

function cancelPass(event){
    event.preventDefault();
    pageLoad()
}

function delet(){
    const user = Cookies.get("user");
    const ok = confirm("Are you sure?");
    if (ok === true) {
        let id = Cookies.get("id");
        let formData = new FormData();
        formData.append("id", id);
        if(user === "student"){
            fetch('/Students/delete', {method: 'post', body: formData}
            ).then(response => response.json()
            ).then(responseData => {
                    if (responseData.hasOwnProperty('error')) {
                        alert(responseData.error);
                    } else {
                        window.location.href = '/client/login.html';
                    }
                }
            );
        }else if(user === "tutor"){
            fetch('/Tutors/delete', {method: 'post', body: formData}
            ).then(response => response.json()
            ).then(responseData => {
                    if (responseData.hasOwnProperty('error')) {
                        alert(responseData.error);
                    } else {
                        window.location.href = '/client/login.html';
                    }
                }
            );
        }else if(user === "parent"){
            fetch('/Parents/delete', {method: 'post', body: formData}
            ).then(response => response.json()
            ).then(responseData => {
                    if (responseData.hasOwnProperty('error')) {
                        alert(responseData.error);
                    } else {
                        window.location.href = '/client/login.html';
                    }
                }
            );
        }
    }
}