package com.geulowup.backend.global.security.oauth2.dto;

import com.geulowup.backend.domain.user.enums.SocialType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final SocialType socialType;

    public CustomOAuth2User(OAuth2User user, SocialType socialType) {
        this.attributes = user.getAttributes();
        this.authorities = user.getAuthorities();
        this.socialType = socialType;
    }

    public CustomOAuth2User(Long userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        this.attributes = map;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        this.socialType = SocialType.UNKNOWN;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "test1";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getName() {
//        HashMap<String, String> map = (HashMap) attributes.get("properties");
//        if (map == null) return "";
//        return map.get("nickname");
        return getSocialName();
    }

    public Long getUserId() {
        return (Long) attributes.get("userId");
    }

    public String getEmail() {
        return "";
    }

    public String getSocialName() {
        return "";
    }

    public String getSocialId() {
        return "";
    }

//    public String getProfileImageUrl() {
//        HashMap<String, String> map = (HashMap) attributes.get("properties");
//        if (map == null) return "";
//        return map.get("profile_image_url");
//    }
//
//
//    public String getEmail() {
//        HashMap<String, String> map = (HashMap) attributes.get("kakao_account");
//        if (map == null)
//            return "";
//        return map.get("email");
//    }
}