import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import VideoEditListPage from "./video/edit/list/VideoEditListPage";
import VideoQuizTryPage from "./video/quiz/try/VideoQuizTryPage";
import VideoQuizResultPage from "./video/quiz/result/VideoQuizResultPage";

function App() {
  return (
    <>
      <Router>
        <Routes>
              <Route path="/" element={<VideoEditListPage/>} />
              <Route path="/video/edit/list" element={<VideoEditListPage/>} />
              <Route path="/video/quiz/try" element={<VideoQuizTryPage/>} />
              <Route path="/video/quiz/result" element={<VideoQuizResultPage/>} />
          </Routes>
      </Router>
    </>
  )
}

export default App;