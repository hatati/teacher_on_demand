(function () {

    var teachers = [];

    function createListElement(base64_image, name, email, last_job, last_education, index) {
        let htmlString = `<div class="cell" data-id="` + index + `">
		  <img class="cell-image" src="data:image/png;base64, ` + base64_image + `"/>
		  <div class="cell-name text-element">` + name + `</div>
		  <div class="cell-email text-element">` + email + `</div>
		  <div class="cell-job text-element">` + last_job + `</div>
		  <div class="cell-education text-element">` + last_education + `</div>
		</div>`;

        return htmlString;
    }

    /**
     * @param {String} html representing a single element
     * @return {Element}
     */
    function htmlToElement(html) {
        let template = document.createElement('template');
        html = html.trim(); // Never return a text node of whitespace as the result
        template.innerHTML = html;
        return template.content.firstChild;
    }

    function populateList(data) {
        teachers = data;
        for (var i = 0; i < teachers.length; i++) {
            let teacher = teachers[i];
            let listElementString = createListElement(
                teacher.image,
                teacher.name,
                teacher.email,
                teacher.educations[0].where,
                teacher.jobs[0].where,
                i);

            let listElement = htmlToElement(listElementString);
            listElement.addEventListener("click", showTeacherProfile);

            $('.list').append(listElement);
        }
    }

    function removeView(event) {
        console.log('closeButton(event)');
        let closeButton = event.target;
        closeButton.parentNode.parentNode.removeChild(closeButton.parentNode);
    }

    function createTeacherView(teacher) {
        let htmlString = `<div id="information-box" class="centered">
              <div class="close-button">x</div>
              <div id="image-container">
                <img id="profile-image" src="data:image/png;base64, ` + teacher.image +`" alt="profile image">
              </div>
              <div id="label-group-1">
                <p class="label">Name:</p>
                <p class="text">` + teacher.name + `</p>
                <p class="label">Email:</p>
                <p class="text">` + teacher.email + `</p>
                <p class="label">City:</p>     
                <p class="text">` + teacher.city + `</pc>
              </div>
              <div id="jobs">
              <p class="label">Jobs:</p>
                <ul id="job-list">
                </ul>
              </div> 
              <div id="education">
                <p class="label">Education:</p>
                <ul id="education-list">
                </ul>
              </div>
              <div id="label-group-2">
                <p class="label">Description:</p>
                <p class="text">` + teacher.description + `</p>
                <p class="label">Skills:</p>
                <ul id="skills-list">
                </ul>     
              </div>
            </div>`;

        let htmlStringElement = htmlToElement(htmlString);

        let jobList = $(htmlStringElement).find('#job-list');
        for (var i = 0; i < teacher.jobs.length; i++) {
            jobList.append(`<li>` + teacher.jobs[i].description +`</li>`)
        }

        let educationList = $(htmlStringElement).find('#education-list');
        for (var i = 0; i < teacher.educations.length; i++) {
            educationList.append(`<li>` + teacher.educations[i].description + `</li>`)
        }

        let skillsList = $(htmlStringElement).find('#skills-list');
        for (var i = 0; i < teacher.skills.length; i++) {
            skillsList.append(`<li>` + teacher.skills[i] + `</li>`)
        }

        let closeButton = $(htmlStringElement).find('.close-button');
        closeButton.on('click', removeView);

        return htmlStringElement;
    }

    function showTeacherProfile(event) {
        let id = event.target.dataset.id;
        let teacherView = createTeacherView(teachers[id]);
        $('body').append(teacherView);
    }

    $.ajax({
        type: "POST",
        url: "/get/teachers",
        success: populateList
    });
}());
