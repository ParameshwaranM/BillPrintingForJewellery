package com.billprint.start;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import com.billprint.dao.Dao;
import com.billprint.model.Tranreftable;
import com.billprint.print.Inspectionprinting;
import com.billprint.print.Orderprinting;
import com.billprint.print.Otherissueprinting;
import com.billprint.print.Paymentprinting;
import com.billprint.print.Purchaseprinting;
import com.billprint.print.Purchasereturnprinting;
import com.billprint.print.ReceiptPrinting;
import com.billprint.print.Repairdeliveryprinting;
import com.billprint.print.Salesprinting;
import com.billprint.print.Salesreturnprinting;
import com.jilaba.common.ReturnStatus;
import com.jilaba.fileresource.FileRead;
import com.jilaba.fileresource.JilabaFile;
public class ApplicationStart {
private static String uniquekey, billtype;
private static String ipid="";
private static InetAddress ipname=null;
private static String printername="",billdate="";
private static List<Tranreftable>lSTranreftable=null;
	public static void main(String[] args) {
		try {
		     if(args.length==0) {
		     JOptionPane.showMessageDialog(null,"ARGUMENTS EMPTY!!.");
		     System.exit(0);
		     }
			ReturnStatus returnStatus=null;
			CodeSource codeSource=ApplicationStart.class.getProtectionDomain().getCodeSource();
	        File file=new File(codeSource.getLocation().getPath());
	        FileWriter fileWriter=new FileWriter(file.getParent()+File.separator+"Param.jas");
	        fileWriter.write(args[0].toString());
	        fileWriter.close();
	        
	        FileRead fileRead=new FileRead();
	        
	        returnStatus=fileRead.read(ApplicationStart.class,JilabaFile.SERVER);
	        if(!returnStatus.isStatus()) {
	        	throw new Exception(returnStatus.getDescription());
	        }
	        
	        returnStatus=fileRead.read(ApplicationStart.class,JilabaFile.REGISTER);
	        if(!returnStatus.isStatus()) {
	        	throw new Exception(returnStatus.getDescription());
	        }
	        
	        if(args.length>0) {
	        	String[] arg=args[0].split("\\|");
	        	if(args[0].contains("|")) {
	        		uniquekey=arg[3];
	        	}else {
	        		uniquekey=arg[0];
	        	}
	        }
	        ipname=InetAddress.getLocalHost();
	        ipid=ipname.getHostAddress();
	        new Dbconnection();
	        Dao dao=new Dao();
	        lSTranreftable=new ArrayList<Tranreftable>();
	        lSTranreftable=dao.tranreftable();
	        printername=dao.printername("BillingPrint");
	        if(printername.equalsIgnoreCase("")) {
	        JOptionPane.showMessageDialog(null,"Check The PrinterControl!!.");
	        System.exit(0);
	        }
	        String numberctrlcode=lSTranreftable.get(0).getNumbercontrolcode();
	        if("".equalsIgnoreCase(numberctrlcode)) {
	         JOptionPane.showMessageDialog(null,"NUMBERCONTROLCODE NOT FOUND");
	           }
	        billtype=dao.billtype(numberctrlcode);
	        if("".equalsIgnoreCase(numberctrlcode)) {
		     JOptionPane.showMessageDialog(null,"BILLTYPE NOT FOUND");
		     }
	        billdate=lSTranreftable.get(0).getBilldate();

	        Paper paper=new Paper();
	        paper.setImageableArea(0,0,600,400);
	        PageFormat pageFormat=new PageFormat();
	        pageFormat.setPaper(paper);
	        PrinterJob printerJob=PrinterJob.getPrinterJob();
//	        PrintService [] printService=PrintServiceLookup.lookupPrintServices(null, null);
	        PrintService printService=PrintServiceLookup.lookupDefaultPrintService();
//	        for (PrintService service:printService) {
//			if(service.getName().equalsIgnoreCase(printername)) {
			printerJob.setPrintService(printService);
	        switch (billtype) {
			case "SALES":
				printerJob.setPrintable(new Salesprinting(),pageFormat);
				break;
		    case "SALES RETURN":
			    printerJob.setPrintable(new Salesreturnprinting(),pageFormat);
			    break;
		    case "PURCHASE":
			   printerJob.setPrintable(new Purchaseprinting(), pageFormat);
				break;
		    case "PURCHASE RETURN":
			   printerJob.setPrintable(new Purchasereturnprinting(), pageFormat);
				break;
		    case "CREDIT SALES":
		    case "CUSTOMER ADVANCE":
		    case "GENERAL RECEIPT":
		    case "FURTHER ADVANCE":
				 printerJob.setPrintable(new ReceiptPrinting(), pageFormat);
				  break;
		    case "CREDIT PURCHASE REPAYMENT":
		    case "GENERAL PAYMENT":
		    case "ORDER REPAYMENT":
		    case "ADVANCE REPAYMENT":
		    case "REPAIR ADVANCE":
		    	printerJob.setPrintable(new Paymentprinting(), pageFormat);
		    	  break;
		    case  "INSPECTION ISSUE PROCESS":
		    case  "INSPECTION RECEIPT PROCESS":
		    	printerJob.setPrintable(new Inspectionprinting(), pageFormat);
		    	  break;
		    case  "ORDER RECEIPT":
		    case  "REPAIR RECEIPT":
		    	printerJob.setPrintable(new Orderprinting(), pageFormat);
		    	  break;
		    case  "REPAIR DELIVERY":
		    	printerJob.setPrintable(new Repairdeliveryprinting(), pageFormat);
		    	  break;
		    case  "RE PROCESS ISSUE":
		    case  "ORNAMENT PURCHASE RETURN":
		    	printerJob.setPrintable(new Otherissueprinting(), pageFormat);
		    	  break;
			  }
	        printerJob.setCopies(1);
	        printerJob.print();
		}
	       
	        //JOptionPane.showMessageDialog(null,"PRINT SUCCESSFULL");
		 catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	public static String getBilltype() {
		return billtype;
	}
	public static void setBilltype(String billtype) {
		ApplicationStart.billtype = billtype;
	}
	public static String getUniquekey() {
		return uniquekey;
	}
	public static void setUniquekey(String uniquekey) {
		ApplicationStart.uniquekey = uniquekey;
	}
	public static String getIpid() {
		return ipid;
	}
	public static void setIpid(String ipid) {
		ApplicationStart.ipid = ipid;
	}
	public static String getBilldate() {
		return billdate;
	}
	public static void setBilldate(String billdate) {
		ApplicationStart.billdate = billdate;
	}
	public static InetAddress getIpname() {
		return ipname;
	}
	public static String getPrintername() {
		return printername;
	}
	public static List<Tranreftable> getlSTranreftable() {
		return lSTranreftable;
	}
}
