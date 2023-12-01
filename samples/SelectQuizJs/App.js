// SelectQuiz 컴포넌트를 활용해보는 샘플

import React, { useState } from 'react';
import SelectQuiz from './SelectQuiz';

function App() {

  const [words, setWords] = useState(["App", "Banana", "Clue", "Do"])

  const onAllCorrect = () => {
    console.log("ALL CORRECT !")
    setWords(["Extra", "Flag", "Guess", "Help"])
  }
  

  return (
    <>
      <SelectQuiz words={words} onAllCorrect={onAllCorrect}/>
    </>
  )
}

export default App;