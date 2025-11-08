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
import com.billprint.model.Repair;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
import com.jilaba.common.NumberToWords;
public class Repairdeliveryprinting implements Printable {
int x=0,y=0,page=1,turn=1,pageheight=330,start=0,sno=1;
List<Repair>lstRepair;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalrepaircharges=0;
public Repairdeliveryprinting() throws Exception {
	Dao dao=new Dao();
	lstRepair=new ArrayList<Repair>();
	lstRepair=dao.repairdetails();
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
			y=repairprint(graphics,pageFormat,y);
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
	private int repairprint(Graphics graphics, PageFormat pageFormat, int y) {
		if(!Common.isRepairprint()) {
			Printheader printheader=new Printheader();
			y=printheader.headerprint(graphics,pageFormat,lstRepair.get(0).getGstbillno(),lstRepair.get(0).getTrandate(),Common.getYpos());
			y=repairheader(graphics,y);
			y=repaircontent(graphics,y);
			y=repairtotal(graphics,y);
			Collection collection=new Collection();
			y=collection.collectionprint(graphics, pageFormat, y);
			Printfooter printfooter=new Printfooter();
			printfooter.footerprint(graphics, pageFormat,lstRepair.get(0).getEmpname(), y);
			if(y>=pageheight) {
				return y;
			}
			
		    Common.setRepairprint(true);
		}
		return y;
	}
	
	private int repairheader(Graphics graphics, int y) {
		if(!Common.isRepairheader()) {
			graphics.setFont(Common.getFontarl1());
			PrintAlignment.printCall(graphics,ApplicationStart.getBilltype(),Common.getXpos1(),Common.getYos4(),TextAlignment.center);
			
			PrintAlignment.printCall(graphics,"SNO",Common.getXpos(),y+=15,TextAlignment.left);
			PrintAlignment.printCall(graphics,"DESCRIPTION",Common.getXpos5()-45,y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"PIECES",Common.getXpos6(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"GROSS WT",Common.getXpos7()+30,y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"NET WT",Common.getXpos8()+40,y,TextAlignment.left);
			PrintAlignment.printCall(graphics,"REPAIRCHARGES",Common.getXpos12()+30,y,TextAlignment.right);
			graphics.setFont(Common.getFontarl4());
			PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7()+37,y+=10,TextAlignment.left);
			PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos8()+40,y,TextAlignment.left);
			graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
			if(y>Common.getYpos()) {
				return y;
			}
			
		    Common.setRepairheader(true);
		}
		return y;
	}
	
	private int repaircontent(Graphics graphics, int y) {
		if(!Common.isRepaircontent()) {
		for (int i = 0; i < lstRepair.size(); i++) {
		    Repair repair=lstRepair.get(i);
		    y+=15;
		    graphics.setFont(Common.getFontarl2());
			PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
			PrintAlignment.printCall(graphics,repair.getProname(),Common.getXpos5()-45,y,TextAlignment.left);	
		
			
			if(repair.getPieces()>0) {
			PrintAlignment.printCall(graphics,String.valueOf(repair.getPieces()),Common.getXpos15(),y,TextAlignment.right);
			}
			
			if(repair.getGrosswt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(repair.getGrosswt()),Common.getXpos16()+30,y,TextAlignment.right);
			}
			
			if(repair.getNetwt()>0) {
			PrintAlignment.printCall(graphics,Common.getWf().format(repair.getNetwt()),Common.getXpos17()+40,y,TextAlignment.right);
			}
		
			
			if(repair.getRepaircharges()>0) {
			PrintAlignment.printCall(graphics,Common.getAf().format(repair.getRepaircharges()),Common.getXpos20()-30,y,TextAlignment.right);
			}
			sno++;
			totalpcs+=repair.getPieces();
			totalgrosswt+=repair.getGrosswt();
			totalnetwt+=repair.getNetwt();
			totalrepaircharges+=repair.getRepaircharges();
			if(y>=pageheight) {
				 start=i+1;
				 return y;
				}
		 }
		 graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		 Common.setRepaircontent(true);
		}
		return y;
	}
	
	private int repairtotal(Graphics graphics, int y) {
		if(!Common.isRepairtotal()) {
		if(y>=pageheight) {
			return y;
		}
		graphics.setFont(Common.getFontarl1());
		PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos5()-45,y+=10,TextAlignment.left);
			
		if(totalpcs>0) {
		PrintAlignment.printCall(graphics,String.valueOf(totalpcs),Common.getXpos15(),y,TextAlignment.right);
		}
			
		if(totalgrosswt>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(totalgrosswt),Common.getXpos16()+30,y,TextAlignment.right);
		}
			
		if(totalnetwt>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(totalnetwt),Common.getXpos17()+40,y,TextAlignment.right);
		}
		
		if(totalrepaircharges>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(totalrepaircharges),Common.getXpos20()-30,y,TextAlignment.right);
		}	
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		
		graphics.setFont(Common.getFontarl2());
		PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos13(),y+=15,TextAlignment.right);
		graphics.setFont(Common.getFonttmr3());
		PrintAlignment.printCall(graphics,"[ Rupees "+NumberToWords.convertNumberToWords(new BigDecimal(Math.round(totalrepaircharges)))+" Only ]",Common.getXpos(),y,TextAlignment.left);
		graphics.setFont(Common.getFontarl2());
		PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12()-20,y,TextAlignment.right);
		graphics.drawLine(Common.getXpos12()-20,y-=10,Common.getXpos20()-25,y);
		PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalrepaircharges)),Common.getXpos20()-30,y+=10,TextAlignment.right);
		graphics.drawLine(Common.getXpos12()-20,y+=5,Common.getXpos20()-25,y);
		Common.setRepairtotal(true);
		}
		return y;
	}

}
