<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

out.clear();

String action = request.getParameter("action");

if(action!=null){
	if(action.equals("send_pass")){
		out.print("{\"status\":1}");
	}
	
}else{
	out.print("请指定action");
}

%>