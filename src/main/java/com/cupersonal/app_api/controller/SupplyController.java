package com.cupersonal.app_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupersonal.app_api.dto.request.CreateSupplyDTO;
import com.cupersonal.app_api.entity.Supply;
import com.cupersonal.app_api.service.SupplyService;

import jakarta.validation.Valid;

@RestController
public class SupplyController {
    @Autowired
    private SupplyService supplyService;

    @PostMapping(value="/supply")
    @ResponseStatus(HttpStatus.OK)
    public Supply createSupply(@Valid @RequestBody CreateSupplyDTO dto){
        return supplyService.createSupply(dto);
    }
}
