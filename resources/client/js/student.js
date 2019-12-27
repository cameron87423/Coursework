function pageLoading() {
    let name = Cookies.get("id");
    let myHTML = '<div/>'
        + '<img src="/client/img/logo.jfif"  alt="Logo"/>';
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("heading").innerHTML = "welcome student: " + name

    document.getElementById("first").style.display="block";
    document.getElementById("ChangeInfo").style.display='block';
    document.getElementById("second").style.display='none';
    document.getElementById("ChangeSubject").style.display='none';
    document.getElementById("ISearch").addEventListener("click", InfoO);
    document.getElementById("SSearch").addEventListener("click", SessionO);
    let I = document.getElementById("changeI");
    I.onclick = Info;
    let P =document.getElementById("changeS");
    P.onclick = pageLoading;

    let tutorsHTML = `<table>` +
        '<tr>' +
        '<th>First name</th>' +
        '<th>Subject</th>' +
        '<th>Tutor ID</th>' +
        '<th>Tutor name</th>' +
        '<th>Tutor surname</th>' +
        '<th>rating</th>' +
        '</tr>';
    fetch('/Students/TutorList/' + Cookies.get("id"),{method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                tutorsHTML += `<tr>` +
                    `<td>${student.StudentName}</td>` +
                    `<td>${student.SubjectName}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.TutorName}</td>` +
                    `<td>${student.TutorSurname}</td>` +
                    `<td>${student.Rating}</td>` +
                    `</tr>`;
            }
        }
        tutorsHTML += '</table>';
        document.getElementById("TutorList").innerHTML = tutorsHTML;
    });

    let subjectsHTML = `<table>` +
        '<tr>' +
        '<th>Subject</th>' +
        '<th>TutorID</th>' +
        '</tr>';
    fetch('/Subjects/StudentSubjects/' + Cookies.get("id"),{method: 'get'}
    ).then(response => response.json()
    ).then(sub => {
        if (sub.hasOwnProperty('error')) {
            alert(sub.error);
        } else {
            for (let student of sub) {
                subjectsHTML += `<tr>` +
                    `<td>${student.SubjectName}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `</tr>`;
            }
        }
        subjectsHTML += '</table>';
        document.getElementById("SubjectsList").innerHTML = subjectsHTML;
    });

    let sessionsHTML = `<table>` +
        '<tr>' +
        '<th>TutorID</th>' +
        '<th>Hours</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
        '</tr>';
    fetch('/Sessions/StudentSessions/' + Cookies.get("id"), {method: 'get'}
    ).then(response => response.json()
    ).then(responseDa => {
        if (responseDa.hasOwnProperty('error')) {
            alert(responseDa.error);
        } else {
            for (let student of responseDa) {
                sessionsHTML += `<tr>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.Hours}</td>` +
                    `<td>${student.Pay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `</tr>`;
            }
        }
        sessionsHTML += '</table>';
        document.getElementById("SessionsList").innerHTML = sessionsHTML;
    });
}

function Info(){
    document.getElementById("first").style.display='none';
    document.getElementById("ChangeInfo").style.display='none';
    document.getElementById("second").style.display=('block');
    document.getElementById("ChangeSubject").style.display='block';

    let infoHTML = `<table>` +
        '<tr>' +
        '<th>Tutor ID</th>' +
        '<th>Total hours</th>' +
        '<th>Total pay</th>' +
        '<th>Remaining pay</th>' +
        '<th>Target grade</th>' +
        '</tr>';
    fetch('/Information/Students/' + Cookies.get("id"), {method: 'get'}
    ).then(response => response.json()
    ).then(responseDat => {
        if (responseDat.hasOwnProperty('error')) {
            alert(responseDat.error);
        } else {
            for (let student of responseDat) {
                infoHTML += `<tr>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.THours}</td>` +
                    `<td>${student.TotalPay}</td>` +
                    `<td>${student.RemainingPay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `</tr>`;
            }
        }
        infoHTML += '</table>';
        document.getElementById("InformationList").innerHTML = infoHTML;
    });
}

function InfoO(event){
    event.preventDefault();
    debugger;
    let infoOHTML = `<table>` +
        '<tr>' +
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
        '<th>TutorID</th>' +
        '<th>StudentID</th>' +
        '<th>Hour(s)</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
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
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.Hours}</td>` +
                    `<td>${student.Pay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `</tr>`;
            }
        }
        sessHTML += '</table>';
        document.getElementById("SessionOne").innerHTML = sessHTML;
    });
}