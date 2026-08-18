import { useState, useEffect } from 'react'

function App() {
  const [rooms, setRooms] = useState([])
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [isLoggedIn, setIsLoggedIn] = useState(false)
  const [authToken, setAuthToken] = useState('')
  const [loggedUser, setLoggedUser] = useState('')
  const [isRegistering, setIsRegistering] = useState(false);
  const [regName, setRegName] = useState('');
  const [regSurname, setRegSurname] = useState('');
  const [regEmail, setRegEmail] = useState('');
  const [regPassword, setRegPassword] = useState('');
  const [selectedRoom, setSelectedRoom] = useState('');
  const [checkInDate, setCheckInDate] = useState('');
  const [checkOutDate, setCheckOutDate] = useState('');
  const [customerId, setCustomerId] = useState('');
  const [catering, setCatering] = useState('NONE');

  useEffect(() => {
    fetch('http://localhost:8080/api/rooms')
      .then(response => response.json())
      .then(data => setRooms(data))
      .catch(error => console.error("Errore nel caricamento delle stanze: ", error));
  }, [])

  const handleLogin = (e) => {
    e.preventDefault();
    if (!email || !password) return;

    const token = 'Basic ' + btoa(email + ':' + password);

    fetch('http://localhost:8080/api/rooms', {
      headers: { 'Authorization': token }
    })
      .then(response => {
        if(response.ok) {
          setAuthToken(token);
          setIsLoggedIn(true);
          setLoggedUser(email);
          alert("Login effettuato con successo!");
        } else {
          alert("Credenziali errate o utente non autorizzato.");
        }
      })
      .catch(error => console.error("Errore di login: ", error));
  }

  const handleRegister = (e) => {
    e.preventDefault();
    if (!regName || !regSurname || !regEmail || !regPassword) return;

    const registrationPayload = {
      name: regName,
      surname: regSurname,
      email: regEmail,
      password: regPassword
    };

    fetch('http://localhost:8080/api/customers', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(registrationPayload)
    })
    .then(response => {
      if (response.ok) {
        alert("Registrazione completata con successo! Ora puoi fare il login.");
        setIsRegistering(false); 
        setRegName('');
        setRegSurname('');
        setRegEmail('');
        setRegPassword('');
      } else {
        alert("Errore durante la registrazione. Forse l'email è già registrata.");
      }
    })
    .catch(error => console.error("Errore di registrazione:", error));
  }

  const handleLogout = () => {
    setIsLoggedIn(false);
    setAuthToken('');
    setLoggedUser('');
    setEmail('');
    setPassword('');
    alert("Logout effettuato.");
  }

  const onSubmitBooking = (e) => {
    e.preventDefault();

    const reservationPayload = {
      customerId: parseInt(customerId),
      roomId: selectedRoom,
      checkInDate: checkInDate,
      checkOutDate: checkOutDate,
      cateringOption: catering
    };

    console.log("Invio dati prenorazione:", reservationPayload);

    fetch('http://localhost:8080/api/reservations', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authToken
      },
      body: JSON.stringify(reservationPayload)
    })
    .then(response => {
      if(response.ok){
        return response.json();
      }else {
        return response.json().then(err => { throw new Error(err.message || "La stanza potrebbe essere già occupata.") });
      }
    })
    .then(data => {
      alert("Prenotazione effettuata con successo!\n" + 
        "- ID Prenotazione: " + data.id + "\n" + 
        "- Servizio Ristorazione:: " + data.cateringOption + "\n" + 
        "- Prezzo Totale Calcolato (Stanza + Ristorazione): " + data.totalPrice + "€\n\n" + 
        "Ti abbiamo inviato un'email di conferma!");

      setSelectedRoom(null)
      setCheckIn('')
      setCheckOutDate('')
      setCustomerId('')
      setCatering('NONE')
    })
    .catch(error => {
      alert("Impossibile prenotare: " + error.message);
    });
  }

  const handleBookRoom = (roomId) => {
    const checkIn = prompt("Inserisci la data di Check-In formato(YYYY-MM-DD):", "2026-10-01");
    const checkOut = prompt("Inserisci la data di Check-Out formato(YYYY-MM-DD):", "2026-10-05");
    const customerId = prompt("Inserisci il tuo ID Cliente del database (es. 1, 2, 4, 5...):", "1");

    if (!checkIn || !checkOut || !customerId) return;

    const reservationPayload = {
      customerId: parseInt(customerId),
      roomId: roomId,
      checkInDate: checkIn,
      checkOutDate: checkOut
    };

    fetch('http://localhost:8080/api/reservations', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': authToken
      },
      body: JSON.stringify(reservationPayload)
    })
    .then(response => {
      if(response.ok){
        return response.json();
      }else {
        return response.json().then(err => { throw new Error(err.message || "La stanza potrebbe essere già occupata in queste date.") });
      }
    })
    .then(data => {
      alert("Prenotazione effettuata con successo! ID Prenotazione: " + data.id + "\nPrezzo Totale: " + data.totalPrice + "€");
    })
    .catch(error => {
      alert("Impossibile completare la prenotazione: " + error.message);
    });
  }
  
  return (
    <div style={{ fontFamily: 'sans-serif', backgroundColor: '#f4f6f9', minHeight: '100vh', margin: 0 }}>
      
      <header style={{ backgroundColor: '#1a252f', color: 'white', padding: '20px 40px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <div>
          <h1 style={{ margin: 0 }}>Spring Hotel</h1>
          <p style={{ margin: '5px 0 0 0' }}>SaaS di prenotazione Online</p>
        </div>

        {!isLoggedIn ? (
          <div>
            {isRegistering ? (
              <form onSubmit={handleRegister} style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                <input type="text" placeholder="Nome" value={regName} onChange={e => setRegName(e.target.value)} required style={{ padding: '8px', borderRadius: '4px', border: 'none' }} />
                <input type="text" placeholder="Cognome" value={regSurname} onChange={e => setRegSurname(e.target.value)} required style={{ padding: '8px', borderRadius: '4px', border: 'none' }} />
                <input type="email" placeholder="Email" value={regEmail} onChange={e => setRegEmail(e.target.value)} required style={{ padding: '8px', borderRadius: '4px', border: 'none' }} />
                <input type="password" placeholder="Password" value={regPassword} onChange={e => setRegPassword(e.target.value)} required style={{ padding: '8px', borderRadius: '4px', border: 'none' }} />
                <button type="submit" style={{ backgroundColor: '#27ae60', color: 'white', border: 'none', padding: '8px 15px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold'}}>Registrati</button>
                <button type="button" onClick={() => setIsRegistering(false)} style={{ background: 'none', color: '#3498db', border: 'none', cursor: 'pointer', textDecoration: 'underline' }}>Accedi</button>
              </form>
            ) : (
              <form onSubmit={handleLogin} style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required style={{ padding: '8px', borderRadius: '4px', border: 'none' }} />
                <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required style={{ padding:'8px', borderRadius: '4px', border: 'none' }} />
                <button type="submit" style={{ backgroundColor: '#27ae60', color: 'white', border: 'none', padding: '8px 15px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold'}}>Login</button>
                <button type="button" onClick={() => setIsRegistering(true)} style={{ background: 'none', color: '#3498db', border: 'none', cursor: 'pointer', textDecoration: 'underline' }}>Registrati</button>
              </form>
            )}
          </div>
        ) : (
          <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
            <span>Benvenuto, <strong>{loggedUser}</strong>!</span>
            <button onClick={handleLogout} style={{ backgroundColor: '#c0392b', color: 'white', border: 'none', padding: '8px 15px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>Logout</button>
          </div>
        )}
      </header>

       <div style={{ textAlign: 'center', padding: '60px 20px', background: 'linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url("http://books.toscrape.com/media/cache/fe/72/fe72f0532301ec28892ae79a629a293c.jpg") no-repeat center/cover', color: 'white' }}>
        <h2 style={{ fontSize: '3em', margin: '0 0 10px 0' }}>Grand Hotel Royal</h2>
        <p style={{ fontSize: '1.2em', maxWidth: '600px', margin: '0 auto' }}>Il comfort e l'eleganza nel cuore della città</p>
      </div>

      <main style={{ padding: '40px' }}>
        <h2 style={{ textAlign: 'center', color: '#2c3e50', marginBottom: '30px' }}>Le Nostre Camere Disponibili</h2>

        {rooms.length === 0 ? (
          <p style={{ textAlign: 'center' }}>Caricamento delle stanze o database vuoto...</p>
        ) : (
          <div style={{ display: 'flex', justifyContent: 'center', gap: '25px', flexWrap: 'wrap' }}>
            {rooms.map(room => (
              <div key={room.id} style={{
                border: 'none',
                borderRadius: '12px',
                padding: '25px',
                width: '260px',
                boxShadow: '0 4px 15px rgba(0,0,0,0.05)',
                backgroundColor: '#fff',
                textAlign: 'left'
              }}>
                <h3 style={{ margin: '0 0 10px 0', color: '#2c3e50', fontSize: '1.4em' }}> Stanza {room.numberRoom}</h3>
                <p style={{ color: '#7f8c8d', margin: '5px 0' }}>Tipologia: <strong style={{ color: '#2c3e50' }}>{room.type}</strong></p>
                <p style={{ color: '#7f8c8d', margin: '5px 0' }}>Prezzo a Notte: <span style={{ color: '#27ae60', fontSize: '1.2em' }}>{room.priceForNight}€</span> / notte</p>
                {isLoggedIn ? (
                  <button onClick={() => handleBookRoom(room.id)} style={{
                    marginTop: '15px',
                    width: '100%',
                    backgroundColor: '#2980b9',
                    color: 'white',
                    border: 'none',
                    padding: '10px',
                    borderRadius: '6px',
                    cursor: 'pointer',
                    fontWeight: 'bold',
                    transition: 'background-color 0.3s'
                  }}>
                    Prenota Ora
                  </button>
                ) : (
                  <p style={{ marginTop: '15px', fontSize: '0.9em', color: '#e74c3c', fontStyle: 'italic', textAlign: 'center' }}>Effettua il login per prenotare</p>
                )}
              </div>
            ))}
          </div>
        )}
      </main>

      {selectedRoom && (
        <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ backgroundColor: 'white', padding: '30px', borderRadius: '12px', width: '380px', boxShadow: '0 5px 15px rgba(0,0,0,0.3)', position: 'relative' }}>
            <button onClick={() => setSelectedRoom(null)} style={{ position: 'absolute', top: '15px', right: '15px', background: 'none', border: 'none', fontSize: '1.5em', cursor: 'pointer', color: '#7f8c8d' }}>&times;</button>
            <h3 style={{ color: '#2c3e50', marginBottom: '20px', textAlign: 'center' }}>Prenota Stanza {selectedRoom.numberRoom}</h3>
            
            <form onSubmit={onSubmitBooking} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
              <div>
                <label style={{ display: 'block', marginBottom: '5px', fontSize: '0.9em', color: '#7f8c8d' }}>Data di Check-In</label>
                <input type="date" value={checkIn} onChange={e => setCheckIn(e.target.value)} required style={{ width: '94%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc' }} />
              </div>

              <div>
                <label style={{ display: 'block', marginBottom: '5px', fontSize: '0.9em', color: '#7f8c8d' }}>Data di Check-Out</label>
                <input type="date" value={checkOut} onChange={e => setCheckOut(e.target.value)} required style={{ width: '94%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc' }} />
              </div>

              <div>
                <label style={{ display: 'block', marginBottom: '5px', fontSize: '0.9em', color: '#7f8c8d' }}>ID Cliente (es. 5)</label>
                <input type="number" placeholder="Inserisci il tuo ID Cliente" value={customerId} onChange={e => setCustomerId(e.target.value)} required style={{ width: '94%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc' }} />
              </div>
              
              <div>
                <label style={{ display: 'block', marginBottom: '5px', fontSize: '0.9em', color: '#7f8c8d' }}>Servizio di Ristorazione</label>
                  <select value={catering} onChange={e => setCatering(e.target.value)} style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc', backgroundColor: 'white' }}>
                    <option value="NONE">Nessun Pasto (+0€)</option>
                    <option value="BREAKFAST">Solo Colazione (+15€ / giorno)</option>
                    <option value="HALF_BOARD">Mezza Pensione (+30€ / giorno)</option>
                    <option value="FULL_BOARD">Pensione Completa (+50€ / giorno)</option>
                  </select>
              </div>

              <button type="submit" style={{ backgroundColor: '#27ae60', color: 'white', border: 'none', padding: '12px', borderRadius: '6px', cursor: 'pointer', fontWeight: 'bold', fontSize: '1em', marginTop: '10px' }}>Conferma Prenotazione</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default App;