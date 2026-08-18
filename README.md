# Full-Stack Hotel Booking & Property Management System (SaaS)

Questo progetto rappresenta un sistema completo di gestione e prenotazione camere per Hotel (SaaS), sviluppato seguendo un'architettura disaccoppiata (Decoupled Architecture) con un'interfaccia utente interattiva in **React** e un solido motore backend in **Java & Spring Boot 3**, integrato con un database relazionale **PostgreSQL** ospitato in **Docker**.

L'applicazione non è un semplice CRUD, ma implementa algoritmi complessi di business logic, sistemi di sicurezza crittografica e integrazioni con servizi di posta elettronica terzi.

## Architettura e Caratteristiche Tecniche

### 1. Backend (Spring Boot 3 & JPA)
- **Architettura a 3 livelli**: Separazione netta delle responsabilità tramite Controller, Service e Repository.
- **Persistenza Relazionale**: Database relazionale **PostgreSQL** mappato tramite **JPA/Hibernate**, con gestione automatica delle relazioni (`@ManyToOne`, `@OneToMany`) e dei vincoli di integrità referenziale.
- **Gestione della Concorrenza**: Implementazione del **Locking Ottimistico** tramite l'annotazione `@Version` sull'entità delle camere per prevenire conflitti di prenotazione simultanei (Race Conditions).

### 2. Sicurezza (Spring Security & Crittografia)
- **Criptazione delle Credenziali**: Hashing a senso unico delle password nel database tramite algoritmo **BCrypt**.
- **Autenticazione & Autorizzazione**: Controllo degli accessi basato sui ruoli (**RBAC**) integrato direttamente con il database tramite l'implementazione di `UserDetailsService`.
- **Politica dei Permessi**: Gestione granulare delle rotte pubbliche (registrazione, lettura camere) e rotte protette (creazione prenotazioni per utenti autenticati, gestione camere riservata all'amministratore con ruolo `ADMIN`).
- **Configurazione CORS esplicita**: Abilitazione della comunicazione inter-origine sicura tra il server di sviluppo React e l'API Spring Boot.

### 3. Frontend (React)
- **Single Page Application (SPA)**: Sviluppo di un'interfaccia interattiva e reattiva con gestione dello stato globale tramite React Hooks (`useState`, `useEffect`).
- **Modulo di Prenotazione Interattivo**: Form integrato in un modale overlay che raccoglie dinamicamente i dati di prenotazione (date, ospiti, ristorazione) senza ricaricare la pagina, preservando lo stato di autenticazione.
- **Integrazione Basic Auth**: Generazione del token di sicurezza Base64 e invio automatico nelle intestazioni HTTP (`Authorization: Basic`) per le chiamate protette.

---

## Logica di Business Avanzata (Algoritmi)

### Sistema Anti-Doppia Prenotazione
Implementazione di una query JPQL personalizzata nel repository per bloccare le prenotazioni sovrapposte sullo stesso numero di stanza:
$$\text{InizioA} < \text{FineB} \quad \text{AND} \quad \text{FineA} > \text{InizioB}$$

### Calcolo Dinamico delle Tariffe (Ristorazione)
Integrazione di un Enum Java `CateringOption` con prezzi preimpostati (Colazione, Mezza Pensione, Pensione Completa). Il sistema calcola dinamicamente il prezzo totale sul server in base ai giorni di soggiorno e al servizio di ristorazione scelto, aggiornando la prenotazione e il database.

### Sistema di Recensioni Blindato
Per prevenire recensioni false o non verificate, il sistema consente a un utente di lasciare un voto (da 1 a 5 stelle) e un commento per una determinata stanza solo se esiste una prenotazione passata (completata con successo) intestata a quell'utente per quella specifica camera.

### Integrazione Email (Spring Mail)
Collegamento con un servizio SMTP di test (**Mailtrap**) per inviare in tempo reale un'email di conferma formattata con tutti i dettagli della prenotazione (ID, date, stanza, prezzo) non appena la transazione viene registrata nel database.

---

## Infrastruttura e DevOps
- **Docker Compose**: Configurazione del container PostgreSQL isolato con persistenza dei dati abilitata tramite volumi locali.
- **Database di Test**: Utilizzo di profili di configurazione per testare l'applicazione in locale prima della distribuzione.

## Come Avviare il Progetto

### Prerequisiti
- Docker Desktop attivo
- Node.js e NPM
- Java 21 e Maven

### Istruzioni di avvio rapido
1. Avvia il database in background:
   ```bash
   cd hotel
   docker-compose up -d
   ```
2. Configura le credenziali di test per le email in application.properties (SMTP Mailtrap).
3. Avvia l'applicazione Spring Boot da STS o da terminale:
   ```bash
   mvn spring-boot:run
   ```
4. Avvia l'applicazione React:
   ```bash
   cd hotel-frontend
   npm install
   npm run dev
   ```
5. Apri il browser all'indirizzo http://localhost:5173/ per utilizzare l'applicazione.
