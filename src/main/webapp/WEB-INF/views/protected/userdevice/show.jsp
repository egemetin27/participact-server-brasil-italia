<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/bootstrap-responsive.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/docs.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/1.0/css/font-awesome.min.css"/>">
<script src="<c:url value="/resources/1.0/js/jquery-1.9.0.js"/>"></script>
<script src="<c:url value="/resources/1.0/js/bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<!--  <script src="<c:url value="/resources/1.0/js/highcharts/highcharts.js"/>"></script>
<script src="<c:url value="/resources/1.0/js/highcharts/modules/data.js"/>"></script>
<script src="<c:url value="/resources/1.0/js/highcharts/modules/exporting.js"/>"></script>-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datepicker.css"/>">
<script>
	$.getJSON('<c:url value="/userdevice/${userDevice.id}/stats" />', function(data) {
		$('#chartAssignedTaskUserDevice').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: 'Task assigned to User Device'//+data
            },
            xAxis: {
                categories: data.months
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Total task sent'
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'center',
                x: 0,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        'Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: false,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
                        style: {
                            textShadow: '0 0 3px black, 0 0 3px black'
                        }
                    }
                }
            },
            series: data.userDevice
        });
    });
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/layout/header.jsp" />
	<div class="portlet">
	<div class="portlet-title">
		<div class="caption">
		<span class="caption-subject font-green sbold uppercase"><spring:message code="deta.title" text="Device Details" /></span>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3 bs-docs-sidebar">
				<jsp:include page="/WEB-INF/templates/userdeviceSidenav.jsp">
					<jsp:param value="protected.userDevice.show" name="subsection" />
				</jsp:include>
			</div>
			<div class="span9">
				<div class="page-header">
					<h3>
						Device <c:out value="${userDevice.name}" /> (<c:out value="${userDevice.device.model}" />)
						<a class="btn btn-warning" href='<c:url value="/protected/userdevice/edit/${userDevice.id}" />' type="button">Edit</a>
					</h3>
				</div>
				    <div id="chartAssignedTaskUserDevice" style="width: 100; height: 400px; margin: 0 auto"></div>
				<br/>			
				<div class="row">
					<div class="span9">
				        <t:showuserdevice userDevice="${userDevice}" />
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<jsp:include page="/WEB-INF/templates/layout/footer.jsp" />
</body>
</html>