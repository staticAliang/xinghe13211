$.extend({
	jTable: function(params) {
		$.post(params.url, params.paramsData, function(data) {
			if(data.status == 401) {
				$("body").html("");
				swal({
					title: "对不起，您没有权限访问此页面信息请联系管理员...",
					showConfirmButton: false,
					type: "error"
				});
				return;
			}
			var resultData;
			//如果没有开启分页，默认数据是保存在data下
			if(data.status == 200) {
				if(params.pageLimit == undefined || params.pageLimit == false) {
					resultData = data.data;
				} else {
					//开启分页后,数据会去resultData下取
					resultData = data.data.resultData;
				}
			}else if(data.status == 500){
				 swal({
					title: data.retmsg,
					showConfirmButton: false,
					type: "error"
				 });
			}
			params.opt.data = resultData;
			params.opt.undefinedText = "",
			params.opt.pagination = false, //分页  
			params.opt.sidePagination = "server",
			params.opt.onClickCell = function(field, value, row, elem) {
				if(params.opt.unChecked == undefined || params.opt.unChecked == false) {
					var tr = $(elem).parent();
					var isShowSuccess = $(elem).attr("class");//如果是操作按钮的话，就不选中
					if(isShowSuccess != undefined) {
						if(isShowSuccess.indexOf('operator') == -1) {
							if(tr.attr("class") == "success") {
								tr.removeClass("success");
								tr.find("input[type='checkbox']").prop("checked", false);
							} else {
								tr.addClass("success");
								tr.find("input[type='checkbox']").prop("checked", true);
							}
						}
					}else {
						if(tr.attr("class") == "success") {
							tr.removeClass("success");
							tr.find("input[type='checkbox']").prop("checked", false);
						} else {
							tr.addClass("success");
							tr.find("input[type='checkbox']").prop("checked", true);
						}
					}
				}
			},
			params.opt.onCheck = function(row, elem) {
				var tr = $(elem).parent().parent();
				tr.addClass("success");
			},
			params.opt.onUncheck = function(row, elem){
				var tr = $(elem).parent().parent();
				tr.removeClass("success");
			}
			params.opt.onCheckAll = function(rows) {
				$.each($("#" + params.id).find("tr"),function(i,o){
					if(i > 0) {
						$(this).addClass("success");					
					}
				})
			}
			params.opt.onUncheckAll = function(){
				$.each($("#" + params.id).find("tr"),function(i,o){
					$(this).removeClass("success");					
				})
			}
			$("#" + params.id).bootstrapTable(('destroy'));
			$("#" + params.id).bootstrapTable(params.opt);
			
			
			$(".fixed-table-loading").hide();
			$(".fixed-table-pagination").show();
			$(".fixed-table-pagination").css("text-align", "right")
			$(".fixed-table-pagination").attr("id", "layPage")
			if(params.pageLimit != undefined && params.pageLimit == true) {
				var html = '<div id="layPageTools" style="text-align: right;"></div>';
				layui.use("laypage", function() {
					var laypage = layui.laypage;
					laypage.render({
						elem: 'layPageTools',
						count: data.data.total,
						limit: pageSize,
						curr: pageNum,
						theme: '#4c82ef',
						first: false,
					    last: false,
						layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
						jump: function(obj, first) {
							if(!first) {
								if(!first) {
									pageSize = obj.limit;
									pageNum = obj.curr;
									params.paramsData.pageSize = obj.limit;
									params.paramsData.pageNum = obj.curr;
									$.jTable(params);
								}
							}
						}
					});
				})
			}
			//回调函数
			if(params.opt.onSuccess != undefined) {
				params.opt.onSuccess();
			}
			//权限校验
			if(params.auth != undefined) {
				var datas = {data: params.auth};
				$.ajax({
					type:"post",
					url:"/m/sys/user/validateAuths",
					dataType:"json",
					contentType: "application/json",
					data:JSON.stringify(datas), 
					success:function(data){
						if(data.status == 200) {
							$(data.data).each(function(i,o){
								if(!o.isAuth) {
									console.log(o.type)
									//禁用操作资源.
									$("button[data-type='"+o.type+"']").css("background-color","#c2c2c2").css("border-color","#c2c2c2").prop("disabled","disabled")
								}
							})
						}else {
							console.log("权限校验失败");
						}
	          		}
				});
			}
		})
		$("#jtbDelete").unbind("click").bind("click", function() {
			var checkboxs = $(".bs-checkbox").find("input[type=checkbox][name=btSelectItem]:checked")
			var selectedTd = $(".bs-checkbox").find("input[type=checkbox][name=btSelectItem]:checked").parent().parent();
			var array = new Array(checkboxs.length);
			var selectedTdArray = new Array(checkboxs.length);
			if(params.opt.onDelete != undefined) {
				for(var i=0;i<array.length;i++) {
					array[i] = $(checkboxs[i]).val();
					selectedTdArray[i] = $(selectedTd);
				}
				if(array.length > 0) {
					params.opt.onDelete(array, selectedTdArray);
				}
			}
		});
		$("#jtbAdd").unbind("click").bind("click", function() {
			if(params.opt.onAdd != undefined) {
				params.opt.onAdd();
			}
		});
	}
});