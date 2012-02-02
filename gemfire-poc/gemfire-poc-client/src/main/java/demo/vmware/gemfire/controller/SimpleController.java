/*
 * VMware
 * Copyright, VMware, Inc.
 */
package demo.vmware.gemfire.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.vmware.gemfire.client.SimpleDAO;
import demo.vmware.gemfire.domain.Simple;

/**
 * Basic controller for manipulating the Simple entity
 * 
 * @author <a href="mailto:cdelashmutt@vmware.com">cdelashmutt</a>
 * @version 1.0
 */
@Controller
@RequestMapping("/simple")
public class SimpleController
{
	@Resource
	private SimpleDAO simpleDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model)
	{
		model.addAttribute("simple", getNewSimple());
		return "simple/index";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(@ModelAttribute("simple") Simple simple, ModelMap model)
	{
		simpleDao.save(simple);
		model.put("message", "Simple entity with id "+ simple.getId() + " saved!");
		return "simple/index";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String find(@PathVariable String id, ModelMap model)
	{
		model.addAttribute("simple", getNewSimple());
		model.addAttribute("foundSimple", simpleDao.findById(id));
		return "simple/index";
	}

	private Simple getNewSimple()
	{
		String id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		return new Simple(id, "");
	}
}