import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { Main } from 'src/app/main';
import { ListAds } from 'src/app/modules/ads/list-ads/list-ads';
import { AppComponent } from 'src/app/app.component';
import { ButtonModule } from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { HttpClientModule } from '@angular/common/http';
import { TableModule } from 'primeng/table';
import { EditAdModal } from 'src/app/modules/ads/edit-ad/edit-ad.modal';
import { DeleteAdModal } from 'src/app/modules/ads/delete-ad/delete-ad.modal';
import { DialogModule } from 'primeng/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';

@NgModule({
  declarations: [
    AppComponent,
    Main,
    ListAds,
    EditAdModal,
    DeleteAdModal
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ButtonModule,
    ToastModule,
    TableModule,
    DialogModule,
    ReactiveFormsModule,
    InputTextModule,
    InputTextareaModule,
    FileUploadModule
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
