package com.jsontotable.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsontotable.model.HealthReport;
import com.jsontotable.service.JsonService;

public class ControllerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsonService jsonService;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("doGet Path: " + request.getServletPath());
		String path = request.getServletPath();
		String targetPath = "index";
		if(path.equals("/detailTable")){
			targetPath = "detailTable";
		}
		targetPath += ".jsp";
		request.getRequestDispatcher(targetPath).forward(request, response);;
		return;
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    													throws ServletException, IOException {
		
		System.out.println("doPost Path: " + request.getServletPath());
		
			     
		String path = request.getServletPath();
		String targetPath = "error";
		if(path.equals("/details")){
			targetPath = "detailTable";
		}
		String inputString = request.getParameter("inputString");
		StringBuffer bf = new StringBuffer();

	    if(null == inputString){
			String line = null;
		    try {
		        BufferedReader reader = request.getReader();
		        while ((line = reader.readLine()) != null)
		        	bf.append(line);
		    } catch (Exception e) { 
		        e.printStackTrace();        
		    }
	    }
	    
	    if((null == inputString || inputString.isEmpty())){
	    	if(bf.length() == 0){
	    		response.sendRedirect("error.jsp");
	    		return;
	    	}
	    	inputString = bf.toString();
	    }
	    	
	    jsonService = new JsonService();
	    HealthReport report = jsonService.getHealthReport(inputString);
	 	if(null == report.getServiceName() || report.getServiceName().isEmpty()){
	 		response.sendRedirect("error.jsp");
			return;
	 	}
	 	request.setAttribute("healthReport", report);
	 	request.getRequestDispatcher("/detailTable.jsp").forward(request, response);
	 	return;
	   	    
    }
	
}
