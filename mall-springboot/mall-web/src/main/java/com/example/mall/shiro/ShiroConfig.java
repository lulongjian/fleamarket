package com.example.mall.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 * @author
 *
 */
@Configuration
public class ShiroConfig {

//	@Autowired
//	private PermissionService permissionService;

	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 * 	常用过滤器：
		 * 		anon:无需认证（登陆可以访问）
		 * 		authc:必须认证才可以访问
		 * 		user:如果使用remember的功能可以直接访问
		 * 		perms:该资源必须得到资源权限才可以访问
		 * 		role：该资源必须得到角色权限才可以访问
		 */
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		//放行静态资源
		filterMap.put("resources/**", "anon");

//		//对菜单权限进行权限过滤
//		List<Permission> permissions = permissionService.findAll(new PermissionForm());
//		for (Permission permission : permissions) {
//			filterMap.put(permission.getHref(), "perms["+permission.getPermission()+"]");
//		}
//	    //拦截所有页面请求
//		filterMap.put("/sys/*", "authc");

		//修改跳转页面路径
		shiroFilterFactoryBean.setLoginUrl("/sys/login");

		//添加无权限时进入的页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/sys/unAuth");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联Realm
		securityManager.setRealm(userRealm);
		securityManager.setCacheManager(getCache());
		return securityManager;
	}

	/**
	 * 创建Realm
	 */
	@Bean(name = "userRealm")
	public UserRealm getRealm(){
		return new UserRealm();
	}

	/**
	 * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}

	/**
	 *使用缓存
	 */
	@Bean
	public EhCacheManager getCache(){
		return new EhCacheManager();
	}


}
