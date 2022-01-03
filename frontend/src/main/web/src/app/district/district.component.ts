import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { District } from '../entities/district';
import { ELECTION_DEFAULT_IMAGE } from '../parameters';
import { DistrictService } from '../services/district/district.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-district',
  templateUrl: './district.component.html',
  styleUrls: ['./district.component.css']
})
export class DistrictComponent implements OnInit {

  constructor(private districtService: DistrictService, private userService: UserService) { }

  @Input()
  district: District;

  @Output()
  onChanged = new EventEmitter<District>();

  error = '';

  message = '';

  imageUrl = ELECTION_DEFAULT_IMAGE;

  getRole() {
    return this.userService.user.role.name;
  }

  edit() {
    this.onChanged.emit(this.district);
  }
  
  delete() {
    this.districtService.deleteDistrict(this.district.id)
      .subscribe(
        res => window.location.reload(),
        error => this.error = error.message
      );
  }

  ngOnInit(): void {
 
  }

}
