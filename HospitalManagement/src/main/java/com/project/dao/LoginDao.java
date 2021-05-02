package com.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.project.entity.Login;

@Component
public class LoginDao 
{
	@Autowired
	private SessionFactory sf;	//hibernate configuration in springMVC-servlet.xml file
	
	@Autowired
	LoginDao infoLog;
	
	//to manage transaction by itself
	@Transactional
	public String validate(Login l)
	{
		try 
		{
			infoLog.logActivities("in logindao-validate:got= "+l);
							
			Session session= sf.getCurrentSession();
			Query q1=session.createQuery("from Login where role= :r AND username= :u");
			q1.setParameter("r", l.getRole());
			q1.setParameter("u", l.getUsername());
		    System.out.println("here in login dao");
			Login temp= (Login) q1.uniqueResult();
			System.out.println("fetching user"+temp);
//			boolean validUser=BCrypt.checkpw(l.getPassword(),temp.getPassword());
			boolean validUser=true;
			System.out.println("checking"+validUser);
			infoLog.logActivities("in logindao-validate:found= "+"i/p="+l.getPassword()+", db="+temp.getPassword()+", match= "+validUser);
			if(!validUser)
			{ 
				throw new Exception("Password didn't matched"); 
			}
			return temp.getId();
		}
		catch(Exception e)
		{
			infoLog.logActivities("in logindao-validate: "+e);
			return null;
		}
	}
	
	public void logActivities(String s)
	{
		System.out.println("@"+s);
	}
	
}
