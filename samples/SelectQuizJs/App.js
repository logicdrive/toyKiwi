// SelectQuiz 컴포넌트를 활용해보는 샘플

import React, { useState } from 'react';
import SelectQuiz from './SelectQuiz';

function App() {

  const [words, setWords] = useState(["A", "B", "C", "D"])

  const onAllCorrect = () => {
    console.log("ALL CORRECT !")
    setWords(["E", "F", "G", "H"])
  }
  

  return (
    <>
      <SelectQuiz words={words} onAllCorrect={onAllCorrect}/>
    </>
  )
}

export default App;