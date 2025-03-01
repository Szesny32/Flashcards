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

function MockFlashcard() : FlashcardData{
    return {
        id: 1,
        question: "What is the capital of France? aaaaaaaaaaaaaaaaaaaaaa  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        answer: "Paris",
        categoryName:  "General Knowledge",
        categoryPath: ""
        
    } 
}

import {useState, useEffect } from 'react'

export default function FlashCard(){

    
    const [flashcard, setFlashcard] =  useState<FlashcardData | null>(MockFlashcard())
    const [answerMode, setAnswerMode] =  useState<boolean>(false)

    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    function changeAnswerModeClick(){
        setAnswerMode(!answerMode)
    }


    const apiURL = "http://localhost:8080/api/flashcard/16"
    
    useEffect(() => {
        


        const fetchFlashcardData = async () => {
          try {
            const response = await fetch(apiURL); // Zamień na właściwy endpoint
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            const data: FlashcardData = await response.json();
            setFlashcard(data);
          } catch (error) {
            setError('Failed to load flashcard data');
          } finally {
            setLoading(false);
          }
        };
        fetchFlashcardData();
    }, []); 
    
    if (loading) {
        return <div>Loading...</div>;
      }
    
      if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className='flashcard'>
            {flashcard && (
                <>
                  <div className ='flashcard-question'><b>Question:</b> {flashcard.question}</div>
                    <div className='flashcard-answer'><b>Answer:</b> {answerMode? '' :flashcard.answer}</div>
                    <div className='flashcard-button-container'>
                        <Button text = {answerMode? 'Hide' : 'Show'} handleClick={changeAnswerModeClick}/>
                        <Button text = 'Next' handleClick={() => setAnswerMode(false)}/>
                    </div>
                </>
            )}
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