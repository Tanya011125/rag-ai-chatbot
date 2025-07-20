import React, { useState } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [userMessage, setUserMessage] = useState('');
  const [chatHistory, setChatHistory] = useState([]); 
  const [sessionTitles, setSessionTitles] = useState([]); 
  const [isTyping, setIsTyping] = useState(false);

  const sendMessage = async () => {
    if (!userMessage.trim()) return;

    const userEntry = { role: 'User', text: userMessage };
    const updatedHistory = [...chatHistory, userEntry];
    setChatHistory(updatedHistory);

    if (sessionTitles.length === 0 || chatHistory.length === 0) {
      setSessionTitles((prev) => [...prev, userMessage.slice(0, 30)]);
    }

    setUserMessage('');
    setIsTyping(true);

    try {
      const res = await fetch('http://localhost:8080/chat', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: userMessage }),
      });

      const responseText = await res.text();
      const botEntry = { role: 'Bot', text: responseText };
      setChatHistory((prev) => [...prev, botEntry]);
    } catch (error) {
      const errorEntry = { role: 'Bot', text: '⚠️ Error contacting server.' };
      setChatHistory((prev) => [...prev, errorEntry]);
    } finally {
      setIsTyping(false);
    }
  };

  return (
   <div className="d-flex" style={{ height: '100vh', overflow: 'hidden' }}>
      {/* Sidebar */}
      <div className="bg-light p-3 border-end" style={{ width: '250px', overflowY: 'auto', flexShrink: 0 }}>
        <h5>Chat History</h5>
        <ul className="list-group">
          {sessionTitles.map((title, idx) => (
            <li key={idx} className="list-group-item small text-truncate" title={title}>
              {title}
            </li>
          ))}
        </ul>
      </div>

      {/* Main Chat Area */}
      <div className="flex-grow-1 d-flex flex-column p-3">
        <h3 className="mb-3">🧠 Aamantran RAG Chatbot</h3>

        <div className="flex-grow-1 border rounded p-3 mb-3 bg-light" style={{ overflowY: 'auto' }}>
         {chatHistory.map((msg, index) => (
            <div key={index} className={`mb-2 ${msg.role === 'User' ? 'text-end' : 'text-start'}`}>
              <div
                className={`d-inline-block p-2 rounded ${
                  msg.role === 'User' ? 'bg-primary text-white' : 'bg-success text-white'
                }`}
                style={{ maxWidth: '100%', wordBreak: 'break-word', whiteSpace: 'pre-wrap', textAlign: 'left' }}
              >
                {msg.text.split('\n').map((line, i) => {
                  if (line.trim().startsWith('-') || /^\d+[\.\)]/.test(line.trim())) {
                    return <li key={i} style={{ marginLeft: '1.2em' }}>{line}</li>;
                  } else if (line.trim()) {
                    return <p key={i} style={{ marginBottom: '0.5em' }}>{line}</p>;
                  } else {
                    return <br key={i} />;
                  }
                })}
              </div>
            </div>
          ))}

          {isTyping && (
            <div className="typing-indicator">
              <span className="dot"></span>
              <span className="dot"></span>
              <span className="dot"></span>
            </div>
          )}
        </div>

       <div className="input-group">
  <button className="btn btn-danger me-2" onClick={() => setChatHistory([])}>
    Clear Chat
  </button>
  <input
    type="text"
    className="form-control"
    placeholder="Ask your question..."
    value={userMessage}
    onChange={(e) => setUserMessage(e.target.value)}
    onKeyDown={(e) => e.key === 'Enter' && sendMessage()}
  />
  <button className="btn btn-primary" onClick={sendMessage}>
    Send
  </button>
</div>

      </div>
    </div>
  );
}

export default App;
