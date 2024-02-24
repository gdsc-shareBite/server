package com.gdscsolutionchallenge.shareBite.store.repository;

import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}
