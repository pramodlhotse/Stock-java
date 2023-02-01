package com.example.TechnicalAnalysis.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import com.example.TechnicalAnalysis.feign.FeignClientNSE;
import com.example.TechnicalAnalysis.feign.FeignClientNSEBankNifty;

@Controller
public class DailyAnalysis {
	
	@Autowired
	FeignClientNSEBankNifty nifty;
	
	@GetMapping("symbol")
	public String showAnalysis(Model model) {
		NSE nse = nifty.getLiveNiftyData(FeignBuilder.builder());
		
		model.addAttribute("expiredate", nse.getRecords().getExpiryDates());
		return "index";
		
	}
}
