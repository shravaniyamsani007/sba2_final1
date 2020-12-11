package com.iiht.training.eloan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

@Entity
//@SequenceGenerator(name="clerkId", sequenceName="clerkId", initialValue=20000)
public class Clerk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//AUTO, generator="clerkId")
    @Column(length=5, nullable=false)
	//@ColumnDefault("20000")
	private Long clerkId;
	
    @Column(length=100, nullable=false)
	private String name;
	
	
	public Long getClerkId() {
		return clerkId;
	}
	public void setClerkId(Long clerkId) {
		this.clerkId = clerkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
