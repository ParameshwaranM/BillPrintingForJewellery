package com.billprint.print;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import com.billprint.dao.Dao;
import com.billprint.model.Inspection;
import com.billprint.model.InspectionStone;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class Inspectionprinting implements Printable {
int x=0,y=0,page=1,turn=1,pageheight=330,start=0,sno=1;
List<Inspection>lstInspection;
List<InspectionStone>lstInspectionStone;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalamount=0;
public Inspectionprinting() throws Exception {
	Dao dao=new Dao();
	lstInspection=new ArrayList<Inspection>();
	lstInspection=dao.inspectiondetails();
	lstInspectionStone=new ArrayList<InspectionStone>();
	lstInspectionStone=dao.inspectionstndetails();
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
		 y=inspectionprint(graphics,pageFormat,y);
		 if(y>=pageheight) {
			turn++;
			return PAGE_EXISTS;
		 } 
		}turn++;
		return PAGE_EXISTS;
	}
	private int inspectionprint(Graphics graphics, PageFormat pageFormat, int y) {
		if(!Common.isInspectionprint()) {
		Printheader printheader=new Printheader();
		y=printheader.headerprint(graphics,pageFormat,lstInspection.get(0).getGstbillno(),lstInspection.get(0).getTrandate(),Common.getYpos());
		y=inspectionheader(graphics,y);
		y=inspectioncontent(graphics,y);
		y=inspectiontotal(graphics,y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics, pageFormat,lstInspection.get(0).getEmpname(), y);
		if(y>=pageheight) {
			return y;
		}
		Common.setInspectionprint(true);
		}
		return y;
	}
	private int inspectionheader(Graphics graphics, int y) {
		if(!Common.isInspectionheader()) {
			if("I".equalsIgnoreCase(lstInspection.get(0).getIssrec())) {
			PrintAlignment.printCall(graphics,"APPROVAL ISSUE VOUCHER",Common.getXpos1(),Common.getYos4(),TextAlignment.center);
			}
			else {
			PrintAlignment.printCall(graphics,"APPROVAL RECEIPT VOUCHER",Common.getXpos1(),Common.getYos4(),TextAlignment.center);	
			}
			graphics.setFont(Common.getFontarl1());
			graphics.drawLine(Common.getXpos(),y=Common.getYpos3()+130,Common.getXpos3(),y);
			PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
			PrintAlignment.printCall(graphics,"HSNNO",Common.getXpos4(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos5(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"PIECES",Common.getXpos6()+50,y,TextAlignment.left);
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
		Common.setInspectionheader(true);
		}
		return y;
	}

	private int inspectioncontent(Graphics graphics, int y) {
		if(!Common.isInspectioncontent()) {
		for (int i = 0; i < lstInspection.size(); i++) {
			Inspection inspection=lstInspection.get(i);
			
			graphics.setFont(Common.getFontarl2());
			y+=15;
			PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,inspection.getHsncode(),Common.getXpos4(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,inspection.getProname(),Common.getXpos5(),y,TextAlignment.left);	
			
			
			if(inspection.getPieces()>0) {
			PrintAlignment.printCall(graphics,String.valueOf(inspection.getPieces()),Common.getXpos15()+50,y,TextAlignment.right);
			}
			
			if(inspection.getGrosswt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(inspection.getGrosswt()),Common.getXpos16()+80,y,TextAlignment.right);
			}
			
			if(inspection.getNetwt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(inspection.getNetwt()),Common.getXpos17()+100,y,TextAlignment.right);
			}
			
			
			if(inspection.getNetamount()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(inspection.getNetamount()),Common.getXpos20(),y,TextAlignment.right);
			}
			for (int j = 0; j <lstInspectionStone.size(); j++) {
			InspectionStone inspectionStone=lstInspectionStone.get(j);
			if(inspection.getRowsign().equalsIgnoreCase(inspectionStone.getInsrowsign())) {
			y+=15;
			graphics.setFont(Common.getFontarl3());
			PrintAlignment.printCall(graphics,inspectionStone.getStnhsncode(),Common.getXpos11(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,inspectionStone.getStnproname(),Common.getXpos21(),y,TextAlignment.left);
				
			if(inspectionStone.getStnpieces()>0) {
			PrintAlignment.printCall(graphics,String.valueOf(inspectionStone.getStnpieces()),Common.getXpos22(),y,TextAlignment.right);
			}
				
			if(inspectionStone.getStnweight()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(inspectionStone.getStnweight()),Common.getXpos23(),y,TextAlignment.right);
			}
				
			if(inspectionStone.getStnrate()>0) {
		    PrintAlignment.printCall(graphics,Common.getWf().format(inspectionStone.getStnrate()),Common.getXpos9()+15,y,TextAlignment.right);
			}
				
			if(inspectionStone.getStnamount()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(inspectionStone.getStnamount()),Common.getXpos10()+15,y,TextAlignment.right);
			}
		   }
	      }
			sno++;
			totalpcs+=inspection.getPieces();
			totalgrosswt+=inspection.getGrosswt();
			totalnetwt+=inspection.getNetwt();
			totalamount+=inspection.getNetamount();
			 if(y>=pageheight) {
				 start=i+1;
				 return y;
				}
		}
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		Common.setInspectioncontent(true);
		}
		return y;
	}
	
	private int inspectiontotal(Graphics graphics, int y) {
		if(!Common.isInspectiontotal()) {
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
		if(totalamount>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(totalamount),Common.getXpos20(),y,TextAlignment.right);
		}
				
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		if(y>=pageheight) {
			return y;
		}
		Common.setInspectiontotal(true);
		}
		return y;
	}
}
