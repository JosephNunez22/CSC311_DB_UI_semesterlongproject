package model;

public enum Major {
    COMPUTER_SCIENCE("Computer Science"),
    INFORMATION_SYSTEMS("Information Systems"),
    CYBER_SECURITY("Cyber Security"),
    SOFTWARE_ENGINEERING("Software Engineering"),
    DATA_SCIENCE("Data Science"),
    COMPUTER_ENGINEERING("Computer Engineering"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    MECHANICAL_ENGINEERING("Mechanical Engineering"),
    CIVIL_ENGINEERING("Civil Engineering"),
    BIOLOGY("Biology"),
    CHEMISTRY("Chemistry"),
    PHYSICS("Physics"),
    MATHEMATICS("Mathematics"),
    ENGLISH("English"),
    HISTORY("History");

    private final String question;

    Major(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static String[] getAllQuestions() {
        Major[] values = Major.values();
        String[] questions = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            questions[i] = values[i].getQuestion();
        }
        return questions;
    }
}
