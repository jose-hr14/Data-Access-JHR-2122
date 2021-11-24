package org.final_activity;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * The xml handler that will read the xml that contains students, courses and subject that will be saved in the
 * database later.
 * @author José Hernández Riquelme
 */
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

    /**
     * Returns a list of students read from a xml file that the object of this class saves within itself
     * after parsing the xml.
     * @return A list of students
     */
    public ArrayList<Student> getStudentList() {
        return studentList;
    }
    /**
     * Returns a list of courses read from a xml file that the object of this class saves within itself
     * after parsing the xml.
     * @return A list of courses
     */
    public ArrayList<Course> getCourseList() {
        return courseList;
    }
    /**
     * Returns a list of subjects read from a xml file that the object of this class saves within itself
     * after parsing the xml.
     * @return A list of subjects
     */
    public ArrayList<Subject> getSubjectList() {
        return subjectList;
    }
    /**
     * This method defines de behaviour of the parser when parses an opening tag.
     * @param uri
     * @param localName
     * @param qName Tag name
     * @param attributes Tag attributes
     * @throws SAXException This exception will be thrown to be caught in the main form and inform the user of the error.
     */
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
            student.setIdCard(attributes.getValue("id"));
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
    /**
     * This method defines de behaviour of the parser when parses the characters withing a tag.
     * @param ch
     * @param start
     * @param length
     * @throws SAXException This exception will be thrown to be caught in the main form and inform the user of the error.
     */
    public void characters( char ch[], int start, int length ) throws SAXException {
        tagContent = new String( ch, start, length );
    }
    /**
     * This method defines de behaviour of the parser when parses a closing tag. It creates the students,
     * courses of subjects with the data read from the xml file, and adds them to its respective list.
     * The form can easily retrieve de data calling the lists getters from this class.
     * @param uri
     * @param localName
     * @param qName Tag name
     * @throws SAXException This exception will be thrown to be caught in the main form and inform the user of the error.
     */
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
                if(qName.equalsIgnoreCase("VTInstitute"))
                {
                    isVTInstitute = false;
                }
            }
        }
    }
}
