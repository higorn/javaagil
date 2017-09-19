import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {
  MdInputModule, MdButtonModule, MdCardModule, MdTabsModule, MdToolbarModule,
  MdIconModule, MdCheckboxModule, MdGridListModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MdInputModule,
    MdButtonModule,
    MdCardModule,
    MdTabsModule,
    MdToolbarModule,
    MdIconModule,
    MdCheckboxModule,
    MdGridListModule,
  ],
  exports: [
    MdInputModule,
    MdButtonModule,
    MdCardModule,
    MdTabsModule,
    MdToolbarModule,
    MdIconModule,
    MdCheckboxModule,
    MdGridListModule,
  ]
})
export class CustomMdModule {
}
