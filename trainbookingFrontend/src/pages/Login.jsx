import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/users/login', { email, password });
            const token = response.data.data; // your ResponseStructure
            localStorage.setItem('token', token);
            navigate('/search');
        } catch (err) {
            setError('Invalid email or password!');
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>🚂 Train Booking</h2>
                <h3 style={styles.subtitle}>Login</h3>

                {error && <p style={styles.error}>{error}</p>}

                <form onSubmit={handleLogin}>
                    <input
                        style={styles.input}
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <input
                        style={styles.input}
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button style={styles.button} type="submit">
                        Login
                    </button>
                </form>

                <p style={styles.link}>
                    Don't have an account?{' '}
                    <span
                        style={styles.linkText}
                        onClick={() => navigate('/register')}
                    >
                        Register here
                    </span>
                </p>
            </div>
        </div>
    );
}

const styles = {
    container: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        backgroundColor: '#f0f2f5'
    },
    card: {
        backgroundColor: 'white',
        padding: '40px',
        borderRadius: '10px',
        boxShadow: '0 4px 15px rgba(0,0,0,0.1)',
        width: '350px'
    },
    title: {
        textAlign: 'center',
        color: '#1a73e8',
        marginBottom: '5px'
    },
    subtitle: {
        textAlign: 'center',
        color: '#333',
        marginBottom: '20px'
    },
    input: {
        width: '100%',
        padding: '12px',
        marginBottom: '15px',
        borderRadius: '6px',
        border: '1px solid #ddd',
        fontSize: '14px',
        boxSizing: 'border-box'
    },
    button: {
        width: '100%',
        padding: '12px',
        backgroundColor: '#1a73e8',
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
    link: {
        textAlign: 'center',
        marginTop: '15px',
        color: '#666'
    },
    linkText: {
        color: '#1a73e8',
        cursor: 'pointer',
        fontWeight: 'bold'
    }
};

export default Login;