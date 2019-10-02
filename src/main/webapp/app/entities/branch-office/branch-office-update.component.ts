import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBranchOffice, BranchOffice } from 'app/shared/model/branch-office.model';
import { BranchOfficeService } from './branch-office.service';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';

@Component({
  selector: 'jhi-branch-office-update',
  templateUrl: './branch-office-update.component.html'
})
export class BranchOfficeUpdateComponent implements OnInit {
  isSaving: boolean;

  affiliates: IAffiliate[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required, Validators.maxLength(4)]],
    name: [null, [Validators.required, Validators.maxLength(30)]],
    status: [null, [Validators.required, Validators.maxLength(1)]],
    affiliateId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected branchOfficeService: BranchOfficeService,
    protected affiliateService: AffiliateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ branchOffice }) => {
      this.updateForm(branchOffice);
    });
    this.affiliateService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAffiliate[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAffiliate[]>) => response.body)
      )
      .subscribe((res: IAffiliate[]) => (this.affiliates = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(branchOffice: IBranchOffice) {
    this.editForm.patchValue({
      id: branchOffice.id,
      number: branchOffice.number,
      name: branchOffice.name,
      status: branchOffice.status,
      affiliateId: branchOffice.affiliateId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const branchOffice = this.createFromForm();
    if (branchOffice.id !== undefined) {
      this.subscribeToSaveResponse(this.branchOfficeService.update(branchOffice));
    } else {
      this.subscribeToSaveResponse(this.branchOfficeService.create(branchOffice));
    }
  }

  private createFromForm(): IBranchOffice {
    return {
      ...new BranchOffice(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      name: this.editForm.get(['name']).value,
      status: this.editForm.get(['status']).value,
      affiliateId: this.editForm.get(['affiliateId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranchOffice>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAffiliateById(index: number, item: IAffiliate) {
    return item.id;
  }
}
