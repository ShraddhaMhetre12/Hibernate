package com.gsft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gsft.model.AssetMaster;
import com.gsft.service.AssetMasterService;

@Controller
public class AssetMasterRouteController {
	
	@Autowired
	AssetMasterService service;
	@RequestMapping("/")
	public String addAssetData(Model model){
		model.addAttribute("assetMaster", new AssetMaster());
		return "view/asset.html";
	}
	
	@RequestMapping("/submitAsset")
	public String submitAsset(Model model){
		List<AssetMaster> list=service.listAll();
		model.addAttribute("assetMaster", list);
		return "view/assetResult.html";
	}
}
