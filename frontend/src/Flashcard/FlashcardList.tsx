
interface RowData{
    id: number,
    category: string,
    question: string,
    answer: string,
    refresh: () => void
}


function Row({id, category, question, answer, refresh} : RowData){

    function handleClick(){
        const api = "http://localhost:8080/api/"
        const deleteFlashcardURL = api + `deleteFlashcard/${id}`;


        async function deleteFlashcard() {
            try {
                const response = await fetch(deleteFlashcardURL); 
                if (!response.ok) {
                throw new Error('Network response was not ok');
                }
                const data = await response.json(); // Pobranie odpowiedzi z API
                if (data === true) {
                    alert("Flashcard deleted successfully!");
                } else {
                    alert("Failed to delete flashcard.");
                }
            } catch (error) {
                alert('Failed to load flashcards data');
            }
            refresh();
        }
        deleteFlashcard();
    

    }




    return (
        <tr key={id} className='flashcard-list-row'>
            <td className='flashcard-list-buttons-container'>
                <button className='flashcard-list-button' onClick={handleClick}>
                    <img src="edit-icon.png" alt="Modify" className='flashcard-list-button-img'/>
                </button>
                
                <button className='flashcard-list-button' onClick={handleClick}>
                    <img src="delete-icon.png" alt="Delete" className='flashcard-list-button-img'/>
                </button>
            </td>
            <td>{category}</td>
            <td>{question}</td>
            <td>{answer}</td>
        </tr>
    );

}


import FlashcardData from '../Model/FlashcardData'

interface FlashcardListProps{
    flashcards: FlashcardData[],
    refresh: () => void
}
export default function FlashcardList({flashcards, refresh} : FlashcardListProps){

    


    return (       
        <table className='flashcard-list'>
             <colgroup>
                <col style={{ width: '10%' }} />
                <col style={{ width: '10%' }} />
                <col style={{ width: '40%'}} />
                <col style={{ width: '40%' }} />
            </colgroup>
            <thead>
                <tr key={-1}>
                    <th className = 'flashcard-list-head'></th>
                    <th className = 'flashcard-list-head'>Category</th>
                    <th className = 'flashcard-list-head'>Question</th>
                    <th className = 'flashcard-list-head'>Answer</th>
                </tr>
            </thead>
            <tbody>
                { flashcards.map((flashcard) => (<Row id={flashcard.id} category={flashcard.categoryName} question={flashcard.question} answer={flashcard.answer} refresh={refresh}/> )) }
            </tbody>
        </table>
    )
}