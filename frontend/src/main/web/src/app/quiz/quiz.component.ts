import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Question} from "../entities/question";
import {QuizService} from "../services/quiz.service";
import {ActivatedRoute} from "@angular/router";
import {QuestionService} from "../services/question.service";
import {SequenceOption} from "../entities/sequence-option";
import {CdkDragDrop, moveItemInArray} from "@angular/cdk/drag-drop";
import {OptionService} from "../services/option.service";
import {Answer} from "../entities/answer";
import {Option} from "../entities/option";
import {UserService} from "../services/user.service";
import {UserSessionResult} from "../entities/UserSessionResult";
import {AchievementService} from "../services/achievement.service";
import {SessionStats} from "../entities/session-stats";
import {NotificationService} from "../services/notification.service";
import {INPUT_QUESTION, OPTIONAL_QUESTION, SEQUENCE_QUESTION, TRUE_FALSE_QUESTION, USER_POINTS} from "../parameters";


@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent implements OnInit, OnDestroy {
  private routeSub: Subscription;
  questionOptionPoints = 0;
  quizId;
  sessionId;
  access_code = '';
  finish = false;
  timeSpent = 0;
  questions: Question[] = [];
  stats: SessionStats[] = [];
  userAnswers: Answer[] = [];
  coefOptional: number;
  topStats: SessionStats[] = [];
  questionOptions = new Map();
  indexQuestion: number = 0;
  timer = 0;
  interval: any = null;
  timeout: any = null;
  optionType = 0;
  numberOfOptions = 4;
  optionalAnswers: Option[];
  optionsSequence: SequenceOption[] = Array.from({length: this.numberOfOptions},() =>
    ({
      serial_num: null,
      text: ''
    }));

  constructor(private quizService: QuizService,
              private optionService: OptionService,
              private route: ActivatedRoute,
              private questionService: QuestionService,
              private achievementService: AchievementService,
              private userService: UserService,
              private notficationService:NotificationService) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.quizId = params['id'];
      this.sessionId = params['sessionId'];
      if(this.userService.user.role.name === "user"){ this.getAccessCode();}
      this.initializeWebSocketConnection();
    });
    this.getQuestions();
  }

  nextQuestion(): void {
    if (this.indexQuestion < this.questions.length - 1) {
      this.indexQuestion++;
      this.startQuestionTimer();
    } else {
      this.interval = null;
      this.finishSession();
    }
    this.optionSwitcher();
  }

  optionSwitcher() {
    switch (+this.questions[this.indexQuestion].type.id) {
      case INPUT_QUESTION:
        this.optionType = INPUT_QUESTION;
        break;
      case TRUE_FALSE_QUESTION:
        this.optionType = TRUE_FALSE_QUESTION;
        break;
      case OPTIONAL_QUESTION: {
        this.setOptionalQuestion();
        break;
      }
      case SEQUENCE_QUESTION:
        this.setSequenceQuestion()
        break;
      default:
        this.optionType = 0;
    }
  }

  startQuestionTimer()  {
    this.optionSwitcher();
    this.timer = this.questions[this.indexQuestion].time;
    this.interval = setInterval(() => this.timer--, 1000);
    this.timeout = setTimeout(() => { clearInterval(this.interval);
        this.nextQuestion();},
      (this.questions[this.indexQuestion].time + 1) * 1000);
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
  defAnswer(answer: string) {
    this.userAnswers.push({questionId: this.questions[this.indexQuestion].id ,
      points: this.questionOptions
        .get(this.questions[this.indexQuestion].id)[0].answer === answer ? USER_POINTS : 0});
    this.timeSpent+=this.questions[this.indexQuestion].time-this.timer;
    this.clearTimer()
    this.nextQuestion();
  }

  addPoint(point: number, event?) {
    point *= this.coefOptional;
    this.questionOptionPoints += event.target.checked ? point : -point;
  }

  sendOptionAnswer() {
    this.userAnswers.push({questionId: this.questions[this.indexQuestion].id,
      points: this.questionOptionPoints*USER_POINTS
    });
    this.timeSpent+=this.questions[this.indexQuestion].time-this.timer;
    this.clearTimer()
    this.nextQuestion();
  }

  seqAnswer() {
    let questionPoints = 0;
    for (let i = 0; i < this.optionsSequence.length; i++) {
      if (i + 1 === this.optionsSequence[i].serial_num) {
        questionPoints += 1/this.optionsSequence.length;
      }
    }
    this.userAnswers.push({questionId: this.questions[this.indexQuestion].id,
       points: questionPoints*USER_POINTS
    });
    this.timeSpent+=this.questions[this.indexQuestion].time-this.timer;
    this.clearTimer()
    this.nextQuestion();
  }

  getQuestions() {
    this.questionService.getQuestions(this.quizId)
      .subscribe(questions => {
        this.questions = questions;
        this.optionType = +this.questions[0].type.id;
        for (let question of this.questions) {
          this.getOptions(question);
        }
      });
  }

  getOptions(question: Question) {
    if(question.type.name === 'a,b,c,d') {
      this.optionService.getOptions(question.id)
        .subscribe(options =>
        {this.questionOptions.set(question.id, options)
        });
    } else if(question.type.name === 'sequence') {
      this.optionService.getSequenceOptions(question.id)
        .subscribe(options =>
        {this.questionOptions.set(question.id, options)});
    } else if(question.type.name === 'enter' || question.type.name === 'true/false') {
        this.optionService.getDefaultOptions(question.id)
          .subscribe(options =>
          {this.questionOptions.set(question.id, options)});
    }
  }

  getScore(): number {
    let score = 0;
    this.userAnswers.forEach(x => score += x.points);
    return score < 0 ? 0 : score;
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.optionsSequence, event.previousIndex, event.currentIndex);
  }

  getUserRole() {
    return this.userService.user.role.name;
  }

  getUserJoin(){
    return this.userService.user.joined;
  }

  startNewGame() {
    if (this.getUserRole() === 'user') {
      this.quizService.startSession(this.sessionId).subscribe(data =>
        console.log(data));
      this.notficationService.stompClient.send('/app/start/game' , {}, this.sessionId);
    }else {this.startQuestionTimer();}
  }


  getStats(){
    this.quizService.getStatsSession(this.sessionId).subscribe(
      data =>this.stats = data);
  }

  getTopStats(){
    this.quizService.getTopStats(this.quizId).subscribe(
      data=>   { this.topStats = data;});
  }
  getAccessCode(){
    this.quizService.getAccessCode(this.sessionId).subscribe(data =>
      this.access_code=data)
  }

  finishSession() {
    this.userService.user.joined=null;
    this.quizService.sendSessionStats({
      ses_id : this.sessionId,
      user_id : +this.userService.user.id,
      score : this.getScore(),
      time: this.timeSpent
    } as UserSessionResult).subscribe(data => {
      console.log(data);
      this.getTopStats();
      this.finish=true;
      this.achievementService.setUserAchievement().subscribe(data => {
        this.finish=true;
      });

    });
  }

  initializeWebSocketConnection() {
    this.notficationService.stompClient.subscribe('/start/'+this.sessionId, (message) => {
      if (message.body) {this.startQuestionTimer();}
    });
  }

  clearTimer() {
    clearInterval(this.interval);
    clearTimeout(this.timeout);
  }
  setSequenceQuestion() {
    this.optionType = SEQUENCE_QUESTION;
    this.optionsSequence = this.questionOptions.get(this.questions[this.indexQuestion].id);
  }

 setOptionalQuestion() {
   this.optionType = OPTIONAL_QUESTION;
   this.optionalAnswers = this.questionOptions
     .get(this.questions[this.indexQuestion].id);
   this.coefOptional = 1 / this.optionalAnswers.filter(x => x.is_correct).length;
  }

}
