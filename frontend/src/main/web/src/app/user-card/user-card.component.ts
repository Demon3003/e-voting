
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from '../entities/user';
import {UserInvite} from "../entities/user-invite";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import { USER_DEFAULT_IMAGE, USER_STATUS_ACTIVE, USER_STATUS_DEACTIVE, USER_STATUS_REJECTED } from '../parameters';
import { Election } from '../entities/election';
import { District } from '../entities/district';
import { ElectionService } from '../services/election/election.service';
import { DistrictService } from '../services/district/district.service';


@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent implements OnInit {

  @Input()
  user: User;

  @Output()
  onChanged = new EventEmitter<User>();

  approved = false;

  elections : Election[] = [];

  districts : District[] = [];

  selectedElections = [];

  selectedDistrict: number = 0;

  imageUrl = USER_DEFAULT_IMAGE;
  

  inviteForm = new FormGroup({
    'inviteText' : new FormControl(null, [
      Validators.required,
      Validators.minLength(4)
    ]),
  });
  name: string = this.userService.user.firstName;

  nameSendButton = 'Send invitation';
  clicked: boolean = false;

  constructor(private userService: UserService,
              private electionService: ElectionService,
              private districtService: DistrictService) { }

  ngOnInit(): void {
    if ((this.userRole() ==='super admin' 
        || this.userRole() ==='admin') 
        && this.user.status.id == 5) {
          this.getAllElections();
          this.getAllDistricts();
    }
  }

  goToProfile() {
    this.onChanged.emit(this.user);
  }
  userRole() {
    return this.userService.user.role.name;
  }
  getUserId() {
    return this.userService.user.id;
  }

  sendInvite() {
    this.userService.sendUserInvite({
      inviteText: this.inviteForm.get('inviteText').value,
      userIdFrom: +this.userService.user.id,
      userIdTo: +this.user.id,
      usernameFrom: this.userService.user.username
    } as UserInvite).subscribe(data  => {
      this.clicked = true;
      this.nameSendButton = 'Invitation was sent';
    });
  }

  approveProfile() {
    this.approved = true;
    
  }

  disapproveProfile() {
    this.userService.setStatus(USER_STATUS_REJECTED, this.user.id).subscribe(_ => {});
    this.onChanged.emit(this.user);
  }

  assign() {
    this.user.district = {id: this.selectedDistrict} as District;
    this.user.elections = [];
    this.selectedElections
      .forEach(el => this.user.elections.push(
        { id: el, 
          users: [{id: this.user.id} as User]} as Election));
      this.user.status.id = USER_STATUS_ACTIVE;
      console.log(this.user);
      this.userService.updateUser_2(this.user)
        .subscribe(_ => {});
  
    // this.userService.setStatus(USER_STATUS_ACTIVE, this.user.id).subscribe(_ => {});
     this.onChanged.emit(this.user);
  }

  getAllElections() {
    this.electionService.getElections()
      .subscribe(
        res => this.elections = res);
  }
  
  getAllDistricts() {
    this.districtService.getDistricts()
      .subscribe(res => this.districts = res);
  }
}


