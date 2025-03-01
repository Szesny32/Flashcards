import { Link } from 'react-router-dom';

export default function Menu(){
    return (
        <div className='menu-container'>
            <Link to ='/' className='menu-option'>Dashboard</Link>
            <Link to ='/add'  className='menu-option'>New Flashcard</Link>
            <Link to ='/list' className='menu-option'>Flashcard list</Link>
            <div className='menu-option'>Categories list</div>
        </div>
    );
}