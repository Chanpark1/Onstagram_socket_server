import java.io.DataOutputStream;
import java.net.Socket;

public class User {
    Socket userSocket;
    String idx;

    DataOutputStream output;

    public User(Socket userSocket, String idx, DataOutputStream output) {
        this.userSocket = userSocket;
        this.idx = idx;
        this.output = output;
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    public void setUserSocket(Socket userSocket) {
        this.userSocket = userSocket;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public void setOutput(DataOutputStream output) {
        this.output = output;
    }
}
