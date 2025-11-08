package com.billprint.print;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import com.billprint.dao.Dao;
import com.billprint.model.BalanceQuery;
import com.billprint.model.Receipt;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class ReceiptPrinting implements Printable {
int x=0,y=0,page=1,turn=1,pageheight=330,start=0;
private List<Receipt>lstreceipt=null;
private List<BalanceQuery>lstBalanceQueries=null;
public ReceiptPrinting() throws Exception {
	Dao dao=new Dao();
	lstreceipt=new ArrayList<Receipt>();
	lstreceipt=dao.receiptdetails();
	lstBalanceQueries=new ArrayList<BalanceQuery>();
	lstBalanceQueries=dao.balancequery();
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
			y=receiptprint(graphics,pageFormat,y);
			if(y>=pageheight) {
				 turn++;
				 return PAGE_EXISTS;
				} 
		}turn++;
		return PAGE_EXISTS;
	}
	private int receiptprint(Graphics graphics, PageFormat pageFormat, int y2) {
		if(!Common.isReceiptprint()) {
		Printheader printheader=new Printheader();
		y=printheader.headerprint(graphics,pageFormat,lstreceipt.get(0).getTranno(),lstreceipt.get(0).getTrandate(),Common.getYpos());
		y=receiptheader(graphics,y);
		y=receiptcontent(graphics,y);
		Collection collection=new Collection();
		y=collection.collectionprint(graphics, pageFormat, y);
		y=receipttotal(graphics,y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics, pageFormat,lstreceipt.get(0).getEmpname(), y);
		Common.setReceiptprint(true);
		}
		return y;
	}
	private int receiptheader(Graphics graphics, int y) {
		if(!Common.isReceiptheader()) {
		graphics.setFont(Common.getFontarl1());
		PrintAlignment.printCall(graphics,"RECEIPT VOUCHER",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		
		PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"AMOUNT",Common.getXpos12(),y,TextAlignment.left);
		graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
		Common.setReceiptheader(true);
		}
		return y;
	}
	
	private int receiptcontent(Graphics graphics, int y) {
		
		if(!Common.isReceiptcontent()) {
		for (int i =start; i < lstreceipt.size(); i++) {
		Receipt receipt=lstreceipt.get(i);
		graphics.setFont(Common.getFonttmr3());
		PrintAlignment.printCall(graphics,"Received with thanks from",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Mr./Ms. "+receipt.getPartyname(),Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Towards "+receipt.getReference()+" Ref No : "+receipt.getAgttranno(),Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(receipt.getCredit()),Common.getXpos12()+40,y,TextAlignment.right);
		graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
		}
		Common.setReceiptcontent(true);
		}
		return y;
	}
	
	private int receipttotal(Graphics graphics, int y) {
		if(!Common.isReceipttotal()) {
		if(lstBalanceQueries.size()>0) {
		if(lstBalanceQueries.get(0).getDebit()>0) {
		PrintAlignment.printCall(graphics,"Total Balance Amount",Common.getXpos4(),y+=50,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstBalanceQueries.get(0).getDebit()),Common.getXpos6()+80,y,TextAlignment.right);	
		
		PrintAlignment.printCall(graphics,"Recevied Amount As On Today",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstBalanceQueries.get(0).getCredit()),Common.getXpos6()+80,y,TextAlignment.right);	
		
		PrintAlignment.printCall(graphics,"Balance As On Today",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos6(),y,TextAlignment.left);
		graphics.drawLine(Common.getXpos7()-30,y-=10,Common.getXpos14()-10,y);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstBalanceQueries.get(0).getDebit()-lstBalanceQueries.get(0).getCredit()),Common.getXpos6()+80,y+=10,TextAlignment.right);
		graphics.drawLine(Common.getXpos7()-30,y+=5,Common.getXpos14()-10,y);
		}
		}
		Common.setReceipttotal(true);
		}
		return y;
	}

}
