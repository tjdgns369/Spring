package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.entity.Board;
import com.smhrd.mapper.BoardMapper;

// @RestController는 비동기 방식만 가능
// 데이터, 객체...들을 리턴한다.
// 페이지 이동이 불가능 하다.
//@RestController가 비동기라 =>@ResponseBody 안써도됨 
@RequestMapping("/board")
@RestController
public class BoardRestController {
		
	@Autowired // mapper객체를 주입	
	private BoardMapper mapper;
	
	// 게시글 전체보기
	@GetMapping("/all") // boardList.do -> board/all
	public List<Board> boardList(){
		System.out.println("[게시글 전체보기]");		
		List<Board> list = mapper.boardList();
		return list;
	}
	
	// 게시글 등록
	@PostMapping("/new")
	public void boardInsert(Board board) {
		mapper.boardInsert(board);
	}
	
	// 게시글 상세보기
	@GetMapping("/{idx}")
	public Board boardContent(@PathVariable("idx") int idx) {
		Board board = mapper.boardContent(idx);
		return board;
	}
	
	// 게시글 삭제
	@DeleteMapping("/{idx}")  // 상세보기와 주소가 겹친다.그래서 @DeleteMapping으로 구분해서 처리
	public void boardDelete(@PathVariable("idx") int idx){
		mapper.boardDelete(idx);
	}
	
	// 게시글 수정
	@PutMapping("/update")
	public void boardUpdate(@RequestBody Board board) {//@RequestBody JSON형식으로 값을 파라미터로 받을 때
		mapper.boardUpdate(board);
	}
	
	// 조회수 올리기
	@PutMapping("/count/{idx}")
	public void boardCount(@PathVariable("idx") int idx) {
		mapper.boardCount(idx);
	}

}
