<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
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
    <title>单文件图片上传</title>

    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
    <script>
        //单文件上传
        function uploadFile(obj) {
            //创建一个FormData对象：用一些键值对来模拟一系列表单控件：即把form中所有表单元素的name与value组装成一个queryString
            var form = new FormData();
            var fileObj = obj.files[0];
            form.append("file", fileObj);

            $.ajax({
                type: "post",
                data: form,
                url: "seckill/onefileupload",
                contentType: false, //必须false才会自动加上正确的Content-Type
                /*
                 *必须false才会避开jQuery对 formdata 的默认处理
                 *XMLHttpRequest会对 formdata 进行正确的处理
                */
                processData: false,
                success: function (data) {
                    console.log(data.data);
                    var imgUrl = data.data.fileUrl;
                    var filesize = data.data.filesize;
                    var filename = data.data.filename;
                    var newFileName = data.data.newFileName;
                    var imgString = "图像预览:<img alt='预览图' src='" + imgUrl + "' width='10%'>";
                    $(".sinimg").html(imgString);
                    $(".filename").html(filename);
                    $(".filesize").html(filesize);
                    $(".xiazai").html("<a href='seckill/download?fileName="+newFileName+"&fileOldname="+filename+"'"+">下载</a>");
                    //$(".xiazai").html("<a target='_blank' href=" + imgUrl +"?attname="+filename+">下载</a>");
                }
            });
        }


    </script>

</head>
<body>
选择文件:
<!-- accept="image/jpeg,image/png"  -->
<input type="file" id="file" name="file" onchange="uploadFile(this)"/>
<br/>
<div class="sinimg">预览图:</div>
<p>文件名：<span class="filename"></span></p>
<p>大小：<span class="filesize"></span></p>
<div class="xiazai"></div>
</body>
</html>