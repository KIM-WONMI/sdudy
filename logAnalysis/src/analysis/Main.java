package analysis;

import java.io.IOException;

import file.FileMng;

public class Main {

	public static void main(String[] args) throws IOException {

		// 파일 읽기
		FileMng.fileInput("D:\\test\\input.log");
		
		// 파일 출력
		FileMng.fileOuput("D:\\test\\result.txt");
	}

}
