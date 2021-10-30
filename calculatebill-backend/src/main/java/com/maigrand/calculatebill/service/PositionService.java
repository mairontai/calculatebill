package com.maigrand.calculatebill.service;

import com.maigrand.calculatebill.entity.PositionEntity;
import com.maigrand.calculatebill.exception.EntityExistsException;
import com.maigrand.calculatebill.exception.EntityNotFoundException;
import com.maigrand.calculatebill.payload.PositionDetails;
import com.maigrand.calculatebill.repository.PositionRepository;
import com.maigrand.calculatebill.validator.group.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public List<PositionEntity> findAll() {
        return this.positionRepository.findAll();
    }

    public PositionEntity findByName(String name) {
        return this.positionRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("position not found"));
    }

    @Validated(OnCreate.class)
    public PositionEntity create(@Valid PositionDetails details) {
        if (this.positionRepository.existsByName(details.getName())) {
            throw new EntityExistsException("position exists");
        }
        PositionEntity entity = new PositionEntity();

        entity.setName(details.getName());
        entity.setCost(details.getCost());

        return this.positionRepository.save(entity);
    }

    public PositionEntity findByNameOrCreate(@Valid PositionDetails details) {
        Optional<PositionEntity> optionalPositionEntity = this.positionRepository.findByName(details.getName());
        if (optionalPositionEntity.isPresent()) {
            return optionalPositionEntity.get();
        } else {
            PositionEntity entity = new PositionEntity();
            entity.setName(details.getName());
            entity.setCost(details.getCost());
            return this.positionRepository.save(entity);
        }
    }
}
