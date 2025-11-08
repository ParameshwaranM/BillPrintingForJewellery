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
import com.billprint.model.Purchase;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
import com.jilaba.common.NumberToWords;
public class Purchaseprinting implements Printable {
private List<Purchase>lstPurchase;
int x,y,pageheight=330,start=0,page=1,turn=1,sno=1;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalwastage=0,totaldustwt=0,
totalrate=0;
private static double totalnetamount=0;
public Purchaseprinting() {
    try {
    	Dao dao=new Dao();
    	lstPurchase=new ArrayList<Purchase>();
    	lstPurchase=dao.purchasedetails();
     } catch (Exception e) {
	  JOptionPane.showMessageDialog(null, e.getMessage());
   }	
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
		y=Common.getYpos1();
		y=purchaseprint(graphics,pageFormat,y);	
		if(y>=pageheight) {
		turn++;
		return PAGE_EXISTS;
		} 
		}turn++;
		return PAGE_EXISTS;
	 }
	public int purchaseprint(Graphics graphics, PageFormat pageFormat, int y) {
		if(!Common.isPurchaseprint()) {
		Printheader printheader=new Printheader();
		y=printheader.headerprint(graphics, pageFormat,lstPurchase.get(0).getBillno(),lstPurchase.get(0).getBilldate(), y);
		PrintAlignment.printCall(graphics,ApplicationStart.getBilltype()+" "+"BILL",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		y=purchaseheader(graphics,y);
		y=purchasecontent(graphics,y);
		y=purchasetotal(graphics,y);
		Collection collection=new Collection();
		y=collection.collectionprint(graphics, pageFormat, y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics, pageFormat,lstPurchase.get(0).getEmpname(),y);
		if(y>=pageheight) {
			return y;
		}
		Common.setPurchaseprint(true);
		}
		return y;
	}
	
	public int purchaseheader(Graphics graphics, int y) {
	  if(!Common.isPurchaseheader()) {
		graphics.setFont(Common.getFontarl1());
		if(lstPurchase.get(0).getEntrytype().equalsIgnoreCase("SP")) {
			graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
		}
		if(y>=pageheight) {
			return y;
		}
		PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos4(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"PIECES",Common.getXpos5()+40,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"GROSS WT",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"NET WT",Common.getXpos7()+20,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"WASTAGE",Common.getXpos8()+20,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"DUSTWT",Common.getXpos9()+20,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"RATE",Common.getXpos19()-20,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"AMOUNT",Common.getXpos12()+15,y,TextAlignment.left);
		
		graphics.setFont(Common.getFontarl4());
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos6(),y+=10,TextAlignment.left);
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7()+20,y,TextAlignment.left);
		graphics.drawLine(Common.getXpos(),y+=15,Common.getXpos3(),y);	
		if(y>Common.getYpos()) {
			return y;
		}
		Common.setPurchaseheader(true);
		 }
		return y;
	}
	
	public int purchasecontent(Graphics graphics, int y) {
	   if(!Common.isPurchasecontent()) {
		for (int i =start; i < lstPurchase.size(); i++) {
			Purchase purchase=lstPurchase.get(i);
			if(y>=pageheight) {
//				 start=i+1;
				 return y;
				}
			graphics.setFont(Common.getFontarl2());
			y+=15;
			PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,purchase.getDescription(),Common.getXpos4(),y,TextAlignment.left);
			
			if(purchase.getPieces()>0) {
			PrintAlignment.printCall(graphics,String.valueOf(purchase.getPieces()),Common.getXpos5()+50,y,TextAlignment.left);
			}
			
			if(purchase.getGrosswt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchase.getGrosswt()),Common.getXpos15()+30,y,TextAlignment.right);
			}
			
			if(purchase.getNetwt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchase.getNetwt()),Common.getXpos16()+10,y,TextAlignment.right);
			}
			
			if(purchase.getWastage()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchase.getWastage()),Common.getXpos17()+25,y,TextAlignment.right);
			}
			
			if(purchase.getDustwt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchase.getDustwt()),Common.getXpos18()+20,y,TextAlignment.right);
			}
			
			if(purchase.getRate()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(purchase.getRate()),Common.getXpos19()+10,y,TextAlignment.right);
			}
			
			if(purchase.getNetamount()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(purchase.getNetamount()),Common.getXpos20(),y,TextAlignment.right);
			}
			sno++;
			totalpcs+=purchase.getPieces();
			totalgrosswt+=purchase.getGrosswt();
			totalnetwt+=purchase.getNetwt();
			totalwastage+=purchase.getWastage();
			totaldustwt+=purchase.getDustwt();
			totalrate+=purchase.getRate();
			totalnetamount+=purchase.getNetamount();
			if(y>=pageheight) {
				 start=i+1;
				 return y;
				}
		     }
		    graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		    Common.setPurchasecontent(true);
	    }
		return y;
	}
	public int purchasetotal(Graphics graphics, int y) {
		if(!Common.isPurchasetotal()) {
			if(y>=pageheight) {
				return y;
			}
			graphics.setFont(Common.getFontarl1());
			PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos4(),y+=10,TextAlignment.left);
			
			if(totalpcs>0) {
			PrintAlignment.printCall(graphics,String.valueOf(totalpcs),Common.getXpos5()+50,y,TextAlignment.left);
			}
			
			if(totalgrosswt>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totalgrosswt),Common.getXpos15()+30,y,TextAlignment.right);
			}
			
			if(totalnetwt>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totalnetwt),Common.getXpos16()+10,y,TextAlignment.right);
			}
			
			if(totalwastage>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totalwastage),Common.getXpos17()+25,y,TextAlignment.right);
			}
			
			if(totaldustwt>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(totaldustwt),Common.getXpos18()+20,y,TextAlignment.right);
			}
			
			if(totalrate>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(totalrate),Common.getXpos19()+10,y,TextAlignment.right);
			}
			
			if(totalnetamount>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalnetamount)),Common.getXpos20(),y,TextAlignment.right);
			}
			graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
			graphics.setFont(Common.getFontarl2());
		    PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos13(),y+=15,TextAlignment.right);
		    
		    graphics.setFont(Common.getFonttmr3());
			PrintAlignment.printCall(graphics,"[ Rupees "+NumberToWords.convertNumberToWords(new BigDecimal(totalnetamount))+" Only ]",Common.getXpos(),y,TextAlignment.left);
			
			graphics.setFont(Common.getFontarl2());
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
			PrintAlignment.printCall(graphics,Common.getAf().format(totalnetamount),Common.getXpos20(),y+=10,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
		    Common.setPurchasetotal(true);
		}
		return y;
	}
	public static double getTotalnetamount() {
		return totalnetamount;
	}
	public static void setTotalnetamount(double totalnetamount) {
		Purchaseprinting.totalnetamount = totalnetamount;
	}
}
