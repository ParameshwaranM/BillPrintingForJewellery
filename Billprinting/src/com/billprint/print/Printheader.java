package com.billprint.print;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import com.billprint.dao.Dao;
import com.billprint.model.Company;
import com.billprint.model.Customer;
import com.billprint.start.ApplicationStart;
import com.billprint.start.Common;
import com.billprint.start.PrintAlignment;
import com.billprint.start.TextAlignment;
public class Printheader {
private static List<Company>lstCompany=null;
private List<Customer>lstCustomer=null;
private List<Customer>lstRate=null;
public Printheader() {
	try {
		Dao dao=new Dao();
		lstCompany=new ArrayList<Company>();
		lstCompany=dao.companydetails();
		lstCustomer=new ArrayList<Customer>();
		lstCustomer=dao.customerdetails();
		lstRate=new ArrayList<Customer>();
		lstRate=dao.ratedetails();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null,e.getMessage());
	}
}
public int headerprint(Graphics graphics, PageFormat pageFormat,String bilno,String billdate,int y) {
	if(!Common.isPrintheader() ) {
	graphics.setFont(Common.getFonttmr1());
	
	// Logo
	BufferedImage bufferedImage=null;
	CodeSource codeSource=ApplicationStart.class.getProtectionDomain().getCodeSource();
	File file=new File(codeSource.getLocation().getPath());
	String path=file.getParent()+File.separator+"logo.jpg";
	file=new File(path);
	bufferedImage=new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
	if(file.isFile()) {
		try {
			bufferedImage=ImageIO.read(file);
			graphics.drawImage(bufferedImage,200, 5, 170, 60, null);
		} catch (Exception e) {
		}
	}
	
//	PrintAlignment.printCall(graphics,"TAX INVOICE",Common.getXpos1(),y=Common.getYpos1(),TextAlignment.center);
	
	graphics.setFont(Common.getFontarl2());
//	graphics.drawRect(Common.getXpos9(), y+=15,5,5);
	
//	PrintAlignment.printCall(graphics,"Original for Receipent",Common.getXpos2(),y+=5,TextAlignment.left);
//	graphics.drawRect(Common.getXpos9(), y+=5,5,5);
//	PrintAlignment.printCall(graphics,"Duplicate for Supplier/Transporter",Common.getXpos2(),y+=5,TextAlignment.left);
//	graphics.drawRect(Common.getXpos9(), y+=5,5,5);
//	PrintAlignment.printCall(graphics,"Triplicate for Supplier",Common.getXpos2(),y+=5,TextAlignment.left);
	y=10;
//	graphics.drawLine(Common.getXpos(),y,Common.getXpos3(),y);
	
	graphics.setFont(Common.getFontarl1());
	if(lstCustomer.size()>0) {
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getCustomername())) {
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getCustomername(),Common.getXpos9()+10,y+=10,TextAlignment.left);
	}
	graphics.setFont(Common.getFontarl2());
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getArea())&&!"".equalsIgnoreCase(lstCustomer.get(0).getAddress1())) {
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getArea()+","+lstCustomer.get(0).getAddress1(),Common.getXpos9()+10,Common.getYpos3()+20,TextAlignment.left);
	}
	else if(!"".equalsIgnoreCase(lstCustomer.get(0).getAddress1())) {
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getAddress1(),Common.getXpos9()+10,Common.getYpos3()+20,TextAlignment.left);
	}
	
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getAddress2())) {
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getAddress2(),Common.getXpos9()+10,Common.getYpos3()+30,TextAlignment.left);
	}
	
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getCity())&&!"".equalsIgnoreCase(lstCustomer.get(0).getCity())) {
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getArea()+"-"+lstCustomer.get(0).getCity(),Common.getXpos9()+10,Common.getYpos3()+45,TextAlignment.left);
	}
	else if(!"".equalsIgnoreCase(lstCustomer.get(0).getCity())){
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getCity(),Common.getXpos9()+10,Common.getYpos3()+45,TextAlignment.left);	
	}
	else  if(!"".equalsIgnoreCase(lstCustomer.get(0).getArea())){
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getArea(),Common.getXpos9()+10,Common.getYpos3()+45,TextAlignment.left);	
	}
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getMobileno())) {
	PrintAlignment.printCall(graphics,"MOBILENO :"+lstCustomer.get(0).getMobileno(),Common.getXpos9()+10,Common.getYpos3()+60,TextAlignment.left);
	}
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getState())) {
	PrintAlignment.printCall(graphics,"STATE :"+lstCustomer.get(0).getState(),Common.getXpos9()+10,Common.getYpos3()+75,TextAlignment.left);
	}
	if(!"".equalsIgnoreCase(lstCustomer.get(0).getGstno())) {
	PrintAlignment.printCall(graphics,"GSTIN :"+lstCustomer.get(0).getGstno(),Common.getXpos9()+10,Common.getYpos3()+90,TextAlignment.left);
	}
	else if (!"".equalsIgnoreCase(lstCustomer.get(0).getProofno())) {
	PrintAlignment.printCall(graphics,lstCustomer.get(0).getProofname()+" : "+lstCustomer.get(0).getProofno(),Common.getXpos9()+10,Common.getYpos3()+90,TextAlignment.left);	
	}
	}
	graphics.setFont(Common.getFontarl1());
	
	PrintAlignment.printCall(graphics,"GOLD RATE    :"+Common.getAf().format(lstRate.get(0).getRate())+"/Grams",Common.getXpos9()+10,Common.getYpos3()+105,TextAlignment.left);
	PrintAlignment.printCall(graphics,"SILVER RATE :"+Common.getAf().format(lstRate.get(1).getRate())+"/Grams",Common.getXpos9()+10,Common.getYpos3()+120,TextAlignment.left);
	graphics.setFont(Common.getFonttmr1());
	Common.setYos4(Common.getYpos3()+120);
	PrintAlignment.printCall(graphics,lstCompany.get(0).getCompanyname(),Common.getXpos(),y=Common.getYpos3()+5,TextAlignment.left);
	
	graphics.setFont(Common.getFontarl2());
	if(!"".equalsIgnoreCase(lstCompany.get(0).getAdd1())) {
	PrintAlignment.printCall(graphics,lstCompany.get(0).getAdd1(),Common.getXpos(),Common.getYpos3()+15,TextAlignment.left);
	}
	if(!"".equalsIgnoreCase(lstCompany.get(0).getAdd2())){
	PrintAlignment.printCall(graphics,lstCompany.get(0).getAdd2()+",",Common.getXpos(),Common.getYpos3()+30,TextAlignment.left);
	 }
	if((!"".equalsIgnoreCase(lstCompany.get(0).getCity()))&&(!"".equalsIgnoreCase(lstCompany.get(0).getPincode()))){
	PrintAlignment.printCall(graphics,lstCompany.get(0).getCity()+"-"+lstCompany.get(0).getPincode(),Common.getXpos(),Common.getYpos3()+45,TextAlignment.left);
	}
	if(!"".equalsIgnoreCase(lstCompany.get(0).getState())) {
	PrintAlignment.printCall(graphics,"STATE :"+lstCompany.get(0).getState(),Common.getXpos(),Common.getYpos3()+60,TextAlignment.left);
	}
	
	PrintAlignment.printCall(graphics,"GSTIN :"+lstCompany.get(0).getGstno(),Common.getXpos(),Common.getYpos3()+75,TextAlignment.left);
	
	if(!"".equalsIgnoreCase(lstCompany.get(0).getPhoneno())) {
	PrintAlignment.printCall(graphics,"Ph :"+lstCompany.get(0).getPhoneno(),Common.getXpos(),Common.getYpos3()+90,TextAlignment.left);
	}
	graphics.setFont(Common.getFontarl1());
	PrintAlignment.printCall(graphics,"GSTBILLNO :"+bilno,Common.getXpos(),Common.getYpos3()+105,TextAlignment.left);
	PrintAlignment.printCall(graphics,"DATE :"+billdate,Common.getXpos(),y=Common.getYpos3()+120,TextAlignment.left);
	graphics.drawLine(Common.getXpos(),y=Common.getYpos3()+130,Common.getXpos3(),y);
	if(y>Common.getYpos()) {
		return y;
	}
	
   }
   return y;
 }
}
