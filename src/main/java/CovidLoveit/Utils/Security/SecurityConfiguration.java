package CovidLoveit.Utils.Security;

import CovidLoveit.Service.Services.CustomUserDetailsServiceImpl;
import CovidLoveit.Utils.Filter.CustomAuthenticationFilter;
import CovidLoveit.Utils.Filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration @EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfiguration(CustomUserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // TODO: Change the API endpoints to your own custom endpoints
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.cors().and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers(POST, "/**/logout").permitAll()
            .antMatchers(POST, "/api/login").permitAll()
            .antMatchers(POST, "/**/client/add").permitAll()
            .antMatchers(POST, "/**/role/add").hasRole("ADMIN")
            .antMatchers(GET, "/**/client/all/*").hasRole("ADMIN")
            .antMatchers(POST, "/**/guideline/add/*").hasRole("ADMIN")
            .antMatchers(PUT, "/**/guideline/*").hasRole("ADMIN")
            .antMatchers(DELETE, "/**/guideline/*").hasRole("ADMIN")
            .antMatchers(POST, "/**/industry/add/*").hasRole("ADMIN")
            .antMatchers(PUT, "/**/industry/*").hasRole("ADMIN")
            .antMatchers(DELETE, "/**/industry/*").hasRole("ADMIN")
            .antMatchers(GET, "/**/registered-businesses").hasRole("ADMIN")
            .antMatchers("/api/token/refresh/**").permitAll()
            .antMatchers(POST, "/api/login/*").permitAll()
            .anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
