package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    //add
    @PostMapping("/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        Question question1 = questionService.addQuestion(question);
        return new ResponseEntity<>(question1, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question){
        return ResponseEntity.ok(questionService.updateQuestion(question));
    }

    //get all question of any quiz
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){

        Quiz quiz = quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList(questions);
        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())){
            list = list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }

        list.forEach((q)->{
            q.setAnswer("");
        });

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid){
        Quiz quiz = new Quiz();
        quiz.setQid(qid);
        Set<Question> questionOfQuiz = questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionOfQuiz);
    }

    //get Single
    @GetMapping("/{quesId}")
    public ResponseEntity<Question> getQuestion(@PathVariable ("quesId") Long quesId){
        return ResponseEntity.ok(questionService.getQuestion(quesId));
    }

    //delete
    @DeleteMapping("/{quesId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable ("quesId") Long quesId){
        questionService.deleteQuestion(quesId);
        return new ResponseEntity<>("Question id: "+quesId+" is deleted!!",HttpStatus.OK);
    }

    //eval quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
        System.out.println(questions);
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;
       for(Question q: questions){
           Question question = questionService.get(q.getQuesId());
            if(question.getAnswer().equals(q.getGivenAnswer())){
//                marksGot = 0;
                correctAnswers++;
                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) /questions.size();
                marksGot+= marksSingle;
            }
           if(q.getGivenAnswer()!=null){
                    attempted++;
           }
        }
        Map<String,Object> map = Map.of("marksGot",marksGot, "correctAnswers",correctAnswers,"attempted",attempted);
        return ResponseEntity.ok(map);
    }

}
