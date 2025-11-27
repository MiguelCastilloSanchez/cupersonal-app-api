package com.cupersonal.app_api.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cupersonal.app_api.dto.request.CreateSupplyDTO;
import com.cupersonal.app_api.dto.response.SupplyResponseDTO;
import com.cupersonal.app_api.dto.update.UpdateSupplyDTO;
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

    public Page<SupplyResponseDTO> findAllSupplies(Pageable pageable){
        return this.supplyRepository.findAll(pageable)
            .map(supply -> new SupplyResponseDTO(
                supply.getId(),
                supply.getName(),
                supply.getUnit(),
                supply.getQuantity(),
                supply.getMinimumQuantity()
            ));
    }

    public Supply findSupplyById(int id) throws EntityNotFoundException{
        return this.supplyRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
    }

    public Supply updateSupply(int id, UpdateSupplyDTO dto){
        Supply supplyToUpdate = findSupplyById(id);
        supplyToUpdate.setName(dto.name());
        supplyToUpdate.setUnit(dto.unit());
        supplyToUpdate.setQuantity(dto.quantity());
        supplyToUpdate.setMinimumQuantity(dto.minimumQuantity());
        return supplyRepository.save(supplyToUpdate);
    }

    public void deleteSupplyById(int id){
        this.supplyRepository.deleteById(Long.valueOf(id));
    }
}
