import Logo from './Logo'
import Menu from './Menu'
import Flashcard from './Flashcard/Flashcard'
import FlashcardList from './Flashcard/FlashcardList'
import FlashcardForm from './Flashcard/FlashcardForm'
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom'

export default function App(){

  return (
    <div>
      
      <Router>
       <Logo/>
       <Menu/>
        <Routes>
          <Route path="/" element={<Flashcard />} />
          <Route path="/dashboard" element={<Flashcard />} />
          <Route path="/list" element={<FlashcardList />} />
          <Route path="/add" element={<FlashcardForm />} />
        </Routes>
    </Router>


    </div>
  );
}