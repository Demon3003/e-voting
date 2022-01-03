import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Election } from 'src/app/entities/election';
import { APP_URL } from 'src/app/parameters';
import { UserService } from '../user.service';

@Injectable({
  providedIn: 'root'
})
export class ElectionService {


  private electionUrl = `${APP_URL}/api/election`;

  private readonly token: string;
  private httpHeader: HttpHeaders;

  constructor(private http: HttpClient, private userService: UserService) {
    this.token = this.userService.getToken();
    this.httpHeader = new HttpHeaders().set('Authorization',  `Bearer_${this.token}`);
  }

  getElection(id: number): Observable<Election> {
    return this.http.get<Election>(this.electionUrl + `/get/${id}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('getElection'))
    );

  }

  getElections(): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl + `/all`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('getElections'))
    );
  }

  getElectionsForUser(id: string): Observable<Election[]> {
    return this.http.get<Election[]>(this.electionUrl + `/getForUser/${id}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('getElectionsForUser'))
    );
  }

  createElection(election: Election) {
    console.log(election);
    return this.http.post<Election>(this.electionUrl + `/create`, election,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('createElection'))
    );
  }

  updateElection(election: Election) {
    return this.http.put<Election>(this.electionUrl + `/update`, election,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('updateElection'))
    );
  }


  addVote(userId, candidateId) {
    return this.http.post<Election>(this.electionUrl + `/vote/${userId}/${candidateId}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('addVote'))
    );
  }
  

  deleteElection(electionId) {
    console.log(electionId)
    return this.http.delete(this.electionUrl + `/delete/${electionId}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('deleteElection'))
    );
  }

  private handleError<T>(operation = 'operation') {
    return (error: any): Observable<T> => {
      console.log(operation + ' ' + error);
      return throwError(error);
    };
  }


}
