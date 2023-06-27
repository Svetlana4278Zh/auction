package ru.skypro.auction.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.auction.dto.BidDTO;
import ru.skypro.auction.dto.CreateLot;
import ru.skypro.auction.dto.FullLot;
import ru.skypro.auction.dto.LotDTO;
import ru.skypro.auction.service.LotService;

import java.util.List;

@RestController
@RequestMapping("/lot")
@RequiredArgsConstructor
public class LotController {
    private final LotService lotService;

    @GetMapping("{id}/first")
    public BidDTO getFirstBidder(@PathVariable int id) {
        return lotService.getFirstBidder(id);
    }

    @GetMapping("{id}/frequent")
    public BidDTO getMostFrequentBidder(@PathVariable int id){
        return lotService.getMostFrequentBidder(id);
    }

    @GetMapping("{id}")
    public FullLot getFullLot(@PathVariable int id){
        return lotService.getFullLot(id);
    }

    @PostMapping("{id}/start")
    public void start(@PathVariable int id){
        lotService.start(id);
    }

    @PostMapping("{id}/bid")
    public void createBid(@PathVariable int id, @RequestBody @Valid BidDTO bid){
        lotService.createBid(id,bid);
    }

    @PostMapping("{id}/stop")
    public void stopt(@PathVariable int id){

    }

    @PostMapping
    public LotDTO createLot(@RequestBody @Valid CreateLot lot){
        return null;
    }

    @GetMapping
    public List<LotDTO> findLots(){
        return null;
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> getCSVFile(){
        return null;
    }
}
