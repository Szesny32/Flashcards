<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('flashcards', function (Blueprint $table) {
            $table->id();
            
            $table->unsignedBigInteger('category_id');
            $table->foreign('category_id')->references('id')->on('category_dictionaries');  
            
            $table->string('question', 256)->nullable();
            $table->unsignedBigInteger('question_type_id');
            $table->foreign('question_type_id')->references('id')->on('data_format_types');

            $table->unsignedBigInteger('question_image')->nullable();
            $table->foreign('question_image')->references('id')->on('images');

            $table->string('answer', 256)->nullable();
            $table->unsignedBigInteger('answer_type_id');
            $table->foreign('answer_type_id')->references('id')->on('data_format_types');
            
            $table->unsignedBigInteger('answer_image')->nullable();
            $table->foreign('answer_image')->references('id')->on('images');

        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('flashcards');
    }
};
