<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Flashcard extends Model
{
    use HasFactory;
    public $timestamps = false;
    protected $fillable = [
        'main_category_id',
        'sub_category_id',
        'question',
        'question_type_id',
        'question_image',
        'answer',
        'answer_type_id',
        'answer_image'
    ];

    protected $casts = [
        'question_image' => 'binary',
        'answer_image' => 'binary'
    ];
}
