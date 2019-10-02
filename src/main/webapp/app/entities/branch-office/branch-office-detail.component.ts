import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBranchOffice } from 'app/shared/model/branch-office.model';

@Component({
  selector: 'jhi-branch-office-detail',
  templateUrl: './branch-office-detail.component.html'
})
export class BranchOfficeDetailComponent implements OnInit {
  branchOffice: IBranchOffice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ branchOffice }) => {
      this.branchOffice = branchOffice;
    });
  }

  previousState() {
    window.history.back();
  }
}
