import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestJhiSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [TestJhiSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class TestJhiHomeModule {}
