package com.billprint.model;
public class Repair {
private String tranno;
private String trandate;
private String proname;
private String empname;
private String orderno;
private String gstbillno;
private String rowsign;
private int pieces;
private double grosswt;
private double netwt;
private double repaircharges;
public String getTranno() {
	return tranno;
}
public void setTranno(String tranno) {
	this.tranno = tranno;
}
public String getTrandate() {
	return trandate;
}
public void setTrandate(String trandate) {
	this.trandate = trandate;
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
public String getOrderno() {
	return orderno;
}
public void setOrderno(String orderno) {
	this.orderno = orderno;
}
public String getGstbillno() {
	return gstbillno;
}
public void setGstbillno(String gstbillno) {
	this.gstbillno = gstbillno;
}
public String getRowsign() {
	return rowsign;
}
public void setRowsign(String rowsign) {
	this.rowsign = rowsign;
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
public double getRepaircharges() {
	return repaircharges;
}
public void setRepaircharges(double repaircharges) {
	this.repaircharges = repaircharges;
}	
}
