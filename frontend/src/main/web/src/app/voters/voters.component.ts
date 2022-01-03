import {Component, OnInit} from '@angular/core';
import { User } from '../entities/user';
import {UserInvite} from "../entities/user-invite";
import {UserService} from "../services/user.service";
import { UserInviteCardComponent } from './user-invite-card/user-invite-card.component';

@Component({
  selector: 'app-voters',
  templateUrl: './voters.component.html',
  styleUrls: ['./voters.component.css']
})

export class VotersComponent implements OnInit {
  // userInvites: UserInvite[] = [];
  // friendsList: UserInvite[] = [];
  pendingVotersList: User[] = [];
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getPendingVoters()
  }

  // getUserInvites(): void {
  //   this.userService.getUserInvite().subscribe(userInvites => {
  //     this.userInvites = userInvites
  //     console.log('From Friends' + userInvites);
  //   })
  // }

  // getFriends(): void {
  //   this.userService.getFriendsList().subscribe(friendsList => {
  //     this.friendsList = friendsList;
  //   })
  // }

  getPendingVoters(): void {
    this.userService.getPendingVoters().subscribe(pendingVotersList => {
      this.pendingVotersList = pendingVotersList;
    }, error => {console.error(error);})
  }

  refreshVotersList(user?: User): void {
    window.location.reload();
    this.getPendingVoters();
  }

}
