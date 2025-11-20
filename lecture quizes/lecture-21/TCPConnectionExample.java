/*
 * Author: Orchlon Chinbat
 * Student ID: 50291063
 * Description: This program demonstrates the State Pattern by implementing a TCP connection state machine.
 */

interface TCPState {
    void open(TCPConnection connection);
    void close(TCPConnection connection);
    void acknowledge(TCPConnection connection);
}

// Concrete state: Closed
class Closed implements TCPState {
    @Override
    public void open(TCPConnection connection) {
        System.out.println("Opening connection...");
        connection.setState(new Listening());
    }

    @Override
    public void close(TCPConnection connection) {
        System.out.println("Connection already closed.");
    }

    @Override
    public void acknowledge(TCPConnection connection) {
        System.out.println("No connection to acknowledge.");
    }
}

// Concrete state: Listening
class Listening implements TCPState {
    @Override
    public void open(TCPConnection connection) {
        System.out.println("Connection already open. Waiting for acknowledgment...");
    }

    @Override
    public void close(TCPConnection connection) {
        System.out.println("Closing connection...");
        connection.setState(new Closed());
    }

    @Override
    public void acknowledge(TCPConnection connection) {
        System.out.println("Acknowledgment received. Connection established.");
        connection.setState(new Established());
    }
}

// Concrete state: Established
class Established implements TCPState {
    @Override
    public void open(TCPConnection connection) {
        System.out.println("Connection already established.");
    }

    @Override
    public void close(TCPConnection connection) {
        System.out.println("Closing established connection...");
        connection.setState(new Closed());
    }

    @Override
    public void acknowledge(TCPConnection connection) {
        System.out.println("Already acknowledged.");
    }
}

// Context
class TCPConnection {
    private TCPState state;

    public TCPConnection() {
        state = new Closed(); // initial state
    }

    public void setState(TCPState state) {
        this.state = state;
    }

    public void open() {
        state.open(this);
    }

    public void close() {
        state.close(this);
    }

    public void acknowledge() {
        state.acknowledge(this);
    }
}

public class TCPConnectionExample {
    public static void main(String[] args) {
        TCPConnection connection = new TCPConnection();

        connection.open();         // Closed -> Listening
        connection.acknowledge();  // Listening -> Established
        connection.open();         // Already established
        connection.close();        // Established -> Closed
        connection.close();        // Already closed
    }
}
