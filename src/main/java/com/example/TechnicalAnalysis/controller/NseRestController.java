package com.example.TechnicalAnalysis.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import com.example.TechnicalAnalysis.feign.FeignClientNSE;
import com.example.TechnicalAnalysis.feign.FeignClientNSEBankNifty;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;

@RestController
public class NseRestController{
	
	@Autowired
	FeignClientNSE nseProxy;
	
	@Autowired
	FeignClientNSEBankNifty nifty;
	
	@Autowired
	NSEDataProcessor processor;
	
	@GetMapping("nifty")
	public FilteredResponse getNiftyData(Model model) {
		return processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500,50);
	}
	
	@GetMapping("banknifty")
	public FilteredResponse getBankNifty(Model model) {
		return processor.processBankNiftyData(nseProxy.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
	}
	
}
