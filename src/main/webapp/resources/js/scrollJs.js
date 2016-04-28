$(function() {
	$('.demo1').bootstrapNews({
		newsPerPage: 5,
		autoplay: true,
		pauseOnHover: true,
		direction: 'up',
		newsTickerInterval: 4000,
		onToDo: function() {
			/**
			 * 在这里动态添加/删除
			 */
			$('.demo1').append(parseDom("xxx", "yyy", "#"));
		}
	});
});

function parseDom(imgSrc, info, href) {
	/*<li class="news-item">
												<table cellpadding="4">
													<tr>
														<td><img src="images/7.png" width="60" class="img-circle" /></td>
														<td>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam in venenatis enim... <a href="#">Read more...</a></td>
													</tr>
												</table>
											</li>
											<li class="news-item"><table cellpadding="4"><td src="xxx" width="60" class="img-circle"></td><td>yyy<a href="#">readMore</a></td></table></li>
											* */
	　　
	var li = document.createElement("li");
	var table = document.createElement("table");
	var tr = document.createElement("tr");
	var td1 = document.createElement("td");
	var td2 = document.createElement("td");
	var a = document.createElement("a");

	li.setAttribute("class", "news-item");
	table.setAttribute("cellpadding", "4");
	td1.setAttribute("src", imgSrc);
	td1.setAttribute("width", "60");
	td1.setAttribute("class", "img-circle");
	a.setAttribute("href", href);

	a.innerHTML="readMore";
	td2.innerHTML=info;

	td2.appendChild(a);
	table.appendChild(td1);
	table.appendChild(td2);
	li.appendChild(table);
	return li;
};