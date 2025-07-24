package com.fengshen.core.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

import com.fengshen.core.exception.SuperException;
import com.fengshen.core.exception.UnauthorizedException;
import com.fengshen.core.util.ErrorCode;
import com.fengshen.core.util.ResponseView;

import lombok.extern.slf4j.Slf4j;



/**
 * 全局异常处理
 * @author plw
 * @sine 1.0
 */
@RestControllerAdvice
@Slf4j
public class CenterControllerAdvice {

	/**
	 * 自定义异常
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(SuperException.class)  
    public ResponseView handlerException(HttpServletRequest request, SuperException ex) {
		
		ResponseView rv = ex.getRv();
		if(rv == null) {
			rv = new ResponseView();
			if(ex.getMessage() == null) {
				rv.put("retmsg", ErrorCode.E500);
			}
			rv.put("status", ErrorCode.ERROR);
			rv.put("retcode", ErrorCode.ERROR);
		}
		ex.setRetmsg((String)rv.get("retmsg"));
		log.error((String)rv.get("retmsg"), ex);
		
		return rv;
    }  
	
	@ExceptionHandler({NestedServletException.class,HttpMediaTypeNotAcceptableException.class})  
    public ResponseView handlerException(HttpServletRequest request, HttpServletResponse response, NestedServletException ex) {
		log.error(ErrorCode.E404, ex);
		return settings(ErrorCode.E404);
    }  
	
	@ExceptionHandler(UnauthorizedException.class)  
    public ResponseView handlerException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException ex) {
		
		ResponseView rv = ex.getRv();
		if(rv == null) {
			rv = new ResponseView();
			if(ex.getMessage() == null) {
				rv.put("retmsg", ErrorCode.E500);
			}
			rv.put("status", ErrorCode.UNAUTHORIZED);
			rv.put("retcode", ErrorCode.UNAUTHORIZED);
		}
		response.setStatus(401);
		ex.setRetmsg((String)rv.get("retmsg"));
		log.error((String)rv.get("retmsg"), ex);
		return rv;
    }  
	
	/**
	 * 数据违反唯一约束条件
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)  
	public ResponseView handlerException(HttpServletRequest request,DataIntegrityViolationException exception) {
		String errorMsg = exception.getMessage();
		if (errorMsg.indexOf("ORA-02291") >= 0) {
			errorMsg = ErrorCode.E103; // 外键引用的表中无此记录（通常情况下可能不会出现此异常，因为前台页面上应该是从下拉列表框中选择而非手工输入数据）
		} else if (errorMsg.indexOf("ORA-02292") >= 0) {
			errorMsg = ErrorCode.E104;// 作为别的表中的外键且数据被引用
		} else if (errorMsg.indexOf("MySQLIntegrityConstraintViolationException") >= 0) {
			errorMsg = ErrorCode.E105; // 数据不唯一
		} else {
			errorMsg = ErrorCode.E106;
		}
		log.error("数据违反唯一约束条件", exception);
		return settings(errorMsg);
	}  
	
	
//	/**
//	 * 数据库访问异常
//	 * @param request
//	 * @param exception
//	 * @return
//	 */
//	@ExceptionHandler(RedisConnectionFailureException.class)  
//	public ResponseView handlerException(HttpServletRequest request,RedisConnectionFailureException exception) {
//		log.error(ErrorCode.E102, exception);
//		return settings(ErrorCode.E102);
//	}  
	
	/**
	 * 数据库连接超时.
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(CannotGetJdbcConnectionException.class)  
	public ResponseView handlerExpcetion(HttpServletRequest request,CannotGetJdbcConnectionException exception) {
		log.error(ErrorCode.E100, exception);
		return settings(ErrorCode.E100);
	}  
	
	
	@ExceptionHandler(MissingServletRequestParameterException.class)  
	public ResponseView handlerException(HttpServletRequest request,MissingServletRequestParameterException exception) {
		log.error(ErrorCode.E500, exception);
		
		ResponseView rv = new ResponseView();
		rv.put("status", ErrorCode.ERROR);
		rv.put("successResponse", false);
		String retmsg =  exception.getMessage().replaceAll("[^\u4E00-\u9FA5]", "");
		rv.put("retmsg", retmsg);
		rv.put("retcode", ResponseView.getErrorCodeName(retmsg));
		return rv;
	}  
	
	/**
	 * 请求类型错误异常
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)  
	public ResponseView handlerException(HttpServletRequest request,HttpRequestMethodNotSupportedException exception) {
		log.error(ErrorCode.E405, exception);
		return settings(ErrorCode.E405);
	}  
	
	/**
	 * 请求参数异常
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(BindException.class)  
	public ResponseView handlerException(BindException exception) {
		log.error(ErrorCode.E406, exception);
		return settings(ErrorCode.E406);
	}  
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseView handlerException(HttpServletRequest request, HttpMessageNotReadableException exception) {
		log.error(exception.getMessage());
		ResponseView rv = new ResponseView();
		rv.put("title", ErrorCode.E108);
		rv.put("retmsg", exception.getMessage());
		rv.put("retcode", 107);
		rv.put("status", 500);
		return rv;
	}
	
	
	/**
	 * 执行业务方法异常
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class) 
	public ResponseView handlerException(HttpServletRequest request, RuntimeException exception) {
		log.error("业务方法执行异常:", exception);
		return settings(ErrorCode.E500);
	}  
	
	@ExceptionHandler(Exception.class)  
    public ResponseView handlerException(HttpServletRequest request, Exception e) {
		log.error("系统异常", e);
		return settings(ErrorCode.E500);
    }  
	
	@ExceptionHandler(Throwable.class)  
	public ResponseView handlerException(HttpServletRequest request, Throwable e) {
		log.error("系统异常", e);
		return settings(ErrorCode.E500);
	}  
	
	
	
	public ResponseView settings(String retmsg){
		ResponseView rv = new ResponseView();
		rv.put("retmsg", retmsg);
		rv.put("status", ErrorCode.ERROR);
		rv.put("retcode", ResponseView.getErrorCodeName(retmsg));
		return rv;
	}
	
	protected final class SQLExceptionMsgConst {

		public static final String MSG_DELETE_FAILED_REFERENCED = "删除失败，该条记录被引用";

		public static final String MSG_INSERT_FAILED_NOPARENT = "添加失败，引用记录不存在";

		public static final String MSG_INSERT_FAILED_NOTUNIQUE = "添加失败，相同记录已存在";

		public static final String E102 = "数据存取失败，请检查数据正确性";
	}
}
