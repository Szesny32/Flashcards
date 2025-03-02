import Logo from './Logo'
import Menu from './Menu'
import Flashcard from './Flashcard/Flashcard'
import FlashcardList from './Flashcard/FlashcardList'
import FlashcardForm from './Flashcard/FlashcardForm'
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import {useState, useEffect} from 'react'
import FlashcardData from './Model/FlashcardData'

export default function App(){
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [flashcards, setFlashcards] = useState<FlashcardData[]>([]);
  const [activeFlashcard, setActiveFlashcard] = useState<FlashcardData | null>(null);
  const [category, setCategory] = useState<string>(''); 

  const api = "http://localhost:8080/api/"
  const getFlashcardURL = api + `flashcards/?category=${category}`;

  function RandFlashcard(){
    if (flashcards.length > 0) {
        const randomIndex = Math.floor(Math.random() * flashcards.length);
        console.log(randomIndex); 
        setActiveFlashcard(flashcards[randomIndex]);
    }
  }

  async function fetchFlashcard(){
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
  }

      
  useEffect(() => {
    const fetchFlashcardData = async () => fetchFlashcard()
 
    fetchFlashcardData();
    RandFlashcard();
  }, [category]); 


  useEffect(() => {
      if (flashcards.length > 0) { 
        RandFlashcard();
        }
    }, [flashcards]); 


  if (loading) {
      return <div>Loading...</div>;
    }

  if (error) {
      return <div>{error}</div>;
  }


  return (
    <div>
      
      <Router>
       <Logo/>
       <Menu/>
        <Routes>
          <Route path="/" element={<Flashcard flashcard = {activeFlashcard} randNewFlashCard={RandFlashcard} />} />
          <Route path="/list" element={<FlashcardList flashcards = {flashcards} refresh = {fetchFlashcard} />} />
          <Route path="/add" element={<FlashcardForm />} />
        </Routes>
    </Router>


    </div>
  );
}