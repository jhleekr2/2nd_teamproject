package server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import view.dto.Message;

//웹 소켓 통신을 위한 엔드포인트 클래스, 클라이언트와의 연결을 관리하고 통신을 수행하는 로직 작성
@ServerEndpoint("/socket/chatserver")
public class ChatServer {

	//서버에서는 접속 유저의 소켓을 각각 관리해야함 - arraylist로 관리
	private static List<Session> sessionList = new ArrayList<Session>();

	//웹소켓 연결이 열릴 때 자동으로 호출
	@OnOpen
	public void handleOpen(Session session) {
		sessionList.add(session);
		checkSessionList();			//접속자 확인
		System.out.println("웹소켓 접속!");
	}
	
	//클라이언트로부터 메시지를 수신할 때 호출
	@OnMessage
	public void handleMessage(String msg, Session session) {
		//전달받은 메시지 확인
		System.out.println(msg);
		
		//JSON형식으로 전달받은 문자열을 자바클래스 객체로 변환
		//이때 구글의 GSON API 사용한다
		Gson gson = new Gson();
		
		//Gson 형식의 문자열(msg)을 자바 객체(Message 클래스의 인스턴스)로 변환
	 	Message message =  gson.fromJson(msg, Message.class);
		
	 	if (message.getCode().equals("1")) {
	 		//모든 접속자 중 방금 메시지 보낸 세션을 제외한 나머지 세션(소켓)에
	 		//현재 접속자를 알리는 메시지를 전달
	 		for (Session s : sessionList) {
	 			if (s != session) {
	 				try {
	 					//세션과 관련된 소켓을 반환하여 메시지를 클라이언트로 보냄
	 					s.getBasicRemote().sendText(msg);
					} catch (Exception e) {
						e.printStackTrace();
					}
	 			}
	 		}
	 	} else if(message.getCode().equals("2")) {
	 		//세션 제거 후 나머지 모든 세션에 유저 퇴장 메시지를 전달
	 		sessionList.remove(session);
	 		for (Session s : sessionList) {
	 			if (s != session) {
	 				try {
	 					//세션과 관련된 소켓을 반환하여 메시지를 클라이언트로 보냄
	 					s.getBasicRemote().sendText(msg);
	 				} catch (Exception e) {
	 					e.printStackTrace();
	 				}
	 			}
	 		}
	 	} else if(message.getCode().equals("3")) {
	 		//보낸 사람빼고 나머지 사람에게 전달한다
	 		for (Session s : sessionList) {
	 			if(s != session) {
	 				try {
						s.getBasicRemote().sendText(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
	 			}
	 		}
	 	}
	}
	
	//접속자를 확인하는 메서드
	private void checkSessionList() {
		System.out.println();
		System.out.println("[Session List]");
		for (Session session : sessionList) {
			System.out.println(session.getId());
		}
		System.out.println();
	}
	
	//안정성을 위한 메서드 - 연결 끊어진 세션이 있으면 세션리스트에서 제거
	//사실 상대방이 나간다는 코드 2번 받았을때 세션을 제거하긴 하지만, 안정성을 위해
	//웹소켓이 열렸을 때 다시한번 확인하는 코드 추가
	private void clearSessionList() {
		
		//List를 이용한 컬렉션은 foreach문 내에서 요소의 추가/삭제 행동을 못함
		//그래서 1. 일반 forans문 쓰거나 2. iterator 사용해야 한다.
		Iterator<Session> iter = sessionList.iterator();

		while(iter.hasNext()) {
			if(!(iter.next()).isOpen()) {
				//연결 끊어진 세션이 있다면 리스트에서 제거한다.
				iter.remove();
			}
		}
		
	}
}
