import {Component, OnInit, OnDestroy} from '@angular/core';
import { Announcement } from '../entities/announcement';
import { Election } from '../entities/election';
import { User } from '../entities/user';
import {UserService} from "../services/user.service";
import { ElectionService } from '../services/election/election.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  announcements: Announcement[] = [];
  candidates: User[] = [];
  election: Election = {id: 0} as Election;
  routeSub: Subscription;
  userId;

  constructor (private app: UserService,
              private electionService: ElectionService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.userId = params['user'];
      this.election.id = params['election'];
      if (this.userId && this.election.id) {
        this.electionService.getElection(this.election.id)
        .subscribe(res => {
          this.election = res;
          this.getAll();
        },
          error => {})
        
      }
     
    });
   
  }

 getAll() {
   this.app.getCandidatesForElection(this.election.id).subscribe(
     res => {this.candidates = res, console.log(res);
      this.candidates.forEach(can => 
        { this.announcements.push(
          {id: can.id, title: can.firstName + ' ' + can.lastName, text: this.election.name, image: can.image, statusId: 7 } as Announcement)
      });
    },
     error => {this.candidates = [];console.error(error)} 
   );
 }

 sendVoice(announcement: Announcement) {
   this.electionService.addVote(this.userId, announcement.id)
   .subscribe(res =>{this.router.navigateByUrl('/goodvoice')},
    error => {});
  

 }

  authenticated() {
    return this.app.authenticated;
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

}
