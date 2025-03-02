interface CategoryData{
    id: number,
    name: string,
  }


import {useState} from 'react'  
import FlashcardData from '../Model/FlashcardData'

interface FlashCardProps {
  flashcard: FlashcardData | null;
  randNewFlashCard: () => void;
}
export default function FlashCard({ flashcard, randNewFlashCard }: FlashCardProps){

    const [answerMode, setAnswerMode] =  useState<boolean>(false)

    function changeAnswerMode(){
      setAnswerMode(!answerMode)
    }

    function offAnswerMode(){
      setAnswerMode(false)
    }

    return (
        <div className='flashcard-container'>
            <div className={answerMode?'flashcard-back flashcard ' : 'flashcard'}>
                {flashcard && (
                    <>
                        <div className ='flashcard-category'><b>Category:</b> {flashcard.categoryName}</div>
                        <div className ='flashcard-question'><b>Question:</b> {flashcard.question}</div>
                        <div className='flashcard-answer'><b>Answer:</b> {answerMode? flashcard.answer : ''}</div>
                        <div className='flashcard-button-container'>
                            <Button text = {answerMode? 'Hide' : 'Show'} handleClick={changeAnswerMode}/>
                            <Button text = 'Next' handleClick={() => {randNewFlashCard(); offAnswerMode();}}/>
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