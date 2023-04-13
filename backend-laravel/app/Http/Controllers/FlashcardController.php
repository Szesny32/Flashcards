<?php

namespace App\Http\Controllers;

use App\Models\Flashcard;
use Illuminate\Http\Request;

class FlashcardController extends Controller
{
    public function getFlashcard(){

        $fleshcard = Flashcard::select('id', 'category', 'question', 'answer')
        ->inRandomOrder()
        ->first();
        
        return $fleshcard;


    }
}
