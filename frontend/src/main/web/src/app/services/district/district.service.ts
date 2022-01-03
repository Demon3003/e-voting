import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { District } from 'src/app/entities/district';
import { APP_URL } from 'src/app/parameters';
import { UserService } from '../user.service';

@Injectable({
  providedIn: 'root'
})
export class DistrictService {


  private electionUrl = `${APP_URL}/api/district`;

  private readonly token: string;
  private httpHeader: HttpHeaders;

  constructor(private http: HttpClient, private userService: UserService) {
    this.token = this.userService.getToken();
    this.httpHeader = new HttpHeaders().set('Authorization',  `Bearer_${this.token}`);
  }

  getDistrict(id: number): Observable<District> {
    return this.http.get<District>(this.electionUrl + `/get/${id}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('getDistrict'))
    );

  }

  getDistricts(): Observable<District[]> {
    return this.http.get<District[]>(this.electionUrl + `/all`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('getDistricts'))
    );
  }

  getDistrictForUser(id: string): Observable<District> {
    return this.http.get<District>(this.electionUrl + `/getForUser/${id}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('getDistrictForUser'))
    );
  }

  createDistrict(election: District) {
    console.log(election);
    return this.http.post<District>(this.electionUrl + `/create`, election,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('createDistrict'))
    );
  }

  updateDistrict(election: District) {
    return this.http.put<District>(this.electionUrl + `/update`, election,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('updateDistrict'))
    );
  }

  deleteDistrict(electionId) {
    console.log(electionId)
    return this.http.delete(this.electionUrl + `/delete/${electionId}`,  {
      headers: this.httpHeader
    }).pipe(
      catchError(this.handleError<any>('deleteDistrict'))
    );
  }

  private handleError<T>(operation = 'operation') {
    return (error: any): Observable<T> => {
      console.log(operation + ' ' + error);
      return throwError(error);
    };
  }
}
