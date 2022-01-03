import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GoodVoiceComponent } from './good-voice.component';

describe('GoodVoiceComponent', () => {
  let component: GoodVoiceComponent;
  let fixture: ComponentFixture<GoodVoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GoodVoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GoodVoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
