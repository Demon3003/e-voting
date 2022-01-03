import { Component, OnInit } from '@angular/core';
import { Election } from '../entities/election';
import { ElectionService } from '../services/election/election.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-election-dashboard',
  templateUrl: './election-dashboard.component.html',
  styleUrls: ['./election-dashboard.component.css']
})
export class ElectionDashboardComponent implements OnInit {


  election: Election;
  elections: Election[] = [];
  editOnly = false;
  constructor(private electionService: ElectionService, private userService: UserService) { }

  getRole() {
    return this.userService.user.role.name;
  }

  editElection(election: Election) {
    this.election = election;
    this.editOnly = true;
  }

  ngOnInit(): void {
    if (this.userService.user.role.name !== 'user') {
      this.getAll();
    } else {
      this.getElectionsForMe();
    }
  }

  getAll() {
    this.electionService.getElections()
      .subscribe(
        res => this.elections = res,
        error => {this.elections = []; console.error(error)}
      );
  }

  getElectionsForMe() {
    this.electionService.getElectionsForUser(this.userService.user.id)
      .subscribe(
        res => this.elections = res,
        error => {this.elections = []; console.error(error)}
      )
  }

}
