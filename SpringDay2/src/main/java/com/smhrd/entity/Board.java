package com.smhrd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor   // spring에서는 반드시 있어야한다.
@ToString  // 데이터 확인
public class Board {
	
	// 번호, 제목, 내용, 작성자, 날짜, 조회수
	private int idx;
	private String title;
	private String content;
	private String writer;
	private String indate;
	private int count;
	
}
