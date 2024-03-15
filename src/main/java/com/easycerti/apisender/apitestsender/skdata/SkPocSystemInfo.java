package com.easycerti.apisender.apitestsender.skdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "random-set-skpoc.system-info")
public class SkPocSystemInfo {

	private List<SkPocSystemInfoVO> list;
	
	public SkPocSystemInfoVO getNetworkDLPFormat(){
		for (SkPocSystemInfoVO system : list) {
			if("Network DLP format".equals(system.getTableName())) {
				return system;
			}
		}
		return new SkPocSystemInfoVO();
	}
	
	public SkPocSystemInfoVO getEndPointDLPFormat(){
		for (SkPocSystemInfoVO system : list) {
			if("EndPoint DLP format".equals(system.getTableName())) {
				return system;
			}
		}
		return new SkPocSystemInfoVO();
	}
	
	public SkPocSystemInfoVO getPrivacyIFormat(){
		for (SkPocSystemInfoVO system : list) {
			if("Privacy I format".equals(system.getTableName())) {
				return system;
			}
		}
		return new SkPocSystemInfoVO();
	}
	
}
