import {Component, OnInit, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from './services/user.service';
import {NotificationService} from './services/notification.service';
import {Notification} from './entities/notification';
import {SettingsService} from "./services/settings.service";
import {TranslateService} from "@ngx-translate/core";






@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnDestroy, OnInit{

  title = 'ui-app';
  constructor(private userService: UserService,
              private router: Router,
              public translate: TranslateService,
              public notificationService: NotificationService,
              private settingsService: SettingsService) {
  }


  setTranslate(language: string) {
    localStorage.setItem('language', language);
    this.translate.use(language);
    this.settingsService.setLanguage(language, this.userService.user).subscribe();
  }

  ngOnInit(): void {
    this.translate.use(localStorage.getItem('language'));
  }

  deleteNotification(notification) {
    this.notificationService.delete([notification]);
  }

  ngOnDestroy(): void {
    localStorage.clear();
  }
  getNewNotifications(): Notification[] {
    return this.notificationService.notifications.filter(x => !x.seen);
  }

  logout() {
    this.userService.logout().subscribe(
      _ => {this.router.navigateByUrl('/login');
            this.notificationService.disconnect()});
  }

  reloadWindow() {
    this.router.navigateByUrl('/dashboard/AddProfile');
    window.location.replace('http://localhost:4200/#/dashboard/AddProfile');
  
  }

  getRole() {
    return this.userService.user.role.name;
  }

  authenticated() {
    return this.userService.authenticated;
  }

}
