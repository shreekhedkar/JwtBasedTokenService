package es.softtek.jwtDemo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.softtek.jwtDemo.security.JWTAuthorizationFilter;

@SpringBootApplication
public class JwtDemoApplication {

	public static void main(String[] args) {

		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("server.port", "8083");
		SpringApplication app = new SpringApplication(JwtDemoApplication.class);
		app.setDefaultProperties(prop);
		app.run(args);

	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(),
							UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests().antMatchers(HttpMethod.POST, "/user").permitAll()
					.anyRequest().authenticated();
		}
	}

}
