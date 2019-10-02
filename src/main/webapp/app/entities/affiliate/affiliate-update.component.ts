import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAffiliate, Affiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from './affiliate.service';

@Component({
  selector: 'jhi-affiliate-update',
  templateUrl: './affiliate-update.component.html'
})
export class AffiliateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    name: [null, [Validators.required, Validators.maxLength(30)]]
  });

  constructor(protected affiliateService: AffiliateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ affiliate }) => {
      this.updateForm(affiliate);
    });
  }

  updateForm(affiliate: IAffiliate) {
    this.editForm.patchValue({
      id: affiliate.id,
      code: affiliate.code,
      name: affiliate.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const affiliate = this.createFromForm();
    if (affiliate.id !== undefined) {
      this.subscribeToSaveResponse(this.affiliateService.update(affiliate));
    } else {
      this.subscribeToSaveResponse(this.affiliateService.create(affiliate));
    }
  }

  private createFromForm(): IAffiliate {
    return {
      ...new Affiliate(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliate>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
