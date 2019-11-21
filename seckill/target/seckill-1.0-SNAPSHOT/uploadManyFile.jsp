<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>ajax图片上传</title>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
<script>
//多文件上传
function mutiFiles(obj){
    //创建一个FormData对象：用一些键值对来模拟一系列表单控件：即把form中所有表单元素的name与value组装成一个queryString
    var form = new FormData();
    var fileObj = obj.files;//拿到所有上传的文件 多文件就会使数组形式的
    var length = fileObj.length;
	for (var i = 0; i < length; i++) {
			form.append("doc", fileObj[i]);
	}
	$.ajax({
			type : "post",
			data : form,
			url : "seckill/mutlUpload",
			contentType : false, //必须false才会自动加上正确的Content-Type
			processData : false,//必须false才会避开jQuery对 formdata 的默认处理  XMLHttpRequest会对 formdata 进行正确的处理
			success : function(data) {
				console.log(data);
				var len = data.length;
				for (var i = 0; i < len; i++) {
					var datajson = data[i];
					$("#f_tbody_m").append("<tr class='f_item'>"
											+ "<td><img src='"+datajson.url+"' alt='预览图像' width='40' height='40' /></td>"
											+ "<td>" + datajson.oldname+"</td>"
											+ "<td>"+datajson.size + "</td>"
											+"</tr>");
						}
					}
				});
	}
</script>

</head>
<body>
	<!--多文件上传-->
	<div class="formmutibox">
		<p class="title">多文件上传（多选）</p>
		<!--多文件需要加上 multiple属性 -->
		<input type="file" class="file" name="files" accept="image/jpeg,image/png" onchange="mutiFiles(this)"   multiple/>
		<div class="files">
			<table>
				<thead>
					<tr>
						<td class="filelook33">文件预览</td>
						<td class="filename33">文件名</td>
						<td class="filesize33">大小</td>
					</tr>
				</thead>
				<tbody id="f_tbody_m">
				</tbody>
			</table>
		</div>
	</div>
	<div class="clear"></div>
</body>
</html>