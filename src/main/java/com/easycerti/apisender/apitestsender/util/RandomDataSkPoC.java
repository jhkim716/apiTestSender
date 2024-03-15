package com.easycerti.apisender.apitestsender.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easycerti.apisender.apitestsender.skdata.SkPocSystemInfo;
import com.easycerti.apisender.apitestsender.skdata.SkPocSystemInfoVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RandomDataSkPoC {

	@Value("${env.targetDate}")
	private String targetDate;

	@Value("${random-set-skpoc.usersInfo}")			// 유저정보
	private List<String> userInfoList;
	
	@Value("${random-set-skpoc.hoursPer}")			// 시간정보
	private List<String> hourList;
	
	@Autowired
	private SkPocSystemInfo systemInfo;				// 시스템정보
	
	
	// 랜덤 수집로그 가져오기
	public String getRandomCollectLogListStr(Integer listSize) {
		
		// --- 설정 준비
		String data = "";
		List<Map<String, Object>> collectLogMapList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		
		String dateString = targetDate;
		if( dateString == null || "".equals(dateString) ) {
			dateString = LocalDate.now().toString();
		}
		List<String> timeListSet = getTimeArrSet(100);
		// --- 설정 준비
		
		// Network DLP format
		for (int i = 0; i < listSize; i++) {
			try {
				Map<String, Object> collectLogMap = new HashMap<>();
				
				setUserInfoMap(collectLogMap);				// 유저 정보
				collectLogMap.put("reqUrl", "");			// Url 정보
				collectLogMap.put("reqType", "RD");			// 요청 타입정보
				setNetwork_DLP_format(collectLogMap, systemInfo.getNetworkDLPFormat());		// Network DLP format 시스템 상세로그 리스트 정보
				setLogTimeMap(collectLogMap, dateString, timeListSet);	// 일시정보
				
				collectLogMapList.add(collectLogMap);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// EndPoint DLP format
		for (int i = 0; i < listSize; i++) {
			try {
				Map<String, Object> collectLogMap = new HashMap<>();
				
				setUserInfoMap(collectLogMap);				// 유저 정보
				collectLogMap.put("reqUrl", "");			// Url 정보
				collectLogMap.put("reqType", "RD");			// 요청 타입정보
				setEndPoint_DLP_format(collectLogMap, systemInfo.getEndPointDLPFormat());		// EndPoint DLP format 시스템 상세로그 리스트 정보
				setLogTimeMap(collectLogMap, dateString, timeListSet);	// 일시정보
				
				collectLogMapList.add(collectLogMap);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Privacy I format
		for (int i = 0; i < listSize; i++) {
			try {
				Map<String, Object> collectLogMap = new HashMap<>();
				
				setUserInfoMap(collectLogMap);				// 유저 정보
				collectLogMap.put("reqUrl", "");			// Url 정보
				collectLogMap.put("reqType", "RD");			// 요청 타입정보
				setPrivacy_I_format(collectLogMap, systemInfo.getPrivacyIFormat());		// Privacy I format 시스템 상세로그 리스트 정보
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
	
	// Network DLP format 시스템 상세로그 리스트 정보
	private void setNetwork_DLP_format(Map<String, Object> logMap, SkPocSystemInfoVO skPocSystemInfoVO) {
		Random random = new Random();
		int index = 0;
		Map<String, List<String>> colMap = skPocSystemInfoVO.getTableColumns();
		List<Map<String, Object>> abContents = new ArrayList<>();
		Map<String, Object> abContent =null;
		
		// type
		String type = "";
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("type").size() );
			type = colMap.get("type").get(index);
			abContent.put("abType", 0);
			abContent.put("content", "type:::::" + type);
			abContents.add(abContent);
		
		// message status
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("message_status").size() );
			abContent.put("abType", 0);
			abContent.put("content", "message status:::::" + colMap.get("message_status").get(index));
			abContents.add(abContent);
		
		// Policy
		if("HTTP".equals(type)) {
			abContent = new HashMap<>();
			index = random.nextInt( colMap.get("Policy-type1").size() );
				abContent.put("abType", 0);
				abContent.put("content", "Policy:::::" + colMap.get("Policy-type1").get(index));
				abContents.add(abContent);
		} else {
			abContent = new HashMap<>();
			index = random.nextInt( colMap.get("Policy-type2").size() );
				abContent.put("abType", 0);
				abContent.put("content", "Policy:::::" + colMap.get("Policy-type2").get(index));
				abContents.add(abContent);
		}
		
		// Recipient
		if("HTTP".equals(type)) {
			index = random.nextInt( colMap.get("Recipient-type1").size() );
				abContent = new HashMap<>();
				abContent.put("abType", 0);
				abContent.put("content", "Recipient:::::" + colMap.get("Recipient-type1").get(index));
				abContents.add(abContent);
		} else {
			index = random.nextInt( colMap.get("Recipient-type2").size() );
			String[] contents = colMap.get("Recipient-type2").get(index).split(",");
			for (String content : contents) {
				abContent = new HashMap<>();
				abContent.put("abType", 0);
				abContent.put("content", "Recipient:::::" + content.trim());
				abContents.add(abContent);
			}
		}
		
		logMap.put("systemSeq", skPocSystemInfoVO.getTableName());
		logMap.put("abContents", abContents);
	}
	
	// EndPoint DLP format 시스템 상세로그 리스트 정보
	private void setEndPoint_DLP_format(Map<String, Object> logMap, SkPocSystemInfoVO skPocSystemInfoVO) {
		Random random = new Random();
		int index = 0;
		Map<String, List<String>> colMap = skPocSystemInfoVO.getTableColumns();
		List<Map<String, Object>> abContents = new ArrayList<>();
		Map<String, Object> abContent =null;
		
		// types
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("types").size() );
			abContent.put("abType", 0);
			abContent.put("content", "types:::::" + colMap.get("types").get(index));
			abContents.add(abContent);
		
		// Machine Name
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("Machine_Name").size() );
			abContent.put("abType", 0);
			abContent.put("content", "Machine Name:::::" + colMap.get("Machine_Name").get(index));
			abContents.add(abContent);
		
		// Policy
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("Policy").size() );
			abContent.put("abType", 0);
			abContent.put("content", "Policy:::::" + colMap.get("Policy").get(index));
			abContents.add(abContent);
		
		// File Name
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("File_Name").size() );
			abContent.put("abType", 0);
			abContent.put("content", "File Name:::::" + colMap.get("File_Name").get(index));
			abContents.add(abContent);
		
		// File Size
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("File_Size").size() );
			abContent.put("abType", 0);
			abContent.put("content", "File Size:::::" + colMap.get("File_Size").get(index));
			abContents.add(abContent);
		
		logMap.put("systemSeq", skPocSystemInfoVO.getTableName());
		logMap.put("abContents", abContents);
	}
	
	// Privacy I format 시스템 상세로그 리스트 정보
	private void setPrivacy_I_format(Map<String, Object> logMap, SkPocSystemInfoVO skPocSystemInfoVO) {
		
		Random random = new Random();
		int index = 0;
		Map<String, List<String>> colMap = skPocSystemInfoVO.getTableColumns();
		List<Map<String, Object>> abContents = new ArrayList<>();
		Map<String, Object> abContent =null;
		
		// dfile_filename
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("dfile_filename").size() );
			abContent.put("abType", 0);
			abContent.put("content", "dfile_filename:::::" + colMap.get("dfile_filename").get(index));
			abContents.add(abContent);
		
		// dfile_computername
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("dfile_computername").size() );
			abContent.put("abType", 0);
			abContent.put("content", "dfile_computername:::::" + colMap.get("dfile_computername").get(index));
			abContents.add(abContent);
		
		// dfile_filesize
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("dfile_filesize").size() );
			abContent.put("abType", 0);
			abContent.put("content", "dfile_filesize:::::" + colMap.get("dfile_filesize").get(index));
			abContents.add(abContent);
		
		// dfile_agentip
		abContent = new HashMap<>();
		index = random.nextInt( colMap.get("dfile_agentip").size() );
			abContent.put("abType", 0);
			abContent.put("content", "dfile_agentip:::::" + colMap.get("dfile_agentip").get(index));
			abContents.add(abContent);
		
		logMap.put("systemSeq", skPocSystemInfoVO.getTableName());
		logMap.put("abContents", abContents);
	}
	
	
	// 시간정보
	private void setLogTimeMap(Map<String, Object> logMap, String date, List<String> timeSet) throws Exception {
		Random random = new Random();
		int index = random.nextInt(timeSet.size());
		
		logMap.put("procDate", date);
		logMap.put("logTime", String.format("%s %s", date, timeSet.get(index)));
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
