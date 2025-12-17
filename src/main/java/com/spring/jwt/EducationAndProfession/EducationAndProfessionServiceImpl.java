package com.spring.jwt.EducationAndProfession;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.entity.EducationAndProfession;
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
public class EducationAndProfessionServiceImpl implements EducationAndProfessionService {

    private final EducationAndProfessionRepository educationRepo;
    private final UserRepository userRepo;
    private final CompleteProfileRepository completeProfileRepo;
    private final CompleteProfileService completeProfileService;

    @Override
    @Transactional
    public EducationAndProfessionDTO create(EducationAndProfessionDTO dto) {
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("userId must be provided");
        }
        Integer userId = dto.getUserId();
        if (educationRepo.existsByUser_Id(userId)) {
            throw new ResourceAlreadyExistsException("Education & Profession already exists for userId: " + userId);
        }
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        EducationAndProfession entity = EducationAndProfessionMapper.toEntity(dto, user);
        EducationAndProfession saved = educationRepo.save(entity);
        CompleteProfile cp = completeProfileRepo.findByUser_Id(userId).orElseGet(() -> {
            CompleteProfile newCp = new CompleteProfile();
            newCp.setUser(user);
            newCp.setStatusCol("INCOMPLETE");
            newCp.setProfileCompleted(false);
            return newCp;
        });

        cp.setEducationAndProfession(saved);
        CompleteProfile persisted = completeProfileRepo.save(cp);
        completeProfileService.recalcAndSave(persisted);
        return EducationAndProfessionMapper.toDTO(saved);
    }

    @Override
    public EducationAndProfessionDTO getById(Integer id) {
        EducationAndProfession e = educationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education record not found: " + id));
        return EducationAndProfessionMapper.toDTO(e);
    }

    @Override
    public List<EducationAndProfessionDTO> getAll() {
        return educationRepo.findAll()
                .stream()
                .map(EducationAndProfessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EducationAndProfessionDTO update(Integer id, EducationAndProfessionDTO dto) {
        EducationAndProfession existing = educationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education record not found: " + id));
        User user = existing.getUser();
        if (dto.getEducation() != null) existing.setEducation(dto.getEducation());
        if (dto.getDegree() != null) existing.setDegree(dto.getDegree());
        if (dto.getOccupation() != null) existing.setOccupation(dto.getOccupation());
        if (dto.getOccupationDetails() != null) existing.setOccupationDetails(dto.getOccupationDetails());
        if (dto.getIncomePerYear() != null) existing.setIncomePerYear(dto.getIncomePerYear());
        if (dto.getEducationAndProfessionalDetailsCol() != null)
            existing.setEducationAndProfessionalDetailsCol(dto.getEducationAndProfessionalDetailsCol());
        existing.setUser(user);

        EducationAndProfession saved = educationRepo.save(existing);
        Integer userId = user.getId();
        CompleteProfile cp = completeProfileRepo.findByUser_Id(userId)
                .orElseGet(() -> {
                    CompleteProfile newCp = new CompleteProfile();
                    newCp.setUser(user);
                    newCp.setStatusCol("INCOMPLETE");
                    newCp.setProfileCompleted(false);
                    return newCp;
                });

        cp.setEducationAndProfession(saved);
        CompleteProfile persisted = completeProfileRepo.save(cp);
        completeProfileService.recalcAndSave(persisted);

        return EducationAndProfessionMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        EducationAndProfession existing = educationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Education record not found: " + id));

        // detach from CompleteProfile if linked
        Integer userId = existing.getUser() != null ? existing.getUser().getId() : null;
        if (userId != null) {
            completeProfileRepo.findByUser_Id(userId).ifPresent(cp -> {
                cp.setEducationAndProfession(null);
                CompleteProfile saved = completeProfileRepo.save(cp);
                completeProfileService.recalcAndSave(saved);
            });
        }

        educationRepo.delete(existing);
    }
}
