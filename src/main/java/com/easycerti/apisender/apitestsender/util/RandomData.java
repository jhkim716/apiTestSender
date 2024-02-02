package com.easycerti.apisender.apitestsender.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RandomData {

	@Value("${random-set.usersInfo}")			// 유저정보
	private List<String> userInfoList;
	
	@Value("${random-set.urls}")				// url 정보
	private String[] urlArr;
	@Value("${random-set.urlLength}")			// url 길이
	private String urlLength;
	
	@Value("${random-set.hoursPer}")			// 시간정보
	private List<String> hourList;
	
	@Value("${random-set.reqTypes}")			// 요청 타입정보
	private List<String> reqTypeList;
	
	@Value("${random-set.systemSeqs}")			// 시스템 Seq 정보
	private List<String> systemSeqList;
	
	@Value("${random-set.abTypes}")				// 개인정보 타입정보
	private List<String> abTypeList;
	
	@Value("${random-set.eachDetailMinCnt:1}")	// 로그당 상세로그 최소값
	private String eachDetailMinCnt;
	
	@Value("${random-set.eachDetailMaxCnt:1}")	// 로그당 상세로그 최대값
	private String eachDetailMaxCnt;
	
	@Value("${random-set.downfileNames}")		// 파일이름 리스트	// 다운로드 로그
	private List<String> downfileNameList;
	
	@Value("${random-set.downReasonTypes}")		// 다운로드 사유 리스트	// 다운로드 로그
	private List<String> downReasonTypeList;
	
	// 랜덤 수집로그 가져오기
	public String getRandomCollectLogListStr(Integer listSize) {
		
		// --- 설정 준비
		String data = "";
		List<Map<String, Object>> collectLogMapList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		int urlLengthNum = Integer.parseInt(urlLength);
		int eachDetailMinCntNum = Integer.parseInt(eachDetailMinCnt);
		int eachDetailMaxCntNum = Integer.parseInt(eachDetailMaxCnt);
		
		String dateString = LocalDate.now().toString();
		List<String> timeListSet = getTimeArrSet(100);
		// --- 설정 준비
		
		for (int i = 0; i < listSize; i++) {
			try {
				Map<String, Object> collectLogMap = new HashMap<>();
				
				setUserInfoMap(collectLogMap);				// 유저 정보
				setUrlMap(collectLogMap, urlLengthNum);		// Url 정보
				setReqTypeMap(collectLogMap);				// 요청 타입정보
				setSystemSeqMap(collectLogMap);				// 시스템 Seq 정보
				setAbContentsMap(collectLogMap, eachDetailMinCntNum, eachDetailMaxCntNum);	// 상세로그 리스트 정보
				setLogTimeMap(collectLogMap, dateString, timeListSet);	// 일시정보
				
				collectLogMapList.add(collectLogMap);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// List 를 String 값으로 변환
		try {
			data = mapper.writeValueAsString(collectLogMapList);
		} catch (JsonProcessingException e) {
			data = "";
			e.printStackTrace();
		}
		
		return data;
	}
	
	// 랜덤 다운로드로그 가져오기
	public String getRandomCollectLogDnListStr(Integer listSize) {
		
		// --- 설정 준비
		String data = "";
		List<Map<String, Object>> downloadLogMapList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		int urlLengthNum = Integer.parseInt(urlLength);
		int eachDetailMinCntNum = Integer.parseInt(eachDetailMinCnt);
		int eachDetailMaxCntNum = Integer.parseInt(eachDetailMaxCnt);
		
		String dateString = LocalDate.now().toString();
		List<String> timeListSet = getTimeArrSet(100);
		// --- 설정 준비
		
		for (int i = 0; i < listSize; i++) {
			try {
				Map<String, Object> downloadLogMap = new HashMap<>();
				
				setUserInfoMap(downloadLogMap);				// 유저 정보
				setUrlMap(downloadLogMap, urlLengthNum);		// Url 정보
				setReqTypeMap(downloadLogMap);				// 요청 타입정보
				setSystemSeqMap(downloadLogMap);				// 시스템 Seq 정보
				setAbContentsMap(downloadLogMap, eachDetailMinCntNum, eachDetailMaxCntNum);	// 상세로그 리스트 정보
				setLogTimeMap(downloadLogMap, dateString, timeListSet);	// 일시정보
				setDownfileNameMap(downloadLogMap);		// 다운로드 파일이름 정보
				setDownReasonTypeMap(downloadLogMap);	// 다운로드 이유 정보
				
				downloadLogMapList.add(downloadLogMap);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// List 를 String 값으로 변환
		try {
			data = mapper.writeValueAsString(downloadLogMapList);
		} catch (JsonProcessingException e) {
			data = "";
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	// 데이터 세팅
	//---------------------------------------------------------------
	// 유저 정보
	private void setUserInfoMap(Map<String, Object> logMap) throws Exception {
		Random random = new Random();
		int index = random.nextInt(userInfoList.size());
		
		String[] tempArr = userInfoList.get(index).split(":");
		
		logMap.put("userName", tempArr[0].toString());
		logMap.put("userId", tempArr[1].toString());
		logMap.put("userIp", tempArr[2].toString());
		logMap.put("deptId", tempArr[3].toString());
		logMap.put("deptName", tempArr[4].toString());
	}
	
	// Url 정보
	private void setUrlMap(Map<String, Object> logMap, int length) throws Exception {
		Random random = new Random();
		String url = "";
		
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(urlArr.length);
			url += "/" + urlArr[index];
		}
		logMap.put("reqUrl", url.toString());
	}
	
	// 요청 타입정보
	private void setReqTypeMap(Map<String, Object> logMap) throws Exception {
		Random random = new Random();
		int index = random.nextInt(reqTypeList.size());
		
		logMap.put("reqType", reqTypeList.get(index).toString());
	}
	
	// 시스템 Seq 정보
	private void setSystemSeqMap(Map<String, Object> logMap) throws Exception {
		Random random = new Random();
		int index = random.nextInt(systemSeqList.size());
		
		logMap.put("systemSeq", systemSeqList.get(index).toString());
	}
	
	// 상세로그 정보
	private void setAbContentsMap(Map<String, Object> logMap, int minCnt, int maxCnt) throws Exception {
		Random random = new Random();
		int detailCnt = random.nextInt(1 + (maxCnt - minCnt)) + minCnt;
		List<Map<String, Object>> abContents = new ArrayList<>();
		
		for (int i = 0; i < detailCnt; i++) {
			Map<String, Object> abContent = new HashMap<>();
			
			int index = random.nextInt(abTypeList.size());
			String content = String.format("3%08d" , Math.abs(random.nextInt())%10000);
			
			abContent.put("abType", Integer.parseInt(abTypeList.get(index)));
			abContent.put("content", content.toString());
			
			abContents.add(abContent);
		}
		logMap.put("abContents", abContents);
	}
	
	// 시간정보
	private void setLogTimeMap(Map<String, Object> logMap, String date, List<String> timeSet) throws Exception {
		Random random = new Random();
		int index = random.nextInt(timeSet.size());
		
		logMap.put("procDate", date);
		logMap.put("logTime", String.format("%s %s", date, timeSet.get(index)));
	}
	
	// 다운로드 파일이름 정보
	private void setDownfileNameMap(Map<String, Object> logMap) {
		Random random = new Random();
		int index = random.nextInt(downfileNameList.size());
		
		logMap.put("fileName", downfileNameList.get(index));
	}
	
	// 다운로드 이유 정보
	private void setDownReasonTypeMap(Map<String, Object> logMap) {
		Random random = new Random();
		int index = random.nextInt(downReasonTypeList.size());
		
		logMap.put("reasonCode", downReasonTypeList.get(index).split(":")[0]);
		logMap.put("downloadReason", downReasonTypeList.get(index).split(":")[1]);
	}
	
	
	// --------------
	// 시간설정 세트리스트
	private List<String> getTimeArrSet(int divSize) {
		
		Random random = new Random();
		List<String> timeSetList = new ArrayList<>();
		int totalPer = 0;
		
		for (String hourPer : hourList) {
			int eachPer = Integer.parseInt(hourPer.split(":")[1]);
			totalPer += eachPer;
		}
		int eachDivSize = totalPer / divSize;
		
		for (String hourPer : hourList) {
			String hour = hourPer.split(":")[0];
			int eachCnt = Integer.parseInt(hourPer.split(":")[1]);
			
			if(eachCnt > 0) {
				int cnt = Math.floorDiv(eachCnt, eachDivSize);
				for (int i = 0; i < cnt; i++) {
					int mnum = random.nextInt(60);
					int snum = random.nextInt(60);
					String mm = (mnum < 10) ? "0"+mnum : ""+mnum;
					String ss = (snum < 10) ? "0"+snum : ""+snum;
					
					timeSetList.add( String.format("%s:%s:%s.000", hour, mm, ss) );
				}
			}
		}
		
		return timeSetList;
	}
}
