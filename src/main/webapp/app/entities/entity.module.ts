import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'affiliate',
        loadChildren: () => import('./affiliate/affiliate.module').then(m => m.TestJhiAffiliateModule)
      },
      {
        path: 'branch-office',
        loadChildren: () => import('./branch-office/branch-office.module').then(m => m.TestJhiBranchOfficeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TestJhiEntityModule {}
