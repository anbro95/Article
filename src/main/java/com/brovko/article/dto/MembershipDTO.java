package com.brovko.article.dto;

import lombok.Data;

import java.util.List;

@Data
public class MembershipDTO {
    private Long id;

    private String description;

    List<UserDTO> userDTOS;
}
