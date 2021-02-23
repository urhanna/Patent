package Kipris_Open_API;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import util.DBUtil;

public class GetAppInfoTest implements Runnable {
	
	private String year;
	
	public GetAppInfoTest(String year) {
		this.year = year;
	}
	
	private static String dbname = "openAPI_han";
	private static DBUtil db = new DBUtil(dbname);
	private static final String pt_table ="patent";
	private static final String pt_claim_table ="patent_claim";
	
	static int success_cnt = 0;
	static int currentLine = 0;
	static int fail_cnt = 0;
	
	static int claim_success_cnt = 0;
	static int claim_currentLine = 0;
	static int claim_fail_cnt = 0;
//	static int fileCnt = 3;
			
	
//	public static void main(String[] args) throws ParserConfigurationException, SAXException {
	public void run() { requestAPI();	}

	public synchronized void requestAPI() {
		
//		Element elements[] = new Element[4];
		// public 		// run 매개변수로 텍스트파일 이름 변수 넣어서 받으면 됨
//		StringBuffer sql = null;

			StringBuffer apiUrl = null;
			StringBuffer cpcUrl = null;
			StringBuffer RndUrl = null;
			StringBuffer FamilyUrl = null;
			String ServiceKey = "hAk4A7kcL4M/AmhRV2kZJ6MmVN9hpQzTpWtWDTZpfPs=";
			
			// File Select
			DataSelect dataSelect = new DataSelect(this.year);
			Vector<String> appNum = dataSelect.getAppNum();
			int errorcount = 0;
			for (int count = 0; count < 9999999; count++) {

				
				apiUrl = new StringBuffer(
						"http://plus.kipris.or.kr/kipo-api/kipi/patUtiModInfoSearchSevice/getBibliographyDetailInfoSearch");
				cpcUrl = new StringBuffer("http://plus.kipris.or.kr/openapi/rest/patUtiModInfoSearchSevice/patentCpcInfo");
				RndUrl = new StringBuffer("http://plus.kipris.or.kr/openapi/rest/patUtiModInfoSearchSevice/patentRndInfo");
				FamilyUrl = new StringBuffer(
						"http://plus.kipris.or.kr/openapi/rest/patUtiModInfoSearchSevice/patentFamilyInfo");
				
//				apiUrl.append("?applicationNumber=" + "1020190000012" + "&ServiceKey=" + ServiceKey);
//				cpcUrl.append("?applicationNumber=" + "1020190000012" + "&accessKey=" + ServiceKey);
//				RndUrl.append("?applicationNumber=" + "1020190000012" + "&accessKey=" + ServiceKey);
//				FamilyUrl.append("?applicationNumber=" + "1020190000012" + "&accessKey=" + ServiceKey);
				
				apiUrl.append("?applicationNumber=" + appNum.elementAt(count) + "&ServiceKey=" + ServiceKey);
				cpcUrl.append("?applicationNumber=" + appNum.elementAt(count) + "&accessKey=" + ServiceKey);
				RndUrl.append("?applicationNumber=" + appNum.elementAt(count) + "&accessKey=" + ServiceKey);
				FamilyUrl.append("?applicationNumber=" + appNum.elementAt(count) + "&accessKey=" + ServiceKey);

				System.out.println(appNum.elementAt(count));

				try {
//					String tmpURL = "http://plus.kipris.or.kr/kipo-api/kipi/patUtiModInfoSearchSevice/getBibliographyDetailInfoSearch?applicationNumber=1020050050026&ServiceKey=";
//					tmpURL += ServiceKey;
//					URL url = new URL(tmpURL);
//					System.out.println(tmpURL);
					
					
					URL url = new URL(apiUrl.toString());
					URL cpcurl = new URL(cpcUrl.toString());
					URL rndurl = new URL(RndUrl.toString());
					URL familyurl = new URL(FamilyUrl.toString());

					InputStream is = url.openStream();
					InputStreamReader isr = new InputStreamReader(is, "utf-8");
					BufferedReader reader = new BufferedReader(isr);
					StringBuffer buffer = new StringBuffer();
					String line = null;
					String tmpStr = null;
					while ((line = reader.readLine()) != null) {
						tmpStr = line.toString();
						tmpStr = tmpStr.replaceAll(" ", "");

						if (!tmpStr.equals(""))
							buffer.append(line).append("\r\n");
					}

					InputStream is2 = cpcurl.openStream();
					InputStreamReader isr2 = new InputStreamReader(is2, "utf-8");
					BufferedReader reader2 = new BufferedReader(isr2);
					StringBuffer buffer2 = new StringBuffer();
					String line2 = null;
					String tmpStr2 = null;
					while ((line2 = reader2.readLine()) != null) {
						tmpStr2 = line2.toString();
						tmpStr2 = tmpStr2.replaceAll(" ", "");

						if (!tmpStr2.equals(""))
							buffer2.append(line2).append("\r\n");
					}

					InputStream is3 = rndurl.openStream();
					InputStreamReader isr3 = new InputStreamReader(is3, "utf-8");
					BufferedReader reader3 = new BufferedReader(isr3);
					StringBuffer buffer3 = new StringBuffer();
					String line3 = null;
					String tmpStr3 = null;
					while ((line3 = reader3.readLine()) != null) {
						tmpStr3 = line3.toString();
						tmpStr3 = tmpStr3.replaceAll(" ", "");

						if (!tmpStr3.equals(""))
							buffer3.append(line3).append("\r\n");
					}

					InputStream is4 = familyurl.openStream();
					InputStreamReader isr4 = new InputStreamReader(is4, "utf-8");
					BufferedReader reader4 = new BufferedReader(isr4);
					StringBuffer buffer4 = new StringBuffer();
					String line4 = null;
					String tmpStr4 = null;
					while ((line4 = reader4.readLine()) != null) {
						tmpStr4 = line4.toString();
						tmpStr4 = tmpStr4.replaceAll(" ", "");

						if (!tmpStr4.equals(""))
							buffer4.append(line4).append("\r\n");
					}

					reader.close();
					reader2.close();
					reader3.close();
					reader4.close();

					String xmlResult = buffer.toString();
					String cpcResult = buffer2.toString();
					String rndResult = buffer3.toString();
					String familyResult = buffer4.toString();

					System.out.println("ForeignPatentBibliographicService/bibliographicInfoRESULT=>" + xmlResult);
					System.out.println("ForeignPatentBibliographicService/bibliographicInfoRESULT=>" + cpcResult);
					System.out.println("ForeignPatentBibliographicService/bibliographicInfoRESULT=>" + rndResult);
					System.out.println("ForeignPatentBibliographicService/bibliographicInfoRESULT=>" + familyResult);
					String xmlRecords = xmlResult;
					String cpcRecords = cpcResult;
					String rndRecords = rndResult;
					String familyRecords = familyResult;

					DocumentBuilder docb = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource p = new InputSource();
					p.setCharacterStream(new StringReader(xmlRecords));

					Document doc = docb.parse(p);
					NodeList nodes = doc.getElementsByTagName("response");

					DocumentBuilder docb2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource p2 = new InputSource();
					p2.setCharacterStream(new StringReader(cpcRecords));

					Document doc2 = docb2.parse(p2);
					NodeList nodes2 = doc2.getElementsByTagName("response");

					DocumentBuilder docb3 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource p3 = new InputSource();
					p3.setCharacterStream(new StringReader(rndRecords));

					Document doc3 = docb3.parse(p3);
					NodeList nodes3 = doc3.getElementsByTagName("response");

					DocumentBuilder docb4 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource p4 = new InputSource();
					p4.setCharacterStream(new StringReader(familyRecords));

					Document doc4 = docb4.parse(p4);
					NodeList nodes4 = doc4.getElementsByTagName("response");

					Element element = (Element) nodes.item(0);
					Element element2 = (Element) nodes2.item(0);
					Element element3 = (Element) nodes3.item(0);
					Element element4 = (Element) nodes4.item(0);

				
					// 출원번호
					String applicationNumber = "";
					NodeList _applicationNumber = element.getElementsByTagName("applicationNumber");
					Element lines = (Element) _applicationNumber.item(0);
					System.out.println(getCharacterDataFromElement(lines));
					applicationNumber = getCharacterDataFromElement(lines).replaceAll("-", "");
					System.out.println(applicationNumber);

					// 제목
					String inventionTitle = "";
					NodeList _inventionTitle = element.getElementsByTagName("inventionTitle");
					lines = (Element) _inventionTitle.item(0);
					inventionTitle = getCharacterDataFromElement(lines).replace("'", "\\'");
					;
					System.out.println(inventionTitle);

					// 제목(영문)
					String inventionTitleEng = "";
					NodeList _inventionTitleEng = element.getElementsByTagName("inventionTitleEng");
					lines = (Element) _inventionTitleEng.item(0);
					inventionTitleEng = getCharacterDataFromElement(lines).replace("'", "\\'");
					;
					System.out.println(inventionTitleEng);

					// 출원일자
					String applicationDate = "";
					NodeList _applicationDate = element.getElementsByTagName("applicationDate");
					lines = (Element) _applicationDate.item(0);
					applicationDate = getCharacterDataFromElement(lines).replace(".", "");
					System.out.println(applicationDate);

					// 등록번호
					String registerNumber = "";
					NodeList _registerNumber = element.getElementsByTagName("registerNumber");
					lines = (Element) _registerNumber.item(0);
					registerNumber = getCharacterDataFromElement(lines).replace("-", "");
					System.out.println(registerNumber);

					// 등록일자
					String registerDate = "";
					NodeList _registerDate = element.getElementsByTagName("registerDate");
					lines = (Element) _registerDate.item(0);
					registerDate = getCharacterDataFromElement(lines).replace(".", "");
					System.out.println(registerDate);

					// 등록상태
					String registerStatus = "";
					NodeList _registerStatus = element.getElementsByTagName("registerStatus");
					lines = (Element) _registerStatus.item(0);
					registerStatus = getCharacterDataFromElement(lines);
					System.out.println(registerStatus);

					// 출원종류(신규, 국제출원, null)
					String originalApplicationKind = "";
					NodeList _originalApplicationKind = element.getElementsByTagName("originalApplicationKind");
					lines = (Element) _originalApplicationKind.item(0);
					originalApplicationKind = getCharacterDataFromElement(lines);
					System.out.println(originalApplicationKind);

					// 청구항수
					String claimCount = "";
					NodeList _claimCount = element.getElementsByTagName("claimCount");
					lines = (Element) _claimCount.item(0);
					claimCount = getCharacterDataFromElement(lines);
					System.out.println(claimCount);

					// 발명인
					NodeList _inventorName = element.getElementsByTagName("inventorInfo");
					lines = (Element) _inventorName.item(0);

					String inventorName = "";
					for (int j = 0; j < _inventorName.getLength(); j++) {
						Element eventEle = (Element) _inventorName.item(j);
						inventorName = inventorName + getChildren(eventEle, "name").replaceAll(" ", "") + " ";
					}
					System.out.println(inventorName);

					// CPC 코드 리스트
					NodeList _listCPC = element2.getElementsByTagName("CooperativepatentclassificationNumber");

					String listCPC = "";
					for (int x = 0; x < _listCPC.getLength(); x++) {
						lines = (Element) _listCPC.item(x);
						listCPC = listCPC + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listCPC.getLength() - 1) {
							listCPC = listCPC + ", ";
						}
					}

					if (_listCPC.getLength() != 0) {
						listCPC = "[" + listCPC + "]";
					}
					System.out.println(listCPC);

					// IPC 코드 리스트
					NodeList _listIPC = element.getElementsByTagName("ipcNumber");

					String listIPC = "";
					for (int x = 0; x < _listIPC.getLength(); x++) {
						lines = (Element) _listIPC.item(x);
						listIPC = listIPC + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listIPC.getLength() - 1) {
							listIPC = listIPC + ", ";
						}
					}

					if (_listIPC.getLength() != 0) {
						listIPC = "[" + listIPC + "]";
					}
					System.out.println(listIPC);

					// 공개번호
					String openNumber = "";
					NodeList _openNumber = element.getElementsByTagName("openNumber");
					lines = (Element) _openNumber.item(0);
					openNumber = getCharacterDataFromElement(lines).replaceAll("-", "");
					System.out.println(openNumber);

					// 공개일자
					String openDate = "";
					NodeList _openDate = element.getElementsByTagName("openDate");
					lines = (Element) _openDate.item(0);
					openDate = getCharacterDataFromElement(lines).replace(".", "");
					System.out.println(openDate);

					// 공고번호
					String publicationNumber = "";
					NodeList _publicationNumber = element.getElementsByTagName("publicationNumber");
					lines = (Element) _publicationNumber.item(0);
					publicationNumber = getCharacterDataFromElement(lines).replaceAll("-", "");
					System.out.println(publicationNumber);

					// 공고일자
					String publicationDate = "";
					NodeList _publicationDate = element.getElementsByTagName("publicationDate");
					lines = (Element) _publicationDate.item(0);
					publicationDate = getCharacterDataFromElement(lines).replace(".", "");
					System.out.println(publicationDate);

					// 출원인
					String applicantName = "";
					NodeList _applicantName = element.getElementsByTagName("name");
					lines = (Element) _applicantName.item(0);
					if (lines != null) {
						applicantName = getCharacterDataFromElement(lines).replace("'", "\\'");
						;
					}
					System.out.println(applicantName);

					// 초록
					String astrtCont = "";
					NodeList _astrtCont = element.getElementsByTagName("astrtCont");
					lines = (Element) _astrtCont.item(0);
					if (lines != null) {
						astrtCont = getCharacterDataFromElement(lines).replace("'", "\\'");
					}
					System.out.println(astrtCont);

					// 청구항
					NodeList _claim = element.getElementsByTagName("claim");

					String claim = "";
					for (int j = 0; j < _claim.getLength(); j++) {
						lines = (Element) _claim.item(j);
						claim = claim + getCharacterDataFromElement(lines).replace("'", "\"") + "\n";
					}
					ClaimParsing cp = new ClaimParsing();
					claim = cp.claimParser(claim);
//				System.out.println(claim);

					// 국가연구개발사업정보-연구부처명
					NodeList _listRndMinistriesandoffices = element3
							.getElementsByTagName("ResearchMinistriesandofficesName");

					String listRndMinistriesandoffices = "";
					for (int x = 0; x < _listRndMinistriesandoffices.getLength(); x++) {
						lines = (Element) _listRndMinistriesandoffices.item(x);
						listRndMinistriesandoffices = listRndMinistriesandoffices
								+ getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listRndMinistriesandoffices.getLength() - 1) {
							listRndMinistriesandoffices = listRndMinistriesandoffices + ", ";
						}
					}
					System.out.println(listRndMinistriesandoffices);

					// 국가연구개발사업정보-연구사업명
					NodeList _listRndProject = element3.getElementsByTagName("ResearchProjectName");

					String listRndProject = "";
					for (int x = 0; x < _listRndProject.getLength(); x++) {
						lines = (Element) _listRndProject.item(x);
						listRndProject = listRndProject + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listRndProject.getLength() - 1) {
							listRndProject = listRndProject + ", ";
						}
					}
					System.out.println(listRndProject);

					// 국가연구개발사업정보-연구과제명
					NodeList _listRndTask = element3.getElementsByTagName("ResearchTaskName");

					String listRndTask = "";
					for (int x = 0; x < _listRndTask.getLength(); x++) {
						lines = (Element) _listRndTask.item(x);
						listRndTask = listRndTask + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listRndTask.getLength() - 1) {
							listRndTask = listRndTask + ", ";
						}
					}
					System.out.println(listRndTask);

					// 국가연구개발사업정보-주관기관명
					NodeList _listRndInstitution = element3.getElementsByTagName("ManagementInstitutionName");

					String listRndInstitution = "";
					for (int x = 0; x < _listRndInstitution.getLength(); x++) {
						lines = (Element) _listRndInstitution.item(x);
						listRndInstitution = listRndInstitution + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listRndInstitution.getLength() - 1) {
							listRndInstitution = listRndInstitution + ", ";
						}
					}
					System.out.println(listRndInstitution);

					// 패밀리정보-국가코드
					NodeList _listFamilycountryCode = element4.getElementsByTagName("countryCode");

					String listFamilycountryCode = "";
					for (int x = 0; x < _listFamilycountryCode.getLength(); x++) {
						lines = (Element) _listFamilycountryCode.item(x);
						listFamilycountryCode = listFamilycountryCode
								+ getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listFamilycountryCode.getLength() - 1) {
							listFamilycountryCode = listFamilycountryCode + ", ";
						}
					}
					System.out.println(listFamilycountryCode);

					// 패밀리정보-국가명
					NodeList _listFamilycountryName = element4.getElementsByTagName("countryName");

					String listFamilycountryName = "";
					for (int x = 0; x < _listFamilycountryName.getLength(); x++) {
						lines = (Element) _listFamilycountryName.item(x);
						listFamilycountryName = listFamilycountryName
								+ getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listFamilycountryName.getLength() - 1) {
							listFamilycountryName = listFamilycountryName + ", ";
						}
					}
					System.out.println(listFamilycountryName);

					// 패밀리정보-패밀리종류
					NodeList _listFamilyKind = element4.getElementsByTagName("familyKind");

					String listFamilyKind = "";
					for (int x = 0; x < _listFamilyKind.getLength(); x++) {
						lines = (Element) _listFamilyKind.item(x);
						listFamilyKind = listFamilyKind + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listFamilyKind.getLength() - 1) {
							listFamilyKind = listFamilyKind + ", ";
						}
					}
					System.out.println(listFamilyKind);

					// 패밀리정보-패밀리번호
					NodeList _listFamilyNumber = element4.getElementsByTagName("familyNumber");

					String listFamilyNumber = "";
					for (int x = 0; x < _listFamilyNumber.getLength(); x++) {
						lines = (Element) _listFamilyNumber.item(x);
						listFamilyNumber = listFamilyNumber + getCharacterDataFromElement(lines).replace("'", "\\'");
						;

						if (x < _listFamilyNumber.getLength() - 1) {
							listFamilyNumber = listFamilyNumber + ", ";
						}
					}
					System.out.println(listFamilyNumber);

					int num = count + 1;
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();

					System.out.println("Data count: " + num + " - " + dateFormat.format(cal.getTime()));

					FileWriter fw = new FileWriter("DataCountLog.txt", true);
					fw.write("Data count: " + num + " - " + applicationNumber + " - " + dateFormat.format(cal.getTime()) + "\r\n");
					fw.flush();
					fw.close();

//				time = System.currentTimeMillis();
//				dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//				str = dayTime.format(new Date(time));
//				System.out.println("* 4 " + str);

//				DBInsert di = new DBInsert();
					
					insertData(pt_table, applicationNumber, inventionTitle.replace("'", "\""),
							inventionTitleEng.replace("'", "\'"), applicationDate, registerNumber, registerDate,
							registerStatus, originalApplicationKind, claimCount, inventorName.replace("'", "\""),
							listCPC.replace("'", "\""), listIPC.replace("'", "\""), openNumber, openDate, publicationNumber,
							publicationDate, applicantName.replace("'", "\""), astrtCont.replace("'", "\""),
							listRndMinistriesandoffices, listRndProject, listRndTask, listRndInstitution,
							listFamilycountryCode, listFamilycountryName, listFamilyKind, listFamilyNumber);
					
					insertData(pt_claim_table, applicationNumber, claim.replace("'", "\""));
					
//				time = System.currentTimeMillis();
//				dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//				str = dayTime.format(new Date(time));
//				System.out.println("* 5 " + str);

//				DataSave ds = new DataSave();
//				sql = ds.setData(sql, count, id, title, adv, gdv, author, listCPC, listIPC, gid, odv, oid, applicant, abstracts);

				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof IOException) {
						try {
							System.out.println("Time interval by IOException.");
							Thread.sleep(3000);
							count--;
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						} 
					} else if (e instanceof NullPointerException) {
						System.out.print("NullPointer 처리 : " + appNum.elementAt(count) + " \t ErrorCnt : \t" + ++errorcount + " \t ");
						continue;		
					}
				} 
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
	
	
	public void insertData(String tblName, String applicationNumber, String claim) {
		try {
			
			String sql;
			String columns="applicationNumber, claim";
			
			sql = "INSERT INTO ";
			sql += tblName + " (";
			sql += columns + ") VALUES ('" + applicationNumber + "', '" + claim + "');";
			
			System.out.println(sql);
			
			String fileName = this.year;
			fileName+="_SQL_Insert_PT_Err.txt";
			File f = new File(fileName);
						
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(f, true));
			
			if (!db.execute(sql)) {
				System.out.println("PT_Claim_fails : " + ++claim_fail_cnt + "\n\n");
				String tmp = null;
				tmp = ++currentLine + "\t" + sql;
				bw.write(tmp);
				bw.newLine();				
			} else {
				System.out.println("PT_Claim_Success : " + ++claim_success_cnt+ "\n");
			}
			
			bw.flush();
			bw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void insertData(String tblName, String applicationNumber, String inventionTitle, String inventionTitleEng,
			String applicationDate, String registerNumber, String registerDate, String registerStatus,
			String originalApplicationKind, String claimCount, String inventorName, String listCPC, String listIPC,
			String openNumber, String openDate, String publicationNumber, String publicationDate, String applicantName,
			String astrtCont, String listRndMinistriesandoffices, String listRndProject, String listRndTask,
			String listRndInstitution, String listFamilycountryCode, String listFamilycountryName,
			String listFamilyKind, String listFamilyNumber) {
		
		try {
			
			String columns = "applicationNumber, inventionTitle, inventionTitleEng, applicationDate, registerNumber, registerDate, registerStatus, originalApplicationKind, claimCount,"
					+ "inventorName, listCPC, listIPC, openNumber, openDate, publicationNumber, publicationDate, applicantName, astrtCont, listRndMinistriesandoffices, listRndProject,"
					+ "listRndTask, listRndInstitution, listFamilycountryCode, listFamilycountryName, listFamilyKind, listFamilyNumber";
			String sql;
			sql = "INSERT INTO ";
			sql += tblName + " (";
			sql += columns + ") VALUES ('" + applicationNumber + "', '" + inventionTitle + "', '"
					+ inventionTitleEng + "', '" + applicationDate + "', '" + registerNumber + "', '" + registerDate
					+ "', '" + registerStatus + "', '" + originalApplicationKind + "', '" + claimCount + "', '"
					+ inventorName + "', '" + listCPC + "', '" + listIPC + "', '" + openNumber + "', '" + openDate
					+ "', '" + publicationNumber + "', '" + publicationDate + "', '" + applicantName + "', '"
					+ astrtCont + "', '" + listRndMinistriesandoffices + "', '" + listRndProject + "', '" + listRndTask
					+ "', '" + listRndInstitution + "', '" + listFamilycountryCode + "', '" + listFamilycountryName
					+ "', '" + listFamilyKind + "', '" + listFamilyNumber + "');";

			System.out.println(sql);
			
			String fileName = this.year;
			fileName+="_SQL_Insert_PT_Err.txt";
			File f = new File(fileName);
			
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(f, true));
			
			if (!db.execute(sql)) {
				System.out.println("PT_Main_fails : " + ++fail_cnt + "\n\n");
				String tmp = null;
				tmp = ++currentLine + "\t" + sql;
				bw.write(tmp);
				bw.newLine();				
			} else {
				System.out.println("PT_Main_Success : " + ++success_cnt + "\n");
			}
			
			bw.flush();
			bw.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
