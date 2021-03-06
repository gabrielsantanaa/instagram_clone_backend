package com.brabos.bahia.instagram.test.dto;

import com.brabos.bahia.instagram.test.domains.UserProfile;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String imageUrl;

    private Set<Long> followers = new HashSet<>();
    private Set<Long> following = new HashSet<>();

    public UserProfileDTO() {
    }

    public UserProfileDTO(UserProfile user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.imageUrl = user.getImageUrl();
        this.followers = user.getFollowers();
        this.following = user.getFollowing();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Long> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Long> followers) {
        this.followers = followers;
    }

    public Set<Long> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Long> following) {
        this.following = following;
    }
}
