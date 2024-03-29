package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ChangeRequest;
import com.example.demo.model.Client;

@RestController
@RequestMapping(path = "/changeRequests")

public class ChangeRequestController extends CoreController<ChangeRequest, CrudRepository<ChangeRequest, Long>> {
	@Autowired
	private CrudRepository<ChangeRequest, Long> chreq;
	@Autowired
	private CrudRepository<Client, Long> clients;
	
	/**
	 * 
	 */
	@PostMapping(path="/addLock") 
	public @ResponseBody String addNewT (@Valid @RequestBody ChangeRequest changeR) {
		Long clientId = changeR.getClientId();
		Client c = clients.findById(clientId).get();
	
		changeR.setDeleted(true);
		changeR.setDescription("Request to lock client");
		changeR.setNewName(c.getName());
		changeR.setNewSurname(c.getSurname());
		changeR.setNewPotentialMoneyLaundring(c.isPotentialMoneyLaundring());
		changeR.setState(ChangeRequest.UNPROSESED);
		chreq.save(changeR);
		return changeR.getId()+"";
	}
	
	/**
	 * Add a change request to the database to be approved
	 * @param t the request to be approved
	 * @return the id of the change request added
	 */
	@PutMapping(path="/editClient")
	public @ResponseBody String editClient (@Valid @RequestBody ChangeRequest t) {
		t.setId(null);
		chreq.save(t);
		return t.getId()+"";
	}
}