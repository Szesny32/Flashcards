interface CategoryData{
    id: number,
    name: string,
  }
  
  interface FlashcardData{
    id: number,
    question: string,
    answer: string,
    categoryName: string,
    categoryPath: string,
  }

import {useState, useEffect } from 'react'

export default function FlashCard(){

    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const [flashcards, setFlashcards] = useState<FlashcardData[]>([]);
    const [answerMode, setAnswerMode] =  useState<boolean>(false)
    const [category, setCategory] = useState<string>(''); 
    const [activeFlashcard, setActiveFlashcard] = useState<FlashcardData | null>(null);

    const api = "http://localhost:8080/api/"
    const getFlashcardURL = api + `flashcards/?category=${category}`;

    function changeAnswerModeClick(){
        setAnswerMode(!answerMode)
    }


    function RandNewQuestion(){
        if (flashcards.length > 0) {
            const randomIndex = Math.floor(Math.random() * flashcards.length);
            console.log(randomIndex); 
            setActiveFlashcard(flashcards[randomIndex]);
        }
        setAnswerMode(false);
    }

    

    
    useEffect(() => {
        const fetchFlashcardData = async () => {
          try {
            const response = await fetch(getFlashcardURL); 
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            const data: FlashcardData[] = await response.json()
            setFlashcards(data);
          } catch (error) {
            setError('Failed to load flashcards data');
          } finally {
            setLoading(false);
          }
        };
        fetchFlashcardData();
        RandNewQuestion();

    }, [category]); 
    


    useEffect(() => {
        if (flashcards.length > 0) { 
            RandNewQuestion();
          }
      }, [flashcards]); 
    

    if (loading) {
        return <div>Loading...</div>;
      }
    
    if (error) {
        return <div>{error}</div>;
    }



    return (
        <div className='flashcard-container'>
            <div className={answerMode?'flashcard-back flashcard ' : 'flashcard'}>
                {activeFlashcard && (
                    <>
                        <div className ='flashcard-category'><b>Category:</b> {activeFlashcard.categoryName}</div>
                        <div className ='flashcard-question'><b>Question:</b> {activeFlashcard.question}</div>
                        <div className='flashcard-answer'><b>Answer:</b> {answerMode? activeFlashcard.answer : ''}</div>
                        <div className='flashcard-button-container'>
                            <Button text = {answerMode? 'Hide' : 'Show'} handleClick={changeAnswerModeClick}/>
                            <Button text = 'Next' handleClick={RandNewQuestion}/>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
}

interface ButtonData {
    text: string
    handleClick: () => void,
}

function Button({text, handleClick} : ButtonData){
       return <button className ='flashcard-button' onClick={handleClick}>{text}</button>
}