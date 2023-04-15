<?php

namespace App\Http\Controllers;

use App\Models\CategoryDictionary;
use App\Models\DataFormatType;
use App\Models\Flashcard;
use App\Models\Image;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;

class FlashcardController extends Controller
{
    public function getFlashcard(){

        $flashcard = Flashcard::select('id', 'question', 'question_type_id', 'question_image', 'answer', 'answer_type_id', 'answer_image', 'category_id')
        ->inRandomOrder()
        ->first();
        return $flashcard;
    }

    public function addFlashcard(Request $request) {
       
        $request->validate([
        'category_id' => 'required|numeric',
        'question' => 'nullable|string|max:256',
        'question_type_id' => 'required|numeric',
        'question_image' => 'nullable|numeric',
        'answer' => 'nullable|string|max:256',
        'answer_type_id' => 'required|numeric',
        'answer_image' => 'nullable|numeric'
        ]);

        $flashcard = new Flashcard;
        $flashcard->category_id = $request->input('category_id');
        $flashcard->question = $request->input('question');
        $flashcard->question_type_id = $request->input('question_type_id');
        $flashcard->question_image = $request->input('question_image');
        $flashcard->answer = $request->input('answer');
        $flashcard->answer_type_id = $request->input('answer_type_id');
        $flashcard->answer_image = $request->input('answer_image');
        $flashcard->save();
        
        return response()->json(['message' => 'Flashcard added successfully']);
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

    public function getCategories() {
        $categories = CategoryDictionary::all();
        return response()->json(['categories' => $categories]);
    }

    public function getDataFormats() {
        $dataFormats = DataFormatType::all();
        return response()->json(['dataFormats' => $dataFormats]);
    }

}
