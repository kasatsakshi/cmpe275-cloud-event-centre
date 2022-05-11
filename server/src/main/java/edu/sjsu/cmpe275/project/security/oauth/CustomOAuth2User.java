package edu.sjsu.cmpe275.project.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private OAuth2User oauth2user;

	public CustomOAuth2User(OAuth2User oauth2user) {

	}

	@Override
	public Map<String, Object> getAttributes() {
		return oauth2user.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oauth2user.getAuthorities();
	}

	@Override
	public String getName() {
		return oauth2user.getName();
	}

}
