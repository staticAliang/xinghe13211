package com.fengshen.core.util;

/**
 * 全局报错统一类
 * 
 * 前缀必须为E
 * 示例E1100
 * 
 * @author: William Peng
 * @sine: 1.0
 */
public class ErrorCode {

	public static final Integer SUCCESS = 200;

	public static final Integer ERROR = 500;

	public static final Integer UNAUTHORIZED = 401;

	public static final String E404 = "service not found";
	
	public static final String E500 = "系统异常";

	public static final String E405 = "请求类型不正确";
	
	public static final String E406 = "参数类型错误";
	
	public static final String E100 = "数据库连接超时";
	
	public static final String E101 = "数据库异常";
	
	public static final String E102 = "Redis异常";
	
	public static final String E103 = "删除失败，该条记录被引用";

	public static final String E104 = "添加失败，引用记录不存在";

	public static final String E105 = "添加失败，相同记录已存在";

	public static final String E106 = "数据存取失败，请检查数据正确性";
	
	public static final String E107 = "解密出现异常";
	
	public static final String E108 = "json数据序列化错误.";
	
	public static final String E401 = "对不起,您无权访问!";
	
	public static final String E402 = "上传失败";

	public static final String E1001 = "token缺失";
	
	public static final String E1002 = "token无效";

	public static final String E1003 = "id无效";
	
	public static final String E1004 = "id不能为空";
	
	public static final String E10042 = "此账号禁止登录、请联系管理人员";
	
	public static final String E1005 = "系统用户已经存在";
	
	public static final String E1006 = "请选择性别";
	
	public static final String E1007 = "请输入用户名";
	
	public static final String E1008 = "请传入refreshToken";
	
	public static final String E1009 = "设备数据不能为空";

	
	/*用户报错相关信息*/
	public static final String E10019 = "用户名或密码错误";
	
	public static final String E10020 = "昵称不能为空";
	
	public static final String E10022 = "头像不能为空";

	public static final String E10025 = "更换头像失败";
	
	public static final String E10026 = "请选择低于2M的头像";
	
	public static final String E10027 = "图片格式不正确";
	
	public static final String E10028 = "请选择上传的图片";
	
	public static final String E10100 = "用户ID不能为空";
	
	public static final String E10103 = "用户名不能为空";
	
	public static final String E10104 = "密码不能为空";
	
	public static final String E10106 = "省不能为空";
	
	public static final String E10107 = "原密码不正确";
	
	public static final String E10108 = "新密码不能为空";
	
	public static final String E10109 = "原密码不能为空";
	
	public static final String E10110 = "手机号不能为空";
	
	public static final String E10111 = "区不能为空";
	
	public static final String E10112 = "市不能为空";
	
	public static final String E10113 = "验证码不能为空";
	
	public static final String E10114 = "验证码错误";
	
	public static final String E10115 = "验证码过期";
	
	public static final String E10116 = "手机号码格式错误";
	
	public static final String E10117 = "验证码令牌不能为空";
	
	public static final String E10118 = "手机号已注册";
	
	public static final String E10121 = "获取用户头像失败";
	
	public static final String E10122 = "获取验证码失败";
	
	public static final String E10123 = "验证码发送过于频繁";
	
	public static final String E10124 = "该手机今日验证码数量已用完";
	
	public static final String E10125 = "国家不能为空";
	
	public static final String E10126 = "设备类型不能为空";
	
	public static final String E10127 = "性别类型错误,男1女0";
	
	public static final String E10128 = "昵称长度过长";
	
	public static final String E10129 = "生日格式错误";
	
	public static final String E10130 = "性别不能为空";
	
	public static final String E10131 = "手机号已被绑定";
	
	public static final String E10132 = "出生年月不能为空";
	
	public static final String E10133 = "绑定失败,对不起您已绑定过手机";
	
	public static final String E10134 = "账户异常,无法登录";
	
	public static final String E10135 = "账号为测试账号,已被停用";
	
	public static final String E10136 = "手机号已被注册";
	
	public static final String E10137 = "身高不能为空";
	
	public static final String E10138 = "体重不能为空";
	
	public static final String E10139 = "出生日期大于当前日期";
	
	public static final String E10140 = "请先完善资料,在开始测试";
	
	public static final String E10141 = "请输入新密码";
	
	public static final String E10142 = "两次确认密码不一致";
	
	public static final String E10143 = "两次确认密码不一致";
	
	public static final String E10144 = "新旧密码不能一样";
	
	
	
	/*系统角色类 E16000*/
	public static final String E16000 = "角色ID不能为空";
	
	public static final String E16001 = "角色名不能为空";
	
	public static final String E16002 = "请选择角色";
	
	public static final String E16003 = "角色已经存在";
	
	
	/*版本 E18000*/
	public static final String E18000 = "版本ID不能为空";

	public static final String E18001 = "平台类型不能为空";

	public static final String E18002 = "版本号不能为空";

	public static final String E18003 = "版本号已存在";
	
	public static final String E18004 = "版本更新内容不能为空";
	
	public static final String E18005 = "平台类型,0:安卓;1:苹果";
	
	public static final String E18006 = "更新内容字数超限";
	
	public static final String E18007 = "请上传app文件";
	
	public static final String E18008 = "版本号格式错误";
	
	public static final String E18009 = "请输入有效版本号";
	
	public static final String E18010 = "请上传有效的安卓程序";
	
	
	/*意见反馈 E19000*/
	public static final String E19000 = "反馈内容不能为空";
	
	public static final String E19001 = "反馈内容最大150字";
	
	public static final String E19002 = "联系方式不能为空";
	
	public static final String E19003 = "邮箱不能为空";
	
	public static final String E19004 = "联系方式只能为手机号码或者邮箱";
	
	/*健康体质 E23000*/
	public static final String E23000 = "标题不能为空";
	
	public static final String E23001 = "答案不能为空";
	
	public static final String E23002 = "类型不能为空";
	
	public static final String E23003 = "链接不为能空";
	
	public static final String E23004 = "请上传预览图";
	
	/*消息通知 E24000*/
	public static final String E24000 = "推送类型错误";
	
	public static final String E24001 = "请选择推送用户";
	
	public static final String E24005 = "请输入通知内容";
	
	public static final String E24006 = "通知内容字数超限";
	
	/*E25001*/
	public static final String E25001 = "分享类型不能为空";
	
	
	/*其他 E26000*/
	public static final String E26000 = "关键词不能为空";
	
	public static final String E26001 = "请登录!";
	
	public static final String E26002 = "数据无效,请重新测试.";
	
	public static final String E26003 = "短信验证码发送失败";
	
	public static final String E26004 = "短信验证码受限";
	
	public static final String E26005 = "非法请求";
	
	public static final String E26006 = "token过期";
	
	public static final String E26007 = "accessToken有误";
	
	public static final String E26008 = "refrshToken已过期";
	
	public static final String E26009 = "请勿提交空白数据";
	
	public static final String E26010 = "数据格式不正确";
	
	public static final String E26011 = "用户唯一标识不能为空";
	
	public static final String E26012 = "请修改初始化密码.";
}