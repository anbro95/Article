package com.brovko.article.controller;

import com.brovko.article.model.Membership;
import com.brovko.article.service.MembershipService;
import com.brovko.article.service.MembershipServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class MembershipController {
    private final MembershipService membershipService;
    @PostMapping("/memberships")
    public ResponseEntity<Membership> saveMembership(@RequestBody Membership membership) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/memberships").toUriString());
        return ResponseEntity.created(uri).body(membershipService.saveMembership(membership));
    }

    @GetMapping("/memberships/{id}")
    public ResponseEntity<?> getMembershipById(@PathVariable Long id) {
        Membership membership = membershipService.getMembershipById(id);
        return ResponseEntity.ok().body(membership == null ? "Membership with id " + id + " not found" : membership);
    }

    @GetMapping("/memberships")
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok().body(membershipService.getAllMemberships());
    }

    @DeleteMapping("/memberships/{id}")
    public String deleteMembership(@PathVariable Long id) {
        return membershipService.deleteMembershipById(id);
    }

    @PutMapping("/memberships")
    public ResponseEntity<?> updateMembership(@RequestBody Membership membership) {
        Membership updatedMembership = membershipService.updateMembership(membership);
        return ResponseEntity.ok().body(updatedMembership == null ? "Membership " + membership.getMembership_id() : membership);
    }


}
