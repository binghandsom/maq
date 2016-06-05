/**
 * 
 */
$(function() {
	$("#loveTimes").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});

	$("#dislikedShengxiao").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#whenShouldMarry").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#wantChild").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#liveWithParents").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#liveWithParentsInLaw").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
	$("#WhetherAcceptDistance").selectmenu({
		change : function(event, data) {
			alert(data.item.value);
		}
	});
});