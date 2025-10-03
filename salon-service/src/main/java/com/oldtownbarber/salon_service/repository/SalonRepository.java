package com.oldtownbarber.salon_service.repository;

import com.oldtownbarber.salon_service.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Long> {

    Salon findSalonByOwnerId(Long ownerId);

    @Query(
            "SELECT s FROM Salon s " +
                    "WHERE (lower(s.city) LIKE lower(concat('%', :keyword, '%')) " +
                    "OR lower(s.name) LIKE lower(concat('%', :keyword, '%')) " +
                    "OR lower(s.address) LIKE lower(concat('%', :keyword, '%')))"
    )
    List<Salon> searchSalons(@Param("keyword") String keyword);
}
