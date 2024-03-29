package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CaseType extends MyModel{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String type;
        
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CaseType [id=" + id + ", type=" + type + "]";
	}
	
	
	
}