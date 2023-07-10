package ru.skypro.auction.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.auction.dto.*;
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
        lotService.createBid(id, bid);
    }

    @PostMapping("{id}/stop")
    public void stop(@PathVariable int id){
        lotService.stop(id);
    }

    @PostMapping
    public LotDTO createLot(@RequestBody @Valid CreateLot newLot){
        return lotService.createLot(newLot);
    }

    @GetMapping
    public List<LotDTO> findLots(@RequestParam(value = "status", required = false) Status status,
                                 @RequestParam(value = "page",required = false, defaultValue = "0") int page){
        return lotService.findLots(status, page);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> getCSVFile(){
        byte[] result = lotService.getCSVFile();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(result);
    }
}
