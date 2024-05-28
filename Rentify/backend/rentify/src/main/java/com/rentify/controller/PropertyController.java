package com.Rentify.controller;

import com.Rentify.model.Property;
import com.Rentify.model.User;
import com.Rentify.repository.PropertyRepository;
import com.Rentify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PropertyController {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/properties")
    public ResponseEntity<Property> addProperty(@RequestBody Property property, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        property.setUser(user);
        Property savedProperty = propertyRepository.save(property);
        return ResponseEntity.ok(savedProperty);
    }

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyRepository.findAll());
    }

    @GetMapping("/properties/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            return ResponseEntity.ok(property.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/properties/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property propertyDetails,
            Principal principal) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            if (!property.getUser().getEmail().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            property.setPlace(propertyDetails.getPlace());
            property.setArea(propertyDetails.getArea());
            property.setNumberOfBedrooms(propertyDetails.getNumberOfBedrooms());
            property.setNumberOfBedrooms(propertyDetails.getNumberOfBedrooms());
            property.setNearbyHospitals(propertyDetails.getNearbyHospitals());
            property.setNearbyColleges(propertyDetails.getNearbyColleges());
            Property updatedProperty = propertyRepository.save(property);
            return ResponseEntity.ok(updatedProperty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id, Principal principal) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            if (!property.getUser().getEmail().equals(principal.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            propertyRepository.delete(property);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
