package com.hotelmanagementsystem.backend.config;

import com.hotelmanagementsystem.backend.utils.filters.JwtAuthenticationFilter;
import com.hotelmanagementsystem.backend.utils.user_details_service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    /**
     * 说明：由于在本类中既定义PasswordEncoder的Bean，又使用该Bean，这就会导致在Spring初始化SecurityConfig类时，需要初始化PasswordEncoder，而在初始化
     * PasswordEncoder时又需要初始化SecurityConfig类，从而导致了循环依赖。破解该循环依赖的方法可以在passwordEncoder参数上加上@Lazy注解，这样一来只有当真正用到
     * passwordEncoder变量时（它在configureAuthenticationManagerBuilder方法中第一次使用），spring才会寻找合适的Bean
     * 自动注入给它，这样就可以做到在未初始化需PasswordEncoder时就可以初始化SecurityConfig类了，从而打破了循环依赖！（好！）
     */
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsServiceImpl userDetailsService, @Lazy PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * 本方法接收一个由Spring Security框架自动创建的HttpSecurity对象，该对象包含了所有与HTTP安全相关的配置选项，开发者在此方法中配置这些选项。
     *
     * @param http Spring Security框架自动创建的HttpSecurity对象
     * @return 根据配置信息构建的过滤器链
     * @throws Exception 继承的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                // 在此处添加无需认证即可访问的url
                "/hotel/online_user/login",
                "/hotel/online_user/register",
                "/hotel/staff_user/login")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Spring Security根据http中的配置构建过滤器链
        return http.build();
        // 当HTTP请求到达时，过滤器链中的每个过滤器都会被逐个调用，执行特定的安全检查或处理
    }
    
    /**
     * 配置认证管理器构造器（指明认证管理器使用哪个UserDetailsService和哪个密码加密器）（不指定密码加密器则数据库中的密码无法与用户传入的密码进行匹配）
     *
     * @param authenticationManagerBuilder 认证管理器构造器
     */
    @Autowired
    public void configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}