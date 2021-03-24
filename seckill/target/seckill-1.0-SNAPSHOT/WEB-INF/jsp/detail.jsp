<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 引入jstl -->
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页面</title>
    <%@include file="common/head.jsp" %>
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1097767_cq35lbisk3w.css">
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
            <h1>剩余数量：${seckill.number}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <!-- 显示time图标 -->
                <i class="iconfont icon-shijian" style="font-size: 30px"></i>
                <!-- 展示倒计时 -->
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>

<!--登录弹出层，输入电话 开始  -->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <!-- <span class="glyphicon glyphicon-phone"></span> -->
                    <i class="iconfont icon-shouji-"></i>秒杀电话注册一下，方可秒杀
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey" placeholder="请填写手机号^0^"
                               class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <!-- 验证信息 -->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <i class="iconfont icon-shouji"></i>提交
                </button>
            </div>
        </div>
    </div>
</div>
<!--登录弹出层，输入电话  结束-->


</body>
<!-- 开始编写交互逻辑 -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!-- <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script> -->

<script src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!--使用cdn 获取 公共js   -->
<!-- jq cookie 操作插件 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.cookie.min.js"></script>
<!-- jq countDown倒计时插件 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.countdown.min.js"></script>


<script src="${pageContext.request.contextPath}/resources/script/seckill.js" type="text/javascript"></script>

<script>
    $(function () {
        //alert("${seckill.seckillId}");
        //alert("${seckill.startTime.time}");
        //alert("${seckill.endTime.time}");
        //使用EL表达式传入参数---值是进入这个详情页的时候 就传过来的seckill对象
        seckill.detail.init(
            //通过.time拿到Date类型的毫秒时间
            {
                seckillId: "${seckill.seckillId}",
                startTime: "${seckill.startTime.time}",
                endTime: "${seckill.endTime.time}"
            }
        );
    });
</script>
</html>