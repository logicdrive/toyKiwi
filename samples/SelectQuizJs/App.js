// SelectQuiz 컴포넌트를 활용해보는 샘플

import React from 'react';
import SelectQuiz from './SelectQuiz';

function App() {

  const onAllCorrect = () => {
    console.log("ALL CORRECT !")
  }

  return (
    <>
      <SelectQuiz words={["A", "B", "C", "D"]} onAllCorrect={onAllCorrect}/>
    </>
  )
}

export default App;