package com.billprint.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.billprint.model.Advancedetails;
import com.billprint.model.BalanceQuery;
import com.billprint.model.Collectiondetails;
import com.billprint.model.Company;
import com.billprint.model.Customer;
import com.billprint.model.Databaseconn;
import com.billprint.model.DiscountMaster;
import com.billprint.model.Inspection;
import com.billprint.model.InspectionStone;
import com.billprint.model.Order;
import com.billprint.model.Otherissue;
import com.billprint.model.Payment;
import com.billprint.model.Purchase;
import com.billprint.model.Purchasereturn;
import com.billprint.model.Receipt;
import com.billprint.model.Repair;
import com.billprint.model.Sales;
import com.billprint.model.SalesWeightAdjusted;
import com.billprint.model.Salesreturn;
import com.billprint.model.Salesreturnstone;
import com.billprint.model.Salestonedetails;
import com.billprint.model.Scheme;
import com.billprint.model.Tranreftable;
import com.billprint.query.Query;
import com.billprint.start.Common;
import com.billprint.start.Dbconnection;
public class Dao {
private static Query query=new Query();
private Connection connection;
private Statement statement;
private ResultSet resultSet;
private List<Databaseconn>lstDbname=null;
private List<Company>lstCompany=null;
private List<Sales>lstSales=null;
private List<Customer>lstCustomer=null;
private List<Customer>lstRate=null;
private List<Salestonedetails>lststndetails=null;
private List<Collectiondetails>lstcollectiondetails=null;
private List<Salesreturn> lstsalesreturn=null;
private List<Salesreturnstone>lstSalesreturnstone=null;
private List<Purchase>lstPurchase=null;
private List<Purchasereturn>lstPurchasereturn=null;
private List<Receipt>lstreceipt=null;
private List<Payment>lstPayment=null;
private List<Inspection>lstInspection=null;
private List<InspectionStone>lstInspectionStone=null;
private List<Order>lstOrder=null;
private List<Repair>lstRepair=null;
private List<Otherissue>lstOtherissue=null;
private List<Advancedetails>lstadvance=null;
private String printername="";
private List<Tranreftable>lstTranreftable=null;
private List<DiscountMaster>lstDiscountMaster=null;
private List<Scheme>lstScheme=null;
private List<SalesWeightAdjusted>lstSalesWeightAdjusted=null;
private List<BalanceQuery>lstBalanceQueries=null;
public List<Databaseconn>dbname() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.dbname());
	lstDbname=new ArrayList<Databaseconn>();
	while(resultSet.next()) {
		Databaseconn databaseconn=new Databaseconn();
		databaseconn.setCompid(resultSet.getString("COMPID"));
		databaseconn.setMasterdb(resultSet.getString("MASTERDB"));
		databaseconn.setTrandb(resultSet.getString("TRANDB"));
		databaseconn.setStockdb(resultSet.getString("STOCKDB"));
		databaseconn.setSchemedb(resultSet.getString("SCHEMEDB"));
		databaseconn.setCatlogdb(resultSet.getString("CATLOGDB"));
		lstDbname.add(databaseconn);
	}
	return lstDbname;	
  }

public String printername(String printname) throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.printername(printname));
	if(resultSet.next()) {
		printername=resultSet.getString("Printername");
	}
	return printername;
  }

public List<Tranreftable> tranreftable() throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.tranreftable());
	lstTranreftable=new ArrayList<Tranreftable>();
	while(resultSet.next()) {
	Tranreftable tranreftable=new Tranreftable();
	tranreftable.setNumbercontrolcode(resultSet.getString("Numbercontrolcode"));
	tranreftable.setBilldate(Common.getSf().format(resultSet.getDate("Trandate")));
	lstTranreftable.add(tranreftable);
	}
	return lstTranreftable;
  }

public String billtype(String numbercontrolcode) throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.billtype(numbercontrolcode));
	if(resultSet.next()) {
		numbercontrolcode=resultSet.getString("TypeName");
	}
	return numbercontrolcode;
  }

public List<Company>companydetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.companydetails());
	lstCompany=new ArrayList<Company>();
	while(resultSet.next()) {
	Company company=new Company();
	company.setCompanyname(resultSet.getString("COMPANYNAME"));
	company.setAdd1(resultSet.getString("ADD1"));
	company.setAdd2(resultSet.getString("ADD2"));
	company.setCity(resultSet.getString("CITY"));
	company.setPincode(resultSet.getString("PINCODE"));
	company.setPhoneno(resultSet.getString("PHONENO"));
	company.setGstno(resultSet.getString("GSTNO"));
	company.setState(resultSet.getString("STATE"));
	lstCompany.add(company);
	}
	return lstCompany;
  }
public List<Customer>customerdetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.customerdetails());
	lstCustomer=new ArrayList<Customer>();
	while(resultSet.next()) {
	Customer customer=new Customer();
	customer.setCustomername(resultSet.getString("CUSTOMERNAME"));
	customer.setAddress1(resultSet.getString("ADDRESS1"));
	customer.setAddress2(resultSet.getString("ADDRESS2"));
	customer.setArea(resultSet.getString("AREA"));
	customer.setCity(resultSet.getString("CITY"));
	customer.setGstno(resultSet.getString("GSTNO"));
	customer.setMobileno(resultSet.getString("MOBILENO"));
	customer.setState(resultSet.getString("STATE"));
	customer.setProofname(resultSet.getString("ProofName"));
	customer.setProofno(resultSet.getString("ProofNumber"));
	lstCustomer.add(customer);
	}
	return lstCustomer;
	
}

public List<Sales>salesdetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.salesdetails());
	lstSales=new ArrayList<Sales>();
	while(resultSet.next()) {
	Sales sales=new Sales();
	sales.setRate(resultSet.getDouble("rate"));
	sales.setCgstper(resultSet.getDouble("CGSTPER"));
	sales.setCgstamt(resultSet.getDouble("CGSTAMT"));
	sales.setDiscount(resultSet.getDouble("DISCOUNT"));
	sales.setGrosswt(resultSet.getDouble("GROSSWT"));
	sales.setGstbillno(resultSet.getString("GSTBILLNO"));
	sales.setHsncode(resultSet.getString("HSNCODE"));
	sales.setIgstamt(resultSet.getDouble("IGSTAMT"));
	sales.setIgstper(resultSet.getDouble("IGSTPER"));
	sales.setMkcharge(resultSet.getDouble("MKCHARGE"));
	sales.setNetamount(resultSet.getDouble("NETAMOUNT"));
	sales.setNetwt(resultSet.getDouble("NETWT"));
	sales.setPieces(resultSet.getInt("PIECES"));
	sales.setProcode(resultSet.getInt("PROCODE"));
	sales.setPurity(resultSet.getDouble("PURITY"));
	sales.setSgstamt(resultSet.getDouble("SGSTAMT"));
	sales.setSgstper(resultSet.getDouble("SGSTPER"));
	sales.setTotalamount(resultSet.getDouble("TOTALAMOUNT"));
	sales.setTrandate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	sales.setProname(resultSet.getString("PRONAME"));
	sales.setWastage(resultSet.getDouble("WASTAGE"));
	sales.setRowsign(resultSet.getString("ROWSIGN"));
	sales.setEmpname(resultSet.getString("EMPNAME"));
	sales.setTagwt(resultSet.getDouble("TAGWT"));
	sales.setEntrytype(resultSet.getString("ENTRYTYPE"));
	sales.setInspectiontype(resultSet.getString("INSPECTIONTYPE"));
	sales.setOrderno(resultSet.getString("ORDERNO"));
	sales.setOrderrate(resultSet.getDouble("ORDERRATE"));
	sales.setOrderdate(Common.getSf().format(resultSet.getDate("ORDERDATE")));
	sales.setOthercharge(resultSet.getDouble("OTHERCHARGE"));
	sales.setAddchg(resultSet.getDouble("ADDCHG"));
	sales.setAddchgtax(resultSet.getDouble("ADDCHGTAX"));
	sales.setOveralldiscount(resultSet.getDouble("OverallDiscount"));
	sales.setBulkdiscount(resultSet.getDouble("BulkDiscount"));
	sales.setSalestype(resultSet.getString("SalesType"));
	lstSales.add(sales);
	}
	return lstSales;
   }
public List<Customer>ratedetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.ratedetails());
	lstRate=new ArrayList<Customer>();
	while(resultSet.next()) {
	Customer customer=new Customer();
	customer.setRate(resultSet.getDouble("RATE"));
	customer.setMetalcode(resultSet.getString("METALCODE"));
	lstRate.add(customer);
	}
	return lstRate;
   }
public List<Salestonedetails>salesstndetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.salesstndetails());
	lststndetails=new ArrayList<Salestonedetails>();
	while(resultSet.next()) {
	Salestonedetails stonedetails=new Salestonedetails();
	stonedetails.setStnhsncode(resultSet.getString("STNHSNCODE"));
	stonedetails.setStnproname(resultSet.getString("STNPRONAME"));
	stonedetails.setStnpieces(resultSet.getInt("STNPIECES"));
	stonedetails.setStnrate(resultSet.getDouble("STNRATE"));
	stonedetails.setStnweight(resultSet.getDouble("STNWEIGHT"));
	stonedetails.setStnamount(resultSet.getDouble("STNAMOUNT"));
	stonedetails.setSalerowsign(resultSet.getString("SALEROWSIGN"));
	lststndetails.add(stonedetails);
	}
	return lststndetails;	
   }
public List<Collectiondetails> collectiondetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.collectiondetails());
	lstcollectiondetails=new ArrayList<Collectiondetails>();
	while(resultSet.next()) {
	Collectiondetails collectiondetails=new Collectiondetails();
	collectiondetails.setDebit(resultSet.getDouble("DEBIT"));
	collectiondetails.setPaymentflag(resultSet.getString("PAYMENTFLAG"));
	collectiondetails.setCredit(resultSet.getDouble("CREDIT"));
	collectiondetails.setModename(resultSet.getString("MODENAME"));
	collectiondetails.setCardorchqnno(resultSet.getString("CardOrChqNo"));
	collectiondetails.setCardname(resultSet.getString("CARDNAME"));
	collectiondetails.setBranchname(resultSet.getString("BRANCHNAME"));
	collectiondetails.setBankname(resultSet.getString("BANKNAME"));
	collectiondetails.setSchemecode(resultSet.getString("SCHEMECODE"));
	collectiondetails.setClientid(resultSet.getString("CLIENTID"));
	collectiondetails.setAgttranno(resultSet.getString("AgtTranNo"));
	lstcollectiondetails.add(collectiondetails);
	}
	return lstcollectiondetails;	
  }
public List<Salesreturn>salesreturndetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.salesretrundetails());
	lstsalesreturn=new ArrayList<Salesreturn>();
	while(resultSet.next()) {
	Salesreturn salesreturn=new Salesreturn();
	salesreturn.setEntrytype(resultSet.getString("Entrytype"));
	salesreturn.setCgstper(resultSet.getDouble("CGSTPER"));
	salesreturn.setCgstamt(resultSet.getDouble("CGSTAMT"));
	salesreturn.setDiscount(resultSet.getDouble("DISCOUNT"));
	salesreturn.setGrosswt(resultSet.getDouble("GROSSWT"));
	salesreturn.setGstbillno(resultSet.getString("GSTBILLNO"));
	salesreturn.setHsncode(resultSet.getString("HSNCODE"));
	salesreturn.setIgstamt(resultSet.getDouble("IGSTAMT"));
	salesreturn.setIgstper(resultSet.getDouble("IGSTPER"));
	salesreturn.setMkcharge(resultSet.getDouble("MKCHARGE"));
	salesreturn.setNetamount(resultSet.getDouble("NETAMOUNT"));
	salesreturn.setNetwt(resultSet.getDouble("NETWT"));
	salesreturn.setPieces(resultSet.getInt("PIECES"));
	salesreturn.setProcode(resultSet.getInt("PROCODE"));
	salesreturn.setPurity(resultSet.getDouble("PURITY"));
	salesreturn.setSgstamt(resultSet.getDouble("SGSTAMT"));
	salesreturn.setSgstper(resultSet.getDouble("SGSTPER"));
	salesreturn.setTotalamount(resultSet.getDouble("TOTALAMOUNT"));
	salesreturn.setTrandate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	salesreturn.setProname(resultSet.getString("PRONAME"));
	salesreturn.setWastage(resultSet.getDouble("WASTAGE"));
	salesreturn.setRowsign(resultSet.getString("ROWSIGN"));
	salesreturn.setEmpname(resultSet.getString("EMPNAME"));
	salesreturn.setBillno(resultSet.getString("BILLNO"));
	salesreturn.setBilldate(Common.getSf().format(resultSet.getDate("BILLDATE")));
	lstsalesreturn.add(salesreturn);
	}
	return lstsalesreturn;
   }

public List<Salesreturnstone>salesreturnstone() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.salesreturnstone());
	lstSalesreturnstone=new ArrayList<Salesreturnstone>();
	while(resultSet.next()) {
	Salesreturnstone salesreturnstone=new Salesreturnstone();
	salesreturnstone.setStnhsncode(resultSet.getString("STNHSNCODE"));
	salesreturnstone.setStnproname(resultSet.getString("STNPRONAME"));
	salesreturnstone.setStnpieces(resultSet.getInt("STNPIECES"));
	salesreturnstone.setStnrate(resultSet.getDouble("STNRATE"));
	salesreturnstone.setStnweight(resultSet.getDouble("STNWEIGHT"));
	salesreturnstone.setStnamount(resultSet.getDouble("STNAMOUNT"));
	salesreturnstone.setSaleretrowsign(resultSet.getString("SALERETROWSIGN"));
	lstSalesreturnstone.add(salesreturnstone);
	}
	return lstSalesreturnstone;	
   }

public List<Purchase>purchasedetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.purchasedetails());
	lstPurchase=new ArrayList<Purchase>();
	while(resultSet.next()) {
	Purchase purchase=new Purchase();
	purchase.setEntrytype(resultSet.getString("Entrytype"));
	purchase.setDescription(resultSet.getString("DESCRIPTION"));
	purchase.setDustwt(resultSet.getDouble("DUSTWT"));
	purchase.setEmpname(resultSet.getString("EMPNAME"));
	purchase.setGrosswt(resultSet.getDouble("GROSSWT"));
	purchase.setHsncode(resultSet.getString("HSNCODE"));
	purchase.setNetamount(resultSet.getDouble("NETAMOUNT"));
	purchase.setNetwt(resultSet.getDouble("NETWT"));
	purchase.setPieces(resultSet.getInt("PIECES"));
	purchase.setRate(resultSet.getDouble("RATE"));
	purchase.setRowsign(resultSet.getString("ROWSIGN"));
	purchase.setWastage(resultSet.getDouble("WASTAGE"));
	purchase.setBillno(resultSet.getString("GSTBILLNO"));
	purchase.setBilldate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	purchase.setTranno(resultSet.getString("TRANNO"));
	lstPurchase.add(purchase);
	}
	return lstPurchase;
   }

public List<Purchasereturn>purchasereturndetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.purchasereturndetails());
	lstPurchasereturn=new ArrayList<Purchasereturn>();
	while(resultSet.next()) {
	Purchasereturn purchasereturn=new Purchasereturn();
	purchasereturn.setDescription(resultSet.getString("DESCRIPTION"));
	purchasereturn.setEmpname(resultSet.getString("EMPNAME"));
	purchasereturn.setGrosswt(resultSet.getDouble("GROSSWT"));
	purchasereturn.setHsncode(resultSet.getString("HSNCODE"));
	purchasereturn.setNetamount(resultSet.getDouble("NETAMOUNT"));
	purchasereturn.setNetwt(resultSet.getDouble("NETWT"));
	purchasereturn.setPieces(resultSet.getInt("PIECES"));
	purchasereturn.setRate(resultSet.getDouble("RATE"));
	purchasereturn.setRowsign(resultSet.getString("ROWSIGN"));
	purchasereturn.setWastage(resultSet.getDouble("WASTAGE"));
	purchasereturn.setBillno(resultSet.getString("GSTBILLNO"));
	purchasereturn.setBilldate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	lstPurchasereturn.add(purchasereturn);
	}
	return lstPurchasereturn;
   }

public List<Receipt>receiptdetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.receiptdetails());
	lstreceipt=new ArrayList<Receipt>();
	while(resultSet.next()) {
	Receipt receipt=new Receipt();
	receipt.setTranno(resultSet.getString("TRANNO"));
	receipt.setCredit(resultSet.getDouble("CREDIT"));
	receipt.setPartyname(resultSet.getString("PARTYNAME"));
	receipt.setReference(resultSet.getString("REFERENCE"));
	receipt.setTrandate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	receipt.setAgttranno(resultSet.getString("AGTTRANNO"));
	receipt.setEmpname(resultSet.getString("EMPNAME"));
	lstreceipt.add(receipt);
	}
	return lstreceipt;
   }
public List<Payment>paymentdetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.paymentdetails());
	lstPayment=new ArrayList<Payment>();
	while(resultSet.next()) {
	Payment payment=new Payment();
	payment.setTranno(resultSet.getString("TRANNO"));
	payment.setCredit(resultSet.getDouble("CREDIT"));
	payment.setPartyname(resultSet.getString("PARTYNAME"));
	payment.setReference(resultSet.getString("REFERENCE"));
	payment.setTrandate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	payment.setAgttranno(resultSet.getString("AGTTRANNO"));
	payment.setEmpname(resultSet.getString("EMPNAME"));
	payment.setDebit(resultSet.getDouble("DEBIT"));
	payment.setTaxableamt(resultSet.getDouble("TAXABLEAMT"));
	lstPayment.add(payment);
	}
	return lstPayment;
   }

public List<Inspection>inspectiondetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.inspectiondetails());
	lstInspection=new ArrayList<Inspection>();
	while(resultSet.next()) {
	Inspection inspection=new Inspection();
	inspection.setCgstper(resultSet.getDouble("CGSTPER"));
	inspection.setCgstamt(resultSet.getDouble("CGSTAMT"));
	inspection.setDiscount(resultSet.getDouble("DISCOUNT"));
	inspection.setGrosswt(resultSet.getDouble("GROSSWT"));
	inspection.setGstbillno(resultSet.getString("GSTBILLNO"));
	inspection.setHsncode(resultSet.getString("HSNCODE"));
	inspection.setIgstamt(resultSet.getDouble("IGSTAMT"));
	inspection.setIgstper(resultSet.getDouble("IGSTPER"));
	inspection.setNetamount(resultSet.getDouble("NETAMOUNT"));
	inspection.setNetwt(resultSet.getDouble("NETWT"));
	inspection.setPieces(resultSet.getInt("PIECES"));
	inspection.setProcode(resultSet.getInt("PROCODE"));
	inspection.setPurity(resultSet.getDouble("PURITY"));
	inspection.setSgstamt(resultSet.getDouble("SGSTAMT"));
	inspection.setSgstper(resultSet.getDouble("SGSTPER"));
	inspection.setTotalamount(resultSet.getDouble("TOTALAMOUNT"));
	inspection.setTrandate(Common.getSf().format(resultSet.getDate("TRANDATE")));
	inspection.setProname(resultSet.getString("PRONAME"));
	inspection.setRowsign(resultSet.getString("ROWSIGN"));
	inspection.setEmpname(resultSet.getString("EMPNAME"));
	inspection.setIssrec(resultSet.getString("ISSREC"));
	lstInspection.add(inspection);
	}
	return lstInspection;
   }

public List<InspectionStone>inspectionstndetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.inspectionstonedetails());
	lstInspectionStone=new ArrayList<InspectionStone>();
	while(resultSet.next()) {
	InspectionStone inspectionStone=new InspectionStone();
	inspectionStone.setStnhsncode(resultSet.getString("STNHSNCODE"));
	inspectionStone.setStnproname(resultSet.getString("STNPRONAME"));
	inspectionStone.setStnpieces(resultSet.getInt("STNPIECES"));
	inspectionStone.setStnrate(resultSet.getDouble("STNRATE"));
	inspectionStone.setStnweight(resultSet.getDouble("STNWEIGHT"));
	inspectionStone.setStnamount(resultSet.getDouble("STNAMOUNT"));
	inspectionStone.setInsrowsign(resultSet.getString("INSROWSIGN"));
	lstInspectionStone.add(inspectionStone);
	}
	return lstInspectionStone;	
   }

public List<Order>orderdetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.orderdetails());
	lstOrder=new ArrayList<Order>();
	while(resultSet.next()) {
	Order order=new Order();
	order.setOrderno(resultSet.getString("ORDERNO"));
	order.setOrderdate(Common.getSf().format(resultSet.getDate("ORDERDATE")));
	order.setDuedate(Common.getSf().format(resultSet.getDate("DUEDATE")));
	order.setRowsign(resultSet.getString("ROWSIGN"));
	order.setProname(resultSet.getString("PRONAME"));
	order.setEmpname(resultSet.getString("EMPNAME"));
	order.setEntrytype(resultSet.getString("ENTRYTYPE"));
	order.setPieces(resultSet.getInt("PIECES"));
	order.setGrosswt(resultSet.getDouble("GROSSWT"));
	order.setNetwt(resultSet.getDouble("NETWT"));
	order.setAmount(resultSet.getDouble("AMOUNT"));
	order.setIgstamount(resultSet.getDouble("IGSTAMOUNT"));
	order.setSgstper(resultSet.getDouble("SGSTPER"));
	order.setSgstamount(resultSet.getDouble("SGSTAMOUNT"));
	order.setCgstper(resultSet.getDouble("CGSTPER"));
	order.setCgstamount(resultSet.getDouble("CGSTAMOUNT"));
	order.setIgstper(resultSet.getDouble("IGSTPER"));
	order.setRate(resultSet.getDouble("RATE"));
	order.setPrwastage(resultSet.getDouble("PRWASTAGE"));
	order.setPrmkcharge(resultSet.getDouble("PRMKCHARGE"));
	lstOrder.add(order);
	}
	return lstOrder;
   }
public List<Repair>repairdetails() throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.repairdetails());
	lstRepair=new ArrayList<Repair>();
	while(resultSet.next()) {
	Repair repair=new Repair();
	repair.setProname(resultSet.getString("PRONAME"));
	repair.setEmpname(resultSet.getString("EMPNAME"));
	repair.setGrosswt(resultSet.getDouble("GROSSWT"));
	repair.setNetwt(resultSet.getDouble("NETWT"));
	repair.setPieces(resultSet.getInt("PIECES"));
	repair.setRowsign(resultSet.getString("ROWSIGN"));
	repair.setOrderno(resultSet.getString("ORDERNO"));
	repair.setTrandate(resultSet.getString("TRANDATE"));
	repair.setTranno(resultSet.getString("TRANNO"));
	repair.setGstbillno(resultSet.getString("GSTBILLNO"));
	repair.setRepaircharges(resultSet.getDouble("REPAIRCHARGES"));
	lstRepair.add(repair);
	}
	return lstRepair;
  }

public List<Otherissue>otherissuedetails()throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.otherissue());
	lstOtherissue=new ArrayList<Otherissue>();
	while(resultSet.next()) {
	Otherissue otherissue=new Otherissue();
	otherissue.setProname(resultSet.getString("PRONAME"));
	otherissue.setEmpname(resultSet.getString("EMPNAME"));
	otherissue.setGrosswt(resultSet.getDouble("GROSSWT"));
	otherissue.setNetwt(resultSet.getDouble("NETWT"));
	otherissue.setPieces(resultSet.getInt("PIECES"));
	otherissue.setTrandate(resultSet.getString("TRANDATE"));
	otherissue.setTranno(resultSet.getString("TRANNO"));
	otherissue.setNetamount(resultSet.getDouble("NETAMOUNT"));
	lstOtherissue.add(otherissue);
	}
	return lstOtherissue;
  }

public List<Advancedetails>advancedetails()throws Exception{
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.advancedetails());
	lstadvance=new ArrayList<Advancedetails>();
	while(resultSet.next()) {
	Advancedetails advancedetails=new Advancedetails();
	advancedetails.setCredit(resultSet.getDouble("CREDIT"));
	advancedetails.setWeight(resultSet.getDouble("WEIGHT"));
	advancedetails.setCatcode(resultSet.getInt("CATCODE"));
	lstadvance.add(advancedetails);
	}
	return lstadvance;
  }

public List<DiscountMaster> discountmaster() throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.discountmasterdetails());
	lstDiscountMaster=new ArrayList<DiscountMaster>();
	while(resultSet.next()) {
	DiscountMaster discountMaster=new DiscountMaster();
	discountMaster.setAdjdiscount(resultSet.getDouble("Discount"));
	discountMaster.setRowsign(resultSet.getString("RowSign"));
	lstDiscountMaster.add(discountMaster);
	}
	return lstDiscountMaster;
  }
public List<Scheme> schemedetails() throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.schemedetails());
	lstScheme=new ArrayList<Scheme>();
	while(resultSet.next()) {
	Scheme scheme=new Scheme();
	scheme.setAccwt(resultSet.getDouble("ACCWT"));
	scheme.setAmtrecd(resultSet.getDouble("AMT_RECD"));
	scheme.setClientid(resultSet.getString("CLIENTID"));
	scheme.setClosingtypeamtwt(resultSet.getString("CLOSINGTYPEAMTWT"));
	scheme.setGroupcode(resultSet.getString("GROUPCODE"));
	scheme.setId(resultSet.getInt("ID"));
	scheme.setMsno(resultSet.getInt("MSNO"));
	scheme.setNetamt(resultSet.getDouble("NET_AMT"));
	scheme.setRate(resultSet.getDouble("RATE"));
	scheme.setSchemecode(resultSet.getString("SCHEMECODE"));
	scheme.setSchemetype(resultSet.getString("SCHTYPE"));
	scheme.setBonus(resultSet.getDouble("Bonus"));
	scheme.setDeduction(resultSet.getDouble("Deductions"));
	scheme.setGenadd(resultSet.getDouble("GenAdd"));
	scheme.setGended(resultSet.getDouble("GenDed"));
	scheme.setGiftvalue(resultSet.getDouble("GiftValue"));
	lstScheme.add(scheme);
	}
	
	return lstScheme;
  }

public List<SalesWeightAdjusted> salesweightadjusted() throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.salesWeightAdjusted());
	lstSalesWeightAdjusted=new ArrayList<SalesWeightAdjusted>();
	while(resultSet.next()) {
	SalesWeightAdjusted salesWeightAdjusted=new SalesWeightAdjusted();
	salesWeightAdjusted.setAdjwastagevalue(resultSet.getDouble("AdjWastageValue"));
	salesWeightAdjusted.setAdjmkcharge(resultSet.getDouble("AdjMkCharge"));
	salesWeightAdjusted.setAdjtaxamount(resultSet.getDouble("AdjTaxAmount"));
	lstSalesWeightAdjusted.add(salesWeightAdjusted);
	}
	return lstSalesWeightAdjusted;
  }

public List<BalanceQuery> balancequery() throws Exception {
	connection=Dbconnection.connection;
	statement=connection.createStatement();
	resultSet=statement.executeQuery(query.balancequery());
	lstBalanceQueries=new ArrayList<BalanceQuery>();
	while(resultSet.next()) {
	BalanceQuery balanceQuery=new BalanceQuery();
	balanceQuery.setCredit(resultSet.getDouble("Credit"));
	balanceQuery.setDebit(resultSet.getDouble("Debit"));
	lstBalanceQueries.add(balanceQuery);
	}
	return lstBalanceQueries;
  }
}
