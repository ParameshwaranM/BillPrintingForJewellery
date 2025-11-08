package com.billprint.print;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.billprint.dao.Dao;
import com.billprint.model.Salesreturn;
import com.billprint.model.Salesreturnstone;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
import com.jilaba.common.NumberToWords;
public class Salesreturnprinting implements Printable {
private  List<Salesreturn>lstsalesreturn;
List<Salesreturnstone>lstsalereturnstone;
int x=0,y=0,page=1,turn=1,pageheight=330,start=0,sno=1;
private int totalpcs=0;
private double totalgrosswt=0,totalnetwt=0,totalwastage=0,totalmkcharge=0,
totalnetamount=0,totalsgstamt=0,totalcgstamt=0,totalsgstper=0,totalcgstper=0,
totaligstper=0,totaligstamt=0;
private static double  srgrosswt=0,srnetwt=0,srmkcharge=0,srwastage=0,srnetamount=0,srsgstamt=0,srcgstamt=0,srigstamt=0;
private static int i,srpcs,srsno;
private static double totalamount=0;
public Salesreturnprinting() throws Exception {
	 Dao dao=new Dao();
	 lstsalesreturn=new ArrayList<Salesreturn>();
	 lstsalesreturn=dao.salesreturndetails();
	 lstsalereturnstone=new ArrayList<Salesreturnstone>();
	 lstsalereturnstone=dao.salesreturnstone();
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
		 y=salesreturnprint(graphics,pageFormat,y);
		 if(y>=pageheight) {
			 turn++;
			 return PAGE_EXISTS;
			} 
		}turn++;
		return PAGE_EXISTS;
	}
	public  int salesreturnprint(Graphics graphics,PageFormat pageFormat,int y) {
		if(!Common.isSalesreturn()) {
		Printheader printheader=new Printheader();
		y=printheader.headerprint(graphics, pageFormat,lstsalesreturn.get(0).getGstbillno(), lstsalesreturn.get(0).getTrandate(),y);
		PrintAlignment.printCall(graphics,ApplicationStart.getBilltype(),Common.getXpos1(),Common.getYos4(),TextAlignment.center);
		y=salesreturnheader(graphics,y);
		y=salesreturncontent(graphics,y);
		y=salesreturntotal(graphics,y);
		Collection collection=new Collection();
		y=collection.collectionprint(graphics, pageFormat, y);
		Printfooter printfooter=new Printfooter();
		printfooter.footerprint(graphics,pageFormat,lstsalesreturn.get(0).getEmpname(),y);
		if(y>=pageheight) {
			return y;
		}
		
		Common.setSalesreturn(true);
		}
		return y;
	}
	public int salesreturnheader(Graphics graphics, int y) {
		if(!Common.isSalesreturnheader()) {
		graphics.setFont(Common.getFontarl1());
		if(lstsalesreturn.get(0).getEntrytype().equalsIgnoreCase("SS")) {
			graphics.drawLine(Common.getXpos(),y+=10,Common.getXpos3(),y);
		}
		if(y>=pageheight) {
			return y;
		}
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
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos7(),y+=10,TextAlignment.left);
		PrintAlignment.printCall(graphics,"(In Grams)",Common.getXpos8(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics, "Addition", Common.getXpos10(), y, TextAlignment.left);
		
		graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		if(y>Common.getYpos()) {
			return y;
		}
		Common.setSalesreturnheader(true);
		}
		return y;
	}
	public int salesreturncontent(Graphics graphics, int y) {
		if(!Common.isSalesreturncontent()) {
		if(getI()>0) {
			start=getI();
			sno=getSrsno();
			totalpcs=getSrpcs();
			totalgrosswt=getSrgrosswt();
			totalnetwt=getSrnetwt();
			totalwastage=getSrwastage();
			totalmkcharge=getSrmkcharge();
			totalnetamount=getSrnetamount();
			totalcgstamt=getSrcgstamt();
			totaligstamt=getSrigstamt();
			totalsgstamt=getSrsgstamt();
		}
		for (int i = start; i <lstsalesreturn.size(); i++) {
		Salesreturn salesreturn=lstsalesreturn.get(i);
		
		graphics.setFont(Common.getFontarl2());
		y+=15;
		PrintAlignment.printCall(graphics,String.valueOf(sno),Common.getXpos(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,salesreturn.getHsncode(),Common.getXpos4(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,salesreturn.getProname(),Common.getXpos5(),y,TextAlignment.left);
		
		if(salesreturn.getPieces()>0) {
		PrintAlignment.printCall(graphics,String.valueOf(salesreturn.getPieces()),Common.getXpos15(),y,TextAlignment.right);
		 }
		
		if(salesreturn.getGrosswt()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(salesreturn.getGrosswt()),Common.getXpos16(),y,TextAlignment.right);
		}
		
		if(salesreturn.getNetwt()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(salesreturn.getNetwt()),Common.getXpos17(),y,TextAlignment.right);
		}
			
		if(salesreturn.getWastage()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(salesreturn.getWastage()),Common.getXpos18(),y,TextAlignment.right);
		  }
			
		if(salesreturn.getMkcharge()>0) {
		PrintAlignment.printCall(graphics,Common.getAf().format(salesreturn.getMkcharge()),Common.getXpos19(),y,TextAlignment.right);
		  }
		PrintAlignment.printCall(graphics,Common.getAf().format(salesreturn.getNetamount()),Common.getXpos20(),y,TextAlignment.right);
		sno++;
		
		for (int j =0; j <lstsalereturnstone.size(); j++) {
		Salesreturnstone salesreturnstone=lstsalereturnstone.get(j);
		if(salesreturn.getRowsign().equalsIgnoreCase(salesreturnstone.getSaleretrowsign())) {
	    y+=15;
		graphics.setFont(Common.getFontarl3());
		PrintAlignment.printCall(graphics,salesreturnstone.getStnhsncode(),Common.getXpos11(),y,TextAlignment.left);
		PrintAlignment.printCall(graphics,salesreturnstone.getStnproname(),Common.getXpos21(),y,TextAlignment.left);
			
		if(salesreturnstone.getStnpieces()>0) {
		PrintAlignment.printCall(graphics,String.valueOf(salesreturnstone.getStnpieces()),Common.getXpos22(),y,TextAlignment.right);
		 }
		
		if(salesreturnstone.getStnweight()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(salesreturnstone.getStnweight()),Common.getXpos23(),y,TextAlignment.right);
		}
		
		if(salesreturnstone.getStnrate()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(salesreturnstone.getStnrate()),Common.getXpos9(),y,TextAlignment.right);
		}
		
		if(salesreturnstone.getStnamount()>0) {
		PrintAlignment.printCall(graphics,Common.getWf().format(salesreturnstone.getStnamount()),Common.getXpos10(),y,TextAlignment.right);
		 }
	    }
	   }
		totalpcs+=salesreturn.getPieces();
		totalgrosswt+=salesreturn.getGrosswt();
		totalnetwt+=salesreturn.getNetwt();
		totalwastage+=salesreturn.getWastage();
		totalmkcharge+=salesreturn.getMkcharge();
		totalnetamount+=salesreturn.getNetamount();
		totalsgstamt+=salesreturn.getSgstamt();
		totalsgstper=salesreturn.getSgstper();
		totalcgstamt+=salesreturn.getCgstamt();
		totalcgstper=salesreturn.getCgstper();
		totaligstper=salesreturn.getIgstper();
		totaligstamt+=salesreturn.getIgstamt();
		totalamount=totalsgstamt+totalcgstamt+totaligstamt+totalnetamount;
		if(y>=pageheight) {
			start=i+1;
			setI(start);
			setSrsno(sno);
			setSrpcs(totalpcs);
			setSrgrosswt(totalgrosswt);
			setSrnetwt(totalnetwt);
			setSrnetamount(totalnetamount);
			setSrwastage(totalwastage);
			setSrmkcharge(totalmkcharge);
			setSrcgstamt(totalcgstamt);
			setSrsgstamt(totalsgstamt);
			setSrigstamt(totaligstamt);
			return y;
		  }
		}
		 graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
		 Common.setSalesreturncontent(true);
		}
		return y;
	}
	public int salesreturntotal(Graphics graphics, int y) {
		if(!Common.isSalesreturntotal()) {
			if(y>=pageheight) {
				return y;
			}
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
			graphics.setFont(Common.getFontarl2());
			
			if(totalsgstper>0) {
			PrintAlignment.printCall(graphics,"SGST",Common.getXpos13(),y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getDf().format(totalsgstper)+"%",Common.getXpos12(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totalsgstamt),Common.getXpos20(),y,TextAlignment.right);
			}
				
			if(totalcgstper>0) {
			PrintAlignment.printCall(graphics,"CGST",Common.getXpos13(),y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getDf().format(totalcgstper)+"%",Common.getXpos12(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totalcgstamt),Common.getXpos20(),y,TextAlignment.right);
			}
				
			if(totaligstper>0) {
			PrintAlignment.printCall(graphics,"IGST",Common.getXpos13(),y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getDf().format(totaligstper)+"%",Common.getXpos12(),y,TextAlignment.right);
			PrintAlignment.printCall(graphics,Common.getAf().format(totaligstamt),Common.getXpos20(),y,TextAlignment.right);
			}
			graphics.setFont(Common.getFonttmr3());
			PrintAlignment.printCall(graphics,"[ Rupees "+NumberToWords.convertNumberToWords(new BigDecimal(Math.round(totalamount)))+" Only ]",Common.getXpos(),y,TextAlignment.left);
			
			graphics.setFont(Common.getFontarl2());
			PrintAlignment.printCall(graphics,"TOTAL",Common.getXpos13(),y+=15,TextAlignment.right);
			PrintAlignment.printCall(graphics,"Rs.",Common.getXpos12(),y,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y-=10,Common.getXpos3(),y);
			PrintAlignment.printCall(graphics,Common.getAf().format(Math.round(totalamount)),Common.getXpos20(),y+=10,TextAlignment.right);
			graphics.drawLine(Common.getXpos12(),y+=5,Common.getXpos3(),y);
			Common.setSalesreturntotal(true);
		}
		return y;
	}
	public static double getTotalamount() {
		return totalamount;
	}
	public static void setTotalamount(double totalamount) {
		Salesreturnprinting.totalamount = totalamount;
	}
	public static double getSrgrosswt() {
		return srgrosswt;
	}
	public static void setSrgrosswt(double srgrosswt) {
		Salesreturnprinting.srgrosswt = srgrosswt;
	}
	public static double getSrnetwt() {
		return srnetwt;
	}
	public static void setSrnetwt(double srnetwt) {
		Salesreturnprinting.srnetwt = srnetwt;
	}
	public static int getI() {
		return i;
	}
	public static void setI(int i) {
		Salesreturnprinting.i = i;
	}
	public static double getSrmkcharge() {
		return srmkcharge;
	}
	public static void setSrmkcharge(double srmkcharge) {
		Salesreturnprinting.srmkcharge = srmkcharge;
	}
	public static double getSrwastage() {
		return srwastage;
	}
	public static void setSrwastage(double srwastage) {
		Salesreturnprinting.srwastage = srwastage;
	}
	public static double getSrnetamount() {
		return srnetamount;
	}
	public static void setSrnetamount(double srnetamount) {
		Salesreturnprinting.srnetamount = srnetamount;
	}
	public static int getSrpcs() {
		return srpcs;
	}
	public static void setSrpcs(int srpcs) {
		Salesreturnprinting.srpcs = srpcs;
	}
	public static int getSrsno() {
		return srsno;
	}
	public static void setSrsno(int srsno) {
		Salesreturnprinting.srsno = srsno;
	}
	public static double getSrsgstamt() {
		return srsgstamt;
	}
	public static void setSrsgstamt(double srsgstamt) {
		Salesreturnprinting.srsgstamt = srsgstamt;
	}
	public static double getSrcgstamt() {
		return srcgstamt;
	}
	public static void setSrcgstamt(double srcgstamt) {
		Salesreturnprinting.srcgstamt = srcgstamt;
	}
	public static double getSrigstamt() {
		return srigstamt;
	}
	public static void setSrigstamt(double srigstamt) {
		Salesreturnprinting.srigstamt = srigstamt;
	}
	}
