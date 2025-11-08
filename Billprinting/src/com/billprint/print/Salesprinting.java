package com.billprint.print;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.billprint.dao.Dao;
import com.billprint.model.DiscountMaster;
import com.billprint.model.Purchase;
import com.billprint.model.Receipt;
import com.billprint.model.Sales;
import com.billprint.model.Salesreturn;
import com.billprint.model.Salestonedetails;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
import com.jilaba.common.NumberToWords;
public class Salesprinting implements Printable {
int x=0,y=0,page=1,turn=1,pageheight=330,start=0,sno=1;
private List<Sales>lstSales=null;
private List<Salestonedetails>lststndetails=null;
private List<DiscountMaster>lstDiscountMaster=null;
//private List<Collection>lstCollection=null;
private List<Receipt>lstreceipt=null;
private int totalpcs=0;
private double adjdiscount=0;
private double totaladjdiscount=0;
private double totalgrosswt=0,totalnetwt=0,totalwastage=0,totalmkcharge=0,
totalnetamount=0,totalsgstamt=0,totalcgstamt=0,totalsgstper=0,totalcgstper=0,
totaligstper=0,totaligstamt=0,totalamount=0,totaldiscount=0;
//private double totaladdchg=0,totaladdchgtax,totalothcharges=0;
public Salesprinting() throws Exception {
	Dao dao=new Dao();
	lstSales=new ArrayList<Sales>();
	lstSales=dao.salesdetails();
	lststndetails=new ArrayList<Salestonedetails>();
	lststndetails=dao.salesstndetails();
	lstDiscountMaster=new ArrayList<DiscountMaster>();
	lstDiscountMaster=dao.discountmaster();
	lstreceipt=new ArrayList<Receipt>();
	lstreceipt=dao.receiptdetails();
  }
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D graphics2d=(Graphics2D)graphics;
		graphics2d.translate(pageFormat.getImageableX(),pageFormat.getImageableY());
		if(y>pageheight&&turn%2!=0) {
			page++;
		}
		if(page==pageIndex) {
			return NO_SUCH_PAGE;
		}
		if(turn%2==0) {
		 try {
			y=Common.getYpos1();
			y=salesprint(graphics,pageFormat,y);
		} catch (Exception e) {
//		JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		 if(y>=pageheight) {
			 turn++;
			 return PAGE_EXISTS;
			} 
		}turn++;
		
		return PAGE_EXISTS;
	   }
	private  int salesprint(Graphics graphics,PageFormat pageFormat,int y) throws Exception {
		if(!Common.isSales()) {
	    Printheader printheader=new Printheader();
		y=printheader.headerprint(graphics,pageFormat,lstSales.get(0).getGstbillno(),lstSales.get(0).getTrandate(),Common.getYpos());
		y=salesheader(graphics,y);
		y=salescontent(graphics,y);
		y=salestotal(graphics,pageFormat,y);
		Collection collection=new Collection();
		y=collection.collectionprint(graphics, pageFormat, y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics, pageFormat,lstSales.get(0).getEmpname(), y);
		if(y>=pageheight) {
			return y;
		}
		
		Common.setSales(true);
		}
		return y;
	}
	private int salesheader(Graphics graphics, int y) {
		if(!Common.isSalesheader()) {
		if("T".equalsIgnoreCase(lstSales.get(0).getSalestype())) {
			PrintAlignment.printCall(graphics,"TO BE SALES ["+lstreceipt.get(0).getAgttranno()+"]",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		   }
		
		else if("T".equalsIgnoreCase(lstSales.get(0).getSalestype())&&"CR".equalsIgnoreCase(lstSales.get(0).getEntrytype())) {
		PrintAlignment.printCall(graphics,"CREDIT SALES ["+lstreceipt.get(0).getAgttranno()+"]",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		 }
		
		if("SS".equalsIgnoreCase(lstSales.get(0).getEntrytype())) {
			PrintAlignment.printCall(graphics,"SALES AND SALES RETURN ",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
			}
		else if("SP".equalsIgnoreCase(lstSales.get(0).getEntrytype())) {
			PrintAlignment.printCall(graphics,"SALES AND PURCHASE",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
			}
		else if(!"".equalsIgnoreCase(lstSales.get(0).getOrderno())) {
			PrintAlignment.printCall(graphics,"ORDER DELIVERY",Common.getXpos1()-60,Common.getYos4()-17,TextAlignment.left);
			graphics.setFont(Common.getFontarl3());
			PrintAlignment.printCall(graphics,"ORDERNO : "+lstSales.get(0).getOrderno(),Common.getXpos1()-60,Common.getYos4()-5,TextAlignment.left);
			PrintAlignment.printCall(graphics,"ORDERDATE : "+lstSales.get(0).getOrderdate(),Common.getXpos1()-60,Common.getYos4()+5,TextAlignment.left);
			PrintAlignment.printCall(graphics,"ORDERRATE : "+Common.getAf().format(lstSales.get(0).getOrderrate())+"/Grams",Common.getXpos1()-60,Common.getYos4()+15,TextAlignment.left);
			}
		else if(!"CR".equalsIgnoreCase(lstSales.get(0).getEntrytype())) {
			PrintAlignment.printCall(graphics,ApplicationStart.getBilltype()+" "+"BILL",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
			}
		graphics.setFont(Common.getFontarl1());
		
		PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"HSNNO",Common.getXpos4(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos5(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"PIECES",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"GROSS WT",Common.getXpos7(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"NET WT",Common.getXpos8(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rate",Common.getXpos9(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Value",Common.getXpos10(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"NETAMOUNT",Common.getXpos12()-5,y,TextAlignment.left);
		
		graphics.setFont(Common.getFontarl4());
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7()+7,y+=10,TextAlignment.left);
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos8(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Addition",Common.getXpos10(),y,TextAlignment.left);
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		if(y>Common.getYpos()) {
			return y;
		}
		Common.setSalesheader(true);
		}
		return y;	
	}
	
	private int salescontent(Graphics graphics, int y) {
	  if(!Common.isSalescontent()) {
	   for (int i =start; i < lstSales.size(); i++) {
		Sales sales=lstSales.get(i);
		graphics.setFont(Common.getFontarl2());
		y+=15;
		PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,sales.getHsncode(),Common.getXpos4(),y,TextAlignment.left);
		if("N".equalsIgnoreCase(sales.getInspectiontype())&&"T".equalsIgnoreCase(sales.getSalestype())) {
		PrintAlignment.printCall(graphics,"***"+sales.getProname(),Common.getXpos5(),y,TextAlignment.left);	
		}
		else {
		PrintAlignment.printCall(graphics,sales.getProname(),Common.getXpos5(),y,TextAlignment.left);	
		}
		
		if(sales.getPieces()>0) {
		PrintAlignment.printCall(graphics,String.valueOf(sales.getPieces()),Common.getXpos15(),y,TextAlignment.right);
		}
		
		if(sales.getGrosswt()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(sales.getGrosswt()),Common.getXpos16(),y,TextAlignment.right);
		}
		
		if(sales.getNetwt()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(sales.getNetwt()),Common.getXpos17(),y,TextAlignment.right);
		}
		
//		if(sales.getWastage()>0) {
//		PrintAlignment.printCall(graphics,Common.getWf().format(),Common.getXpos18(),y,TextAlignment.right);
//		}
//		
		if(sales.getRate()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(sales.getRate()),Common.getXpos18(),y,TextAlignment.right);
		}
		if(sales.getMkcharge()>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(sales.getMkcharge()+sales.getWastage()),Common.getXpos19(),y,TextAlignment.right);
		}
		
		if(lstDiscountMaster.size()>0) {
			for (int k = 0; k < lstDiscountMaster.size(); k++) {
			DiscountMaster discountMaster=lstDiscountMaster.get(k);
			if(sales.getRowsign().equalsIgnoreCase(discountMaster.getRowsign())) {
				adjdiscount=discountMaster.getAdjdiscount();
				totaldiscount+=discountMaster.getAdjdiscount();
			  }
			 }
			}
		
		if(sales.getNetamount()>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(sales.getNetamount()+sales.getOveralldiscount()+sales.getBulkdiscount()+sales.getDiscount()+adjdiscount),Common.getXpos20(),y,TextAlignment.right);
		}
		
		if(sales.getGrosswt()!=sales.getTagwt()&&sales.getTagwt()!=0) {
		graphics.setFont(Common.getFontarl4());
		PrintAlignment.printCall(graphics,"Partly Wt :"+Common.getWf().format(sales.getTagwt()-sales.getGrosswt()),Common.getXpos5(),y+=10,TextAlignment.left);
		}
		
		if(sales.getOthercharge()!=0) {
		graphics.setFont(Common.getFontarl4());
		PrintAlignment.printCall(graphics,"HMC :"+Common.getAf().format(sales.getOthercharge()),Common.getXpos5(),y+=10,TextAlignment.left);
		}
		
		sno++;
		
		for (int j = 0; j <lststndetails.size(); j++) {
		Salestonedetails stonedetails=lststndetails.get(j);
		if(sales.getRowsign().equalsIgnoreCase(stonedetails.getSalerowsign())) {
		y+=15;
		graphics.setFont(Common.getFontarl3());
		PrintAlignment.printCall(graphics,stonedetails.getStnhsncode(),Common.getXpos11(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,stonedetails.getStnproname(),Common.getXpos21(),y,TextAlignment.left);
		
		if(stonedetails.getStnpieces()>0) {
		PrintAlignment.printCall(graphics,String.valueOf(stonedetails.getStnpieces()),Common.getXpos22(),y,TextAlignment.right);
		}
		
		if(stonedetails.getStnweight()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(stonedetails.getStnweight()),Common.getXpos23(),y,TextAlignment.right);
		}
		
		if(stonedetails.getStnrate()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(stonedetails.getStnrate()),Common.getXpos9(),y,TextAlignment.right);
		}
		
		if(stonedetails.getStnamount()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(stonedetails.getStnamount()),Common.getXpos10(),y,TextAlignment.right);
		}
	  }
	}  
		totalpcs+=sales.getPieces();
		totalgrosswt+=sales.getGrosswt();
		totalnetwt+=sales.getNetwt();
		totalwastage+=sales.getWastage();
		totalmkcharge+=sales.getMkcharge();
		totalnetamount+=sales.getNetamount();
		totalsgstamt+=sales.getSgstamt();
		totalsgstper=sales.getSgstper();
		totalcgstamt+=sales.getCgstamt();
		totalcgstper=sales.getCgstper();
		totaligstper=sales.getIgstper();
		totaligstamt+=sales.getIgstamt();
//		totalothcharges+=sales.getOthercharge();
//		totaladdchg+=sales.getAddchg();
//		totaladdchgtax+=sales.getAddchgtax();
		totaldiscount+=sales.getBulkdiscount()+sales.getOveralldiscount()+sales.getDiscount();
		totalamount=totalsgstamt+totalcgstamt+totaligstamt+totalnetamount;
		 if(y>=pageheight) {
			 start=i+1;
			 return y;
			}
		}
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		Common.setSalescontent(true);
		}
		return y;
	}
	
	private int salestotal(Graphics graphics, PageFormat pageFormat,int y) throws Exception {
		if(!Common.isSalestotal()) {
			if(y>=pageheight) {
				return y;
			}
//			System.out.println(y);
			graphics.setFont(Common.getFontarl1());
			PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos5(),y+=10,TextAlignment.left);
			
			if(totalpcs>0) {
			PrintAlignment.printCall(graphics,String.valueOf(totalpcs),Common.getXpos15(),y,TextAlignment.right);
			}
			if(totalgrosswt>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totalgrosswt),Common.getXpos16(),y,TextAlignment.right);
			}
			
			if(totalnetwt>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totalnetwt),Common.getXpos17(),y,TextAlignment.right);
			}
			
			if(totalwastage>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totalwastage),Common.getXpos18(),y,TextAlignment.right);
			}
			
			if(totalmkcharge>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(totalmkcharge),Common.getXpos19(),y,TextAlignment.right);
			}
			
			if(totalnetamount>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(totalnetamount),Common.getXpos20(),y,TextAlignment.right);
			}
			graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
			
//			if(totalothcharges>0) {
//			PrintAlignment.printCall(graphics,"OTHERCHARGES Rs.",Common.getXpos13()-50,y+=15,TextAlignment.left);
//			PrintAlignment.printCall(graphics,Common.getAf().format(totalothcharges),Common.getXpos3(),y,TextAlignment.right);
//			}
//			
//			if(totaladdchg>0) {
//			PrintAlignment.printCall(graphics,"ADDITIONALCHARGES Rs.",Common.getXpos13()-75,y+=15,TextAlignment.left);
//			PrintAlignment.printCall(graphics,Common.getAf().format(totaladdchg+totaladdchgtax),Common.getXpos3(),y,TextAlignment.right);
//			}
			
			if(totaldiscount>0) {
			PrintAlignment.printCall(graphics,"DISCOUNT Rs.",Common.getXpos13()+27,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totaldiscount+totaladjdiscount),Common.getXpos20(),y,TextAlignment.right);	
			}
			if(totaldiscount>0) {
			PrintAlignment.printCall(graphics,"TOTAL Rs.",Common.getXpos13()+27,y+=15,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
			PrintAlignment.printCall(graphics,Common.getAf().format(totalnetamount),Common.getXpos20(),y+=10,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
			}
			Common.setSalestotal(true);
		}
			if(!Common.isSaTaxPrint()) {
//				System.out.println(y);
				if(y>=pageheight) {
					return y;
				}
			graphics.setFont(Common.getFontarl2());
			if(totalsgstper>0) {
			PrintAlignment.printCall(graphics,"SGST",Common.getXpos13()+27,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getDf().format(totalsgstper)+"%",Common.getXpos12()+10,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totalsgstamt),Common.getXpos20(),y,TextAlignment.right);
			 }
			
			if(totalcgstper>0) {
			PrintAlignment.printCall(graphics,"CGST",Common.getXpos13()+27,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getDf().format(totalcgstper)+"%",Common.getXpos12()+10,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totalcgstamt),Common.getXpos20(),y,TextAlignment.right);
			}
			
			if(totaligstper>0) {
			PrintAlignment.printCall(graphics,"IGST",Common.getXpos13()+27,y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getDf().format(totaligstper)+"%",Common.getXpos12()+10,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totaligstamt),Common.getXpos20(),y,TextAlignment.right);
			}
			graphics.setFont(Common.getFonttmr3());
			PrintAlignment.printCall(graphics,"[ Rupees "+NumberToWords.convertNumberToWords(new BigDecimal(Math.round(totalamount)))+" Only ]",Common.getXpos(),y,TextAlignment.left);
			
			graphics.setFont(Common.getFontarl2());
			PrintAlignment.printCall(graphics,"TOTAL Rs.",Common.getXpos13()+27,y+=15,TextAlignment.right);
			//PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12()+10,y,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
			PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount)),Common.getXpos20(),y+=10,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
			
		Common.setSaTaxPrint(true);
			}
		    if(!Common.isSalesothers()) {
			Dao dao=new Dao();
			List<Salesreturn>lstsalesreturn=new ArrayList<Salesreturn>();
			lstsalesreturn=dao.salesreturndetails();
			List<Purchase>lstpurchase=new ArrayList<Purchase>();
			lstpurchase=dao.purchasedetails();
			if(lstSales.size()>0&&lstsalesreturn.size()>0&&lstpurchase.size()==0) {
				Common.setSalesheader(true);
				graphics.setFont(Common.getFontarl1());
				if(!Common.isSrheading()) {
			    PrintAlignment.printCall(graphics,"SALES RETURN / Refer Billno : "+lstsalesreturn.get(0).getBillno()+" / "+lstsalesreturn.get(0).getBilldate(),Common.getXpos(),y+=10,TextAlignment.left);
			    Common.setSrheading(true);
				}
				if(y>=pageheight) {
					return y;
				}
				Salesreturnprinting salesreturnprinting=new Salesreturnprinting();
				y=salesreturnprinting.salesreturnheader(graphics, y);
				if(y>=pageheight) {
					return y;
				}
				y=salesreturnprinting.salesreturncontent(graphics, y);
				if(y>=pageheight) {
					return y;
				}
				y=salesreturnprinting.salesreturntotal(graphics, y);
				if(y>=pageheight) {
					return y;
				}
				graphics.setFont(Common.getFontarl1());
				if(!Common.isSrsasalesamount()) {
				PrintAlignment.printCall(graphics,"SALES AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount)),Common.getXpos3(),y,TextAlignment.right);
				Common.setSrsasalesamount(true);
				}
				if(y>=pageheight) {
					return y;
				}
				PrintAlignment.printCall(graphics,"SALES RETURN AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(Salesreturnprinting.getTotalamount())),Common.getXpos3(),y,TextAlignment.right);
				if(y>=pageheight) {
					return y;
				}
				PrintAlignment.printCall(graphics,"TOTAL Rs.",Common.getXpos13(),y+=15,TextAlignment.right);
				//PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
				graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount-Salesreturnprinting.getTotalamount())),Common.getXpos3(),y+=10,TextAlignment.right);
				graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
			}
			
			if(lstpurchase.size()>0&&lstSales.size()>0&&lstsalesreturn.size()==0) {
				graphics.setFont(Common.getFontarl1());
			    PrintAlignment.printCall(graphics," URD PURCHASE NO : "+lstpurchase.get(0).getTranno(),Common.getXpos(),y+=10,TextAlignment.left);
			    Purchaseprinting purchaseprinting=new Purchaseprinting();
			    System.out.println(y);
			    y=purchaseprinting.purchaseheader(graphics, y);
			    y=purchaseprinting.purchasecontent(graphics, y);
			    y=purchaseprinting.purchasetotal(graphics, y);
			    if(y>=pageheight) {
			    	return y;
			    }
			    
			    graphics.setFont(Common.getFontarl1());
				PrintAlignment.printCall(graphics,"SALES AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount)),Common.getXpos3(),y,TextAlignment.right);
				if(y>=pageheight) {
			    	return y;
			    }
				PrintAlignment.printCall(graphics,"PURCHASE AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(Purchaseprinting.getTotalnetamount())),Common.getXpos3(),y,TextAlignment.right);
				if(y>=pageheight) {
			    	return y;
			    }
				PrintAlignment.printCall(graphics,"TOTAL Rs.",Common.getXpos13(),y+=15,TextAlignment.right);
				//PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
				graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount-Purchaseprinting.getTotalnetamount())),Common.getXpos3(),y+=10,TextAlignment.right);
				graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
			}
			
			if(lstpurchase.size()>0&&lstSales.size()>0&&lstsalesreturn.size()>0) {
				graphics.setFont(Common.getFontarl1());
			    PrintAlignment.printCall(graphics,"SALES RETURN / Refer Billno : "+lstsalesreturn.get(0).getBillno()+" / "+lstsalesreturn.get(0).getBilldate(),Common.getXpos(),y+=10,TextAlignment.left);
				Salesreturnprinting salesreturnprinting=new Salesreturnprinting();
				y=salesreturnprinting.salesreturnheader(graphics, y);
				y=salesreturnprinting.salesreturncontent(graphics, y);
				y=salesreturnprinting.salesreturntotal(graphics, y);
				
				graphics.setFont(Common.getFontarl1());
				PrintAlignment.printCall(graphics," URD PURCHASE NO : "+lstpurchase.get(0).getTranno(),Common.getXpos(),y+=10,TextAlignment.left);
			    Purchaseprinting purchaseprinting=new Purchaseprinting();
			    y=purchaseprinting.purchaseheader(graphics, y);
			    y=purchaseprinting.purchasecontent(graphics, y);
			    y=purchaseprinting.purchasetotal(graphics, y);
			    if(y>=pageheight) {
			    	return y;
			    }
			    
			    graphics.setFont(Common.getFontarl1());
				PrintAlignment.printCall(graphics,"SALES AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount)),Common.getXpos3(),y,TextAlignment.right);
				if(y>=pageheight) {
			    	return y;
			    }
				PrintAlignment.printCall(graphics,"SALES RETURN AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(Salesreturnprinting.getTotalamount())),Common.getXpos3(),y,TextAlignment.right);
				if(y>=pageheight) {
			    	return y;
			    }
				PrintAlignment.printCall(graphics,"PURCHASE AMOUNT",Common.getXpos13(),y+=15,TextAlignment.right);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(Purchaseprinting.getTotalnetamount())),Common.getXpos3(),y,TextAlignment.right);
				if(y>=pageheight) {
			    	return y;
			    }
				PrintAlignment.printCall(graphics,"TOTAL Rs.",Common.getXpos13(),y+=15,TextAlignment.right);
				//PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
				graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
				PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount-Salesreturnprinting.getTotalamount()-Purchaseprinting.getTotalnetamount())),Common.getXpos3(),y+=10,TextAlignment.right);
				graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y); 
				
			}
			if(y>=pageheight) {
				return y;
			}
			Common.setSalesothers(true);
		    }
		return y;
	}	
}
