import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Announcement} from '../entities/announcement';
import {AnnouncementService} from '../services/announcement.service';
import {UserService} from '../services/user.service';
import {ANNOUNCEMENT_APPROVED, ANNOUNCEMENT_CREATED, USER_DEFAULT_IMAGE, VOTE_MODE} from '../parameters';

@Component({
  selector: 'app-announcement',
  templateUrl: './announcement.component.html',
  styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {

  announcementCreated = ANNOUNCEMENT_CREATED;
  voteMode = VOTE_MODE;
  sure: boolean = false;
  
  @Input()
  announcement: Announcement;
  @Output()
  onChanged = new EventEmitter<Announcement>();
  error = '';
  message = '';
  imageUrl = USER_DEFAULT_IMAGE;


  constructor(private announcementService: AnnouncementService, private userService: UserService) { }

  getRole() {
    if (this.userService.user && this.userService.user.role) {
      return this.userService.user.role.name;
    } else {
      return 'user';
    }
    
  }

  edit() {
    this.onChanged.emit(this.announcement);
  }

  delete() {
    this.announcementService.deleteAnnouncement(+this.announcement.id).
    subscribe(resp => {window.location.reload(); },
        error => {this.error = error.message; });
  }

  ngOnInit(): void {
    if (this.announcement.image != null) {
      this.imageUrl = this.announcement.image;
    }
  }

  approve(approved: boolean) {
    this.announcement.statusId = approved ? ANNOUNCEMENT_APPROVED : 0;
    this.announcementService.approve(this.announcement).
    subscribe(resp => {},
      error => {this.announcement.statusId = ANNOUNCEMENT_CREATED ; this.error = error.message; });
  }

  vote() {
    this.sure = true;
  }

  sendVoice() {
    this.onChanged.emit(this.announcement);
  }

  decline() {
    this.sure = false;
  }

}
