package com.nagarro.ticket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TICKET")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ticktId;

	@Column(name = "SUBJECT")
	private String ticktSubjct;
	@Column(name = "DESCRIPTION")
	private String ticktDesc;
	@Column(name = "EMPLOYEEID")
	private String empId;

	public Ticket() {
		super();
	}

	public Ticket(String ticktSubjct, String ticktDesc, String empId) {
		super();	
		this.ticktSubjct = ticktSubjct;
		this.ticktDesc = ticktDesc;
		this.empId=empId;
	}

	public Integer getTicktId() {
		return ticktId;
	}

	public void setTicktId(Integer ticktId) {
		this.ticktId = ticktId;
	}

	public String getTicktSubjct() {
		return ticktSubjct;
	}

	public void setTicktSubjct(String ticktSubjct) {
		this.ticktSubjct = ticktSubjct;
	}

	public String getTicktDesc() {
		return ticktDesc;
	}

	public void setTicktDesc(String ticktDesc) {
		this.ticktDesc = ticktDesc;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}	

}
