package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import parsing.StringPasing;

public class FileMng {

	// 파일 읽기
	public static void fileInput(String filePath) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new File(filePath));
		
		while (scanner.hasNextLine()) {		// 한줄씩 pasing
			StringPasing.readLine(scanner.nextLine());
		}
	}

	// 파일 쓰기
	public static void fileOuput(String filePath) throws IOException {
		
		// 파일 생성
		File file = new File(filePath);
		
		// 파일 존재 여부 확인 및 생성
		if(!file.exists()) {
			file.createNewFile();
		}
		
		// Writer start
		FileWriter fw = new FileWriter(file);
		PrintWriter writer = new PrintWriter(fw);
		
		// 분석결과
		Map<String, Map<String, String>> resultMap = StringPasing.resultMap();

		writer.println("최다호출 API KEY");
		resultPrint(writer, resultMap.get("resultApiKeyMap"), 1, "key");
		
		writer.println("상위 3개의 API SERVICE ID와 각각의 요청 수");
		resultPrint(writer, resultMap.get("resultApiServiceIdMap"), 3);
		
		writer.println("웹브라우저별 사용 비율");
		resultPrint(writer, resultMap.get("resultBrowserMap"));
		
		// Writer end
		writer.close();
	}

	// 분석결과 MAP data writer
	private static void resultPrint(PrintWriter writer, Map<String, String> resultMap) {

		resultPrint(writer, resultMap, resultMap.size());
	}

	// 분석결과 MAP data writer 
	private static void resultPrint(PrintWriter writer, Map<String, String> resultMap, int length) {

		resultPrint(writer, resultMap, length, "");
	}

	// 분석결과 MAP data writer 
	private static void resultPrint(PrintWriter writer, Map<String, String> resultMap, int length, String writerType) {
		int index = 0;
		// key 갯수만큼 반복
		for(String key : resultMap.keySet()) {
			// 입력된 출력갯수와 비교
			if(index < length) {
				// key & key-value 출력
				if(writerType.equals("key")) {
					writer.println(key);
				} else {
					writer.println(key + " : " + resultMap.get(key));
				}
			}
			index++;
		}
		writer.println();
	}
	
}
