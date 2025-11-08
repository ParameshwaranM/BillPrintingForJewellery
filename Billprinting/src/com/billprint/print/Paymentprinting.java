package com.billprint.print;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import com.billprint.dao.Dao;
import com.billprint.model.Payment;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class Paymentprinting implements Printable {
int x=0,y=0,page=1,turn=1,pageheight=330,start=0;
List<Payment>lstPayment;
public Paymentprinting() throws Exception {
	 Dao dao=new Dao();
	 lstPayment=new ArrayList<Payment>();
	 lstPayment=dao.paymentdetails();
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
			y=paymentprint(graphics,pageFormat,y);
		}turn++;
		return PAGE_EXISTS;
	}
	private int paymentprint(Graphics graphics, PageFormat pageFormat, int y) {
		if(!Common.isPaymentprint()) {
			Printheader printheader=new Printheader();
			y=printheader.headerprint(graphics,pageFormat,lstPayment.get(0).getTranno(),lstPayment.get(0).getTrandate(),Common.getYpos());
			y=paymentheader(graphics,y);
			y=paymentcontent(graphics,y);
			Collection collection=new Collection();
			y=collection.collectionprint(graphics, pageFormat, y);
			y=paymenttotal(graphics,y);
			Printfooter printfooter=new Printfooter();
			printfooter.footerprint(graphics, pageFormat,lstPayment.get(0).getEmpname(), y);
			Common.setPaymentprint(true);
		}
		return y;
	}
	private int paymentheader(Graphics graphics, int y) {
		if(!Common.isPaymentheader()) {
		graphics.setFont(Common.getFontarl1());
		PrintAlignment.printCall(graphics,"PAYMENT VOUCHER",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		
		PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"AMOUNT",Common.getXpos12(),y,TextAlignment.left);
		graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
		Common.setPaymentheader(true);
		}
		return y;
	}
	
	private int paymentcontent(Graphics graphics, int y) {
		if(!Common.isPaymentcontent()) {
		for (int i = 0; i <lstPayment.size(); i++) {
		 Payment payment=lstPayment.get(i);
		 graphics.setFont(Common.getFonttmr3());
		 PrintAlignment.printCall(graphics,"Payment made to",Common.getXpos4(),y+=15,TextAlignment.left);
		 PrintAlignment.printCall(graphics,"Mr./Ms. "+payment.getPartyname(),Common.getXpos4(),y+=15,TextAlignment.left);
		 PrintAlignment.printCall(graphics,"Towards "+payment.getReference()+" Ref No : "+payment.getAgttranno(),Common.getXpos4(),y+=15,TextAlignment.left);
		 PrintAlignment.printCall(graphics,Common.getAf().format(payment.getDebit()),Common.getXpos12()+40,y,TextAlignment.right);
		 graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);	
		}
		Common.setPaymentcontent(true);
		}
		return y;
	}
    
	private int paymenttotal(Graphics graphics, int y) {
		if(!Common.isPaymenttotal()) {
		if(lstPayment.get(0).getTaxableamt()>0) {
		PrintAlignment.printCall(graphics,"Total Advance Amount",Common.getXpos4(),y+=50,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstPayment.get(0).getTaxableamt()),Common.getXpos6()+80,y,TextAlignment.right);	
		
		PrintAlignment.printCall(graphics,"RePaid Amount",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstPayment.get(0).getDebit()),Common.getXpos6()+80,y,TextAlignment.right);	
		
		PrintAlignment.printCall(graphics,"Balance As On Today",Common.getXpos4(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos6(),y,TextAlignment.left);
		
		graphics.drawLine(Common.getXpos7()-30,y-=10,Common.getXpos14()-10,y);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstPayment.get(0).getTaxableamt()-lstPayment.get(0).getDebit()),Common.getXpos6()+80,y+=10,TextAlignment.right);
		graphics.drawLine(Common.getXpos7()-30,y+=5,Common.getXpos14()-10,y);
		}
		Common.setPaymenttotal(true);
		}
		return y;
	}
}
