package com.example.api.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.component.EmployeeResourceAssembler;
import com.example.api.exception.EmployeeNotFoundException;
import com.example.api.jpa.EmployeeRepository;
import com.example.api.model.Employee;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class EmployeeController {
	
	private final EmployeeRepository repository;
	
	private final EmployeeResourceAssembler assembler;
	
	public EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler assembler) {
		this.repository=repository;
		this.assembler=assembler;
	}
	
	
	/**************** OBTENER TODOS LOS EMPLEADOS *******************/
	
//	@GetMapping("/employees")
//	public List<Employee> all(){
//		return repository.findAll();
//	}
	
	/* ----- Uso de HATEOAS -----*/
	@SuppressWarnings("unchecked")
	@GetMapping("/employees")
	public Resources<Resource<Employee>> allEmployees(){
		
//		List<Resource<Employee>> employees=repository.findAll().stream()
//			.map(employee -> new Resource(employee, 
//				linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel(),
//				linkTo(methodOn(EmployeeController.class).allEmployees()).withRel("employees")))
//			.collect(Collectors.toList());
		
		List<Resource<Employee>> employees=repository.findAll().stream()
				.map(assembler:: toResource)
				.collect(Collectors.toList());
		
		return new Resources<Resource<Employee>>(employees, 
				linkTo(methodOn(EmployeeController.class).allEmployees()).withSelfRel());
	}
	
	
	/************** GUARDAR UN EMPLEADO ***************/
	
//	@PostMapping("/employee")
//	public Employee save(@RequestBody Employee employee) {
//		return repository.save(employee);
//	}
	
	@PostMapping("/employee")
	public ResponseEntity<?> save(@RequestBody Employee emp) throws URISyntaxException{
		Resource<Employee> resource=assembler.toResource(repository.save(emp));
		
		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}
	
	
	/************** OBTENER EMPLEADO POR ID ********************/
	
//	@GetMapping("/employee/{id}")
//	public Employee getById(@PathVariable Long id) {
//		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//	}
	
	/* ----- Uso de HATEOAS ----- */
	@GetMapping("/employee/{id}")
	public Resource<Employee> findById(@PathVariable Long id){
		
		Employee emp=repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
		
		return assembler.toResource(emp);
		
//		return new Resource<Employee>(emp, 
//			linkTo(methodOn(EmployeeController.class).findById(id)).withSelfRel(),
//			linkTo(methodOn(EmployeeController.class).allEmployees()).withRel("employees"));
		
	}
	
	/**************** ACTUALIZAR EMPLEADO ********************/
	/*
	 @PutMapping("/employee/{id}")
	public Employee update(@RequestBody Employee empleado, @PathVariable Long id) {
		
		return repository.findById(id)
				.map(employee -> {
					employee.setName(empleado.getName());
					employee.setRole(empleado.getRole());
					return repository.save(employee);
				})
				.orElseGet(() -> {
					return repository.save(empleado);
				});
	}*/
	
	@PutMapping("/employee/{id}")
	public ResponseEntity<?> update(@RequestBody Employee emp, @PathVariable Long id) throws URISyntaxException{
		Employee update=repository.findById(id)
			.map(employee -> {
				employee.setName(emp.getName());
				employee.setRole(emp.getRole());
				return repository.save(employee);
			}).orElseGet(() -> {
				emp.setId(id);
				return repository.save(emp);
			});
		
		Resource<Employee> resource=assembler.toResource(update);
		
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
	
	
	/**************** ELIMINAR EMPLEADO **********************/
	/*
	@DeleteMapping("/employee/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}*/
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build(); 
	}
	
}
