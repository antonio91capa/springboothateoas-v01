package com.example.api.component;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.example.api.controller.EmployeeController;
import com.example.api.model.Employee;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {

	@Override
	public Resource<Employee> toResource(Employee entity) {
		return new Resource<Employee>(entity, 
			linkTo(methodOn(EmployeeController.class).findById(entity.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).allEmployees()).withRel("employees"));
	}

}
