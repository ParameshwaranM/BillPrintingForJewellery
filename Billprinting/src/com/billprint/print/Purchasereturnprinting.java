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
import com.billprint.model.Purchasereturn;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
import com.jilaba.common.NumberToWords;
public class Purchasereturnprinting implements Printable {
private static List<Purchasereturn>lstPurchasereturn;
int y,start=0,turn=1,page=1,pageheight=330,sno=1;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalwastage=0,totalrate=0,
totalnetamount=0;
public Purchasereturnprinting() {
	 try {
		 lstPurchasereturn=new ArrayList<Purchasereturn>();
		 Dao dao=new Dao();
		 lstPurchasereturn=dao.purchasereturndetails();
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
		 y=purchasereturn(graphics,pageFormat,y);
		 if(y>=pageheight) {
			 turn++;
			 return PAGE_EXISTS;
			} 
		}turn++;
		return PAGE_EXISTS;
	}
	private int purchasereturn(Graphics graphics, PageFormat pageFormat, int y) {
		if(!Common.isPurchasereturn()) {
		Printheader printheader=new Printheader();
		y=printheader.headerprint(graphics, pageFormat,lstPurchasereturn.get(0).getBillno(),lstPurchasereturn.get(0).getBilldate(),y);
		y=purchasereturnheader(graphics,y);
		y=purchasereturncontent(graphics,y);
		y=purchasereturntotal(graphics,y);
		Collection collection=new Collection();
		y=collection.collectionprint(graphics, pageFormat, y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics, pageFormat,lstPurchasereturn.get(0).getEmpname(),y);
		if(y>=pageheight) {
			return y;
		}
		Common.setPurchasereturn(true);
	  }
		return y;
	}
	
	private int purchasereturnheader(Graphics graphics, int y) {
		if(!Common.isPurchasereturnheader()) {
			graphics.setFont(Common.getFontarl1());
			
			PrintAlignment.printCall(graphics,ApplicationStart.getBilltype(),Common.getXpos1(),Common.getYos4(),TextAlignment.center);
			PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
			PrintAlignment.printCall(graphics,"HSNNO",Common.getXpos4(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos5(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"PIECES",Common.getXpos6(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"GROSS WT",Common.getXpos7(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"NET WT",Common.getXpos8(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"WASTAGE",Common.getXpos9(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"RATE",Common.getXpos19()-25,y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"NETAMOUNT",Common.getXpos12()-5,y,TextAlignment.left);
			
			graphics.setFont(Common.getFontarl4());
			PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos6(),y+=10,TextAlignment.left);
			PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7()+20,y,TextAlignment.left);
			graphics.drawLine(Common.getXpos(),y+=15,Common.getXpos3(),y);
			if(y>Common.getYpos()) {
				return y;
			}
			Common.setPurchasereturnheader(true);
		}
		return y;
	}
	
	private int purchasereturncontent(Graphics graphics, int y) {
		if(!Common.isPurchasereturncontent()) {
		  for (int i =start; i < lstPurchasereturn.size(); i++) {
			Purchasereturn purchasereturn=lstPurchasereturn.get(i);
			graphics.setFont(Common.getFontarl2());
			y+=15;
			PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,purchasereturn.getHsncode(),Common.getXpos4(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,purchasereturn.getDescription(),Common.getXpos5(),y,TextAlignment.left);
			
			if(purchasereturn.getPieces()>0) {
			PrintAlignment.printCall(graphics,String.valueOf(purchasereturn.getPieces()),Common.getXpos15(),y,TextAlignment.right);
			}
			
			if(purchasereturn.getGrosswt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchasereturn.getGrosswt()),Common.getXpos16(),y,TextAlignment.right);
			}
			
			if(purchasereturn.getNetwt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchasereturn.getNetwt()),Common.getXpos17(),y,TextAlignment.right);
			}
			
			if(purchasereturn.getWastage()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(purchasereturn.getWastage()),Common.getXpos18(),y,TextAlignment.right);
			}
			
			if(purchasereturn.getRate()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(purchasereturn.getRate()),Common.getXpos19(),y,TextAlignment.right);
			}
			
			if(purchasereturn.getNetamount()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(purchasereturn.getNetamount()),Common.getXpos20(),y,TextAlignment.right);
			}
			sno++;
			totalpcs+=purchasereturn.getPieces();
			totalgrosswt+=purchasereturn.getGrosswt();
			totalnetwt+=purchasereturn.getNetwt();
			totalwastage+=purchasereturn.getWastage();
			totalrate+=purchasereturn.getRate();
			totalnetamount+=purchasereturn.getNetamount();
			 if(y>=pageheight) {
				 start=i+1;
				 return y;
				}
		    }
		    graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
			Common.setPurchasereturncontent(true);
		}
		return y;
	}
	
	private int purchasereturntotal(Graphics graphics, int y) {
		if(!Common.isPurchasereturntotal()) {
			if(y>=pageheight) {
				return y;
			}
			graphics.setFont(Common.getFontarl1());
			PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos5(),y+=15,TextAlignment.left);
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
			
			if(totalrate>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(totalrate),Common.getXpos19(),y,TextAlignment.right);
			}
			
			if(totalnetamount>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(totalnetamount),Common.getXpos20(),y,TextAlignment.right);
			}
			graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
		    graphics.setFont(Common.getFontarl2());
		    PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos13(),y+=15,TextAlignment.right);
		    graphics.setFont(Common.getFonttmr3());
			PrintAlignment.printCall(graphics,"[ Rupees "+NumberToWords.convertNumberToWords(new BigDecimal(Math.round(totalnetamount)))+" Only ]",Common.getXpos(),y,TextAlignment.left);
			graphics.setFont(Common.getFontarl2());
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
			PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalnetamount)),Common.getXpos20(),y+=10,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
			Common.setPurchasereturntotal(true);
		}
		return y;
	}

}
