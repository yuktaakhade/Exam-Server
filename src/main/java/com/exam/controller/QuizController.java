package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //add
    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        Quiz quiz1 = quizService.addQuiz(quiz);
        return new ResponseEntity<>(quiz1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/")
    public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(quizService.updateQuiz(quiz));
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<?> getAllQuiz(){
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    //get single quiz
    @GetMapping("/{qid}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable ("qid") Long qid){
        return ResponseEntity.ok(quizService.getQuiz(qid));
    }

    //delete
    @DeleteMapping("/{qid}")
    public ResponseEntity<String> deleteQuiz(@PathVariable ("qid") Long qid){
        quizService.deleteQuiz(qid);
        return new ResponseEntity<>("Quiz id: "+qid+" is deleted!!",HttpStatus.OK);
    }

    @GetMapping("/category/{cid}")
    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid){
        Category category = new Category();
        category.setCid(cid);
        return quizService.getQuizzesOfcategory(category);
    }

    //get active quizzes
    @GetMapping("/active")
    public List<Quiz> getActiveQuizzes(){
        return quizService.getActiveQuizzes();
    }

    //get active quizzes of category
    @GetMapping("/category/active/{cid}")
    public List<Quiz> getActiveQuizzes(@PathVariable("cid") Long cid){
        Category category = new Category();
        category.setCid(cid);
        return quizService.getActiveQuizzesOfCategory(category);
    }

}
