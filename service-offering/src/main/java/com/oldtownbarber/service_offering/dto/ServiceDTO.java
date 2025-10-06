package com.oldtownbarber.service_offering.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    private Long id;

    private String name;

    private String description;

    private int price;

    private int duration;

    private Long salonId;

    private Long categoryId;

    private String image;
}
