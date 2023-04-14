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
            
            $table->unsignedBigInteger('main_category_id');
            $table->foreign('main_category_id')->references('id')->on('category_dictionaries');
            
            $table->unsignedBigInteger('sub_category_id');
            $table->foreign('sub_category_id')->references('id')->on('category_dictionaries');
            
            $table->string('question', 256)->nullable();
            $table->unsignedBigInteger('question_type_id');
            $table->foreign('question_type_id')->references('id')->on('data_format_types');
            $table->binary('question-image')->nullable();

            $table->string('answer', 256)->nullable();
            $table->unsignedBigInteger('answer_type_id');
            $table->foreign('answer_type_id')->references('id')->on('data_format_types');
            $table->binary('answer-image')->nullable();

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
