$(function() {

	$("#jobKind").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});

	$("#companyKind").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#busyDegree").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
});