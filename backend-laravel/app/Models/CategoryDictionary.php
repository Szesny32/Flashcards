<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class CategoryDictionary extends Model
{
    use HasFactory;
    public $timestamps = false;
    protected $fillable = [
        'category',
        'category_group_id'
    ];
}
