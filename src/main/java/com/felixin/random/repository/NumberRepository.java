package com.felixin.random.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  com.felixin.random.domain.Number;

import java.util.List;

@Repository
public interface NumberRepository extends JpaRepository<Number, Long> {

    List<Number> findByValueLessThanEqual(Long i, Pageable pageable);

    List<Number> findByValueGreaterThanEqual(Long i, Pageable pageable);
}