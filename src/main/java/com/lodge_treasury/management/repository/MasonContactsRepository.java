package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonContact;
import com.lodge_treasury.management.entity.Mason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasonContactsRepository  extends JpaRepository<MasonContact,Integer> {

    Optional<MasonContact> findByMason(Mason mason);
    Boolean existsByEmail(String email);
    Boolean existsByMobile(String mobile);

    @Modifying
    @Query("DELETE FROM MasonContact mc WHERE mc.mason.masonId = :masonId")
    void deleteByMasonId(@Param("masonId") Integer masonId);

    boolean existsByEmailAndMason_MasonIdNot(String email, Integer masonId);
    boolean existsByMobileAndMason_MasonIdNot(String mobile, Integer masonId);
}
