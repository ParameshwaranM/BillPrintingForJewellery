package com.billprint.model;
public class Order {
private String orderno;
private String orderdate;
private String entrytype;
private String rowsign;
private String proname;
private String empname;
private String trandate;
private String tranno;
private int pieces;
private double grosswt;
private double netwt;
private double rate;
private double amount;
private double sgstper;
private double sgstamount;
private double cgstper;
private double cgstamount;
private double igstper;
private double igstamount;
private double taxableamt;
private double prmkcharge;
private double prwastage;
private String duedate;
public String getOrderno() {
	return orderno;
}
public void setOrderno(String orderno) {
	this.orderno = orderno;
}
public String getOrderdate() {
	return orderdate;
}
public void setOrderdate(String orderdate) {
	this.orderdate = orderdate;
}
public String getEntrytype() {
	return entrytype;
}
public void setEntrytype(String entrytype) {
	this.entrytype = entrytype;
}
public String getRowsign() {
	return rowsign;
}
public void setRowsign(String rowsign) {
	this.rowsign = rowsign;
}
public String getProname() {
	return proname;
}
public void setProname(String proname) {
	this.proname = proname;
}
public String getEmpname() {
	return empname;
}
public void setEmpname(String empname) {
	this.empname = empname;
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
public int getPieces() {
	return pieces;
}
public void setPieces(int pieces) {
	this.pieces = pieces;
}
public double getGrosswt() {
	return grosswt;
}
public void setGrosswt(double grosswt) {
	this.grosswt = grosswt;
}
public double getNetwt() {
	return netwt;
}
public void setNetwt(double netwt) {
	this.netwt = netwt;
}
public double getRate() {
	return rate;
}
public void setRate(double rate) {
	this.rate = rate;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public double getSgstper() {
	return sgstper;
}
public void setSgstper(double sgstper) {
	this.sgstper = sgstper;
}
public double getSgstamount() {
	return sgstamount;
}
public void setSgstamount(double sgstamount) {
	this.sgstamount = sgstamount;
}
public double getCgstper() {
	return cgstper;
}
public void setCgstper(double cgstper) {
	this.cgstper = cgstper;
}
public double getCgstamount() {
	return cgstamount;
}
public void setCgstamount(double cgstamount) {
	this.cgstamount = cgstamount;
}
public double getIgstper() {
	return igstper;
}
public void setIgstper(double igstper) {
	this.igstper = igstper;
}
public double getIgstamount() {
	return igstamount;
}
public void setIgstamount(double igstamount) {
	this.igstamount = igstamount;
}
public String getDuedate() {
	return duedate;
}
public void setDuedate(String duedate) {
	this.duedate = duedate;
}
public double getTaxableamt() {
	return taxableamt;
}
public void setTaxableamt(double taxableamt) {
	this.taxableamt = taxableamt;
}
public double getPrmkcharge() {
	return prmkcharge;
}
public void setPrmkcharge(double prmkcharge) {
	this.prmkcharge = prmkcharge;
}
public double getPrwastage() {
	return prwastage;
}
public void setPrwastage(double prwastage) {
	this.prwastage = prwastage;
}
}
