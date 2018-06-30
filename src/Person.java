/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 * this class is only to be extended
 */
public abstract class Person {

    private int id;
    private String name;
    private AccessLevel accessLevel;

    private void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return this.id;
    }

    public void setAccessLevel(int accesscode) {

        /**
         * 0 = student 1 = teacher 2 = admin
         *
         */
        switch (accesscode) {
            case 0:
                this.accessLevel = AccessLevel.STUDENT;
                break;
            case 1:
                this.accessLevel = AccessLevel.TEACHER;
                break;
            case 2:
                this.accessLevel = AccessLevel.ADMIN;
                break;

        }

    }

    public AccessLevel getAccessLevel() {

        return this.accessLevel;
    }

    //this is called while login in
    public boolean login(String username, String password) {

        return true;
    }
    
    //this is called to see results either by class or by student
    public void viewRecords(String subject){
    
    }

}
