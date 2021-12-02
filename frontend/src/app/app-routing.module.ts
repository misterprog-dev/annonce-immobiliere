import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListAds } from 'src/app/modules/ads/list-ads/list-ads';
import { Main } from 'src/app/main';

const routes: Routes = [
  {
    path: '', component: Main,
    children: [
      {
        path: 'home', component: ListAds
      },
      { path: '', redirectTo: '/home', pathMatch: 'full' },
    ]
  },
  {
    path: '**', redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
