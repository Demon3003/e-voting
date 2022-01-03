import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorizationComponent} from "./authorization/authorization.component";
import {HomeComponent} from "./home/home.component";
import {AppComponent} from "./app.component";
import {RegistrationComponent} from "./registration/registration.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {UserProfileComponent} from "./user-profile/user-profile.component"
import {QuizCreateComponent} from "./quiz-create/quiz-create.component";
import {QuizEditComponent} from "./quiz-edit/quiz-edit.component";
import {QuizDashboardComponent} from "./quiz-dashboard/quiz-dashboard.component";
import {QuizComponent} from "./quiz/quiz.component";
import {PassRecoveryComponent} from "./pass-recovery/pass-recovery.component";
import { GoodVoiceComponent } from './good-voice/good-voice.component';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/home'},
  { path: 'dashboard/:tab', component: DashboardComponent},
  { path: 'dashboard', pathMatch: 'full', redirectTo: '/dashboard/Quizzes'},
  { path: 'home', component: HomeComponent},
  { path: 'home/:user/:election', component: HomeComponent},
  { path: 'login', component: AuthorizationComponent},
  { path: 'signup', component: RegistrationComponent },
  { path: 'profile', component: UserProfileComponent },
  { path: 'quiz-create', component: QuizCreateComponent },
  { path: 'quiz-edit/:id', component: QuizEditComponent },
  { path: 'quiz-dashboard', component: QuizDashboardComponent },
  { path: 'quiz/:id', component: QuizComponent },
  { path: 'quiz/:id/:sessionId', component: QuizComponent },
  { path: 'pass-reset', component: PassRecoveryComponent },
  { path: 'goodvoice', component: GoodVoiceComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
