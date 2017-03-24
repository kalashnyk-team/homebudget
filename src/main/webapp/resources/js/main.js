/**
 * Created by Sergii on 26.02.2017.
 */

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

function getYearMonth(date, lang) {
    var year = date.getFullYear();
    var monthNames_en = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"];

    var monthNames_ru = ["январь", "февраль", "март", "апрель", "май", "июнь",
        "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"];

    if (lang =='ru'){
        return monthNames_ru[date.getMonth()] + ", " + year
    } else {
        return monthNames_en[date.getMonth()] + ", " + year;
    }
}

function getUserLanguage() {
    var lang = navigator.language || navigator.systemLanguage;
    return lang;
}