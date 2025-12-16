package com.spring.jwt.UserCredit;

import com.spring.jwt.Subscription.SubscriptionRepository;
import com.spring.jwt.entity.Subscription;
import com.spring.jwt.entity.UserCredit;
import com.spring.jwt.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreditServiceImpl implements UserCreditService {

    private final UserCreditRepository repo;
    private final SubscriptionRepository subscriptionRepo;

    @Override
    public UserCreditDTO create(UserCreditDTO dto) {

        // Validate subscription
        Subscription sub = subscriptionRepo.findById(dto.getSubscriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found: " + dto.getSubscriptionId()));

        UserCredit entity = UserCreditMapper.toEntity(dto, sub);

        UserCredit saved = repo.save(entity);

        return UserCreditMapper.toDTO(saved);
    }

    @Override
    public UserCreditDTO getById(Integer id) {
        UserCredit uc = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserCredit not found: " + id));
        return UserCreditMapper.toDTO(uc);
    }

    @Override
    public List<UserCreditDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(UserCreditMapper::toDTO)
                .toList();
    }

    @Override
    public UserCreditDTO update(Integer id, UserCreditDTO dto) {

        UserCredit existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserCredit not found: " + id));

        // Update only editable fields
        if (dto.getTotalCredit() != null) existing.setTotalCredit(dto.getTotalCredit());
        if (dto.getDate() != null) existing.setDate(dto.getDate());
        if (dto.getEndDate() != null) existing.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getUseCredit() != null) existing.setUseCredit(dto.getUseCredit());
        if (dto.getBalanceCredit() != null) existing.setBalanceCredit(dto.getBalanceCredit());
        if (dto.getUserId() != null) existing.setUserId(dto.getUserId());

        // Update subscription if provided
        if (dto.getSubscriptionId() != null) {
            Subscription sub = subscriptionRepo.findById(dto.getSubscriptionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subscription not found: " + dto.getSubscriptionId()));
            existing.setSubscription(sub);
        }

        UserCredit saved = repo.save(existing);
        return UserCreditMapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {

        UserCredit uc = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserCredit not found: " + id));

        repo.delete(uc);
    }
}
