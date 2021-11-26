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
    boolean isStudents = false;
    boolean isSubjects = false;
    boolean isCourses = false;
    boolean isStudent = false;
    boolean isSubject = false;
    boolean isCourse = false;
    Student student;
    Course course;
    Subject subject;
    ArrayList<Student> studentList;
    ArrayList<Course> courseList;
    ArrayList<Subject> subjectList;

    public XMLReader() {
        studentList = new ArrayList<>();
        courseList = new ArrayList<>();
        subjectList = new ArrayList<>();
    }

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
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;
        if (qName.equalsIgnoreCase("vtinstitute"))
        {
            isVTInstitute = true;
        }
        if(qName.equalsIgnoreCase("students"))
        {
            if(!isStudents)
                isStudents = true;
            else
                throw new SAXException("XML file not valid");
        }
        if(qName.equalsIgnoreCase("student"))
        {
            if(!isCourse)
            {
                isStudent = true;
                student = new Student();
                student.setIdCard(attributes.getValue("id"));
            }
            else
                throw new SAXException("XML file not valid");
        }
        if(qName.equalsIgnoreCase("courses"))
        {
            if(!isCourses)
                isCourses = true;
            else
                throw new SAXException("XML file not valid");
        }
        if(qName.equalsIgnoreCase("course"))
        {
            if(!isCourse)
            {
                isCourse = true;
                course = new Course();
                course.setCode(Integer.parseInt(attributes.getValue("id")));
            }
            else
                throw new SAXException("XML file not valid");
        }
        if(qName.equalsIgnoreCase("subjects"))
        {
            if(!isSubjects)
                isSubjects = true;
            else
                throw new SAXException("XML file not valid");
        }
        if(qName.equalsIgnoreCase("subject"))
        {
            if(!isSubject)
            {
                isSubject = true;
                subject = new Subject();
                subject.setCode(Integer.parseInt(attributes.getValue("id")));
                subject.setCourseID(Integer.parseInt(attributes.getValue("course")));
            }
            else
                throw new SAXException("XML file not valid");
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
                if(isStudent && isStudents)
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
                if(isCourse && isCourses)
                {
                    if(qName.equalsIgnoreCase("name"))
                    {
                        course.setName(tagContent);
                    }
                }
                if(isSubject && isSubjects)
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
                    if(isStudent)
                    {
                        isStudent = false;
                        studentList.add(student);
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
                if(qName.equalsIgnoreCase("students"))
                {
                    if(isStudents)
                    {
                        isStudents = false;
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
                if(qName.equalsIgnoreCase("course"))
                {
                    if(isCourse)
                    {
                        isCourse = false;
                        courseList.add(course);
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
                if(qName.equalsIgnoreCase("courses"))
                {
                    if(isCourses)
                    {
                        isCourses = false;
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
                if(qName.equalsIgnoreCase("subject"))
                {
                    if(isSubject)
                    {
                        isSubject = false;
                        subjectList.add(subject);
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
                if(qName.equalsIgnoreCase("subjects"))
                {
                    if(isSubjects)
                    {
                        isSubjects = false;
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
                if(qName.equalsIgnoreCase("vtinstitute"))
                {
                    if(isVTInstitute)
                    {
                        isVTInstitute = false;
                    }
                    else
                        throw new SAXException("XML file not valid");
                }
            }
        }
    }
}
