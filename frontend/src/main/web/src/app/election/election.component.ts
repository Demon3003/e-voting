import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Election } from '../entities/election';
import { ELECTION_DEFAULT_IMAGE } from '../parameters';
import { ElectionService } from '../services/election/election.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-election',
  templateUrl: './election.component.html',
  styleUrls: ['./election.component.css']
})
export class ElectionComponent implements OnInit {

  @Input()
  election: Election;

  @Output()
  onChanged = new EventEmitter<Election>();

  error = '';

  message = '';

  imageUrl = ELECTION_DEFAULT_IMAGE;


  constructor(private electionService: ElectionService, private userService: UserService) { }

  getRole() {
    return this.userService.user.role.name;
  }

  edit() {
    this.onChanged.emit(this.election);
  }
  
  delete() {
    this.electionService.deleteElection(this.election.id)
      .subscribe(
        res => window.location.reload(),
        error => this.error = error.message
      );
  }

  ngOnInit(): void {
 
  }

}
