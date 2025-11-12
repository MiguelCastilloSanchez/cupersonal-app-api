package com.cupersonal.app_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.dto.request.CreateSupplyDTO;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.service.SupplyService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/{id}")
    public ResponseEntity<Supply> getSupply(@PathVariable int id) throws EntityNotFoundException{
        Supply supply = this.supplyService.findSupplyById(id);
        return new ResponseEntity<Supply>(supply, HttpStatus.OK);
    }
    
}
