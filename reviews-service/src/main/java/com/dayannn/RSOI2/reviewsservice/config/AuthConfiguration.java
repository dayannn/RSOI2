package com.dayannn.RSOI2.reviewsservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {

	private static final int AUTH_TOKEN_VALIDITY_SECONDS = 60*5;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
            .withClient("gatewayService")
                .secret("gatewaySecret")
                .authorizedGrantTypes("client_credentials")
                .scopes("read", "write")
                .accessTokenValiditySeconds(AUTH_TOKEN_VALIDITY_SECONDS)
                .autoApprove(true);
	}

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
//    	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
//                .accessTokenConverter(defaultAccessTokenConverter());
		endpoints.authenticationManager(authenticationManager);
    }
//
//	@Bean
//	public TokenStore tokenStore(){
//		return new JwtTokenStore(defaultAccessTokenConverter());
//	}
//
//	@Bean
//	public JwtAccessTokenConverter defaultAccessTokenConverter() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setSigningKey("123");
//		return converter;
//	}
}
