
google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['Năm', 'Doanh Thu \n (Triệu)'],
        ['2020', 1000],
        ['2021', 1170],
        ['2022', 1030],
        ['2023', 660],
    ]);

    var options = {
        title: '',
        hAxis: {title: 'Năm', titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0}
    };

    var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}