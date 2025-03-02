import { useState, useEffect } from "react";

export default function Logo(){
    const [counter, setCounter] = useState(3);


    useEffect(() => {
        const interval = setInterval(() => {
            setCounter((prevCounter) => (prevCounter + 1) % 12);
        }, 1000); 
        return () => clearInterval(interval);
    }, []);

    let logoURL = "./logo-D.png";
    if (counter < 3) logoURL = "./logo-A.png";
    else if (counter < 6) logoURL = "./logo-B.png";
    else if (counter < 9) logoURL = "./logo-C.png";



    return (
        
        <div className='logo-container'>
            <button >
                <img src={logoURL}/>
                <p className='logo-text'>Flashcards</p>
            </button>
        </div>

        
        );
}