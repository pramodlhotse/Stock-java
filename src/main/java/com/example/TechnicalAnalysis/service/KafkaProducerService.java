package com.example.TechnicalAnalysis.service;

import com.example.TechnicalAnalysis.entity.CE;
import com.example.TechnicalAnalysis.entity.FilteredResponse;
import com.example.TechnicalAnalysis.entity.PE;
import com.example.TechnicalAnalysis.feign.FeignBuilder;
import com.example.TechnicalAnalysis.feign.FeignClientNSE;
import com.example.TechnicalAnalysis.feign.FeignClientNSEBankNifty;
import com.example.TechnicalAnalysis.model.Result;
import com.example.TechnicalAnalysis.processor.NSEDataProcessor;
import com.example.TechnicalAnalysis.repository.CEPastRepository;
import com.example.TechnicalAnalysis.repository.CERepository;
import com.example.TechnicalAnalysis.repository.DailyIntervalTesting;
import com.example.TechnicalAnalysis.repository.PERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class KafkaProducerService {

	@Value("${kafka.topic}")
	private String topicName;

	@Value("${kafka.sensehat}")
	private String senseHat;
	@Autowired
	FeignClientNSEBankNifty nifty;

	@Autowired
	FeignClientNSE nseProxy;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	NSEDataProcessor processor;


	@Autowired
	DailyIntervalTesting dailyIntervalTesting;

	@Autowired
	PERepository peRepository;

	@Autowired
	CERepository ceRepository;
	@Autowired
	CEPastRepository cePastRepository;

	private Date callDateTime;


	public void sendMessage(String records) {
		kafkaTemplate.send(topicName, records);
	}
	public void sendMessage(String records, String topicName) {
		kafkaTemplate.send(topicName, records);
	}

	@Scheduled(fixedRate = 60000)
	public void schedulerMethod() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime start = LocalTime.parse( "09:15:00" );
		LocalTime stop = LocalTime.parse( "15:30:00" );
		LocalTime target = LocalTime.parse(dtf.format(LocalDateTime.now()));;
		Boolean isTargetAfterStartAndBeforeStop = (target.isAfter(start) && target.isBefore(stop) ) ;
		if (false) {
			System.out.println("--- Stock is open now ---");
			callDateTime = new Date();
			callNifty(callDateTime);
			callBankNifty(callDateTime);
		} else {
			System.out.println("Stock is not open now -> it will open 9:15 AM - 3:30 PM");
		}

	}


		public void init() {
			callDateTime = new Date();
			callNifty(callDateTime);
			callBankNifty(callDateTime);
	}

	private void callNifty(Date callDateTime) {
		FilteredResponse filteredResponse = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
		Result result = new Result();
		result.setCallTotalOI(filteredResponse.getCallTotalOI());
		result.setCurrentStrike(filteredResponse.getCurrentStrike());
		result.setCallTotalVolume(filteredResponse.getCallTotalVolume());
		result.setPutTotalOI(filteredResponse.getPutTotalOI());
		result.setPastUnderlyingPrice(filteredResponse.getPastUnderlyingPrice());
		result.setUnderlyingPrice(filteredResponse.getUnderlyingPrice());
		result.setPutTotalVolume(filteredResponse.getPutTotalVolume());
		result.setDate(callDateTime);
		result.setUnderlying(filteredResponse.getRecords().get(0).getCe().getUnderlying());
		dailyIntervalTesting.save(result);

		int size = filteredResponse.getPastRecords().size();
		List<PE> peList = new ArrayList<>();
		List<CE> ceList = new ArrayList<>();
		List<PE> peList1 = new ArrayList<>();
		List<CE> ceList1 = new ArrayList<>();
		for (int i = 0; i < size; i++) {

			filteredResponse.getRecords().get(i).getCe().setPastAndCurrentRecord("Current");
			filteredResponse.getRecords().get(i).getCe().setDate(callDateTime);
			filteredResponse.getRecords().get(i).getCe().setId(result.getId());
			filteredResponse.getRecords().get(i).getCe().setCeId(rand());
			filteredResponse.getRecords().get(i).getPe().setPastAndCurrentRecord("Current");
			filteredResponse.getRecords().get(i).getPe().setDate(callDateTime);
			filteredResponse.getRecords().get(i).getPe().setId(result.getId());
			filteredResponse.getRecords().get(i).getPe().setPeId(rand());
			peList1.add(filteredResponse.getRecords().get(i).getPe());
			ceList1.add(filteredResponse.getRecords().get(i).getCe());

		}

		savePe(peList1);
		saveCe(ceList1);

		for (int i = 0; i < size; i++) {
			filteredResponse.getPastRecords().get(i).getCe().setPastAndCurrentRecord("Past");
			filteredResponse.getPastRecords().get(i).getCe().setDate(callDateTime);
			filteredResponse.getPastRecords().get(i).getCe().setId(result.getId());
			filteredResponse.getPastRecords().get(i).getCe().setCeId(rand());
			filteredResponse.getPastRecords().get(i).getPe().setPastAndCurrentRecord("Past");
			filteredResponse.getPastRecords().get(i).getPe().setDate(callDateTime);
			filteredResponse.getPastRecords().get(i).getPe().setId(result.getId());
			filteredResponse.getPastRecords().get(i).getPe().setPeId(rand());
			peList.add(filteredResponse.getPastRecords().get(i).getPe());
			ceList.add(filteredResponse.getPastRecords().get(i).getCe());


		}

		savePe(peList);
		saveCe(ceList);
	}

	private void callBankNifty(Date callDateTime) {
		FilteredResponse filteredResponse = processor.processBankNiftyData(nseProxy.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
		Result result = new Result();
		result.setCallTotalOI(filteredResponse.getCallTotalOI());
		result.setCurrentStrike(filteredResponse.getCurrentStrike());
		result.setCallTotalVolume(filteredResponse.getCallTotalVolume());
		result.setPutTotalOI(filteredResponse.getPutTotalOI());
		result.setPastUnderlyingPrice(filteredResponse.getPastUnderlyingPrice());
		result.setUnderlyingPrice(filteredResponse.getUnderlyingPrice());
		result.setPutTotalVolume(filteredResponse.getPutTotalVolume());
		result.setDate(callDateTime);
		result.setUnderlying(filteredResponse.getRecords().get(0).getCe().getUnderlying());
		dailyIntervalTesting.save(result);

		int size = filteredResponse.getPastRecords().size();
		List<PE> peList = new ArrayList<>();
		List<CE> ceList = new ArrayList<>();
		List<PE> peList1 = new ArrayList<>();
		List<CE> ceList1 = new ArrayList<>();
		for (int i = 0; i < size; i++) {

			filteredResponse.getRecords().get(i).getCe().setPastAndCurrentRecord("Current");
			filteredResponse.getRecords().get(i).getCe().setDate(callDateTime);
			filteredResponse.getRecords().get(i).getCe().setId(result.getId());
			filteredResponse.getRecords().get(i).getCe().setCeId(rand());
			filteredResponse.getRecords().get(i).getPe().setPastAndCurrentRecord("Current");
			filteredResponse.getRecords().get(i).getPe().setDate(callDateTime);
			filteredResponse.getRecords().get(i).getPe().setId(result.getId());
			filteredResponse.getRecords().get(i).getPe().setPeId(rand());
			peList1.add(filteredResponse.getRecords().get(i).getPe());
			ceList1.add(filteredResponse.getRecords().get(i).getCe());

		}

		savePe(peList1);
		saveCe(ceList1);

		for (int i = 0; i < size; i++) {
			filteredResponse.getPastRecords().get(i).getCe().setPastAndCurrentRecord("Past");
			filteredResponse.getPastRecords().get(i).getCe().setDate(callDateTime);
			filteredResponse.getPastRecords().get(i).getCe().setId(result.getId());
			filteredResponse.getPastRecords().get(i).getCe().setCeId(rand());
			filteredResponse.getPastRecords().get(i).getPe().setPastAndCurrentRecord("Past");
			filteredResponse.getPastRecords().get(i).getPe().setDate(callDateTime);
			filteredResponse.getPastRecords().get(i).getPe().setId(result.getId());
			filteredResponse.getPastRecords().get(i).getPe().setPeId(rand());
			peList.add(filteredResponse.getPastRecords().get(i).getPe());
			ceList.add(filteredResponse.getPastRecords().get(i).getCe());


		}

		savePe(peList);
		saveCe(ceList);
	}

private long rand() {
	return (long) (Math.floor(Math.random() * (9 * Math.pow(10, 9))) + Math.pow(10, (9)));
}
	@Transactional
	public void savePe(List<PE> peList) {
		peRepository.saveAllAndFlush(peList);

	}

	@Transactional
	public void saveCe(List<CE> peList) {
		ceRepository.saveAllAndFlush(peList);
	}
	}

	//public void init() {
//
//		Map<String, String> value = new HashMap<>();
//		DecimalFormat df = new DecimalFormat("#.##");
//			new Thread(() -> {
//				DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//				LocalDateTime now = null;
//				LocalTime start = LocalTime.parse( "09:15:00" );
//				LocalTime stop = LocalTime.parse( "15:30:00" );
//				LocalTime target = null;
//				while (true) {
//					target = LocalTime.parse(dtf.format(LocalDateTime.now()));
//					Boolean isTargetAfterStartAndBeforeStop = (target.isAfter(start) && target.isBefore(stop) ) ;
//					try {
//						if (isTargetAfterStartAndBeforeStop) {
//							System.out.println("--- Stock is open now ---");
//							try {
//								FilteredResponse result = processor.processNiftyData(nifty.getLiveNiftyData(FeignBuilder.builder()), 500, 50);
//
//								value.put("currentStrike", String.valueOf(result.getCurrentStrike()));
//
//
//
//								int size = result.getPastRecords().size();
//								now = LocalDateTime.now();
//								for (int i = 0; i < size; i++) {
//									//	value.put("impliedVolatility", "totalTradedVolume");
//									value.put("P_IV", String.valueOf(result.getPastRecords().get(i).getPe().getImpliedVolatility()));
//									value.put("P_VOLUME", String.valueOf(result.getPastRecords().get(i).getPe().getTotalTradedVolume()));
//									value.put("P_CHNG IN OI", String.valueOf(result.getPastRecords().get(i).getPe().getChangeinOpenInterest()));
//									value.put("P_OI", String.valueOf(result.getPastRecords().get(i).getPe().getOpenInterest()));
//
//									value.put("C_IV", String.valueOf(result.getPastRecords().get(i).getCe().getImpliedVolatility()));
//									value.put("C_VOLUME", String.valueOf(result.getPastRecords().get(i).getCe().getTotalTradedVolume()));
//									value.put("C_CHNG IN OI", String.valueOf(result.getPastRecords().get(i).getCe().getChangeinOpenInterest()));
//									value.put("C_OI", String.valueOf(result.getPastRecords().get(i).getCe().getOpenInterest()));
//
//									value.put("strikePrice", String.valueOf(result.getPastRecords().get(i).getPe().getStrikePrice()));
//
//									double cpr = Double.parseDouble(df.format(result.getPastRecords().get(i).getPe().getOpenInterest())) /
//											Double.parseDouble(df.format(result.getPastRecords().get(i).getCe().getOpenInterest()));
//									value.put("cpr", String.valueOf(df.format(cpr)));
//									value.put("days", date.format(now));
//									value.put("time", dtf.format(now));
//									Gson gson = new Gson();
//									String json = gson.toJson(value);
//									sendMessage(json, "nifty1111");
//								}
//								value.clear();
//
//								try {
//									Thread.sleep(3*60000);
//								} catch (InterruptedException e) {
//									throw new RuntimeException(e);
//								}
//							} catch (Exception e) {
//								System.out.println("Exception occurred -> " + e.getMessage());
//							}
//						} else {
//							System.out.println("Stock is not open now -> it will open 9:15 AM - 3:30 PM");
//						}
//					} finally {
//
//					}
//					}
//
//			}).start();
//
//		new Thread(() -> {
//			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
//			LocalDateTime now = null;
//			LocalTime start = LocalTime.parse( "09:15:00" );
//			LocalTime stop = LocalTime.parse( "15:30:00" );
//			LocalTime target = null;
//
//			while (true) {
//				target = LocalTime.parse(dtf.format(LocalDateTime.now()));
//				Boolean isTargetAfterStartAndBeforeStop = (target.isAfter(start) && target.isBefore(stop) ) ;
//				try {
//					if (isTargetAfterStartAndBeforeStop) {
//						System.out.println("--- Stock is open now ---");
//						try {
//
//							FilteredResponse result = processor.processBankNiftyData(nseProxy.getLiveBankNiftyData(FeignBuilder.builder()), 1000, 100);
//							int size = result.getPastRecords().size();
//							now = LocalDateTime.now();
//							for (int i = 0; i < size; i++) {
//								//	value.put("impliedVolatility", "totalTradedVolume");
//								value.put("P_IV", String.valueOf(result.getPastRecords().get(i).getPe().getImpliedVolatility()));
//								value.put("P_VOLUME", String.valueOf(result.getPastRecords().get(i).getPe().getTotalTradedVolume()));
//								value.put("P_CHNG IN OI", String.valueOf(result.getPastRecords().get(i).getPe().getChangeinOpenInterest()));
//								value.put("P_OI", String.valueOf(result.getPastRecords().get(i).getPe().getOpenInterest()));
//
//								value.put("C_IV", String.valueOf(result.getPastRecords().get(i).getCe().getImpliedVolatility()));
//								value.put("C_VOLUME", String.valueOf(result.getPastRecords().get(i).getCe().getTotalTradedVolume()));
//								value.put("C_CHNG IN OI", String.valueOf(result.getPastRecords().get(i).getCe().getChangeinOpenInterest()));
//								value.put("C_OI", String.valueOf(result.getPastRecords().get(i).getCe().getOpenInterest()));
//
//								value.put("strikePrice", String.valueOf(result.getPastRecords().get(i).getPe().getStrikePrice()));
//
//								double cpr = Double.parseDouble(df.format(result.getPastRecords().get(i).getPe().getOpenInterest())) /
//										Double.parseDouble(df.format(result.getPastRecords().get(i).getCe().getOpenInterest()));
//								value.put("cpr", String.valueOf(df.format(cpr)));
//								value.put("days", date.format(now));
//								value.put("time", dtf.format(now));
//								Gson gson = new Gson();
//								String json = gson.toJson(value);
//								sendMessage(json, "bankNifty1111");
//							}
//							value.clear();
//
//							try {
//								Thread.sleep(3*60000);
//							} catch (InterruptedException e) {
//								throw new RuntimeException(e);
//							}
//						} catch (Exception e) {
//							System.out.println("Exception occurred -> " + e.getMessage());
//						}
//					} else {
//						System.out.println("Stock is not open now -> it will open 9:15 AM - 3:30 PM");
//					}
//				} finally {
//
//				}
//			}
//
//		}).start();
//		}


