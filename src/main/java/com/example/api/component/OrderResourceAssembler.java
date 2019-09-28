package com.example.api.component;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.example.api.controller.OrderController;
import com.example.api.model.Order;
import com.example.api.model.Status;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class OrderResourceAssembler implements ResourceAssembler<Order, Resource<Order>> {

	@Override
	public Resource<Order> toResource(Order order) {
		Resource<Order> orderResource=new Resource<Order>(order, 
				linkTo(methodOn(OrderController.class).getById(order.getId())).withSelfRel(),
				linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));
		
		if(order.getStatus() == Status.IN_PROGRESS) {
			orderResource.add(
				linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
			
			orderResource.add(
				linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
		}
		return orderResource;
	}

	
}
