package com.spring.jwt.ExpressInterest;

import com.spring.jwt.entity.Enums.InterestStatus;
import com.spring.jwt.entity.ExpressInterest;
import com.spring.jwt.entity.User;
import com.spring.jwt.entity.UserProfile;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.profile.UserProfileDTO2;
import com.spring.jwt.profile.UserProfileMapper;
import com.spring.jwt.repository.UserProfileRepository;
import com.spring.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpressInterestServiceImpl implements ExpressInterestService {

    private final ExpressInterestRepository repo;
    private final UserRepository userRepo;
    private final UserProfileRepository userProfileRepository;

    @Override
    public ExpressInterestDTO create(ExpressInterestDTO dto) {

        User fromUser = userRepo.findById(dto.getFromUser())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        if (repo.existsByFromUser_IdAndToUserId(dto.getFromUser(), dto.getToUserId())) {
            throw new ResourceAlreadyExistsException("Interest already sent to this user.");
        }

        dto.setCreatedAt(LocalDateTime.now());
        dto.setStatus(InterestStatus.PENDING);

        ExpressInterest entity = ExpressInterestMapper.toEntity(dto, fromUser);

        ExpressInterest saved = repo.save(entity);

        return ExpressInterestMapper.toDTO(saved);
    }

    @Override
    public ExpressInterestDTO getById(Long id) {
        ExpressInterest e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found"));
        return ExpressInterestMapper.toDTO(e);
    }

    @Override
    public List<ExpressInterestDTO> getSent(Integer userId) {
        return repo.findByFromUser_Id(userId)
                .stream()
                .map(ExpressInterestMapper::toDTO)
                .toList();
    }

    @Override
    public List<ExpressInterestDTO> getReceived1(Integer userId) {
        return repo.findByToUserId(userId)
                .stream()
                .map(ExpressInterestMapper::toDTO)
                .toList();
    }

    @Override
    public ExpressInterestDTO updateStatus(Long id, String status) {
        ExpressInterest e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found"));

        e.setStatus(InterestStatus.valueOf(status.toUpperCase()));

        ExpressInterest saved = repo.save(e);

        return ExpressInterestMapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        ExpressInterest e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found"));
        repo.delete(e);
    }

    @Override
    public List<ExpressInterestDTO> getSent(Integer userId, String status) {
        validateUser(userId);
        InterestStatus st = parseStatus(status);
        return repo.findByFromUser_IdAndStatus(userId, st)
                .stream()
                .map(ExpressInterestMapper::toDTO)
                .toList();
    }

    @Override
    public List<ExpressInterestWithProfileDTO> getReceived(Integer userId, String status) {

        validateUser(userId);
        InterestStatus st = parseStatus(status);

        List<ExpressInterest> list = repo.findByToUserIdAndStatus(userId, st);

        return list.stream()
                .map(interest -> {

                    // Get sender profile
                    UserProfile senderProfile = userProfileRepository
                            .findByUser_Id(Math.toIntExact(interest.getFromUser().getId()))
                            .orElse(null);

                    UserProfileDTO2 senderDto = null;
                    if (senderProfile != null) {
                        senderDto = UserProfileMapper.toDTO(senderProfile);
                    }

                    return new ExpressInterestWithProfileDTO(
                            interest.getInterestId(),
                            Math.toIntExact(interest.getFromUser().getId()),
                            interest.getToUserId(),
                            interest.getStatus().name(),
                            interest.getCreatedAt().toString(),
                            senderDto
                    );
                })
                .toList();
    }

    private void validateUser(Integer userId) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }


    /**
     * Converts status safely and throws meaningful exception.
     */
    private InterestStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new InvalidStatusException("Status cannot be empty. Allowed values: PENDING, ACCEPTED, DECLINED.");
        }

        try {
            return InterestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusException("Invalid status: " + status +
                    ". Allowed values: PENDING, ACCEPTED, DECLINED.");
        }
    }
    @Override
    public InterestCountDTO getInterestSummary(Integer userId) {

        // Validate user exists
        validateUser(userId);

        Long sentAccepted     = repo.countByFromUser_IdAndStatus(userId, InterestStatus.ACCEPTED);
        Long sentDeclined     = repo.countByFromUser_IdAndStatus(userId, InterestStatus.DECLINED);
        Long sentPending      = repo.countByFromUser_IdAndStatus(userId, InterestStatus.PENDING);

        Long receivedAccepted = repo.countByToUserIdAndStatus(userId, InterestStatus.ACCEPTED);
        Long receivedDeclined = repo.countByToUserIdAndStatus(userId, InterestStatus.DECLINED);
        Long receivedPending  = repo.countByToUserIdAndStatus(userId, InterestStatus.PENDING);

        return new InterestCountDTO(
                sentAccepted,
                sentDeclined,
                sentPending,
                receivedAccepted,
                receivedDeclined,
                receivedPending
        );
    }

}
