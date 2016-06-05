/**
 * 编辑照片页面引用
 */
$(function() {
	$("#addPhotoImgBtn").click(function() {
		// 加号图片点击触发文件选择事件
		$("#photoFiles").click();

	}).hover(
			function() {
				$(this).attr("src",
						ctx + "/resources/img/system/add_photo72pxHOVER.png");
				$(this).css({
					"cursor" : "pointer"
				});
			},
			function() {
				$(this).attr("src",
						ctx + "/resources/img/system/add_photo72px.png");
			});

});

function fileChanged() {
	var formData = new FormData();
	var files = $("#uploadPhotosForm")[0].files;
	for (var int = 0; int < files.length; int++) {
		formData.append("file" + int, files[int]);
	}
	$.ajax({
		url : ctx + "/userMainInfo/isPictureFiles",
		type : 'POST',
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(returndata) {
			alert(returndata);
		},
		error : function(returndata) {
			alert(returndata);
		}
	});
}
