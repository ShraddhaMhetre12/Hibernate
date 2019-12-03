package com.gsft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsft.model.AssetMaster;
import com.gsft.repository.AssetMasterRepository;

@Service
public class AssetMasterService {

	@Autowired
	private AssetMasterRepository repo;
	
	public AssetMaster saveData(AssetMaster assetMaster) {
		 assetMaster=repo.save(assetMaster);
		 return assetMaster;
	}
	
	public List<AssetMaster> listAll(){
		return repo.findAll();
	}
	
	
}
