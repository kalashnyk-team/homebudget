/**
 * Created by Sergii on 26.02.2017.
 */
$(document).ready(function () {
    var now = new Date();

    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);

    var today = now.getFullYear() + "-" + (month) + "-" + (day);


    $('#datePicker').val(today);
});

window.onload = function () {

    /* ---------  start // creating pie chart using HTML5 Canvas   -------------- */
    (function () {

        function drawPieChart(canvas, chartData, centerX, centerY, pieRadius) {
            var ctx;  // The context of canvas
            var previousStop = 0;  // The end position of the slice
            var totalAmount = 0;

            var totalCategories = chartData.length;

            // Count total donors
            for (var i = 0; i < totalCategories; i++) {
                console.log("total amount = " + totalAmount + " + " + chartData[i].value + "\n");
                totalAmount += chartData[i].value;

            }

            ctx = canvas.getContext("2d");
            ctx.clearRect(0, 0, canvas.width, canvas.heigh);

            var colorScheme = ["#2F69BF", "#A2BF2F", "#BF5A2F",
                "#BFA22F", "#772FBF", "#2F94BF", "#c3d4db"];


            for (var i = 0; i < totalCategories; i++) {

                //draw the sector
                ctx.fillStyle = colorScheme[i];
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.arc(centerX, centerY, pieRadius, previousStop, previousStop +
                    (Math.PI * 2 * (chartData[i].value / totalAmount)), false);
                ctx.lineTo(centerX, centerY);
                ctx.fill();

                // label's bullet
                var labelY = 20 * i + 10;
                var labelX = pieRadius * 2 + 20;

                ctx.rect(labelX, labelY, 10, 10);
                ctx.fillStyle = colorScheme[i];
                ctx.fill();

                // label's text
                ctx.font = "italic 12px sans-serif";
                ctx.fillStyle = "#222";
                var txt = chartData[i].key.name + " | " + chartData[i].value;
                ctx.fillText(txt, labelX + 18, labelY + 8);

                previousStop += Math.PI * 2 * (chartData[i].value / totalAmount);
            }
        }


        function loadData(dataUrl, canvas) {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', dataUrl, true);

            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4) {
                    if ((xhr.status >= 200 && xhr.status < 300) ||
                        xhr.status === 304) {
                        var jsonData = xhr.responseText;

                        var chartData = JSON.parse(jsonData);

                        drawPieChart(canvas,chartData, 100, 100, 98);

                    } else {
                        console.log(xhr.statusText);
                        tempContainer.innerHTML += '<p class="error">Error getting ' +
                            target.name + ": "+ xhr.statusText +
                            ",code: "+ xhr.status + "</p>";
                    }
                }
            }
            xhr.send();
        }

        loadData('/homebudget/rest/expenses/categories', document.getElementById("canvas"));

    })();
    /* ---------  creating pie chart using HTML5 Canvas //end  -------------- */
}