package com.billprint.model;
public class Payment {
private String partyname;
private String trandate;
private String tranno;
private double credit;
private double debit;
private String reference;
private String agttranno;
private String empname;
private double taxableamt;
public String getPartyname() {
	return partyname;
}
public void setPartyname(String partyname) {
	this.partyname = partyname;
}
public String getTrandate() {
	return trandate;
}
public void setTrandate(String trandate) {
	this.trandate = trandate;
}
public String getTranno() {
	return tranno;
}
public void setTranno(String tranno) {
	this.tranno = tranno;
}
public double getCredit() {
	return credit;
}
public void setCredit(double credit) {
	this.credit = credit;
}
public double getDebit() {
	return debit;
}
public void setDebit(double debit) {
	this.debit = debit;
}
public String getReference() {
	return reference;
}
public void setReference(String reference) {
	this.reference = reference;
}
public String getAgttranno() {
	return agttranno;
}
public void setAgttranno(String agttranno) {
	this.agttranno = agttranno;
}
public String getEmpname() {
	return empname;
}
public void setEmpname(String empname) {
	this.empname = empname;
}
public double getTaxableamt() {
	return taxableamt;
}
public void setTaxableamt(double taxableamt) {
	this.taxableamt = taxableamt;
}
}
