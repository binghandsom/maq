/**
 * 
 */
$(function() {

	$("#marriage").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#workingProvince").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});

	$("#workingCity").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#homeProvince").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#homeCity").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#eduLevel").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#haveChild").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});

	$("#salary-range").slider({
		range : true,
		min : 0,
		max : 20000,
		step : 500,
		values : [ 3000, 4000 ],
		slide : function(event, ui) {
			$("#salaryRangeShow").html(ui.values[0] + "~" + ui.values[1]);
			$("input[name='minSalary']").val(ui.values[0]);
			$("input[name='maxSalary']").val(ui.values[1]);
		}

	});
	$("#age-range").slider({
		range : true,
		min : 14,
		max : 60,
		step : 1,
		values : [ 18, 25 ],
		slide : function(event, ui) {
			$("#ageRangeShow").html(ui.values[0] + "岁~" + ui.values[1] + "岁");
			$("input[name='minAge']").val(ui.values[0]);
			$("input[name='maxAge']").val(ui.values[1]);
		}

	});
	$("#height-range").slider(
			{
				range : true,
				min : 140,
				max : 210,
				step : 1,
				values : [ 160, 180 ],
				slide : function(event, ui) {
					$("#heightRangeShow").html(
							ui.values[0] + "cm~" + ui.values[1] + "cm");
					$("input[name='minHeight']").val(ui.values[0]);
					$("input[name='maxHeight']").val(ui.values[1]);
				}
			});
	$("#houseAndCar").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
});