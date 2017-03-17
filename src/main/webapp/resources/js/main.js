/**
 * Created by Sergii on 26.02.2017.
 */
$(document).ready(function () {
    var today = isoDate(new Date());
    $('#datePicker').val(today);
});

function isoDate(date) {
    var day = ("0" + date.getDate()).slice(-2);
    var month = ("0" + (date.getMonth() + 1)).slice(-2);

    return date.getFullYear() + "-" + (month) + "-" + (day);
}

function yearBeforeIsoDate(date) {
    var day = "01";
    var month;
    var year;

    if ((date.getMonth() + 1) != 12) {
        year = date.getFullYear() - 1;
        month = ("0" + (date.getMonth() + 2)).slice(-2)
    } else {
        year = date.getFullYear();
        month = "01";
    }

    return year + "-" + month + "-" + day;
}

function getYearMonth(date) {
    var year = date.getFullYear();
    var monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    return monthNames[date.getMonth()] + ", " + year;
}