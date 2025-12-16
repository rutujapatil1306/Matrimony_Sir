package com.spring.jwt.FamilyBackground;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.entity.FamilyBackground;
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
public class FamilyBackgroundServiceImpl implements FamilyBackgroundService {

    private final FamilyBackgroundRepository familyRepo;
    private final UserRepository userRepo;
    private final CompleteProfileRepository completeProfileRepo;
    private final CompleteProfileService completeProfileService;

    @Override
    @Transactional
    public FamilyBackgroundDTO create(FamilyBackgroundDTO dto) {

        if (dto.getUserId() == null)
            throw new IllegalArgumentException("userId must be provided");

        Integer userId = dto.getUserId();
        if (familyRepo.existsByUser_Id(userId))
            throw new ResourceAlreadyExistsException("Family background already exists for userId: " + userId);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        FamilyBackground entity = FamilyBackgroundMapper.toEntity(dto, user);
        FamilyBackground saved = familyRepo.save(entity);
        CompleteProfile cp = completeProfileRepo.findByUser_Id(userId)
                .orElseGet(() -> {
                    CompleteProfile newCp = new CompleteProfile();
                    newCp.setUser(user);
                    newCp.setStatusCol("INCOMPLETE");
                    newCp.setProfileCompleted(false);
                    return newCp;
                });
        cp.setFamilyBackground(saved);
        CompleteProfile persisted = completeProfileRepo.save(cp);
        completeProfileService.recalcAndSave(persisted);
        return FamilyBackgroundMapper.toDTO(saved);
    }

    @Override
    public FamilyBackgroundDTO getById(Integer id) {
        FamilyBackground fb = familyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family background not found: " + id));
        return FamilyBackgroundMapper.toDTO(fb);
    }

    @Override
    public List<FamilyBackgroundDTO> getAll() {
        return familyRepo.findAll()
                .stream()
                .map(FamilyBackgroundMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FamilyBackgroundDTO update(Integer id, FamilyBackgroundDTO dto) {

        FamilyBackground existing = familyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family background not found: " + id));

        // do NOT change user
        User user = existing.getUser();

        // partial update
        if (dto.getFathersName() != null) existing.setFathersName(dto.getFathersName());
        if (dto.getFatherOccupation() != null) existing.setFatherOccupation(dto.getFatherOccupation());
        if (dto.getMothersName() != null) existing.setMothersName(dto.getMothersName());
        if (dto.getMotherOccupation() != null) existing.setMotherOccupation(dto.getMotherOccupation());
        if (dto.getBrother() != null) existing.setBrother(dto.getBrother());
        if (dto.getMarriedBrothers() != null) existing.setMarriedBrothers(dto.getMarriedBrothers());
        if (dto.getSisters() != null) existing.setSisters(dto.getSisters());
        if (dto.getMarriedSisters() != null) existing.setMarriedSisters(dto.getMarriedSisters());
        if (dto.getInterCasteInFamily() != null) existing.setInterCasteInFamily(dto.getInterCasteInFamily());
        if (dto.getParentResiding() != null) existing.setParentResiding(dto.getParentResiding());
        if (dto.getFamilyWealth() != null) existing.setFamilyWealth(dto.getFamilyWealth());
        if (dto.getMamaSurname() != null) existing.setMamaSurname(dto.getMamaSurname());
        if (dto.getMamaPlace() != null) existing.setMamaPlace(dto.getMamaPlace());
        if (dto.getFamilyBackgroundCol() != null) existing.setFamilyBackgroundCol(dto.getFamilyBackgroundCol());
        if (dto.getRelativeSurnames() != null) existing.setRelativeSurnames(dto.getRelativeSurnames());

        existing.setUser(user);

        FamilyBackground saved = familyRepo.save(existing);

        // update complete profile
        Integer userId = user.getId();
        CompleteProfile cp = completeProfileRepo.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("CompleteProfile missing for user: " + userId));

        cp.setFamilyBackground(saved);
        CompleteProfile persisted = completeProfileRepo.save(cp);

        completeProfileService.recalcAndSave(persisted);

        return FamilyBackgroundMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        FamilyBackground existing = familyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family background not found: " + id));

        Integer userId = existing.getUser().getId();

        // detach from CP
        completeProfileRepo.findByUser_Id(userId).ifPresent(cp -> {
            cp.setFamilyBackground(null);
            CompleteProfile saved = completeProfileRepo.save(cp);
            completeProfileService.recalcAndSave(saved);
        });

        familyRepo.delete(existing);
    }
}
