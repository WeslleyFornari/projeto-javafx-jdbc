package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao depDao = DaoFactory.createDepartmentDao(); //INJECÃO DE DEPENDENCIA
	
	public List<Department> findAll(){
		
		return depDao.findAll(); // FAZ A BUSCA NO BANCO DE DADOS
		
		
	}
	

	
	
}
