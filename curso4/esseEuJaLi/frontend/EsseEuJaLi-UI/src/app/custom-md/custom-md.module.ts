import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {
  MdInputModule, MdButtonModule, MdCardModule, MdTabsModule, MdToolbarModule,
  MdIconModule, MdCheckboxModule, MdGridListModule, MdTableModule
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
    MdTableModule,
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
    MdTableModule,
  ]
})
export class CustomMdModule {
}
