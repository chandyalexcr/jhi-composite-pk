import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BranchOffice } from 'app/shared/model/branch-office.model';
import { BranchOfficeService } from './branch-office.service';
import { BranchOfficeComponent } from './branch-office.component';
import { BranchOfficeDetailComponent } from './branch-office-detail.component';
import { BranchOfficeUpdateComponent } from './branch-office-update.component';
import { BranchOfficeDeletePopupComponent } from './branch-office-delete-dialog.component';
import { IBranchOffice } from 'app/shared/model/branch-office.model';

@Injectable({ providedIn: 'root' })
export class BranchOfficeResolve implements Resolve<IBranchOffice> {
  constructor(private service: BranchOfficeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBranchOffice> {
    const code = route.params['code'];
    const number = route.params['number'];
    if (code && number) {
      return this.service.find(code, number).pipe(
        filter((response: HttpResponse<BranchOffice>) => response.ok),
        map((branchOffice: HttpResponse<BranchOffice>) => branchOffice.body)
      );
    }
    return of(new BranchOffice());
  }
}

export const branchOfficeRoute: Routes = [
  {
    path: '',
    component: BranchOfficeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'affiliateCode,asc',
      pageTitle: 'testJhiApp.branchOffice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':code/:number/view',
    component: BranchOfficeDetailComponent,
    resolve: {
      branchOffice: BranchOfficeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testJhiApp.branchOffice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BranchOfficeUpdateComponent,
    resolve: {
      branchOffice: BranchOfficeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testJhiApp.branchOffice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':code/:number/edit',
    component: BranchOfficeUpdateComponent,
    resolve: {
      branchOffice: BranchOfficeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testJhiApp.branchOffice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const branchOfficePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BranchOfficeDeletePopupComponent,
    resolve: {
      branchOffice: BranchOfficeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testJhiApp.branchOffice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
