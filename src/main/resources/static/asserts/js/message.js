let maillist = new Vue({
	el : "#maillist",
	data : {
		mails:[],
		pageNo:0,
		pageSize:10,
		message:'加载更多...',
	},
	methods: {
		loadmore: function(){
			let result = [];
			$.ajax({
				type : "GET",
				url: "/mails",
				data: {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
				},
				async: false,
				dataType: "json",
				success : function(data) {
					result = data;
				},
				error : function(data) {
				}
			});
			this.pageNo ++;
			this.mails = this.mails.concat(result.content);
			if(result.content.length < this.pageSize){
				this.message = "没有更多了！";
			}
		},
		resendmail:function(id){
			if(id == undefined){
				alert("邮件ID为空了");
				return false;
			}
			alert('正在发送...');
			$.ajax({
				type : "get",
				url: "/mail/send/" + id,
				data: {},
				async: true,
				dataType: "json",
				success : function(data) {
					if(data.success == true){
						if(data.object.sendStatatus == true){
							$("#" + id).hide();
							$("#" + id + "state").text("已发送");
							$("#" + id + "state").css("color","green");
							alert('重新发送成功！');
						}
					}else{
						alert('重新发送失败！');
					}
				},
				error : function(data) {
					alert('系统异常，重新发送失败！');
				}
			});
		}
	},
});
/** 显示邮件列表 第一页**/
maillist.loadmore(maillist.pageNo);


/** 表单初始化**/
let result = {};
$.ajax({
	type : "GET",
	url: "/mailconfig",
	data: {},
	async: false,
	dataType: "json",
	success : function(data) {
		result = data;
	},
	error : function(data) {
	}
});
	


new Vue({
	el : "#mailconfigform",
	data : {
		smtpHost: result.smtpHost,
		smtpPort: result.smtpPort,
		username:result.username,
		password:result.password,
		useSSL:result.useSSL
	},
	methods : {
		submit: function(){
			$.ajax({
				type : "POST",
				url: "/mailconfig",
				data: {
					smtpHost:this.smtpHost,
					smtpPort:this.smtpPort,
					username:this.username,
					password:$("#EmailAuthorizationCode").val(),
					useSSL:this.useSSL,
				},
				async: false,
				dataType: "json",
				success : function(data) {
					if(true == data){
						$("#tip").modal({
							keyboard: true
						});
					}else{
						alert("保存失败！");
					}
				}
			});
		}
	},
});
