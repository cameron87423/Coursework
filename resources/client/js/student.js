function pageLoading() {
    debugger;
    let name = Cookies.get("id");
    let myHTML = '<div/>'
        + '<img src="/client/img/logo.jfif"  alt="Logo"/>';
    document.getElementById("imageDiv").innerHTML = myHTML;
    document.getElementById("heading").innerHTML = "welcome student: " + name;

    let tutorsHTML = `<table>` +
        '<tr>' +
        '<th>First name</th>' +
        '<th>Subject</th>' +
        '<th>Tutor ID</th>' +
        '<th>Tutor name</th>' +
        '<th>Tutor surname</th>' +
        '<th>rating</th>' +
        '</tr>';
    fetch('/Students/TutorList/' + Cookies.get("id"), {method: 'get'}
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
}
