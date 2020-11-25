package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AlienRepo;
import com.example.demo.model.Alien;

@RestController
public class AlienController 
{
	@Autowired
	AlienRepo repo;
	
	@RequestMapping("/")
	public String home() 
	{
		return "home.jsp";
	}
	
	/*
	//without using REST
	@RequestMapping("/alien")
	public String addAlien(Alien alien) 
	{
		repo.save(alien);
		return "home.jsp";
	} */
	
	/*
	//without using REST
	@RequestMapping("/getAlien")
	public ModelAndView getAlien(@RequestParam int aid) 
	{
		ModelAndView mv = new ModelAndView("showAlien.jsp");
		Alien alien = repo.findById(aid).orElse(new Alien());
		
		System.out.println(repo.findByTech("Java"));
		System.out.println(repo.findByAidGreaterThan(101));
		System.out.println(repo.findByTechSorted("Java"));
		
		mv.addObject(alien);	
		return mv;
	}  */
	
	//http://localhost:8080/aliens --> REST URL
	//using REST 
	//only xml format - restricted
	@RequestMapping(path="/aliens")//,produces= {"application/xml"})
	@ResponseBody
	public List<Alien> getAliens() 
	{	
		return repo.findAll();
	}
	
	
	//using REST 
	//JSON format - JPA repository
	@RequestMapping("/alien/{aid}")
	@ResponseBody
	public Optional<Alien> getAlien(@PathVariable("aid") int aid) 
	{	
		return repo.findById(aid);
	}
	
	
	@PostMapping(path="/alien", consumes= {"application/json"})
	public Alien addAlien(@RequestBody Alien alien) 
	{
		repo.save(alien);
		return alien;
	}
	
	
	@DeleteMapping("/alien/{aid}")
	public String deleteAlien(@PathVariable int aid) {
		Alien a = repo.getOne(aid);
		repo.delete(a);
		return "Deleted";
		
	}
	
	
	@PutMapping(path="/alien", consumes= {"application/json"})
	public Alien saveOrUpdateAlien(@RequestBody Alien alien) 
	{
		repo.save(alien);
		return alien;
	}
	
	
}
