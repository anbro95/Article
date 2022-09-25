package com.brovko.article.service;


import com.brovko.article.model.Membership;
import com.brovko.article.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public Membership saveMembership(Membership membership) {
        log.info("Trying to save membership with id {}", membership.getMembership_id());
        return membershipRepository.save(membership);
    }

    public Membership getMembershipById(Long id){
        log.info("Trying to get membership with id {}", id);
        return membershipRepository.findById(id).orElse(null);
    }

    public List<Membership> getAllMemberships() {
        log.info("Trying to get list of all Memberships");
        return membershipRepository.findAll();
    }
// TODO
//    public Membership getMembershipByType(String type) {}

    public String deleteMembershipById(Long id) {
        log.info("Trying to delete Membership with id {}", id);
        try {
            membershipRepository.deleteById(id);
        } catch (Exception e) {
            return "Could not delete Membership " + id;
        }

        return "Membership deleted successfully!";
    }

    public Membership updateMembership(Membership membership) {
        log.info("Trying to update Membership with id {}", membership.getMembership_id());
        Membership updatedMembership = membershipRepository.findById(membership.getMembership_id()).orElse(null);
        if(updatedMembership == null) return null;

        updatedMembership.setDescription(membership.getDescription());
        updatedMembership.setMembershipType(membership.getMembershipType());

        return membershipRepository.save(membership);
    }



}
