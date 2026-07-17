package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonsWithStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasonWithStatusRepository extends JpaRepository<MasonsWithStatus, Integer> {

    List<MasonsWithStatus> findByCurrentStatusTrue(); // Hermanos activos
    List<MasonsWithStatus> findByCurrentStatusFalse(); // Hermanos inactivos
    List<MasonsWithStatus> findByIsFreeMemberTrue(); // Miembros libres (exentos de pago)
}
