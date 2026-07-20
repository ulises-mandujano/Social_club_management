package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.entity.MasonDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MasonDegreesRepository extends JpaRepository<MasonDegree,Integer> {

    @Query("SELECT md FROM MasonDegree md WHERE md.mason.masonId = :masonId ORDER BY md.receivedDate " +
            "DESC, md.degreeId DESC")
    List<MasonDegree> findLatestByMasonId(@Param("masonId") Integer masonId, Pageable pageable);

    @Query("SELECT md FROM MasonDegree md WHERE md.degreeId IN " +
           "(SELECT MAX(md2.degreeId) FROM MasonDegree md2 GROUP BY md2.mason.masonId)")
    List<MasonDegree> findAllLastestDegrees();
}
