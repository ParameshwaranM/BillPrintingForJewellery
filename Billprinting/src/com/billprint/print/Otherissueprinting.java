package com.billprint.print;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import com.billprint.dao.Dao;
import com.billprint.model.Otherissue;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class Otherissueprinting implements Printable {
int x=0,y=0,page=1,turn=1,pageheight=330,start=0,sno=1;
List<Otherissue>lstOtherissue;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalamount=0;
public Otherissueprinting() throws Exception {
	    Dao dao=new Dao();
	    lstOtherissue=new ArrayList<Otherissue>();
	    lstOtherissue=dao.otherissuedetails();
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
		 y=otherissueprint(graphics,pageFormat,y);
		 if(y>=pageheight) {
			 turn++;
			 return PAGE_EXISTS;
			} 
		}turn++;
		return PAGE_EXISTS;
	}
	
	private int otherissueprint(Graphics graphics, PageFormat pageFormat, int y) {
	     if(!Common.isOtherissueprint()) {
	     Printheader printheader=new Printheader();
	 	 y=printheader.headerprint(graphics,pageFormat,lstOtherissue.get(0).getTranno(),lstOtherissue.get(0).getTrandate(),Common.getYpos());
	 	 y=otherissueheader(graphics,y);
	 	 y=otherissuecontent(graphics,y);
	 	 y=otherissuetotal(graphics,y);
	 	 Printfooter printfooter=new Printfooter();
		 printfooter.footerprint(graphics, pageFormat,lstOtherissue.get(0).getEmpname(), y);
		 if(y>=pageheight) {
				return y;
			}
	     Common.setOtherissueprint(true);
	     }
		return y;
	}

	private int otherissueheader(Graphics graphics, int y) {
		if(!Common.isOtherissueheader()) {
		PrintAlignment.printCall(graphics,"OTHER ISSUE",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		graphics.setFont(Common.getFontarl1());
		
		PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
		PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos5(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"PIECES",Common.getXpos6()+55,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"GROSS WT",Common.getXpos7()+80,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"NET WT",Common.getXpos8()+100,y,TextAlignment.left);
		PrintAlignment.printCall(graphics,"AMOUNT",Common.getXpos12()+10,y,TextAlignment.left);
		graphics.setFont(Common.getFontarl4());
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7()+87,y+=10,TextAlignment.left);
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos8()+100,y,TextAlignment.left);
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		if(y>Common.getYpos()) {
			return y;
		}
		Common.setOtherissueheader(true);
		}
		return y;
	}

	private int otherissuecontent(Graphics graphics, int y) {
		if(!Common.isOtherissuecontent()) {
		for (int i =start; i <lstOtherissue.size(); i++) {
		  Otherissue otherissue=lstOtherissue.get(i);
		  graphics.setFont(Common.getFontarl2());
			y+=15;
			PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,otherissue.getProname(),Common.getXpos5(),y,TextAlignment.left);	
			
			if(otherissue.getPieces()>0) {
			PrintAlignment.printCall(graphics,String.valueOf(otherissue.getPieces()),Common.getXpos15()+55,y,TextAlignment.right);
			}
			
			if(otherissue.getGrosswt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(otherissue.getGrosswt()),Common.getXpos16()+80,y,TextAlignment.right);
			}
			
			if(otherissue.getNetwt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(otherissue.getNetwt()),Common.getXpos17()+100,y,TextAlignment.right);
			}
			
			if(otherissue.getNetamount()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(otherissue.getNetamount()),Common.getXpos20(),y,TextAlignment.right);
			}
			sno++;
			totalpcs+=otherissue.getPieces();
			totalgrosswt+=otherissue.getGrosswt();
			totalnetwt+=otherissue.getNetwt();
			totalamount+=otherissue.getNetamount();
			 if(y>=pageheight) {
				 start=i+1;
				 return y;
				}
		  }
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		Common.setOtherissuecontent(true);
		}
		return y;
	}
	
	private int otherissuetotal(Graphics graphics, int y) {
		if(!Common.isOtherissuetotal()) {
			if(y>=pageheight) {
				return y;
			}
		graphics.setFont(Common.getFontarl1());
		PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos5(),y+=10,TextAlignment.left);
		
		if(totalpcs>0) {
		PrintAlignment.printCall(graphics,String.valueOf(totalpcs),Common.getXpos15()+55,y,TextAlignment.right);
		}
		
		if(totalgrosswt>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(totalgrosswt),Common.getXpos16()+80,y,TextAlignment.right);
		}
		
		if(totalnetwt>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(totalnetwt),Common.getXpos17()+100,y,TextAlignment.right);
		}
		
		if(totalamount>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(totalamount),Common.getXpos20(),y,TextAlignment.right);
		}
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		Common.setOtherissuetotal(true);
		}
		return y;
	}
}
