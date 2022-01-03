import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { District } from '../entities/district';
import { Election } from '../entities/election';
import { ELECTION_DEFAULT_IMAGE } from '../parameters';
import { DistrictService } from '../services/district/district.service';
import { UserService } from '../services/user.service';
import { ElectionService } from '../services/election/election.service';

@Component({
  selector: 'app-district-edit',
  templateUrl: './district-edit.component.html',
  styleUrls: ['./district-edit.component.css']
})
export class DistrictEditComponent implements OnInit {

  @Input()
  editOnly = false;

  @Input()
  district: District = {elections: []} as District;

  allElections: Election[] = [];

  error = '';

  message = '';

  imageUrl = ELECTION_DEFAULT_IMAGE;

  form: FormGroup;

  districtForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router,
              private districtService: DistrictService,
              private electionService: ElectionService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.districtForm = this.fb.group({
      number: [this.district.number, [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      city: [this.district.city, [Validators.required, Validators.minLength(3), Validators.maxLength(4990)]],
      address: [this.district.address, [Validators.required]],
      elections: []
    });
    this.getAllElections();
   
  }

  save() {
    this.fillMainInfo();
    this.districtService.updateDistrict(this.district)
      .subscribe(
        res => this.message = 'Дані про дільницю оновлені.',
        error => this.error = error.message);
  }

  add() {
    this.fillMainInfo();
    this.districtService.createDistrict(this.district)
    .subscribe(
      res => {
        this.message = 'Дані про дільницю додані'; 
        this.districtForm.reset()},
      error => this.error = error.message);
  }

  fillMainInfo() {
    this.cleanMessage();
    this.district.number = this.districtForm.get('number').value;
    this.district.city = this.districtForm.get('city').value;
    this.district.address = this.districtForm.get('address').value;
    this.district.elections = [];
    this.districtForm.get('elections').value.forEach(element => {
      this.district.elections.push({id: element} as Election);
    });
    console.log(this.district.elections);
  }

  delete() {
    this.cleanMessage();
    this.districtService.deleteDistrict(this.district.id)
      .subscribe(
        resp => {
          this.message = 'Дані про дільницю видалені.';
          this.router.navigateByUrl('/dashboard');
          this.districtForm.reset()
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

  getAllElections() {
    this.electionService.getElections()
    .subscribe(
      res => {this.allElections = res; console.log(res)},
      error => this.allElections = []);
  }

  submit() {
  }

}
