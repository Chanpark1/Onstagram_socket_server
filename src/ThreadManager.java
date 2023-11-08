import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadManager extends Thread {

    private Socket m_socket;
    boolean is = true;

    @Override
    public void run() {
        super.run();

        try {
            DataInputStream in = new DataInputStream(m_socket.getInputStream());
            DataOutputStream out = new DataOutputStream(m_socket.getOutputStream());

            String text;

            while(true) {
                text = in.readUTF();

                String[] filt = text.split("@");

                if(filt.length > 1) {
                    if(text != null) {
                        boolean found = false;

                        System.out.println(text);

                        if(filt[1].equals("ACCESS")) {
                            for (int i = 0; i < SocketServer.list.size(); i++) {
                                String idx = SocketServer.list.get(i).getIdx();

                                if(idx.equals(filt[0])) {
                                    found = true;
                                    break;
                                }
                            }
                            if(!found) {
                                SocketServer.list.add(new User(m_socket, filt[0], out));
                                System.out.println("user list added");
                                System.out.println(String.valueOf(SocketServer.list.size()));
                            }

                        } else if(filt[1].equals("FOLLOW")) {
                            for (int i = 0; i < SocketServer.list.size(); i++) {

                                if(SocketServer.list.get(i).getIdx().equals(filt[2])) {
                                    System.out.println("팔로우 요청 확인 :: " + "보낸 pk : " + filt[0] +  " :: 받은 pk : " + filt[2]);
                                    try {
                                        OutputStream os = SocketServer.list.get(i).getOutput();

                                        DataOutputStream dos = new DataOutputStream(os);
                                        System.out.println("알림 보냄");

                                        dos.writeUTF(text);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        } else if(filt[1].equals("LIKE")) {
                            for(int i = 0; i < SocketServer.list.size(); i++) {
                                if(SocketServer.list.get(i).getIdx().equals(filt[3])) {
                                    System.out.println("좋아요 요청 확인");
                                    System.out.println(filt[3] + "에게 알림 보냄");

                                    try {
                                        OutputStream os = SocketServer.list.get(i).getOutput();

                                        DataOutputStream dos = new DataOutputStream(os);
                                        System.out.println("알림 보냄 성공");

                                        dos.writeUTF(text);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        } else if (filt[1].equals("CHATTING_PRIVATE")) {
                            for(int i = 0; i < SocketServer.list.size(); i++) {
                                if(SocketServer.list.get(i).getIdx().equals(filt[3])) {
                                    System.out.println("채팅 상대 확인됨");
                                    System.out.println(filt[3] + "에게 메시지 전송됨");

                                    try {

                                        OutputStream os = SocketServer.list.get(i).getOutput();

                                        DataOutputStream dos = new DataOutputStream(os);

                                        dos.writeUTF(text);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else if (filt[1].equals("CHATTING_GROUP")) {
                            int length = in.readInt();

                            byte[] serializedData = new byte[length];
                            in.readFully(serializedData);

                            ByteArrayInputStream bis = new ByteArrayInputStream(serializedData);
                            ObjectInputStream ois = new ObjectInputStream(bis);

                            ArrayList<String> idx_list = (ArrayList<String>) ois.readObject();

                            for (String item : idx_list) {
                                System.out.println(item);
                            }

                            for (int i = 0; i < SocketServer.list.size(); i++) {
                                String idx = SocketServer.list.get(i).getIdx();
                                for (int j = 0; j < idx_list.size(); j++) {
                                    String u_idx = idx_list.get(j);

                                    if(idx.equals(u_idx)) {

                                        try {

                                            OutputStream os = SocketServer.list.get(i).getOutput();

                                            DataOutputStream dos = new DataOutputStream(os);

                                            dos.writeUTF(text);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }
                        }

                    }
                }


            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private ArrayList<String> deserialized_list(byte[] data) {
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        ObjectInputStream ois = new ObjectInputStream(bis)) {
//            return (ArrayList<String>) ois.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public void setSocket(Socket _socket) {
        m_socket = _socket;
    }
}
