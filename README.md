# AWS S3 & JWT token Login & JPQL & QueryDsl

# 결과

### 초기 화면
![Untitled](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/de54454f-f952-4a73-80d3-583b6059b440)

<br>
    
### 회원 가입
![Untitled (1)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/b06d4e9a-dd7e-4d68-b2b6-7e4b907b9e0c)
    
![Untitled (2)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/abf80e7d-959f-4a20-ad40-a9d31f356bda)
    
이메일 인증을 하지 않았다는 예외 처리를 했기 때문에 /emailconfirm 페이지로
이동하지 않고 예외 처리.    
google email 인증 후
    
![Untitled (4)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/58be8091-b8ae-4102-90fb-f515e166a4f0)
    
회원 가입 완료 및 자동 로그인 <br>
JWT token도 잘 도착한 모습 확인.
    
![Untitled (5)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/e3b42a69-668b-4bc9-9ad3-3292f6d31d73)
    
DB에 저장된 모습 확인. 

<br>
    
### 상품 등록
![Untitled (6)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/09d46095-6520-4a8f-b22f-f42dab4b000c)
    
![Untitled (7)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/f45c04ab-5b8e-4079-ba47-7ca5977826a5)
    
로그인 되어 있는 상태이고 token도 있으므로 상품 등록 정상 접속 
    
![Untitled (8)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/dcec3f1f-8ba6-4f76-94f7-187965513890)
    
상품이 정상적으로 등록된 모습 확인.
    
사진 2개 모두 정상적으로 업로드. 
    
![Untitled (9)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/55d2d2a4-1980-4218-8a3b-d01ecd3fb7bb)
    
![Untitled (10)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/829ac640-8519-43ce-a829-3cfbd2a90928)
    
![Untitled (11)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/13b8d451-8ea8-4ca7-afbf-028969f4ba8e)
    
메인 페이지에서 백엔드의 /product/list 메소드가 실행되고
    
등록한 상품이 response에 잘 오고 화면에도 잘 출력되는 모습 확인.
    
저장된 사진 중 첫 번째 사진이 미리 보기 사진으로 잘 출력된다. 
    
![Untitled (12)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/e735bd54-392e-451a-9876-4014387051f0)
    
AWS S3에 지정한 위치로 잘 저장되는 모습 확인.

<br>
    
### 장바구니
![Untitled (13)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/b6547103-9fa4-408c-8eaf-66f1ade6aa4b)
    
장바구니 추가 
    
![Untitled (14)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/5e21c012-3498-4976-b710-53b4418ad523)
    
장바구니에 잘 추가된 모습 확인.
    
그리고 이미지가 보이지 않는 건 프론트에서 구현이 되지 않아서 그렇다. 
    
![Untitled (15)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/6f6271ac-4a1a-4a6d-88ce-f4c983641b05)
    
![Untitled (16)](https://github.com/Tesssssssssy/HanwhaBC-be02-pre-SpringExpandedProject-practice/assets/105422037/65044174-75c4-498f-966f-9f17a026f773)
    
그리고 아직 구현은 되지 않았지만 결제 API까지 잘 실행되는 모습 확인.
