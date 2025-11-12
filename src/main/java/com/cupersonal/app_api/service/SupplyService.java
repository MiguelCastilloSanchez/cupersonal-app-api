package com.cupersonal.app_api.service;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cupersonal.app_api.dto.request.CreateSupplyDTO;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.repository.SupplyRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;

    public Supply createSupply(CreateSupplyDTO dto){
        Supply supply = Supply.builder().
            name(dto.name()).
            unit(dto.unit()).
            quantity(dto.quantity()).
            minimumQuantity(dto.minimumQuantity()).
            build();
        return supplyRepository.save(supply);
    }

    public Supply findSupplyById(int id) throws EntityNotFoundException{
        return this.supplyRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
    }
}
