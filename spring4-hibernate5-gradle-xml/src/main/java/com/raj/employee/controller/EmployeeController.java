package com.raj.employee.controller;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.raj.beans.EmployeeBean;
import com.raj.employee.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeSerivce;
	
	private static Logger logger = Logger.getLogger(EmployeeController.class);
	
	@GetMapping(value="/")
	public Callable<String> home(Model model){
		logger.info("home controller");
		try {
			model.addAttribute("employee", new EmployeeBean());
			model.addAttribute("employeeList", employeeSerivce.getAllEmployee());
		} catch(Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return () -> {
			return "employee";
		};
	}
	
	
	@PostMapping(value="/saveOrUpdateEmployee")
	public Callable<String> saveOrUpdateEmployee(@ModelAttribute("employee") EmployeeBean employee){
		logger.info("saveOrUpdateEmployee controller");
		try {
			String status = employeeSerivce.saveOrUpdateEmployee(employee);
			logger.info("Status of saveOrUpdate: "+status);
		} catch(Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return () -> {
			return "redirect:/";
		};
	}
	
	@GetMapping(value="/updateEmployee/{id}")
	public String getEmployeeById(@PathVariable("id")Integer id, Model model){
		logger.info("getEmployeeById in controller");
		String status = null;
		EmployeeBean bean = null;
		try {
			bean = employeeSerivce.getEmployeeById(id);
			if(null == bean){
				status = "redirect:/";
			}
			else{
				model.addAttribute("employee", bean);
				status = "employee";
			}
		} catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
		}
		return status;
	}
	
	@GetMapping(value="/deleteEmployee/{id}")
	public Callable<String> deleteEmployee(@PathVariable("id")Integer id){
		logger.info("deleteEmployee in controller");
		try {
			String status = employeeSerivce.deleteEmployee(id);
			logger.info("deleteEmployee Status: "+status);
		} catch (Exception e) {
			logger.error("Exception : "+e.getMessage());
		}
		return () -> {
			return "redirect:/";
		};
	}
}
