function getTable(containerTemplateClass, tableClass) {
    var $tableContainer = $('.' + containerTemplateClass + '[hidden]').clone();
    $tableContainer.find('table').addClass(tableClass);
    return $tableContainer.removeAttr('hidden').html();
}

function appendCourseLink($containerToAppend, course, onCourseLinkClick) {
    var courseLink = '<div><a href="#" id="' + course.id + '">'  + course.title + ' </a></div>';
    var $courseLink = $(courseLink);
    $courseLink.one('click', function (event) {
        onCourseLinkClick(event)
    });
    $containerToAppend.append($courseLink)
}

function establishServerSideErrorsHandler() {
    $(document).ajaxError(function (event, jqXHR, options) {
        if (jqXHR.status == 500) {
            $('body').html('<h1>Some problems with server have occurred. Please, try latter</h1>');
        }
    })
}
