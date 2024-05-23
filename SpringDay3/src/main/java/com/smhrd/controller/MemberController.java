package com.smhrd.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.smhrd.entity.Member;
import com.smhrd.mapper.MemberMapper;

@Controller
public class MemberController {

	@Autowired
	private MemberMapper memberMapper;
	
	// 이미지 업로드 기능
	@PostMapping("/imageUpdate.do")
	public String imageUpdate(HttpServletRequest request, RedirectAttributes rttr, HttpSession session) {
		//파일업로드 필요하 API => cos.jar가 있어야->MultipartRequest객체
		MultipartRequest multi = null;
		
		//MultipartRequest객체 생성하기위해 매개변수 필요
		/*1. request 객체
		  2. 이미지를 저장할 폴더의 경로(String)
		  3. 허용가능한 크기(int)
		  4. 파일이름에 대한 인코딩(String), UTF-8
		  5. 파일명 중복 제거 FileRenamePolicy */
		String savePath = request.getSession().getServletContext().getRealPath("resources/upload");
		int maxSize=1024*1024*100;
		String encoding = "UTF-8";
		DefaultFileRenamePolicy rename=new DefaultFileRenamePolicy();
		
		// 기존 가지고 있는 로그인한 사용자의 프로필 이미지를 삭제
		String memId=((Member)session.getAttribute("mem")).getMemId();
		String oldImg = memberMapper.getMember(memId).getMemProfile();
		File oldFile = new File(savePath + "/"+oldImg);
		
		// 파일이 존재 할 때만 삭제
		if(oldFile.exists()) {
			oldFile.delete();
		}
		try {
			multi = new MultipartRequest(request, savePath, maxSize, encoding, rename);
			
		} catch (IOException e) {
			// 파일저장 경로를 못찾았을 때 예외처리
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "경로를 찾을 수 없습니다.");
			return "redirect:/imageForm.do";

		}
		
		// img 파일인지 판별하기(jpg, png, jpeg, gif)
		// 내가 보낸 파일 가져오기
		File file = multi.getFile("memProfile"); //jsp에서 넘어온 name 값이다.
		
		if(file != null) {
			// 확장자만 잘라서 판별
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
			//대문자로 바꿔주기
			ext=ext.toUpperCase();
			
			boolean extResult =ext.equals("JPG") || ext.equals("JPEG") ||ext.equals("PNG") ||ext.equals("GIF");
			
			if(!extResult) {
				// 이미지 파일이 아닌경우
				if(file.exists()) {
					file.delete();
					rttr.addFlashAttribute("msgType", "실패 메세지");
					rttr.addFlashAttribute("msg", "이미지 파일만 가능합니다.");
					return "redirect:/imageForm.do";
				}
			}
			
		}
		
		// 이미지 파일만 저장
		// 서버에서 받은 사진을 저장
		String newProfile=multi.getFilesystemName("memProfile");
		//mapper에 넣기 위해 memId, newProfile -> member 객체 묶어서 DB에 넣주기
		
		
		return "redirect:/";
	}
	
	@GetMapping("imageForm.do")
	private String imageForm() { // imageForm.jsp로 감
		return "member/imageForm";
	}
	
	//로그인 기능 /login.do
	@PostMapping("login.do")
	public String login(Member mem, RedirectAttributes rttr, HttpSession session) {
		
		Member loginMem = memberMapper.loging(mem); // mem=>memId, memPassword
		
		if(loginMem == null) {
			//로그인 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "아이디와 비밀번호를 다시 입력해주세요.");			
			return "redirect:/loginForm.do";
		}else {
			//로그인 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "로그인 되었습니다.");
			
			session.setAttribute("mem", loginMem); //"mem" <= 헤더가 mem으로 인식해서
			return "redirect:/";			
		}
	}
	
	//로그인 페이지로 이동 / loginForm.do
	@GetMapping("loginForm.do")
	public String loginForm() {
		
		return "member/loginForm";
	}
	
	// 회원가입 페이지로 이동 /joinForm.do
	@GetMapping("/joinForm.do")
	public String joinForm() {
		return "member/joinForm";
	}
	
	// 아이디 중복체크 기능 /idCheck.do
	@GetMapping("/idCheck.do")
	public @ResponseBody int idCheck(@RequestParam("memId") String memId) {
		Member mem = memberMapper.getMember(memId);
		// mem = null -> 사용 가능한 아이디 -> 1
		// mem != null -> 사용 불가능한 아이디 -> 0
		
		if(mem != null) {
			return 0;
		}else {
			return 1;
		}
	}
	
	// 회원가입 기능
	@PostMapping("/join.do")
	public String join(Member mem, RedirectAttributes rttr, HttpSession session) { //id, pw, name, age ...
		
		/*RedirectAttributes
		Redirect 방식으로 페이지를 이동할 때 전달한 데이터가 있는데
		request에 담자니 데이터가 사라지고 session 담자니 뭔가 아까워..
		다음 페이지에 딱 한번만 데이터를 저장해서 뿌려주는 저장소(객체) */
		
		//System.out.println(mem.toString()); =>값이 잘 들어오나 확인용
		// null => name값이 틀린거(못받아옴), "" => 아무것도 안적은 것
		if(mem.getMemId() == null || mem.getMemId().equals("") || mem.getMemPassword() == null 
		    || mem.getMemPassword().equals("") || mem.getMemName() == null || mem.getMemName().equals("")
		    || mem.getMemAge() == 0 || mem.getMemEmail() == null || mem.getMemEmail().equals("")){
			// 회원가입 실패 시 다시 joinForm.jsp로 이동
		      
			rttr.addFlashAttribute("msgType", "실패 메세지"); //msg=> 메세지창 모달에 연결
			rttr.addFlashAttribute("msg", "모든 정보를 입력해주세요.");
			
		    return "redirect:/joinForm.do"; //  joinForm.jsp -> ${msgType},${msg}
		    }else { // 입력값이 null 이나 공백도 없다.
		    	
		    	//사진파일이 없기 때문에 null이 아닌 ""(빈문자로열)로 DB에 넣자
		    	mem.setMemProfile("");
		    	int cnt = memberMapper.join(mem);// <= mem에는 회원정보들이 들어있다.
		    	
		    	if(cnt == 1) {
		    		//회원가입 성공
		    		
		    		rttr.addFlashAttribute("msgType", "성공 메세지");
		    		rttr.addFlashAttribute("msg", "회원가입에 성공했습니다.");
		    		
		    		//회원가입 성공 시 로그인 처리까지 하기
		    		session.setAttribute("mem", mem);
		    		
		    		return "redirect:/";
		    		
		    	}else {
		    		//회원가입 실패 => varchar길이에 어긋났을 때 실패 시
		    		rttr.addFlashAttribute("msgType", "실패 메세지");
		    		rttr.addFlashAttribute("msg", "회원가입에 실패했습니다.");
		    		
		    		return "redirect:/joinForm.do"; 
		    	}
		    	
		    }
	}
	
	// 로그아웃 기능
	@GetMapping("/logout.do")
	public String logout(HttpSession session, RedirectAttributes rttr) {
		
		// 세션을 지우는 두가지 방법
		//session.removeAttribute("mem");
		session.invalidate();
		
		rttr.addFlashAttribute("msgType", "로그아웃 메세지");
		rttr.addFlashAttribute("msg", "정상적으로 로그아웃 되었습니다.");
		
		return "redirect:/";
	}

}
