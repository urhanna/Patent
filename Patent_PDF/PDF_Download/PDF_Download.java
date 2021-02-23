package Kipris_Open_API;

import java.io.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URI;
import java.awt.Desktop;

public class PDF_Download extends Thread {
	public String year;
	public static int count = 0;

	static int success_cnt = 0;
	static int currentLine = 0;
	static int fail_cnt = 0;
	private static String PT_cnt = "0000000";

	static String values = "";
	int line_count = 0;

	public PDF_Download() { }

	public PDF_Download(String year, int i) {
		this.year = year;
	}

	public void run() { 
		try {
			String appNum = "";
			for (count = 0; count < 10; count++) {
				String temp = PT_cnt.substring(Integer.toString(count).length());
				appNum = "10" + this.year + temp + count;
				line_count++;

				File DataFile = new File("A:\\1.AMI\\6.특허데이터수집\\2.PDF\\0.Data\\0.Data_"
						+ this.year + "\\" + appNum + "_PDF.txt");
				File DataFile2 = new File("A:\\1.AMI\\6.특허데이터수집\\2.PDF\\0.Data\\0.Data_"
						+ this.year + "\\" + appNum + "_Pub_PDF.txt");

				if (DataFile.exists()) {
					BufferedReader br = new BufferedReader(new FileReader(DataFile));

					StringBuffer buffer = new StringBuffer();
					String line = null;
					String tmpStr = null;
					while ((line = br.readLine()) != null) {
						tmpStr = line.toString();
						tmpStr = tmpStr.replaceAll(" ", "");

						if (!tmpStr.equals(""))
							buffer.append(line).append("\r\n");
					}
					br.close();

					String xmlResult = buffer.toString();
					String xmlRecords = xmlResult;

					DocumentBuilder docb = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource p = new InputSource();
					p.setCharacterStream(new StringReader(xmlRecords));

					Document doc = docb.parse(p);
					NodeList nodes = doc.getElementsByTagName("response");

					Element element = (Element) nodes.item(0);

					// PDF 파일 이름 (docName) 
					String docName = "";
					NodeList _docName = element.getElementsByTagName("docName");
					Element lines = (Element) _docName.item(0);
					checkElement2(lines);
					if(checkElement2(lines).equals("nothing")) {
						count++;
						continue;
					}
					docName = getCharacterDataFromElement(lines).replace("-", "");
					System.out.println(docName);

					// PDF 다운로드 경로(path)
					String path = "";
					NodeList _path = element.getElementsByTagName("path");
					lines = (Element) _path.item(0);
					path = checkElement(lines);
					System.out.println(path);
					
					// pdf 다운로드 
					Desktop.getDesktop().browse(new URI(path));  
					Thread.sleep(1000);
				}
				
				if (DataFile2.exists()) {
					BufferedReader br2 = new BufferedReader(new FileReader(DataFile2));

					StringBuffer buffer2 = new StringBuffer();
					String line2 = null;
					String tmpStr2 = null;
					while ((line2 = br2.readLine()) != null) {
						tmpStr2 = line2.toString();
						tmpStr2 = tmpStr2.replaceAll(" ", "");

						if (!tmpStr2.equals(""))
							buffer2.append(line2).append("\r\n");
					}
					br2.close();

					String xmlResult2 = buffer2.toString();
					String xmlRecords2 = xmlResult2;

					DocumentBuilder docb2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource p2 = new InputSource();
					p2.setCharacterStream(new StringReader(xmlRecords2));

					Document doc2 = docb2.parse(p2);
					NodeList nodes2 = doc2.getElementsByTagName("response");

					Element element2 = (Element) nodes2.item(0);

					// PDF 파일 이름 (docName) 
					String docName2 = "";
					NodeList _docName2 = element2.getElementsByTagName("docName");
					Element lines2 = (Element) _docName2.item(0);
					checkElement2(lines2);
					if(checkElement2(lines2).equals("nothing")) {
						count++;
						continue;
					}
					docName2 = getCharacterDataFromElement(lines2).replace("-", "");
					System.out.println("Pub" + docName2);

					// PDF 다운로드 경로(path)
					String path2 = "";
					NodeList _path2 = element2.getElementsByTagName("path");
					lines2 = (Element) _path2.item(0);
					path2 = checkElement(lines2);
					System.out.println(path2);
					
					// pdf 다운로드 
					Desktop.getDesktop().browse(new URI(path2));  
					Thread.sleep(1000);
				} else {
					continue;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String checkElement(Element lines) {
		if(lines==null) {
			return "nothing";
		}
		else {
			return getCharacterDataFromElement(lines).replace("'", "\'");
		}
	}
	
	private static String checkElement2(Element lines) {  // docName이 여기 해당
		if(lines==null) {
			return "nothing";
		}
		else {
			return getCharacterDataFromElement(lines).replace("-", "");
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
}
