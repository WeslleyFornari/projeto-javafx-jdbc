package model.dao;

import db.DB;

import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

//CLASSE QUE FAZ A LIGAÇÃO (CONEXÃO DB (conn, statement, resultset) ENTRE A INTERFACE DAO E O JDBC

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
