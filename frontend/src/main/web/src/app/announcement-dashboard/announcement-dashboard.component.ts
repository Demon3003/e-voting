import { Component, OnInit } from '@angular/core';
import {AnnouncementService} from "../services/announcement.service";
import {UserService} from "../services/user.service";
import {Announcement} from "../entities/announcement";
import {CANDIDATE_ROLE_ID, CANDIDATE_STATUS_ID, CREATED_ANNOUNCEMENT_ID} from "../parameters";
import { User } from '../entities/user';

@Component({
  selector: 'app-announcement-dashboard',
  templateUrl: './announcement-dashboard.component.html',
  styleUrls: ['./announcement-dashboard.component.css']
})
export class AnnouncementDashboardComponent implements OnInit {

  announcement: Announcement;
  announcements: Announcement[] = [];
  newAnnouncements: Announcement[] = [];
  user: User = {role: {id: CANDIDATE_ROLE_ID, name: 'candidate'}, status: {id:CANDIDATE_STATUS_ID, name: 'candidate'}} as User;
  editOnly = false;
  constructor(private announcementService: AnnouncementService, private userService: UserService) { }

  getRole() {
    return this.userService.user.role.name;
  }

  editAnn(announcement: Announcement) {
    this.announcement = announcement;
    this.editOnly = true;
  }

  ngOnInit(): void {
    this.getAll();
    this.getCreated();
  }

  getAll() {
    this.announcementService.getAll(this.userService.user.id).
    subscribe(res => {this.announcements = res, console.log(res)},
              error => {this.announcements = [];console.error(error)} );
  }

  getNewAnnouncements() {
    return this.newAnnouncements.filter(ann => ann.statusId === CREATED_ANNOUNCEMENT_ID);
  }

  getCreated() {
    this.announcementService.getCreated().
    subscribe(res => {this.newAnnouncements = res},
      error => this.newAnnouncements = [] );
  }

}
