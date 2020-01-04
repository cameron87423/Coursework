function pageLoading() {
    let name = Cookies.get("id");
    let myHTML = '<div/>'
        + '<img src="/client/img/logo.jfif"  alt="Logo"/>';
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("heading").innerHTML ="welcome tutor: " + name;

    document.getElementById("first").style.display="block";
    document.getElementById("ChangeInfo").style.display='block';
    document.getElementById("create1").style.display='block';
    document.getElementById("second").style.display='none';
    document.getElementById("ChangeSubject").style.display='none';
    document.getElementById("create2").style.display='none';
    document.getElementById("editSub").style.display='none';
    document.getElementById("editSes").style.display='none';
    document.getElementById("editInfo").style.display='none';
    document.getElementById("ISearch").addEventListener("click", InfoO);
    document.getElementById("SSearch").addEventListener("click", SessionO);
    document.getElementById("createSub").addEventListener("click", editSubject);
    document.getElementById("createSes").addEventListener("click", editSession);
    document.getElementById("createInfo").addEventListener("click", editInfo);
    document.getElementById("saveSub").addEventListener("click", saveEditSubject);
    document.getElementById("cancelSub").addEventListener("click", cancelEditSubject);
    document.getElementById("saveSes").addEventListener("click", saveEditSession);
    document.getElementById("cancelSes").addEventListener("click", cancelEditSession);
    document.getElementById("saveInfo").addEventListener("click", saveEditInfo);
    document.getElementById("cancelInfo").addEventListener("click", cancelEditInfo);


    let I = document.getElementById("changeI");
    I.onclick = Info;
    let P =document.getElementById("changeS");
    P.onclick = pageLoading;

    let studentsHTML = `<table>` +
        '<tr>' +
        '<th>TutorID</th>' +
        '<th>First name</th>' +
        '<th>SubjectID</th>' +
        '<th>Subject</th>' +
        '<th>Student ID</th>' +
        '<th>Student name</th>' +
        '<th>Student surname</th>' +
        '<th class="last">Options</th>' +
        '</tr>';
    fetch('/Tutors/StudentList/' + Cookies.get("id"),{method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                studentsHTML += `<tr>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.TutorName}</td>` +
                    `<td>${student.SubjectID}</td>` +
                    `<td>${student.SubjectName}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.StudentName}</td>` +
                    `<td>${student.StudentSurname}</td>` +
                    `<td class="last">` +
                    `<button class='editSub' data-id='${student.SubjectID}'>Edit</button>` +
                    `<button class='deleteSub' data-id='${student.SubjectID}'>Delete</button>` +
                    `</td>` +
                    `</tr>`;
            }
        }
        studentsHTML += '</table>';
        document.getElementById("StudentList").innerHTML = studentsHTML;
        let editButtons = document.getElementsByClassName("editSub");
        for (let button of editButtons) {
            button.addEventListener("click", editSubject);
        }
        let deleteButtons = document.getElementsByClassName("deleteSub");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteSub);
        }
    });

    let sessionsHTML = `<table>` +
        '<tr>' +
        '<th>SessionID</th>' +
        '<th>TutorID</th>' +
        '<th>StudentID</th>' +
        '<th>Hours</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
        '<th>Review</th>' +
        '<th class="last">Options</th>' +
        '</tr>';
    fetch('/Sessions/TutorSessions/' + Cookies.get("id"), {method: 'get'}
    ).then(response => response.json()
    ).then(responseDa => {
        if (responseDa.hasOwnProperty('error')) {
            alert(responseDa.error);
        } else {
            for (let student of responseDa) {
                sessionsHTML += `<tr>` +
                    `<td>${student.SessionID}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.Hours}</td>` +
                    `<td>${student.Pay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `<td>${student.Review}</td>` +
                    `<td class="last">` +
                    `<button class='editSes' data-id='${student.SessionID}'>Edit</button>` +
                    `<button class='deleteSes' data-id='${student.SessionID}'>Delete</button>` +
                    `</td>` +
                    `</tr>`;
            }
        }
        sessionsHTML += '</table>';
        document.getElementById("SessionsList").innerHTML = sessionsHTML;
        let editButtons = document.getElementsByClassName("editSes");
        for (let button of editButtons) {
            button.addEventListener("click", editSession);
        }
        let deleteButtons = document.getElementsByClassName("deleteSes");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteSession);
        }
    });
}

function Info(){
    document.getElementById("first").style.display='none';
    document.getElementById("ChangeInfo").style.display='none';
    document.getElementById("create1").style.display='none';
    document.getElementById("second").style.display=('block');
    document.getElementById("ChangeSubject").style.display='block';
    document.getElementById("create2").style.display='block';

    let infoHTML = `<table>` +
        '<tr>' +
        '<th>Info ID</th>' +
        '<th>TutorID</th>' +
        '<th>StudentID</th>' +
        '<th>Total hours</th>' +
        '<th>Total pay</th>' +
        '<th>Remaining pay</th>' +
        '<th>Target grade</th>' +
        '<th class="last">Options</th>' +
        '</tr>';
    fetch('/Information/Tutors/' + Cookies.get("id"), {method: 'get'}
    ).then(response => response.json()
    ).then(responseDat => {
        if (responseDat.hasOwnProperty('error')) {
            alert(responseDat.error);
        } else {
            for (let student of responseDat) {
                infoHTML += `<tr>` +
                    `<td>${student.InformationID}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.THours}</td>` +
                    `<td>${student.TotalPay}</td>` +
                    `<td>${student.RemainingPay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `<td class="last">` +
                    `<button class='editInfo' data-id='${student.InformationID}'>Edit</button>` +
                    `<button class='deleteInfo' data-id='${student.InformationID}'>Delete</button>` +
                    `</td>` +
                    `</tr>`;
            }
        }
        infoHTML += '</table>';
        document.getElementById("InformationList").innerHTML = infoHTML;
        let editButtons = document.getElementsByClassName("editInfo");
        for (let button of editButtons) {
            button.addEventListener("click", editInfo);
        }
        let deleteButtons = document.getElementsByClassName("deleteInfo");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteInfo);
        }
    });
}

function InfoO(event){
    event.preventDefault();
    let infoOHTML = `<table>` +
        '<tr>' +
        '<th>InfoID</th>' +
        '<th>StudentID</th>' +
        '<th>TutorID</th>' +
        '<th>StudentID</th>' +
        '<th>Total Hours</th>' +
        '<th>Total Pay</th>' +
        '<th>Remaining Pay</th>' +
        '<th>Target Grade</th>' +
        '</tr>';
    const id = document.getElementById("Info");
    fetch('/Information/One/' + Cookies.get("id") +'/'+ id.value,{method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                infoOHTML += `<tr>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.THours}</td>` +
                    `<td>${student.TotalPay}</td>` +
                    `<td>${student.RemainingPay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `</tr>`;
            }
        }
        infoOHTML += '</table>';
        document.getElementById("InformationOne").innerHTML = infoOHTML;
    });
}

function SessionO(event){
    event.preventDefault();
    let sessHTML = `<table>` +
        '<tr>' +
        '<th>StudentID</th>' +
        '<th>TutorID</th>' +
        '<th>StudentID</th>' +
        '<th>Hour(s)</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
        '<th>Review</th>' +
        '</tr>';
    const id = document.getElementById("sess");
    fetch('/Sessions/ListSessions/' + Cookies.get("id") +'/'+ id.value , {method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                sessHTML += `<tr>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.Hours}</td>` +
                    `<td>${student.Pay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `<td>${student.Review}</td>` +
                    `</tr>`;
            }
        }
        sessHTML += '</table>';
        document.getElementById("SessionOne").innerHTML = sessHTML;
    });
}

function editSubject(event) {
    const id = event.target.getAttribute("data-id");
    if (id === null) {
        document.getElementById("editHeading").innerHTML = 'Add new subject:';
        document.getElementById("subjectID").value = '';
    }else {
        document.getElementById("editHeading").innerHTML = 'Editing subject:';
        document.getElementById("subjectID").value = event.target.getAttribute("data-id");
    }
    document.getElementById("subjectName").value = '';
    document.getElementById("studentID").value = '';
    document.getElementById("tutorID").value = '';

    document.getElementById("first").style.display = 'none';
    document.getElementById("create1").style.display = 'none';
    document.getElementById("ChangeInfo").style.display = 'none';
    document.getElementById("editSub").style.display = 'block';
}

function saveEditSubject(event) {
    event.preventDefault();
    const id = document.getElementById("subjectID").value;
    const form = document.getElementById("SubForm");
    const formData = new FormData(form);
    let apiPath = '';
    if (id === '') {
        apiPath = '/Subjects/new';
    } else {
        apiPath = '/Subjects/change';
    }
    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoading()
        }
    });
}

function cancelEditSubject(event) {
    event.preventDefault();
    document.getElementById("first").style.display = 'block';
    document.getElementById("create1").style.display = 'block';
    document.getElementById("ChangeInfo").style.display = 'block';
    document.getElementById("editSub").style.display = 'none';
}

function deleteSub(event){
    const ok = confirm("Are you sure?");
    if (ok === true) {
        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);
        fetch('/Subjects/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoading();
                }
            }
        );
    }
}

function editSession(event) {
    const id = event.target.getAttribute("data-id");
    if (id === null) {
        document.getElementById("editHead").innerHTML = 'Add new session:';
        document.getElementById("sessionID").value = '';
    }else {
        document.getElementById("editHead").innerHTML = 'Editing session:';
        document.getElementById("sessionID").value = event.target.getAttribute("data-id");
    }
    document.getElementById("review").value = '';
    document.getElementById("hours").value = '';
    document.getElementById("pay").value = '';
    document.getElementById("grade").value = '';
    document.getElementById("studentI").value = '';
    document.getElementById("tutorI").value = '';

    document.getElementById("first").style.display = 'none';
    document.getElementById("create1").style.display = 'none';
    document.getElementById("ChangeInfo").style.display = 'none';
    document.getElementById("editSes").style.display='block';
}

function saveEditSession(event) {
    event.preventDefault();
    const id = document.getElementById("sessionID").value;
    const form = document.getElementById("SesForm");
    const formData = new FormData(form);
    let apiPath = '';
    if (id === '') {
        apiPath = '/Sessions/new';
    } else {
        apiPath = '/Sessions/change';
    }
    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoading()
        }
    });
}

function cancelEditSession(event) {
    event.preventDefault();
    document.getElementById("first").style.display = 'block';
    document.getElementById("create1").style.display = 'block';
    document.getElementById("ChangeInfo").style.display = 'block';
    document.getElementById("editSes").style.display = 'none';
}

function deleteSession(event){
    const ok = confirm("Are you sure?");
    if (ok === true) {
        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);
        fetch('/Sessions/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoading();
                }
            }
        );
    }
}

function editInfo(event) {
    const id = event.target.getAttribute("data-id");
    if (id === null) {
        document.getElementById("editHe").innerHTML = 'Add new information:';
        document.getElementById("infoID").value = '';
    }else {
        document.getElementById("editHe").innerHTML = 'Editing information:';
        document.getElementById("infoID").value = event.target.getAttribute("data-id");
    }
    document.getElementById("thours").value = '';
    document.getElementById("tpay").value = '';
    document.getElementById("rpay").value = '';
    document.getElementById("tgrade").value = '';
    document.getElementById("student").value = '';
    document.getElementById("tutor").value = '';

    document.getElementById("second").style.display = 'none';
    document.getElementById("create2").style.display = 'none';
    document.getElementById("ChangeSubject").style.display = 'none';
    document.getElementById("editInfo").style.display='block';
}

function saveEditInfo(event) {
    event.preventDefault();
    const id = document.getElementById("infoID").value;
    const form = document.getElementById("InfoForm");
    const formData = new FormData(form);
    let apiPath = '';
    if (id === '') {
        apiPath = '/Information/new';
    } else {
        apiPath = '/Information/change';
    }
    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            pageLoading()
        }
    });
}

function cancelEditInfo(event) {
    event.preventDefault();
    document.getElementById("first").style.display = 'block';
    document.getElementById("create1").style.display = 'block';
    document.getElementById("ChangeInfo").style.display = 'block';
    document.getElementById("editInfo").style.display = 'none';
}

function deleteInfo(event){
    const ok = confirm("Are you sure?");
    if (ok === true) {
        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);
        fetch('/Information/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {
                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoading();
                }
            }
        );
    }
}