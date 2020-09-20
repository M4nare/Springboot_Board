<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content= "text/html; charset=UTF-8">
<!-- BootStrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<title>게시글 목록</title>
<script type="text/javascript">
<%String id = (String)session.getAttribute("id");%>

	function userservice(){
		//alert("유저서비스작동 ");
		$.ajax({
			type:"POST",
			url:"http://127.0.0.1:9999/user/user",
			dataType : "json",
			contentType: "application/json",
			data:JSON.stringify({'id' : '<%=id%>'}),
			success : function(data){
				$.each(data,function(key,value) {
					var arrNumber = new Array();
					

					alert('[카카오 이메일 : '+value[0].id+', 상태매세지 : '+value[0].msg +']');

				});
			},
			error : function(request,status,error){
      		  alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
      		}
			
		});
		
		}
	</script>
</head>
<body>
    <h3>게시글 목록</h3>
<% if(session.getAttribute("nickname")== null){%>
    
    <button class="btn btn-primary" style="float : right;" onclick="location.href='/board/index'">로그인</button>
     <%}else{ %>
     
	 <input type =button value="Nickname : ${nickname}"onclick='userservice()'>
        <button class="btn btn-primary" style="float : right;" onclick="location.href='/board/logout'">
        로그아웃</button>
        <button class="btn btn-primary" style="float : right;" onclick="location.href='/board/post'">글 작성</button>
        
    
     <%} %>
    
    <table class="table">
        <tr>
            
            <th>제목</th>
            <th>작성자</th>
            <th>작성날짜</th>
            <th>조회수</th>
        </tr>
        <c:forEach var="board" items="${list}">
        <tr>
           
            <td><a href="/board/${board.bno}">${board.subject}</a></td>
            <td>${board.writer}</td>
            <td><fmt:formatDate value="${board.reg_date}" pattern="MM / dd" /></td>
            <td>${board.hit}</td>

        </tr>
        </c:forEach>
    </table>
</body>
</html>



