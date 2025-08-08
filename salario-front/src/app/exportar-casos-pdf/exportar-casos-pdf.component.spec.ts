import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExportarCasosPdfComponent } from './exportar-casos-pdf.component';

describe('ExportarCasosPdfComponent', () => {
  let component: ExportarCasosPdfComponent;
  let fixture: ComponentFixture<ExportarCasosPdfComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExportarCasosPdfComponent]
    });
    fixture = TestBed.createComponent(ExportarCasosPdfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
