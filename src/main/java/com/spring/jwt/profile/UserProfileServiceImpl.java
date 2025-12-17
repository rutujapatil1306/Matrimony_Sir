package com.spring.jwt.profile;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.entity.Enums.Gender;
import com.spring.jwt.entity.Enums.Status;
import com.spring.jwt.entity.User;
import com.spring.jwt.entity.UserProfile;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.profile.UserProfileMapper;
import com.spring.jwt.repository.UserProfileRepository;
import com.spring.jwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final CompleteProfileRepository completeProfileRepository;
    private final CompleteProfileService completeProfileService;

    // CREATE
//    @Override
//    public UserProfileDTO2 createUserProfile(UserProfileDTO2 dto) {
//
//
//        User user = userRepository.findById(Long.valueOf(dto.getUserId()))
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUserId()));
//        UserProfile profile = UserProfileMapper.toEntity(dto, user);
//        UserProfile savedProfile = userProfileRepository.save(profile);
//        Optional<CompleteProfile> existingCP = completeProfileRepository.findByUser_UserId(dto.getUserId());
//
//        if (existingCP.isPresent()) {
//            CompleteProfile cp = existingCP.get();
//            cp.setUserProfile(savedProfile);
//            cp.setProfileCompleted(false);
//            completeProfileRepository.save(cp);
//        } else {
//            CompleteProfile newCP = new CompleteProfile();
//            newCP.setUser(user);
//            newCP.setUserProfile(savedProfile);
//            newCP.setStatusCol("INCOMPLETE");
//            newCP.setProfileCompleted(false);
//            completeProfileRepository.save(newCP);
//        }
//        return UserProfileMapper.toDTO(savedProfile);
//    }
    @Override
    @Transactional
    public UserProfileDTO2 createUserProfile(UserProfileDTO2 dto) {

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("userId must be provided");
        }
        Integer userId = dto.getUserId();
        if (userProfileRepository.existsByUser_Id(userId)) {
            throw new ResourceAlreadyExistsException("UserProfile already exists for userId: " + userId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        UserProfile profile = UserProfileMapper.toEntity(dto, user);
<<<<<<< HEAD
        profile.setUser(user);
=======
>>>>>>> 958f3652905e138112b0a552cd9eb6e947182df2
        UserProfile savedProfile = userProfileRepository.save(profile);
        CompleteProfile cp = completeProfileRepository.findByUser_Id(userId).orElseGet(() -> {
            CompleteProfile newCp = new CompleteProfile();
            newCp.setUser(user);
            newCp.setStatusCol("INCOMPLETE");
            newCp.setProfileCompleted(false);
            return newCp;
        });
        cp.setUserProfile(savedProfile);
        CompleteProfile persistedCp = completeProfileRepository.save(cp);
        completeProfileService.recalcAndSave(persistedCp);
        return UserProfileMapper.toDTO(savedProfile);
    }


    // GET BY ID
    @Override
    public UserProfileDTO2 getUserProfileById(Integer id) {

        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with ID: " + id));

        return UserProfileMapper.toDTO(profile);
    }

    // GET ALL
    @Override
    public List<UserProfileDTO2> getAllUserProfiles() {
        return userProfileRepository.findAll()
                .stream()
                .map(UserProfileMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public UserProfileDTO2 updateUserProfile(Integer id, UserProfileDTO2 dto) {

        // Fetch existing profile
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with ID: " + id));
        User user = existing.getUser();

        // Update fields only if NOT NULL
        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getMiddleName() != null) existing.setMiddleName(dto.getMiddleName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getAge() != null) existing.setAge(dto.getAge());
        if (dto.getGender() != null) existing.setGender(Gender.valueOf(dto.getGender()));
        if (dto.getStatus() != null) existing.setStatus(Status.valueOf(dto.getStatus()));

        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getTaluka() != null) existing.setTaluka(dto.getTaluka());
        if (dto.getDistrict() != null) existing.setDistrict(dto.getDistrict());
        if (dto.getPinCode() != null) existing.setPinCode(dto.getPinCode());
        if (dto.getMobileNumber() != null) existing.setMobileNumber(dto.getMobileNumber());
        if (dto.getMail() != null) existing.setMail(dto.getMail());

        if (dto.getReligion() != null) existing.setReligion(dto.getReligion());
        if (dto.getCaste() != null) existing.setCaste(dto.getCaste());
        if (dto.getMaritalStatus() != null) existing.setMaritalStatus(dto.getMaritalStatus());
        if (dto.getHeight() != null) existing.setHeight(dto.getHeight());
        if (dto.getWeight() != null) existing.setWeight(dto.getWeight());
        if (dto.getBloodGroup() != null) existing.setBloodGroup(dto.getBloodGroup());
        if (dto.getComplexion() != null) existing.setComplexion(dto.getComplexion());
        if (dto.getDiet() != null) existing.setDiet(dto.getDiet());

        if (dto.getSpectacle() != null) existing.setSpectacle(dto.getSpectacle());
        if (dto.getLens() != null) existing.setLens(dto.getLens());
        if (dto.getPhysicallyChallenged() != null) existing.setPhysicallyChallenged(dto.getPhysicallyChallenged());

        if (dto.getHomeTownDistrict() != null) existing.setHomeTownDistrict(dto.getHomeTownDistrict());
        if (dto.getNativeTaluka() != null) existing.setNativeTaluka(dto.getNativeTaluka());
        if (dto.getCurrentCity() != null) existing.setCurrentCity(dto.getCurrentCity());

        if (dto.getUserProfileCol() != null) existing.setUserProfileCol(dto.getUserProfileCol());

        // Ensure original user stays same
        existing.setUser(user);

        // Save
        UserProfile saved = userProfileRepository.save(existing);

        return UserProfileMapper.toDTO(saved);
    }

    // DELETE
    @Override
    public void deleteUserProfile(Integer id) {

        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with ID: " + id));

        userProfileRepository.delete(profile);
    }
@Override
    public List<Object> getProfilesByGender(String gender) {

        Gender g = Gender.valueOf(gender.toUpperCase());

        List<UserProfile> profiles = userProfileRepository.findByGender(g);

        // CHECK AUTHENTICATION ONLY FROM SECURITY CONTEXT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());

        return profiles.stream()
                .map(profile -> isAuthenticated
                        ? UserProfileMapper.toDTO(profile)          // FULL profile
                        : UserProfileMapper.toPublicDTO(profile) // LIMITED profile
                )
                .toList();
    }
}
