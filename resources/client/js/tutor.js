function pageLoading() {
    debugger;
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
    document.getElementById("ISearch").addEventListener("click", InfoO);
    document.getElementById("SSearch").addEventListener("click", SessionO);


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
                    `<button class='editSub' data-id='${student.TutorID}'>Edit</button>` +
                    `<button class='deleteSub' data-id='${student.TutorID}'>Delete</button>` +
                    `</td>` +
                    `</tr>`;
            }
        }
        studentsHTML += '</table>';
        document.getElementById("StudentList").innerHTML = studentsHTML;
    });

    let sessionsHTML = `<table>` +
        '<tr>' +
        '<th>SessionID</th>' +
        '<th>TutorID</th>' +
        '<th>StudentID</th>' +
        '<th>Hours</th>' +
        '<th>Pay</th>' +
        '<th>Grade</th>' +
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
                    `<td class="last">` +
                    `<button class='editSes' data-id='${student.StudentID}'>Edit</button>` +
                    `<button class='deleteSes' data-id='${student.StudentID}'>Delete</button>` +
                    `</td>` +
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
                    `</tr>`;
            }
        }
        sessHTML += '</table>';
        document.getElementById("SessionOne").innerHTML = sessHTML;
    });
}