import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserService} from "./user.service";
import {Observable} from "rxjs";
import {Notification} from '../entities/notification';
import {Setting} from '../entities/setting';

import {tap} from "rxjs/operators";
import {User} from "../entities/user";
import { APP_URL } from '../parameters';
@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  notificationSettings: Notification[];
  activitySettings:Setting[];


  private apiURL = `${APP_URL}/api`;

  // private apiURL = '/api';


  constructor(private http: HttpClient, private userService: UserService) { }


  getNotificationSettings(userId: number) {
    return  this.http.get<Notification[]>(this.apiURL + `/notification/settings/get/${userId}`,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)}).pipe(
      tap(response => {this.notificationSettings = response;}))
  }
  getActivitiesSettings(userId: number) {
    return  this.http.get<Setting[]>(this.apiURL + `/activity/settings/${userId}`,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)}).pipe(
      tap(response => {this.activitySettings = response;}))
  }
  setNotificationSetting(notification) {
    return  this.http.post<Notification>(this.apiURL + '/notification/settings', notification,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
  }
  setActivitySetting(setting){
    return  this.http.post<Setting>(this.apiURL + '/activity/settings', setting,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
  }
  setLanguage(lang: string, user: User) {
    return  this.http.put(this.apiURL + `/change/${lang}`, user,
      { headers: new HttpHeaders()
          .set('Authorization',  `Bearer_${this.userService.getToken()}`)})
  }
}
