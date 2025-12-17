package com.spring.jwt.HoroscopeDetails;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.entity.HoroscopeDetails;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoroscopeDetailsServiceImpl implements HoroscopeDetailsService {

    private final HoroscopeDetailsRepository horoscopeRepo;
    private final UserRepository userRepo;
    private final CompleteProfileRepository completeProfileRepo;
    private final CompleteProfileService completeProfileService;

//    @Override
//    public HoroscopeDetailsDTO create(HoroscopeDetailsDTO dto) {
//
//        User user = userRepo.findById(Long.valueOf(dto.getUserId()))
//                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.getUserId()));
//
//        HoroscopeDetails details = HoroscopeDetailsMapper.toEntity(dto, user);
//
//        HoroscopeDetails saved = horoscopeRepo.save(details);
//
//        // Update or Create CompleteProfile entry
//        CompleteProfile cp = completeProfileRepo.findByUser_UserId(dto.getUserId()).orElse(new CompleteProfile());
//
//        cp.setUser(user);
//        cp.setHoroscopeDetails(saved);
//        cp.setProfileCompleted(false);
//        cp.setStatusCol("INCOMPLETE");
//
//        completeProfileRepo.save(cp);
//
//        return HoroscopeDetailsMapper.toDTO(saved);
//    }
@Override
@Transactional
public HoroscopeDetailsDTO create(HoroscopeDetailsDTO dto) {

    if (dto.getUserId() == null) {
        throw new IllegalArgumentException("userId must be provided");
    }
    Integer userId = dto.getUserId();
    User user = userRepo.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    Optional<HoroscopeDetails> existing = horoscopeRepo.findByUser_Id(userId);
    if (existing.isPresent()) {
        throw new ResourceAlreadyExistsException(
                "Horoscope details already exist for this user: " + userId
        );
    }
    HoroscopeDetails details = HoroscopeDetailsMapper.toEntity(dto, user);
    HoroscopeDetails saved = horoscopeRepo.save(details);
    CompleteProfile cp = completeProfileRepo.findByUser_Id(userId)
            .orElseGet(() -> {
                CompleteProfile newCp = new CompleteProfile();
                newCp.setUser(user);
                newCp.setStatusCol("INCOMPLETE");
                newCp.setProfileCompleted(false);
                return newCp;
            });
    cp.setHoroscopeDetails(saved);

    CompleteProfile persistedCp = completeProfileRepo.save(cp);
    completeProfileService.recalcAndSave(persistedCp);
    return HoroscopeDetailsMapper.toDTO(saved);
}


    @Override
    public HoroscopeDetailsDTO getById(Integer id) {
        HoroscopeDetails h = horoscopeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horoscope not found ID: " + id));

        return HoroscopeDetailsMapper.toDTO(h);
    }

    @Override
    public List<HoroscopeDetailsDTO> getAll() {
        return horoscopeRepo.findAll()
                .stream()
                .map(HoroscopeDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HoroscopeDetailsDTO update(Integer id, HoroscopeDetailsDTO dto) {

        HoroscopeDetails existing = horoscopeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horoscope not found: " + id));

        // PATCH: only update fields that are not null
        if (dto.getDob() != null) existing.setDob(dto.getDob());
        if (dto.getTime() != null) existing.setTime(dto.getTime());
        if (dto.getBirthPlace() != null) existing.setBirthPlace(dto.getBirthPlace());
        if (dto.getRashi() != null) existing.setRashi(dto.getRashi());
        if (dto.getNakshatra() != null) existing.setNakshatra(dto.getNakshatra());
        if (dto.getCharan() != null) existing.setCharan(dto.getCharan());
        if (dto.getNadi() != null) existing.setNadi(dto.getNadi());
        if (dto.getGan() != null) existing.setGan(dto.getGan());
        if (dto.getMangal() != null) existing.setMangal(dto.getMangal());
        if (dto.getGotra() != null) existing.setGotra(dto.getGotra());
        if (dto.getDevak() != null) existing.setDevak(dto.getDevak());

        HoroscopeDetails saved = horoscopeRepo.save(existing);

        return HoroscopeDetailsMapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        HoroscopeDetails existing = horoscopeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horoscope not found: " + id));

        horoscopeRepo.delete(existing);
    }
}
