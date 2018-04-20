/**
 * 
 */
package com.example.demo.controller;

/**
 * @author Chrysovalantis
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.form.PersonForm;
import com.example.demo.model.Person;

import com.example.demo.model.Staff;
@Controller
public class MainController extends AllRepositories{
        
    private static List<Person> persons = new ArrayList<Person>();
 
    static {
        persons.add(new Person("Bill", "Gates"));
        persons.add(new Person("Steve", "Jobs"));
    }
 
    // Inject via application.properties
    @Value("${welcome.message}")
    private String message;
 
    @Value("${error.message}")
    private String errorMessage;
 
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
 
        model.addAttribute("message", message);
 
        return "index";
    }
  
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {
  
        
        return "personList";
    }
    
    @RequestMapping(value = { "/consultation" }, method = RequestMethod.GET)
    public String consultation(Model model) {
    	
    	model.addAttribute("legalOpinions", legalRep.findAll());
    	model.addAttribute("recommendations", recomRep.findAll());
    	model.addAttribute("caseHistory", caseHistoryRep.findAll());
 
        return "consultation";
    }
    
    @RequestMapping(value = { "/receptionist" }, method = RequestMethod.GET)
    public String receptionist(Model model) {
    	
    	model.addAttribute("appointments", apointmentsRep.findAll());
    	model.addAttribute("appointmentsNumber", apointmentsRep.count());
   
        return "receptionist";
    }
    
    @RequestMapping(value = { "/clientss" }, method = RequestMethod.GET)
    public String clientss(Model model) {
    	
    	model.addAttribute("client", clientRep.findAll());
    	model.addAttribute("clientsNumber", clientRep.count());
   
        return "clientss";
    }

    @RequestMapping(value = { "/recommendations" }, method = RequestMethod.GET)
    public String recommendations(Model model) {
    	
    	model.addAttribute("recommendationss", recomRep.findAll());
    	// model.addAttribute("clientsNumber", clientRep.count());
   
        return "recommendations";
    }
 
    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {
 
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
 
        return "addPerson";
    }
 
    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, //
            @ModelAttribute("personForm") PersonForm personForm) {
 
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
 
        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            Person newPerson = new Person(firstName, lastName);
            persons.add(newPerson);
 
            return "redirect:/personList";
        }
 
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    } 
    
    
    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public RedirectView login(String username ,String password) {
    	Iterable<Staff> users = staffRep.findAll();
    	for(Staff user : users) {
    		System.out.println(user);
    		String uUsername = user.getUsername();
    		String uPassword = user.getPassword();
    		if(uUsername == null || uPassword == null) {
    			continue;
    		}
    		if(uUsername.equals(username)&&uPassword.equals(password)) {
    			String role = user.getRole();
    			if(role==null) {
    				continue;
    			}
    			if(role.compareTo(Staff.LEGAL_OFFICE)==0) {
    				return new RedirectView("consultation");
    			}
				if(role.compareTo(Staff.LEGAL_STAFF)==0) {
					return new RedirectView("consultation");				
				    			}
				if(role.compareTo(Staff.RECEPTIONIST)==0) {
					return new RedirectView("consultation");
				}
    		}
    	}
    	 
    	
    	// TODO add error
    	return new RedirectView("");
    	
    }
}