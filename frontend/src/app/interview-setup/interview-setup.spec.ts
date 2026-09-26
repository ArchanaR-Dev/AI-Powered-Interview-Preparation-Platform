import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InterviewSetup } from './interview-setup';

describe('InterviewSetup', () => {
  let component: InterviewSetup;
  let fixture: ComponentFixture<InterviewSetup>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InterviewSetup],
    }).compileComponents();

    fixture = TestBed.createComponent(InterviewSetup);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
