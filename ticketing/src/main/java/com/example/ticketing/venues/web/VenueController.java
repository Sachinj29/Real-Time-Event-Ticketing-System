package com.example.ticketing.venues.web;

import com.example.ticketing.venues.domain.Venue;
import com.example.ticketing.venues.service.VenueService;
import com.example.ticketing.venues.web.dto.CreateVenueRequest;
import com.example.ticketing.venues.web.dto.UpdateVenueRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @PostMapping
    public ResponseEntity<Venue> create(@Valid @RequestBody CreateVenueRequest req) {
        Venue v = new Venue();
        v.setName(req.name());
        v.setAddress(req.address());
        v.setTimezone(req.timezone());
        v.setSeatMapJson(req.seatMapJson());
        return ResponseEntity.ok(venueService.create(v));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venue> update(@PathVariable Long id, @Valid @RequestBody UpdateVenueRequest req) {
        Venue v = venueService.get(id);
        v.setName(req.name());
        v.setAddress(req.address());
        v.setTimezone(req.timezone());
        v.setSeatMapJson(req.seatMapJson());
        return ResponseEntity.ok(venueService.update(v));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> get(@PathVariable Long id) {
        return ResponseEntity.ok(venueService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<Venue>> list(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(venueService.list(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        venueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
