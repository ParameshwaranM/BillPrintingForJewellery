package com.billprint.start;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
public class Common {	
private static Font fonttmr1=new Font("Times New Roman",Font.BOLD,16);
private static Font fonttmr2=new Font("Times New Roman",Font.PLAIN,12);
private static Font fonttmr3=new Font("Times New Roman",Font.ITALIC,11);
private static Font fonttmr4=new Font("Times New Roman",Font.BOLD,10);
private static Font fontarl1=new Font("Arial",Font.BOLD,10);
private static Font fontarl2=new Font("Arial",Font.PLAIN,10);
private static Font fontarl3=new Font("Arial",Font.ITALIC,7);
private static Font fontarl4=new Font("Arial",Font.BOLD,8);
private static boolean printheader;
private static boolean sales;
private static boolean salesheader;
private static boolean salescontent;
private static boolean salestotal;
private static boolean salesothers;
private static boolean salesreturnheader;
private static boolean salesreturncontent;
private static boolean salesreturntotal;
private static boolean salesreturn;
private static boolean printfooter;
private static boolean purchase;
private static boolean purchaseheader;
private static boolean purchaseprint;
private static boolean purchasecontent;
private static boolean purchasetotal;
private static boolean purchasereturn;
private static boolean purchasereturnheader;
private static boolean purchasereturnprint;
private static boolean purchasereturncontent;
private static boolean purchasereturntotal;
private static boolean receiptheader;
private static boolean receiptprint;
private static boolean receiptcontent;
private static boolean receipttotal;
private static boolean paymentheader;
private static boolean paymentprint;
private static boolean paymentcontent;
private static boolean paymenttotal;
private static boolean inspectionheader;
private static boolean inspectionprint;
private static boolean inspectioncontent;
private static boolean inspectiontotal;
private static boolean orderheader;
private static boolean orderprint;
private static boolean ordercontent;
private static boolean ordertotal;
private static boolean repairheader;
private static boolean repairprint;
private static boolean repaircontent;
private static boolean repairtotal;
private static boolean orderothers;
private static boolean collection;
private static boolean otherissueheader;
private static boolean otherissueprint;
private static boolean otherissuecontent;
private static boolean otherissuetotal;
private static boolean srheading;
private static boolean srsasalesamount,saTaxPrint,srTaxPrint,prTaxPrint;
private static boolean srsasalesreturnamount;
private static DecimalFormat af=new DecimalFormat("0.00");
private static DecimalFormat wf=new DecimalFormat("0.000");
private static DecimalFormat df=new DecimalFormat("0.0");
private static SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy");
private static int xpos=30,xpos1=300,xpos2=400,xpos3=585,xpos4=60,xpos5=120,xpos6=210,xpos7=260,xpos8=330,xpos9=390,
xpos10=475,xpos11=90,xpos12=523,xpos13=480,xpos14=320,xpos15=230,xpos16=310,xpos17=365,xpos18=425,xpos19=495,xpos20=580,
xpos21=180,xpos22=250,xpos23=330,xpos24=1,xpos25=1;
private static int ypos=200,ypos1=30,ypos2=50,ypos3=10,yos4,ypos5=230,ypos6=1,ypos7=350,pageheight=330;
public static int getYos4() {
	return yos4;
}
public static void setYos4(int yos4) {
	Common.yos4 = yos4;
}
public static Font getFonttmr1() {
	return fonttmr1;
}
public static boolean isSaTaxPrint() {
	return saTaxPrint;
}
public static void setSaTaxPrint(boolean saTaxPrint) {
	Common.saTaxPrint = saTaxPrint;
}
public static boolean isSrTaxPrint() {
	return srTaxPrint;
}
public static void setSrTaxPrint(boolean srTaxPrint) {
	Common.srTaxPrint = srTaxPrint;
}
public static boolean isPrTaxPrint() {
	return prTaxPrint;
}
public static void setPrTaxPrint(boolean prTaxPrint) {
	Common.prTaxPrint = prTaxPrint;
}
public static Font getFonttmr2() {
	return fonttmr2;
}
public static Font getFonttmr3() {
	return fonttmr3;
}
public static Font getFontarl1() {
	return fontarl1;
}
public static Font getFontarl2() {
	return fontarl2;
}
public static Font getFontarl3() {
	return fontarl3;
}
public static boolean isPrintheader() {
	return printheader;
}
public static boolean isSales() {
	return sales;
}
public static boolean isSalesheader() {
	return salesheader;
}
public static DecimalFormat getAf() {
	return af;
}
public static DecimalFormat getWf() {
	return wf;
}
public static int getXpos() {
	return xpos;
}
public static int getXpos1() {
	return xpos1;
}
public static int getXpos2() {
	return xpos2;
}
public static int getXpos3() {
	return xpos3;
}
public static int getXpos4() {
	return xpos4;
}
public static int getXpos5() {
	return xpos5;
}
public static int getXpos6() {
	return xpos6;
}
public static int getYpos() {
	return ypos;
}
public static int getYpos1() {
	return ypos1;
}
public static int getYpos2() {
	return ypos2;
}
public static int getYpos3() {
	return ypos3;
}
//public static int getYos4() {
//	return yos4;
//}
public static int getYpos5() {
	return ypos5;
}
public static int getPageheight() {
	return pageheight;
}
public static void setPrintheader(boolean printheader) {
	Common.printheader = printheader;
}
public static void setSales(boolean sales) {
	Common.sales = sales;
}
public static void setSalesheader(boolean salesheader) {
	Common.salesheader = salesheader;
}
public static int getXpos7() {
	return xpos7;
}
public static int getXpos8() {
	return xpos8;
}
public static int getXpos9() {
	return xpos9;
}
public static int getXpos10() {
	return xpos10;
}
public static int getXpos11() {
	return xpos11;
}
public static boolean isSalescontent() {
	return salescontent;
}
public static void setSalescontent(boolean salescontent) {
	Common.salescontent = salescontent;
}
public static int getYpos6() {
	return ypos6;
}
public static int getXpos12() {
	return xpos12;
}
public static int getXpos13() {
	return xpos13;
}
public static int getXpos14() {
	return xpos14;
}
public static int getXpos15() {
	return xpos15;
}
public static int getXpos16() {
	return xpos16;
}
public static int getXpos17() {
	return xpos17;
}
public static int getXpos18() {
	return xpos18;
}
public static int getXpos19() {
	return xpos19;
}
public static int getXpos20() {
	return xpos20;
}
public static Font getFonttmr4() {
	return fonttmr4;
}
public static Font getFontarl4() {
	return fontarl4;
}
public static int getXpos21() {
	return xpos21;
}
public static int getXpos22() {
	return xpos22;
}
public static int getXpos23() {
	return xpos23;
}
public static int getXpos24() {
	return xpos24;
}
public static int getXpos25() {
	return xpos25;
}
public static DecimalFormat getDf() {
	return df;
}
public static SimpleDateFormat getSf() {
	return sf;
}

public static boolean isSalesreturnheader() {
	return salesreturnheader;
}
public static void setSalesreturnheader(boolean salesreturnheader) {
	Common.salesreturnheader = salesreturnheader;
}
public static boolean isSalesreturncontent() {
	return salesreturncontent;
}
public static void setSalesreturncontent(boolean salesreturncontent) {
	Common.salesreturncontent = salesreturncontent;
}
public static boolean isSalesreturn() {
	return salesreturn;
}
public static void setSalesreturn(boolean salesreturn) {
	Common.salesreturn = salesreturn;
}
public static boolean isPrintfooter() {
	return printfooter;
}
public static void setPrintfooter(boolean printfooter) {
	Common.printfooter = printfooter;
}
public static boolean isPurchase() {
	return purchase;
}
public static void setPurchase(boolean purchase) {
	Common.purchase = purchase;
}
public static boolean isPurchaseheader() {
	return purchaseheader;
}
public static void setPurchaseheader(boolean purchaseheader) {
	Common.purchaseheader = purchaseheader;
}
public static boolean isPurchaseprint() {
	return purchaseprint;
}
public static void setPurchaseprint(boolean purchaseprint) {
	Common.purchaseprint = purchaseprint;
}
public static boolean isPurchasecontent() {
	return purchasecontent;
}
public static void setPurchasecontent(boolean purchasecontent) {
	Common.purchasecontent = purchasecontent;
}
public static boolean isPurchasereturn() {
	return purchasereturn;
}
public static void setPurchasereturn(boolean purchasereturn) {
	Common.purchasereturn = purchasereturn;
}
public static boolean isPurchasereturnheader() {
	return purchasereturnheader;
}
public static void setPurchasereturnheader(boolean purchasereturnheader) {
	Common.purchasereturnheader = purchasereturnheader;
}
public static boolean isPurchasereturnprint() {
	return purchasereturnprint;
}
public static void setPurchasereturnprint(boolean purchasereturnprint) {
	Common.purchasereturnprint = purchasereturnprint;
}
public static boolean isPurchasereturncontent() {
	return purchasereturncontent;
}
public static void setPurchasereturncontent(boolean purchasereturncontent) {
	Common.purchasereturncontent = purchasereturncontent;
}
public static boolean isCollection() {
	return collection;
}
public static void setCollection(boolean collection) {
	Common.collection = collection;
}
public static boolean isSalestotal() {
	return salestotal;
}
public static void setSalestotal(boolean salestotal) {
	Common.salestotal = salestotal;
}
public static boolean isSalesreturntotal() {
	return salesreturntotal;
}
public static void setSalesreturntotal(boolean salesreturntotal) {
	Common.salesreturntotal = salesreturntotal;
}
public static boolean isPurchasetotal() {
	return purchasetotal;
}
public static void setPurchasetotal(boolean purchasetotal) {
	Common.purchasetotal = purchasetotal;
}
public static boolean isPurchasereturntotal() {
	return purchasereturntotal;
}
public static void setPurchasereturntotal(boolean purchasereturntotal) {
	Common.purchasereturntotal = purchasereturntotal;
}
public static boolean isReceiptheader() {
	return receiptheader;
}
public static void setReceiptheader(boolean receiptheader) {
	Common.receiptheader = receiptheader;
}
public static boolean isReceiptprint() {
	return receiptprint;
}
public static void setReceiptprint(boolean receiptprint) {
	Common.receiptprint = receiptprint;
}
public static boolean isReceiptcontent() {
	return receiptcontent;
}
public static void setReceiptcontent(boolean receiptcontent) {
	Common.receiptcontent = receiptcontent;
}
public static boolean isPaymentheader() {
	return paymentheader;
}
public static void setPaymentheader(boolean paymentheader) {
	Common.paymentheader = paymentheader;
}
public static boolean isPaymentprint() {
	return paymentprint;
}
public static void setPaymentprint(boolean paymentprint) {
	Common.paymentprint = paymentprint;
}
public static boolean isPaymentcontent() {
	return paymentcontent;
}
public static void setPaymentcontent(boolean paymentcontent) {
	Common.paymentcontent = paymentcontent;
}
public static boolean isPaymenttotal() {
	return paymenttotal;
}
public static void setPaymenttotal(boolean paymenttotal) {
	Common.paymenttotal = paymenttotal;
}
public static boolean isInspectionheader() {
	return inspectionheader;
}
public static void setInspectionheader(boolean inspectionheader) {
	Common.inspectionheader = inspectionheader;
}
public static boolean isInspectioncontent() {
	return inspectioncontent;
}
public static void setInspectioncontent(boolean inspectioncontent) {
	Common.inspectioncontent = inspectioncontent;
}
public static boolean isInspectiontotal() {
	return inspectiontotal;
}
public static void setInspectiontotal(boolean inspectiontotal) {
	Common.inspectiontotal = inspectiontotal;
}
public static boolean isInspectionprint() {
	return inspectionprint;
}
public static void setInspectionprint(boolean inspectionprint) {
	Common.inspectionprint = inspectionprint;
}
public static boolean isOrderheader() {
	return orderheader;
}
public static void setOrderheader(boolean orderheader) {
	Common.orderheader = orderheader;
}
public static boolean isOrderprint() {
	return orderprint;
}
public static void setOrderprint(boolean orderprint) {
	Common.orderprint = orderprint;
}
public static boolean isOrdercontent() {
	return ordercontent;
}
public static void setOrdercontent(boolean ordercontent) {
	Common.ordercontent = ordercontent;
}
public static boolean isOrdertotal() {
	return ordertotal;
}
public static void setOrdertotal(boolean ordertotal) {
	Common.ordertotal = ordertotal;
}
public static boolean isOrderothers() {
	return orderothers;
}
public static void setOrderothers(boolean orderothers) {
	Common.orderothers = orderothers;
}
public static boolean isRepairheader() {
	return repairheader;
}
public static void setRepairheader(boolean repairheader) {
	Common.repairheader = repairheader;
}
public static boolean isRepairprint() {
	return repairprint;
}
public static void setRepairprint(boolean repairprint) {
	Common.repairprint = repairprint;
}
public static boolean isRepaircontent() {
	return repaircontent;
}
public static void setRepaircontent(boolean repaircontent) {
	Common.repaircontent = repaircontent;
}
public static boolean isRepairtotal() {
	return repairtotal;
}
public static void setRepairtotal(boolean repairtotal) {
	Common.repairtotal = repairtotal;
}
public static boolean isOtherissueheader() {
	return otherissueheader;
}
public static void setOtherissueheader(boolean otherissueheader) {
	Common.otherissueheader = otherissueheader;
}
public static boolean isOtherissueprint() {
	return otherissueprint;
}
public static void setOtherissueprint(boolean otherissueprint) {
	Common.otherissueprint = otherissueprint;
}
public static boolean isOtherissuecontent() {
	return otherissuecontent;
}
public static void setOtherissuecontent(boolean otherissuecontent) {
	Common.otherissuecontent = otherissuecontent;
}
public static boolean isOtherissuetotal() {
	return otherissuetotal;
}
public static void setOtherissuetotal(boolean otherissuetotal) {
	Common.otherissuetotal = otherissuetotal;
}
public static int getYpos7() {
	return ypos7;
}
public static boolean isSalesothers() {
	return salesothers;
}
public static void setSalesothers(boolean salesothers) {
	Common.salesothers = salesothers;
}
public static boolean isSrheading() {
	return srheading;
}
public static void setSrheading(boolean srheading) {
	Common.srheading = srheading;
}
public static boolean isSrsasalesreturnamount() {
	return srsasalesreturnamount;
}
public static void setSrsasalesreturnamount(boolean srsasalesreturnamount) {
	Common.srsasalesreturnamount = srsasalesreturnamount;
}
public static boolean isSrsasalesamount() {
	return srsasalesamount;
}
public static void setSrsasalesamount(boolean srsasalesamount) {
	Common.srsasalesamount = srsasalesamount;
}
public static boolean isReceipttotal() {
	return receipttotal;
}
public static void setReceipttotal(boolean receipttotal) {
	Common.receipttotal = receipttotal;
}
}
