package parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringPasing {
	
	private static Map<String, Integer> browserMap = new HashMap<>();
	private static Map<String, Integer> apiKeyMap = new HashMap<>();
	private static Map<String, Integer> apiServiceIdMap = new HashMap<>();
	private static int reqCount = 0;

	// 한줄씩 처리
	public static void readLine(String nextLine) {

		// line 가공 후 문자열 배열로 저장
		String[] str = nextLine.replace("[", "").split("]");
		
		// 정상요청 여부 확인
		if(str[0].contains("200") && str[1].startsWith("http://apis.daum.net/search/")) {
			// 브라우저별 count set
			countBrowser(str[2]);
			
			// API KEY 별 count set
			countApiKey(str[1].substring(str[1].indexOf("=") + 1, str[1].indexOf("&")));
			
			// API SERVICE ID 별 count set
			countApiServiceId(str[1].substring(0, str[1].indexOf("?")).replace("http://apis.daum.net/search/", ""));

			/// 요청 count +1
			reqCount++;
		}
		
	}
	
	// 브라우저별 count set
	private static void countBrowser(String browser) {
		
		browserMap.put(browser, browserMap.get(browser) != null ? browserMap.get(browser) + 1 : 1);
	}

	// API KEY 별 count set
	private static void countApiKey(String apiKey) {
		
		apiKeyMap.put(apiKey, apiKeyMap.get(apiKey) != null ? apiKeyMap.get(apiKey) + 1 : 1);
	}
	
	// API SERVICE ID 별 count set
	private static void countApiServiceId(String apiServiceId) {

		apiServiceIdMap.put(apiServiceId, apiServiceIdMap.get(apiServiceId) != null ? apiServiceIdMap.get(apiServiceId) + 1 : 1);
	}

	// 최종 결과 return
	public static Map<String, Map<String, String>> resultMap() {
		
		Map<String, Map<String, String>> returnMap = new HashMap<>();
		
		returnMap.put("resultBrowserMap", sort(browserMap, "ratio"));
		returnMap.put("resultApiKeyMap", sort(apiKeyMap, "value"));
		returnMap.put("resultApiServiceIdMap", sort(apiServiceIdMap, "value"));
		
		return returnMap;
	}

	// 내림차순 정렬
	private static Map<String, String> sort(Map<String, Integer> map, String type) {

		// 내림차순 정렬
		List<String> keyList = new ArrayList<>(map.keySet());
		
        Collections.sort(keyList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return map.get(o2).compareTo(map.get(o1));
            }
        });
        
        // 정렬 결과 return
        Map<String, String> returnMap = new LinkedHashMap<>();
        
        for(String key : keyList) {
        	// 비율 정렬 & 값 정렬
        	if(type.equals("ratio")) {
        		returnMap.put(key, String.format("%.2f", (double) (Integer) map.get(key) / (double) reqCount * 100)+"%");
        	} else if(type.equals("value")) {
        		returnMap.put(key, map.get(key).toString());
        	}
        }
		
		return returnMap;
	}

}
