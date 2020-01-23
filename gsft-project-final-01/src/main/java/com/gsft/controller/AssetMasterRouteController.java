package com.gsft.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsft.AssetCreationDto;
import com.gsft.model.AssetMaster;
import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;
import com.gsft.model.LoanCompanyMaster;
import com.gsft.service.AssetTableService;

@Controller
public class AssetMasterRouteController {

	
	private Log log=LogFactory.getLog(AssetMasterRouteController.class);
	
	@Autowired
	AssetTableService service;

	@RequestMapping("/")
	public String asset(Model model) {
		model.addAttribute("assetTable", new AssetCreationDto());
		return "view/submitasset";

	}

	@RequestMapping("/optimizedCal")
	public String submitAsset(Model model) {
		List<CustomerInputDetails> list = service.listAll();
		model.addAttribute("assetTable", list);
		return "view/optimizedCal";
	}
	
	@RequestMapping(value = "/submitAsset", method = RequestMethod.POST)
	public String submitAsset(Model model, @ModelAttribute AssetCreationDto assetTable, Long summaryId) {
		
		CustomerInputSummary customerSummary = service.saveData(assetTable);
		List<CustomerInputDetails> list= customerSummary.getCustomerInputDetails();
		 
		Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
				.getLoanCompanyMasterForCustomerInput(customerSummary.getCustomerInputDetails());
		
	//List<CustomerInputDetails> custList = service.getInputDetailsBySummaryId(customerSummary.getId());
		
		
	//	model.addAttribute("assetTable", custList);
		model.addAttribute("assetTable",customerSummary.getCustomerInputDetails());
		model.addAttribute("loanCompanyMap", loanCompanyMap);
		model.addAttribute("summaryId", customerSummary.getId());
		return "view/optimizedCal";
	}
	
	@RequestMapping(value = "api/asset/", method = RequestMethod.POST)
	public @ResponseBody String getAssetDetails() {
		List<AssetMaster> assetMaster = service.getAssetMasterDetails();
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;

		try {
		json = objectMapper.writeValueAsString(assetMaster);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
		
	}


	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "api/calculate/{id}", method = RequestMethod.POST)
	public @ResponseBody String calculate(@PathVariable Long id) {



		CustomerInputSummary customerInputSummary = service.getInputSummaryById(id);
		List<CustomerInputDetails> customerInputDetailsLst = customerInputSummary.getCustomerInputDetails();

		
		Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
				.getLoanCompanyMasterForCustomerInput(customerInputDetailsLst);

		List<LoanCompanyMaster> abcdLoanMasterLst = loanCompanyMap.containsKey("ABCD") ? loanCompanyMap.get("ABCD")
				: new ArrayList<LoanCompanyMaster>();
		List<LoanCompanyMaster> gsftLoanMasterLst = loanCompanyMap.containsKey("GSFT") ? loanCompanyMap.get("GSFT")
				: new ArrayList<LoanCompanyMaster>();

		// map for GSFT loan master
		Map<Long, CustomerInputDetails> customerInputDetailsMap = customerInputDetailsLst.stream()
				.collect(Collectors.toMap(customerInputDetails -> customerInputDetails.getAssetId().getId(),
						customerInputDetails -> customerInputDetails));

		
		// map for ABCD loan master
		Map<Long, LoanCompanyMaster> abcdLoanMasterMap = abcdLoanMasterLst.stream().collect(Collectors.toMap(
				loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));

		// map for GSFT loan master
		Map<Long, LoanCompanyMaster> gsftLoanMasterMap = gsftLoanMasterLst.stream().collect(Collectors.toMap(
				loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));
		
		

		CustomerInputDetails customerInputDetailstmp = null;
		LoanCompanyMaster loanCompanyMasterABCDtmp = null;
		LoanCompanyMaster loanCompanyMasterGSFTtmp = null;
		
		Double BankRate =0.0;
		Double BankLtv = 0.0;
	
		Double GsftRate =0.0;
		Double GsftLtv = 0.0;
		
		Double AmtTotalGSFT = 0.0;
		Double x =0.0;
	
		Map<Double,Double> GsftRateList = new TreeMap<Double,Double>(); 
		Map<Double,Double> BankRateList = new TreeMap<Double,Double>(); 
		Map<Double,Double> mergeList = new TreeMap<Double,Double>();
		


		for (Long assetId : customerInputDetailsMap.keySet()) {
	
			customerInputDetailstmp = customerInputDetailsMap.get(assetId);
			loanCompanyMasterABCDtmp = abcdLoanMasterMap.containsKey(assetId) ? abcdLoanMasterMap.get(assetId) : null;
			loanCompanyMasterGSFTtmp = gsftLoanMasterMap.containsKey(assetId) ? gsftLoanMasterMap.get(assetId) : null;
			
			
				
			BankRate = loanCompanyMasterABCDtmp.getRate();
			BankLtv = loanCompanyMasterABCDtmp.getLtv();
			GsftRate = loanCompanyMasterGSFTtmp.getRate();
			GsftLtv = loanCompanyMasterGSFTtmp.getLtv();
			
			BankRateList.put(BankRate, BankLtv);
			GsftRateList.put(GsftRate, GsftLtv);
			mergeList.put(BankRate, BankLtv);
			mergeList.put(GsftRate, GsftLtv);
			
			AmtTotalGSFT += ((customerInputDetailstmp.getValue() * customerInputDetailstmp.getLtv())/100);
			x=mergeList.get(BankRate);
			System.out.println("x"+x);
			
		}
		System.out.println("Bank");
		System.out.println(BankRateList);
		System.out.println("Gsft");
		System.out.println(GsftRateList);
		System.out.println("sorted");
		System.out.println(mergeList);
		System.out.println("Loan Amount");
		System.out.println(AmtTotalGSFT);
		
		

		
		
//			
////			//Multiply And Add rate,ltv,tenure to value for GSFT
//		if (customerInputDetailstmp != null) {
//
//			AmtTotalGSFT += ((customerInputDetailstmp.getValue() * customerInputDetailstmp.getLtv())/100);//
//			System.out.println(AmtTotalGSFT);
//			
//					
//			}
		
			//Multiply And Add rate,ltv,tenure to value for ABCD
			
		//Weighted Avg for ABCD
//		rateWeightedAvgABCD = (rateAmtTotalABCD / amtTotalCustInputABCD);
//		ltvWeightedAvgABCD = (ltvAmtTotalABCD / amtTotalCustInputABCD);
//		tenuerWeightedAvgABCD = (tenuerAmtTotalABCD / amtTotalCustInputABCD);
//
//		//Weighted Avg for GSFT
//		rateWeightedAvgGSFT = (rateAmtTotalGSFT / amtTotalCustInputGSFT);
//		ltvWeightedAvgGSFT = (ltvAmtTotalGSFT / amtTotalCustInputGSFT);
//		tenuerWeightedAvgGSFT = (tenuerAmtTotalGSFT / amtTotalCustInputGSFT);

//		Map<String, String> weightedAvgMap = new HashMap<>();
//
//		DecimalFormat df = new DecimalFormat("00.00");
//
//		weightedAvgMap.put("rateWeightedAvgABCD", df.format(rateWeightedAvgABCD));
//		weightedAvgMap.put("ltvWeightedAvgABCD", df.format(ltvWeightedAvgABCD));
//		weightedAvgMap.put("tenuerWeightedAvgABCD", df.format(tenuerWeightedAvgABCD));
//
//		weightedAvgMap.put("rateWeightedAvgGSFT", df.format(rateWeightedAvgGSFT));
//		weightedAvgMap.put("ltvWeightedAvgGSFT", df.format(ltvWeightedAvgGSFT));
//		weightedAvgMap.put("tenuerWeightedAvgGSFT", df.format(tenuerWeightedAvgGSFT));

//		ObjectMapper objectMapper = new ObjectMapper();
//		String json = null;
//
//		try {
//			json = objectMapper.writeValueAsString(weightedAvgMap);
//			
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
	
//
	
		return null;	
}
}
	