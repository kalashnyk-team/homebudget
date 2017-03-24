<%--
  Created by IntelliJ IDEA.
  User: Sergii
  Date: 13.03.2017
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
    <title>Главная</title>
    <script src="<c:url value="https://www.gstatic.com/charts/loader.js"/>"></script>
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="fluid-container">
    <div id="pie_chart_container" class="col-xs-12 col-sm-4 col-md-3 col-lg-3">
        <div id="pie_chart" class="chart">
        </div>
        <div id="pie_chart_signature"><b>Куда уходят деньги</b></div>
    </div>
    <form>
        <label for="start">Начало периода:</label>
        <input id="start" type="date">
        <label for="end">Конец периода:</label>
        <input id="end" type="date">
    </form>
    <div id="histogram_container" class="col-xs-12 col-sm-8 col-md-9 col-lg-9">
        <div id="histogram" class="chart">
        </div>
        <div id="histogram_signature"><b>Затраты по месяцам</b></div>
    </div>
</div>


<script type="text/javascript">
    var lang = getUserLanguage();
    if (lang == undefined)
        lang = 'en-US';
    google.charts.load('current', {'packages': ['corechart'], 'language': lang});
    var today = new Date();

    google.charts.setOnLoadCallback(drawPieChart);
    $('#pie_chart_signature').append("<p><b>(" + getYearMonth(today, lang) + ")</b></p>");

    var expensesByCategories = $.ajax({
        url: "/homebudget/rest/expenses?groupBy=category",
        dataType: "json",
        async: false
    }).responseJSON;

    function drawPieChart() {

        var array = new Array();
        array[0] = ['category', 'amount'];

        expensesByCategories.forEach(function (item, i, arr) {
            if (i < 6) {
                array[i + 1] = [item.key.name, item.value];
            } else {
                var amount = array[6][1] + item.value;
                array[6] = ['Прочее', amount];
            }
        });

        var data = google.visualization.arrayToDataTable(array);

        var options = {
            chartArea: {width: '95%', height: '95%'},
            titlePosition: 'none',
            legend: 'none',
            pieHole: 0.4
        };

        var chart = new google.visualization.PieChart(document.getElementById('pie_chart'));

        chart.draw(data, options);
    }


    google.charts.setOnLoadCallback(drawHistogram);
    var from = yearBeforeIsoDate(today);
    var to = isoDate(today);
    $('#histogram_signature').append("<b> (" + from + " - " + to + ")</b></p>");

    var expensesByMonthes = $.ajax({
        url: "/homebudget/rest/expenses?groupBy=month&start=" + from + "&end=" + to,
        dataType: "json",
        async: false
    }).responseJSON;

    function drawHistogram() {

        var data = new google.visualization.DataTable();

        data.addColumn('date', 'Месяц');
        data.addColumn('number', 'Затраты');

        expensesByMonthes.forEach(function (item, i, arr) {
            var date = new Date(item.key[0], item.key[1] - 1);
            var yearMonth = getYearMonth(date, lang);
            data.addRow([{v: date, f: yearMonth}, item.value]);
        });

        var options = {
            chartArea: {width: '85%', height: '80%'},

            vAxis: {
                format: 'decimal',
            },
            legend: {
                position: "none"
            },
            trendlines: {0: {
                color: 'red'
            }}
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('histogram'));
        google.visualization.events.addListener(chart, 'onmouseover', function(e){
            $('svg *:contains("* x")').each(function(){
                $(this).text('')
            })
        })
        chart.draw(data, options);
    }

    $(window).resize(function () {
        drawPieChart();
        drawHistogram();
    });

</script>
</body>
</html>