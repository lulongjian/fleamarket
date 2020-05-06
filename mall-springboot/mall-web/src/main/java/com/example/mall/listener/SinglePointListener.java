package com.example.mall.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 单点登录
 */
@WebListener
//@Component
public class SinglePointListener  implements HttpSessionAttributeListener {
	public static Map<String, HttpSession> map = new HashMap<String, HttpSession>();
	/**
	 * 当属性增加时。触发该方法
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		String loginName = (String) se.getSession().getAttribute("loginName");
		if(null != loginName && !"".equals(loginName)){//判断session中的loginName是否为空

			if(SinglePointListener.map!=null){//判断存放session的map是否为空

				if(SinglePointListener.map.containsKey(loginName)){//存在key，把之前的session失效

					HttpSession sessionOId = SinglePointListener.map.get(loginName);
					if(sessionOId != null){
						Enumeration<?> e= sessionOId.getAttributeNames();//这行有疑问 todo
						while(e.hasMoreElements()){
							String sessionKeyName = (String) e.nextElement();
							sessionOId.removeAttribute(sessionKeyName);
						}
						sessionOId.setAttribute("forcedout","yes");
					}

				}

			}
			SinglePointListener.map.put(loginName,se.getSession());
		}

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {

	}
}
