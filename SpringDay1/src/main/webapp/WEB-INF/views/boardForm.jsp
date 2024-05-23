<%@page import="com.smhrd.entity.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
	  <h2>Spring Day1</h2>
	  <div class="panel panel-primary">
	    <div class="panel-heading">Board</div>
	    <div class="panel-body">
	    
	    	<form action="boardInsert.do" method="post">
			<table class="table table-hover">

				<tr>
					<td>제목</td>
					<td><input required="required" class="form-control" type="text" name="title"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea required="required" class="form-control" name="content" rows="7" cols="" style="resize: none;"></textarea></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input required="required"  class="form-control" type="text" name="writer"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a class="btn btn-info btn-sm" href="boardList.do">돌아가기</a>
						<button type="reset" class="btn btn-success btn-sm">취소</button>
						<button type="submit" class="btn btn-warning btn-sm">등록</button>
					</td>
				</tr>
			</table>
			</form>	    	    
	    </div>
	    <div class="panel-footer">스프링 - 강성훈</div>
	  </div>
	</div>

<!-- 	
		// Model 영역안에 있는 list 꺼내기 
		// ㄴ=> request영역안에 model이 있기 때문에 request.getAttribute 한다.
		List<Board> list =(List<Board>)request.getAttribute("list");
-->

	
</body>
</html>