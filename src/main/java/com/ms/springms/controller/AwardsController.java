package com.ms.springms.controller;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Awards;
import com.ms.springms.service.awards.AwardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AwardsController {


    @Autowired
    private AwardsService awardsService;

    @PostMapping("/create-awards")
    public ResponseEntity<?> createAwards( @RequestBody Awards awards){

        try {
            String result = awardsService.createEvent(awards);
            return  new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DuplicateEntryException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }


    @GetMapping("/get-all-awards")
    public List<Awards> getAllAwards(){
        return awardsService.getAllAwards();
    }

}
