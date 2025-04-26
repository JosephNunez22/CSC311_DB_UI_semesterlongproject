package model;

public enum Departments {
    COMPUTER_SCIENCE("Computer Science Department"),
    ENGINEERING("Engineering Department"),
    BIOLOGY("Biology Department"),
    CHEMISTRY("Chemistry Department"),
    PHYSICS("Physics Department"),
    MATHEMATICS("Mathematics Department"),
    HUMANITIES("Humanities Department"),
    ENGLISH("English Department"),
    SOCIAL_SCIENCES("Social Sciences Department");

    private final String question;

    Departments(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static String[] getAllQuestions() {
        Departments[] values = Departments.values();  // Correct enum
        String[] questions = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            questions[i] = values[i].getQuestion();
        }
        return questions;
    }
}
