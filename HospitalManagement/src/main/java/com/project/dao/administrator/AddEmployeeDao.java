package com.project.dao.administrator;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.project.dao.LoginDao;
import com.project.entity.Employee;
import com.project.entity.IdGenerate;
import com.project.entity.Login;

@Component
public class AddEmployeeDao 
{
	@Autowired
	private SessionFactory sf;	//hibernate configuration in springMVC-servlet.xml file
	
	@Autowired
	LoginDao infoLog;
	
	//to manage transaction by itself
	@Transactional
	public boolean add(Employee e)
	{
		//try
		//{
			//currentdate is stored
			Date date= new Date();
			e.setJoiningDate(date);
			System.out.println("join date"+date);
			e.setStatus(1);
			
			infoLog.logActivities("in AddEmployeeDao-add: got= "+e);
			System.out.println("add employee"+e);
			
			//storing info in Login table
			String id=e.getEid();
			System.out.println(id);
			Session session= sf.getCurrentSession();
			//Transaction tr= session.beginTransaction();
			session.save(e);
			
			String role=e.getRole();
			String username=e.getEid();
            System.out.println("employeeid"+e.getEid());
			String password=BCrypt.hashpw(e.getAdharNo()+"", BCrypt.gensalt());
			System.out.println("password"+password);
			infoLog.logActivities("aadhar no= "+e.getAdharNo()+", generated hash= "+password);
			System.out.println("password"+e.getAdharNo());
			Login l= new Login(id, role, username, password);
			infoLog.logActivities(""+l);
			
			session.save(l);
			 
			//incrementing eid of idgenerate table contents
			Query q1=session.createQuery(" from IdGenerate");//hql
			IdGenerate temp= (IdGenerate) q1.uniqueResult();
			int eid=temp.getEid();
			eid++;
			q1=session.createQuery("update IdGenerate set eid= :e");
			q1.setParameter("e", eid);
			int res= q1.executeUpdate();
			infoLog.logActivities("incremented eid "+res);
			return true;
		//}
		//catch(Exception ex)
		//{
			
			
	//		infoLog.logActivities("in AddEmployeeDao-add: "+ex);
		//	return false;
		//}
	}
}
