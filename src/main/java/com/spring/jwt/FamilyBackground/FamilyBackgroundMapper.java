package com.spring.jwt.FamilyBackground;

import com.spring.jwt.entity.FamilyBackground;
import com.spring.jwt.entity.User;

public class FamilyBackgroundMapper {

    public static FamilyBackgroundDTO toDTO(FamilyBackground entity) {
        return new FamilyBackgroundDTO(
                entity.getFamilyBackgroundId(),
                entity.getFathersName(),
                entity.getFatherOccupation(),
                entity.getMothersName(),
                entity.getMotherOccupation(),
                entity.getBrother(),
                entity.getMarriedBrothers(),
                entity.getSisters(),
                entity.getMarriedSisters(),
                entity.getInterCasteInFamily(),
                entity.getParentResiding(),
                entity.getFamilyWealth(),
                entity.getMamaSurname(),
                entity.getMamaPlace(),
                entity.getFamilyBackgroundCol(),
                entity.getRelativeSurnames(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    public static FamilyBackground toEntity(FamilyBackgroundDTO dto, User user) {
        FamilyBackground entity = new FamilyBackground();
        entity.setFamilyBackgroundId(dto.getFamilyBackgroundId());
        entity.setFathersName(dto.getFathersName());
        entity.setFatherOccupation(dto.getFatherOccupation());
        entity.setMothersName(dto.getMothersName());
        entity.setMotherOccupation(dto.getMotherOccupation());
        entity.setBrother(dto.getBrother());
        entity.setMarriedBrothers(dto.getMarriedBrothers());
        entity.setSisters(dto.getSisters());
        entity.setMarriedSisters(dto.getMarriedSisters());
        entity.setInterCasteInFamily(dto.getInterCasteInFamily());
        entity.setParentResiding(dto.getParentResiding());
        entity.setFamilyWealth(dto.getFamilyWealth());
        entity.setMamaSurname(dto.getMamaSurname());
        entity.setMamaPlace(dto.getMamaPlace());
        entity.setFamilyBackgroundCol(dto.getFamilyBackgroundCol());
        entity.setRelativeSurnames(dto.getRelativeSurnames());
        entity.setUser(user);
        return entity;
    }
}
