# Patent

## 키프리스 Open API 활용
[Link]("http://www.kipris.or.kr/khome/main.jsp", "Kipris link")

## DB(DDL)

### Patent 서지정보 
<pre>
<code>
CREATE TABLE `patent` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `applicationNumber` varchar(45) DEFAULT NULL,
  `inventionTitle` varchar(1000) DEFAULT NULL,
  `inventionTitleEng` varchar(1000) DEFAULT NULL,
  `applicationDate` varchar(500) DEFAULT NULL,
  `registerNumber` varchar(45) DEFAULT NULL,
  `registerDate` varchar(500) DEFAULT NULL,
  `registerStatus` varchar(45) DEFAULT NULL,
  `originalApplicationKind` varchar(45) DEFAULT NULL,
  `claimCount` varchar(45) DEFAULT NULL,
  `inventorName` varchar(1000) DEFAULT NULL,
  `listCPC` varchar(2000) DEFAULT NULL,
  `listIPC` varchar(2000) DEFAULT NULL,
  `openNumber` varchar(45) DEFAULT NULL,
  `openDate` varchar(500) DEFAULT NULL,
  `publicationNumber` varchar(45) DEFAULT NULL,
  `publicationDate` varchar(500) DEFAULT NULL,
  `applicantName` varchar(1000) DEFAULT NULL,
  `astrtCont` varchar(5000) DEFAULT NULL,
  `listRndMinistriesandoffices` varchar(500) DEFAULT NULL,
  `listRndProject` varchar(500) DEFAULT NULL,
  `listRndTask` varchar(500) DEFAULT NULL,
  `listRndInstitution` varchar(500) DEFAULT NULL,
  `listFamilycountryCode` varchar(100) DEFAULT NULL,
  `listFamilycountryName` varchar(500) DEFAULT NULL,
  `listFamilyKind` varchar(500) DEFAULT NULL,
  `listFamilyNumber` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
</code>
</pre>

### Patent 청구항
<pre>
<code>
CREATE TABLE `patent_claim` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `applicationNumber` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `claim` varchar(8176) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
</code>
</pre>
