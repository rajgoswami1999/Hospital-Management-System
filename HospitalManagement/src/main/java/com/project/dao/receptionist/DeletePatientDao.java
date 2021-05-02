package com.project.dao.receptionist;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dao.LoginDao;
import com.project.dao.doctor.PatientHistoryDao;
import com.project.entity.OpdDetails;

@Component
public class DeletePatientDao 
{
	@Autowired
	SessionFactory sf;
	
	@Autowired
	LoginDao infoLog;
		
	@Transactional
	public int delete(String pid) 
	{
		infoLog.logActivities("in DeletePatientDao-delete: got= "+pid);
		
		Session session= sf.getCurrentSession();
		Query q1=session.createQuery("update Opd set status=0 where pid= :id AND status=1");
		q1.setParameter("id", pid);
		
		try {
			int res= q1.executeUpdate();
			infoLog.logActivities("in DeletePatientDao-delete:update status="+res);
			return res;
			}
		catch(Exception e)
		{
			infoLog.logActivities("in DeletePatientDao-delete: "+e);
			return 0;
		}
		
	}

	@Transactional
	public void prescriptionPrint(String pid) 
	{
		infoLog.logActivities("in prescription print dao ");
		Session session= sf.getCurrentSession();
		Query q1=session.createQuery("update Opd set status=2 where pid= :id AND status=1");
		q1.setParameter("id", pid);
		
		int res= q1.executeUpdate();
		infoLog.logActivities(""+res);		
	}

	@Transactional
	public int prescriptionPrintDone(String pid) 
	{
		infoLog.logActivities("in DeletePatientDao-prescriptionPrintDone: got= "+pid);
		
		Session session= sf.getCurrentSession();
		Query q1=session.createQuery("update Opd set status=0 where pid= :id AND status=2");
		q1.setParameter("id", pid);
		
		try {
			int res= q1.executeUpdate();
			return res;
			}
		catch(Exception e)
		{
			infoLog.logActivities("in DeletePatientDao-prescriptionPrintDone:"+e);
			return 0;
		}
	}

}
