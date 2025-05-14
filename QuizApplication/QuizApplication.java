import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizApplication extends JFrame implements ActionListener{
    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionGroup;
    private JButton actionButton;
    private JLabel timerLabel;
    private Timer timer;
    private int timeRemaining = 15; // 10 seconds per question

    private int currentQuestion = 0;
    private int score = 0;

    private String[] questions = {
            "1. What is the capital of Japan?",
            "2. JVM stands for?",
            "3. What is the largest ocean on Earth?",
            "4. Who is the author of 'To Kill a Mockingbird'?",
            "5. Which country is known as the 'Land of the Rising Sun'?"
    };

    private String[] correctAnswers = {
            "Tokyo",
            "Java Virtual Machine",
            "Pacific",
            "Harper Lee",
            "Japan"
    };

    private String[][] options = {
            {"Seoul", "Beijing", "Tokyo", "Bangkok"},
            {"Java Verified Machine", "Java Virtual Machine", "Java Development kit", "Java Runtime Environment"},
            {"Atlantic", "Indian", "Arctic", "Pacific"},
            {"Mark Twain", "Harper Lee", "Jane Austen", "F. Scott Fitzgerald"},
            {"China", "India", "Japan", "South Korea"}
    };

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(600, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Set background color

        // Create components
        questionLabel = new JLabel(questions[currentQuestion]);
        option1 = new JRadioButton(options[currentQuestion][0]);
        option2 = new JRadioButton(options[currentQuestion][1]);
        option3 = new JRadioButton(options[currentQuestion][2]);
        option4 = new JRadioButton(options[currentQuestion][3]);

        // Set background color for options to white
        option1.setBackground(Color.WHITE);
        option2.setBackground(Color.WHITE);
        option3.setBackground(Color.WHITE);
        option4.setBackground(Color.WHITE);

        Font questionFont = new Font("SansSerif", Font.BOLD, 16);
        Font optionFont = new Font("Comic Sans MS ", Font.PLAIN, 14);

        questionLabel.setFont(questionFont);
        option1.setFont(optionFont);
        option2.setFont(optionFont);
        option3.setFont(optionFont);
        option4.setFont(optionFont);

        optionGroup = new ButtonGroup();
        optionGroup.add(option1);
        optionGroup.add(option2);
        optionGroup.add(option3);
        optionGroup.add(option4);

        actionButton = new JButton("Next");
        timerLabel = new JLabel("Time: " + timeRemaining + "s");

        // Set layout to null
        setLayout(null);

        // Set bounds for components
        questionLabel.setBounds(50, 70, 500, 50);
        option1.setBounds(50, 120, 500, 30);
        option2.setBounds(50, 160, 500, 30);
        option3.setBounds(50, 200, 500, 30);
        option4.setBounds(50, 240, 500, 30);
        actionButton.setBounds(250, 320, 100, 30);
        timerLabel.setBounds(500, 30, 500, 30);

        // Add components to the frame
        add(questionLabel);
        add(option1);
        add(option2);
        add(option3);
        add(option4);
        add(actionButton);
        add(timerLabel);

        actionButton.addActionListener(this);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.start();
    }
    public void actionPerformed(ActionEvent e){
        if (currentQuestion == questions.length - 1) {
            checkAnswer();
            submitQuiz();
        }
        else {
            checkAnswer();
            if (currentQuestion == questions.length - 1) {
                actionButton.setText("Submit");
            }
        }
    }
    private void checkAnswer() {
        String userAnswer = getUserAnswer();

        if (userAnswer.equals(correctAnswers[currentQuestion])) {
            score++;
        }

        if (currentQuestion < questions.length - 1) {
            currentQuestion++;
            updateQuestion();
        }
    }

    private void updateQuestion() {
        questionLabel.setText(questions[currentQuestion]);
        option1.setText(options[currentQuestion][0]);
        option2.setText(options[currentQuestion][1]);
        option3.setText(options[currentQuestion][2]);
        option4.setText(options[currentQuestion][3]);

        optionGroup.clearSelection();
        timeRemaining = 15;
        timerLabel.setText("Time: " + timeRemaining + "s");
        timer.restart();
    }

    private String getUserAnswer() {
        if (option1.isSelected()) {
            return option1.getText();
        } else if (option2.isSelected()) {
            return option2.getText();
        } else if (option3.isSelected()) {
            return option3.getText();
        } else if (option4.isSelected()) {
            return option4.getText();
        }
        return "";
    }

    private void submitQuiz() {
        timer.stop();
        int totalQuestions = questions.length;
        int incorrectAnswers = totalQuestions - score;

        String resultMessage = String.format("Quiz Completed!\nYour Score: %d/%d\nIncorrect Answers: %d", score, totalQuestions, incorrectAnswers);

        JFrame resultFrame = new JFrame("Quiz Result");
        resultFrame.setSize(400, 300);
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setLayout(new BorderLayout());

        JTextArea resultTextArea = new JTextArea(resultMessage);
        resultTextArea.setEditable(false);

        JButton closeButton = new JButton("Close");
        JButton tryAgainButton = new JButton("Try Again");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultFrame.dispose();
                System.exit(0);
            }
        });

        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultFrame.dispose();
                resetQuiz();
            }
        });

        resultFrame.add(new JScrollPane(resultTextArea), BorderLayout.CENTER); // Use JScrollPane for multiline text
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(closeButton);
        buttonPanel.add(tryAgainButton);
        resultFrame.add(buttonPanel, BorderLayout.SOUTH);

        resultFrame.setVisible(true);
    }

    private void resetQuiz() {
        currentQuestion = 0;
        score = 0;
        updateQuestion();
    }

    private void updateTimer() {
        if (timeRemaining > 0) {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + "s");
        } else {
            timer.stop();
            if (currentQuestion < questions.length - 1) {
                checkAnswer();
            } else {
                submitQuiz();
            }
        }
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}
