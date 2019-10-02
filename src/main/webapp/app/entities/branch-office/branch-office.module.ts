import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestJhiSharedModule } from 'app/shared/shared.module';
import { BranchOfficeComponent } from './branch-office.component';
import { BranchOfficeDetailComponent } from './branch-office-detail.component';
import { BranchOfficeUpdateComponent } from './branch-office-update.component';
import { BranchOfficeDeletePopupComponent, BranchOfficeDeleteDialogComponent } from './branch-office-delete-dialog.component';
import { branchOfficeRoute, branchOfficePopupRoute } from './branch-office.route';

const ENTITY_STATES = [...branchOfficeRoute, ...branchOfficePopupRoute];

@NgModule({
  imports: [TestJhiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BranchOfficeComponent,
    BranchOfficeDetailComponent,
    BranchOfficeUpdateComponent,
    BranchOfficeDeleteDialogComponent,
    BranchOfficeDeletePopupComponent
  ],
  entryComponents: [BranchOfficeDeleteDialogComponent]
})
export class TestJhiBranchOfficeModule {}
