<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<body>
    <c:if test="${nickname eq null}">
        
<a href="https://kauth.kakao.com/oauth/authorize?client_id=a29d2fec9ef352a27f4dbc16d53d66b0&redirect_uri=http://localhost:1010/board/login&response_type=code">
            <img src="https://developers.kakao.com/assets/img/about/logos/kakaologin/kr/kakao_account_login_btn_medium_narrow.png">
        </a>
    </c:if>
  
</body>



        
        