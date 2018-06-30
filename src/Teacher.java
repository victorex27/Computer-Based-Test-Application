
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    private PreparedStatement pStatement;
    private ResultSet resultSet;

    public Teacher() throws SQLException, ClassNotFoundException {

        connection = SimpleConnection.getConnection();

        String queryString = "SELECT * FROM person";
        pStatement = connection.prepareStatement(queryString);
        resultSet = pStatement.executeQuery();
        Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, "started");
        while (resultSet.next()) {

            System.out.println(resultSet.getInt(1));
        }
        Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, "finished");
    }

    private boolean isFormatValid(String uri) throws Exception {
        /**
         * These are the allowed extension for version 1 which is csv
         *
         */
        ALLOWEDEXTENSION.add("csv");

        String ext = FilenameUtils.getExtension(uri);

        //
        if (!ALLOWEDEXTENSION.contains(ext)) {
            throw new Exception("Invalid File Format");
        }
        return true;
    }

    private boolean isFileValid(File file) throws Exception {

        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        return true;
    }

    public void parseFile(File file) {

    }

    //this is called when the Teacher is about to set the exam questions.
    public boolean setExam(String subject, String uri) throws Exception {

        File file = new File(uri);

        isFormatValid(uri);

        isFileValid(file);

        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            List<String> line = parseLine(scanner.nextLine());
            System.out.println("Country [id= " + line.get(0) + ", code= " + line.get(1) + " , name=" + line.get(2) + "]");
        }
        scanner.close();

        return true;
    }

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

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
}
