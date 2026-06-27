import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';

function Register() {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        phone: ''
    });
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await api.post('/users/register-user', formData);
            setSuccess('Registration successful! Please login.');
            setTimeout(() => navigate('/login'), 2000);
        } catch (err) {
            setError('Registration failed! Email may already exist.');
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>🚂 Train Booking</h2>
                <h3 style={styles.subtitle}>Register</h3>

                {error && <p style={styles.error}>{error}</p>}
                {success && <p style={styles.success}>{success}</p>}

                <form onSubmit={handleRegister}>
                    <input
                        style={styles.input}
                        type="text"
                        name="name"
                        placeholder="Full Name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                    <input
                        style={styles.input}
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                    <input
                        style={styles.input}
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    <input
                        style={styles.input}
                        type="text"
                        name="phone"
                        placeholder="Phone Number"
                        value={formData.phone}
                        onChange={handleChange}
                        required
                    />
                    <button style={styles.button} type="submit">
                        Register
                    </button>
                </form>

                <p style={styles.link}>
                    Already have an account?{' '}
                    <span
                        style={styles.linkText}
                        onClick={() => navigate('/login')}
                    >
                        Login here
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
    success: {
        color: 'green',
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

export default Register;