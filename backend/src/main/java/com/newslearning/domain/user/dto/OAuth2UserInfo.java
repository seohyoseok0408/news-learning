package com.newslearning.domain.user.dto;

public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getNickName();
    String getName();
    String getProfileImage();
}
