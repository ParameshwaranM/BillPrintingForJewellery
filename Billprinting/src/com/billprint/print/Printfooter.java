package com.billprint.print;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.billprint.dao.Dao;
import com.billprint.model.Company;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class Printfooter {
private static List<Company>lstCompany=null;
public Printfooter() {
	try {
		Dao dao=new Dao();
		lstCompany=new ArrayList<Company>();
	    lstCompany=dao.companydetails();
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null,e.getMessage());
	 }
   }
public int footerprint(Graphics graphics, PageFormat pageFormat,String empname ,int y) {
	if(!Common.isPrintfooter()) {
	graphics.setFont(Common.getFontarl2());
	y=Common.getYpos7();
	if(!"".equalsIgnoreCase(empname)) {
	PrintAlignment.printCall(graphics,"("+empname+")",Common.getXpos(),y,TextAlignment.left);
	}
	graphics.setFont(Common.getFontarl1());
	PrintAlignment.printCall(graphics,"SALEMAN SIGN",Common.getXpos(),y+=15,TextAlignment.left);
	PrintAlignment.printCall(graphics,"CUSTOMER SIGN",Common.getXpos1(),y,TextAlignment.center);
	PrintAlignment.printCall(graphics,"For "+lstCompany.get(0).getCompanyname(),Common.getXpos3(),y,TextAlignment.right);
	graphics.drawLine(Common.getXpos(),y+=5,Common.getXpos3(),y);
	PrintAlignment.printCall(graphics," (Certified that particulars given above are true and correct).",Common.getXpos3(),y+=10,TextAlignment.right);
	PrintAlignment.printCall(graphics,"Delivery at Showroom",Common.getXpos(),y,TextAlignment.left);
//	if(y>Common.getPageheight()) {
//		return y;
//	}
//	Common.setPrintfooter(true);
	}
	return y;	
 }
}
