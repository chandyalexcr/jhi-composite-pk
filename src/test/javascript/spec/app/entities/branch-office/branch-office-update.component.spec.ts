import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestJhiTestModule } from '../../../test.module';
import { BranchOfficeUpdateComponent } from 'app/entities/branch-office/branch-office-update.component';
import { BranchOfficeService } from 'app/entities/branch-office/branch-office.service';
import { BranchOffice } from 'app/shared/model/branch-office.model';

describe('Component Tests', () => {
  describe('BranchOffice Management Update Component', () => {
    let comp: BranchOfficeUpdateComponent;
    let fixture: ComponentFixture<BranchOfficeUpdateComponent>;
    let service: BranchOfficeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestJhiTestModule],
        declarations: [BranchOfficeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BranchOfficeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BranchOfficeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BranchOfficeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BranchOffice(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BranchOffice();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
