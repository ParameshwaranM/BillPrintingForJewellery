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
import com.billprint.model.Advancedetails;
import com.billprint.model.Collectiondetails;
import com.billprint.model.Order;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
import com.jilaba.common.NumberToWords;
public class Orderprinting implements Printable {
int x=0,y,page=1,turn=1,pageheight=330,start=0,sno=1;
List<Order>lstOrder;
List<Advancedetails>lstAdvancedetail;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalamount=0,totalsgstamt=0,totalcgstamt=0,totalsgstper=0,totalcgstper=0,
totaligstper=0,totaligstamt=0,totalAmount=0,totaladvance=0,totalwastage=0,totalmkcharge=0;
public Orderprinting() throws Exception {
	Dao dao=new Dao();
	lstOrder=new ArrayList<Order>();
	lstOrder=dao.orderdetails();
	lstAdvancedetail=new ArrayList<Advancedetails>();
	lstAdvancedetail=dao.advancedetails();
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
			y=orderprint(graphics,pageFormat,y);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		if(y>=pageheight) {
			 turn++;
			 return PAGE_EXISTS;
			} 
		}turn++;
		return PAGE_EXISTS;
	}
	private int orderprint(Graphics graphics, PageFormat pageFormat, int y) throws Exception {
		if(!Common.isOrderprint()) {
		Printheader printheader=new Printheader();
		if("".equalsIgnoreCase(ApplicationStart.getBilltype())) {
		y=printheader.headerprint(graphics,pageFormat,lstOrder.get(0).getTranno(),lstOrder.get(0).getTrandate(),Common.getYpos());
		}
		else {
			y=printheader.headerprint(graphics,pageFormat,lstOrder.get(0).getOrderno(),lstOrder.get(0).getOrderdate(),Common.getYpos());	
		}
		y=orderheader(graphics,y);
		y=ordercontent(graphics,y);
		y=ordertotal(graphics,pageFormat,y);
		if(lstAdvancedetail.size()>0) {
		if(lstAdvancedetail.get(0).getCredit()>0) {
		Collection collection=new Collection();
		y=collection.collectionprint(graphics, pageFormat, y);
		}
		}
		y=advancedetail(graphics,pageFormat,y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics, pageFormat,lstOrder.get(0).getEmpname(), y);
		if(y>=pageheight) {
			return y;
		}
		Common.setOrderprint(true);
		}
		return y;
	}
	
	private int orderheader(Graphics graphics, int y) {
		if(!Common.isOrderheader()) {
		PrintAlignment.printCall(graphics,ApplicationStart.getBilltype(),Common.getXpos1()-60,Common.getYos4(),TextAlignment.left);
		graphics.setFont(Common.getFontarl1());
		
		PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos5()-20,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"PIECES",Common.getXpos6(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"GROSS WT",Common.getXpos7(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"NET WT",Common.getXpos8(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"WASTAGE",Common.getXpos9(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"MC",Common.getXpos10(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"AMOUNT",Common.getXpos12()+15,y,TextAlignment.left);
		graphics.setFont(Common.getFontarl4());
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7()+7,y+=10,TextAlignment.left);
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos8(),y,TextAlignment.left);
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		if(y>Common.getYpos()) {
			return y;
		}
		Common.setOrderheader(true);	
		}
		return y;
	}

	private int ordercontent(Graphics graphics, int y) {
		if(!Common.isOrdercontent()) {
		for (int i=start; i <lstOrder.size(); i++) {
		Order order=lstOrder.get(i);
		graphics.setFont(Common.getFontarl2());
		y+=15;
		PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,order.getProname(),Common.getXpos5()-30,y,TextAlignment.left);	
		
		if(order.getPieces()>0) {
		PrintAlignment.printCall(graphics,String.valueOf(order.getPieces()),Common.getXpos15(),y,TextAlignment.right);
		}
		
		if(order.getGrosswt()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(order.getGrosswt()),Common.getXpos16(),y,TextAlignment.right);
		}
		
		if(order.getNetwt()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(order.getNetwt()),Common.getXpos17(),y,TextAlignment.right);
		}
		
		if(order.getPrwastage()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(order.getPrwastage()),Common.getXpos18(),y,TextAlignment.right);
		}
			
		if(order.getPrmkcharge()>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(order.getPrmkcharge()),Common.getXpos19(),y,TextAlignment.right);
		}
		
		if(order.getAmount()>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(order.getAmount()),Common.getXpos20(),y,TextAlignment.right);
		}
		sno++;
		totalpcs+=order.getPieces();
		totalgrosswt+=order.getGrosswt();
		totalnetwt+=order.getNetwt();
		totalamount+=order.getAmount();
		totalsgstamt+=order.getSgstamount();
		totalsgstper=order.getSgstper();
		totalcgstamt+=order.getCgstamount();
		totalcgstper=order.getCgstper();
		totaligstper=order.getIgstper();
		totaligstamt+=order.getIgstamount();
		totalAmount=totalsgstamt+totalcgstamt+totaligstamt+totalamount;
		if(y>=pageheight) {
			 start=i+1;
			 return y;
			}
		}
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		Common.setOrdercontent(true);
		}
		return y;
	}
	
	private int ordertotal(Graphics graphics, PageFormat pageFormat, int y) {
		if(!Common.isOrdertotal()) {
		if(y>=pageheight) {
			return y;
		}
		graphics.setFont(Common.getFontarl1());
		PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos5()-30,y+=10,TextAlignment.left);
				
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
		
		if(totalamount>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(totalamount),Common.getXpos20(),y,TextAlignment.right);
		}
			
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		graphics.setFont(Common.getFontarl2());
		
		if(totalsgstper>0) {
		PrintAlignment.printCall(graphics,"SGST",Common.getXpos13()+10,y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getDf().format(totalsgstper)+"%",Common.getXpos12(),y,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getAf().format(totalsgstamt),Common.getXpos20(),y,TextAlignment.right);
		 }
		
		if(totalcgstper>0) {
		PrintAlignment.printCall(graphics,"CGST",Common.getXpos13()+10,y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getDf().format(totalcgstper)+"%",Common.getXpos12(),y,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getAf().format(totalcgstamt),Common.getXpos20(),y,TextAlignment.right);
		}
		
		if(totaligstper>0) {
		PrintAlignment.printCall(graphics,"IGST",Common.getXpos13()+10,y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getDf().format(totaligstper)+"%",Common.getXpos12(),y,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getAf().format(totaligstamt),Common.getXpos20(),y,TextAlignment.right);
		}
		
		if(totalAmount>0) {
		graphics.setFont(Common.getFonttmr3());
		PrintAlignment.printCall(graphics,"[ Rupees "+NumberToWords.convertNumberToWords(new BigDecimal(Math.round(totalamount)))+" Only ]",Common.getXpos(),y,TextAlignment.left);
		}
		
		if(totalAmount>0) {
		graphics.setFont(Common.getFontarl2());
		PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos13()+10,y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
		graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
		PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalAmount)),Common.getXpos20(),y+=10,TextAlignment.right);
		graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
		}
		
		if("REP".equalsIgnoreCase(lstOrder.get(0).getEntrytype())) {
		if(lstAdvancedetail.size()>0) {
		for (int i = 0; i <lstAdvancedetail.size(); i++) {
			Advancedetails advancedetails=lstAdvancedetail.get(i);
			if(advancedetails.getCatcode()==0&&advancedetails.getCredit()>0) {
			graphics.setFont(Common.getFontarl1());
			PrintAlignment.printCall(graphics,"Repair Advance : "+"Rs. "+Common.getAf().format(advancedetails.getCredit()),Common.getXpos8(),y+=30,TextAlignment.left);
			PrintAlignment.printCall(graphics,"Repair Details :",Common.getXpos8(),y+=15,TextAlignment.left);
			PrintAlignment.printCall(graphics,"Tentative Delivery Date : "+lstOrder.get(0).getDuedate(),Common.getXpos8()+60,y+=15,TextAlignment.left);
		 }
		}
	   }
	  }
		Common.setOrdertotal(true);	
		}
		return y;
	}
	

	private int advancedetail(Graphics graphics, PageFormat pageFormat, int y) throws Exception {
		if(!Common.isOrderothers()) {
		if(lstAdvancedetail.size()>0) {
		if(!"REP".equalsIgnoreCase(lstOrder.get(0).getEntrytype())) {
		if(lstAdvancedetail.get(0).getWeight()==0&&lstAdvancedetail.get(0).getCredit()>0) {
		PrintAlignment.printCall(graphics,"Advance Details :",Common.getXpos5(),y+=30,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Tentative Delivery Date : "+lstOrder.get(0).getDuedate(),Common.getXpos14()+60,y+=20,TextAlignment.right);
		Dao dao=new Dao();
		List<Collectiondetails>lstCollection=new ArrayList<Collectiondetails>();
		lstCollection=dao.collectiondetails();
		for (int i = 0; i <lstCollection.size(); i++) {
			Collectiondetails collectiondetails=lstCollection.get(i);
			if("C".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
			PrintAlignment.printCall(graphics,"ADVANCE CASH RECD : ",Common.getXpos14(),y+=20,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+20,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(collectiondetails.getDebit()),Common.getXpos14()+80,y,TextAlignment.right);
			}
			if("H".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
			PrintAlignment.printCall(graphics,"ADVANCE CREDIT CARD: ",Common.getXpos14()+2,y+=20,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+20,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(collectiondetails.getDebit()),Common.getXpos14()+80,y,TextAlignment.right);
			}
			
			if("Q".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
			PrintAlignment.printCall(graphics,"ADVANCE CHEQUE : ",Common.getXpos14()+2,y+=20,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+20,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(collectiondetails.getDebit()),Common.getXpos14()+80,y,TextAlignment.right);
			}
			
			if("N".equalsIgnoreCase(collectiondetails.getPaymentflag())) {
			PrintAlignment.printCall(graphics,"ADVANCE NETBANKING : ",Common.getXpos14()+2,y+=20,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+20,y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(collectiondetails.getDebit()),Common.getXpos14()+80,y,TextAlignment.right);
			}
			
			totaladvance+=collectiondetails.getDebit();
			
		}
		
		if(totaladvance>0) {
		PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos14(),y+=15,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+20,y,TextAlignment.right);
		graphics.drawLine(Common.getXpos14(),y-=10,Common.getXpos14()+90,y);
		PrintAlignment.printCall(graphics,Common.getAf().format(totaladvance),Common.getXpos14()+80,y+=10,TextAlignment.right);
		graphics.drawLine(Common.getXpos14(),y+=5,Common.getXpos14()+90,y);
		}
		}
		else if (lstAdvancedetail.get(0).getWeight()>0&&lstAdvancedetail.get(0).getCredit()>0) {
		PrintAlignment.printCall(graphics,"Advance Details :",Common.getXpos5(),y+=30,TextAlignment.left);
		PrintAlignment.printCall(graphics,"Tentative Delivery Date : "+lstOrder.get(0).getDuedate(),Common.getXpos14()+60,y+=20,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Advance Weight : ",Common.getXpos14()+2,y+=20,TextAlignment.right);
		PrintAlignment.printCall(graphics,Common.getWf().format(lstAdvancedetail.get(0).getWeight())+"/Grams",Common.getXpos14()+3,y,TextAlignment.left);
		
		PrintAlignment.printCall(graphics,"In Amount : ",Common.getXpos14()+2,y+=20,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+3,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstAdvancedetail.get(0).getCredit()),Common.getXpos14()+80,y,TextAlignment.right);
		
		PrintAlignment.printCall(graphics,"Balance Amount To Pay : ",Common.getXpos14()+2,y+=20,TextAlignment.right);
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos14()+3,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,Common.getAf().format(lstOrder.get(0).getAmount()-lstAdvancedetail.get(0).getCredit()),Common.getXpos14()+80,y,TextAlignment.right);
		}
		}
		}
		Common.setOrderothers(true);
		}
		return y;
	}
}
