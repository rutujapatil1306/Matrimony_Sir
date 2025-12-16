package com.spring.jwt.CompleteProfile;

import com.spring.jwt.entity.CompleteProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompleteProfileService {

    private final CompleteProfileRepository completeProfileRepository;

    /**
     * Recalculate profileCompleted based on presence of sections.
     * Rule (example): profileCompleted = true when these sub-entities are non-null:
     *   userProfile, horoscopeDetails, educationAndProfession, familyBackground,
     *   partnerPreference, contactDetails, documents (or documentIds)
     *
     * You can adjust business rule as required.
     */
    public void recalcAndSave(CompleteProfile cp) {
        boolean completed = true;

        if (cp.getUserProfile() == null) completed = false;
        if (cp.getHoroscopeDetails() == null) completed = false;
        if (cp.getEducationAndProfession() == null) completed = false;
        if (cp.getFamilyBackground() == null) completed = false;
        if (cp.getPartnerPreference() == null) completed = false;
        if (cp.getContactDetails() == null) completed = false;

        // documents list or documentIds not empty
        if ((cp.getDocuments() == null || cp.getDocuments().isEmpty())
                && (cp.getDocumentIds() == null || cp.getDocumentIds().trim().isEmpty())) {
            completed = false;
        }

        cp.setProfileCompleted(completed);
        completeProfileRepository.save(cp);
    }
}
