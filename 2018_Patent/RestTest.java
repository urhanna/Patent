package Kipris_Open_API;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Vector;

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

public class RestTest {
	public static void main(String[] args) throws NullPointerException, ParserConfigurationException, SAXException {
		StringBuffer sql = null;
		
		StringBuffer apiUrl = null;
		StringBuffer cpcUrl = null;
		String ServiceKey = "cPaV9WQIPuD1LtkSHVTZBsWDbuY/oj4n6lafkmoW=x0=";

		DBSelect dbSelect = new DBSelect();
		String table = "g_kipris";
		Vector<String> appNum = dbSelect.selectAppNum(table);

		for (int count = 0; count < appNum.size(); count++) {
			apiUrl = new StringBuffer("http://plus.kipris.or.kr/kipo-api/kipi/patUtiModInfoSearchSevice/getBibliographyDetailInfoSearch");
			cpcUrl = new StringBuffer("http://plus.kipris.or.kr/openapi/rest/patUtiModInfoSearchSevice/patentCpcInfo");

			apiUrl.append("?applicationNumber=" + appNum.elementAt(count) + "&ServiceKey=" + ServiceKey);
			cpcUrl.append("?applicationNumber=" + appNum.elementAt(count) + "&accessKey=" + ServiceKey);

			try {
				URL url = new URL(apiUrl.toString());
				URL cpcurl = new URL(cpcUrl.toString());

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

				reader.close();
				reader2.close();

				String xmlResult = buffer.toString();
				String cpcResult = buffer2.toString();

				// System.out.println("ForeignPatentBibliographicService/bibliographicInfoRESULT=>" + xmlResult);
				// System.out.println("ForeignPatentBibliographicService/bibliographicInfoRESULT=>" + cpcResult);
				String xmlRecords = xmlResult;
				String cpcRecords = cpcResult;
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource p = new InputSource();
				p.setCharacterStream(new StringReader(xmlRecords));

				Document doc = db.parse(p);
				NodeList nodes = doc.getElementsByTagName("response");

				DocumentBuilder db2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource p2 = new InputSource();
				p2.setCharacterStream(new StringReader(cpcRecords));

				Document doc2 = db2.parse(p2);
				NodeList nodes2 = doc2.getElementsByTagName("response");

				Element element = (Element) nodes.item(0);
				Element element2 = (Element) nodes2.item(0);

				// 출원번호
				String id = "";
				NodeList _id = element.getElementsByTagName("applicationNumber");
				Element lines = (Element) _id.item(0);
				id = getCharacterDataFromElement(lines).replaceAll("-", "");
				// System.out.println(id);

				// 제목
				String title = "";
				NodeList _title = element.getElementsByTagName("inventionTitle");
				lines = (Element) _title.item(0);
				title = getCharacterDataFromElement(lines);
				// System.out.println(title);

				// 출원일자
				String adv = "";
				NodeList _adv = element.getElementsByTagName("originalExaminationRequestDate");
				lines = (Element) _adv.item(0);
				adv = getCharacterDataFromElement(lines).replace(".", "");
				// System.out.println(adv);

				// 등록일자
				String gdv = "";
				NodeList _gdv = element.getElementsByTagName("publicationDate");
				lines = (Element) _gdv.item(0);
				gdv = getCharacterDataFromElement(lines).replace(".", "");
				// System.out.println(gdv);

				// 발명인
				NodeList _author = element.getElementsByTagName("inventorInfo");
				lines = (Element) _author.item(0);

				String author = "";
				for (int j = 0; j < _author.getLength(); j++) {
					Element eventEle = (Element) _author.item(j);
					author = author + getChildren(eventEle, "name").replaceAll(" ", "") + " ";
				}
				// System.out.println(author);

				// CPC 코드 리스트
				NodeList _listCPC = element2.getElementsByTagName("CooperativepatentclassificationNumber");

				String listCPC = "";
				for (int x = 0; x < _listCPC.getLength(); x++) {
					lines = (Element) _listCPC.item(x);
					listCPC = listCPC + getCharacterDataFromElement(lines);

					if (x < _listCPC.getLength() - 1) {
						listCPC = listCPC + ", ";
					}
				}

				if (_listCPC.getLength() != 0) {
					listCPC = "[" + listCPC + "]";
				}
				// System.out.println(listCPC);

				// IPC 코드 리스트
				NodeList _listIPC = element.getElementsByTagName("ipcNumber");

				String listIPC = "";
				for (int x = 0; x < _listIPC.getLength(); x++) {
					lines = (Element) _listIPC.item(x);
					listIPC = listIPC + getCharacterDataFromElement(lines);

					if (x < _listIPC.getLength() - 1) {
						listIPC = listIPC + ", ";
					}
				}

				if (_listIPC.getLength() != 0) {
					listIPC = "[" + listIPC + "]";
				}
				// System.out.println(listIPC);

				// 등록번호
				String gid = "";
				NodeList _gid = element.getElementsByTagName("registerNumber");
				lines = (Element) _gid.item(0);
				gid = getCharacterDataFromElement(lines).replaceAll("-", "");
				// System.out.println(gid);

				// 공개일자
				String odv = "";
				NodeList _odv = element.getElementsByTagName("openDate");
				lines = (Element) _odv.item(0);
				odv = getCharacterDataFromElement(lines).replace(".", "");
				// System.out.println(odv);

				// 공개번호
				String oid = "";
				NodeList _oid = element.getElementsByTagName("openNumber");
				lines = (Element) _oid.item(0);
				oid = getCharacterDataFromElement(lines).replaceAll("-", "");
				// System.out.println(oid);

				// 출원인
				String applicant = "";
				NodeList _applicant = element.getElementsByTagName("name");
				lines = (Element) _applicant.item(0);
				applicant = getCharacterDataFromElement(lines);
				// System.out.println(applicant);

				// 초록
				String abstracts = "";
				NodeList _abstracts = element.getElementsByTagName("astrtCont");
				lines = (Element) _abstracts.item(0);
				abstracts = getCharacterDataFromElement(lines).replace("'", "\"");
				// System.out.println(abstracts);

//				청구항
//				NodeList _claim = element.getElementsByTagName("claim");
//
//				String claim = "";
//				for (int j = 0; j < _claim.getLength(); j++) {
//					lines = (Element) _claim.item(j);
//					claim = claim + getCharacterDataFromElement(lines).replace("'", "\"") + "\n";
//				}
//				System.out.println(claim);
				
				System.out.println("Data count: " + (count + 1));

				DataSave ds = new DataSave();
				sql = ds.setData(sql, count, id, title, adv, gdv, author, listCPC, listIPC, gid, odv, oid, applicant, abstracts);
			} catch (Exception e) {
				e.printStackTrace();
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
}
