package com.smhrd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smhrd.entity.Board;

@Mapper
public interface BoardMapper {
	// Spring과 MyBatis를 연결하는 역할
	
	// 게시글 목록보기
	public List<Board> boardList(); // 추상메서드 그래서 Class말고 interface로 한다.
	
	public Board boardContent(int idx);

	public void boardInsert(Board vo);
	
	public void boardDelete(int idx);

	public void boardUpdate(Board vo);

	public void boardCount(int idx);
}
