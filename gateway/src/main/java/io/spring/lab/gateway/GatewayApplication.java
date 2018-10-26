package io.spring.lab.gateway;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Order(HIGHEST_PRECEDENCE)
	@EnableWebSecurity
	public static class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.authorizeRequests()
						.anyRequest().authenticated()
						.and()
					.oauth2Login()
						.userInfoEndpoint()
							.userAuthoritiesMapper(this::grantedAuthoritiesMapper);
		}

		private Collection<? extends GrantedAuthority> grantedAuthoritiesMapper(Collection<? extends GrantedAuthority> authorities) {
			LinkedHashSet<SimpleGrantedAuthority> resourceAccess = authorities.stream()
					.filter(OidcUserAuthority.class::isInstance)
					.map(OidcUserAuthority.class::cast)
					.filter(this::issuedByKeycloak)
					.filter(this::hasResourceAccessClaims)
					.map(a -> a.getIdToken().getClaimAsStringList("client-authorities"))
					.flatMap(Collection::stream)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toCollection(LinkedHashSet::new));
			log.info("RESOURCE_ACCESS: {}", resourceAccess);
			return resourceAccess;
		}

		private boolean issuedByKeycloak(OidcUserAuthority authority) {
			return authority.getIdToken().getIssuer().toString().equals("http://localhost:8080/auth/realms/sprio");
		}

		private boolean hasResourceAccessClaims(OidcUserAuthority authority) {
			return authority.getIdToken().containsClaim("client-authorities");
		}
	}

//	@Order(HIGHEST_PRECEDENCE)
//	@EnableWebSecurity
//	public static class OAuth2ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http
//					.authorizeRequests()
//						.anyRequest().authenticated()
//						.and()
//					.oauth2ResourceServer();
//		}
//	}
}
