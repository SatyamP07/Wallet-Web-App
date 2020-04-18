import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionProcessComponent } from './transaction-process.component';

describe('TransactionProcessComponent', () => {
  let component: TransactionProcessComponent;
  let fixture: ComponentFixture<TransactionProcessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionProcessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
