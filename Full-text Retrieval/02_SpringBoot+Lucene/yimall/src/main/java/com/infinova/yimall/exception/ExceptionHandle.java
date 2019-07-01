package com.infinova.yimall.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infinova.yimall.entity.ResultBean;
import com.infinova.yimall.entity.ResultEnum;
import com.infinova.yimall.utils.ResultUtil;
/**
 * 异常处理类
 * @author yizl
 * @param <T>
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle<T> {
	
	@ExceptionHandler(value = Exception.class)
	public ResultBean<T> handle(Exception e) {
		if(e instanceof YiMallException) {
			YiMallException yiMallException=(YiMallException) e;
			return ResultUtil.error(yiMallException.getResultEnum());
		}else {
			return ResultUtil.error(ResultEnum.UNKNOW_ERROR);
		}
	}
}
