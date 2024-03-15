package com.easycerti.apisender.apitestsender;

import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.easycerti.apisender.apitestsender.util.RandomData;
import com.easycerti.apisender.apitestsender.util.RandomDataSkPoC;

import lombok.extern.slf4j.Slf4j;

@Profile("!test")
@Slf4j
@Component
public class MainApp implements CommandLineRunner {

	@Value("${app.api-url:http://127.0.0.1:9081/ipm-api}")
	private String apiUrl;
	
	@Value("${app.send-collect-logs:collect-logs/send}")
	private String sendCollectLog;
	
	@Value("${app.send-dn-logs:download-logs/send}")
	private String sendDownloadLog;
	
	@Value("${env.type}")
	private String type;
	
	@Value("${env.cnt}")
	private String cnt;
	
	@Autowired
	private RandomData randomData;
	
	@Autowired
	private RandomDataSkPoC randomSkPoC;
	
	@Override
	public void run(String... args) {
		
		String data = "";
		String secondUrl = "";
		
		try {
			// 설정
			int listNum = Integer.parseInt(cnt);
			if("collect".equals(type) ) {		// 접속기록로그 타입 설정
				data = randomData.getRandomCollectLogListStr(listNum < 1 ? 1 : listNum);	// 랜덤 접속기록로그 데이터 가져오기
				secondUrl = sendCollectLog;		// url
			}
			else if("download".equals(type) ) {	// 다운로드로그 타입 설정
				data = randomData.getRandomCollectLogDnListStr(listNum < 1 ? 1 : listNum);	// 랜덤 다운로드로그 데이터 가져오기
				secondUrl = sendDownloadLog;	// url
			}
			else if( type != null && "skpoc".equals(type.toLowerCase()) ) {	// skPoc용 로그 타입
				data = randomSkPoC.getRandomCollectLogListStr(listNum < 1 ? 1 : listNum);	// 랜덤 로그 데이터 가져오기
				secondUrl = sendCollectLog;	// url
			}
			else {
				throw new Exception("TYPE 이 매칭되지 않음. --TYPE=collect, --TYPE=download 중 하나를 입력. : type: " + type);
			}
			
			// api 호출동작 시작
			try {
				// -- 호출설정
				String url = apiUrl + "/" + secondUrl;		// API URL
				String requestBodyStr = data;				// RequestBody Data
				
				HttpClient client = HttpClientBuilder.create().build();
				
				HttpPost post = new HttpPost( url );
				post.setHeader("Accept", "application/json");
				post.setHeader("Content-Type", "application/json;charset=UTF-8");
				post.setEntity( new StringEntity(requestBodyStr, ContentType.create("application/json", "UTF-8")) );
				// -- 호출설정
				
				// Request
				HttpResponse response = client.execute(post); 
				
				// Response 출력
				ResponseHandler<String> handler = new BasicResponseHandler();
				String result = handler.handleResponse(response);
				log.info(result);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
