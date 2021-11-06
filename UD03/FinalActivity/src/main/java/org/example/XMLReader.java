package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLReader extends DefaultHandler {
    protected String currentTag;
    protected String tagContent;
    boolean isVTInstitute = false;
    boolean isStudent = false;
    boolean isSubject = false;
    boolean isCourse = false;
    Student student;
    Course course;
    Subject subject;
    ArrayList<Student> studentList;
    ArrayList<Course> courseList;
    ArrayList<Subject> subjectList;

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public ArrayList<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    // Tag opening found
    //
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        currentTag = qName;
        if (qName.equalsIgnoreCase("VTInstitute"))
        {
            isVTInstitute = true;
        }
        if(qName.equalsIgnoreCase("students"))
        {
            studentList = new ArrayList<>();
        }
        if(qName.equalsIgnoreCase("student"))
        {
            isStudent = true;
            student = new Student();
            student.setIdCard(Integer.parseInt(attributes.getValue("id")));
        }
        if(qName.equalsIgnoreCase("courses"))
        {
            courseList = new ArrayList<>();
        }
        if(qName.equalsIgnoreCase("course"))
        {
            isCourse = true;
            course = new Course();
            course.setCode(Integer.parseInt(attributes.getValue("id")));
        }
        if(qName.equalsIgnoreCase("subjects"))
        {
            subjectList = new ArrayList<>();
        }
        if(qName.equalsIgnoreCase("subject"))
        {
            isSubject = true;
            subject = new Subject();
            subject.setCode(Integer.parseInt(attributes.getValue("id")));
            subject.setCourseID(Integer.parseInt(attributes.getValue("course")));
        }
    }
    // Tag content, usually CDATA
    //
    public void characters( char ch[], int start, int length ) throws SAXException {
        tagContent = new String( ch, start, length );
    }
    // Tag ending
    //
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ( !currentTag.isBlank() ) {
            if (isVTInstitute)
            {
                if(isStudent)
                {
                    if(qName.equalsIgnoreCase("firstname"))
                    {
                        student.setFirstName(tagContent);
                    }
                    if(qName.equalsIgnoreCase("lastname"))
                    {
                        student.setLastName(tagContent);
                    }
                    if(qName.equalsIgnoreCase("email"))
                    {
                        student.setEmail(tagContent);
                    }
                    if(qName.equalsIgnoreCase("phone"))
                    {
                        student.setPhone(tagContent);
                    }
                }
                if(isCourse)
                {
                    if(qName.equalsIgnoreCase("name"))
                    {
                        course.setName(tagContent);
                    }
                }
                if(isSubject)
                {
                    if(qName.equalsIgnoreCase("name"))
                    {
                        subject.setName(tagContent);
                    }
                    if(qName.equalsIgnoreCase("hours"))
                    {
                        subject.setHours(Integer.parseInt(tagContent));
                    }
                    if(qName.equalsIgnoreCase("year"))
                    {
                        subject.setYear(Integer.parseInt(tagContent));
                    }
                }

                if(qName.equalsIgnoreCase("student"))
                {
                    isStudent = false;
                    studentList.add(student);
                }
                if(qName.equalsIgnoreCase("course"))
                {
                    isCourse = false;
                    courseList.add(course);
                }
                if(qName.equalsIgnoreCase("subject"))
                {
                    isSubject = false;
                    subjectList.add(subject);
                }
            }
        }
    }

}
