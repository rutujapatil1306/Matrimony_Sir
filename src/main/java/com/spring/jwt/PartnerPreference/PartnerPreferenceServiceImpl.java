package com.spring.jwt.PartnerPreference;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.entity.PartnerPreference;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerPreferenceServiceImpl implements PartnerPreferenceService {

    private final PartnerPreferenceRepository repo;
    private final UserRepository userRepo;
    private final CompleteProfileRepository completeRepo;
    private final CompleteProfileService completeService;

    @Override
    @Transactional
    public PartnerPreferenceDTO create(PartnerPreferenceDTO dto) {

        if (dto.getUserId() == null)
            throw new IllegalArgumentException("userId must not be null");

        Integer userId = dto.getUserId();

        if (repo.existsByUser_Id(userId))
            throw new ResourceAlreadyExistsException("Partner Preference already exists for userId: " + userId);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        PartnerPreference entity = PartnerPreferenceMapper.toEntity(dto, user);
        PartnerPreference saved = repo.save(entity);

        // Attach to CompleteProfile
        CompleteProfile cp = completeRepo.findByUser_Id(userId)
                .orElseGet(() -> {
                    CompleteProfile newCP = new CompleteProfile();
                    newCP.setUser(user);
                    newCP.setStatusCol("INCOMPLETE");
                    newCP.setProfileCompleted(false);
                    return newCP;
                });

        cp.setPartnerPreference(saved);
        CompleteProfile persisted = completeRepo.save(cp);

        completeService.recalcAndSave(persisted);

        return PartnerPreferenceMapper.toDTO(saved);
    }

    @Override
    public PartnerPreferenceDTO getById(Integer id) {
        PartnerPreference entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner Preference not found: " + id));

        return PartnerPreferenceMapper.toDTO(entity);
    }

    @Override
    public List<PartnerPreferenceDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(PartnerPreferenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PartnerPreferenceDTO update(Integer id, PartnerPreferenceDTO dto) {

        PartnerPreference existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner Preference not found: " + id));

        User user = existing.getUser(); // DO NOT change

        // PATCH update
        if (dto.getAge() != null) existing.setAge(dto.getAge());
        if (dto.getLookingFor() != null) existing.setLookingFor(dto.getLookingFor());
        if (dto.getHeight() != null) existing.setHeight(dto.getHeight());
        if (dto.getEatingHabits() != null) existing.setEatingHabits(dto.getEatingHabits());
        if (dto.getCountryLivingIn() != null) existing.setCountryLivingIn(dto.getCountryLivingIn());
        if (dto.getCityLivingIn() != null) existing.setCityLivingIn(dto.getCityLivingIn());
        if (dto.getComplexion() != null) existing.setComplexion(dto.getComplexion());
        if (dto.getReligion() != null) existing.setReligion(dto.getReligion());
        if (dto.getCaste() != null) existing.setCaste(dto.getCaste());
        if (dto.getEducation() != null) existing.setEducation(dto.getEducation());
        if (dto.getMangal() != null) existing.setMangal(dto.getMangal());
        if (dto.getResidentStatus() != null) existing.setResidentStatus(dto.getResidentStatus());
        if (dto.getPartnerOccupation() != null) existing.setPartnerOccupation(dto.getPartnerOccupation());
        if (dto.getPartnerIncome() != null) existing.setPartnerIncome(dto.getPartnerIncome());

        PartnerPreference saved = repo.save(existing);

        // Update CompleteProfile
        CompleteProfile cp = completeRepo.findByUser_Id(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Complete Profile missing"));

        cp.setPartnerPreference(saved);
        CompleteProfile persisted = completeRepo.save(cp);

        completeService.recalcAndSave(persisted);

        return PartnerPreferenceMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        PartnerPreference existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner Preference not found: " + id));

        Integer userId = existing.getUser().getId();

        completeRepo.findByUser_Id(userId).ifPresent(cp -> {
            cp.setPartnerPreference(null);
            completeService.recalcAndSave(completeRepo.save(cp));
        });

        repo.delete(existing);
    }
}
