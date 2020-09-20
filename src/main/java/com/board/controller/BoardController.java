package com.board.controller;
 
import java.util.HashMap;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.KakaoAPI;
import com.board.domain.BoardVO;
import com.board.domain.userinfo;
import com.board.mapper.BoardMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

 
@Controller
@RequestMapping("/board")
public class BoardController {
 
    @Autowired
    private BoardMapper boardMapper;
    
    
    //게시글 목록
    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView list() throws Exception{
        
        List<BoardVO> list = boardMapper.boardList();
        
        return new ModelAndView("boardList","list",list);
    }
    
    //게시글 작성 페이지(GET)    
    @RequestMapping(value="/post",method=RequestMethod.GET)
    public ModelAndView writeForm() throws Exception{
        
        return new ModelAndView("boardWrite");
    }
    
    //게시글 작성(POST)
    @RequestMapping(value="/post",method=RequestMethod.POST)
    public String write(@ModelAttribute("BoardVO") BoardVO board) throws Exception{
 
        boardMapper.boardInsert(board);
        
        return "redirect://localhost:1010/board";
    }
    
  //게시글 상세
    @RequestMapping(value="/{bno}",method=RequestMethod.GET)
    public ModelAndView view(@PathVariable("bno") int bno) throws Exception{
        
        BoardVO board = boardMapper.boardView(bno);
        boardMapper.hitPlus(bno);
        
        return new ModelAndView("boardView","board",board);
        
        
    }
    
  //게시글 수정 페이지(GET)
    @RequestMapping(value="/post/{bno}", method=RequestMethod.GET)
    public ModelAndView updateForm(@PathVariable("bno") int bno) throws Exception{
            
        BoardVO board = boardMapper.boardView(bno);
        
        return new ModelAndView("boardUpdate","board",board);
    }
        
    //게시글 수정(PATCH)
    @RequestMapping(value="/post/{bno}", method=RequestMethod.PATCH)
    public String update(@ModelAttribute("BoardVO")BoardVO board,@PathVariable("bno") int bno) throws Exception{
            
        boardMapper.boardUpdate(board);
            
        return "redirect://localhost:1010/board/"+bno;
    }
    //게시글 삭제(DELETE)
    @RequestMapping(value="/post/{bno}", method=RequestMethod.DELETE)
    public String delete(@PathVariable("bno") int bno) throws Exception{
            
        boardMapper.boardDelete(bno);
            
        return "redirect://localhost:1010/board";
    }
    
   
    
    @Autowired
    private KakaoAPI kakao;
    
    @RequestMapping(value="/index")
    public String index() {
        
        return "index";
    }
    
    @RequestMapping(value="/login")
    public String login(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        session.setAttribute("access_Token", access_Token);
        session.setAttribute("nickname", userInfo.get("nickname"));
        session.setAttribute("id", userInfo.get("email"));


        return "redirect://localhost:1010/board";
    }
    
    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        kakao.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("nickname");
        return "redirect://localhost:1010/board";
    }
    
    
    @RestController
    public class UserController {
    	
    	@RequestMapping(method = RequestMethod.POST, path = "/userinfo")
        public HashMap<String, Object> postRequest(@RequestBody userinfo user) throws Exception{
    		List<BoardVO> list = boardMapper.boardUserList(user);
            HashMap<String, Object> map = new HashMap<>();
            map.put("Board", list);
            
            return map;
        }

    @RestController
    public class test
    {
      @RequestMapping(value="/userinfo/{writer}", method= RequestMethod.GET)      
      @ResponseStatus(value = HttpStatus.OK)
      public HashMap<String, Object> user(@PathVariable("writer") String writer) throws Exception {
		List<BoardVO> list = boardMapper.boardUserList(writer);
          HashMap<String, Object> map = new HashMap<>();
        
          map.put("board", list);
          
          return map;
       
      }
    }

    }
}
 


