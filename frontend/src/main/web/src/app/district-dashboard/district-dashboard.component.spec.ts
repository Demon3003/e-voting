import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictDashboardComponent } from './district-dashboard.component';

describe('DistrictDashboardComponent', () => {
  let component: DistrictDashboardComponent;
  let fixture: ComponentFixture<DistrictDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DistrictDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistrictDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
