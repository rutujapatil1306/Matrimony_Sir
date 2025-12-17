package com.spring.jwt.profile;

import java.util.List;

public interface UserProfileService {
    UserProfileDTO2 createUserProfile(UserProfileDTO2 userProfileDTO);
    UserProfileDTO2 getUserProfileById(Integer id);
    List<UserProfileDTO2> getAllUserProfiles();
    UserProfileDTO2 updateUserProfile(Integer id, UserProfileDTO2 userProfileDTO);
    void deleteUserProfile(Integer id);
    List<Object> getProfilesByGender(String gender);
}
