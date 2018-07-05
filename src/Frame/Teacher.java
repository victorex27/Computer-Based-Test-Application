package Frame;


import Frame.Course;
import Frame.SimpleConnection;
import Frame.Question;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class Teacher extends Person {

    private ArrayList<String> listOfClasses;
    private ArrayList<String> listOfSubjects;
    private static final ArrayList<String> ALLOWEDEXTENSION = new ArrayList<>();

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    private static Connection connection;

    private ArrayList<Question> allQuestions;
    // this is the minimum number of options
    private final static int NO_OF_OPTIONS = 3;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public Teacher() {

        /**
         * These are the allowed extension for version 1 which is csv
         *
         */
        ALLOWEDEXTENSION.add("csv");

        allQuestions = new ArrayList();

    }

    private boolean isFormatValid(String uri) throws Exception {

        String ext = FilenameUtils.getExtension(uri);

        //
        if (!ALLOWEDEXTENSION.contains(ext)) {
            throw new Exception("Invalid File Format");
        }
        System.out.println("File Format is valid");
        return true;
    }

    private boolean isFileValid(File file) throws Exception {

        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        System.out.println("File is valid");
        return true;
    }

    public void parseFile(File file) {

    }

    //this is called when the Teacher is about to set the exam questions.
    public boolean setExam(String subject, String uri) throws Exception {

        File file = new File(uri);

        isFormatValid(uri);

        isFileValid(file);

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNext()) {

                int count = 0;

                Question q = null;
                String questionText = null;
                String optionA = null;
                String optionB = null;
                String optionC = null;
                String optionD = null;
                String optionE = null;

                //Question q = new Question.Builder(question, a, b).build();
                List<String> line = parseLine(scanner.nextLine());
                int size = line.size();
                if (size - 1 < NO_OF_OPTIONS) {
                    throw new Exception("Your Options must be More than three");
                }
                while (count < size) {

                    switch (count) {

                        case 0:
                            questionText = line.get(count);
                            break;
                        case 1:
                            optionA = line.get(count);
                            break;
                        case 2:
                            optionB = line.get(count);
                            break;
                        case 3:
                            optionC = line.get(count);
                            break;
                        case 4:
                            optionD = line.get(count);
                            break;
                        case 5:
                            optionE = line.get(count);
                            break;

                    }

                    count++;
                }

                q = new Question.Builder(questionText, optionA, optionB).addC(optionC).addD(optionD).addE(optionE).build();
                allQuestions.add(q);

            }
        }

        addToDatabase(/*Question q */);
        return true;
    }

    private PreparedStatement setQueryValues(PreparedStatement pStatement) {

        allQuestions.forEach(q -> {

            int a = 7 * atomicInteger.getAndIncrement();

            try {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, "pStatement", "");
                pStatement.setInt(a + 1, 1);
                pStatement.setString(a + 2, q.getQuestion());
                pStatement.setString(a + 3, q.getA());
                pStatement.setString(a + 4, q.getB());
                pStatement.setString(a + 5, q.getC());
                pStatement.setString(a + 6, q.getD());
                pStatement.setString(a + 7, q.getE());

            } catch (Exception ex) {
                Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return pStatement;

    }

    private void addToDatabase(/*Question q */) throws SQLException {

        try {
            int sizeOfQuestion = allQuestions.size();
            if (sizeOfQuestion < 1) {
                throw new Exception("Invalid Operation");
            }

           

            String sqlQuery = "INSERT INTO exam_question(teacher_id, question, a, b, c, d, e) VALUES (?,?,?,?,?,?,?) ";

            while (sizeOfQuestion > 1) {

                sqlQuery += " ,(?,?,?,?,?,?,?) ";

                sizeOfQuestion--;
            }

            connection = SimpleConnection.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

            pStatement = setQueryValues(pStatement);
            System.out.println(""+pStatement.executeUpdate());

            
                

        } catch (Exception ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
        
            connection.close();
        }
    }

    private static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    private static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    private static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

    public ArrayList<Course> getCourses() throws SQLException, ClassNotFoundException, Exception {

        ArrayList<Course> subjects = new ArrayList<>();
        connection = SimpleConnection.getConnection();

        String sqlQuery = "SELECT course.id, course_code, course_title "
                + "FROM course "
                + "INNER JOIN teacher "
                + "ON course.id = teacher.course_id "
                + "where teacher.person_id = ? ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, this.getId());

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            throw new Exception("No record Found");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                subjects.add(new Course(resultSet.getInt("id"), resultSet.getString("course_code"), resultSet.getString("course_title")));

            }
        }

        connection.close();

        return subjects;

    }

    public ArrayList<Question> getAllQuestions() {
        return allQuestions;
    }
}
