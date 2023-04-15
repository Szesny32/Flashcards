<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\FlashcardController;
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "api" middleware group. Make something great!
|
*/

Route::get('/getFlashcard', [FlashcardController::class, 'getFlashcard']);
Route::post('/addFlashcard', [FlashcardController::class, 'addFlashcard']);
Route::get('/getImage/{id}', [FlashcardController::class, 'getImage']);

Route::get('/getCategories', [FlashcardController::class, 'getCategories']);
Route::get('/getDataFormatTypes', [FlashcardController::class, 'getDataFormats']);


Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
