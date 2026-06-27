
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';

function SearchTrain() {
    const [source, setSource] = useState('');
    const [destination, setDestination] = useState('');
    const [trains, setTrains] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
        const response = await api.get('/trains/search', {
            params: { source, destination }
        });

        console.log(response.data); // check response
        setTrains(response.data.data);

    } catch (err) {
        setError('No trains found for this route!');
        setTrains([]);
    } finally {
        setLoading(false);
    }
};

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const handleBook = (train) => {
        navigate('/book', { state: { train } });
    };

    return (
        <div style={styles.container}>
            {/* Navbar */}
            <div style={styles.navbar}>
                <h2 style={styles.navTitle}>🚂 Train Booking</h2>
                <div>
                    <button
                        style={styles.navBtn}
                        onClick={() => navigate('/history')}
                    >
                        My Bookings
                    </button>
                    <button
                        style={styles.logoutBtn}
                        onClick={handleLogout}
                    >
                        Logout
                    </button>
                </div>
            </div>

            {/* Search Form */}
            <div style={styles.searchCard}>
                <h3 style={styles.searchTitle}>Search Trains</h3>
                <form onSubmit={handleSearch} style={styles.form}>
                    <input
                        style={styles.input}
                        type="text"
                        placeholder="From (Source)"
                        value={source}
                        onChange={(e) => setSource(e.target.value)}
                        required
                    />
                    <input
                        style={styles.input}
                        type="text"
                        placeholder="To (Destination)"
                        value={destination}
                        onChange={(e) => setDestination(e.target.value)}
                        required
                    />
                    <button style={styles.searchBtn} type="submit">
                        {loading ? 'Searching...' : 'Search Trains'}
                    </button>
                </form>
            </div>

            {/* Error */}
            {error && <p style={styles.error}>{error}</p>}

            {/* Train Results */}
            {trains.length > 0 && (
                <div style={styles.results}>
                    <h3 style={styles.resultsTitle}>
                        Available Trains ({trains.length})
                    </h3>
                    {trains.map((train) => (
                        <div key={train.id} style={styles.trainCard}>
                            <div style={styles.trainInfo}>
                                <h4 style={styles.trainName}>
                                    {train.trainName}
                                </h4>
                                <p style={styles.trainDetail}>
                                    🚉 {train.source} → {train.destination}
                                </p>
                                <p style={styles.trainDetail}>
                                    🕐 {train.departureTime} → {train.arrivalTime}
                                </p>
                                <p style={styles.trainDetail}>
                                    💺 Available Seats: {train.availableSeats}
                                </p>
                            </div>
                            <div style={styles.trainRight}>
                                <p style={styles.fare}>₹{train.fare}</p>
                                <button
                                    style={styles.bookBtn}
                                    onClick={() => handleBook(train)}
                                    disabled={train.availableSeats === 0}
                                >
                                    {train.availableSeats === 0
                                        ? 'Full'
                                        : 'Book Now'}
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}
const styles = {
    container: {
        minHeight: '100vh',
        backgroundColor: '#f0f2f5'
    },
    navbar: {
        backgroundColor: '#1a73e8',
        padding: '15px 30px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        color: '#fff'
    },
    navTitle: {
        margin: 0
    },
    navBtn: {
        marginRight: '10px',
        padding: '10px 15px',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        backgroundColor: '#fff',
        color: '#1a73e8'
    },
    logoutBtn: {
        padding: '10px 15px',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        backgroundColor: '#dc3545',
        color: '#fff'
    },
    searchCard: {
        backgroundColor: '#fff',
        padding: '30px',
        margin: '30px auto',
        width: '80%',
        maxWidth: '600px',
        borderRadius: '10px',
        boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
    },
    searchTitle: {
        textAlign: 'center',
        marginBottom: '20px'
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        gap: '15px'
    },
    input: {
        padding: '12px',
        borderRadius: '5px',
        border: '1px solid #ccc'
    },
    searchBtn: {
        padding: '12px',
        border: 'none',
        borderRadius: '5px',
        backgroundColor: '#1a73e8',
        color: '#fff',
        cursor: 'pointer'
    },
    error: {
        color: 'red',
        textAlign: 'center'
    },
    results: {
        width: '80%',
        margin: '20px auto'
    },
    resultsTitle: {
        marginBottom: '20px'
    },
    trainCard: {
        backgroundColor: '#fff',
        padding: '20px',
        marginBottom: '15px',
        borderRadius: '10px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
    },
    trainInfo: {
        flex: 1
    },
    trainName: {
        margin: '0 0 10px 0'
    },
    trainDetail: {
        margin: '5px 0'
    },
    trainRight: {
        textAlign: 'right'
    },
    fare: {
        fontSize: '20px',
        fontWeight: 'bold'
    },
    bookBtn: {
        padding: '10px 15px',
        border: 'none',
        borderRadius: '5px',
        backgroundColor: '#28a745',
        color: '#fff',
        cursor: 'pointer'
    }
};

export default SearchTrain;