import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestJhiTestModule } from '../../../test.module';
import { BranchOfficeDeleteDialogComponent } from 'app/entities/branch-office/branch-office-delete-dialog.component';
import { BranchOfficeService } from 'app/entities/branch-office/branch-office.service';

describe('Component Tests', () => {
  describe('BranchOffice Management Delete Component', () => {
    let comp: BranchOfficeDeleteDialogComponent;
    let fixture: ComponentFixture<BranchOfficeDeleteDialogComponent>;
    let service: BranchOfficeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestJhiTestModule],
        declarations: [BranchOfficeDeleteDialogComponent]
      })
        .overrideTemplate(BranchOfficeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BranchOfficeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BranchOfficeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
