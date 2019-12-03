package com.gsft.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gsft.model.AssetMaster;

@Repository
public interface AssetMasterRepository extends JpaRepository<AssetMaster,Long> {

}
