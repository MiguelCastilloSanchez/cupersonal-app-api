package com.cupersonal.app_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cupersonal.app_api.dto.request.CreateSupplyDTO;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.repository.SupplyRepository;

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
}
