import { formatDate } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Election } from '../entities/election';
import { ELECTION_DEFAULT_IMAGE } from '../parameters';
import { ElectionService } from '../services/election/election.service';
import { UserService } from '../services/user.service';


@Component({
  selector: 'app-election-edit',
  templateUrl: './election-edit.component.html',
  styleUrls: ['./election-edit.component.css']
})
export class ElectionEditComponent implements OnInit {

  @Input()
  editOnly = false;

  @Input()
  election: Election = {} as Election;

  error = '';

  message = '';

  imageUrl = ELECTION_DEFAULT_IMAGE;

  form: FormGroup;

  electionForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router,
              private electionService: ElectionService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.electionForm = this.fb.group({
      title: [this.election.name, [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      text: [this.election.description, [Validators.required, Validators.minLength(3), Validators.maxLength(4990)]],
      startDate: ['', [Validators.required]],
      endDate: ['', [Validators.required]]//formatDate(this.election.endDate, 'yyyy-MM-dd', 'en')
    });
  }

  save() {
    this.fillElectionInfo();
    this.electionService.updateElection(this.election)
      .subscribe(
        res => this.message = 'Форма виборів оновлена.',
        error => this.error = error.message);
  }

  add() {
    this.fillElectionInfo();
    this.electionService.createElection(this.election)
    .subscribe(
      res => {
        this.message = 'Форма виборів додана.'; 
        this.electionForm.reset()},
      error => this.error = error.message);
  }

  fillElectionInfo() {
    this.cleanMessage();
    this.election.name = this.electionForm.get('title').value;
    this.election.description = this.electionForm.get('text').value;
    this.election.createdById = +this.userService.user.id;
    this.election.startDate = this.electionForm.get('startDate').value;
    this.election.endDate = this.electionForm.get('endDate').value;
    console.log(this.electionForm.get('startDate').value);
  }

  delete() {
    this.cleanMessage();
    this.electionService.deleteElection(this.election.id)
      .subscribe(
        resp => {
          this.message = 'Форма виборів видалена.';
          this.router.navigateByUrl('/dashboard');
          this.electionForm.reset()
        },
        error => this.error = error.message);
  }

  cleanMessage() {
    this.error = '';
    this.message = '';
  }

  getUserRole(): string {
    return this.userService.user.role.name;
  }

  submit() {
  }

}

