package com.spring.jwt.ContactDetails;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.entity.CompleteProfile;
import com.spring.jwt.entity.ContactDetails;
import com.spring.jwt.entity.User;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final ContactDetailsRepository contactRepo;
    private final UserRepository userRepo;
    private final CompleteProfileRepository completeProfileRepo;
    private final CompleteProfileService completeProfileService;

    // CREATE
    @Override
    @Transactional
    public ContactDetailsDTO create(ContactDetailsDTO dto) {

        Integer userId = dto.getUserId();

        // Check user exists
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        // Prevent duplicate contact
        if (contactRepo.findByUser_Id(userId).isPresent()) {
            throw new ResourceAlreadyExistsException("Contact details already exist for this user: " + userId);
        }

        // Convert DTO → Entity
        ContactDetails entity = ContactDetailsMapper.toEntity(dto, user);

        ContactDetails saved = contactRepo.save(entity);

        // Update or create CompleteProfile
        CompleteProfile cp = completeProfileRepo.findByUser_Id(userId)
                .orElseGet(() -> {
                    CompleteProfile newCP = new CompleteProfile();
                    newCP.setUser(user);
                    newCP.setStatusCol("COMPLETE");
                    newCP.setProfileCompleted(false);
                    return newCP;
                });

        cp.setContactDetails(saved);

        CompleteProfile persistedCP = completeProfileRepo.save(cp);

        // Auto-update completion %
        completeProfileService.recalcAndSave(persistedCP);

        return ContactDetailsMapper.toDTO(saved);
    }

    // GET BY ID
    @Override
    public ContactDetailsDTO getById(Integer id) {
        ContactDetails entity = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact details not found with ID: " + id));

        return ContactDetailsMapper.toDTO(entity);
    }

    // GET ALL
    @Override
    public List<ContactDetailsDTO> getAll() {
        return contactRepo.findAll()
                .stream()
                .map(ContactDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    @Transactional
    public ContactDetailsDTO update(Integer id, ContactDetailsDTO dto) {

        ContactDetails existing = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact details not found with ID: " + id));

        // User cannot be changed
        User user = existing.getUser();

        // PATCH logic
        if (dto.getFullAddress() != null) existing.setFullAddress(dto.getFullAddress());
        if (dto.getCity() != null) existing.setCity(dto.getCity());
        if (dto.getPinCode() != null) existing.setPinCode(dto.getPinCode());
        if (dto.getMobileNumber() != null) existing.setMobileNumber(dto.getMobileNumber());
        if (dto.getAlternateNumber() != null) existing.setAlternateNumber(dto.getAlternateNumber());

        existing.setUser(user);

        ContactDetails saved = contactRepo.save(existing);

        return ContactDetailsMapper.toDTO(saved);
    }

    // DELETE
    @Override
    public void delete(Integer id) {
        ContactDetails entity = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact details not found with ID: " + id));

        contactRepo.delete(entity);
    }
}
