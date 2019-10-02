import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBranchOffice } from 'app/shared/model/branch-office.model';
import { BranchOfficeService } from './branch-office.service';

@Component({
  selector: 'jhi-branch-office-delete-dialog',
  templateUrl: './branch-office-delete-dialog.component.html'
})
export class BranchOfficeDeleteDialogComponent {
  branchOffice: IBranchOffice;

  constructor(
    protected branchOfficeService: BranchOfficeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.branchOfficeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'branchOfficeListModification',
        content: 'Deleted an branchOffice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-branch-office-delete-popup',
  template: ''
})
export class BranchOfficeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ branchOffice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BranchOfficeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.branchOffice = branchOffice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/branch-office', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/branch-office', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
