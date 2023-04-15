export interface FlashcardData{
    id: number;
    main_category_id: number;
    sub_category_id: number;
    question: string;
    question_type_id: number;
    question_image : number | null;
    answer: string;
    answer_type_id: number;
    answer_image : number | null;
}