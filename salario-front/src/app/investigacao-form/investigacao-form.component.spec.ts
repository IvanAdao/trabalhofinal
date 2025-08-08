import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvestigacaoFormComponent } from './investigacao-form.component';

describe('InvestigacaoFormComponent', () => {
  let component: InvestigacaoFormComponent;
  let fixture: ComponentFixture<InvestigacaoFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InvestigacaoFormComponent]
    });
    fixture = TestBed.createComponent(InvestigacaoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
