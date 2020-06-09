package com.nagarro.emp.model;

public class TicketDto {

	private String ticktId;
	private String ticktSubjct;
	private String ticktDesc;
	private String empId;

	public TicketDto() {
		super();
	}

	public TicketDto(String ticktSubjct, String ticktDesc, String empId) {
		super();		
		this.ticktSubjct = ticktSubjct;
		this.ticktDesc = ticktDesc;
		this.empId = empId;
	}

	

	public String getTicktId() {
		return ticktId;
	}

	public void setTicktId(String ticktId) {
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empId == null) ? 0 : empId.hashCode());
		result = prime * result + ((ticktDesc == null) ? 0 : ticktDesc.hashCode());
		result = prime * result + ((ticktId == null) ? 0 : ticktId.hashCode());
		result = prime * result + ((ticktSubjct == null) ? 0 : ticktSubjct.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketDto other = (TicketDto) obj;
		if (empId == null) {
			if (other.empId != null)
				return false;
		} else if (!empId.equals(other.empId))
			return false;
		if (ticktDesc == null) {
			if (other.ticktDesc != null)
				return false;
		} else if (!ticktDesc.equals(other.ticktDesc))
			return false;
		if (ticktId == null) {
			if (other.ticktId != null)
				return false;
		} else if (!ticktId.equals(other.ticktId))
			return false;
		if (ticktSubjct == null) {
			if (other.ticktSubjct != null)
				return false;
		} else if (!ticktSubjct.equals(other.ticktSubjct))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TicketDto [ticktssId=" + ticktId + ", ticktSubjct=" + ticktSubjct + ", ticktDesc=" + ticktDesc
				+ ", empId=" + empId + "]";
	}

}
