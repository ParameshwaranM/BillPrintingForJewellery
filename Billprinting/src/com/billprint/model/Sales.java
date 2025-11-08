package com.billprint.model;
public class Sales {
private String hsncode;
private String trandate;
private String gstbillno;
private String proname;
private int procode;
private int pieces;
private double grosswt,rate;

private double othercharge;
private double addchg;
private double addchgtax;
private double netwt;
private double purity;
private double mkcharge;
private double totalamount;
private double sgstper;
private double sgstamt;
private double cgstper;
private double cgstamt;
private double igstper;
private double igstamt;
private double discount;
private double netamount;
private double wastage;
private String rowsign;
private String orderno;
private String orderdate;
private double orderrate;
private String empname;
private String entrytype;
private String inspectiontype;
private double tagwt;
private double overalldiscount;
private double bulkdiscount;
private String salestype;
public void setEmpname(String empname) {
	this.empname = empname;
}
public String getHsncode() {
	return hsncode;
}
public double getRate() {
	return rate;
}
public void setRate(double rate) {
	this.rate = rate;
}
public void setHsncode(String hsncode) {
	this.hsncode = hsncode;
}
public String getTrandate() {
	return trandate;
}
public void setTrandate(String trandate) {
	this.trandate = trandate;
}
public String getGstbillno() {
	return gstbillno;
}
public void setGstbillno(String gstbillno) {
	this.gstbillno = gstbillno;
}
public int getProcode() {
	return procode;
}
public void setProcode(int procode) {
	this.procode = procode;
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
public double getPurity() {
	return purity;
}
public void setPurity(double purity) {
	this.purity = purity;
}
public double getMkcharge() {
	return mkcharge;
}
public void setMkcharge(double mkcharge) {
	this.mkcharge = mkcharge;
}
public double getTotalamount() {
	return totalamount;
}
public void setTotalamount(double totalamount) {
	this.totalamount = totalamount;
}
public double getSgstper() {
	return sgstper;
}
public void setSgstper(double sgstper) {
	this.sgstper = sgstper;
}
public double getSgstamt() {
	return sgstamt;
}
public void setSgstamt(double sgstamt) {
	this.sgstamt = sgstamt;
}
public double getCgstper() {
	return cgstper;
}
public void setCgstper(double cgstper) {
	this.cgstper = cgstper;
}
public double getIgstper() {
	return igstper;
}
public void setIgstper(double igstper) {
	this.igstper = igstper;
}
public double getIgstamt() {
	return igstamt;
}
public void setIgstamt(double igstamt) {
	this.igstamt = igstamt;
}
public double getDiscount() {
	return discount;
}
public void setDiscount(double discount) {
	this.discount = discount;
}
public double getNetamount() {
	return netamount;
}
public void setNetamount(double netamount) {
	this.netamount = netamount;
}
public String getProname() {
	return proname;
}
public void setProname(String proname) {
	this.proname = proname;
}
public double getCgstamt() {
	return cgstamt;
}
public void setCgstamt(double cgstamt) {
	this.cgstamt = cgstamt;
}
public double getWastage() {
	return wastage;
}
public void setWastage(double wastage) {
	this.wastage = wastage;
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
public double getTagwt() {
	return tagwt;
}
public void setTagwt(double tagwt) {
	this.tagwt = tagwt;
}
public String getEntrytype() {
	return entrytype;
}
public void setEntrytype(String entrytype) {
	this.entrytype = entrytype;
}
public String getInspectiontype() {
	return inspectiontype;
}
public void setInspectiontype(String inspectiontype) {
	this.inspectiontype = inspectiontype;
}
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
public double getOrderrate() {
	return orderrate;
}
public void setOrderrate(double orderrate) {
	this.orderrate = orderrate;
}
public double getOthercharge() {
	return othercharge;
}
public void setOthercharge(double othercharge) {
	this.othercharge = othercharge;
}
public double getAddchg() {
	return addchg;
}
public void setAddchg(double addchg) {
	this.addchg = addchg;
}
public double getAddchgtax() {
	return addchgtax;
}
public void setAddchgtax(double addchgtax) {
	this.addchgtax = addchgtax;
}
public double getOveralldiscount() {
	return overalldiscount;
}
public void setOveralldiscount(double overalldiscount) {
	this.overalldiscount = overalldiscount;
}
public double getBulkdiscount() {
	return bulkdiscount;
}
public void setBulkdiscount(double bulkdiscount) {
	this.bulkdiscount = bulkdiscount;
}
public String getSalestype() {
	return salestype;
}
public void setSalestype(String salestype) {
	this.salestype = salestype;
}
}
