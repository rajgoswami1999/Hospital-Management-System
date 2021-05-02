package com.project.controller.receptionist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.dao.LoginDao;


import com.project.dao.receptionist.AllPatientDetailsDao;
import com.project.dao.receptionist.PatientDetailsDao;

import com.project.entity.Patient;

@Controller
public class ShowAllPatientDetailsController 
{
	@Autowired
	AllPatientDetailsDao dao1;
	@Autowired
	PatientDetailsDao dao2;
	@Autowired
	LoginDao infoLog;
	
	@RequestMapping("/allPatientsView.html")
	public ModelAndView view()
	{
		ModelAndView mv= new ModelAndView();
		mv.setViewName("receptionist/AllPatientDetailsView");
		List<Patient> p=dao1.getAllPatients(); 
		System.out.println("patient" + p.size());
		mv.addObject("patients", dao1.getAllPatients());
		return mv;
	}
	
	@RequestMapping(value = "/viewPatient.html", method = RequestMethod.POST)
	public ModelAndView showEmployeeDetailsViewMethod(@RequestParam("pid")String pid)
	{
		try {
			infoLog.logActivities("in ShowAllPatientDetailsController-showPatientDetailsViewMethod: got "+pid);
			Patient l=(Patient) dao2.show(pid);
			if(! l.equals(null))
			{
				ModelAndView mv= new ModelAndView();
				mv.setViewName("receptionist/PatientDetailsView");
				mv.addObject("patient",l);
				return mv;
			}
			else
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			infoLog.logActivities("in ShowAllPatientDetailsController-showPatientDetailsViewMethod: "+e);
			ModelAndView mv= new ModelAndView();
			mv.setViewName("failure");
			mv.addObject("error",e);
			return mv;
		}
	}
	
}
