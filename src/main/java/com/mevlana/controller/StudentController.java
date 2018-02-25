package com.mevlana.controller;

import com.mevlana.dao.DatabaseOperationsDAO;
import com.mevlana.entity.Student;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

@ManagedBean
@SessionScoped
public class StudentController
{
    private DatabaseOperationsDAO databaseOperationsDAO;
    private List<Student> students;
    private Logger logger = Logger.getLogger(getClass().getName());

    public  StudentController()
    {
        databaseOperationsDAO=new DatabaseOperationsDAO();
        students=new ArrayList<Student>();
    }

    public void loadStudents()
    {
        students= databaseOperationsDAO.getStudents();
    }

    public String addStudent(Student theStudent)
    {

        logger.info("Adding student: " + theStudent);

        try {

            // add student to the database
            databaseOperationsDAO.addStudent(theStudent);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error adding students", exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-students";
    }

    public String loadStudent(int studentId) {

        logger.info("loading student: " + studentId);

        try {
            // get student from database
            Student theStudent = databaseOperationsDAO.getStudent(studentId);
            System.out.println(theStudent.getId());
            // put in the request attribute ... so we can use it on the form page
            ExternalContext externalContext =
                    FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("student", theStudent);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading student id:" + studentId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "update-student-form.xhtml";
    }

    public String updateStudent(Student theStudent) {

        logger.info("updating student: " + theStudent);

        try {

            // update student in the database
            databaseOperationsDAO.updateStudent(theStudent);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating student: " + theStudent, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-students?faces-redirect=true";
    }

    public List<Student> getStudents()
    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }

    public String deleteStudent(int studentId) {

        logger.info("Deleting student id: " + studentId);

        try {

            // delete the student from the database
            databaseOperationsDAO.deleteStudent(studentId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting student id: " + studentId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-students";
    }


    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
