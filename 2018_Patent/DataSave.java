package Kipris_Open_API;

public class DataSave {
	public StringBuffer setData(StringBuffer sql, int count, String id, String title, String adv, String gdv, String author, String listCPC, String listIPC,
			String gid, String odv, String oid, String applicant, String abstracts) {
		DBInsert dbQuery = new DBInsert();
		
		while (count < 1242492) {
			if (count % 1000 == 0 && (count + 1) < 1242492) {
				sql = new StringBuffer(
						"INSERT INTO patent (id, title, adv, gdv, author, listCPC, listIPC, gid, odv, oid, applicant, abstract) VALUES ");
			} else if ((count + 1) > 1242492) {
				sql = new StringBuffer(
						"INSERT INTO patent (id, title, adv, gdv, author, listCPC, listIPC, gid, odv, oid, applicant, abstract) VALUES ");
			}
			sql.append("('" + id + "', '");
			sql.append(title + "', '");
			sql.append(adv + "', '");
			sql.append(gdv + "', '");
			sql.append(author + "', '");
			sql.append(listCPC + "', '");
			sql.append(listIPC + "', '");
			sql.append(gid + "', '");
			sql.append(odv + "', '");
			sql.append(oid + "', '");
			sql.append(applicant + "', '");
			sql.append(abstracts);

			if ((count + 1) % 1000 == 0) {
				sql.append("');");
				dbQuery.insertQuery(sql);
				sql = null;
				return sql;
			} else if ((count + 1) % 1000 != 0 && (count + 1) < 1242000) {
				sql.append("'), ");
				return sql;
			} else {
				sql.append("');");
				dbQuery.insertQuery(sql);
				sql = null;
				return sql;
			}
		}
		return sql;
	}
}
