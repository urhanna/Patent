package Kipris_Open_API;

import java.io.*;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import util.DBUtil;

public class PDF_SaveToDB extends Thread {

	private static String dbname = "openAPI_han";
	private static DBUtil db = new DBUtil(dbname);
	private static final String pt_table = "PDF";
	public String year;
	public static int thread_id = 0;
	public static int count = 0;

	static int success_cnt = 0;
	static int currentLine = 0;
	static int fail_cnt = 0;
	private static String PT_cnt = "0000000";

	static String values = "";
	int line_count = 0;

	public PDF_SaveToDB() { }

	public PDF_SaveToDB(String year, int i) {
		this.year = year;
		this.thread_id = i;
	}

	public void run() { 
		try {
			String appNum = "";
			for (count = 0; count < 180000; count++) {
				String temp = PT_cnt.substring(Integer.toString(count).length());
				appNum = "10" + this.year + temp + count;
				line_count++;

				File DataFile = new File("A:\\1.AMI\\6.특허데이터수집\\2.PDF\\2.PDF_Data\\"  // 다운로드된 pdf 
						+ appNum + ".pdf");
				
				File DataFile_Pub = new File("A:\\1.AMI\\6.특허데이터수집\\2.PDF\\2.PDF_Data\\"  // 다운로드된 Pub_pdf 
						+ appNum + " (1).pdf");

				String[] splitData;
				String[] splitData2;
				
				splitData = new String[2];
				splitData2 = new String[2];

				if (DataFile.exists()) {  // 다운로드된 pdf가 있으면 
					
					String docName = "";
					docName = appNum + ".pdf";
					splitData[0] = docName;
					System.out.println(docName);
					
					// pdf 텍스트 추출 
					PDDocument pdf = PDDocument.load(new File("A:\\1.AMI\\6.특허데이터수집\\2.PDF\\2.PDF_Data\\" + appNum + ".pdf" ));  
					PDFTextStripper textStripper = new PDFTextStripper();
					
					StringWriter textWriter = new StringWriter();
					textStripper.writeText(pdf, textWriter);
					pdf.close();
					String pdfText = textWriter.toString();
					pdfText = pdfText.replace("\"", "");
					splitData[1] = pdfText;
//					System.out.println(pdfText);
					
					insertSQL_Multi(pt_table, line_count, this.year, splitData);
				} 
				
				if (DataFile_Pub.exists()) {
					
					String docName2 = "";
					docName2 = appNum + " (1).pdf";
					splitData2[0] = docName2;
					System.out.println(docName2);
					
					// pdf 텍스트 추출 
					PDDocument pdf2 = PDDocument.load(new File("A:\\1.AMI\\6.특허데이터수집\\2.PDF\\2.PDF_Data\\" + appNum + " (1).pdf" ));  
					PDFTextStripper textStripper2 = new PDFTextStripper();
					
					StringWriter textWriter2 = new StringWriter();
					textStripper2.writeText(pdf2, textWriter2);
					pdf2.close();
					String pdfText2 = textWriter2.toString();
					pdfText2 = pdfText2.replace("\"", "");
					splitData2[1] = pdfText2;
//					System.out.println(pdfText2);
					
					insertSQL_Multi(pt_table, line_count, this.year, splitData2);
				} else {
					continue;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public static String getChildren(Element element, String tagName) {
		NodeList list = element.getElementsByTagName(tagName);
		Element cElement = (Element) list.item(0);

		if (cElement.getFirstChild() != null) {
			return cElement.getFirstChild().getNodeValue();
		} else {
			return "";
		}
	}
	
	public static String checkNull(String text) {
		if(text==null) { return "nothing"; }
		else {	return text;}
	}
	
	public static void insertSQL_Multi(String tableName, int line_count, String year, String[] splitData) {
		String columns = "docName, pdfText";
		String insert_sql = "INSERT INTO " + dbname + ".";
		insert_sql += tableName + " (";
		insert_sql += columns;
		insert_sql += ") ";
		insert_sql += "VALUES ";
		
		values += "(\"" + splitData[0] + "\", \"" +  splitData[1]  + "\")" ;
		
		try {
			String filename = "errfile.txt";
			File f = new File(filename);
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(f, true));
			insert_sql += values;
			insert_sql += ";";
			values = "";
			
			if(!db.execute(insert_sql)) {
				System.out.println("fails: " + ++fail_cnt + "\n\n");
				String tmp = "";
				tmp = line_count + "\t" + insert_sql;
				bw.write(tmp);
				bw.newLine();
			} else {
				System.out.println("success: " + success_cnt++ + "\n\n");
				values = "";
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			System. out.println("Exception fail: " + fail_cnt + "\n\n");
			e.printStackTrace();
		}
	}
}
