import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestJhiTestModule } from '../../../test.module';
import { BranchOfficeDetailComponent } from 'app/entities/branch-office/branch-office-detail.component';
import { BranchOffice } from 'app/shared/model/branch-office.model';

describe('Component Tests', () => {
  describe('BranchOffice Management Detail Component', () => {
    let comp: BranchOfficeDetailComponent;
    let fixture: ComponentFixture<BranchOfficeDetailComponent>;
    const route = ({ data: of({ branchOffice: new BranchOffice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestJhiTestModule],
        declarations: [BranchOfficeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BranchOfficeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BranchOfficeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.branchOffice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
