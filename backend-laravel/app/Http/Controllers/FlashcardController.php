<?php

namespace App\Http\Controllers;

use App\Models\Flashcard;
use App\Models\Image;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class FlashcardController extends Controller
{
    public function getFlashcard(){

        $flashcard = Flashcard::select('id', 'main_category_id', 'sub_category_id', 'question', 'question_type_id', 'question_image', 'answer', 'answer_type_id', 'answer_image')
        ->inRandomOrder()
        ->first();
    
        return $flashcard;
    }

    public function getImage($id)
    {
        $image = Image::select('image_blob')->where('id', $id)->first();
        if ($image) {
            return response($image->image_blob)
                ->header('Content-Type', 'image/png')
                ->header('Content-Disposition', 'attachment; filename=image.png');
        } else {
            return response()->json(['message' => 'Image not found'], 404);
        }
    }


}
