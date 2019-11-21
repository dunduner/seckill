//seckill秒杀逻辑
var seckill = {
		//封装秒杀的接口地址
		URL:{
			timeNow:function(){
				return "/seckill/seckill/time";
			},
			exposer:function(seckillId){
				return "/seckill/seckill/"+seckillId+"/exposer";
			},
			execution:function(seckillId,md5){
				return "/seckill/seckill/"+seckillId+"/"+md5+"/execution";
			}
		},
		//秒杀逻辑，展示时间的函数
		countdown:function(seckillId,now,start,end){
			var seckillBox = $("#seckill-box");
			//系统当前时间，商品的id ，商品的秒杀开始 和结束时间
			if(now<start){
				//没有开始  时间倒计时
				//秒杀未开始 计时时间绑定
				startTime=parseInt(start );//把字符串转成int型
				var killTime= new Date(startTime);
				//alert(killTime);
				seckillBox.countdown(killTime,function(res){
					//时间格式
					var format = res.strftime('秒杀倒计时:%D天 %H时 %M分 %S秒');
					seckillBox.html(format);
					//时间倒计时以后完成的事件
				}).on('finish.countdown',function(){//告诉插件时间到了以后干嘛，名字必须是finish.countdown
					//获取秒杀地址  控制实现逻辑 执行秒杀按钮
					//seckillbox为span的节点
					//TODO
					seckill.handlerSeckill(seckillId, seckillBox);
				});
				
			}else if(now>end){
				//已经结束
				seckillBox.html("已经结束");
			}else{
				//开始秒杀
				seckill.handlerSeckill(seckillId, seckillBox);
			}
		},
		//详情页秒杀逻辑
		detail:{
			//详情页初始化代码
			init:function(params){
				console.log(params);
				
				//在cookie查找手机号
				var killPhone= $.cookie("killPhoneKey");
				
				var seckillId= params.seckillId;
				var startTime= params.startTime;
				var endTime= params.endTime;
				//验证手机号
				
				if(seckill.validatePhone(killPhone)){
					//手机号没错，cookie中有值了
//					alert("jinlaile")
					//获取系统时间
					var timeNowUrl=seckill.URL.timeNow();//获取当前时间的接口地址
					$.get(timeNowUrl,{},function(res){
						//success: true, data: 1553136757262, error: null}
						console.log(res);
						var success = res.success;
						if(success){
							var tNow = res.data;//当前系统的时间
							//seckillId,now,start,over
							seckill.countdown(seckillId, tNow, startTime, endTime);
						}
					},"json");
					
				}else{
					//说明手机号不正确或者没有 
					//打开弹出层，让用户登陆
					var killPhoneModal = $("#killPhoneModal");
					killPhoneModal.modal({
						show:true,
						backdrop:'static',
						keyboard:false
					});
					//给提交按钮绑定时间
					$("#killPhoneBtn").click(function(){
						var killPhoneKey=$("#killPhoneKey").val();
						//调用验证手机号方法
						if(seckill.validatePhone(killPhoneKey)){
							//通过 放到cookie里面  expires单位是天
							$.cookie('killPhoneKey',killPhoneKey,{
								expires:7,path:'/seckill/seckill'
							});
							window.location.reload();
						}else{
							//不通过提示手机号不正确
							$("#killPhoneMessage").html("<label class='label label-danger'>手机号错误！-》</label>").show(3000);
						}
						
					});
				}
			}
		},
		//验证手机号的方法
		validatePhone:function(phone){
			if(phone !=null&& phone.length==11 && !isNaN(phone)){
				//手机号长度是正确的
				var phoneReg=/^[1][3,4,5,7,8][0-9]{9}$/;//通过正则表达式的形式验证手机号是否合法
	            if (phoneReg.test(phone)) {
	                return true;
	            } else {
	                return false;
	            }
			}else{
				//没有手机号或者错误的
				return false;
			}
		},
		//秒杀按钮展示函数
		handlerSeckill:function(seckillId,node){
			//seckillBox是想要操作的节点DOM对象
			node.hide().html('<buttion class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');//给按钮
			//暴漏秒杀地址
			$.post(seckill.URL.exposer(seckillId),{},function(result){
				//{"success":true,"data":{"exposed":true,"md5":"26a00ff44271e437bfe00848d8cf3a16","seckillId":3,"now":0,"start":0,"end":0},"error":null}
				if(result.success){
					var md5Res = result.data.md5;
					node.show();
					//对按钮进行单次点击
					$('#killBtn').one('click',function(){
						$(this).addClass('disabled');//不能点击了
						//执行秒杀
						$.post(seckill.URL.execution(seckillId, md5Res),{},function(result){
//							秒杀成功console.log(result);
							if(result.success){
								var killResult = result["data"];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];//秒杀成功等提示
								node.html('<span class="label label-success">'+stateInfo+'</span>');
							}else{
								var msg = result.data.stateInfo;
								node.html('<span class="label label-success">'+msg+'</span>');
							}
						});
					});
				}else{
					//没有暴露秒杀接口地址
				}
			});
				
			
		}
};

