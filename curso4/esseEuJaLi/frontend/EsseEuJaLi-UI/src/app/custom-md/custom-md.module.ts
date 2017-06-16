import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MdInputModule, MdButtonModule, MdCardModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MdInputModule,
    MdButtonModule,
    MdCardModule,
  ],
  exports: [
    MdInputModule,
    MdButtonModule,
    MdCardModule,
  ]
})
export class CustomMdModule {
}
