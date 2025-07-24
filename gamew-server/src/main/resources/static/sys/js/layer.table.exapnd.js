/*layer table数据表自我拓展 */
$.extend({
	myRendar:function(params) {
		var pageSize = 10;
		if(params.pageSize != undefined) {
			pageSize = params.pageSize;
		}
		var page = { prev : "上一页",next : "下一页",limits:[15,30,45,60,75,90,105,200,500],limit:pageSize};
		//如果用户需要自定义则传入
		if(params.pageLayout != undefined) {
			//设置基础参数
			page = params.pageLayout;
		}else if(params.page == false) {
			page = false;
		}
		
		var callbackJson;
		var orginData;
		if(params.templet != undefined) {
			var s = {
					title: "操作",
					width: params.width||"",
					templet: params.templet
			}
			params.cols.push(s);
		}
		params.options = {
				elem : params.elem,
				url : params.url,
				height:params.height||"",
				method : params.method,
				page : page,
				autoSort: false,
				even: true,
				totalRow:params.totalRow||false,
				request : {
					pageName : 'pageNum',
					limitName : 'pageSize'
				},
				limit : pageSize,
				parseData : function(res) { // 将原始数据解析成 table 组件所规定的数据
					callbackJson = res;
					var total = 0;
					var resultData = [];
					if (res.data != undefined) {
						if (res.data.total != undefined) {
							total = res.data.total;
						}
						if (res.data.resultData != undefined) {
							resultData = res.data.resultData;
						}
						orginData = res;
					}
					if(params.page == false) {
						return {
							"code" : res.retcode,
							"msg" : res.retmsg,
							"data" : res.data
						};
					}else {
						return {
							"code" : res.retcode,
							"msg" : res.retmsg,
							"count" : total,
							"data" : resultData
						};
					}
					if(params.before != undefined) {
						params.before(1);
					}
				},
				done:function(res, curr, count){
					//调用前,先校验程序
					if(params.auth != undefined) {
						var datas = {"data":params.auth};
						$.ajax({
							type:"post",
							url:"/m/sys/user/validateAuths",
							dataType:"json",
							contentType: "application/json",
							data:JSON.stringify(datas), 
							async:false,
							success:function(data){
								if(data.status == 200) {
									if(data.data != "ignore") {
										$(data.data).each(function(i,o){
											//判断是否为头部按钮.
											if(o.isHead) {
												if(o.isAuth) {
													$("[data-type="+o.type+"]").removeClass("hide")
												}
											}else if(!o.isAuth) { //当isAuth等于false,表示没有该权限.
												$("[data-type="+o.type+"]").remove();
											}
										});
									}else {
										 //忽略校验权限. 
										$("[data-type]").removeClass("hide")
									}
								}else {
									console.log("权限校验失败");
								}
			          		}
						});
					}
					$(params.elem).hide();
					if(params.success != undefined) {
						params.success(orginData,curr,count);
					}
					//初始化select
					$(document).on("click",".dropdown-toggle",function(e){
						var height = $(this).height();
						var top = $(this).offset().top;
						var left = $(this).offset().left;
						var bodyWidth = document.body.offsetWidth;
						var ul = $(this).next("ul");
						var clientWidth = e.currentTarget.clientWidth;
						ul.css("position","fixed").css("top",(top+height+8)+"px").css("right",(bodyWidth-left-clientWidth)+"px");
					})
					$(document).on("keyup","input[type-close]",function(){
						var t = $(this);
						if(t.val().length > 0) {
							if(t.parent().find("span[class='layui-layer-setwin clear_content']").length ==0) {
								var top = t.parent().position().top;
								var left = t.parent().position().left;
								var width = t.parent().width();
								top = top+(t.parent().height()/2-7);
								left = (width-30)+left;
								t.parent().append('<span class="layui-layer-setwin clear_content" style="left:'+left+'px;top:'+top+'px"><a class="layui-layer-ico layui-layer-close layui-layer-close1" href="javascript:;"></a></span>');
							}
						}else {
							t.parent().find("span[class='layui-layer-setwin clear_content']").remove();
						}
					})
					$(document).on("click",".clear_content",function(){
						var t = $(this);
						t.parent().find("input").val("");
						t.remove();
					})
					if(params.callback != undefined) {
						params.callback(callbackJson)
					}
				},
				cols : [ params.cols],
				where:params.where
		}
		if(params.toolbar == undefined || params.toolbar == true) {
			//获取工具栏是否需要使用
			params.options.toolbar = "#toolBar";
		}
		params.table.render(params.options);
		$(document).on("click",".openMaxImg",function(e){
			var src = $(this).attr("src");
			layer.open({
				title:false,
				type :1,
				content:"<img src="+src+" width='100%'/>",
				resize:false,
				shadeClose:true
			})
		});
		
		return params;
	},
	validateAuths:function(params) {
		
	},
	unitConvert:function(num) {
	    var moneyUnits = ["元", "万元", "亿元", "万亿"] 
	    var dividend = 10000;
	    var curentNum = num;
	    //转换数字 
	    var curentUnit = moneyUnits[0];
	    //转换单位 
	    for (var i = 0; i <4; i++) { 
	        curentUnit = moneyUnits[i] 
	        if($.strNumSize(curentNum)<5){ 
	            break;
	        }
	        curentNum = curentNum / dividend 
	    } 
	    var m = {num: 0, unit: ""} 
	    m.num = curentNum.toFixed(2)
	    m.unit = curentUnit;
	    return m;
	},
	load:function(msg){
		if(msg == undefined) {
			msg = "请求中...";
		}
		var index = layer.msg(msg, {
			  icon: 16
			  ,time:1000000*100000000
			  ,shade: 0.5
		});
		return index;
	},
	refreshSuccess:function(layer){
		setTimeout(function(){
			layer.closeAll();
		},1000)
	},
	getQueryString:function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	},
	encodeSearchKey:function(key) {
	    const encodeArr = [{
	      code: '%',
	      encode: '%25'
	    }, {
	      code: '?',
	      encode: '%3F'
	    }, {
	      code: '#',
	      encode: '%23'
	    }, {
	      code: '&',
	      encode: '%26'
	    }, {
	      code: '=',
	      encode: '%3D'
	    }];
	    return key.replace(/[%?#&=]/g, ($, index, str) => {
	      for (const k of encodeArr) {
	        if (k.code === $) {
	          return k.encode;
	        }
	      }
	    });
	  },
	strNumSize:function(tempNum){ 
		if(tempNum != undefined) {
			 var stringNum = tempNum.toString() 
			    var index = stringNum.indexOf(".") 
			    var newNum = stringNum;
			    if(index!=-1){
			        newNum = stringNum.substring(0,index) 
			    } 
			    return newNum.length
		}
	}
});