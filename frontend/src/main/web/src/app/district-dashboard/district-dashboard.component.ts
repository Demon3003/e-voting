import { Component, OnInit } from '@angular/core';
import { District } from '../entities/district';
import { DistrictService } from '../services/district/district.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-district-dashboard',
  templateUrl: './district-dashboard.component.html',
  styleUrls: ['./district-dashboard.component.css']
})
export class DistrictDashboardComponent implements OnInit {

  district: District;
  districts: District[] = [];
  editOnly = false;
  constructor(private districtService: DistrictService, private userService: UserService) { }

  getRole() {
    return this.userService.user.role.name;
  }

  editDistrict(district: District) {
    this.district = district;
    this.editOnly = true;
  }

  ngOnInit(): void {
    if (this.userService.user.role.name !== 'user') {
      this.getAll();
    } else {
      this.getDistrictForMe();
    }
  }

  getAll() {
    this.districtService.getDistricts()
      .subscribe(
        res => {this.districts = res; console.log(this.districts)},
        error => {this.districts = []; console.error(error)}
      );
  }

  getDistrictForMe() {
    this.districtService.getDistrictForUser(this.userService.user.id)
      .subscribe(
        res => {this.districts = [res]; console.log(res)},
        error => {this.districts = []; console.error(error)}
      )
  }

}
