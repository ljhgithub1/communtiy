package life.langteng.community.config;

import life.langteng.community.interception.CheckLoginInterception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * 注入拦截器
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CheckLoginInterception checkLoginInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         *  spring 底层 会将 这些 信息 封装成一个 MappedInterceptor
         *
         *    然后会执行里面的 public boolean matches(String lookupPath, PathMatcher pathMatcher)
         *
         *          lookupPath  请求地址
         *          pathMatcher  路径匹配器
         *
         */
        registry.addInterceptor(checkLoginInterception)
                .addPathPatterns("/**")
                .excludePathPatterns("/callback");

//                .excludePathPatterns("/static/");  // 将 静态资源放行了，配置规则在 application.properties中
//                 spring.mvc.static-path-pattern=/static/**
    }

}
