import {useState} from 'react'

export default function Logo(){
    const [counter, setCount] = useState(3);

    function handleClick(){
        setCount((counter + 1) % 12);
        
    }


    let  logoURL = '.logo-png'
    if(counter < 3)
        logoURL = './logo-A.png'
    else if(counter < 6)
        logoURL = './logo-B.png'
    else if(counter < 9)
        logoURL = './logo-C.png'
    else
        logoURL = './logo-D.png'



    return (
        
        <div className='logo-container'>
            <button onClick = {handleClick}>
                <img src={logoURL}/>
                <p className='logo-text'>Flashcards</p>
            </button>
        </div>

        
        );
}