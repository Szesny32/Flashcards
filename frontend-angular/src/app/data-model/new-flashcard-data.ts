export interface NewFlashcardData{
    category_id: number;
    question: string;
    question_type_id: number;
    question_image?: Blob | null;
    answer: string;
    answer_type_id: number;
    answer_image?: Blob | null;
}
