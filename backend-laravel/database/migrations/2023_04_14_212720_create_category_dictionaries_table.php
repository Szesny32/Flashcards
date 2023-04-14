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
        Schema::create('category_dictionaries', function (Blueprint $table) {
            $table->id();
            $table->string('category', 256);
            $table->unsignedBigInteger('category_group_id');
            $table->foreign('category_group_id')->references('id')->on('category_dictionaries');

        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('category_dictionaries');
    }
};
