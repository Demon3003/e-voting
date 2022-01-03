import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectionEditComponent } from './election-edit.component';

describe('ElectionEditComponent', () => {
  let component: ElectionEditComponent;
  let fixture: ComponentFixture<ElectionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ElectionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ElectionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
