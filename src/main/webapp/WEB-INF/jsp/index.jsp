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
    <div id="pie_chart_container" class="col-xs-12 col-sm-5 col-md-4 col-lg-3">
        <div id="pie_chart_signature"><b>Куда уходят деньги</b></div>
        <div id="pie_chart" class="chart">
        </div>
    </div>
    <div id="histogram_container" class="col-xs-12 col-sm-7 col-md-6 col-lg-4">
        <div id="histogram" class="chart">
        </div>
        <div id="histogram_signature"><b>Затраты по месяцам</b></div>
    </div>
</div>


<script type="text/javascript">
    google.charts.load('current', {'packages': ['corechart', 'bar']});
    var today = new Date();

    google.charts.setOnLoadCallback(drawPieChart);
    $('#pie_chart_signature').append("<p><b>(" + getYearMonth(today) + ")</b></p>");
    function drawPieChart() {
        var jsonData = $.ajax({
            url: "/homebudget/rest/expenses?groupBy=category",
            dataType: "json",
            async: false
        }).responseJSON;

        var array = new Array(jsonData.length + 1);
        array[0] = ['category', 'amount'];

        jsonData.forEach(function (item, i, arr) {
            array[i + 1] = [item.key.name, item.value];
        });

        var data = google.visualization.arrayToDataTable(array);

        var options = {
            titlePosition: 'none',
            legend: 'none'
        };

        var chart = new google.visualization.PieChart(document.getElementById('pie_chart'));

        chart.draw(data, options);
    }


    google.charts.setOnLoadCallback(drawHistogram);
    var from = yearBeforeIsoDate(today);
    var to = isoDate(today);
    $('#histogram_signature').append("<b> (" + from + " - " + to + ")</b></p>");

    function drawHistogram() {
        var jsonData = $.ajax({
            url: "/homebudget/rest/expenses?groupBy=month&start=" + from + "&end=" + to,
            dataType: "json",
            async: false
        }).responseJSON;

        var array = new Array(jsonData.length + 1);
        array[0] = ['Месяц', 'Затраты'];

        jsonData.forEach(function (item, i, arr) {
            array[i + 1] = [item.key[0] + "-" + item.key[1], item.value];
        });

        var data = google.visualization.arrayToDataTable(array);

        var options = {
            title: 'Затраты по месяцам',
            chartArea: {width: '50%'},
            hAxis: {
                title: 'Всего потрачено',
                minValue: 0
            },
            vAxis: {
                title: 'Месяц'
            },
            legend: {position: "none"}
        };

        var chart = new google.charts.Bar(document.getElementById('histogram'));

        chart.draw(data, options);
    }

    $(window).resize(function () {
        drawPieChart();
        drawHistogram();
    });

</script>
</body>
</html>