import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';

function BookTicket() {
    const location = useLocation();
    const navigate = useNavigate();
    const train = location.state?.train;

    const [seatNumber, setSeatNumber] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleBook = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const response = await api.post('/booking/bookSeat', {
                trainId: train.id,
                seatNumber: parseInt(seatNumber)
            });

            setSuccess('Booking successful! 🎉');
            setTimeout(() => navigate('/history'), 2000);

        } catch (err) {
            setError('Booking failed! Please try again.');
        } finally {
            setLoading(false);
        }
    };

    if (!train) {
        return (
            <div style={styles.container}>
                <p>No train selected!</p>
                <button onClick={() => navigate('/search')}>
                    Go Back to Search
                </button>
            </div>
        );
    }

    return (
        <div style={styles.container}>
            {/* Navbar */}
            <div style={styles.navbar}>
                <h2 style={styles.navTitle}>🚂 Train Booking</h2>
                <button
                    style={styles.backBtn}
                    onClick={() => navigate('/search')}
                >
                    ← Back to Search
                </button>
            </div>

            <div style={styles.card}>
                <h3 style={styles.title}>Book Ticket</h3>

                {/* Train Details */}
                <div style={styles.trainInfo}>
                    <h4 style={styles.trainName}>{train.trainName}</h4>
                    <p style={styles.detail}>
                        🚉 {train.source} → {train.destination}
                    </p>
                    <p style={styles.detail}>
                        🕐 {train.departureTime} → {train.arrivalTime}
                    </p>
                    <p style={styles.detail}>
                        💺 Available Seats: {train.availableSeats}
                    </p>
                    <p style={styles.fare}>Fare: ₹{train.fare}</p>
                </div>

                <hr style={styles.divider} />

                {/* Booking Form */}
                {error && <p style={styles.error}>{error}</p>}
                {success && <p style={styles.success}>{success}</p>}

                <form onSubmit={handleBook}>
                    <label style={styles.label}>Enter Seat Number</label>
                    <input
                        style={styles.input}
                        type="number"
                        placeholder="e.g. 15"
                        value={seatNumber}
                        onChange={(e) => setSeatNumber(e.target.value)}
                        min="1"
                        max={train.totalSeats}
                        required
                    />
                    <button
                        style={styles.bookBtn}
                        type="submit"
                        disabled={loading}
                    >
                        {loading ? 'Booking...' : `Book Now - ₹${train.fare}`}
                    </button>
                </form>
            </div>
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
        alignItems: 'center'
    },
    navTitle: {
        color: 'white',
        margin: 0
    },
    backBtn: {
        backgroundColor: 'transparent',
        color: 'white',
        border: '1px solid white',
        padding: '8px 16px',
        borderRadius: '6px',
        cursor: 'pointer'
    },
    card: {
        backgroundColor: 'white',
        margin: '30px auto',
        padding: '30px',
        borderRadius: '10px',
        boxShadow: '0 4px 15px rgba(0,0,0,0.1)',
        maxWidth: '500px'
    },
    title: {
        textAlign: 'center',
        color: '#333',
        marginBottom: '20px'
    },
    trainInfo: {
        backgroundColor: '#f8f9fa',
        padding: '15px',
        borderRadius: '8px',
        marginBottom: '20px'
    },
    trainName: {
        color: '#1a73e8',
        margin: '0 0 10px 0'
    },
    detail: {
        color: '#666',
        margin: '5px 0',
        fontSize: '14px'
    },
    fare: {
        fontSize: '18px',
        fontWeight: 'bold',
        color: '#333',
        marginTop: '10px'
    },
    divider: {
        border: 'none',
        borderTop: '1px solid #eee',
        margin: '20px 0'
    },
    label: {
        display: 'block',
        color: '#333',
        marginBottom: '8px',
        fontWeight: 'bold'
    },
    input: {
        width: '100%',
        padding: '12px',
        borderRadius: '6px',
        border: '1px solid #ddd',
        fontSize: '14px',
        boxSizing: 'border-box',
        marginBottom: '15px'
    },
    bookBtn: {
        width: '100%',
        padding: '12px',
        backgroundColor: '#34a853',
        color: 'white',
        border: 'none',
        borderRadius: '6px',
        fontSize: '16px',
        cursor: 'pointer'
    },
    error: {
        color: 'red',
        textAlign: 'center',
        marginBottom: '10px'
    },
    success: {
        color: 'green',
        textAlign: 'center',
        marginBottom: '10px'
    }
};

export default BookTicket;