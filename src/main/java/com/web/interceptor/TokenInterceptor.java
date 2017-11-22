
package com.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.utils.ResBody;

/**
 * token拦截器
 * @author 夏夜梦星辰
 *
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
	private static Logger log = Logger.getLogger(TokenInterceptor.class);
	
	/**
	 * 保存token数据的集合
	 */
	//private Map<String, String> takenMap = new HashMap<String, String>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//请求执行之前
		String token = request.getParameter("_token");
		if(StringHelper.isEmpty(token)) {
			log.info("请求中未包含_token参数值");
			response.setCharacterEncoding("gbk");
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSON(new ResBody(-1, "请求中未包含_token参数值")));
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//控制器执行之后
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//整个请求完成之后
	}

}
