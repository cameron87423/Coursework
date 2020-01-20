function pageLoading() {
    debugger;
    let name = Cookies.get("id");
    let myHTML = '<div/>'
        + '<img src="/client/img/logo.PNG"  alt="Logo"/>';
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("heading").innerHTML ="welcome parent: " + name;

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
        '<th>StudentID</th>' +
        '<th>First name</th>' +
        '<th>SubjectID</th>' +
        '<th>Subject</th>' +
        '<th>Tutor ID</th>' +
        '<th>Tutor name</th>' +
        '<th>Tutor surname</th>' +
        '<th>rating</th>' +
        '</tr>';
    fetch('/Students/TutorList/' + Cookies.get("stuid"),{method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                tutorsHTML += `<tr>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.StudentName}</td>` +
                    `<td>${student.SubjectID}</td>` +
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

    let sessionsHTML = `<table>` +
        '<tr>' +
        '<th>SessionID</th>' +
        '<th>StudentID</th>' +
        '<th>TutorID</th>' +
        '<th>Hours</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
        '<th>Review</th>' +
        '</tr>';
    fetch('/Sessions/StudentSessions/' + Cookies.get("stuid"), {method: 'get'}
    ).then(response => response.json()
    ).then(responseDa => {
        if (responseDa.hasOwnProperty('error')) {
            alert(responseDa.error);
        } else {
            for (let student of responseDa) {
                sessionsHTML += `<tr>` +
                    `<td>${student.SessionID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.TutorID}</td>` +
                    `<td>${student.Hours}</td>` +
                    `<td>${student.Pay}</td>` +
                    `<td>${student.Grade}</td>` +
                    `<td>${student.Review}</td>` +
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
    document.getElementById("second").style.display='block';
    document.getElementById("ChangeSubject").style.display='block';

    let infoHTML = `<table>` +
        '<tr>' +
        '<th>Info ID</th>' +
        '<th>Student ID</th>' +
        '<th>Tutor ID</th>' +
        '<th>Total hours</th>' +
        '<th>Total pay</th>' +
        '<th>Remaining pay</th>' +
        '<th>Target grade</th>' +
        '</tr>';
    fetch('/Information/Students/' + Cookies.get("stuid"), {method: 'get'}
    ).then(response => response.json()
    ).then(responseDat => {
        if (responseDat.hasOwnProperty('error')) {
            alert(responseDat.error);
        } else {
            for (let student of responseDat) {
                infoHTML += `<tr>` +
                    `<td>${student.InformationID}</td>` +
                    `<td>${student.StudentID}</td>` +
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
    let infoOHTML = `<table>` +
        '<tr>' +
        '<th>InfoID</th>' +
        '<th>StudentID</th>' +
        '<th>TutorID</th>' +
        '<th>Total Hours</th>' +
        '<th>Total Pay</th>' +
        '<th>Remaining Pay</th>' +
        '<th>Target Grade</th>' +
        '</tr>';
    const id = document.getElementById("Info");
    fetch('/Information/One/' + Cookies.get("stuid") +'/'+ id.value,{method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                infoOHTML += `<tr>` +
                    `<td>${student.InfoID}</td>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.TutorID}</td>` +
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
        '<th>Hour(s)</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
        '<th>Review</th>' +
        '</tr>';
    const id = document.getElementById("sess");
    fetch('/Sessions/ListSessions/' + Cookies.get("stuid") +'/'+ id.value , {method: 'get'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            for (let student of responseData) {
                sessHTML += `<tr>` +
                    `<td>${student.StudentID}</td>` +
                    `<td>${student.TutorID}</td>` +
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

function profile(){
    Cookies.set("user","parent");
    window.location.href = '/client/profile.html';
}