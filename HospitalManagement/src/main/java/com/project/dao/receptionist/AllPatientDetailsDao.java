package com.project.dao.receptionist;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dao.LoginDao;

import com.project.entity.Login;
import com.project.entity.Patient;

@Component
public class AllPatientDetailsDao 
{
	@Autowired
	SessionFactory sf;
	
	@Autowired
	LoginDao infoLog;
	
	@Transactional
	public List<Patient> getAllPatients()
	{
		try {
			infoLog.logActivities("in AllPatientDetailsDao-getAllPatients: ");
			Session session= sf.getCurrentSession();
			Query q1= session.createQuery("from Patient ");	//HQL use classname not tablename
			
			List<Patient> l=(List<Patient>) q1.list();

			return l;
		}
		catch(Exception e)
		{
			infoLog.logActivities("in AllPatientDetailsDao-getAllPatients: "+e);
			return null;
		}
	}
	
	
}
