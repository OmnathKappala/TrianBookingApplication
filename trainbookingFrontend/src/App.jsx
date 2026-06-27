import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import SearchTrain from './pages/SearchTrain';
import BookingHistory from './pages/BookingHistory';
import Home from './pages/Home';
import BookTicket from './pages/BookTicket';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/search" element={<SearchTrain />} />
                <Route path="/book" element={<BookTicket/>}/>
                <Route path="/history" element={<BookingHistory />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;