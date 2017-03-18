package com.jsontotable.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jsontotable.model.Dependency;
import com.jsontotable.model.HealthReport;

public class JsonService {
	private static final String overallHealth = "overallHealth";
	private static final String serviceName = "serviceName";
	private static final String dependencies = "dependencies";
	private static final String depServiceName = "serviceName";
	private static final String serviceHealth = "healthy";
	private static final String serviceUrl = "endpoint";
	private static final String serviceLatency = "latency";
	private static final String serviceDetails = "details";
	
	public HealthReport getHealthReport(String inputString){
		HealthReport report = new HealthReport();
		if(null == inputString || inputString.isEmpty()) return report;
	    JSONParser parser = new JSONParser();
	    JSONObject inputJson = null;
	    try {
			inputJson = (JSONObject) parser.parse(inputString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    if(null == inputJson || inputJson.isEmpty()) return report;
	    Object obj = inputJson.containsKey(overallHealth)? inputJson.get(overallHealth) : null;
		if(null != obj && obj.getClass().equals(String.class)){
			report.setOverallHealth((String)obj);
		}
		obj = inputJson.containsKey(serviceName)? inputJson.get(serviceName) : null;
		if(null != obj && obj.getClass().equals(String.class)){
			report.setServiceName((String)obj);
		}
		obj = inputJson.containsKey(serviceName)? inputJson.get(dependencies) : null;
		List<Dependency> depList = new ArrayList<>();
		if(null != obj && obj.getClass().equals(JSONArray.class)){
			getDependencyList((JSONArray)obj, depList);
		}
		report.setDependencies(depList);
		return report;
	}

	private void getDependencyList(JSONArray jsonArray, List<Dependency> depList) {
		if(jsonArray == null || jsonArray.size() == 0) return;
		for(int i = 0; i < jsonArray.size(); ++i){
			JSONObject dependencyJson = (JSONObject)jsonArray.get(i);
			if(dependencyJson.isEmpty()) continue;
			Dependency tempDependency = new Dependency();
			
			tempDependency.setStatus((String)dependencyJson.get(serviceHealth));
			tempDependency.setUrl((String)dependencyJson.get(serviceUrl));
			tempDependency.setLatency(dependencyJson.get(serviceLatency).toString());
			tempDependency.setDetails((String)dependencyJson.get(serviceDetails));
			tempDependency.setServiceName((String)dependencyJson.get(depServiceName));
			
			depList.add(tempDependency);
		}
	}
}
