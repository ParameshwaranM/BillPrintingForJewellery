package com.billprint.query;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Dbconnection;
public class Query {
public String dbname() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(CompId,'')AS COMPID,ISNULL(MASTERDB,'')AS MASTERDB,\n");
	sb.append("ISNULL(TranDB,'')AS TRANDB, ISNULL(STOCKDB,'')AS STOCKDB,\n");
	sb.append("ISNULL(SchemeDB,'')AS SCHEMEDB, ISNULL(CatLogDB,'')AS CATLOGDB\n");
	sb.append("from filemain as a left join computer as b on a.CompId=b.CompCode\n");
	sb.append("where IPAdd='"+ApplicationStart.getIpid()+"'\n");
	sb.append("and finyearfromdate between FromDate and ToDate\n");
	return sb.toString();
   }

public String printername(String printname)throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT isnull(PrinterName,'')as PrinterName FROM "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.printercontrol\n");
	sb.append("WHERE SystemIP='"+ApplicationStart.getIpid()+"'\n");
	sb.append("and DESCRIPTION='"+printname+"'");
	return sb.toString();
   }

public String tranreftable()throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT Numbercontrolcode,Trandate from\n"+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.tranreftable\n");
	sb.append("where uniquekey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
   }

public String billtype(String numbercontrolcode)throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("select TypeName from\n"+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.NumberControl\n");
	sb.append("where ctrl_code='"+numbercontrolcode+"'");
	return sb.toString();
   }

public String companydetails() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.COMPNAME,'')AS COMPANYNAME,ISNULL(A.ADD1,'')AS ADD1,\n");
	sb.append("ISNULL(A.ADD2,'')AS ADD2,ISNULL(A.PINCODE,'')AS PINCODE,\n");
	sb.append("ISNULL(A.PHONE,'')AS PHONENO,ISNULL(A.CITY,'')AS CITY,\n");
	sb.append("ISNULL(B.GSTNO,'')AS GSTNO,ISNULL(C.DESCRIPTION,'')AS STATE from company AS A\n");
	sb.append("LEFT JOIN  CompanyAddlDetails AS B ON A.COMPID=B.COMPID\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.StateMaster as c on a.statecode=c.StateCode\n");
	sb.append("where A.compid='"+Dbconnection.getLstDbname().get(0).getCompid()+"'");
	return sb.toString();
   }
public String customerdetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(CustomerName,'')AS CUSTOMERNAME,ISNULL(Address1,'')AS ADDRESS1,\n");
	sb.append("ISNULL(Address2,'')AS ADDRESS2,ISNULL(Area,'')AS AREA ,ISNULL(City,'')AS CITY,\n");
	sb.append("ISNULL(State,'')AS STATE,ISNULL(MobileNo,'')AS MOBILENO,ISNULL(GSTNo,'') AS GSTNO,ISNULL(ProofName,'')AS ProofName,ISNULL(ProofNumber,'')AS ProofNumber from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.customer as a\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.CustomerProof as b on a.rowsign=b.RowSign\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.ProofMaster as c on b.ProofCode=c.ProofCode\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
   }
public String ratedetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("select Rate,MetalCode from\n"+Dbconnection.getLstDbname().get(0).getMasterdb()+"..RateMaster\n");
	sb.append("where purity in(91.6) and MetalCode in('g','s') and rategroup in(select max(rategroup) from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.RateMaster where\n ");
	sb.append("Date='"+ApplicationStart.getBilldate()+"')and Date='"+ApplicationStart.getBilldate()+"' order by metalcode");
	return sb.toString();
   }
public String salesdetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT a.rate,ISNULL(A.HSNCode,'')AS HSNCODE,ISNULL(A.TranDate,'') AS TRANDATE,ISNULL(A.ProCode,'')AS PROCODE,\n");
	sb.append("ISNULL(A.Pieces,'')AS PIECES,ISNULL(A.GrossWt,'')AS GROSSWT,ISNULL(A.NetWt,'')AS NETWT,\n");
	sb.append("ISNULL(A.Purity,'')AS PURITY,ISNULL(A.MkCharge,'') AS MKCHARGE,ISNULL(A.TotalAmount,'') AS TOTALAMOUNT,\n");
	sb.append("ISNULL(A.GSTBillNo,'')AS GSTBILLNO,ISNULL(A.SGSTPer,'')AS SGSTPER,\n");
	sb.append("ISNULL(A.SGSTAmt,'')AS SGSTAMT,ISNULL(A.CGSTPer,'')AS CGSTPER,ISNULL(A.CGSTAmt,'')AS CGSTAMT,\n");
	sb.append("ISNULL(A.IGSTPer,'')AS IGSTPER,ISNULL(A.IGSTAmt,'')AS IGSTAMT,ISNULL(A.Discount,'')AS DISCOUNT,\n");
	sb.append("ISNULL(A.NetAmount,'')AS NETAMOUNT,ISNULL(B.proname,'')AS PRONAME,ISNULL(a.Wastage,'')AS WASTAGE,\n");
	sb.append("ISNULL(A.rowsign,'')AS ROWSIGN,ISNULL(c.EmpName,'')AS EMPNAME,ISNULL(A.TAGWT,'')AS TAGWT,\n");
	sb.append("ISNULL(D.INSPECTIONTYPE,'')AS INSPECTIONTYPE,ISNULL(A.ENTRYTYPE,'')AS ENTRYTYPE,ISNULL(A.ORDERNO,'')AS ORDERNO,\n");
	sb.append("ISNULL(E.ORDERDATE,'')AS ORDERDATE ,E.RATE AS ORDERRATE,ISNULL(A.OTHERCHARGE,'')AS OTHERCHARGE,Isnull(SalesType,'')as SalesType, \n");
	sb.append("ISNULL(A.AddChg,'')AS ADDCHG,ISNULL(A.AddChgTax,'')AS ADDCHGTAX,ISNULL(A.OverallDiscount,0)AS OverallDiscount,ISNULL(A.BulkDiscount,0)AS BulkDiscount  FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+ ".dbo.sales as a \n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.product as b\n");
	sb.append("on a.procode=b.procode\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.EMPLOYEE as c\n");
	sb.append("on a.SalemanCode=c.EmpUId\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.INSPECTION as D\n");
	sb.append("on D.SaleROWSIGN=A.ROWSIGN\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.ordermast as E\n");
	sb.append(" on a.orderno=E.orderno\n");
	sb.append(" where A.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
   }
public String salesstndetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(b.Pieces,'')AS STNPIECES,ISNULL(b.Weight,'')AS STNWEIGHT,\n");
	sb.append("ISNULL(b.Rate,'')AS STNRATE,ISNULL(b.Amount,'')AS STNAMOUNT,\n");
	sb.append("ISNULL(b.hsncode,'')AS STNHSNCODE,ISNULL(b.SaleRowSign,'')AS SALEROWSIGN,\n");
	sb.append("ISNULL(c.ProName,'')AS STNPRONAME from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.salesstudstone as b\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.sales as a on  a.RowSign=b.SaleRowSign\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Product as c \n");
	sb.append("on b.StnCode=c.ProCode where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
  }
public String collectiondetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(DEBIT,'')AS DEBIT,ISNULL(CREDIT,'')AS CREDIT,ISNULL(PAYMENTFLAG,'')AS PAYMENTFLAG,\n");
	sb.append("ISNULL(CardName,'')AS CARDNAME, ISNULL(MODEName,'')AS MODENAME,ISNULL(CardOrChqNo,'')AS CardOrChqNo,\n");
	sb.append("ISNULL(BRANCHNAME,'')AS BRANCHNAME,ISNULL(BankNAME,'')AS BANKNAME,\n");
	sb.append("ISNULL(SCHEMECODE,'')AS SCHEMECODE,ISNULL(CLIENTID,'')AS CLIENTID,ISNULL(AgtTranNo,'')AS AgtTranNo from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.collection AS A\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.CREDITCARD AS B ON A.CardCode =B.CardCode\n");
	sb.append(" LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+"..PAYMENTMODE AS C ON A.PAYMENTFLAG =C.MODECODE\n");
	sb.append("where paymentflag  in('C','H','Q','C','N','CR','CP','OA','S','CA')and\n");
	sb.append("UniqueKey='"+ApplicationStart.getUniquekey()+"'order by paymentflag desc");
	return sb.toString();
  }
public String salesretrundetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT entrytype,ISNULL(A.HSNCode,'')AS HSNCODE,ISNULL(A.TranDate,'') AS TRANDATE,ISNULL(A.ProCode,'')AS PROCODE,\n");
	sb.append("ISNULL(A.Pieces,'')AS PIECES,ISNULL(A.GrossWt,'')AS GROSSWT,ISNULL(A.NetWt,'')AS NETWT,\n");
	sb.append("ISNULL(A.Purity,'')AS PURITY,ISNULL(A.MkCharge,'') AS MKCHARGE,ISNULL(A.TotalAmount,'') AS TOTALAMOUNT,\n");
	sb.append("ISNULL(A.GSTBillNo,'')AS GSTBILLNO,ISNULL(A.SGSTPer,'')AS SGSTPER,\n");
	sb.append("ISNULL(A.SGSTAmt,'')AS SGSTAMT,ISNULL(A.CGSTPer,'')AS CGSTPER,ISNULL(A.CGSTAmt,'')AS CGSTAMT,\n");
	sb.append("ISNULL(A.IGSTPer,'')AS IGSTPER,ISNULL(A.IGSTAmt,'')AS IGSTAMT,ISNULL(A.Discount,'')AS DISCOUNT,\n");
	sb.append("ISNULL(A.NetAmount,'')AS NETAMOUNT,ISNULL(B.proname,'')AS PRONAME,ISNULL(a.Wastage,'')AS WASTAGE,\n");
	sb.append("ISNULL(A.rowsign,'')AS ROWSIGN,ISNULL(c.EmpName,'')AS EMPNAME,\n");
	sb.append("ISNULL(A.BILLNO,'')AS BILLNO,ISNULL(A.BILLDATE,'')AS BILLDATE FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+ ".dbo.salesreturn as a\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.product as b\n");
	sb.append("on a.procode=b.procode\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.EMPLOYEE as c\n");
	sb.append("on a.SalemanCode=c.EmpUId\n");
	sb.append(" where A.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
  }
public String salesreturnstone() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(b.Pieces,'')AS STNPIECES,ISNULL(b.Weight,'')AS STNWEIGHT,\n");
	sb.append("ISNULL(b.Rate,'')AS STNRATE,ISNULL(b.Amount,'')AS STNAMOUNT,\n");
	sb.append("ISNULL(b.hsncode,'')AS STNHSNCODE,ISNULL(b.SaleretRowSign,'')AS SALEretROWSIGN,\n");
	sb.append("ISNULL(c.ProName,'')AS STNPRONAME from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.salesreturnstudstone as b\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.salesreturn as a on  a.RowSign=b.SaleretRowSign\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Product\n");
	sb.append("as c on b.StnCode=c.ProCode where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
  }
public String purchasedetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT entrytype,ISNULL(A.HSNCode,'')AS HSNCODE,ISNULL(A.DESCRIPTION,'')AS DESCRIPTION,\n");
	sb.append("ISNULL(A.PIECES,'')AS PIECES,ISNULL(A.GROSSWT,'')AS GROSSWT,\n");
	sb.append("ISNULL(A.NETWT,'')AS NETWT,ISNULL(A.DUSTWT,'')AS DUSTWT,\n");
	sb.append("ISNULL(A.WASTAGE,'')AS WASTAGE,ISNULL(A.NETAMOUNT,'')AS NETAMOUNT,\n");
	sb.append("ISNULL(A.RATE,'')AS RATE,ISNULL(A.ROWSIGN,'')AS ROWSIGN,ISNULL(A.TRANNO,'')AS TRANNO,\n");
	sb.append("ISNULL(B.EMPNAME,'')AS EMPNAME,ISNULL(a.TRANDATE,'')AS TRANDATE,ISNULL(A.GSTBillNo,'')AS GSTBILLNO from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.CUSTOMERPURCHASE AS A\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Employee AS B ON A.SaleManCode=B.EmpUId\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
  }

public String purchasereturndetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.HSNCode,'')AS HSNCODE,ISNULL(C.PRONAME,'')AS DESCRIPTION,\n");
	sb.append("ISNULL(A.PIECES,'')AS PIECES,ISNULL(A.GROSSWT,'')AS GROSSWT,\n");
	sb.append("ISNULL(A.NETWT,'')AS NETWT,\n");
	sb.append("ISNULL(A.WASTAGE,'')AS WASTAGE,ISNULL(A.NETAMOUNT,'')AS NETAMOUNT,\n");
	sb.append("ISNULL(A.RATE,'')AS RATE,ISNULL(A.ROWSIGN,'')AS ROWSIGN,\n");
	sb.append("ISNULL(B.Empname,'')AS EMPNAME,ISNULL(a.TRANDATE,'')AS TRANDATE,ISNULL(A.GSTBillNo,'')AS GSTBILLNO from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+"..CUSTOMERPURCHASERETURN AS A\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Employee AS B ON A.SaleManCode=B.EmpUId\n");
	sb.append("left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.product\n");
	sb.append("as C on a.procode=C.procode\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();	
   }

public String receiptdetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.PARTYNAME,'')AS PARTYNAME,ISNULL(A.CREDIT,0)AS CREDIT,\n");
	sb.append("ISNULL(A.TRANNO,'')AS TRANNO,ISNULL(A.TRANDATE,'')AS TRANDATE,\n");
	sb.append("ISNULL(B.REFERENCE,'') AS REFERENCE,ISNULL(A.AGTTRANNO,'') AS AGTTRANNO,ISNULL(c.Empname,'')AS EMPNAME FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.AmountOutStanding AS A\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.ACCREFMASTER AS B ON A.REFCODE=B.REFCODE\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Employee AS C ON A.SaleManCode=c.EmpUId\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
  }

public String paymentdetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.PARTYNAME,'')AS PARTYNAME,ISNULL(sum(A.CREDIT),0)AS CREDIT,\n");
	sb.append("ISNULL(Sum(A.DEBIT),0)AS DEBIT,ISNULL(A.TRANNO,'')AS TRANNO,ISNULL(A.TRANDATE,'')AS TRANDATE,\n");
	sb.append("ISNULL(B.REFERENCE,'') AS REFERENCE,ISNULL(A.AGTTRANNO,'') AS AGTTRANNO,\n");
	sb.append("ISNULL(D.Empname,'')AS EMPNAME,ISNULL(sum(C.TAXABLEAMT),0) as TAXABLEAMT FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.AmountOutStanding AS A\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.ACCREFMASTER AS B ON A.REFCODE=B.REFCODE\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.AdvanceDetail AS C ON C.OutRowSign=A.RowSign\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Employee AS D ON C.SaleManCode=D.EmpUId\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'\n");
	sb.append("Group by a.TranNo,a.TranDate,a.AgtTranNo,d.EmpName,a.PartyName,b.Reference");
	//System.out.println(sb);
	return sb.toString();
  }

public String inspectiondetails() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(a.HSNCode,'')AS HSNCODE,ISNULL(a.TranDate,'') AS TRANDATE,ISNULL(a.ProCode,'')AS PROCODE,\n");
	sb.append("ISNULL(a.Pieces,'')AS PIECES,ISNULL(a.GrossWt,'')AS GROSSWT,ISNULL(a.NetWt,'')AS NETWT,\n");
	sb.append("ISNULL(a.Purity,'')AS PURITY,ISNULL(a.TotalAmount,'') AS TOTALAMOUNT,\n");
	sb.append("ISNULL(a.GSTBillNo,'')AS GSTBILLNO,ISNULL(a.SGSTPer,'')AS SGSTPER,\n");
	sb.append("ISNULL(a.SGSTAmt,'')AS SGSTAMT,ISNULL(a.CGSTPer,'')AS CGSTPER,ISNULL(a.CGSTAmt,'')AS CGSTAMT,\n");
	sb.append("ISNULL(a.IGSTPer,'')AS IGSTPER,ISNULL(a.IGSTAmt,'')AS IGSTAMT,ISNULL(a.Discount,'')AS DISCOUNT,\n");
	sb.append("ISNULL(a.NetAmount,'')AS NETAMOUNT,ISNULL(b.proname,'')AS PRONAME,ISNULL(a.ISSREC,'')AS ISSREC,\n");
	sb.append("ISNULL(c.EmpName,'')AS EMPNAME,ISNULL(a.rowsign,'')AS ROWSIGN FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.inspection as a\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.product as b on a.procode=b.procode\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Employee as c on a.SalemanCode=c.EmpUId\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
 }

public String inspectionstonedetails() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(b.Pieces,'')AS STNPIECES,ISNULL(b.Weight,'')AS STNWEIGHT,\n");
	sb.append("ISNULL(b.Rate,'')AS STNRATE,ISNULL(b.Amount,'')AS STNAMOUNT,\n");
	sb.append("ISNULL(b.hsncode,'')AS STNHSNCODE,ISNULL(b.INSROWSIGN,'')AS INSROWSIGN,\n");
	sb.append("ISNULL(c.ProName,'')AS STNPRONAME from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.InspectionStudStone as b\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.Inspection as a on  a.RowSign=b.INSROWSIGN\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.Product as c \n");
	sb.append("on b.StnCode=c.ProCode where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
 }

public String orderdetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.ORDERNO,'')AS ORDERNO,ISNULL(A.ORDERDATE,'')AS ORDERDATE,\n");
	sb.append("ISNULL(A.ENTRYTYPE,'')AS ENTRYTYPE,ISNULL(A.PIECES,'')AS PIECES,\n");
	sb.append("ISNULL(A.GROSSWT,'')AS GROSSWT,ISNULL(A.NETWT,'')AS NETWT,\n");
	sb.append("ISNULL(A.RATE,'')AS RATE,ISNULL(A.AMOUNT,'')AS AMOUNT,\n");
	sb.append("ISNULL(A.SGSTPER,'')AS SGSTPER,ISNULL(A.SGSTAMOUNT,'')AS SGSTAMOUNT,\n");
	sb.append("ISNULL(A.CGSTPER,'')AS CGSTPER,ISNULL(A.CGSTAMOUNT,'')AS CGSTAMOUNT,\n");
	sb.append("ISNULL(A.IGSTPER,'')AS IGSTPER,ISNULL(A.IGSTAMOUNT,'')AS IGSTAMOUNT,\n");
	sb.append("ISNULL(A.ROWSIGN,'')AS ROWSIGN,ISNULL(B.PRONAME,'')AS PRONAME,\n");
	sb.append("ISNULL(C.EMPNAME,'')AS EMPNAME,ISNULL(A.DUEDATE,'')AS DUEDATE,\n");
	sb.append("ISNULL(A.PRWASTAGE,'')AS PRWASTAGE,ISNULL(A.PRMKCHARGE,'')AS PRMKCHARGE\n");
	sb.append("FROM "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.ORDERMAST AS A\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.PRODUCT AS B ON A.PROCODE=B.PROCODE\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.EMPLOYEE AS C ON A.SALMANCODE=C.EMPUID\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
    }

public String repairdetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.TRANNO,'')AS TRANNO,ISNULL(A.TRANDATE,'')AS TRANDATE,ISNULL(B.PRONAME,'')AS PRONAME,\n");
	sb.append("ISNULL(A.PIECES,'')AS PIECES,ISNULL(A.GROSSWT,'')AS GROSSWT,ISNULL(A.NETWT,'')AS NETWT,\n");
	sb.append("ISNULL(A.MKCHARGE,'')AS REPAIRCHARGES,ISNULL(C.EMPNAME,'')AS EMPNAME,ISNULL(A.ORDERNO,'')AS ORDERNO,\n");
	sb.append("ISNULL(A.GSTBILLNO ,'')AS GSTBILLNO,ISNULL(A.ROWSIGN,'')AS ROWSIGN FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.SALES AS A");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.PRODUCT AS B ON A.PROCODE=B.PROCODE\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.EMPLOYEE AS C ON A.SALEMANCODE=C.EMPUID\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
   }

public String otherissue() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(B.PRONAME,'')AS PRONAME,ISNULL(PIECES,'')AS PIECES,\n");
	sb.append("ISNULL(GROSSWT,'')AS GROSSWT,ISNULL(NETWT,'')AS NETWT,ISNULL(NETAMOUNT,'')AS NETAMOUNT,\n");
	sb.append("ISNULL(C.EMPNAME,'')AS EMPNAME,ISNULL(A.TranDate,'') AS TRANDATE,ISNULL(A.DEPTTRANNO,'')AS TRANNO FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.DEPARTMENTTRANSFER AS A");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.PRODUCT AS B ON A.PROCODE=B.PROCODE\n");
	sb.append(" left join "+Dbconnection.getLstDbname().get(0).getMasterdb()+".dbo.EMPLOYEE AS C ON A.EMPUID=C.EMPUID\n");
	sb.append("where a.UniqueKey='"+ApplicationStart.getUniquekey()+"'and issrec='I'");
	return sb.toString();
    }

public String advancedetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT CREDIT,WEIGHT,CATCODE FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.AmountOutStanding where UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
    }

public String schemedetails() throws Exception{
	StringBuilder sb=new StringBuilder();
	sb.append("SELECT ISNULL(A.ID,0)AS ID,ISNULL(A.MSNO,0)AS MSNO,ISNULL(A.GROUPCODE,'')AS GROUPCODE,\n");
	sb.append("ISNULL(A.SCHEMECODE,'')AS SCHEMECODE,ISNULL(A.AMT_RECD,0)AS AMT_RECD,\n");
	sb.append("ISNULL(A.NET_AMT,0)AS NET_AMT,ISNULL(A.ACCWT,0)AS ACCWT,ISNULL(A.SCHTYPE,'')AS SCHTYPE,\n");
	sb.append("ISNULL(A.RATE,0)AS RATE,ISNULL(B.CLIENTID,'')AS CLIENTID,ISNULL(A.CLOSINGTYPEAMTWT,'')AS CLOSINGTYPEAMTWT,\n");
	sb.append("ISNULL(A.Bonus,0)AS Bonus,ISNULL(Deductions,0)AS Deductions,ISNULL(GiftValue,0)AS GiftValue,\n");
	sb.append("ISNULL(GenAdd,0)AS GenAdd,ISNULL(GenDed,0)AS GenDed FROM\n");
	sb.append(Dbconnection.getLstDbname().get(0).getSchemedb()+".dbo.SLIPCLOSE AS A\n");
	sb.append("LEFT JOIN "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.SALESSLIPADJUSTED AS B ON A.ID=B.SLIPNO\n");
	sb.append("where B.UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	//System.out.println(sb);
	return sb.toString();
   }

public String salesWeightAdjusted() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("select ISNULL(AdjWastageValue,0)as AdjWastageValue,isnull(AdjMkCharge,0)as AdjMkCharge,\n");
	sb.append("isnull(AdjTaxAmount,0)as AdjTaxAmount from "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.SalesWeightAdjusted where UniqueKey='"+ApplicationStart.getUniquekey()+"'and disccode=0");
	return sb.toString();
}

public String discountmasterdetails() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("select ISNULL(Discount,0)as Discount,ISNULL(RowSign,'')as RowSign from "+Dbconnection.getLstDbname().get(0).getTrandb()+".dbo.SalesWeightAdjusted\n");
	sb.append("where UniqueKey='"+ApplicationStart.getUniquekey()+"'");
	return sb.toString();
   }

public String balancequery() throws Exception {
	StringBuilder sb=new StringBuilder();
	sb.append("select Sum(debit) as Debit,Sum(Credit) as Credit From\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo. AmountOutStanding\n");
	sb.append("where AgtTranNo in (select AgtTranNo from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo. AmountOutStanding where UniqueKey='"+ApplicationStart.getUniquekey()+"')\n");
	sb.append("and TranDate <= (select TranDate from\n");
	sb.append(Dbconnection.getLstDbname().get(0).getTrandb()+".dbo. AmountOutStanding where UniqueKey='"+ApplicationStart.getUniquekey()+"')and TranType in ('CR','TB')");
	return sb.toString();
   }
}
