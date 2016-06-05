/**
 * 
 */
$(function() {

	$("#drinking").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});

	$("#smoking").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#houseAndCar").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#cooking").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#housework").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#deposit").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});

});