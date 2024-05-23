package com.smhrd.controller;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smhrd.entity.Board;
import com.smhrd.mapper.BoardMapper;

@Controller   // 해당 클래스가 Controller의 역할을 수행한다 => url
public class BoardController {
	// 게시판 관련 기능 수행 (CRUD)
	
	@Autowired // mapper객체를 주입	
	private BoardMapper mapper;
	
	// 메인실행
	@RequestMapping("/")
	public String home() {
		return "redirect:/boardList.do";
	}
	
	// 게시판 목록
	@RequestMapping("boardList.do")   // 요청했을 때 해당 기능을 연결해주는것
	public String boardList(Model model) {
				
		List<Board> list = mapper.boardList();
		
		model.addAttribute("list",list); //model에다가 잠깐 값을 담아서 간다.
		
		return "boardList"; // view name,  WEB-INF/views/boardList.jsp => view resolver가 해준다.
	}
	
	// 게시판 상세보기  @RequestParam("idx")=> 생략가능
	@GetMapping("boardContent.do")
	public String boardContent(@RequestParam("idx") int idx, Model model) {
		// 조회수
		mapper.boardCount(idx);
		Board vo = mapper.boardContent(idx);
		model.addAttribute("vo",vo);
		// System.out.println(vo.toString()); => 값이 잘 있난 확인
		return "boardContent";			
	}
	
	// 게시글 작성 페이지 이동
	@GetMapping("boardForm.do")
	public String boardForm() {
		return "boardForm";
	}
	
	// 게시글 작성
	@PostMapping("boardInsert.do")
	public String boardInsert(Board vo, RedirectAttributes rttr) {
		mapper.boardInsert(vo);
		rttr.addFlashAttribute("result","ok"); //result라는 값으로 ok값이 있으면 모달을 띄운다.
		return "redirect:/boardList.do"; // 값없이 해당페이지로 보내려면 redirect로 보낸다.
	}
	
	// 게시글 삭제
	@GetMapping("boardDelete.do")
	public String boardDelete(@RequestParam("idx") int idx) { //String => return이 이동해야할 페이지
		mapper.boardDelete(idx);
		return "redirect:/boardList.do";
	}
	
	// 게시글 수정화면 이동
	@GetMapping("boardUdateForm.do")
	public String boardUdateForm(@RequestParam("idx") int idx, Model model) {
		Board vo = mapper.boardContent(idx);
		model.addAttribute("vo", vo);
		return "boardUdateForm";
	}
	
	// 게시글 수정
	@PostMapping("boardUpdate.do")
	public String boardUpdate(Board vo) {
		mapper.boardUpdate(vo);
		return "redirect:/boardContent.do?idx="+vo.getIdx();
	}

}
