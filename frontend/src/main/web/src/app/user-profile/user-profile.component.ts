import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import { User } from '../entities/user';
import {MustMatchValidator} from "../registration/_helpers/must-match.validator";
import {HashBcrypt} from "../util/hashBcrypt";
import {USER_DEFAULT_IMAGE, USER_STATUS_ACTIVE, USER_STATUS_DEACTIVE} from '../parameters';
import {Router} from '@angular/router';
import { Election } from '../entities/election';
import { ElectionService } from '../services/election/election.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  userStatusActive  = USER_STATUS_ACTIVE;
  userStatusDeactive =  USER_STATUS_DEACTIVE;

  @Input()
  user: User;
  @Input()
  editOnly = false;

  error = '';
  message = '';

  elections : Election[] = [];

  selectedElection = 0;

  uploadResponse = { status: '', message: '', filePath: '' };
  userForm: FormGroup;
  imageUrl: string = USER_DEFAULT_IMAGE;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private router: Router,
              private electionService: ElectionService) { }

  ngOnInit(): void {
    if (this.user.role.name === 'candidate') {
      this.getAllElections();
    }
    this.setUserForm();
  }


  onChanged(url:string){
    this.imageUrl = url;
    this.setImage();
}
  setImage() {
    this.user.image = this.imageUrl;
    if (this.user.id != null) {
      this.userService.updateUser_2(this.user).subscribe(
        response => {this.user = response;},
        error => {this.error = error.message});
        if (this.user.id === this.userService.user.id) {
          localStorage.setItem('user', JSON.stringify(this.user)); 
        }
    }
  }

  userRole() {
    return this.userService.user.role.name;
  }

  private setUserForm() {
    this.userForm = this.fb.group({
      firstname: [this.user.firstName, [Validators.required, Validators.minLength(3)]],
      lastname: [this.user.lastName, [Validators.required, Validators.minLength(3)]],
      email: [this.user.email, [Validators.required, Validators.minLength(3), Validators.email]],
      username: [this.user.username, [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      image: [this.user.image],
      passport: [this.user.passport == null ? '' : this.user.passport, [Validators.required, Validators.minLength(4)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(3)]],
      role: [{value: null, disabled: this.userRole() === 'super admin' && this.user.status.id !== 7 ? false : true}],
    } );
    this.setProperties();
  }
  private setProperties(): void {
    if (this.editOnly && this.user.status.id != 7) {
      this.userForm.setValidators(MustMatchValidator.passwordConfirming);
    } else {
      if (this.userService.user.status.id == 2) {
        this.userForm.get('passport').clearValidators();
      }
     this.userForm.clearValidators();
     this.userForm.get('confirmPassword').clearValidators();
     this.userForm.get('password').clearValidators();
    }
    this.userForm.updateValueAndValidity();
  }

  add() {
    this.clearMessages();
    this.setMaininfo();
    if (this.user.status.id !=7) {
      this.user.password = HashBcrypt.hash(this.userForm.get('password').value);
      this.user.role.name = this.userRole() === 'admin' ? 'specialist' : this.userForm.get('role').value;
    } else {
      this.user.elections = [];
      this.user.elections.push({id: this.selectedElection} as Election);
    }
    console.log(this.user);
    this.userService.createUser_2(this.user).subscribe(
      response => {this.message = 'Анкета додана!';},
      error => {this.error = error.message});
  }

  update() {
    this.clearMessages();
    this.setMaininfo();
    this.user.password = this.checkPassword();
    if(!this.error) {
      if (this.isPendingUser()) {
        this.user.status.id = 5;
        this.userService.user.status.id = 5;
        console.log(`${this.userService.user.status.id} dmzh`)

      }
      
      this.userService.updateUser_2(this.user).subscribe(
        response => {
          this.user = response;
          this.message = 'Анкета була оновлена!';
          if (this.user.id == this.userService.user.id) {
            localStorage.setItem('user', JSON.stringify(response));
            if(this.user.status.id == 5)  window.location.reload();
          }},
        error => {this.error = error.message});
    }
  }
  private setMaininfo() {
    this.user.firstName = this.userForm.get('firstname').value;
    this.user.lastName = this.userForm.get('lastname').value;
    this.user.email = this.userForm.get('email').value;
    this.user.username = this.userForm.get('username').value;
    this.user.passport = this.userForm.get('passport').value;
  }

  private clearMessages() {
    this.error = '';
    this.message = '';
  }

  private checkPassword() {
    if(this.userForm.get('password').dirty && this.userForm.get('confirmPassword').dirty) {
      if(HashBcrypt.compare(this.userForm.get('confirmPassword').value, this.userService.user.password)){
        return HashBcrypt.hash(this.userForm.get('password').value);
      }
      this.error = 'Password incorrect';
    } else {
      return this.user.password;
    }
  }

  private isPendingUser() {
    return this.userService.user.status.id === 4
  }

  clearPass() {
    this.userForm.get('password').reset();
    this.userForm.get('confirmPassword').reset();
  }


  setStatus(statusId) {
    this.clearMessages();
    this.user.status.id = statusId;
    this.userService.setStatus(statusId, this.user.id).
    subscribe(response => {this.message = 'User has been edited!'},
    error => { this.error = error.error});
  }

  getAllElections() {
    this.electionService.getElections()
      .subscribe(
        res => this.elections = res);
  }

  submit() {

  }
}
