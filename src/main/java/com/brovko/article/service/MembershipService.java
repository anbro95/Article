package com.brovko.article.service;

import com.brovko.article.model.Membership;

import java.util.List;

public interface MembershipService {
    Membership saveMembership(Membership membership);
    Membership getMembershipById(Long id);
    List<Membership> getAllMemberships();
    String deleteMembershipById(Long id);
    Membership updateMembership(Membership membership);
}
