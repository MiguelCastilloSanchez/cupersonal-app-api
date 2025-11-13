package com.cupersonal.app_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.dto.request.CreateSupplyDTO;
import com.cupersonal.app_api.dto.response.ProductResponseDTO;
import com.cupersonal.app_api.dto.response.SupplyResponseDTO;
import com.cupersonal.app_api.dto.update.UpdateSupplyDTO;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.service.SupplyService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/supplies")
public class SupplyController {
    @Autowired
    private SupplyService supplyService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Supply createSupply(@Valid @RequestBody CreateSupplyDTO dto){
        return supplyService.createSupply(dto);
    }

    @GetMapping()
    public ResponseEntity<List<SupplyResponseDTO>> findAllSupplies( 
        @PageableDefault(size = 5, sort = "id") Pageable pageable){
        Page<SupplyResponseDTO> supplies = this.supplyService.findAllSupplies(pageable);
        return ResponseEntity.ok(supplies.getContent());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Supply> findSupplyById(@PathVariable int id) throws EntityNotFoundException{
        Supply supply = this.supplyService.findSupplyById(id);
        return new ResponseEntity<Supply>(supply, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supply> updateSupply(@PathVariable int id, @Valid @RequestBody UpdateSupplyDTO dto) throws EntityNotFoundException{
        Supply supply = this.supplyService.updateSupply(id, dto);
        
        return new ResponseEntity<Supply>(supply, HttpStatus.OK);
    }

    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSupply(@PathVariable int id) throws EntityNotFoundException{
        supplyService.deleteSupplyById(id);
    }
}
