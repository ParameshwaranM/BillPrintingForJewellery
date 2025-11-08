package com.billprint.print;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.billprint.dao.Dao;
import com.billprint.model.Collectiondetails;
import com.billprint.model.SalesWeightAdjusted;
import com.billprint.model.Scheme;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class Collection {
private List<Collectiondetails>lstCollection;
//private double total=0,debittot,credittot;
private String x;
private List<Scheme>lstScheme=null;
private List<SalesWeightAdjusted>lstSalesWeightAdjusted=null;
private double totadjwastagevalue=0,totadjmkcharge=0,totadjtaxamount=0;
public Collection() {
	try {
		Dao dao=new Dao();
		lstCollection=new ArrayList<Collectiondetails>();
		lstCollection=dao.collectiondetails();
		lstScheme=new ArrayList<Scheme>();
		lstScheme=dao.schemedetails();
		lstSalesWeightAdjusted=new ArrayList<SalesWeightAdjusted>();
		lstSalesWeightAdjusted=dao.salesweightadjusted();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null,e.getMessage());
	}
  }
public int collectionprint(Graphics graphics, PageFormat pageFormat,int y){
	   graphics.setFont(Common.getFontarl1());
	   if(!Common.isCollection()) {
		   
	    for (int i =0; i < lstCollection.size(); i++) {
	    	 if(y>=Common.getPageheight()) {
				 return y;
				}
	    Collectiondetails collectiondetails=lstCollection.get(i);
	    String mode=collectiondetails.getDebit()>0?"Recd":"Paid";
	    x=collectiondetails.getDebit()>0?Common.getAf().format(collectiondetails.getDebit()):Common.getAf().format(collectiondetails.getCredit());
	    if("T".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
	    PrintAlignment.printCall(graphics,collectiondetails.getModename(),Common.getXpos6()+25,y+=15,TextAlignment.right);
	 	PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
	 	x=collectiondetails.getDebit()>0?"-"+Common.getAf().format(collectiondetails.getDebit()):Common.getAf().format(collectiondetails.getCredit());
	 	PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);
	    }
	    
	    if("C".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
	    PrintAlignment.printCall(graphics,collectiondetails.getModename()+" "+mode,Common.getXpos6()+25,y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);
	    }
	    
	    if("H".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
	    PrintAlignment.printCall(graphics,"[ "+collectiondetails.getCardname()+" / "+collectiondetails.getCardorchqnno()
	    +" ] "+collectiondetails.getModename(),Common.getXpos6()+25,y+=15,TextAlignment.right);
	    PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
	    PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);	
	    }
	    
	    if("Q".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
		 PrintAlignment.printCall(graphics,"[ "+collectiondetails.getBankname()+" / "+collectiondetails.getCardorchqnno()+" ] "+
		 collectiondetails.getModename(),Common.getXpos6()+25,y+=15,TextAlignment.right);
		 PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		 PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);	
		 }
	    
	    if("N".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
	     PrintAlignment.printCall(graphics,"[ "+collectiondetails.getBranchname()+" / "+collectiondetails.getCardorchqnno()+" ] "+
	     collectiondetails.getModename(),Common.getXpos6()+25,y+=15,TextAlignment.right);
		 PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		 PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);	
		 }
	    
	    if("CR".equalsIgnoreCase(collectiondetails.getPaymentflag())&&collectiondetails.getCredit()==0) {
		 PrintAlignment.printCall(graphics,collectiondetails.getModename()+"[ "+collectiondetails.getAgttranno()+" ]",Common.getXpos6()+25,y+=15,TextAlignment.right);
		 PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		 PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);	
		 }
	    
	    if("CP".equalsIgnoreCase(collectiondetails.getPaymentflag())&&collectiondetails.getDebit()==0) {
		 PrintAlignment.printCall(graphics,collectiondetails.getModename()+"[ "+collectiondetails.getAgttranno()+" ]",Common.getXpos6()+25,y+=15,TextAlignment.right);
		 PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		 PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);	
		 }
	    
	    if("CA".equalsIgnoreCase(collectiondetails.getPaymentflag())&&collectiondetails.getDebit()!=0) {
		PrintAlignment.printCall(graphics,collectiondetails.getModename()+"[ "+collectiondetails.getAgttranno()+" ]",Common.getXpos6()+25,y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);	
		}
	    
	    if("OA".equalsIgnoreCase(collectiondetails.getPaymentflag())&&!"ORDER RECEIPT".equalsIgnoreCase(ApplicationStart.getBilltype())&&!"ORDER REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())&&collectiondetails.getDebit()!=0) {
		  PrintAlignment.printCall(graphics,collectiondetails.getModename(),Common.getXpos6()+25,y+=15,TextAlignment.right);
		  PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
		  PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);
		  }
	    
//	    if("S".equalsIgnoreCase(collectiondetails.getPaymentflag())&&"SALES".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//			PrintAlignment.printCall(graphics,"[ "+collectiondetails.getSchemecode()+" / "+collectiondetails.getClientid()+" ] "+collectiondetails.getModename()+"AMOUNT",Common.getXpos6()+25,y+=15,TextAlignment.right);
//			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
//			PrintAlignment.printCall(graphics,x,Common.getXpos14(),y,TextAlignment.right);
//			  }
	    
//	    if("CREDIT SALES".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//	    if(!"T".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
//	    	credittot+=collectiondetails.getCredit();
//	    	}
//	    }
//	     
//	    else if("CREDIT PURCHASE REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())||"ADVANCE REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())||"ORDER REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//		   if(!"T".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
//		    credittot+=collectiondetails.getCredit();
//		     }
//		    }
//	    
//	    else if("ORDER RECEIPT".equalsIgnoreCase(ApplicationStart.getBilltype())||"REPAIR RECEIPT".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//		    if(!"T".equalsIgnoreCase(collectiondetails.getPaymentflag())&&!"OA".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
//		    	debittot+=collectiondetails.getDebit();
//		    	credittot+=collectiondetails.getCredit();
//		    	}
//		    }
//	    
//	    else {
//	    if(!"T".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
//	    debittot+=collectiondetails.getDebit();
//	    credittot+=collectiondetails.getCredit();
//	    }
//	    }
	  }
	    
//	    if("CREDIT SALES".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//	    total=credittot;
//	    }
//	    else if("CREDIT PURCHASE REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())||"ADVANCE REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())||"ORDER REPAYMENT".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//	    total=-credittot;	
//	    }
//	    else if("ORDER RECEIPT".equalsIgnoreCase(ApplicationStart.getBilltype())||"REPAIR RECEIPT".equalsIgnoreCase(ApplicationStart.getBilltype())) {
//	    total=debittot+credittot;	
//	    }
//	    else {
//	    total=debittot-credittot;
//	    }
//	    if(y>Common.getPageheight()) {
//		    return y;
//		}
//	    if(total!=0) {
//	    PrintAlignment.printCall(graphics,"Total",Common.getXpos6()+25,y+=15,TextAlignment.right);
//	    PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
//	    graphics.drawLine(Common.getXpos7(),y-=10,Common.getXpos14(),y);
//	    PrintAlignment.printCall(graphics,Common.getAf().format(total),Common.getXpos14(),y+=10,TextAlignment.right);
//	    graphics.drawLine(Common.getXpos7(),y+=5,Common.getXpos14(),y);
//	    }
	    
	    if(lstScheme.size()>0) {
		    for (int i = 0; i < lstScheme.size(); i++) {
			Scheme scheme=lstScheme.get(i);
			if(y>=Common.getPageheight()) {
				 return y;
				}
			if(scheme.getAmtrecd()!=0) {
			PrintAlignment.printCall(graphics,"Scheme Amount ["+scheme.getGroupcode()+"-"+scheme.getMsno()+"]",Common.getXpos6()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(scheme.getAmtrecd()-scheme.getGended()),Common.getXpos14(),y,TextAlignment.right);
			 }
			if(y>=Common.getPageheight()) {
				 return y;
				}
			
			if(scheme.getBonus()!=0) {
			PrintAlignment.printCall(graphics,"Scheme Bonus ["+scheme.getGroupcode()+"-"+scheme.getMsno()+"]",Common.getXpos6()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(scheme.getBonus()),Common.getXpos14(),y,TextAlignment.right);
			}
			if(y>=Common.getPageheight()) {
				 return y;
				}
			
			if(scheme.getGenadd()!=0) {
			PrintAlignment.printCall(graphics,"Scheme Addition ["+scheme.getGroupcode()+"-"+scheme.getMsno()+"]",Common.getXpos6()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(scheme.getGenadd()),Common.getXpos14(),y,TextAlignment.right);
			 }
			if(y>=Common.getPageheight()) {
				 return y;
				}
			if(scheme.getGiftvalue()!=0) {
			PrintAlignment.printCall(graphics,"Scheme Gift Amount ["+scheme.getGroupcode()+"-"+scheme.getMsno()+"]",Common.getXpos6()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos7(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(scheme.getGenadd()),Common.getXpos14(),y,TextAlignment.right);
			 }
			}
		   }
		    
		    if(lstSalesWeightAdjusted.size()>0) {
		    for (int i = 0; i < lstSalesWeightAdjusted.size(); i++) {
		    	if(y>=Common.getPageheight()) {
					 return y;
					}
			SalesWeightAdjusted  salesWeightAdjusted=lstSalesWeightAdjusted.get(i);
			totadjwastagevalue+=salesWeightAdjusted.getAdjwastagevalue();
			totadjmkcharge+=salesWeightAdjusted.getAdjmkcharge();
			totadjtaxamount+=salesWeightAdjusted.getAdjtaxamount();
			}
		    }
		    if(totadjwastagevalue!=0||totadjmkcharge!=0||totadjtaxamount!=0) {
		    PrintAlignment.printCall(graphics,"Scheme Benefits",Common.getXpos5(),y+=15,TextAlignment.right);	
		    }
		    if(y>=Common.getPageheight()) {
				 return y;
				}
		    if(totadjwastagevalue!=0) {
			PrintAlignment.printCall(graphics,"No Wastage Value",Common.getXpos5()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos5()+50,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totadjwastagevalue),Common.getXpos6()+20,y,TextAlignment.right);
			}
		    if(y>=Common.getPageheight()) {
				 return y;
				}
		    if(totadjmkcharge!=0) {
			PrintAlignment.printCall(graphics,"No MkCharge Value",Common.getXpos5()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos5()+50,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totadjmkcharge),Common.getXpos6()+20,y,TextAlignment.right);
			}
		    if(y>=Common.getPageheight()) {
				 return y;
				}
		    if(totadjtaxamount!=0) {
			PrintAlignment.printCall(graphics,"No TaxAmount Value",Common.getXpos5()+25,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos5()+50,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totadjtaxamount),Common.getXpos6()+20,y,TextAlignment.right);
			}
		    Common.setPrintheader(true);
	    Common.setCollection(true);
	  }
	    return y;
   }
}
