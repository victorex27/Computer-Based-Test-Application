
import Frame.Question;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class ExamFile {

    private final ArrayList<Question> allQuestions;

    public ArrayList<Question> getPaper(String courseCode) {

        return allQuestions;
    }

    public ExamFile() {

        allQuestions = new ArrayList<>();

    }

}
