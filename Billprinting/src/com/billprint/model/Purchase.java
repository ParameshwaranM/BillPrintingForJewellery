package com.billprint.model;
public class Purchase {
private String hsncode;
private String description,entrytype;
public String getEntrytype() {
	return entrytype;
}
public void setEntrytype(String entrytype) {
	this.entrytype = entrytype;
}
private int pieces;
private double grosswt;
private double netwt;
private double dustwt;
private double wastage;
private double netamount;
private double rate;
private String rowsign;
private String empname;
private String billno;
private String billdate;
private String tranno;
public String getHsncode() {
	return hsncode;
}
public void setHsncode(String hsncode) {
	this.hsncode = hsncode;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
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
public double getDustwt() {
	return dustwt;
}
public void setDustwt(double dustwt) {
	this.dustwt = dustwt;
}
public double getWastage() {
	return wastage;
}
public void setWastage(double wastage) {
	this.wastage = wastage;
}
public double getNetamount() {
	return netamount;
}
public void setNetamount(double netamount) {
	this.netamount = netamount;
}
public double getRate() {
	return rate;
}
public void setRate(double rate) {
	this.rate = rate;
}
public String getRowsign() {
	return rowsign;
}
public void setRowsign(String rowsign) {
	this.rowsign = rowsign;
}
public String getEmpname() {
	return empname;
}
public void setEmpname(String empname) {
	this.empname = empname;
}
public String getBillno() {
	return billno;
}
public void setBillno(String billno) {
	this.billno = billno;
}
public String getBilldate() {
	return billdate;
}
public void setBilldate(String billdate) {
	this.billdate = billdate;
}
public String getTranno() {
	return tranno;
}
public void setTranno(String tranno) {
	this.tranno = tranno;
}
}
