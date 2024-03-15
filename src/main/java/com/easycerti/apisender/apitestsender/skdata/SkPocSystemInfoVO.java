package com.easycerti.apisender.apitestsender.skdata;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkPocSystemInfoVO {

	private String tableName;
	private Map<String, List<String>> tableColumns;
	
}
