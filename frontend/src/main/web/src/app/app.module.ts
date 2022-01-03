import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthorizationComponent} from './authorization/authorization.component';
import {HomeComponent} from './home/home.component';
import {RegistrationComponent} from './registration/registration.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {UserCardComponent} from './user-card/user-card.component';
import {QuizCreateComponent} from './quiz-create/quiz-create.component';
import {QuizEditComponent} from './quiz-edit/quiz-edit.component';
import {QuizDashboardComponent} from './quiz-dashboard/quiz-dashboard.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AnnouncementComponent} from './announcement/announcement.component';
import {AnnouncementEditComponent} from './announcement-edit/announcement-edit.component';
import {QuizComponent} from './quiz/quiz.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatDatetimepickerModule} from "@mat-datetimepicker/core";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {LoaderService} from './services/loader.service';
import {LoaderInterceptor} from './interceptors/loader-interceptor.service';
import {MyLoaderComponent} from './components/my-loader/my-loader.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { QuizAproveFormComponent } from './quiz-aprove-form/quiz-aprove-form.component';
import { AnnouncementDashboardComponent } from './announcement-dashboard/announcement-dashboard.component';
import {QuizCardComponent} from './quiz-card/quiz-card.component';
import {VotersComponent} from './voters/voters.component';
import {UserInviteCardComponent} from './voters/user-invite-card/user-invite-card.component';
import { AchievementsComponent } from './achievements/achievements.component';
import { SettingsComponent } from './settings/settings.component';
import { UserActivityDashboardComponent } from './user-activity-dashboard/user-activity-dashboard.component';
import { UserActivityComponent } from './user-activity/user-activity.component';
import { AchievementDashboardComponent } from './achievement-dashboard/achievement-dashboard.component';
import { AchievementCreateComponent } from './achievement-create/achievement-create.component';
import { PassRecoveryComponent } from './pass-recovery/pass-recovery.component';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {MatDatepickerModule} from '@angular/material/datepicker';
import {FileValidator} from "./user-profile/_helpers/file-input.validator";
import {FileValueAccessor} from "./user-profile/_helpers/file-control-value-accessor";
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import {MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { ElectionComponent } from './election/election.component';
import { ElectionDashboardComponent } from './election-dashboard/election-dashboard.component';
import { ElectionEditComponent } from './election-edit/election-edit.component';
import { NgxMatDatetimePickerModule, NgxMatTimepickerModule } from '@angular-material-components/datetime-picker';
import { DistrictComponent } from './district/district.component';
import { DistrictEditComponent } from './district-edit/district-edit.component';
import { DistrictDashboardComponent } from './district-dashboard/district-dashboard.component';
import { GoodVoiceComponent } from './good-voice/good-voice.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    AuthorizationComponent,
    HomeComponent,
    RegistrationComponent,
    DashboardComponent,
    UserProfileComponent,
    UserCardComponent,
    QuizCreateComponent,
    QuizEditComponent,
    QuizDashboardComponent,
    AnnouncementComponent,
    AnnouncementEditComponent,
    QuizComponent,
    MyLoaderComponent,
    QuizCardComponent,
    QuizAproveFormComponent,
    AnnouncementDashboardComponent,
    VotersComponent,
    UserInviteCardComponent,
    AchievementsComponent,
    SettingsComponent,
    UserActivityDashboardComponent,
    UserActivityComponent,
    AchievementDashboardComponent,
    AchievementCreateComponent,
    PassRecoveryComponent,
    FileValidator,
    FileValueAccessor,
    UploadFileComponent,
    ElectionComponent,
    ElectionDashboardComponent,
    ElectionEditComponent,
    DistrictComponent,
    DistrictEditComponent,
    DistrictDashboardComponent,
    GoodVoiceComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    DragDropModule,
    MatDatetimepickerModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatIconModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,

    MatInputModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      defaultLanguage: 'ua'
    })
  ],
  exports: [
    MatDatepickerModule
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy},
    LoaderService,
    MatNativeDateModule,
    { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }

