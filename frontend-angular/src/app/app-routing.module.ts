import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FlipCardComponent } from './flip-card/flip-card.component';
import { AddFlashcardComponent } from './add-flashcard/add-flashcard.component';

const routes: Routes = [
  { path: '', redirectTo: '/flip-card', pathMatch: 'full' },
  { path: 'flip-card', component: FlipCardComponent },
  { path: 'add-flashcard', component: AddFlashcardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
