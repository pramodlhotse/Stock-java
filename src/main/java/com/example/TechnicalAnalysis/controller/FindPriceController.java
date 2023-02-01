package com.example.TechnicalAnalysis.controller;

import com.example.TechnicalAnalysis.entity.CE;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.entity.NSE;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import com.example.TechnicalAnalysis.feign.FeignClientNSE;
import com.example.TechnicalAnalysis.feign.FeignClientNSEBankNifty;
import com.example.TechnicalAnalysis.model.ExpiryDates;
import com.example.TechnicalAnalysis.model.PriceResponse;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;
import com.example.TechnicalAnalysis.repository.CERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/price")
public class FindPriceController {
    @Autowired
    FeignClientNSEBankNifty nifty;

    @Autowired
    FeignClientNSE nseProxy;

    @Autowired
    NSEDataProcessor processor;

    @Autowired
    private CERepository ceRepository;

    private static  FilteredResponse filteredResponse;
    @GetMapping("/current")
    public List<CE> getPrice(@RequestParam("start") Optional<String> startDate, @RequestParam("symbol") Optional<String> symbol) throws ParseException {

        String start = startDate.get().substring(0, startDate.get().indexOf("GMT")) + "GMT+" +
                startDate.get().substring(startDate.get().indexOf("GMT")+4);

        DateFormat inputFormat = new SimpleDateFormat(
                "E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
        Date date = inputFormat.parse(start);

        DateFormat outputFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm",
                Locale.ENGLISH);
        //outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        String output = outputFormat.format(date);
        System.out.println(output);
        System.out.println(symbol.get());

        List<CE> ce = ceRepository.findByDate(output, symbol.get());
        return ce;
    }
    @GetMapping("/curr-strike-price")
    public PriceResponse getCurrentStrikePrice(@RequestParam("symbol") Optional<String> symbol) {
        FilteredResponse filteredResponse = null;
        System.out.println("request for current strike price");
       try {
           if (symbol.get().equals("BANKNIFTY")) {
               filteredResponse = processor.processBankNiftyData(nseProxy.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
           }
           if (symbol.get().equals("NIFTY")) {
               filteredResponse = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
           }
       } catch (Exception e) {
           System.out.println("Exception caught => " + e.getMessage());
           return null;
       }

        return new PriceResponse(1,  filteredResponse.getUnderlyingPrice(),
                filteredResponse.getRecords().get(10).getCe().getLastPrice(),
                filteredResponse.getRecords().get(10).getPe().getLastPrice(),
                filteredResponse.getRecords().get(0).getExpiryDate());
    }

    @GetMapping("/expiry-dates")
    public List<ExpiryDates> showAnalysis() {
        List<String> dates = List.of(nifty.getLiveNiftyData(FeignBuilder.builder()).getRecords().getExpiryDates());
        Map<String, String> map = new HashMap<>();
        List<ExpiryDates> list = new ArrayList<>();
        long i = 1;
        for (String s : dates) {
           list.add(new ExpiryDates(i, s));
           i++;
        }
        return list;

    }

}
