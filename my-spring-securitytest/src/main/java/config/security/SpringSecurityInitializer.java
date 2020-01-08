package config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 继承了 AbstractSecuritWebApplicationInitializer 等价于注册了委托过滤器
 */
// DelegationFilterProxy
// 这个过滤器可以通过直接在web.xml中进行配置或者创建一个类继承 AbstractSecurityWebApplicationinitializer
// 作用是从spring容器中找出一个其他的过滤器来干活查找的方法是getBean("名字")，这个名字就是你配置委托过滤器时指定的filterName
// 委托过滤器本质作用是把自己的工作交给一个被spring管理的过滤器来做这样可以本来由tomcat容器管理filter的执行，变成spring容器管理过滤器的初始化
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}









